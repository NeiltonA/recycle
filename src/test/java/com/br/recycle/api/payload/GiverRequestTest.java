package com.br.recycle.api.payload;

import static com.google.code.beanmatchers.BeanMatchers.hasValidGettersAndSetters;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class GiverRequestTest {

	@Test
	public void test() {
		assertThat(GiverRequest.class, allOf(hasValidGettersAndSetters()));
		new GiverRequest();
	}

}