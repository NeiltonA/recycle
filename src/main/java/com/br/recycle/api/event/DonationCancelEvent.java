package com.br.recycle.api.event;

import com.br.recycle.api.model.Donation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DonationCancelEvent {

    private Donation donation;

}
