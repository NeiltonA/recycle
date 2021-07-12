package com.br.recycle.api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.recycle.api.bean.CnpjResponseBean;
import com.br.recycle.api.exception.CooperativeNotFoundException;
import com.br.recycle.api.exception.InternalServerException;
import com.br.recycle.api.exception.NoContentException;
import com.br.recycle.api.exception.UnprocessableEntityException;
import com.br.recycle.api.feign.ViaCnpjClient;
import com.br.recycle.api.model.Cooperative;
import com.br.recycle.api.model.User;
import com.br.recycle.api.repository.CooperativeRepository;

/**
 * Classe responsável por realizar os serviços das transações de comunicação com
 * a base de dados.
 */
@Service
public class CooperativeService {

	// private static final String COOPERATIVE_IN_USE_MESSAGE = "CooperativA de
	// código %d não pode ser removida, pois está em uso.";

	private CooperativeRepository cooperativeRepository;
	private ViaCnpjClient viaCnpjClient;

	@Autowired
	public CooperativeService(CooperativeRepository cooperativeRepository, ViaCnpjClient viaCnpjClient) {
		this.cooperativeRepository = cooperativeRepository;
		this.viaCnpjClient = viaCnpjClient;
	}

	/**
	 * Método responsável por buscar os dados da cooperativa. Pode buscar todos ou
	 * através do filtro por ID do usuário.
	 * 
	 * @param {@code Long} idUser
	 * @return {@code List<Cooperative>} - Retorna os dados da cooperativa.
	 */
	public List<Cooperative> findAll(Long idUser) {

		List<Cooperative> response = new ArrayList<>();

		if (Objects.nonNull(idUser)) {
			response = cooperativeRepository.findByUserId(idUser);
			return validateEmpty(response);
		} else {
			response = cooperativeRepository.findAll();
			return validateEmpty(response);
		}
	}

	/**
	 * Método responsável por buscar o serviço de comunicação da busca da empresa
	 * pelo CNPJ.
	 * 
	 * @param {@code String} cnpj
	 * @return {@code CnpjResponseBean} - Caso exista uma empresa de acordo com o
	 *         CNPJ informado, é retornado os dados de sucesso da empresa. - Caso
	 *         ocorra algum erro, é retornado que o CNPJ não está relacionado a
	 *         nenhuma empresa.
	 */
	public CnpjResponseBean searchCnpj(String cnpj) {
		try {
			CnpjResponseBean cnpjResponseBean = viaCnpjClient.searchCnpj(cnpj);
			if (Objects.isNull(cnpjResponseBean.getNome())) {
				throw new UnprocessableEntityException(
						"De acordo com o CNPJ informado não está relacionado a nenhuma empresa");
			}
			return cnpjResponseBean;
		} catch (Exception e) {
			throw new UnprocessableEntityException(
					"De acordo com o CNPJ informado não está relacionado a nenhuma empresa");
		}
	}

	/**
	 * Método responsável por realizar o cadastro da cooperativa, realizando
	 * validações, para saber se pode ser feita.
	 * 
	 * @param {@code Cooperative} - cooperative
	 * @return
	 */
	@Transactional
	public void save(Cooperative cooperative) {

		verifyCooperative(cooperative.getCnpj());

		try {
			cooperativeRepository.save(cooperative);
		} catch (Exception e) {
			throw new InternalServerException("Ocorreu um erro ao salvar os dados da cooperativa.");
		}
	}

	/**
	 * Método responsável por realizar o serviço de atualização dos dados da
	 * cooperativa. E caso ocorra algum erro, é lançado a exceção de erro interno.
	 * 
	 * @param {@code Long} - id
	 * @param {@code Cooperative} - cooperative
	 */
	@Transactional
	public void update(Long id, Cooperative cooperative) {

		Cooperative cooperativeActual = findOrFail(id);
		validateUpdateCnpj(cooperativeActual.getCnpj(), cooperative.getCnpj());
		cooperative.setId(cooperativeActual.getId());

		try {
			cooperativeRepository.save(cooperative);
		} catch (Exception e) {
			throw new InternalServerException("Ocorreu um erro ao atualizar os dados da cooperativa.");
		}

	}

	/**
	 * Método responsável por realizar o serviço de atualização dos dados da
	 * cooperativa parcialmente. E casso ocorra algum erro ao salvar, é lançado a
	 * exceção.
	 * 
	 * @param {@code Cooperative} - cooperative
	 * @param {@code Long} id
	 */
	@Transactional
	public void updatePatch(final Cooperative cooperative, Long id) {

		Cooperative cooperativeActual = findOrFail(id);
		validateUpdateCnpj(cooperativeActual.getCnpj(), cooperative.getCnpj());

		cooperative.setId(cooperativeActual.getId());
		cooperative.setUser(getUser(cooperativeActual));
		cooperative.setCnpj(cooperativeActual.getCnpj());

		if (Objects.isNull(cooperative.getCompanyName())) {
			cooperative.setCompanyName(cooperativeActual.getCompanyName());
		}

		if (Objects.isNull(cooperative.getFantasyName())) {
			cooperative.setFantasyName(cooperativeActual.getFantasyName());
		}

		try {
			cooperativeRepository.save(cooperative);
		} catch (Exception e) {
			throw new InternalServerException("Ocorreu um erro ao atualizar os dados da cooperativa.");
		}
	}

	/**
	 * Método responsável por deletar os dados da cooperativa da base de dados pelo
	 * o seu ID.
	 * 
	 * @param {@code Long} id
	 */
	@Transactional
	public void delete(Long id) {
		findOrFail(id);

		cooperativeRepository.deleteById(id);
	}

	/**
	 * Método responsável por buscar os dados pelo ID da cooperativa.
	 * 
	 * @param {@code Long} - id
	 * @return {@code Cooperative} - Caso exista um cadastro de acordo com o ID, é
	 *         retornado os dados da cooperative. - Caso não exista, é lançado a
	 *         exceção que a cooperativa não existe.
	 */
	public Cooperative findOrFail(Long id) {
		return cooperativeRepository.findById(id).orElseThrow(() -> new CooperativeNotFoundException(id));
	}

	/**
	 * Método responsável por validar se o retorno da base de dados da cooperativa,
	 * existe cadastro.
	 * 
	 * @param {@code List<Cooperative>} - cooperatives
	 * @return {@code List<Cooperative>} - Caso exista retorno, é retornado os dados
	 *         da cooperativa. - Caso a lista esteja vazia, é lançado a exceção que
	 *         não existe conteúdo.
	 */
	private List<Cooperative> validateEmpty(List<Cooperative> cooperatives) {
		if (cooperatives.isEmpty()) {
			throw new NoContentException("No momento não existe cooperativas cadastradas.");
		}

		return cooperatives;
	}

	/**
	 * Método responsável por validar se no cadastro da cooperativa, ela já existe,
	 * porque não é possível cadastrar a mesma cooperativa.
	 * 
	 * @param {@code String} - cnpj
	 */
	private void verifyCooperative(String cnpj) {
		List<Cooperative> cooperative = cooperativeRepository.findByCnpj(cnpj);

		if (!cooperative.isEmpty()) {
			throw new UnprocessableEntityException(
					String.format("Já existe uma cooperativa cadastrada com o CNPJ '%s'", cnpj));
		}
	}

	/**
	 * Método responsável por validar se o usuário está tentando alterar o CNPJ.
	 * Caso sejam diferentes é lançado a exceção que não permite.
	 * 
	 * @param {@code String} - cnpjActual
	 * @param {@code String} - newCnpj
	 */
	private void validateUpdateCnpj(String cnpjActual, String newCnpj) {
		if (Objects.nonNull(newCnpj)) {
			if (!cnpjActual.equalsIgnoreCase(newCnpj)) {
				throw new UnprocessableEntityException("Não é possível alterar o CNPJ da empresa.");
			}
		}
	}

	private User getUser(Cooperative cooperativeActual) {
		User user = new User();
		user.setId(cooperativeActual.getUser().getId());

		return user;
	}

//	@Transactional
//	public void remove(Long cooperativeId) {
//		try {
//			cooperativeRepository.deleteById(cooperativeId);
//			cooperativeRepository.flush();
//
//		} catch (EmptyResultDataAccessException e) {
//			throw new CooperativeNotFoundException(cooperativeId);
//
//		} catch (DataIntegrityViolationException e) {
//			throw new EntityInUseException(String.format(COOPERATIVE_IN_USE_MESSAGE, cooperativeId));
//		}
//	}
}