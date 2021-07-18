package com.br.recycle.api.payload;

import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanToString;
import static com.google.code.beanmatchers.BeanMatchers.hasValidGettersAndSetters;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class LogOutRequestTest {

	@Test
	public void test() {
		assertThat(LogOutRequest.class, allOf(hasValidGettersAndSetters(), hasValidBeanToString()));
		new LogOutRequest();
	}

}
