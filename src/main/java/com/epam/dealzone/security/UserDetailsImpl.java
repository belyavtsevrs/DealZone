package com.epam.dealzone.security;

import com.epam.dealzone.domain.entity.Customer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class UserDetailsImpl implements UserDetails {
    private Customer customer;

    public UserDetailsImpl(Customer customer) {
        this.customer = customer;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return customer.getRole();
    }

    @Override
    public String getPassword() {
        return customer.getPassword();
    }

    @Override
    public String getUsername() {
        return customer.getEmail();
    }

    @Override
    public boolean isAccountNonLocked() {
        return customer.isActive();
    }

    public Customer getCustomer() {
        return customer;
    }
}
