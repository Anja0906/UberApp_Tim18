package tools;

import com.example.uberapp_tim18.R;

import java.time.LocalDateTime;
import java.util.ArrayList;

import model.Message;
import model.Ride;
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
        user1 = new User("anja", "petkovic", R.drawable.user4, 614578456, "anja@gmail.com", "Strumicka 6, Novi Sad, Srbija", "anja123", 1);
        user2 = new User("kristina", "andrijin", R.drawable.user5, 564845445, "kris@gmail.com", "ajhsdgeygdfwefj", "kris123", 1);
        user3 = new User("branislav", "stojkovic", R.drawable.user1, 45548745, "bane@gmail.com", "eucbdsfewfebcasodkwpdlmdv", "bane123", 2);
        user4 = new User("driver", "d", R.drawable.user2, 963852741, "driver@gmail.com", "oiugvbniuytgfvbnhgfcvbnytgf", "driver", 2);
        user5 = new User("passenger", "p", R.drawable.user3, 23456789, "passenger@gmail.com", "laksfwecnjkejdwqjf", "passenger", 1);

        ArrayList<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);
        users.add(user3);
        users.add(user4);
        users.add(user5);
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
