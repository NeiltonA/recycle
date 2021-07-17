package com.br.recycle.api.email;

import static com.google.code.beanmatchers.BeanMatchers.hasValidGettersAndSetters;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class EmailPropertiesTest {

	@Test
	public void testEmail() {
		assertThat(EmailProperties.class, allOf(hasValidGettersAndSetters()));
		new EmailProperties();
	}
}
