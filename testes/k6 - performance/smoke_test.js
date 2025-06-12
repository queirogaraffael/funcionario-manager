import http from 'k6/http';
import { check, sleep } from 'k6';

export let options = {
    vus: 1,
    duration: '5s',
};


export default function () {

    // Criação de endereço
    let endereco = {
        rua: 'Rua Exemplo',
        cidade: 'Cidade Exemplo',
        estado: 'Estado Exemplo'
    };


    // Cria funcionário com endereço
    let response = http.post('http://localhost:8080/funcionarios', JSON.stringify({
        cpf: '12345678911',
        nome: 'Nome',
        cargo: 'Cargo',
        enderecoRequestDTO: endereco
    }), { headers: { 'Content-Type': 'application/json' } });

    check(response, {
        'status is 201': (r) => r.status === 201,
    });

    sleep(1)


    // Consulta de funcionários paginados
    response = http.get('http://localhost:8080/funcionarios?page=0&size=10');

    check(response, {
        'status is 200': (r) => r.status === 200,
    });

    sleep(1)


    // Consulta por CPF
    response = http.get('http://localhost:8080/funcionarios/12345678911');

    check(response, {
        'status is 200': (r) => r.status === 200,
    });

    sleep(1)


    // Atualização de funcionário
    response = http.put('http://localhost:8080/funcionarios/12345678911', JSON.stringify({
        nome: 'Nome Atualizado',
        cargo: 'Cargo Atualizado',
        endereco: endereco
    }), { headers: { 'Content-Type': 'application/json' } });

    check(response, {
        'status is 200': (r) => r.status === 200,
    });

    sleep(1);


    // Consulta de funcionários por nome paginados
    let nomeParams = 'page=0&size=10&nome=Nome';

    // Use crases para interpolação de variáveis
    response = http.get(`http://localhost:8080/funcionarios/nome?${nomeParams}`);

    // Verifique a resposta
    check(response, {
        'status is 200': (r) => r.status === 200,
    });

    sleep(1);



    // Consulta de funcionários por cargo paginados
    let cargoParams = 'page=0&size=10&cargo=Cargo'

    response = http.get(`http://localhost:8080/funcionarios/cargo?${cargoParams}`)

    check(response, {
        'status is 200': (r) => r.status === 200,
    })

    sleep(1)


    // Consulta de funcionários por cidade paginados
    let cidadeParams = `page=0&size=10&cidade=${encodeURIComponent("Cidade Exemplo")}`;

    response = http.get(`http://localhost:8080/funcionarios/cidade?${cidadeParams}`)

    check(response, {
        'status is 200': (r) => r.status === 200,

    });

    sleep(1)


    // Deleção de funcionário
    response = http.del('http://localhost:8080/funcionarios/12345678911');

    check(response, {
        'status is 204': (r) => r.status === 204,
    });

    sleep(1)

}
