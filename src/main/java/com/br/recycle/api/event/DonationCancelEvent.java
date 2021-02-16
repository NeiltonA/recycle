package com.br.recycle.api.event;



import com.br.recycle.api.model.Donation;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DonationCancelEvent {
	
	private Donation donation;

}
