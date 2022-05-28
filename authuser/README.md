# Microservice Authuser

## @JsonView

A utilização da anotação @JsonView em determinadas propriedades de [UserDto](src/main/java/com/ead/authuser/dtos/UserDto.java) faz com que o recurso anotado com a anotação `@JsonView(Interface.class)` passe a gerenciar apenas propriedades com a mesma anotação. 
Podendo uma propriedade ser anotada com mais de uma interface.

Controller:
```java
public ResponseEntity<Object> updateUser(@PathVariable UUID userId,
@RequestBody @JsonView(UserDto.UserView.UserPut.class) UserDto userDto) {
```

Model:
```java
 @JsonView({UserView.RegistrationPost.class, UserView.UserPut.class})
    private String fullName;
```
Recurso desserializa apenas as propriedades especificas anotadas com `@JsonView(UserDto.UserView.UserPut.class)`:
![JsonView](../assets/jsonview.png)

## BeanValidation

Habilitando validação apenas para determinadas Views do Jackson inserindo o atributo "groups" na anotação da propriedade model:
`@NotBlank(groups = UserView.PasswordPut.class)`.

No controller inserir a anotação `@Validated` informando a View a ser utilizada para validação.
```java
    @PutMapping("/{userId}")
    public ResponseEntity<Object> updateUser(@PathVariable UUID userId,
    @RequestBody @Validated(UserDto.UserView.UserPut.class)
    @JsonView(UserDto.UserView.UserPut.class) UserDto userDto) { .. } 
```

#### Spring Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.6.7/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/2.6.7/maven-plugin/reference/html/#build-image)
* [Spring Web](https://docs.spring.io/spring-boot/docs/2.6.7/reference/htmlsingle/#boot-features-developing-web-applications)

#### Spring Guides
The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/bookmarks/)

