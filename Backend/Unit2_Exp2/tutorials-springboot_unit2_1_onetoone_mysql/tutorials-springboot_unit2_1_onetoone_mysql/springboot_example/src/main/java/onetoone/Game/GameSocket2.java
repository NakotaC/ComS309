package onetoone.Game;

import java.io.IOException;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.StringReader;

import onetoone.Game.GameRepository;
import onetoone.Users.UserRepository;
import org.junit.jupiter.api.Disabled;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
@Disabled("Exclude from test coverage")
@Controller      // this is needed for this to be an endpoint to springboot
@ServerEndpoint(value = "/game2/{username}")  // this is Websocket url
public class GameSocket2 {

    // cannot autowire static directly (instead we do it by the below
    // method
    private static GameRepository gameRepo;

    private static UserRepository userRepo;

    /*
     * Grabs the MessageRepository singleton from the Spring Application
     * Context.  This works because of the @Controller annotation on this
     * class and because the variable is declared as static.
     * There are other ways to set this. However, this approach is
     * easiest.
     */
    @Autowired
    public void setGameRepository(GameRepository repo) {
        gameRepo = repo;  // we are setting the static variable
    }

    @Autowired
    public void setUserRepository(UserRepository repo) {
        userRepo = repo;  // we are setting the static variable
    }

    // Store all socket session and their corresponding username.
    private static Map<Session, String> sessionUsernameMap = new Hashtable<>();
    private static Map<String, Session> usernameSessionMap = new Hashtable<>();

    private final Logger logger = LoggerFactory.getLogger(GameSocket.class);

    @OnOpen
    public void onOpen(Session session, @PathParam("username") String username)
            throws IOException {

        logger.info("Entered into Open");

        // store connecting user information
        sessionUsernameMap.put(session, username);
        usernameSessionMap.put(username, session);
        int idk = usernameSessionMap.size();
        String number = Integer.toString(idk);

        //Send chat history to the newly connected user
        //sendMessageToPArticularUser(username, getChatHistory());

        // broadcast that new user joined
        //String message = "User:" + username + " has Joined the game";

        //UNCOMMENT FOR JUST PLAYER NUMBER AND COMMENT BELOW
        //broadcast(number);

        String testmessage = "User: " + username + " Player Number: " + number + " Gameid: 2";
        String test = "{\"playernum\":\"" + number + "\", \"gameid\":\"2\"}";
        broadcast(test);


        //session.getBasicRemote().sendText(number);
    }


    @OnMessage
    public void onMessage(Session session, String message) throws IOException {

        JsonReader reader = Json.createReader(new StringReader(message));
        JsonObject jsonObject = reader.readObject();
        reader.close();

        String player = jsonObject.getString("player");
        String card = jsonObject.getString("Card");

        // Handle new messages
        logger.info("Entered into Message: Got Message:" + message);
        String username = sessionUsernameMap.get(session);
        String www = "Player ";
        www = www.concat(player);
        www = www.concat(" drew a ");
        www = www.concat(card);
        //Change message to www if you want to send "Player _ drew a _" !!???????
        broadcast(message);

        // Direct message to a user using the format "@username <message>"
//        if (message.startsWith("@")) {
//            String destUsername = message.split(" ")[0].substring(1);
//
//            // send the message to the sender and receiver
//            sendMessageToPArticularUser(destUsername, "[DM] " + username + ": " + message);
//            sendMessageToPArticularUser(username, "[DM] " + username + ": " + message);
//
//        }
//        else { // broadcast
//            broadcast(username + ": " + message);
//        }
    }


    @OnClose
    public void onClose(Session session) throws IOException {
        logger.info("Entered into Close");

        // remove the user connection information
        String username = sessionUsernameMap.get(session);
        sessionUsernameMap.remove(session);
        usernameSessionMap.remove(username);

        // broadcase that the user disconnected
        String message = username + " disconnected";
        broadcast(message);
    }


    @OnError
    public void onError(Session session, Throwable throwable) {
        // Do error handling here
        logger.info("Entered into Error");
        throwable.printStackTrace();
    }


    private void sendMessageToPArticularUser(String username, String message) {
        try {
            usernameSessionMap.get(username).getBasicRemote().sendText(message);
        }
        catch (IOException e) {
            logger.info("Exception: " + e.getMessage().toString());
            e.printStackTrace();
        }
    }


    private void broadcast(String message) {
        sessionUsernameMap.forEach((session, username) -> {
            try {
                session.getBasicRemote().sendText(message);
            }
            catch (IOException e) {
                logger.info("Exception: " + e.getMessage().toString());
                e.printStackTrace();
            }

        });

    }


    // Gets the Chat history from the repository
//    private String getChatHistory() {
//        List<Message> messages = msgRepo.findAll();
//
//        // convert the list to a string
//        StringBuilder sb = new StringBuilder();
//        if(messages != null && messages.size() != 0) {
//            for (Message message : messages) {
//                sb.append(message.getUserName() + ": " + message.getContent() + "\n");
//            }
//        }
//        return sb.toString();
//    }

} // end of Class
