package com.procrastinator.library.libraryapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.Arrays;

/**
 * Task: Create an API in TransactionController to get all the TRX for a student in last 30 days.
 * and return it as an CSV file
 */

@SpringBootApplication
public class LibraryAppApplication implements CommandLineRunner {
	//40:43

	@Autowired
	ApplicationContext appContext;


	public static void main(String[] args) {
		SpringApplication.run(LibraryAppApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		String[] beans = appContext.getBeanDefinitionNames();
		Arrays.sort(beans);
		for (String bean : beans) {
			System.out.println(bean);
		}

	}
}
