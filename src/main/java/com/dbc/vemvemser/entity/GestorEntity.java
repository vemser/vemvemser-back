package com.dbc.vemvemser.entity;

import com.dbc.vemvemser.enums.TipoMarcacao;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@Entity(name = "GESTOR")
public class GestorEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GESTOR")
    @SequenceGenerator(name = "SEQ_GESTOR", sequenceName = "SEQ_GESTOR", allocationSize = 1)
    @Column(name = "ID_GESTOR")
    private Integer idGestor;

    @Column(name = "nome")
    private String nome;

    @Email
    @Column(name = "email")
    private String email;

    @Column(name = "senha")
    private String senha;

    @Column(name = "ativo")
    @Enumerated(EnumType.STRING)
    private TipoMarcacao ativo;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cargo")
    private CargoEntity cargoEntity;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<CargoEntity> lista = new ArrayList();
        lista.add(cargoEntity);
        return lista;
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return getAtivo() == TipoMarcacao.T;
    }

}
