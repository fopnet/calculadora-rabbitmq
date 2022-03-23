### Requisitos Funcionais:

- REST API que expõe as operações de soma, subtracção, multiplicação e divisão;
- Suporte para dois operandos apenas (a e b, por simplicidade). text/Code 
- Suporte para arbitraryprecision signed decimal numbers.
### Requisitos Funcionais:

### Requisitos Não Funcionais:
- Projecto Gradle ou Maven com pelo menos dois módulos — rest e calculator.
- Utilização de Spring Boot 2.2.6 como foundation de ambos os módulos.
- Utilização de RabbitMQ e Spring AMQP para comunicação intermódulo.
- Configuração via application.properties (default do Spring Boot).
- Nenhuma configuração XML (com excepção, eventualmente, da de logging).
- Versionamento do trabalho em Git.
- Java 11

### Swagger UI
- http://localhost:9000/swagger-ui.html

### Rabbit UI
- http://localhost:15672/

### Running Docker 
./scripts/docker-compose up

### Test usage
- ./scripts/sum.sh
- ./scripts/multiply.sh
- ./scripts/minus.sh
- ./scripts/sdivideum.sh

### Git repository

 - [GitHub Project Link](https://github.com/fopnet/calculadora-rabbitmq)

### Extra Design Patterns Applied
- Command Pattern
- Builder
- Some Units Test using Mockito including static methods

###End