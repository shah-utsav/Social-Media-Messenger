package message;
import account.AccountStatus;
import account.Account; 

import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.IOException;


/**
 * Represents a public post in a group.
 */

public class Post extends Message {
    private Group group;

    /**
     * Constructs a new Post.
     * 
     * @param from      The account sending the post.
     * @param repliedTo The message this post is replying to (can be null).
     * @param body      The content of the post.
     * @param group     The group where the post is published.
     */

    public Post(Account from, Group group, Message repliedTo, String body){
        super(from, repliedTo, body);
        this.group = group;
    }

    // New constructor for streaming
    public Post(BufferedReader br, Message repliedTo) throws IOException {
        super(br, repliedTo);
        this.group = new Group(br);
    }

    // Save method
    @Override
    public void save(BufferedWriter bw) throws IOException {
        super.save(bw);
        group.save(bw);
    }

    /**
     * Returns a string representation of the post.
     * 
     * @return The group name followed by the message details.
     */
    @Override
    public String toString(){
        return "Group: " + group + "\n" + super.toString();
    }
}