package com.techelevator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.*;

public class VendingMachine {

    Scanner userInput = new Scanner(System.in);
    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss aa");

    // Instance variables are slot location, product name, price, and type
    private String slotLocation;
    private String productName;
    private double price;
    private String typeOfProduct;
    private File inventoryFile;
    private File logFile = new File("module-1-capstone/src/main/resources/Log.txt");
    Map<String, Product> machineInventory = new HashMap<>();
    private double moneyInMachine = 0.00;

    public Map<String, Product> getMachineInventory() {
        return machineInventory;
    }

    // Constructor of Vending Machine needs to take the input file for inventory to start
    public VendingMachine(String inventoryFile) {
        this.inventoryFile = new File(inventoryFile);
    }

    // Stock vending machine
    public void stockVendingMachine() {
        int STOCKED_QUANTITY = 5;
        try (Scanner inputFile = new Scanner(inventoryFile)) {
            while (inputFile.hasNextLine()) {
                String inventoryLine = inputFile.nextLine();
                // split inventoryLine by pipe char
                String[] splitLine = inventoryLine.split("\\|");
                for (int i = 0; i <= splitLine.length - 1; i++) {
                    if (i == 0) {
                        this.slotLocation = splitLine[i];
                    } else if (i == 1) {
                        this.productName = splitLine[i];
                    } else if (i == 2) {
                        this.price = Double.parseDouble(splitLine[i]);
                    } else if (i == 3) {
                        this.typeOfProduct = splitLine[i];
                    }
                }

                // if statement inside the while loop to make our instance of a product and set values. and then map it to the Slot.
                if (typeOfProduct.equalsIgnoreCase("Chip")) {
                    Chip product = new Chip(productName, price, STOCKED_QUANTITY);
                    machineInventory.put(slotLocation, product);
                } else if (typeOfProduct.equalsIgnoreCase("Candy")) {
                    Candy product = new Candy(productName, price, STOCKED_QUANTITY);
                    machineInventory.put(slotLocation, product);
                } else if (typeOfProduct.equalsIgnoreCase("Drink")) {
                    Drink product = new Drink(productName, price, STOCKED_QUANTITY);
                    machineInventory.put(slotLocation, product);
                } else if (typeOfProduct.equalsIgnoreCase("Gum")) {
                    Gum product = new Gum(productName, price, STOCKED_QUANTITY);
                    machineInventory.put(slotLocation, product);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Inventory input file not found" + inventoryFile.getAbsolutePath());
        }
    }

    // Main Menu for the Machine
    public void mainMenu() {
        System.out.println("(1) Display Vending Machine Items");
        System.out.println("(2) Purchase");
        System.out.println("(3) Exit");
        System.out.println();
        System.out.print("ENTER OPTION: ");

        String userChoice = userInput.nextLine();
        if (userChoice.equals("1")) {
            System.out.println();
            displayInventory();
            System.out.println();
            mainMenu();
        } else if (userChoice.equals("2")) {
            System.out.println();
            purchaseMenu();
        } else if (userChoice.equals("3")) {
            System.out.println();
            System.out.println("*** THANK YOU FOR USING VEND'O'MATIC ***");
            System.out.println("****** GOOD BYE ******");
        } else if (userChoice.equals("4")) {
            System.out.println();
            salesReport();
        } else {
            System.out.println("User input not accepted.");
            System.out.println("Please choose from menu options below (input must be numeric):");
            mainMenu();
        }
    }

    // Display the vending machine Items
    public void displayInventory() {
        List<String> slots = new ArrayList<>(machineInventory.keySet());
        Collections.sort(slots);
        for (String slot : slots) {
            if (machineInventory.get(slot).getQuantityRemaining() < 1) {
                System.out.println(slot + " - " + machineInventory.get(slot).getName() + ", $" + machineInventory.get(slot).getPrice() + ", SOLD OUT.");
            } else {
                System.out.println(slot + " - " + machineInventory.get(slot).getName() + ", $" + machineInventory.get(slot).getPrice() + ", Quantity: " + machineInventory.get(slot).getQuantityRemaining());
            }
        }
    }

    // Purchase Menu for the Machine
    public void purchaseMenu() {
        System.out.println();
        System.out.println("Current Money Provided: $" + moneyInMachine);
        System.out.println();
        System.out.println("(1) Feed Money");
        System.out.println("(2) Select Product");
        System.out.println("(3) Finish Transaction");
        System.out.println();
        System.out.print("ENTER OPTION: ");

        String userChoice = userInput.nextLine();
        if (userChoice.equals("1")) {
            feedMoney();
        } else if (userChoice.equals("2")) {
            List<String> slots = new ArrayList<>(machineInventory.keySet());
            Collections.sort(slots);
            for (String slot : slots) {
                if (machineInventory.get(slot).getQuantityRemaining() < 1) {
                    continue;
                } else {
                    System.out.println(slot + " - " + machineInventory.get(slot).getName() + ", $" + machineInventory.get(slot).getPrice() + ", Quantity: " + machineInventory.get(slot).getQuantityRemaining());
                }
            }
            purchaseItem();
        } else if (userChoice.equals("3")) {
            System.out.println("ARE YOU SURE YOU WANT TO END YOUR TRANSACTION AND RECEIVE CHANGE? (Y/N): ");
            String userFinished = userInput.nextLine();
            if (userFinished.equalsIgnoreCase("n")) {
                purchaseMenu();
            } else if (userFinished.equalsIgnoreCase("y")) {
                System.out.println("CONTINUING TO CHECKOUT");
                finishTransaction();
            } else {
                System.out.println("INVALID INPUT");
                System.out.println("PLEASE ENTER THE LETTER 'Y' FOR YES, OR 'N' FOR NO");
                purchaseMenu();
            }

        } else {
            System.out.println("User input not accepted.");
            System.out.println("Please choose from menu options below (input must be numeric):");
            purchaseMenu();
        }
    }

    // Feed Monies
    public void feedMoney() {
        System.out.println();
        System.out.println("INSERT BILLS INTO MACHINE BELOW");
        String billInserted = userInput.nextLine();
        if (billInserted.equals("1") || billInserted.equals("2") || billInserted.equals("5") || billInserted.equals("10") || billInserted.equals("20")) {
            moneyInMachine += Double.parseDouble(billInserted);
            logFeed(billInserted);
            purchaseMenu();
        } else {
            if (Double.parseDouble(billInserted) > 20) {
                System.out.println("THIS MACHINE DOES NOT ACCEPT BILLS OVER $20");

            } else {
                System.out.println("THIS IS NOT AN ACCEPTED TENDER FOR THIS MACHINE");
            }
            System.out.println("MONEY FED INTO THE MACHINE HAS BEEN RETURNED");
            System.out.println();
            System.out.println("PLEASE ENTER YOUR BILL VALUE AS A WHOLE NUMBER");
            feedMoney();
        }
    }

    // Call to purchase from the vending machine
    public void purchaseItem() {
        System.out.println();
        System.out.print("SELECT SLOT TO PURCHASE ITEM: ");
        String userChoice = userInput.nextLine();
        if (machineInventory.containsKey(userChoice)) {
            if (machineInventory.get(userChoice).getQuantityRemaining() < 1) {
                System.out.println(machineInventory.get(userChoice).getName() + " IN SLOT " + userChoice + " IS CURRENTLY SOLD OUT");
                purchaseMenu();
            } else {
                if (machineInventory.get(userChoice).getPrice() > moneyInMachine) {
                    System.out.println("INSUFFICIENT FUNDS, SELECTION NOT DISPENSED");
                    purchaseMenu();
                } else {
                    dispenseItem(userChoice);
                }
            }

        } else {
            System.out.println("SLOT LOCATION INVALID");
            System.out.println("PLEASE INPUT THE SLOT LOCATION AS A CAPITAL LETTER + NUMBER");
            purchaseMenu();
        }
    }

    // Dispensing the item
    public void dispenseItem(String inputSlot) {
        System.out.println();
        System.out.println("DISPENSING: " + machineInventory.get(inputSlot).getName() + " FOR: $" + machineInventory.get(inputSlot).getPrice());
        System.out.println(machineInventory.get(inputSlot).dispenseMessage());
        machineInventory.get(inputSlot).setQuantityRemaining(machineInventory.get(inputSlot).getQuantityRemaining() - 1);
        moneyInMachine -= machineInventory.get(inputSlot).getPrice();
        System.out.println("BALANCE REMAINING IN MACHINE: $" + moneyInMachine);
        logTransaction(inputSlot);
        purchaseMenu();
    }

    public void finishTransaction() {
        double change = moneyInMachine;
        int quarters = 0;
        int dimes = 0;
        int nickles = 0;

         quarters = (int)Math.floor(moneyInMachine / 0.25);
         moneyInMachine -= (0.25 * (double)quarters);
         dimes = (int)Math.floor(moneyInMachine / 0.10);
         moneyInMachine -= (0.10 * (double)dimes);
         nickles = (int)Math.floor(moneyInMachine / 0.05);
         moneyInMachine -= (0.05 * (double)nickles);

        System.out.println();
        System.out.println("THE FOLLOWING MONIES HAVE BEEN DISPENSED TO THE CHANGE CATCH:");
        System.out.println(quarters + " QUARTERS, " + dimes + " DIMES, " + nickles + " NICKLES");

        moneyInMachine = 0.00;
        System.out.println();
        System.out.println("*** THANK YOU FOR YOUR CHOOSING VEND'O'MATIC ***");
        System.out.println();
        logChange(change);
        mainMenu();
    }




    // Log Vending Machine Transactions
    public void logFeed(String billInserted) {
        Date currentTime = new Date();
        try (PrintWriter dataOutput = new PrintWriter(new FileOutputStream(logFile, true))) {
            dataOutput.println(formatter.format(currentTime) + " FEED MONEY: $" + billInserted + " $" + moneyInMachine);
        } catch (FileNotFoundException e) {
            System.out.println("Cannot open file for writing " + logFile.getAbsolutePath());
        }
    }

    public void logTransaction(String inputSlot) {
        Date currentTime = new Date();
        try (PrintWriter dataOutput = new PrintWriter(new FileOutputStream(logFile, true))) {
            dataOutput.println(formatter.format(currentTime) + " " + machineInventory.get(inputSlot).getName() + " " + inputSlot + " $" + machineInventory.get(inputSlot).getPrice() + " $" + moneyInMachine);
        } catch (FileNotFoundException e) {
            System.out.println("Cannot open file for writing " + logFile.getAbsolutePath());
        }
    }

    public void logChange(double change) {
        Date currentTime = new Date();
        try (PrintWriter dataOutput = new PrintWriter(new FileOutputStream(logFile, true))) {
            dataOutput.println(formatter.format(currentTime) + " GIVE CHANGE: $" + change + " $" + moneyInMachine);
        } catch (FileNotFoundException e) {
            System.out.println("Cannot open file for writing " + logFile.getAbsolutePath());
        }
    }



    public void salesReport() {
        System.out.println("*** SALES REPORT ***");
        System.out.println();
        // Make a map that with the key being the purchased Item and the value as the quantity purchased.
        // Map is a new map from looping through the inventory file and setting the name as key and value is 0 until purchase when I will increment the quantity ++
        // Add a new instance variable for total sales amount.
        //Loop through the Map and print each key value pair separated my a pipe character |
        // Print total sales amount
        // return to the main menu
    }
    
    // Exit the vending machine


    // Work on incorporating money format to 2 and only and always 2 decimal places.
}
