# Тестовое задание (Операции со счетами пользователей)
### Описание REST API 
* Положить деньги на счёт
```sh
http://localhost:8080/change-balance POST
{
	"accountId" : 1,
	"amount": "199"
}
```
* Снять деньги со счёта
```sh
http://localhost:8080/change-balance POST
{
	"accountId" : 1,
	"amount": "-199"
}
```
* перевод денег с одного счёта на другой
```sh
http://localhost:8080/transfer POST
{
	"fromAccountId": 1,
	"toAccountId" : 2,
	"amount": "999"
}
```

### Используемые технологии
* [Spring Boot](https://projects.spring.io/spring-boot/)
* [Spring Data JPA](http://projects.spring.io/spring-data-jpa/)
* [H2 Database](http://www.h2database.com/html/main.html)
* [Maven](https://maven.apache.org/)
* [Project Lombok](https://projectlombok.org/)
* [JUnit](http://junit.org/junit4/)
* Для сборки в исполняемый jar использовался
```sh
    <plugin>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-maven-plugin</artifactId>
    </plugin>
```