package onetoone;

import onetoone.Clans.ClanRepository;
import onetoone.Inventory.Inventory;
import onetoone.Inventory.InventoryRepository;
import onetoone.ShopItems.ShopItem;
import onetoone.ShopItems.ShopItemsRepository;
import onetoone.Wins.Wins;
import onetoone.Wins.WinsRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import onetoone.Laptops.LaptopRepository;
import onetoone.Users.User;
import onetoone.Users.UserRepository;
import onetoone.Clans.Clan;
import onetoone.Clans.ClanRepository;

import java.util.ArrayList;

/**
 * 
 * @author Vivek Bengre
 * 
 */ 

@SpringBootApplication
@EnableJpaRepositories
class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    // Create 3 users with their machines
    /**
     * 
     * @param userRepository repository for the User entity
     * Creates a commandLine runner to enter dummy data into the database
     * As mentioned in User.java just associating the Laptop object with the User will save it into the database because of the CascadeType
     */
    @Bean
    CommandLineRunner initUser(UserRepository userRepository, WinsRepository winsRepository, ShopItemsRepository shopItemsRepository, ClanRepository clanRepository, InventoryRepository inventoryRepository) {
        return args -> {
//            User user1 = new User("John", "JohnPassword");
//            User user2 = new User("John2", "John2Password");
//            Inventory inventory1 = new Inventory();
//            ArrayList<ShopItem> bort = new ArrayList<>();
//            ShopItem SI1 = new ShopItem("new item fortnite1", "description of fortnite1");
//            ShopItem SI2 = new ShopItem("new item fortnite2", "description of fortnite2");
//            ShopItem SI3 = new ShopItem("new item fortnite3", "description of fortnite3");
//            ShopItem SI4 = new ShopItem("new item fortnite4", "description of fortnite4");
//            bort.add(SI1);
//            bort.add(SI2);
//            bort.add(SI3);
//            inventory1.setShopItems(bort);
//            user1.setWins(4);
//            user1.setInventory(inventory1);
//            shopItemsRepository.save(SI1);
//            shopItemsRepository.save(SI2);
//            shopItemsRepository.save(SI3);
//            shopItemsRepository.save(SI4);
//            inventoryRepository.save(inventory1);
//            userRepository.save(user1);
//            userRepository.save(user1);
//            userRepository.save(user2);
//            Clan clan1 = new Clan("JennyClan", user2.getId());
//            clanRepository.save(clan1);
//            user2.setClan(clan1);
//            user1.setClan(clan1);
//            userRepository.save(user2);
//            userRepository.save(user1);
        };
    }

}
