package com.br.recycle.api.exception;

public class CooperativeNotFoundException extends EntityNotFoundException{

	private static final long serialVersionUID = 1L;

	public CooperativeNotFoundException(String message) {
		super(message);
	}
	
	public CooperativeNotFoundException(Long kitchenId) {
		this(String.format("Não existe um cadastro de cooperativa com código %d", kitchenId));
	}
}
