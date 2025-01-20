package com.example.funcionario_manager.repositories;

import com.example.funcionario_manager.entities.Funcionario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {


    @Query("SELECT f FROM Funcionario f WHERE LOWER(f.nome) LIKE LOWER(CONCAT('%', :nome, '%'))")
    Page<Funcionario> findByNamePaginados(@Param("nome") String nome, Pageable pageable);


    @Query("SELECT f FROM Funcionario f WHERE LOWER(f.cargo) LIKE LOWER(CONCAT('%', :cargo, '%'))")
    Page<Funcionario> findByCargoPaginados(@Param("cargo") String cargo , Pageable pageable);


    @Query("SELECT f FROM Funcionario f JOIN f.endereco e WHERE LOWER(e.cidade) LIKE LOWER(CONCAT('%', :cidade, '%'))")
    Page<Funcionario> findByCidadePaginados(@Param("cidade") String cidade, Pageable pageable);


    Optional<Funcionario> findByCpf(String cpf);

    void deleteByCpf(String cpf);
}
