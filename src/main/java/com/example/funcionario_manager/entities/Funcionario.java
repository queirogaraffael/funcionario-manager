package com.example.funcionario_manager.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
public class Funcionario implements Serializable {

    @Id
    @EqualsAndHashCode.Include
    @Size(min = 11, max = 11, message = "CPF deve ter 11 caracteres")
    private String cpf;
    private String nome;
    private String cargo;

    @NotNull
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "endereco_id")
    private Endereco endereco;
}
