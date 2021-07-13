package com.br.recycle.api.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class StorageTest {

	@Test
	public void testGetDescriptionSuccess() {
		Storage storage = Storage.BARREL;
		assertEquals("Barrel", storage.getDescription());
	}
}
