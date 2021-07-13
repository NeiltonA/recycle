package com.br.recycle.api.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class DonationStatusTest {
	
	@Test
	public void testGetDescription() {
		DonationStatus donationStatus = DonationStatus.CONFIRMED;
		assertEquals("Confirmed", donationStatus.getDescription());
	}
	
	@Test
	public void testCantChangeToSuccess() {
		DonationStatus donationStatus = DonationStatus.CREATED;
		assertEquals(true, donationStatus.cantChangeTo(donationStatus));
	}
}
