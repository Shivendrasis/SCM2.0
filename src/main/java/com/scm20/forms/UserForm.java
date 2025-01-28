package com.scm20.forms;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString

public class UserForm {

    @NotBlank(message = "Username is required")
    // @Size(min = 3,message = "Min 3 characters is required")
    private String name;

    @NotBlank(message = "Invalid Email Address")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 6, max = 12, message = "Min 8 character is required")
    private String password;

    @NotBlank(message = "About is required")
    private String about;

    @NotBlank(message = "Phone Number is required")
    private String phoneNumber;

}
