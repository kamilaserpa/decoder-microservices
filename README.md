# Projeto Decoder - Microservices

Projetos em arquitetura de Microservices utilizando o framework Spring, construído em acompanhamento ao curso Projeto Decoder.
[Cronograma](/assets/cronograma.png)

Tecnologias:
 - Spring Framework
 - Java
 - Maven
 - PostgreSQL

## Módulo 1 - Planejamento Arquitetura de Microservices: Componentes e Comunicações

### 1. Concepção da Arquitetura de Microservices

Requisitos: Sistema adaptável a expansão, independência maior entre equipes.
<b>Opções</b>: iniciar com um sistema monolítico e depois migr-alo para arquitetura microservices, ou iniciar em uma arquitetura microservice.

<b>Considerações</b>: Ao iniciar com microserviços se torna mais fácil expandir o sistema, possui cenários menores possibilitando maior controle, conforme vão surgindo novas frentes de negócio podem ser criados novos serviços de forma independente se comunicando entre si. Ao partir de um monolito para expandir posteriormente em microserviços, em um determinado momento seria necessário trabalhar na migração de um monolito para microservice ao invés de criar novas features, o que demandaria muito mais tempo e investimento.

##### Granilarity of Microservices
É possível definir diversos microservices abordando pequenos níveis de negócio.

### 2. Definição dos Componentes e Comunicações
-
### 3. Base de Dados Compartilhada e Base de Dados por Microservice

##### Shared Database Pattern

 <b>Prós</b>: Forte consistência; ACID Transactions locally, atomicidade (transação ocorre por completo ou não ocorre), forte consistência, isolameto de transações, durabilidade (mesmo ocorrendo alguma falha os dados são persistidos ao final da transação).

 <b>Contras</b>: Forte acoplamento, performance prejudicada (concorrência, lentidão), ausência de modelos de dados isolado por serviço, maior dependência entre as equipes.

##### Database by Service Pattern
  
 <b>Prós</b>: Baixo acoplamento, modelo de dados isolado, maior flexibilidade na escolha do banco de dados por serviço, flexibilidade de modelagem por serviço, menor dependência entre equipes.

 <b>Contras</b>: lidar com sincronização de dados, lidar com transações distribuídas, (CAP Theorem and Eventual Consistency) Teorema da Consistência versus Disponibilidade, lidar com replicação de dados.

 Algumas maneiras possíveis de sincronizar os dados entre microservices e retornar ao consumidor da API seria o padrão API Composition, Mensageria, Eventos.

- CAP Theorem
![CAP Theorem](/assets/cap-theorem.png)

Define que precisamos ter três garantias em um sistema: Consistência, Disponibilidade e Particionamneto com Tratativas a Falhas.

Em sistemas distribuídos o teorema afirma que conseguimos garantir apenas duas. Em microservices deve-se garantir a partição tolerante à falhas, a outra garantia deve ser selecionada dependendo do negócio.

Alta disponibilidade prejudica a consistência, havendo o que chamamos de "Consistência Eventual". A disponibilidade dos dados é garantida, porém os dados podem estar divergentes em um dado específico de tempo, em um momento futuro esse dado se tornará consistente.

Observação: a replicação de dados deve ser parcial.

- Transações Distribuídas

Sequência de transações local em cada um dos microservices, na qual o MS notifica outro MS pra que este segundo faça sua transação em seguida.

- Compensação de dados

Quando ocorre uma falha ou algo inesperado, é necessário que este MS informe os demais MSs para alterar o estado atual daquele recurso. Exemplo: MS de pagamento verifica necessidade de bloqueio de cliente, notifica MS de bloqueio que bloqueia o usuário. 
Padrão SAGA.

### 4. Communication between Microservice

|   | One-To-One  | One-To_many  |
|---|---|---|
| Synchronous  | Request/response  | - |
| Asynchronous | Asynchronous Request/response. One-way notifications | Publish/subscribe. Publish/ async  |

- Comunicação síncrona - cliente envia requisição para servilo determinado e fica bloqueado esperando a resposta, possuindo uma disponibilidade menor do que de forma assíncrona.

- Comunicação assíncrona: o cliente aguarda o retorno de forma não imediara e não fica bloqueado aguardando a resposta, possuindo disponibilidade maior e menor acoplamento.

- One-to-one: um serviço envia solicitação para outro serviço específico.

- One-to-many: serviço envia uma solicitação para um ou vários serviços, aguardando retorno de alguns desses serviços ou não.

### 5. Assincronia por Mensageria ou Eventos
Uma mensagem é um peiddo genéricom com carga útil para comunicação entre MS.

Comunicação entre Producer e Consumer: o producer produz uma mensagem entregue a determinado consumidor, preparado para recebê-la.

Comunicação entre Publish adn Subscribe: o publish produz mensagem e envia para o Broker em uso, o produtor desconhece o microservices que recebem essa mensagem, ou o processamento que eles realizam.

Diferença entre Command and Event: a intenção d euma mensagme pode ser um comando, quando um MS informa a outro "faça algo", conhece o MS que receberá e aguarda retorno,já um evento ocorre quando um MS informa outros que alguma coisa aconteceu e não se interessa no retorno.

Possibilitam baixo acoplamento, maior disponibilidade, comunicação assíncrona, não bloqueante (dependendo da regra de negócio).
 
## Módulo 2 - Ecossistema Spring para Arquitetura de Microservices

### 1. Spring Framework e Spring Boot
Spring é composto por vários projetos, podendo conter subprojetos,por exemplo: Spring Boot, Spring Frameworks, Spring Data, Spring Cloud,Spring Security, Spring Batch, Spring AMQP, Spring Hateoas, etc.

<b>Spring Framework</b> é a base para o framework com um todo, possui o projeto Core Container, que é responsável pelos beans e ciclo de vida do Spring Framework.
Inversão de controle é um padrão de projeto em que um objeto declara a sua dependẽncia sem precisar instanciá-la, delega a um container essa responsabilidade. O Core container fica responsável por criar essas instâncias, através da injeção de dependência.

Um Bean consiste em um objeto que é instanciado, montado e gerenciado por um container do Spring através da Inversão de Controle (IoC) e injeção de dependências.
 - `@Component`, `@Controller`, `@Service`, `@Repository`, são exemplos de anotação que indicam ao Spring os beans que ele deve instanciar.
 - `@Autowired` indica um ponto de injeção de dependência.
 - `@Configuration` indicam ao Spring beans de classes de bibliotecas externas, construídas e gerenciadas dentro da aplicação. Como exmeplificado abaixo:

```java
@Configuration
public class RestTemplateClient {
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
```

[<b>Spring Boot</b>](https://spring.io/projects/spring-boot) é utilizado para fornecer pré configurações para iniciar uma aplicação, fornecendo um servlet container embutido (Tomcat ou Net). Net no caso do Spring Web Flux.

## Módulo 3 - Criando os primeiros Microservices com Spring
[Jhipster](https://www.jhipster.tech/) e [Spring Initializr](https://start.spring.io/) podem ser utilizados para geração do projeto inicial.

Microservice:
 - <b>AuthUser</b>, serviço para controle de usuários e autenticação.
 - <b>Course</b>
 - <b>Notification</b>

Para executar a aplicação por linha de comando execute: `mvn spring-boot:run`.


#### Developer
[Kamila Serpa](https://kamilaserpa.github.io)

[1]: https://www.linkedin.com/in/kamila-serpa/
[2]: https://gitlab.com/java-kamila
[3]: https://github.com/kamilaserpa

[![linkedin](https://img.shields.io/badge/LinkedIn-0077B5?style=for-the-badge&logo=linkedin&logoColor=white)][1]
[![gitlab](https://img.shields.io/badge/GitLab-330F63?style=for-the-badge&logo=gitlab&logoColor=white)][2]
[![github](https://img.shields.io/badge/GitHub-100000?style=for-the-badge&logo=github&logoColor=white)][3]