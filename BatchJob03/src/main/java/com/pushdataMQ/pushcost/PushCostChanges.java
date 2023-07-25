package com.pushdataMQ.pushcost;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;

public class PushCostChanges {

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: java PushCostChanges <eprn_central_db_url> <store_db_url>");
            System.exit(1);
        }

        String eprnCentralDbUrl = args[0];
        String storeDbUrl = args[1];

        try {
            // Set up JMS connection to IBM MQ
            Context initialContext = new InitialContext();
            ConnectionFactory connectionFactory = (ConnectionFactory) initialContext.lookup("jms/ConnectionFactory");
            Destination queue = (Destination) initialContext.lookup("jms/Queue");

            // Establish the JMS connection
            Connection connection = connectionFactory.createConnection();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            MessageProducer producer = session.createProducer(queue);

            // Retrieve cost changes from EPRN Central database and send as JMS messages
            // ... (Add your code here to retrieve cost changes and send messages)

            System.out.println("Cost changes have been pushed to the store database using IBM MQ.");

            // Close JMS resources
            producer.close();
            session.close();
            connection.close();

        } catch (Exception e) {
            System.err.println("An unexpected error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

