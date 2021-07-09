package com.br.recycle.api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.br.recycle.api.bean.AddressResponseBean;
import com.br.recycle.api.exception.AddressNotFoundException;
import com.br.recycle.api.exception.InternalServerException;
import com.br.recycle.api.exception.NoContentException;
import com.br.recycle.api.exception.UnprocessableEntityException;
import com.br.recycle.api.feign.ViaZipCodeClient;
import com.br.recycle.api.model.Address;
import com.br.recycle.api.model.User;
import com.br.recycle.api.repository.AddressRepository;

import lombok.extern.log4j.Log4j2;

/**
 * Classe responsável por realizar os serviços das transações de comunicação
 * com a base de dados relacionado aos endereços.. 
 */
@Service
@Log4j2
public class AddressService {

	//private static final String MSG_ADDRESS_EM_USO = "Address de código %d não pode ser removida, pois está em uso";

	private AddressRepository addressRepository;
	private ViaZipCodeClient viaZipCodeClient;
	
	@Autowired
	public AddressService(AddressRepository addressRepository, ViaZipCodeClient viaZipCodeClient) {
		this.addressRepository = addressRepository;
		this.viaZipCodeClient = viaZipCodeClient;
	}

	/**
	 * Método responsável por buscar todos os endereços na base de dados.
	 * @return {@code List<Address>}
	 * 		- Caso a base de dados esteja vazia, retorna que não foi encontrado conteúdo.
	 * 		- Caso esteja preenchida, retorna os endereços cadastrados.
	 */
	//@Cacheable(cacheNames = "Address", key="#user", condition = "#user != null")
	public List<Address> findAll(Long user) {
		log.info("Address No cache");
		List<Address> response = new ArrayList<>();
		
		if (Objects.nonNull(user)) {
			response = addressRepository.findByUserId(user);
			return validateEmpty(response);
		} else {
			response = addressRepository.findAll();
			return validateEmpty(response);
		}
	}

	/**
	 * Método responsável por salvar os dados de endereço na base de dados.
	 * @param {@code Address} - address
	 * @return 
	 * 		- Caso ocorra tudo corretamente, o endereço é salvo com sucesso e retornado.
	 * 		- Caso ocorra algum problema, retorna que houve um erro na base de dados.
	 */
	@Transactional
	//@CacheEvict(cacheNames = "Address", allEntries = true)
	public void save(Address address) {
		try {
			addressRepository.save(address);			
		} catch (Exception exception) {
			throw new InternalServerException("Erro ao salvar os dados de cadastro do endereço.");
		}
	}
	
	/**
	 * Método responsável por realizar a ação de alterar parcialmente os dados de endereço
	 * de acordo com o que foi informado no parâmetro.
	 * Realiza uma validação de comparação dos dados atuais com os novos dados informados.
	 * @param {@code Address} - address
	 * 		- Os novos dados a serem atualizados na base de dados
	 * @param {@code Long} - id
	 */
	@Transactional
	//@CachePut(cacheNames = "Address", key = "#address.getId()")
	public void updatePartial(final Address address, Long id) {
		try {
			Address addressActual = findOrFail(id);

			addressActual.setComplement(validateNull(address.getComplement(), addressActual.getComplement()));
			addressActual.setNumber(validateNull(address.getNumber(), addressActual.getNumber()));
			
			addressRepository.save(addressActual);
		} catch (DataIntegrityViolationException e) {
			throw new AddressNotFoundException(String.format("Erro ao alterar o endereço"));
		}
	}

	/**
	 * Método responsável por realizar a ação de alterar por completo os dados de endereço
	 * de acordo com o que foi informado no parâmetro.
	 * @param {@code Address} - address
	 * 		- Os novos dados a serem atualizados na base de dados
	 * @param {@code Long} - id
	 */
	@Transactional
	//@CachePut(cacheNames = "Address", key = "#address.getId()")
	public void update(final Address address, Long id) {
		try {
			Address addressActual = findOrFail(id);
			address.setId(addressActual.getId());
			address.setUser(getUser(addressActual));
			
			addressRepository.save(address);
		} catch (DataIntegrityViolationException e) {
			throw new AddressNotFoundException(String.format("Erro ao alterar o endereço"));
		}
	}

	/**
	 * Método responsável por buscar o endereço por ID e validar e se está presente na base de dados.
	 * @param {@code Long} - addressId
	 * @return {@code Address}
	 * 		- Caso o endereço seja encontrado, é retornado os dados
	 * 		- Caso o endereço não esteja na base de dados, retorna que o a entidade endereço 
	 * 	não foi encontrada.
	 */
	//@Cacheable(cacheNames = "Address", key="#addressId")
	public Address findOrFail(Long addressId) {
		return addressRepository.findById(addressId).orElseThrow(() -> new AddressNotFoundException(addressId));
	}

	/**
	 * Método responsável por buscar o endereço de acordo com o CEP informado.
	 * @param {@code String} zipCode
	 * @return {@code AddressResponseBean}
	 * 		- Caso o CEP seja informado contenha conteúdo, será retornado os dados de endereço.
	 * 		- Caso ocorra algum erro, será lançado a exceção que a entidade não pode ser procesada.
	 */
	public AddressResponseBean searchAddress(String zipCode) {
		try {
			return viaZipCodeClient.searchAddress(zipCode);
		} catch (Exception e) {
			throw new UnprocessableEntityException("De acordo com o CEP informado não está relacionado a nenhum endereço");
		}	
	}

	/**
	 * Método resposnável por realizar a deleção do endereço da base de dados.
	 * @param {@code Long} id
	 */
	public void deleteById(Long id) {
		findOrFail(id);
		addressRepository.deleteById(id);
	}
	
	/**
	 * Método responsável por validar se os dados novos a serem inseridos estão nulos.
	 * Caso eles estiverem nulos, é considerado os dados velhos, na hora do retorno.
	 * @param {@code String} - newValue
	 * @param {@code String} - oldValue
	 * @return {@code String}
	 * 		- Caso os dados novos sejam diferentes de nulo, é retornado o novo valor.
	 * 		- Caso os dados novos sejam iguais a nulo, é retorno o valor antigo.
	 */
	private String validateNull(String newValue, String oldValue) {
		return Objects.isNull(newValue) ? oldValue : newValue;
	}
	
	/**
	 * Método responsável por validar se a lista de endereços está vazia.
	 * Caso esteja vazia, retorna uma resposta de sucesso mas sem conteúdo.
	 * @param {@code List<Address>} - addresses
	 * @return {@code List<Address>} - Caso não tenha erro, retorna a lista de endereços.
	 */
	private List<Address> validateEmpty(List<Address> addresses) {
		if (addresses.isEmpty()) {
			throw new NoContentException("A lista de endereços está vazia.");
		}
		
		return addresses;
	}
	
	/**
	 * Método responsável por montar o id do usuário e não permitir 
	 * sua alteração ou deixar o valor nulo.
	 * @param {@code Address} - addressActual
	 * @return {@code User} - user
	 */
	private User getUser(Address addressActual) {
		User user = new User();
		user.setId(addressActual.getUser().getId());
		
		return user;
	}
	
//	@Transactional
//	@CacheEvict(cacheNames = "Address", key="#addressId")
//	public void remove(Long addressId) {
//		try {
//			addressRepository.deleteById(addressId);
//			addressRepository.flush();
//
//		} catch (EmptyResultDataAccessException e) {
//			throw new AddressNotFoundException(addressId);
//
//		} catch (DataIntegrityViolationException e) {
//			throw new EntityInUseException(String.format(MSG_ADDRESS_EM_USO, addressId));
//		}
//	}
}