# Arquivo de Filmes e séries 💻 🎥

Arquivo de Entretenimento é uma aplicação Spring Boot projetada para gerenciar e organizar dados relacionados ao entretenimento. 
Este projeto fornece um serviço de backend robusto para gerenciar usuários, autenticação e várias entradas de entretenimento.
O foco desse projeto é o armazenamento de informações sobre filmes e séries e com isso ter um controle de quais ja assistiu.
Esse é apenas um mvp de funcionamento com o objetivo de evoluir e trazer novas funcionalidades, até mesmo para um banco que tenha um armazenamento e não apenas em memória. 

## Recursos
- Gerenciamento e autenticação de usuários
- Operações CRUD para entradas de entretenimento
- Provedor de autenticação customizado
- Inicialização do banco de dados com criação de usuários no início do sistema

## Pré-requisitos
- Java 11 ou superior
- Gradle

## Instalação
1. Clone o repositório:
  
  `` git clone https://github.com/theodirk21/EntertainmentArchive.git ``

2. Navegue até o diretório do projeto:
   
  `` cd EntertainmentArchive ``

3. Se quiser modificar as informações do seu banco de dados só entrar em 'src/main/resources/application.properties'

4. Compile o projeto:

`` ./gradlew build ``

5. Execute a aplicação:

`` ./gradlew bootRun ``

## Uso
Uma vez que a aplicação esteja em execução, você pode interagir com ela através dos endpoints da API expostos.
Os os usuarios e grupos só são possiveis ser criados com uma atentiação de ADM. Há demais endpois que também tem essa onfiguração, sugiro dar uma olhada.

## Contribuição
Contribuições são bem-vindas! Por favor, faça um fork do repositório e crie um pull request com suas alterações.
