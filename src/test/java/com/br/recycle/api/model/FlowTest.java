package com.br.recycle.api.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class FlowTest {

	@Test
	public void testGetDescription() {
		Flow flow = Flow.C;
		assertEquals("Cooperative", flow.getDescription());
	}
}
