package com.br.recycle.api.service;



import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.br.recycle.api.model.Cooperative;
import com.br.recycle.api.model.User;
import com.br.recycle.api.repository.CooperativeRepository;

@ExtendWith(MockitoExtension.class)
public class CooperativeServiceTest {
	
	
	private static final String NOT_NULL = "Not Null";

	@Mock
    private CooperativeRepository repository;
	
	@InjectMocks
    private CooperativeService service;

	@Test
	public void cooperativeSave() {
		Cooperative cooperative = new Cooperative();
		cooperative.setCnpj("0987898789809");
		cooperative.setCompanyName("teste");
		cooperative.setCpfResponsible("0987898789");
		cooperative.setResponsibleName("Teste");
		User user = new User();
		user.setId(1L);
		cooperative.setUser(user);
		when(repository.save(cooperative)).thenReturn(cooperative);
		assertNotNull(service.save(cooperative), NOT_NULL);
	}
	
	@Test
	void cooperativeFindOrFail() {
		Cooperative cooperative = new Cooperative();
		cooperative.setCnpj("0987898789809");
		cooperative.setCompanyName("teste");
		cooperative.setCpfResponsible("0987898789");
		cooperative.setResponsibleName("Teste");
		cooperative.setId(1L);
		given(repository.findById(1L)).willReturn(Optional.of(cooperative));
		Cooperative coop = service.findOrFail(1L);
		
		assertNotNull(coop, NOT_NULL);

	}
	
	@Test
	void cooperativeRemove() {
		Cooperative cooperative = new Cooperative();
		cooperative.setId(1L);
		service.remove(1L);
		
		assertNotNull(cooperative, NOT_NULL);

	}

	
}
