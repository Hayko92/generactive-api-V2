package com.example.generative_api_v2.model;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Entity
@Table(name = "authorities")
public class Authority  implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "authority")
    private String authority;
    @ManyToOne
    @JoinColumn(name = "username")
    private User user;

    @Override
    public String getAuthority() {
        return authority;
    }
}
