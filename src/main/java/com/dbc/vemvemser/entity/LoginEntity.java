package com.dbc.vemvemser.entity;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "login" )
public class LoginEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="SEQ_LOGIN")
    @SequenceGenerator(name = "SEQ_LOGIN", sequenceName = "seq_login",allocationSize = 1)
    @Column(name = "id_login")
    private Integer idLogin;

    @Column(name = "email")
    private String email;

    @Column(name = "senha")
    private String senha;
}
