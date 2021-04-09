package com.br.recycle.api.bean;

import static com.google.code.beanmatchers.BeanMatchers.hasValidGettersAndSetters;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanToString;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanConstructor;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanEquals;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanHashCode;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class GiverResponseBeanTest {
	
	@Test
	public void test() {
		assertThat(GiverResponseBean.class, allOf(hasValidGettersAndSetters(), hasValidBeanToString(),
				hasValidBeanConstructor(),hasValidBeanHashCode(), hasValidBeanEquals()));
		new GiverResponseBean();
	}

}
