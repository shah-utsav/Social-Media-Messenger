package message;
import account.AccountStatus;
import account.Account;

import java.util.Scanner;

public class DemoMessage {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Account alice = new Account("Alice"), bob = new Account("Bob");

        System.out.println("Enter Alice's message:");
        Message msg1 = new Message(alice, null, scanner.nextLine());

        System.out.println("Enter Bob's reply:");
        Message msg2 = new Message(bob, msg1, scanner.nextLine());

        System.out.println("Enter Alice's reply to Bob:");
        Message msg3 = new Message(alice, msg2, scanner.nextLine());

        System.out.println("\nMessages and Replies:");
        System.out.println(msg1);
        System.out.println(msg2);
        System.out.println(msg3);

        System.out.println("\nReplies to Alice's original message:");
        msg1.getReplies().forEach(reply -> System.out.println(reply));
    }
}