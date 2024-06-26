package onetoone.ShopItems;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@Api(value = "ShopItemsController", description = "REST APIs related to the ShopItems Entity")
@RestController
public class ShopItemsController {
    @Autowired
    ShopItemsRepository shopItemsRepository;

    private String success = "{\"message\":\"success\"}";
    private String failure = "{\"message\":\"failure\"}";

    //return all cosmetics in the game (in the DB)
    @GetMapping(path = "/shopItems")
    List<ShopItem> getShopItems() { return shopItemsRepository.findAll(); }

}
