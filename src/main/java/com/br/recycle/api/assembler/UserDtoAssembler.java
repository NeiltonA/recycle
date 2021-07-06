package com.br.recycle.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.br.recycle.api.model.User;
import com.br.recycle.api.payload.UserDtoIn;
import com.br.recycle.api.payload.UserDtoOut;
import com.br.recycle.api.payload.UserInput;

/**
 * Classe responsável por transformar os dados relacionado aos modelos do Usuário.
 * 
 */
@Component
public class UserDtoAssembler {

	private ModelMapper modelMapper = new ModelMapper();
	
	public User toDomainObject(UserInput userInput) {
		return modelMapper.map(userInput, User.class);
	}
	
	public UserDtoOut toModel(User user) {
		return modelMapper.map(user, UserDtoOut.class);
	}
	
	public List<UserDtoOut> toCollectionModel(List<User> users) {
		return users.stream()
				.map(user -> toModel(user))
				.collect(Collectors.toList());
	}
	
	public User toDomainObject(UserDtoIn userDtoIn) {
		return modelMapper.map(userDtoIn, User.class);
	}
}