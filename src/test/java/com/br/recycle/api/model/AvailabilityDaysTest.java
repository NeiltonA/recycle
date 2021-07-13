package com.br.recycle.api.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class AvailabilityDaysTest {
	
	@Test
	public void testGetDescription() {
		AvailabilityDays availabilityDays = AvailabilityDays.EVERY_DAY;
		assertEquals("Every day", availabilityDays.getDescription());
	}
}
