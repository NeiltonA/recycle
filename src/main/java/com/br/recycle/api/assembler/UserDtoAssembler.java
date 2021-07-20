package com.br.recycle.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.br.recycle.api.model.User;
import com.br.recycle.api.payload.UserDtoIn;
import com.br.recycle.api.payload.UserDtoOut;
import com.br.recycle.api.payload.UserInput;
import com.br.recycle.api.utils.RegexCharactersUtils;

/**
 * Classe responsável por transformar os dados relacionado aos modelos do Usuário.
 * 
 */
@Component
public class UserDtoAssembler {

	private ModelMapper modelMapper = new ModelMapper();
	
	/**
	 * Objeto sendo montado, sem o auxilio do modelMapper, devido a 
	 * remoção dos campos que foram informados no momento da entrada
	 * para ser salvo na base de dados, sem caracteres.
	 * 
	 * @param userInput - {@code UserInput}
	 * @return {@code User}
	 * 		- Retorna um usuário montado.
	 */
	public User toDomainObject(UserInput userInput) {
		User user = new User();
		user.setName(userInput.getName());
		user.setEmail(userInput.getEmail());
		user.setCellPhone(RegexCharactersUtils.removeSpecialCharacters(userInput.getCellPhone()));
		user.setIndividualRegistration(
				RegexCharactersUtils.removeSpecialCharacters(userInput.getIndividualRegistration()));
		user.setPassword(userInput.getPassword());
		user.setConfirmPassword(userInput.getConfirmPassword());
		user.setFlowIndicator(userInput.getFlowIndicator());
		user.setActive(user.getActive());
		
		return user;
	}
	
	public UserDtoOut toModel(User user) {
		return modelMapper.map(user, UserDtoOut.class);
	}
	
	public List<UserDtoOut> toCollectionModel(List<User> users) {
		return users.stream()
				.map(user -> toModel(user))
				.collect(Collectors.toList());
	}
	
	/**
	 * Objeto sendo montado, sem o auxilio do modelMapper, devido a 
	 * remoção dos campos que foram informados no momento da entrada
	 * para ser salvo na base de dados, sem caracteres.
	 * 
	 * @param userDtoIn - {@code UserDtoIn}
	 * @return {@code User}
	 * 		- Retorna um usuário montado.
	 */
	public User toDomainObject(UserDtoIn userDtoIn) {
		User user = new User();
		user.setName(userDtoIn.getName());
		user.setEmail(userDtoIn.getEmail());
		user.setCellPhone(RegexCharactersUtils.removeSpecialCharacters(userDtoIn.getCellPhone()));
		user.setIndividualRegistration(
				RegexCharactersUtils.removeSpecialCharacters(userDtoIn.getIndividualRegistration()));
		//user.setFlowIndicator(userDtoIn.getFlowIndicator());
		user.setActive(userDtoIn.getActive());
		
		return user;
	}
}
