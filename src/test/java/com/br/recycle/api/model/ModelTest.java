package com.br.recycle.api.model;



import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanConstructor;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanEquals;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanHashCode;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanToString;
import static com.google.code.beanmatchers.BeanMatchers.hasValidGettersAndSetters;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ModelTest {
	
	@Test
	public void test() {
		Donation don = new Donation();
		don.confirm();
		don.deliver();
		
		Donation dons = new Donation();
		dons.cancel();
		
		don.setCode("8768767788");
	
		assertThat(Address.class, allOf(hasValidGettersAndSetters(), hasValidBeanToString(),
				hasValidBeanConstructor(),hasValidBeanHashCode(), hasValidBeanEquals()));
		assertThat(Cooperative.class, allOf(hasValidGettersAndSetters(), hasValidBeanToString(),
				hasValidBeanConstructor(),hasValidBeanHashCode(), hasValidBeanEquals()));
		assertThat(Giver.class, allOf(hasValidGettersAndSetters(), hasValidBeanToString()));
		assertThat(Rate.class, allOf(hasValidGettersAndSetters(), hasValidBeanToString()));
		assertThat(Role.class, allOf(hasValidGettersAndSetters(), hasValidBeanToString()));

	}

}
