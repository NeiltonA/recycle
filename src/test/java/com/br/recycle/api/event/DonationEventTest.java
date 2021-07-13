package com.br.recycle.api.event;

import static com.google.code.beanmatchers.BeanMatchers.hasValidGettersAndSetters;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class DonationEventTest {

	@Test
	public void testCancel() {
		assertThat(DonationCancelEvent.class, allOf(hasValidGettersAndSetters()));
		new DonationCancelEvent();
	}
	
	@Test
	public void testConfirmed() {
		assertThat(DonationConfirmedEvent.class, allOf(hasValidGettersAndSetters()));
		new DonationConfirmedEvent();
	}
	
	@Test
	public void testDelivered() {
		assertThat(DonationDeliveredEvent.class, allOf(hasValidGettersAndSetters()));
		new DonationDeliveredEvent();
	}
}
