package account;

/**
 * Represents an account in the system. Each account has a unique ID, a name, and a status.
 * The status can be Normal, Muted, or Blocked.
 * 
 * @author Utsav Shah
 * @version 1.0
 * @since 02/08/2025
 */
public class Account {
    private String name;        // The name of the account
    private int id;             // The unique ID of the account
    private static int nextID = 1; // Static counter for generating unique IDs
    private AccountStatus status; // The status of the account (Normal, Muted, or Blocked)

    /**
     * Constructs a new Account with the given name.
     * The account ID is automatically generated, and the status is set to Normal by default.
     * 
     * @param name The name of the account. Cannot be null or empty.
     * @throws IllegalArgumentException If the name is null or empty.
     */
    public Account(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Bad Name!");
        }
        this.name = name;
        this.id = nextID++;
        this.status = AccountStatus.Normal;
    }

    /**
     * Sets the status of the account.
     * 
     * @param status The new status of the account (Normal, Muted, or Blocked).
     */
    public void setStatus(AccountStatus status) {
        this.status = status;
    }

    /**
     * Checks if the account is muted.
     * An account is muted if its status is either Muted or Blocked.
     * 
     * @return true if the account is muted, false otherwise.
     */
    public boolean isMuted() {
        return this.status != AccountStatus.Normal;
    }

    /**
     * Checks if the account is blocked.
     * An account is blocked if its status is Blocked.
     * 
     * @return true if the account is blocked, false otherwise.
     */
    public boolean isBlocked() {
        return this.status == AccountStatus.Blocked;
    }

    /**
     * Returns a string representation of the account.
     * The string includes the account name, ID, and status (if not Normal).
     * 
     * @return A string in the format "Name (ID)" or "Name (ID) [Status]".
     */
    @Override
    public String toString() {
        String result = this.name + " (" + this.id + ")";
        if (this.status != AccountStatus.Normal) {
            result += " [" + this.status + "]";
        }
        return result;
    }
}