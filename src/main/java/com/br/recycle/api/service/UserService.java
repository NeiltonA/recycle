package com.br.recycle.api.service;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.br.recycle.api.exception.NegocioException;
import com.br.recycle.api.exception.UserNaoEncontradoException;
import com.br.recycle.api.model.User;
import com.br.recycle.api.payload.RoleName;
import com.br.recycle.api.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository repository;
	
//	@Autowired
//	private GrupoService cadastroGrupo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@PersistenceContext
    private EntityManager manager;
	
	@Transactional
	public User salvar(User user) {
		repository.save(user);
		
		Optional<User> usuarioExistente = repository.findByEmail(user.getEmail());
		
		if (usuarioExistente.isPresent() && !usuarioExistente.get().equals(user)) {
			throw new NegocioException(
					String.format("Já existe um usuário cadastrado com o e-mail %s", user.getEmail()));
		}
		
		if (user.isNovo()) {
			user.setPassword(passwordEncoder.encode(user.getPassword()));
		}
		
		return repository.save(user);
	}
	
	@Transactional
	public User alterarSenha(Long userId, String passwordAtual, String novoPassword) {
		User user = FetchOrFail(userId);
		
		
		
		if (!passwordEncoder.matches(passwordAtual, user.getPassword())) {
			throw new NegocioException("Password atual informada não coincide com a password do user.");
		}
		Object nameGroup  = findByGroup(userId);
		user.setRole((RoleName.valueOf(nameGroup.toString())));
		user.setPassword(passwordEncoder.encode(novoPassword));
		
		return repository.save(user);
	}

//	@Transactional
//	public void desassociarGrupo(Long usuarioId, Long grupoId) {
//		Usuario usuario = buscarOuFalhar(usuarioId);
//		Grupo grupo = cadastroGrupo.buscarOuFalhar(grupoId);
//		
//		usuario.removerGrupo(grupo);
//	}
	
//	@Transactional
//	public void associarGrupo(Long usuarioId, Long grupoId) {
//		Usuario usuario = buscarOuFalhar(usuarioId);
//		Grupo grupo = cadastroGrupo.buscarOuFalhar(grupoId);
//		
//		usuario.adicionarGrupo(grupo);
//	}
	
	public User FetchOrFail(Long userId) {
		return repository.findById(userId)
			.orElseThrow(() -> new UserNaoEncontradoException(userId));
	}
	
	
    public Object findByGroup(Long userId) {
        Object group =  manager.createNativeQuery("select s.name from users u inner join user_roles r on u.id_user= r.user_id  inner join roles s on s.id = r.role_id and u.id_user ='" + userId + "'")
        		.getSingleResult();
        return group;
            
    }

	
}