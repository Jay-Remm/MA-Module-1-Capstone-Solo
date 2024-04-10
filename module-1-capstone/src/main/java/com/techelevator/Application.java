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

		// Hidden menu options in the main menu that shows a sales report since the machine started

	}
}


// Work on making a UI for the vending machine on the front end and then figure out how to link the java files with the front end
