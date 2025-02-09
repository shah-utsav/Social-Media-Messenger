package account;

/**
 * Represents the status of an account.
 * An account can be in one of three states: Normal, Muted, or Blocked.
 * 
 * @author Utsav Shah
 * @version 1.0
 * @since 02/08/2025
 */
public enum AccountStatus {
    /**
     * The account is in a normal state with no restrictions.
     */
    Normal,

    /**
     * The account is muted. It can read posts but cannot create new posts visible to others.
     */
    Muted,

    /**
     * The account is blocked. It cannot log in or perform any actions.
     */
    Blocked
}