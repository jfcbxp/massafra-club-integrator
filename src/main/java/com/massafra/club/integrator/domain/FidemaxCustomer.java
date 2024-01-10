package com.massafra.club.integrator.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "FIDEMAX_CUSTOMER")
public class FidemaxCustomer implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "REC")
    private Integer id;

    @Column(name = "CODIGO")
    private String codigo;

    @Column(name = "LOJA")
    private String LOJA;

    @Column(name = "CGC")
    private String cgc;

    @Column(name = "NOME")
    private String nome;

    @Column(name = "ENDERECO")
    private String endereco;

    @Column(name = "DATA_NASCIMENTO")
    private LocalDate dataNascimento;

    @Column(name = "DDD")
    private String ddd;

    @Column(name = "TELEFONE")
    private String telefone;

    @Column(name = "DATA_INTEGRACAO")
    private LocalDate dataIntegracao;

    @Column(name = "HORA_INTEGRACAO")
    private String horaIntegracao;


}
