package message;

import account.Account;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.IOException;

/**
 * Represents a message in the system. A message can be sent by an account and
 * can be a reply to another message.
 * Each message has a sender, a timestamp, a body, and a list of replies.
 * 
 * @author Utsav Shah
 * @version 2.0
 * @since 03/01/2025    
 */
public class Message {
    private Account from; // The account that sent the message
    private Date date; // The timestamp of the message
    private Message repliedTo; // The message this message is replying to (can be null)
    private List<Message> replies; // The list of replies to this message
    private String body; // The content of the message

    /**
     * Constructs a new Message with the given sender, replied-to message, body, and
     * group.
     * If the message is a reply, it is added to the replied-to message's list of
     * replies.
     * 
     * @param from      The account that sent the message.
     * @param repliedTo The message this message is replying to (can be null).
     * @param body      The content of the message.
     */
    public Message(Account from, Message repliedTo, String body) {
        this.from = from;
        this.repliedTo = repliedTo;
        this.body = body;
        this.date = new Date();
        this.replies = new ArrayList<>();

        if (repliedTo != null) {
            repliedTo.addReply(this);
        }
    }

    // New constructor for streaming
    public Message(BufferedReader br, Message repliedTo) throws IOException {
        this.from = new Account(br);
        this.date = new Date(Long.parseLong(br.readLine()));
        this.body = br.readLine();
        this.repliedTo = repliedTo;
        this.replies = new ArrayList<>();

        int replyCount = Integer.parseInt(br.readLine());
        for (int i = 0; i < replyCount; i++) {
            String className = br.readLine();
            if (className.equals("message.Post")) {
                replies.add(new Post(br, this));
            } else if (className.equals("message.DirectMessage")) {
                replies.add(new DirectMessage(br, this));
            }
        }

        if (repliedTo != null) {
            repliedTo.addReply(this);
        }
    }

    // Save method
    public void save(BufferedWriter bw) throws IOException {
        from.save(bw);
        bw.write(date.getTime() + "\n");
        bw.write(body + "\n");
        bw.write(replies.size() + "\n");
        for (Message reply : replies) {
            bw.write(reply.getClass().getName() + "\n");
            reply.save(bw);
        }
    }

    /**
     * Returns the message this message is replying to.
     * 
     * @return The replied-to message, or null if this message is not a reply.
     */
    public Message getRepliedTo() {
        return repliedTo;
    }

    public int getNumReplies() {
        return replies.size();
    }

    /**
     * Returns the reply at the specified index.
     * 
     * @param index The index of the reply to retrieve.
     * @return The reply at the specified index, or null if the index is out of
     *         bounds.
     */
    public Message getReply(int index) {
        if (index >= 0 && index < replies.size()) {
            return replies.get(index);
        }
        return null;
    }

    public String getTitle() {
        return body.split("\n")[0];
    }

    /**
     * Returns the list of replies to this message.
     * 
     * @return A list of Message objects representing the replies.
     */
    public List<Message> getReplies() {
        return replies;
    }

    /**
     * Returns a string representation of the message.
     * The string includes the group, timestamp, sender, replied-to message (if
     * any), replies (if any), and body.
     * 
     * @return A formatted string representation of the message.
     */
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("Date: ").append(date).append("\n");
        result.append("From: ").append(from).append("\n");

        if (repliedTo != null) {
            result.append("In Reply To: ").append(repliedTo.from.toString()).append("\n");
        }

        if (!replies.isEmpty()) {
            result.append("Replies: ");
            for (int i = 0; i < replies.size(); i++) {
                result.append("[").append(i).append("] ").append(replies.get(i).from.toString()).append("\n");
            }
        }

        result.append("\n").append(body).append("\n");
        return result.toString();
    }

    /**
     * Adds a reply to this message's list of replies.
     * 
     * @param reply The reply to add.
     */
    private void addReply(Message reply) {
        if (reply != null) {
            replies.add(reply);
        }
    }
}