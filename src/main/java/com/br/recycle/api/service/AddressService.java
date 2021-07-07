package com.br.recycle.api.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.recycle.api.bean.AddressResponseBean;
import com.br.recycle.api.exception.AddressNotFoundException;
import com.br.recycle.api.exception.EntityNotFoundException;
import com.br.recycle.api.exception.InternalServerException;
import com.br.recycle.api.exception.NoContentException;
import com.br.recycle.api.feign.ViaZipCodeClient;
import com.br.recycle.api.model.Address;
import com.br.recycle.api.repository.AddressRepository;

/**
 * Classe responsável por realizar os serviços das transações de comunicação
 * com a base de dados relacionado aos endereços.. 
 */
@Service
public class AddressService {

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
	public List<Address> findAll() {
		List<Address> addresses = addressRepository.findAll();
		
		if (addresses.isEmpty()) {
			throw new NoContentException("Não existem endereços cadastrados.");
		}
		
		return addresses;
	}

	/**
	 * Método responsável por realizar o cadastro de endereço na base de dados.
	 * @param {@code Address} address
	 * @return {@code Address}
	 * 		- Caso não ocorra nenhum erro, o cadastro é feito com sucesso na base dedados.
	 * 		- Caso ocorra algum erro, retorna um erro de sistema genérico, para a 
	 * 	mensagem ser tratada.
	 */
	@Transactional
	public Address save(Address address) {
		try {
			return addressRepository.save(address);			
		} catch (Exception e) {
			throw new InternalServerException("Erro ao realizar o cadastro de enderenço.");
		}
	}

	/**
	 * Método responsável por buscar o endereço de acordo com o ID cadastrado na base de dados.
	 * @param {@code Long} - addressId
	 * @return {@code Address}
	 * 		- Caso exista o ID na base de dados, retorna o endereço.
	 * 		- Caso o ID não exista, retorna que a entidade de endereço 
	 * 	não exista.
	 */
	public Address findOrFail(Long addressId) {
		return addressRepository.findById(addressId)
				.orElseThrow(() -> new AddressNotFoundException(addressId));
	}

	/**
	 * Método responsável por buscar os endereços na classe de comunicação
	 * com o endpoint externo e retornar os endereços.
	 * @param {@code String} - zipCode
	 * @return {@code AddressResponseBean}
	 * 		- Retorna os dados de endereço de acordo com o CEP.
	 * 		- Caso ocorra uma exceção na busca, lança que a entidade (CEP)
	 * 	não foi encontrada.
	 */
	public AddressResponseBean searchAddress(String zipCode) {
		try {
			return viaZipCodeClient.searchAddress(zipCode);
		} catch (Exception e) {
			throw new EntityNotFoundException("O CEP informado não existe ou está inválido.");
		}
	}

	/**
	 * Método responsável por realizar a atualização de dados
	 * na base de dados. Primeiro consulta o endereço atual de acordo
	 * com o ID e depois, salva.
	 * @param {@code Long} - id
	 * @param {@code Address} address
	 */
	@Transactional
	public void update(Long id, Address address) {
		Address addressActual = findOrFail(id);
		address.setId(addressActual.getId());
		save(address);
	}

	/**
	 * Método responsável por realizar a deleção de um cadastro
	 * na base de dados de acordo com o ID informado.
	 * @param {@code Long} - id
	 */
	@Transactional
	public void delete(Long id) {
		findOrFail(id);
		addressRepository.deleteById(id);
	}
}