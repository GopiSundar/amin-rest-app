package com.amin.realty.web.rest.vm;

import com.amin.realty.service.dto.UserDTO;
import javax.validation.constraints.Size;

import java.time.Instant;
import java.util.Set;

/**
 * View Model extending the UserDTO, which is meant to be used in the user management UI.
 */
public class ManagedUserVM extends UserDTO {

    public static final int PASSWORD_MIN_LENGTH = 4;

    public static final int PASSWORD_MAX_LENGTH = 100;

    @Size(min = PASSWORD_MIN_LENGTH, max = PASSWORD_MAX_LENGTH)
    private String password;

    public ManagedUserVM() {
        // Empty constructor needed for Jackson.
    }

	public ManagedUserVM(Long id, String login, String password, String firstName, String lastName, String title,
			String company, String addressLine1, String addressLine2, String city, String state, String zip,
			String country, String mobileNumber, String email, boolean activated, String imageUrl, String langKey,
			String createdBy, Instant createdDate, String lastModifiedBy, Instant lastModifiedDate,
			Set<String> authorities) {

		super(id, login, firstName, lastName, title, company, addressLine1, addressLine2, city, state, zip, country,
				mobileNumber, email, activated, imageUrl, langKey, createdBy, createdDate, lastModifiedBy,
				lastModifiedDate, authorities);

        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "ManagedUserVM{" +
            "} " + super.toString();
    }
}
