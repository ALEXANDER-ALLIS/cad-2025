# Отчет по лабораторной работе

В начале проекта выполнена инициализация с помощью команды `gradle init`.  
Для корректного отображения русского языка в консоли выполнена настройка через `chcp 65001`.  
Сборка и обновление зависимостей произведены командами `gradle clean build` и `gradle build --refresh-dependencies`.  
Время сборки составляет около 2 секунд.

## Цель работы

Цель лабораторной работы — изучить конфигурацию Spring-приложений с использованием аннотаций, включая внедрение зависимостей через `@Component`, параметров через `@Value` и SpEL, а также разработать новые компоненты и измерить производительность с помощью аспектно-ориентированного программирования (AOP). Кроме того, в рамках работы реализуется HTML-представление данных и задействуются события жизненного цикла бинов для дополнительной инициализации.

## Выполнение работы

- Настроен проект на основе результатов лабораторной работы №2, добавлены зависимости для работы с базой H2 и Spring JDBC в файле `build.gradle.kts`.
- С помощью `EmbeddedDatabaseBuilder` сконфигурирована встраиваемая база данных H2, которая при старте приложения автоматически запускает SQL-скрипты создания таблиц.
- Написан SQL-скрипт для создания таблиц `PRODUCTS` и `CATEGORIES` с необходимыми внешними ключами.
- Автоматизирован запуск скрипта создания таблиц при старте приложения через `EmbeddedDatabaseBuilder`.
- Созданы классы модели `Category`, `Product` и класс `ConcreteCategoryProvider` для загрузки данных из CSV.
- Реализован класс `DataBaseRenderer` для сохранения данных из CSV в базу данных.
- Создан класс `CategoryRequest`, выполняющий выборку категорий с количеством товаров больше одного, и выводящий результаты через логирование Logback.
- Проверено успешное выполнение приложения командой `gradle run` с корректным выводом в консоль и завершением работы.

Настройки аналогичны первой работе:  
- Инициализация через `gradle init`  
- Консоль на `chcp 65001`  
- Сборка `gradle clean build` и обновление зависимостей `gradle build --refresh-dependencies`  
- Время выполнения — 2 секунды

Новая диаграмма В проекте под названием UML diagramm2![alt text](<UML diagramm2.png>).png

## Контрольные вопросы

1. **Что такое Spring JDBC и его преимущества?**  
   Spring JDBC — модуль Spring, упрощающий работу с базой данных, автоматизируя рутинные операции: управление соединениями, обработку ошибок, маппинг результатов в объекты. Позволяет сосредоточиться на бизнес-логике.

2. **Какой основной класс используется для работы с базой через JDBC в Spring?**  
   `JdbcTemplate` — удобный класс-обертка для выполнения SQL-запросов и обработки результатов.

3. **Какие шаги необходимы для настройки JDBC в Spring?**  
   Подключить зависимости, настроить `DataSource`, создать `JdbcTemplate` и обработку исключений через `DataAccessException`.

4. **Что такое JdbcTemplate и его основные методы?**  
   Основной класс для работы с SQL: методы `query()`, `update()`, `execute()`, `queryForObject()`, `queryForList()`.

5. **Как выполнить SELECT-запрос и получить объект?**  
   Использовать метод `query()` с `RowMapper` для преобразования результата в объект Java.

6. **Что такое RowMapper?**  
   Интерфейс, преобразующий каждую строку результата SQL в Java-объект.

7. **Как выполнить вставку (INSERT) через JdbcTemplate?**  
   Метод `update()` с SQL-запросом и параметрами.

8. **Как обновить (UPDATE) или удалить (DELETE) записи?**  
   Тоже метод `update()` с нужным SQL-запросом.

9. **Как обрабатывать исключения в Spring JDBC?**  
   Через `DataAccessException`, который оборачивает ошибки работы с БД.

10. **Какие альтернативы JdbcTemplate есть в Spring?**  
    Spring Data JPA, MyBatis, Spring Data MongoDB — более высокоуровневые или специализированные инструменты.

## Выводы

В ходе лабораторной работы освоена интеграция приложения с базой данных через Spring JDBC, выполнены SQL-запросы с выводом результатов в логи. Реализованы модели, загружены данные из CSV, применено Spring AOP для логирования действий. Все это обеспечило эффективное взаимодействие с базой и удобное отображение информации.
