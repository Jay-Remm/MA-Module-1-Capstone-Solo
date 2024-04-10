package com.techelevator;

public class Application {

	public static void main(String[] args) {

		// Vending Machine Class, create a new instance and stock the instance.
		VendingMachine vendOMatic = new VendingMachine("module-1-capstone/vendingmachine.csv");
		vendOMatic.stockVendingMachine();

		// Display menu to the user
		System.out.println("*** WELCOME TO VEND'O'MATIC ***");
		System.out.println();
		vendOMatic.mainMenu();

	}
}

// Work on making a UI for the vending machine on the front end and then figure out how to link the java files with the front end
