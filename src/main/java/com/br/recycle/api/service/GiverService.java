package com.br.recycle.api.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.br.recycle.api.bean.GiverResponseBean;
import com.br.recycle.api.exception.BusinessException;
import com.br.recycle.api.exception.EntityInUseException;
import com.br.recycle.api.exception.GiverNotFoundException;
import com.br.recycle.api.exception.UserNotFoundException;
import com.br.recycle.api.model.Giver;
import com.br.recycle.api.model.User;
import com.br.recycle.api.repository.GiverRepository;
import com.br.recycle.api.repository.UserRepository;

import lombok.extern.log4j.Log4j2;


@Service
@Log4j2
public class GiverService {

	private static final String MSG_GIVER_EM_USO = "Giver de código %d não pode ser removida, pois está em uso";

	@Autowired
	private GiverRepository repository;
	
	@Autowired
	private UserRepository userRepository;

	@PersistenceContext
	private EntityManager manager;
	
	@Transactional
	public Giver save(Long id) {
		
		if (!userRepository.findById(id).isPresent()) {
			throw new UserNotFoundException(id);
		}
		
		Optional<Giver> foundGiver = repository.findByUserId(id);

		if (foundGiver.isPresent()) {
			log.error("Doador já cadastrado!");
			throw new BusinessException(
					String.format("Já existe um doador cadastrado com o código de usuário %s", id));

		}
		Giver giver = new Giver();
		User user = new User();
		user.setId(id);
		giver.setUser(user);
		return repository.save(giver);
	}

	@Transactional
	public void excluir(Long giverId) {
		try {
			repository.deleteById(giverId);
			repository.flush();

		} catch (EmptyResultDataAccessException e) {
			throw new GiverNotFoundException(giverId);

		} catch (DataIntegrityViolationException e) {
			throw new EntityInUseException(String.format(MSG_GIVER_EM_USO, giverId));
		}
	}

	public Giver buscarOuFalhar(Long giverId) {
		return repository.findById(giverId).orElseThrow(() -> new GiverNotFoundException(giverId));
	}
	
	
	@SuppressWarnings({ "deprecation", "unchecked" })
	public List<GiverResponseBean> consultByAll() {
	
		return manager
				.createNativeQuery("select u.name, u.email, u.cpf_cnpj, u.cell_phone,  a.city, a.complement, a.neighborhood, a.number, a.state, a.street, a.zip_code from giver g inner join users u on g.id_giver=u.id_user inner join address a on u.id_user=a.id_user")
				.unwrap(org.hibernate.query.Query.class)
				.setResultTransformer(new org.hibernate.transform.AliasToBeanResultTransformer(GiverResponseBean.class)).list();

	}

}