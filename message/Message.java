package message;
import account.AccountStatus;
import account.Account;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

public class Message{
    private Account from;
    private Date date;
    private Message repliedTo;
    private List<Message> replies;
    private String body;

    public Message(Account from, Message repliedTo, String body){
        this.from = from;
        this.repliedTo = repliedTo;
        this.body = body;
        this.date = new Date();
        this.replies = new ArrayList<>();
        
        if (repliedTo != null){
            repliedTo.addReply(this);
        }
    }

    public Message getRepliedTo(){
        return repliedTo;
    }
    
    public Message getReply(int index){
        if (index >= 0 && index < replies.size()){
            return replies.get(index);
        }

        return null;
    }

        /**
     * Returns the list of replies to this message.
     * 
     * @return A list of Message objects representing the replies.
     */
    public List<Message> getReplies() {
        return replies;
    }

    @Override
    public String toString(){
        StringBuilder result = new StringBuilder();
        result.append("Date: ").append(date).append("\n");
        result.append("From: ").append(from).append("\n");
        if (repliedTo != null) result.append("In Reply To: ").append(repliedTo.from.toString()).append("\n");

        if (!replies.isEmpty()){
            result.append("Replies: ");
            for (Message reply : replies){
                result.append("- ").append(reply.from.toString()).append("\n");
            }
        }

        return result.toString();
    }

    private void addReply(Message reply){
        if (reply != null) replies.add(reply);
    }
}