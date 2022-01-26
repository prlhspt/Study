# JWT-Tutorial

인프런에 있는 [Spring Boot JWT Tutorial (정은구)](https://www.inflearn.com/course/%EC%8A%A4%ED%94%84%EB%A7%81%EB%B6%80%ED%8A%B8-jwt/dashboard) 강의를 보고 따라한 프로젝트입니다.

Refresh Token 로직을 추가하고 예외 처리 부분을 조금 수정하였습니다.

# Spring Initializr

- Gradle 
- Java 11 
- Spring Boot 2.6.3
- Jar 
- Dependency
  - Spring Web
  - Spring Security
  - Spring Data JPA
  - Lombok
  - H2 Database

# 개선 사항

- refresh Token 만료 시 삭제 필요
  - Redis에 저장하면서 만료 기간 설정 
  - 또는 별도 배치 작업을 통해 만료일 시 토큰 삭제