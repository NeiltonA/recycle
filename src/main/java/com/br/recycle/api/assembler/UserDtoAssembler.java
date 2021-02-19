package com.br.recycle.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.br.recycle.api.model.User;
import com.br.recycle.api.payload.UserDto;


@Component
public class UserDtoAssembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public UserDto toModel(User user) {
		return modelMapper.map(user, UserDto.class);
	}
	
	public List<UserDto> toCollectionModel(List<User> users) {
		return users.stream()
				.map(user -> toModel(user))
				.collect(Collectors.toList());
	}
	
}
