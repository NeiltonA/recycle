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


@Service
public class UserDetailsServiceImpl implements UserDetailsService {
  @Autowired
  UserRepository userRepository;

  @Override
  @Transactional
  public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
    User user = userRepository.findById(Long.valueOf(id))
        .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com o email: " + id));

    return MainUser.create(user);
  }

}
