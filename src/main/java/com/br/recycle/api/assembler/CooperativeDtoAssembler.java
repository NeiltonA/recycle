package com.br.recycle.api.assembler;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.br.recycle.api.bean.CnpjResponseBean;
import com.br.recycle.api.model.Address;
import com.br.recycle.api.model.Cooperative;
import com.br.recycle.api.model.User;
import com.br.recycle.api.payload.AddressIdInput;
import com.br.recycle.api.payload.CooperativeDtoOut;
import com.br.recycle.api.payload.CooperativeInput;
import com.br.recycle.api.payload.DictionaryCnpj;
import com.br.recycle.api.payload.UserIdInput;
import com.br.recycle.api.utils.RegexCharactersUtils;

/**
 * Classe responsável por transformar os dados relacionado aos modelos da cooperativa
 * 
 */
@Component
public class CooperativeDtoAssembler {

	private ModelMapper modelMapper = new ModelMapper();
	
	/**
	 * Método responsável por transformar os dados de entrada da cooperativa
	 * em dados de entidade. Não foi utilizado o modelMaper, devido
	 * a necessidade de alteração de alguns dados. 
	 * @param cooperativeInput - {@code CooperativeInput}
	 * @return {@code Cooperative} - cooperative
	 */
	public Cooperative toDomainObject(CooperativeInput cooperativeInput) {
		Cooperative cooperative = new Cooperative();
		cooperative.setCompanyName(cooperativeInput.getCompanyName());
		cooperative.setFantasyName(cooperativeInput.getFantasyName());
		cooperative.setCnpj(RegexCharactersUtils.removeSpecialCharacters(cooperativeInput.getCnpj()));
		cooperative.setUser(toDomainDomainCooperativeUser(cooperativeInput.getUser()));
		cooperative.setAddress(toDomainAddress(cooperativeInput.getAddress()));
		
		return cooperative;
	}
	
	private Address toDomainAddress(AddressIdInput addressIdInput) {
		if (Objects.isNull(addressIdInput)) {
			return null;
		}
		
		Address address = new Address();
		address.setId(Objects.isNull(addressIdInput.getId()) ? null : addressIdInput.getId());
		return address;
	}

	/**
	 * Método responsável por mapear o objeto de usuário para 
	 * salvar no banco. Não foi utilizado modelMaper,
	 * devido a necessidade de salvar alguns dados diferentes.
	 * @param userIdInput - {@code UserIdInput}
	 * @return {@code User} - user
	 */
	private User toDomainDomainCooperativeUser(UserIdInput userIdInput) {
		if (Objects.isNull(userIdInput)) {
			return null;
		}
		
		User user = new User();
		user.setId(Objects.isNull(userIdInput.getId()) ? null : userIdInput.getId());
		return user;
	}

	public CooperativeDtoOut toModel(Cooperative cooperative) {
		return modelMapper.map(cooperative, CooperativeDtoOut.class);
	}
	
	public List<CooperativeDtoOut> toCollectionModel(List<Cooperative> cooperatives) {
		return cooperatives.stream()
				.map(cooperative -> toModel(cooperative))
				.collect(Collectors.toList());
	}	
	
	public DictionaryCnpj toDictionary(CnpjResponseBean cnpjResponseBean) {
		DictionaryCnpj dictionary = new DictionaryCnpj();
		dictionary.setFantasyName(cnpjResponseBean.getFantasia());
		dictionary.setCompanyName(cnpjResponseBean.getNome());
		dictionary.setSituation(cnpjResponseBean.getSituacao());
		dictionary.setType(cnpjResponseBean.getTipo());
		dictionary.setState(cnpjResponseBean.getUf());

		
		return dictionary;
	}
}