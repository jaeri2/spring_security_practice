package com.example.securitypracitce.domain.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Authority {
    @Id @Column(name = "authority_name", length = 50)
    @Enumerated(EnumType.STRING)
    private RoleType authorityName;
}
