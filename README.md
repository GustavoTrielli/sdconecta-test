# SD Conecta Teste Backend

![](https://site.sdconecta.com/wp-content/uploads/2022/05/Logo-SD-Conecta-02-e1653394044218.png)

## Instruções para rodar a aplicação
**1. clone o repositório**
`$ git clone https://github.com/GustavoTrielli/sdconecta-test.git`

**2. Na IDE de sua preferência executar o arquivo BackendTestApplication.java**
A aplicação possui base url http://localhost:8080

## Informações da aplicação
### Usuários pré cadastrados
O projeto inicia com 6 usuários cadastrados, com os seguintes emails:
- usuario.teste-1@email.com
- usuario.teste-2@email.com
- usuario.teste-3@email.com
- usuario.teste-4@email.com
- usuario.teste-5@email.com
- usuario.teste-6@email.com

Todos estes usuários possuem a senha "123456". Os 3 primeiros possuem Role "ADMIN" e podem alterar a base de dados, os outros três só podem consultar os dados.

### Rotas da aplicação
#### /login
Esta rota tem a finalidade de fazer a autenticação do usuário. Para isso é necessário informar o **email** e o **password** do usuário no **body** da request, como no exemplo abaixo:
```json
{
    "email": "usuario.teste-1@email.com",
    "password": "123456"
}
```
Resposta esperada:
```json
{
	"access_token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c3VhcmlvLnRlc3RlLTFAZW1haWwuY29tIiwiaWF0IjoxNjYzMjcwMjYxLCJfaGFzaCI6IjY0MzZiMDY3LWNhMTMtNDI4OC1hMDc3LTkxMzA5Y2RjZGQ3YiIsImV4cCI6MTY2MzM1NjY2MSwiY2siOiJ0ZXN0ZS1iYWNrZW5kIn0.Wjsa02so6L0mCHzyA0ZOOX6bMag8D06OGEp-50oaKhdGvPoYjep18Sx_tK1e9PuVrItC-LCw-_Guo3qiboP7ZCWg",
	"refresh_token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c3VhcmlvLnRlc3RlLTFAZW1haWwuY29tIiwiaWF0IjoxNjYzMjcwMjYxLCJfaGFzaCI6IjY0MzZiMDY3LWNhMTMtND7POC1hMDc3LTkxMzA5Y2RjZGQ3YiIsImV4cCI6MTY5NDgwNjI2MSwiY2siOiJ0ZXN0ZS1iYWNrZW5kIn0.4e95R8PD8muLgIni_0k1Uf0eSHfs8ulcUd2xOwysHMeMRKlCUzORjWfsXiCdx4eGxEZZYkgF8wHc94-TaQyJ3g",
	"authorization_status": "AUTHORIZED"
}
```
#### /users
O endpoint /users realiza todas as operações com os usuários, dependendo do método da requisição.
##### /users POST
Com o método post é possivel cadastrar um novo usuário. Os dados do usuário devem ser informados no body da requisição, como no exemplo abaixo:
```json
{
    "email": "fulano@email.com",
    "name": "Fulano",
    "surname": "da Silva",
    "password": "4424222",
    "mobilePhone": "11987545592",
    "crms": [
        {
            "crm": "1234",
            "uf": "SP",
            "specialty": "ORTOPEDIA"
        },
        {
            "crm": "4321",
            "uf": "MG",
            "specialty": "RADIOLOGIA"
        }
    ]
}
```
##### /users GET
Este endpoint retorna uma listagem de usuários. Os usuários podem ser filtrados por nome, através do parâmetro **name**, e/ou por especialidade, através do parâmetro **specialty**.
Exemplo de requisição:
`http://localhost:8080/users?name=Fulano&specialty=ortopedia

##### /users/{id} GET
Substituindo "id" pelo id de um usuário, o endpoint retorna as informações deste usuário.

##### /users/{id} PATCH
Substituindo "id" pelo id de um usuário, o endpoint pode alterar informações do usuário informado. Por exemplo, se quiser alterar o Nome e os Crms de do usuário com Id 1, no body da requisição deve ser enviado as alterações da seguinte forma:
```json
{
	"name": "Cicrano",
    "crms": [
        {
            "crm": "6498",
            "uf": "SP",
            "specialty": "CARDIOLOGIA"
        }
    ]
}
```
##### /users/{id} DELETE
Substituindo "id" pelo id de um usuário, o endpoint apaga o determinado usuário da base de dados.

### Banco de dados
#### /h2
Pelo seu navegador, através da rota http://localhost:8080/h2 você pode acessar o console da base de dados, onde é possivel realizar consultas SQL e vizualizar o **authorization_status** dos usuários com a SD Conecta.