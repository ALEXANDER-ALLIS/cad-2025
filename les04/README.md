# Лекция 4. Введение в аспектно-ориентированное средствами Spring

Аспектно-ориентированное программирование (AOP, Aspect-Oriented Programming) — это парадигма программирования, которая позволяет отделять сквозную функциональность (cross-cutting concerns) от основной бизнес-логики.

+ Сквозная функциональность — это код, который затрагивает несколько модулей системы (например, логирование, транзакции, обработка ошибок).
+ Без AOP код повторяется в разных классах, что приводит к дублированию и затрудняет поддержку.
+ С AOP такие функции можно вынести в отдельные аспекты, что упрощает код и делает его более читаемым.

AOP в Spring применяется для:

+ Логирования (Logging)
+ Управления транзакциями (Transaction Management)
+ Обработки исключений (Exception Handling)
+ Кэширования (Caching)
+ Безопасности (Security)

## Основные понятия AOP

+ Аспект (aspect): Модульно организованная функциональность (concern), которая охватывает несколько классов. Управление транзакциями является хорошим примером сквозной функциональности в корпоративных Java-приложениях. В Spring AOP аспекты реализуются с помощью обычных классов (подход на основе схем) или обычных классов, аннотированных аннотацией @Aspect (стиль @AspectJ).

+ Точка соединения (joint point): Точка во время выполнения программы, например, выполнение метода или обработка исключения. В Spring AOP точка соединения всегда представляет собой выполнение метода.

+ Advice: Действие, предпринимаемое аспектом в определенной точке соединения. Различные виды Advice включают Advice "вместо (around)", "перед (before)" и "после (after)". (Типы Advice будут рассмотрены дальше). Многие АОП-фреймворки, включая Spring, моделируют Advice как перехватчик и поддерживают цепочку перехватчиков вместо точки соединения.

+ Срез (pointcut): Предикат, который соответствует точкам соединения. Advice связан с выражением среза и выполняется в любой точке соединения, совпадающей со срезом (например, выполнение метода с определенным именем). Концепция точек соединения, сопоставленных с выражениями среза, является центральной в АОП, а Spring по умолчанию использует язык выражений срезов AspectJ.

+ Введение (introduction): Объявление дополнительных методов или полей от имени типа. Spring AOP позволяет вам внедрять новые интерфейсы (и соответствующую реализацию) в любой снабженный Advice-ом объект. Например, можно использовать введение, чтобы в принудительном порядке бин реализовывал интерфейс IsModified для упрощения кэширования. (В сообществе AspectJ введение известно как межтиповое объявление).

+ Целевой объект (Target object): Объект, который снабжается Advice-ом по одному или нескольким аспектам. Также называется "снабжаемый Advice-ом объект". Поскольку Spring AOP реализован с использованием динамических прокси, этот объект всегда является проксированным объектом.

+ Прокси АОП: Объект, создаваемый АОП-фреймворком для реализации аспектных контрактов (снабжает Advice-ом выполнение методов и так далее). В Spring Framework прокси АОП – это динамический прокси JDK или прокси CGLIB.

+ Связывание (weaving): связывание аспектов с другими типами приложений или объектами для создания снабженного советом объекта. Оно может быть произведено во время компиляции (например, с помощью компилятора AspectJ), во время загрузки или во время выполнения программы. Spring AOP, как и другие чистые АОП-фреймворки на Java, осуществляет связывание во время выполнения программы.

📌 Виды Advice (советов) в AOP

Вид совета Описание
@Before Выполняется перед вызовом метода
@After Выполняется после вызова метода
@AfterReturning Выполняется, если метод завершился успешно
@AfterThrowing Выполняется, если метод выбросил исключение
@Around Полный контроль: выполняется до и после метода

📌 Пример AOP в Spring

1️⃣ Подключаем AOP в pom.xml (Spring Boot)

Если используете Spring Boot, добавьте зависимость:

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-aop</artifactId>
</dependency>

2️⃣ Создаём сервис (MyService)

Это обычный Spring-бин, в который мы добавим аспект.

import org.springframework.stereotype.Service;

@Service
public class MyService {

    public void doWork() {
        System.out.println("📌 Выполнение метода doWork()");
    }
}

3️⃣ Создаём аспект (логирование)

Аспект будет логировать вызовы методов перед их выполнением.

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    @Before("execution(* com.example.service.MyService.*(..))") // Перехватываем все методы MyService
    public void logBeforeMethod() {
        System.out.println("🔍 Логирование: метод вызывается...");
    }
}

4️⃣ Включаем AOP в @Configuration

Если используете Spring Boot, это не обязательно (AOP включается автоматически).

Для обычного Spring-приложения добавьте:

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy  // Включаем AOP
public class AppConfig {
}

5️⃣ Тестируем

Запускаем Spring-контекст и вызываем doWork().

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AppRunner implements CommandLineRunner {

    private final MyService myService;

    public AppRunner(MyService myService) {
        this.myService = myService;
    }

    @Override
    public void run(String... args) {
        myService.doWork();
    }
}

⏩ Вывод в консоли:

🔍 Логирование: метод вызывается...
📌 Выполнение метода doWork()

✅ Совет @Before сработал перед вызовом метода doWork().

📌 Примеры других типов Advice

@AfterReturning (если метод успешно завершился)

@AfterReturning("execution(*com.example.service.MyService.*(..))")
public void logAfterReturning() {
    System.out.println("✅ Метод завершился успешно!");
}

⏩ Вывод:

🔍 Логирование: метод вызывается...
📌 Выполнение метода doWork()
✅ Метод завершился успешно!

@AfterThrowing (если метод выбросил исключение)

@AfterThrowing("execution(*com.example.service.MyService.*(..))")
public void logAfterException() {
    System.out.println("❌ Метод завершился с ошибкой!");
}

@Around (полный контроль до и после метода)

@Around("execution(*com.example.service.MyService.*(..))")
public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
    System.out.println("🔁 Начало работы метода...");
    Object result = joinPoint.proceed();  // Вызов метода
    System.out.println("🔁 Метод завершился.");
    return result;
}

⏩ Вывод:

🔁 Начало работы метода...
📌 Выполнение метода doWork()
🔁 Метод завершился.

✅ Этот способ даёт полный контроль над выполнением метода.

📌 Итог

✅ Spring AOP позволяет отделить сквозную логику от бизнес-кода
✅ Основные аннотации: @Aspect, @Before, @After, @Around, @AfterReturning, @AfterThrowing
✅ Используется для логирования, транзакций, обработки исключений и безопасности

🔹 Есть вопросы? Пиши — разберём подробнее! 🚀
