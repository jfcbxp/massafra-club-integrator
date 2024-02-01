package com.massafra.club.integrator.domains;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "FIDEMAX_ORDER_PROFESSIONAL")
public class FidemaxOrderProfessional implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "REC")
    private Integer id;

    @Column(name = "EMPRESA")
    private String empresa;

    @Column(name = "DOCUMENTO")
    private String documento;

    @Column(name = "SERIE")
    private String serie;

    @Column(name = "EMISSAO")
    private LocalDate emissao;

    @Column(name = "CGC")
    private String cgc;

    @Column(name = "DDD")
    private String ddd;

    @Column(name = "TELEFONE")
    private String telefone;

    @Column(name = "CODIGO_PROFISSIONAL")
    private String codigoProfissional;

    @Column(name = "NOME_PROFISSIONAL")
    private String nomeProfissional;

    @Column(name = "CODIGO_CLIENTE")
    private String codigoCliente;

    @Column(name = "LOJA_CLIENTE")
    private String lojaCliente;

    @Column(name = "NOME_CLIENTE")
    private String nomeCliente;

    @Column(name = "CODIGO_TIPO")
    private String codigoTipoProfissional;

    @Column(name = "DESCRICAO_TIPO")
    private String descricaoTipoProfissional;

    @Column(name = "PERCENTUAL_TIPO")
    private BigDecimal percentualTipoProfissional;

    @OneToMany(
            fetch = FetchType.EAGER,
            mappedBy = "order",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<FidemaxOrderItemProfessional> produtos;
}
