package com.example.funcionario_manager.resources;

import com.example.funcionario_manager.entities.Funcionario;
import com.example.funcionario_manager.services.FuncionarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/funcionarios")
public class FuncionarioResource {

    @Autowired
    private FuncionarioService funcionarioService;


    @Operation(summary = "Cria novo funcionario", description = "O endereço é criado junto com o novo funcionario. O CPF deve ter 11 caracteres.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Funcionário criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "409", description = "Conflito: Funcionário já existe"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @PostMapping
    public ResponseEntity<?> criaFuncionario(@Valid @RequestBody Funcionario funcionario, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors());
        }
        Funcionario funcionarioCriado = funcionarioService.criaFuncionario(funcionario);
        return ResponseEntity.status(HttpStatus.CREATED).body(funcionarioCriado);
    }


    @Operation(summary = "Busca paginada dos funcionarios", description = "Retorna todos os dados de Funcionario e de Endereço.")
    @ApiResponse(responseCode = "200", description = "Retorna funcionarios de uma pagina")
    @GetMapping()
    public Page<Funcionario> funcionarioPaginados(@RequestParam(defaultValue = "0") int page,
                                                  @RequestParam(defaultValue = "10") int size) {
        return funcionarioService.getFuncionariosPaginados(page, size);

    }


    @Operation(summary = "Retorna Funcionario pelo CPF.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Funcionário encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Funcionário não encontrado com o CPF fornecido"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @GetMapping("/{cpf}")
    public ResponseEntity<Funcionario> getFuncionarioByCpf(@PathVariable String cpf) {
        Funcionario funcionario = funcionarioService.getFuncionarioByCpf(cpf);

        if (funcionario == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.ok(funcionario);
    }


    @PutMapping("/{cpf}")
    @Operation(summary = "Atualiza dados do funcionario.", description = "Nome, cargo e endereço.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Funcionário atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Funcionário não encontrado com o CPF fornecido"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<Funcionario> atualizaFuncionario(@PathVariable String cpf, @RequestBody Funcionario funcionarioAtualizado) {
        Funcionario funcionario = funcionarioService.atualizaFuncionarioByCpf(cpf, funcionarioAtualizado);

        return ResponseEntity.status(HttpStatus.OK).body(funcionario);
    }


    @GetMapping("/nome")
    @Operation(summary = "Busca funcionários por nome de forma paginada.", description = "Retorna uma página de funcionários cujo nome contém o valor fornecido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Funcionários encontrados com sucesso"),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos fornecidos"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public Page<Funcionario> buscaFuncionariosPorNome(
            @RequestParam String nome,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return funcionarioService.buscaFuncionariosPorNome(nome, page, size);
    }


    @GetMapping("/cargo")
    @Operation(summary = "Busca funcionários por cargo de forma paginada.", description = "Retorna uma página de funcionários cujo cargo contém o valor fornecido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Funcionários encontrados com sucesso"),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos fornecidos"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public Page<Funcionario> buscaFuncionariosPorCargo(
            @RequestParam String cargo,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return funcionarioService.buscaFuncionariosPorCargo(cargo, page, size);
    }

    @GetMapping("/cidade")
    @Operation(summary = "Busca funcionários por cidade de forma paginada.", description = "Retorna uma página de funcionários cujo cidade contém o valor fornecido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Funcionários encontrados com sucesso"),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos fornecidos"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public Page<Funcionario> buscaFuncionariosPorCidade(
            @RequestParam String cidade,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return funcionarioService.buscaFuncionariosPorCidade(cidade, page, size);
    }


    @DeleteMapping("/{cpf}")
    @Operation(summary = "Deleta um funcionário pelo CPF.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Funcionário deletado com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<Void> deletaFuncionarioByCpf(@PathVariable String cpf) {
        funcionarioService.deletaFuncionarioByCpf(cpf);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


}
