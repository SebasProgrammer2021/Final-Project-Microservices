package co.edu.uniquindio.inventory;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class InventoryApp {
    String dbName = "inventory";
    MongoDatabase database = MongoClients.create().getDatabase(dbName);
    public static void main(String[] args) {
        SpringApplication.run(InventoryApp.class, args);
    }
}