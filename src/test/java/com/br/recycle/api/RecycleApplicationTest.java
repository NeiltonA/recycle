package com.br.recycle.api;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class RecycleApplicationTest {
	
	@Test
	public void contextLoads() {
		RecycleApplication.main(new String[] {"args"});
	}
}
