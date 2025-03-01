package message;

import account.AccountStatus;
import account.Account;

import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.IOException;

/**
 * Represents a group in the system.
 * 
 * @author Utsav Shah
 * @version 1.0
 * @since 02/08/2025
 */

public class Group {
    private String name;
    private boolean active;

    /**
     * Constructs a new Group with the given name.
     * 
     * @param name The name of the group.
     * @throws IllegalArgumentException If the name is null or empty.
     */

    public Group(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty!");
        }
        this.name = name;
        this.active = true;
    }

    // New constructor for streaming
    public Group(BufferedReader br) throws IOException {
        this.name = br.readLine();
    }

    // Save method
    public void save(BufferedWriter bw) throws IOException {
        bw.write(name + "\n");
    }

    /**
     * Checks if the group is active.
     * 
     * @return true if the group is active, false otherwise.
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Disables the group, making it inactive.
     */
    public void disable() {
        this.active = false;
    }

    /**
     * Enables the group, making it active.
     */
    public void enable() {
        this.active = true;
    }

    /**
     * Returns a string representation of the group.
     * 
     * @return The group's name, followed by " [inactive]" if the group is inactive.
     */
    @Override
    public String toString() {
        return name + (active ? "" : " [inactive]");
    }
}