package com.br.recycle.api.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public class RegexCharactersUtilsTest {
	
	@Test
	public void testRemoveSpecialCharactersSuccess() {
		String zipcode = RegexCharactersUtils.removeSpecialCharacters("06.766-200");
		assertEquals("06766200", zipcode);
	}
	
	@Test
	public void testRemoveSpecialCharactersSuccessNull() {
		String zipcode = RegexCharactersUtils.removeSpecialCharacters(null);
		assertNull(zipcode);
	}
}
