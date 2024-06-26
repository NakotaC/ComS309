package onetoone.Users;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import onetoone.Clans.Clan;
import onetoone.EquippedItems.EquippedItems;
import onetoone.Inventory.Inventory;
import onetoone.MatchHistory.History;
import onetoone.ShopItems.ShopItem;
import onetoone.Wins.Wins;

import java.util.ArrayList;
import java.util.List;
import onetoone.Clans.ClanRepository;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 
 * @author Vivek Bengre
 * 
 */



@Entity
public class User {

     /* 
     * The annotation @ID marks the field below as the primary key for the table created by springboot
     * The @GeneratedValue generates a value if not already present, The strategy in this case is to start from 1 and increment for each table
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String username;
    private String password;

    @ManyToOne
    @JoinColumn(name = "clan_id")
    @JsonIgnore
    private Clan clan;

    @OneToOne(mappedBy = "user", cascade = CascadeType.MERGE)
    @JsonBackReference
    private Inventory inventory;

    @OneToOne(mappedBy = "user", cascade = CascadeType.MERGE)
    @JsonBackReference
    private EquippedItems equippedItems;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonBackReference
    private Wins wins;

    private int bank = 0;

    private int xp = 0;

    /*
     * @OneToOne creates a relation between the current entity/table(Laptop) with the entity/table defined below it(User)
     * cascade is responsible propagating all changes, even to children of the class Eg: changes made to laptop within a user object will be reflected
     * in the database (more info : https://www.baeldung.com/jpa-cascade-types)
     * @JoinColumn defines the ownership of the foreign key i.e. the user table will have a field called laptop_id
     */
//    @Autowired
//    ClanRepository clanRepository;
    public User(String Username, String password, ClanRepository clanRepository) {
        if(Username != null) {
            this.username = Username;
        }
        else {
            this.username = " ";
        }
        this.password = password;
        this.setWins(0);
        this.clan = clanRepository.findById(1);
    }

    public User() {
    }

    // =============================== Getters and Setters for each field ================================== //

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }
    public String getUsername(){
        return username;
    }

    public void setUsername(String Username){
        this.username = Username;
    }
    public String getPassword(){
        return password;
    }

    public void setPassword(String Password){
        this.password = Password;
    }
    public int getClan(){
        return clan.getId();
    }

    public void setClan(Clan clan){
        this.clan = clan;
    }
//    public int getUM(){
//        return user_match.getId();
//    }
//
//    public void setUM(UM um){
//        this.user_match = um;
//    }
    public Inventory getInventory(){
        return inventory;
    }

    public void setInventory(Inventory inventory){
        if (this.inventory == null) {
            this.inventory = new Inventory();
        }
        this.inventory = inventory;
        inventory.setUser(this);
    }

    public EquippedItems getEquipped(){
        return equippedItems;
    }

    public void setEquipped(EquippedItems equippedItems){
        if (this.equippedItems == null) {
            this.equippedItems = new EquippedItems();
        }
        this.equippedItems = equippedItems;
        equippedItems.setUser(this);
    }

//    public void setInventory(List<ShopItem> shopItem){
//        if (this.inventory == null) {
//            this.inventory = new Inventory();
//        }
//        this.inventory = inventory;
//    }
    public void setItems(List<ShopItem> shopItem){
        if (this.inventory == null) {
            this.inventory = new Inventory();
        }
        this.inventory.setShopItems(shopItem);
    }

//    public void setHistory(List<History> history){
//        if (this.user_match == null) {
//            this.user_match = new UM();
//        }
//        this.user_match.setUM(history);
//    }

    public void setItems(ShopItem shopItem){
        if (this.inventory == null) {
            this.inventory = new Inventory();
        }
        this.inventory.setShopItems(shopItem);
    }

    public void setequippedItems(List<ShopItem> shopItem){
        if (this.equippedItems == null) {
            this.equippedItems = new EquippedItems();
        }
        this.equippedItems.setShopItems(shopItem);
    }

    public void setequippedItems(ShopItem shopItem){
        if (this.equippedItems == null) {
            this.equippedItems = new EquippedItems();
        }
        this.equippedItems.setShopItems(shopItem);
    }


    public Wins getWins(){
        return wins;
    }

    public void setWins(int wins){
        if (this.wins == null) {
            this.wins = new Wins(0);
            // branch 20-quest-init
            this.wins.setQDate(-1);
            // ____________________
            this.wins.setUser(this);

        }
        this.wins.setWins(wins);
    }

    public void setWins2(int wins){
        Wins tmp = new Wins(wins);
        this.wins = tmp;
    }

    public void setXp(int xp){
        this.xp = xp;
    }

    public int getXp() {
        return this.xp;
    }

    public void setBank(int xp){
        this.bank = xp;
    }

    public int getBank() {
        return this.bank;
    }




}
