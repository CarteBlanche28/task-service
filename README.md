# Task Service

REST API сервис для управления задачами на Java/Spring Boot с использованием PostgreSQL и Kafka.

---

## Описание

Сервис предоставляет API для:

▪ создания задач  
▪ получения списка задач с пагинацией  
▪ получения задачи по id  
▪ назначения исполнителя задаче  
▪ изменения статуса задачи  

При создании задачи и назначении исполнителя отправляются события в Kafka.

---

## Технологии

▪ Java 21  
▪ Spring Boot 3  
▪ Hibernate  
▪ PostgreSQL  
▪ Apache Kafka  
▪ Docker  
▪ Maven  

---

## Архитектура

Проект состоит из следующих частей:

▪ Controller — обработка HTTP-запросов  
▪ Service — бизнес-логика  
▪ Repository — работа с БД  
▪ Model — модель данных  
▪ DTO — модели запросов/ответов  
▪ Kafka — producer, events, config  
▪ Exception Handling — централизованная обработка ошибок  

---

## Модель данных

### Task

| Поле         | Тип          |
|---------------|--------------|
| `id`          | `Long`       |
| `name`        | `String`     |
| `description` | `String`     |
| `status`      | `TaskStatus` |
| `executor`    | `User`       |

### User

| Поле    | Тип      |
|----------|-----------|
| `id`     | `Long`    |
| `name`   | `String`  |
| `email`  | `String`  |

---

## Запуск проекта

1. Клонировать репозиторий

```bash
git clone https://github.com/CarteBlanche28/task-service
cd task-service
```

2. Запустить PostgreSQL и Kafka

```bash
docker-compose up -d
```

3. Собрать проект

**Windows:**

```bash
mvnw.cmd clean package
```

**Linux / MacOS:**

```bash
./mvnw clean package
```

4. Запустить приложение

**Windows:**

```bash
mvnw.cmd spring-boot:run
```

**Linux / MacOS:**

```bash
./mvnw spring-boot:run
```

---

## API

### Создать задачу

```
POST /tasks
```

#### Request body

```json
{
  "name": "Test task",
  "description": "Test description"
}
```

#### Response

```json
{
  "id": 1,
  "name": "Test task",
  "description": "Test description",
  "status": "NEW",
  "executorId": null
}
```

---

### Получить список задач

```
GET /tasks?page=0&size=10
```

---

### Получить задачу по id

```
GET /tasks/{id}
```

---

### Назначить исполнителя

```
POST /tasks/{id}/assign
```

#### Request body

```json
{
  "userId": 1
}
```

---

### Изменить статус задачи

```
POST /tasks/{id}/status
```

#### Request body

```json
{
  "status": "IN_PROGRESS"
}
```

---

## Проверка Kafka

### Просмотр списка топиков

```bash
docker exec -it <kafka-container-name> bash

kafka-topics --bootstrap-server localhost:9092 --list
```

### Чтение сообщений из топика

```bash
kafka-console-consumer \
  --bootstrap-server localhost:9092 \
  --topic task-created \
  --from-beginning
```

---

## Тестирование API

Для тестирования приложения использовался Postman.

---

## Особенности реализации
  
- Использована пагинация для получения списка задач  
- Реализована интеграция с Kafka  
- PostgreSQL и Kafka запускаются через Docker Compose  
  
