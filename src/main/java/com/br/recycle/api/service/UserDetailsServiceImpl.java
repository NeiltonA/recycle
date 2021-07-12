package com.br.recycle.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.br.recycle.api.model.User;
import com.br.recycle.api.repository.UserRepository;
import com.br.recycle.api.security.MainUser;

/**
 * Classe de serviço responsável por detalhar os dados de um usuário autenticad.
 *
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	private UserRepository userRepository;
	
	@Autowired
	public UserDetailsServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
		User user = userRepository.findById(Long.valueOf(id))
				.orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com o id: " + id));

		return MainUser.create(user);
	}

}
