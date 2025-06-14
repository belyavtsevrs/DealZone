package com.epam.dealzone.web.dto;

import com.epam.dealzone.domain.entity.Customer;
import com.epam.dealzone.domain.entity.Image;
import com.epam.dealzone.domain.enums.Role;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@ToString
public class CustomerRequest {
    @Email
    @NotBlank
    private String email;
    @Size(min = 8, message = "Password must be at least 8 characters")
    private String password;
    @NotBlank(message = "can not be empty")
    private String name;
    @NotBlank(message = "can not be empty")
    private String surname;
    @NotBlank(message = "can not be empty")
    private String city;
    @Pattern(regexp = "\\+?[0-9]{10,15}", message = "Invalid phone number")
    private String phoneNumber;
    private MultipartFile newAvatar;
}
