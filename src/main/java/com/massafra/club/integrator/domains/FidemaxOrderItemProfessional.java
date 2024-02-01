package com.massafra.club.integrator.domains;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "FIDEMAX_ORDER_PROFESSIONAL")
public class FidemaxOrderItemProfessional implements Serializable {

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

    @Column(name = "CODIGO")
    private String codigoProduto;

    @Column(name = "QUANTIDADE")
    private BigDecimal quantidade;

    @Column(name = "PRECO_VENDA")
    private BigDecimal precoVenda;

    @Column(name = "PERCENTUAL_GRUPO")
    private BigDecimal percentualGrupo;

    @Column(name = "PERCENTUAL_MARCA")
    private BigDecimal percentualMarca;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(
            name = "EMPRESA",
            referencedColumnName = "EMPRESA",
            insertable = false,
            updatable = false
    )
    @JoinColumn(
            name = "DOCUMENTO",
            referencedColumnName = "DOCUMENTO",
            insertable = false,
            updatable = false
    )
    @JoinColumn(
            name = "SERIE",
            referencedColumnName = "SERIE",
            insertable = false,
            updatable = false
    )
    private FidemaxOrderProfessional order;
}
