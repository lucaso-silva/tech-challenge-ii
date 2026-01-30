# Tech Challenge II

## Introdução

Este projeto consiste no desenvolvimento de uma API backend para gestão de usuários de uma plataforma compartilhada entre restaurantes, permitindo o cadastro e manutenção de diferentes tipos de usuários, como donos de restaurante e clientes.

O sistema foi desenvolvido como parte do Tech Challenge – Fase 2 do programa de Pós-Graduação em Arquitetura e Desenvolvimento Java (FIAP), com foco na aplicação prática dos conceitos estudados ao longo do curso.

### Objetivo do projeto

## Arquitetura do Sistema

### Descrição da Arquitetura

### Diagrama da Arquitetura

#### Diagrama de classes (domain)

```mermaid
classDiagram
direction TB
    class Address {
     -Long id
     -String street
     -String number
     -String city
     -String zipCode
    }

    class User {
        <<abstract>>
     -Long id
     -String name
     -String email
     -String login
     -String password
     -LocalDate lastModifiedDate
     -Address address
    }

    class Client {
     -List~Order~ orders
    }

    class Owner {
     -List~Restaurant~ restaurants
    }

    class Restaurant {
        -String name
        -Address address
        -KitchenTypeEnum kitchenType
        -String openingHours
        -Menu menu
    }

    class Menu{
        -List~MenuItem~ itens
    }

    class MenuItem {
    -Long id
    -String name
    -String description
    -BigDecimal price
    -Boolean onlyLocalConsumption
    -String photoPath
}

    User <|-- Client
    User <|-- Owner
    User "1" --> "1" Address
    Owner "1" --> "1..*" Restaurant
    Restaurant "1" --> "1" Address
    Restaurant --> Menu
    Menu "1" --> "1..*" MenuItem
```

#### Diagrama de entidades

```mermaid
erDiagram

    USER {
        BIGINT id PK
        VARCHAR name
        VARCHAR email
        VARCHAR login
        VARCHAR password
        VARCHAR user_type
        DATE last_modified_date
        BIGINT address_id FK
    }

    ADDRESS {
        BIGINT id PK
        VARCHAR street
        VARCHAR number
        VARCHAR city
        VARCHAR zip_code
    }

    RESTAURANT {
        BIGINT id PK
        VARCHAR name
        VARCHAR kitchen_type
        VARCHAR opening_hours
        BIGINT address_id FK
        BIGINT owner_id FK
        BIGINT menu_id FK
    }

    MENU {
        BIGINT id PK
    }

    MENU_ITEM {
        BIGINT id PK
        VARCHAR name
        VARCHAR description
        DECIMAL price
        BOOLEAN only_local_consumption
        VARCHAR photo_path
        BIGINT menu_id FK
    }

    ORDER {
        BIGINT id PK
        
    }

    ORDER_ITEMS {
        BIGINT id PK
        BIGINT client_id FK
        BIGINT order_id FK
        BIGINT menu_item_id FK
    }

    USER ||--|| ADDRESS : has
    USER ||--o{ RESTAURANT : owns
    RESTAURANT ||--|| ADDRESS : located_at
    RESTAURANT ||--|| MENU : has
    MENU ||--o{ MENU_ITEM : contains
    USER ||--|| ORDER : do
    ORDER ||--o{ ORDER_ITEMS : contains
```

## API – Endpoints

### Tabela de Endpoints

### Exemplos de Requisição e Resposta

## Configuração e Execução

### Docker Compose

## Testes

## Repositório do Código

[https://github.com/HugoOliveiraSoares/tech-challenge-ii](https://github.com/HugoOliveiraSoares/tech-challenge-ii)

## Integrantes

- [Hugo Soares](https://github.com/HugoOliveiraSoares)
- [Lucas Oliveira](https://github.com/lucaso-silva)
- [Matheus Sousa](https://github.com/msousa-s)
