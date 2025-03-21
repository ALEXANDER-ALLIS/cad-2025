/*
 * This source file was generated by the Gradle 'init' task
 */
package ru.bsuedu.cad.lab;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

@Component("app")
public class App {
    private static final Logger logger = LoggerFactory.getLogger(App.class);


    final private PersonaService personaService;

    public App( PersonaService personaService) {
        this.personaService = personaService;
    }

    public static void main(String[] args) {
        logger.info("Старт приложения");
        ApplicationContext ctx = new AnnotationConfigApplicationContext(Config.class);
        var app =  ctx.getBean("app", App.class);
        app.run();

    }

    public void run(){
       var persona =  personaService.getPersonaById(2L);
       logger.info(persona.toString());
    }
}
