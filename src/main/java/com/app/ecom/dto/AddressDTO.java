package com.app.ecom.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AddressDTO {
    String street;
    String city;
    String state;
    String country;
    String zipcode;
}
