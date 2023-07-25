package com.pushdataMQ.loadcost;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class LoadProductCost {

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: java LoadProductCost <cost_file_path> <database_url>");
            System.exit(1);
        }

        String costFilePath = args[0];
        String databaseUrl = args[1];

        try {
            // Load the JDBC driver (You may need to replace 'your_jdbc_driver_class' with your actual driver class)
            Class.forName("com.mysql.jdbc.Driver");
            
            // Establish the database connection
            Connection connection = DriverManager.getConnection(databaseUrl, "root", "uday");

            // Prepare the SQL statement for inserting product cost data
            String insertQuery = "INSERT INTO product_costs (product_name, cost) VALUES (?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);

            // Read the product cost file and insert data into the database
            try (BufferedReader reader = new BufferedReader(new FileReader(costFilePath))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split("\\|");
                    if (parts.length == 2) {
                        String productName = parts[0];
                        double cost = Double.parseDouble(parts[1]);

                        preparedStatement.setString(1, productName);
                        preparedStatement.setDouble(2, cost);
                        preparedStatement.executeUpdate();
                    }
                }
            }

            System.out.println("Product cost data has been loaded into the EPRN Central database.");

            // Close resources
            preparedStatement.close();
            connection.close();

        } catch (ClassNotFoundException e) {
            System.err.println("Failed to load the JDBC driver.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("An unexpected error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
