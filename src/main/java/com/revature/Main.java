package com.revature;

import java.util.Scanner;

import org.apache.log4j.Logger;

import com.revature.exception.DepositException;
import com.revature.exception.WithdrawException;
import com.revature.model.Users;

import com.revature.repository.UsersDAOImplOJDBC;
import com.revature.util.ConnectionUtil;


public class Main {
	private static Logger log;

	public static void main(String[] args) throws WithdrawException, DepositException {
		log=Logger.getLogger(Main.class);
		
		ConnectionUtil.getConnection();
		//System.out.println((new UsersDAOImplOJDBC()).getUsers());
		Scanner sc = new Scanner(System.in);
		System.out.println("Welcome to my Banking App");
		System.out.println("");
		System.out.println("Enter username: ");
		String username = sc.nextLine();
		System.out.println("Enter password: ");
		String password=sc.nextLine();
		
		int option = 0;
		float amount=0;
		if (UsersDAOImplOJDBC.checkLogIn(username,password) == true) {
			System.out.println("Username and password combination accepted");
			System.out.println("");
			log.info("User logged in");

		}
		while (UsersDAOImplOJDBC.checkLogIn(username, password)==false)
		{
			System.out.println("Incorrect username/password combination");
			System.out.println("Enter username: ");
			 username = sc.nextLine();
			System.out.println("Enter password: ");
			 password=sc.nextLine();
			
		} 
		
		if (UsersDAOImplOJDBC.checkLogIn(username, password)) {
			System.out.println("Welcome, "+username);
			System.out.println("");
			do {	
				UsersDAOImplOJDBC.options();
				option = sc.nextInt();
				if (option == 1) {
					float balance = UsersDAOImplOJDBC.viewBalance(username, password);
					System.out.println("Your current balance is : $" + balance);
					System.out.println("");
				} else if (option == 2) {
					System.out.println("How much would you like to deposit?");
					amount=sc.nextFloat();
					UsersDAOImplOJDBC.deposit(username, password, amount);
					log.info("User made a deposit");
					
				} else if (option == 3) {
					System.out.println("How much would you like to withdraw?");
					amount=sc.nextFloat();
					UsersDAOImplOJDBC.withdraw(username, password, amount);
					log.info("User made a withdrawal");

				} else if (option == 4) {
					System.out.println("end program");
					option =-1;
				} else {
					System.out.println("Invalid option entered. Please enter a valid option.");
					
				}
			} while (option != -1);
		} 
		


	}

}
