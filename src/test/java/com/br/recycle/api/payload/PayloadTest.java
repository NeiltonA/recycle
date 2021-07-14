package com.br.recycle.api.payload;

import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanConstructor;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanToString;
import static com.google.code.beanmatchers.BeanMatchers.hasValidGettersAndSetters;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class PayloadTest {
	
	@Test
	public void test() {
		DonationDtoOut dons = new DonationDtoOut();
		DonationDtoOut don = new DonationDtoOut();
		GiverDtoOut gv = new GiverDtoOut();
		gv.setId(1L);
		CooperativeDtoOut cp = new CooperativeDtoOut();
		cp.setId(2L);
		don.getStatus().name();
		don.setCode("");
		don.setGiver(gv);
		don.setCooperative(cp);
		
		 assertNotEquals(don,dons);
		 

		ApiResponse response = new ApiResponse(true, "");
		response.setMessage("test");
		response.setSuccess(true);
		response.getMessage();
		
		UserProfile pf = new UserProfile(1L, "", "");
		pf.setId(1L);
		pf.setName("");
		pf.setUsername("");
	
		assertThat(AddressDto.class, allOf(hasValidGettersAndSetters(),hasValidBeanToString()));
		assertThat(AddressDtoOut.class, allOf(hasValidGettersAndSetters(),hasValidBeanToString()));
		assertThat(AddressInput.class, allOf(hasValidGettersAndSetters(),hasValidBeanToString()));
		assertThat(CooperativeDtoOut.class, allOf(hasValidGettersAndSetters(),hasValidBeanToString()));
		assertThat(CooperativeIdInput.class, allOf(hasValidGettersAndSetters()));
		assertThat(CooperativeInput.class, allOf(hasValidGettersAndSetters()));
		//assertThat(don.getCode(),  is(equalTo("")));
		assertThat(DonationInput.class, allOf(hasValidGettersAndSetters(),hasValidBeanToString()));
		assertThat(GiverDtoOut.class, allOf(hasValidGettersAndSetters(),hasValidBeanToString()));
		assertThat(GiverIdInput.class, allOf(hasValidGettersAndSetters()));
		//assertThat(JwtAuthenticationResponse.class, allOf(hasValidGettersAndSetters(),
				//hasValidBeanConstructor(),hasValidBeanHashCode()));
		assertThat(LoginRequest.class, allOf(hasValidGettersAndSetters()));
		assertThat(PagedResponse.class, allOf(hasValidGettersAndSetters(),hasValidBeanConstructor()));
		
		assertThat(response.getSuccess(), is(equalTo(true)));
		assertThat(PasswordInput.class, allOf(hasValidGettersAndSetters()));
		assertThat(RateDtoOut.class, allOf(hasValidGettersAndSetters(),hasValidBeanToString()));
		
		assertThat(RateInput.class, allOf(hasValidGettersAndSetters(),hasValidBeanToString()));
		assertThat(RoleDto.class, allOf(hasValidGettersAndSetters(),hasValidBeanToString()));
		assertThat(SignUpRequest.class, allOf(hasValidGettersAndSetters()));
		assertThat(UserDtoIn.class, allOf(hasValidGettersAndSetters(),hasValidBeanToString()));
		//assertThat(UserDtoOut.class, allOf(hasValidGettersAndSetters(),hasValidBeanToString()));
		assertThat(UserIdInput.class, allOf(hasValidGettersAndSetters()));
		assertThat(UserInput.class, allOf(hasValidGettersAndSetters(),hasValidBeanToString()));
		
		assertThat(pf.getId(), is(equalTo(1L)));
		assertThat(pf.getName(), is(equalTo("")));
		assertThat(pf.getUsername(), is(equalTo("")));
		
		

	}

}
