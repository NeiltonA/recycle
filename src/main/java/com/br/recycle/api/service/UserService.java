package com.br.recycle.api.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.br.recycle.api.exception.BusinessException;
import com.br.recycle.api.exception.UserNotFoundException;
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
    public User save(User user) {
    	
    	if (user.getRole().name().isEmpty()) {
    		Object nameGroup = findByGroup(user.getId());
            user.setRole((RoleName.valueOf(nameGroup.toString())));	
		}
        
        return repository.save(user);

		/*
		 * Optional<User> foundUser = repository.findByEmail(user.getEmail());
		 * 
		 * if (foundUser.isPresent() && !foundUser.get().equals(user)) { throw new
		 * BusinessException(
		 * String.format("Já existe um usuário cadastrado com o e-mail %s",
		 * user.getEmail())); }
		 * 
		 * if (user.isNew()) {
		 * user.setPassword(passwordEncoder.encode(user.getPassword())); }
		 */
    }

    @Transactional
    public User changePassword(Long userId, String passwordAtual, String novoPassword) {
        User user = fetchOrFail(userId);

        if (!passwordEncoder.matches(passwordAtual, user.getPassword())) {
            throw new BusinessException("A senha atual informada não coincide com a password do user.");
        }

        Object nameGroup = findByGroup(userId);
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

    public User fetchOrFail(Long userId) {
        return repository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
    }

    public Object findByGroup(Long userId) {
        Object group = manager.createNativeQuery("select s.name from users u inner join user_roles r on u.id_user= r.user_id  inner join roles s on s.id = r.role_id and u.id_user ='" + userId + "'")
                .getSingleResult();
        return group;
    }

}