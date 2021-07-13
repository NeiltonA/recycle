package com.br.recycle.api.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class AvailabilityPeriodTest {

	@Test
	public void testGetDescriptionSuccess() {
		AvailabilityPeriod availabilityPeriod = AvailabilityPeriod.EVENING;
		assertEquals("Evening", availabilityPeriod.getDescription());
	}
}
