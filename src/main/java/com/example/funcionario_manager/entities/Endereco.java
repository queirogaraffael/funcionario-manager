package com.example.funcionario_manager.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
public class Endereco implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    private String rua;
    private String cidade;
    private String estado;

    @OneToOne(mappedBy = "endereco", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Funcionario funcionario;
}
