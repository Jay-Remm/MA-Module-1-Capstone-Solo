package com.techelevator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.*;

public class SalesReport {

    SimpleDateFormat urlFormat = new SimpleDateFormat("dd_MM_yy-HH.mm.ss");
    Date currentTime = new Date();

    private double profit;
    private Map<String, Integer> salesInventory = new HashMap<>();
    private final String FILE_PATH_BASE = "module-1-capstone/src/main/resources/";
    // File name has to include date and time
    private String fileReportName = "Sales-Report-" + urlFormat.format(currentTime) + ".txt";
    private File reportFile = new File(FILE_PATH_BASE + fileReportName);

    public Map<String, Integer> getSalesInventory() {
        return salesInventory;
    }

    public SalesReport(Map<String, Product> inventory, double profit) {
        this.profit = profit;
        List<String> slots = new ArrayList<>(inventory.keySet());
        Collections.sort(slots);
        for (String slot : slots) {
            String productName = inventory.get(slot).getName();
            int productQuantitySold = 5 - inventory.get(slot).getQuantityRemaining();
            salesInventory.put(productName, productQuantitySold);
        }
    }

    // Method to write a new file and loop through my map to print it in the file.
    public void writeReport() {
        try (PrintWriter dataOutput = new PrintWriter(reportFile)) {
            List<String> slots = new ArrayList<>(salesInventory.keySet());
            Collections.sort(slots);
            for (String slot : slots) {
                dataOutput.println(slot + " | " + salesInventory.get(slot));
            }
            dataOutput.println();
            dataOutput.println("*** TOTAL SALES *** $" + profit);
        } catch (FileNotFoundException e) {
            System.out.println("Cannot open file for writing " + reportFile.getAbsolutePath());
        }
    }

}
