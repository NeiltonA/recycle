package com.br.recycle.api.mock;

import java.util.List;

import com.br.recycle.api.model.Giver;

/**
 * Classe de mock para atender os cenários de Doador entrada
 * para evitar repetição de código
 *
 * @author Caio Henrique do Carmo Bastos
 * @since 05/07/2021
 */
public class GiverMock {

	public static Giver getMockToModel() {
		Giver giver = new Giver();
		giver.setId(1L);
		giver.setUser(UserMock.getMockUserDto());
		
		return giver;
	}
	
	public static List<Giver> getMockToCollectionModel() {
		Giver giver = new Giver();
		giver.setId(1L);
		giver.setUser(UserMock.getMockUserDto());
		
		return List.of(giver);
	}
}
