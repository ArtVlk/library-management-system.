package ru.ArtemVolk.NauJava;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

@SpringBootApplication
@ComponentScan(basePackages = {"ru.ArtemVolk.NauJava", "crud.work", "business.logic"})
public class NauJavaApplication implements CommandLineRunner {
	private final ConsoleInputHandler consoleInputHandler;
	private final BookInitializer bookInitializer;

	@Autowired
	public NauJavaApplication(ConsoleInputHandler consoleInputHandler, BookInitializer bookInitializer) {
		this.consoleInputHandler = consoleInputHandler;
		this.bookInitializer = bookInitializer;
	}

	public static void main(String[] args) {
		SpringApplication.run(NauJavaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		bookInitializer.initializeBooks();
		consoleInputHandler.start();
	}
}
