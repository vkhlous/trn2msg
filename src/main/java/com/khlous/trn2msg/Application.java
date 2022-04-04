package com.khlous.trn2msg;

import com.khlous.trn2msg.services.TransactionFileReader;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;

@SpringBootApplication
public class Application{

	private final TransactionFileReader transactionFileReader;

	public Application(TransactionFileReader transactionFileReader) {
		this.transactionFileReader = transactionFileReader;
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
