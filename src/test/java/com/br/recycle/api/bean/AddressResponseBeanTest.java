package com.br.recycle.api.bean;

import static com.google.code.beanmatchers.BeanMatchers.hasValidGettersAndSetters;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class AddressResponseBeanTest {
	
	@Test
	public void test() {
		assertThat(AddressResponseBean.class, allOf(hasValidGettersAndSetters()));
		new AddressResponseBean();
	}

}
