package com.example.blogging_application_api.payload;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDTO {
    int id;
    String name;
    String email;
    String password;
    String about;
}
