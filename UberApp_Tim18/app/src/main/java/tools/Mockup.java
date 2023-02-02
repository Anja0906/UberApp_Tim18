package tools;

import com.example.uberapp_tim18.R;

import java.time.LocalDateTime;
import java.util.ArrayList;

import model.Message;
import model.Ride;
import model.Role;
import model.User;

public class Mockup {
    private static User user1, user2, user3, user4, user5;

    public static ArrayList<Ride> getRides(){
        Ride ride1 = new Ride(LocalDateTime.now(), LocalDateTime.now(), 250, false, true, true, true, 14);
        Ride ride2 = new Ride(LocalDateTime.now(), LocalDateTime.now(), 340, true, true, false, false, 20);
        Ride ride3 = new Ride(LocalDateTime.now(), LocalDateTime.now(), 520, false, false, true, true, 35);
        Ride ride4 = new Ride(LocalDateTime.now(), LocalDateTime.now(), 800, true, false, true, true, 60);
        Ride ride5 = new Ride(LocalDateTime.now(), LocalDateTime.now(), 400, false, true, false, false, 28);

        ArrayList<Ride> rides = new ArrayList<>();
        rides.add(ride1);
        rides.add(ride2);
        rides.add(ride3);
        rides.add(ride4);
        rides.add(ride5);

        return rides;

    }

    public static ArrayList<User> getUsers(){
//        user1 = new User(1, "anja", "petkovic", R.drawable.user4, "614578456", "anja@gmail.com", "Strumicka 6, Novi Sad, Srbija", false, "anja123", Role.ADMIN);
//        user2 = new User(2, "kristina", "andrijin", R.drawable.user5, "564845445", "kris@gmail.com", "ajhsdgeygdfwefj", false, "kris123", Role.PASSENGER);
//        user3 = new User(3, "branislav",  "stojkovic", R.drawable.user1, "614578456", "bane@gmail.com", "Strumicka 6, Novi Sad, Srbija", false, "bane123", Role.DRIVER);
//        user4 = new User(4, "driver", "d", R.drawable.user2, "614578456", "driver@gmail.com", "Strumicka 6, Novi Sad, Srbija", false, "driver123", Role.PASSENGER);
//        user5 = new User(5, "passenger", "p", R.drawable.user3, "614578456", "passenger@gmail.com", "Strumicka 6, Novi Sad, Srbija", false, "passenger123", Role.PASSENGER);

        ArrayList<User> users = new ArrayList<>();
//        users.add(user1);
//        users.add(user2);
//        users.add(user3);
//        users.add(user4);
//        users.add(user5);
        return users;
    }

    public static ArrayList<Message> getMessages(){
        Message message1 = new Message(user1, user2, "fnfakjnflkannQEKBGFnQLGBliwbgliwjbnghlkngjkqewngoqengjkqnegjnqejgnjwngkljsrgnkjngounqeqhnOČGINQLIGBJWNOoijnqagčoGOurwnbgowrigjmglengl", LocalDateTime.now(), Message.MessageType.PANIC);
        Message message6 = new Message(user1, user2, "fnfakjnflkannQEKBGFnQLGBliwbgliwjbnghlkngjkqewngoqengjkqnegjnqejgnjwngkljsrgnkjngounqeqhnOČGINQLIGBJWNOoijnqagčoGOurwnbgowrigjmglengl", LocalDateTime.now(), Message.MessageType.PANIC);
//        Message message7 = new Message(user2, user1, "hi", LocalDateTime.now(), Message.MessageType.PANIC);
//        Message message10 = new Message(user2, user1, "hello", LocalDateTime.now(), Message.MessageType.PANIC);
//        Message message11 = new Message(user2, user1, "what's up?", LocalDateTime.now(), Message.MessageType.PANIC);
        Message message12 = new Message(user2, user1, "fnfakjnflkannQEKBGFnQLGBliwbgliwjbnghlkngjkqewngoqengjkqnegjnqejgnjwngkljsrgnkjngounqeqhnOČGINQLIGBJWNOoijnqagčoGOurwnbgowrigjmglengl", LocalDateTime.now(), Message.MessageType.PANIC);
//        Message message8 = new Message(user1, user2, ":D", LocalDateTime.now(), Message.MessageType.PANIC);
//        Message message9 = new Message(user1, user2, "ikr", LocalDateTime.now(), Message.MessageType.PANIC);
        Message message2 = new Message(user2, user3, "nfqlgqlnglqnglngqngpqnkg", LocalDateTime.now(), Message.MessageType.SUPPORT);
        Message message3 = new Message(user3, user4, "nmglngmgnqng", LocalDateTime.now(), Message.MessageType.RIDE);
        Message message4 = new Message(user4, user5, "gnknqjg.lngoqngolqngonqgn0", LocalDateTime.now(), Message.MessageType.RIDE);
        Message message5 = new Message(user5, user1, "gmnlwnmglw glwnlgnqngqgpqgpwngpwgmn", LocalDateTime.now(), Message.MessageType.SUPPORT);

        ArrayList<Message> messages = new ArrayList<>();
        messages.add(message1);
        messages.add(message2);
        messages.add(message3);
        messages.add(message4);
        messages.add(message5);
        messages.add(message6);
//        messages.add(message7);
//        messages.add(message8);
//        messages.add(message9);
//        messages.add(message10);
//        messages.add(message11);
        messages.add(message12);


        return messages;
    }
}
