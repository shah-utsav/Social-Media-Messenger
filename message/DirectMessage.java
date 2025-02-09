package message;
import account.AccountStatus;
import account.Account;

/**
 * Represents a private message sent to a specific recipient.
 */

public class DirectMessage extends Message{
    private Account to;

    /**
     * Constructs a new DirectMessage.
     * 
     * @param from      The account sending the message.
     * @param repliedTo The message this message is replying to (can be null).
     * @param body      The content of the message.
     * @param recipient The recipient of the message.
     */

    public DirectMessage(Account from, Account to, Message repliedTo, String body){
        super(from, repliedTo, body);
        this.to = to;
    }

    /**
     * Returns a string representation of the direct message.
     * 
     * @return The recipient's name followed by the message details.
     */
    @Override
    public String toString(){
        return "To: " + to + "\n" + super.toString();
    }
}
