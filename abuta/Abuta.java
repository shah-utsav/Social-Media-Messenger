package abuta;

import account.Account;
import message.DirectMessage;
import message.Group;
import message.Message;
import message.Post;
import menu.Menu;
import menu.MenuItem;

import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Abuta {
    private ArrayList<Account> accounts;
    private ArrayList<Group> groups;
    private Message message;
    private Menu menu;
    private String output;
    private boolean running;
    private String filename;

    public Abuta() {
        // Initialize accounts and groups as ArrayLists
        accounts = new ArrayList<>();
        groups = new ArrayList<>();

        // Add at least 5 Account instances to accounts
        accounts.add(new Account("Gandalf the Grey"));
        accounts.add(new Account("Frodo Baggins"));
        accounts.add(new Account("Arwen"));
        accounts.add(new Account("Balrog"));
        accounts.add(new Account("Legolas"));

        // Add at least 5 Group instances to groups
        groups.add(new Group("Power"));
        groups.add(new Group("Wisdom"));
        groups.add(new Group("Courage"));
        groups.add(new Group("Friendship"));
        groups.add(new Group("Adventure"));

        // Create the root message using an account and a group
        message = new Post(accounts.get(0), groups.get(0), null,
                "Welcome to Middle Earth! The journey doesn't end here; it is only the beginning!");

        // Initialize the menu and add MenuItem objects
        menu = new Menu();
        menu.addMenuItem(new MenuItem("Exit", this::endApp));
        menu.addMenuItem(new MenuItem("Show Reply", this::showReply));
        menu.addMenuItem(new MenuItem("Show Replied To", this::showRepliedTo));
        menu.addMenuItem(new MenuItem("Add Reply", this::reply));
        menu.addMenuItem(new MenuItem("Add Account", () -> this.addAccount())); // Bonus: Add Account
        menu.addMenuItem(new MenuItem("Add Group", () -> this.addGroup())); // Bonus: Add Group
        menu.addMenuItem(new MenuItem("New abUTA", () -> this.newAbuta()));
        menu.addMenuItem(new MenuItem("Save", () -> this.save()));
        menu.addMenuItem(new MenuItem("Save As", () -> this.saveAs()));
        menu.addMenuItem(new MenuItem("Open", () -> this.open()));

        // Initialize output and running
        output = "";
        running = true;
    }

    public void mdi() {
        while (running) {
            // Print the title, menu, output, and message
            String s = "- = # a b U T A # = -";
            System.out.printf(" %75s \n", s);
            System.out.println(menu);
            System.out.println(output);
            System.out.println(message);
            System.out.println(
                    "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n\n");

            // Clear the output
            output = "";

            // Get the user's selection
            int selection = Menu.getInt("Enter your choice: ");

            // Run the selected menu item
            menu.run(selection);
        }
    }

    private void endApp() {
        running = false;
        output = "Exiting the application...";
    }

    private void showRepliedTo() {
        if (message.getRepliedTo() != null) {
            message = message.getRepliedTo();
        } else {
            output = "This message is not a reply to any other message.";
        }
    }

    private void showReply() {
        int numReplies = message.getReplies().size();
        if (numReplies == 0) {
            output = "This message has no replies.";
        } else if (numReplies == 1) {
            // If there's only one reply, switch to it automatically
            message = message.getReply(0);
        } else {
            // Prompt the user for a reply index
            while (true) {
                Integer replyIndex = Menu
                        .getInt("Enter the index of the reply to view (0 to " + (numReplies - 1) + "): ");

                // Handle cancel or invalid input
                if (replyIndex == null) {
                    output = "Reply selection canceled.";
                    break;
                }

                // Check if the index is valid
                if (replyIndex >= 0 && replyIndex < numReplies) {
                    message = message.getReply(replyIndex);
                    break; // Exit the loop after switching to the selected reply
                } else {
                    output = "Invalid reply index. Please enter a number between 0 and " + (numReplies - 1) + ".";
                }
            }
        }
    }

    private void reply() {
        // Ask the user for the type of message (Post or DirectMessage)
        Character messageType = Menu.getChar("Enter 'P' for Post or 'D' for DirectMessage: ", null, null);
        if (messageType == null || (messageType != 'P' && messageType != 'p' && messageType != 'D')) {
            output = "Invalid message type.";
            return;
        }

        // Select the author
        int authorIndex = Menu.selectItemFromList("Select the author: ", accounts);
        if (authorIndex == -1) {
            output = "Invalid author selection.";
            return;
        }
        Account author = accounts.get(authorIndex);

        // Select the group or recipient
        Group group = null;
        Account recipient = null;
        if (messageType == 'P' || messageType == 'p') {
            int groupIndex = Menu.selectItemFromList("Select the group: ", groups);
            if (groupIndex == -1) {
                output = "Invalid group selection.";
                return;
            }
            group = groups.get(groupIndex);
        } else {
            int recipientIndex = Menu.selectItemFromList("Select the recipient: ", accounts);
            if (recipientIndex == -1) {
                output = "Invalid recipient selection.";
                return;
            }
            recipient = accounts.get(recipientIndex);
        }

        // Get the body of the message
        String body = Menu.getString("Enter the message body: ");
        if (body == null || body.isEmpty()) {
            output = "Message body cannot be empty.";
            return;
        }

        // Create the new message
        Message newMessage;
        if (messageType == 'P' || messageType == 'p') {
            newMessage = new Post(author, group, message, body);
        } else {
            newMessage = new DirectMessage(author, recipient, message, body);
        }

        // Update the output
        output = "Reply added successfully.";
    }

    
    private void addAccount() {
        String name = Menu.getString("Enter the name of the new account: ");
        if (name == null || name.isEmpty()) {
            output = "Account name cannot be empty.";
            return;
        }

        Account newAccount = new Account(name);
        accounts.add(newAccount);
        output = "Account '" + name + "' added successfully.";
    }

    
    private void addGroup() {
        String name = Menu.getString("Enter the name of the new group: ");
        if (name == null || name.isEmpty()) {
            output = "Group name cannot be empty.";
            return;
        }

        Group newGroup = new Group(name);
        groups.add(newGroup);
        output = "Group '" + name + "' added successfully.";
    }

    private void newAbuta() {
        // Reset to the original root message
        message = new Post(accounts.get(0), groups.get(0), null, "Welcome to Middle Earth!");
        output = "New abUTA created.";
    }

    private void save() {
        if (filename == null) {
            output = "No filename specified. Use 'Save As' to specify a filename.";
            return;
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
            message.save(bw);
            output = "Saved successfully to " + filename;
        } catch (IOException e) {
            output = "Error saving file: " + e.getMessage();
            e.printStackTrace();
        }
    }

    private void saveAs() {
        String newFilename = Menu.getString("Enter the filename to save as: ");
        if (newFilename == null || newFilename.isEmpty()) {
            output = "Filename cannot be empty.";
            return;
        }

        filename = newFilename;
        save();
    }

    private void open() {
        String newFilename = Menu.getString("Enter the filename to open: ");
        if (newFilename == null || newFilename.isEmpty()) {
            output = "Filename cannot be empty.";
            return;
        }

        filename = newFilename;
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            message = new Post(br, null);
            output = "Opened successfully from " + filename;
        } catch (IOException e) {
            output = "Error opening file: " + e.getMessage();
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Abuta abuta = new Abuta();
        abuta.mdi();
    }
}