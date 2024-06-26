package onetoone.Users;

import java.net.http.HttpHeaders;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.util.JSONWrappedObject;
import io.swagger.annotations.Api;
import onetoone.Clans.Clan;
import onetoone.Clans.ClanRepository;
import onetoone.Inventory.InventoryController;
import onetoone.Inventory.InventoryRepository;
import onetoone.Wins.WinsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;


/**
 * 
 * @author Vivek Bengre
 * 
 */
@Api(value = "UserController", description = "REST APIs related to the User Entity")
@RestController
public class UserController {

    @Autowired
    UserRepository userRepository;


    @Autowired
    InventoryRepository inventoryRepository;

    @Autowired
    ClanRepository clanRepository;

    private String success = "{\"message\":\"success\"}";
    private String failure = "{\"message\":\"failure\"}";

    @GetMapping("/users")
    List<User> getAllUsers(){
        return userRepository.findAll();
    }

    @GetMapping(path = "/users/{id}")
    User getUserById( @PathVariable int id){
        return userRepository.findById(id);
    }


    @PostMapping(path = "/users/signup")
    String createUser(@RequestHeader("username") String username, @RequestHeader("password") String password){
        List<User> users = userRepository.findAll();
        for (int i = 1; i < users.size(); i++){
            if (username.equals(users.get(i).getUsername())){
                return failure;
            }
        }
        User user = new User(username, password, clanRepository);
        userRepository.save(user);
        Clan noClan = clanRepository.findById(1);
        ArrayList<Integer> mems = noClan.toIntList(noClan.getMembers());
        mems.add(user.getId());
        noClan.setMember(mems);
        clanRepository.save(noClan);
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/";
        String requestBody = null;
        restTemplate.postForObject(url + "/inventory/" + Integer.toString(user.getId()), requestBody, String.class);
        restTemplate.postForObject(url + "/newEquip/" + Integer.toString(user.getId()), requestBody, String.class);
        userRepository.save(user);
        return success;
    }

    @GetMapping(path = "/users/login")
    User loginUser(@RequestHeader("username") String username, @RequestHeader("password") String password){
        List<User> users = userRepository.findAll();
        for (int i = 0; i < users.size(); i++){
            if (username.equals(users.get(i).getUsername()) && password.equals(users.get(i).getPassword())){
                return users.get(i);
            }
        }
        return users.get(0);
    }

//    @PostMapping(path = "/users/{id}/{score}")
//    String userAddToUserScore(@PathVariable int id, @PathVariable int score) {
//        User user = userRepository.findById(id);
//        user.setScore(user.getScore() + score);
//        userRepository.save(user);
//        return success;
//    }

//    @PutMapping("/users/{id}")
//    User updateUser(@PathVariable int id, @RequestBody User request){
//        User user = userRepository.findById(id);
//        if(user == null)
//            return null;
//        userRepository.save(request);
//        return userRepository.findById(id);
//    }

//    @PutMapping("/users/{userId}/laptops/{laptopId}")
//    String assignLaptopToUser(@PathVariable int userId,@PathVariable int laptopId){
//        User user = userRepository.findById(userId);
//        Laptop laptop = laptopRepository.findById(laptopId);
//        if(user == null || laptop == null)
//            return failure;
//        laptop.setUser(user);
//        user.setLaptop(laptop);
//        userRepository.save(user);
//        return success;
//    }

    @DeleteMapping(path = "/users/{id}")
    String deleteUser(@PathVariable int id){
        userRepository.deleteById(id);
        return success;
    }

    @PostMapping(path = "/users/xp/{id}/{amount}")
    void addUserXp(@PathVariable int id, @PathVariable int amount) {
        User user = userRepository.findById(id);
        user.setXp(user.getXp() + amount);
        userRepository.save(user);
    }

    @PostMapping(path = "/users/bank/{id}/{amount}")
    void addUserBank(@PathVariable int id, @PathVariable int amount) {
        User user = userRepository.findById(id);
        user.setBank(user.getBank() + amount);
        userRepository.save(user);
    }

}
