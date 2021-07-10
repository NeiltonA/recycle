package com.br.recycle.api.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.recycle.api.exception.GiverNotFoundException;
import com.br.recycle.api.exception.NoContentException;
import com.br.recycle.api.exception.UnprocessableEntityException;
import com.br.recycle.api.model.Cooperative;
import com.br.recycle.api.model.Giver;
import com.br.recycle.api.model.User;
import com.br.recycle.api.repository.CooperativeRepository;
import com.br.recycle.api.repository.GiverRepository;

/**
 * Classe responsável por realizar os serviços das transações de comunicação
 * com a base de dados relacionado aos doadores. 
 */
@Service
public class GiverService {

	//private static final String MSG_GIVER_EM_USO = "Giver de código %d não pode ser removida, pois está em uso";
	//@PersistenceContext
	//private EntityManager manager;
	
	private GiverRepository giverRepository;
	private UserService userService;
	private CooperativeRepository cooperativeRepository;

	@Autowired
	public GiverService(GiverRepository giverRepository, UserService userService, CooperativeRepository cooperativeRepository) {
		this.giverRepository = giverRepository;
		this.userService = userService;
		this.cooperativeRepository = cooperativeRepository;
	}

	/**
	 * O método é responsável por conter a busca de todos os cadastros de doadores.
	 * @return {@code List<Giver>}
	 * 		- Caso tem cadastro ativo, ele retorna a lusta de Doadores.
	 * 		- Caso o cadastro esteja vazio, retorna que a lista está vazia com a exceção.
	 */
	public List<Giver> findaAll() {
		List<Giver> givers = giverRepository.findAll();
		
		if (givers.isEmpty()) {
			throw new NoContentException("Não existe cadastros de doadores no momento.");
		}
		
		return givers;
	}
	
	/**
	 * O método é responsável por conter a busca doador por ID.
	 * @param {@code Long} - giverId
	 * @return {@code Giver} 
	 * 		- Em caso de sucesso na busca, retorna um sucesso.
	 * 		- Caso o ID não esteja na base de dados retorna que o Registro
	 * 	 não foi encontrado.
	 */
	public Giver findOrFail(Long giverId) {
		return giverRepository.findById(giverId)
				.orElseThrow(() -> new GiverNotFoundException(giverId));
	}
	
	/**
	 * O método é responsável por conter a busca de um usuário por ID,
	 * verifica se o Usuário já é doador, se é também uma cooperativa e
	 * se tudo ocorrer certo, salva na base de dados.
	 * @param {@code Long} - id
	 * @return {@code Giver} - Retorna os dados salvos do usuário.
	 */
	@Transactional
	public Giver save(Long id) {

		userService.findById(id);
		verifyGiver(id);
		verifyCooperative(id);
		
		Giver giver = new Giver();
		User user = new User();
		user.setId(id);
		giver.setUser(user);
		
		return giverRepository.save(giver);
	}
	
	/**
	 * Método responsável por validar se um usuário de doador já está cadastrado
	 * na base de dados. Caso esteja cadastrado, lança a exceção
	 * que aquela entidade não pode ser processada. Uma regra de negócio.
	 * @param {@code Long} - id
	 */
	public void verifyGiver(Long id) {
		Optional<Giver> giverPresent = giverRepository.findByUserId(id);
		
		if (giverPresent.isPresent()) {
			throw new UnprocessableEntityException(String.format("Já existe um doador cadastrado com o código de usuário %s", id));
		}
	}
	
	/**
	 * Método responsável por validar se uma cooperativa está presente na base de dados. 
	 * Caso esteja cadastrado com aquele ID, é lançada uma exceção
	 * entidade não pode ser processada. Uma regra de negócio.
	 * @param {@code Long} - id
	 */
	public void verifyCooperative(Long id) {
		Optional<Cooperative> cooperativePresent = cooperativeRepository.findById(id);
		
		if (cooperativePresent.isPresent()) {
			throw new UnprocessableEntityException(String.format("já existe um usuário do código (%s)  associado com a Cooperativa", id));
		}
	}

	/**
	 * Método responsável por reaizar a deleção dos dados do doador na base de dados através do ID.
	 * @param {@code Long} id
	 */
	public void deleteById(Long id) {
		findOrFail(id);
		giverRepository.deleteById(id);
	}

//	@Transactional
//	public void excluir(Long giverId) {
//		try {
//			giverRepository.deleteById(giverId);
//			giverRepository.flush();
//
//		} catch (EmptyResultDataAccessException e) {
//			throw new GiverNotFoundException(giverId);
//
//		} catch (DataIntegrityViolationException e) {
//			throw new EntityInUseException(String.format(MSG_GIVER_EM_USO, giverId));
//		}
//	}
	
//	@SuppressWarnings({ "deprecation", "unchecked" })
//	public List<GiverResponseBean> consultByAll() {
//
//		return manager.createNativeQuery(
//				"select u.name, u.email, u.cpf_cnpj, u.cell_phone,  a.city, a.complement, a.neighborhood, a.number, a.state, a.street, a.zip_code from giver g inner join users u on g.id_giver=u.id_user inner join address a on u.id_user=a.id_user")
//				.unwrap(org.hibernate.query.Query.class)
//				.setResultTransformer(new org.hibernate.transform.AliasToBeanResultTransformer(GiverResponseBean.class))
//				.list();
//
//	}
}