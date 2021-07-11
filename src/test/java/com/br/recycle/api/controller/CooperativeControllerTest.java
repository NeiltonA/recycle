package com.br.recycle.api.controller;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.br.recycle.api.assembler.CooperativeDtoAssembler;
import com.br.recycle.api.model.Cooperative;
import com.br.recycle.api.payload.ApiResponse;
import com.br.recycle.api.payload.CooperativeDtoOut;
import com.br.recycle.api.payload.CooperativeInput;
import com.br.recycle.api.payload.UserIdInput;
import com.br.recycle.api.repository.CooperativeRepository;
import com.br.recycle.api.service.CooperativeService;

@ExtendWith(MockitoExtension.class)
public class CooperativeControllerTest {

	private static final String NOT_NULL = "not null";

	@Mock
	private CooperativeRepository repository;

	@Mock
	private CooperativeDtoAssembler assemble;

	@Mock
	private CooperativeService service;

	@InjectMocks
	private CooperativeController controller = new CooperativeController();

	@Test
	public void cooperativeListAll() {
		List<Cooperative> coo = new ArrayList<Cooperative>();

		when(assemble.toCollectionModel(coo)).thenReturn(new ArrayList<CooperativeDtoOut>());

		when(service.findAll(null)).thenReturn(new ArrayList<>());

		List<CooperativeDtoOut> coop = controller.getAll(null);
		assertNotNull(coop, NOT_NULL);

	}

	@Test
	public void cooperativeListById() {
		Cooperative cooperative = new Cooperative();
		cooperative.setCnpj("0987898789809");

		List<Cooperative> coo = new ArrayList<Cooperative>();
		coo.add(cooperative);

		when(assemble.toModel(cooperative)).thenReturn(new CooperativeDtoOut());
		when(service.findOrFail(1L)).thenReturn(cooperative);

		CooperativeDtoOut coop = controller.getById(1L);
		assertNotNull(coop, NOT_NULL);

	}

	@Test
	public void cooperativeSave() {

		UserIdInput user = new UserIdInput();
		user.setId(1L);
		CooperativeInput input = new CooperativeInput();
		input.setCnpj("0987898789809");
		input.setCompanyName("teste");
		input.setUser(user);

		Cooperative cooperative = new Cooperative();
		cooperative.setCnpj("0987898789809");

		List<Cooperative> coo = new ArrayList<Cooperative>();
		coo.add(cooperative);

		when(assemble.toDomainObject(input)).thenReturn(cooperative);
		when(service.save(cooperative)).thenReturn(cooperative);

		ResponseEntity<ApiResponse> save = controller.save(input);
		assertNotNull(save, NOT_NULL);

	}

	@Test
	public void cooperativeUpdate() {

		UserIdInput user = new UserIdInput();
		user.setId(1L);
		CooperativeInput input = new CooperativeInput();
		input.setCnpj("0987898789809");
		input.setCompanyName("teste");
		input.setUser(user);

		Cooperative cooperative = new Cooperative();
		cooperative.setCnpj("0987898789809");

		List<Cooperative> coo = new ArrayList<Cooperative>();
		coo.add(cooperative);

		when(repository.findById(1L)).thenReturn(Optional.ofNullable(cooperative));
		when(assemble.toDomainObject(input)).thenReturn(cooperative);
		ResponseEntity<ApiResponse> save = controller.update(1L, input);
		assertNotNull(save, NOT_NULL);

	}

	@Test
	public void cooperativeDelite() {

		Cooperative cooperative = new Cooperative();
		cooperative.setCnpj("0987898789809");

		when(repository.findById(1L)).thenReturn(Optional.ofNullable(cooperative));
		ResponseEntity<HttpStatus> save = controller.delete(1L);
		assertNotNull(save, NOT_NULL);

	}

}
