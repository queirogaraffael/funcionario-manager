package com.example.funcionario_manager.repositories;

import com.example.funcionario_manager.dtos.funcionario.FuncionarioResponseDTO;
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

    @Query("SELECT new com.example.funcionario_manager.dtos.funcionario.FuncionarioResponseDTO(f.id, f.cpf, f.nome, f.cargo) " +
            "FROM Funcionario f " +
            "WHERE LOWER(f.nome) LIKE LOWER(CONCAT('%', :nome, '%'))")
    Page<FuncionarioResponseDTO> findByNamePaginados(@Param("nome") String nome, Pageable pageable);


    @Query("SELECT new com.example.funcionario_manager.dtos.funcionario.FuncionarioResponseDTO(f.id, f.cpf, f.nome, f.cargo) " +
            "FROM Funcionario f " +
            "WHERE LOWER(f.cargo) LIKE LOWER(CONCAT('%', :cargo, '%'))")
    Page<FuncionarioResponseDTO> findByCargoPaginados(@Param("cargo") String cargo , Pageable pageable);

    @Query("SELECT new com.example.funcionario_manager.dtos.funcionario.FuncionarioResponseDTO(f.id, f.cpf, f.nome, f.cargo) " +
            "FROM Funcionario f JOIN f.endereco e WHERE LOWER(e.cidade) LIKE LOWER(CONCAT('%', :cidade, '%'))")
    Page<FuncionarioResponseDTO> findByCidadePaginados(@Param("cidade") String cidade, Pageable pageable);

    boolean existsByCpf(String cpf);

    void deleteByCpf(String cpf);

    @Query("SELECT new com.example.funcionario_manager.dtos.funcionario.FuncionarioResponseDTO(f.id, f.cpf, f.nome, f.cargo) " +
            "FROM Funcionario f WHERE f.cpf = :cpf")
    Optional<FuncionarioResponseDTO> findDTOByCpf(@Param("cpf") String cpf);

    Optional<Funcionario> findByCpf(String cpf);

    @Query("SELECT new com.example.funcionario_manager.dtos.funcionario.FuncionarioResponseDTO(f.id, f.cpf, f.nome, f.cargo) FROM Funcionario f")
    Page<FuncionarioResponseDTO> buscaTodosPaginados(Pageable pageable);

}
