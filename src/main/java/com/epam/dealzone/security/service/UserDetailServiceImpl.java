package com.epam.dealzone.security.service;

import com.epam.dealzone.domain.entity.Customer;
import com.epam.dealzone.repository.CustomerRepository;
import com.epam.dealzone.security.UserDetailsImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
    private final CustomerRepository customerRepository;

    public UserDetailServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Customer customer = customerRepository.findCustomerByEmail(username).orElseThrow(()->{
            throw new UsernameNotFoundException("user with such email not found" + username);
        });
        return new UserDetailsImpl(customer);
    }
}
