package com.app.ecom.dto;

import com.app.ecom.models.enums.UserRole;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserRequest {
    String firstName;
    String lastName;
    String email;
    String phone;
    AddressDTO address;
}
