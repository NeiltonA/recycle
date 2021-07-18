package com.br.recycle.api.utils;

import java.util.Objects;

public class RegexCharactersUtils {

	public static String removeSpecialCharacters(String value) {
		if (Objects.nonNull(value)) {
			value = value.replaceAll("[^a-zA-Z0-9]", "");
			return value;			
		}
		return null;
	}
}
