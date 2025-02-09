package message;
import account.AccountStatus;
import account.Account;

public class TestGroup {
    public static void main(String[] args){
        Group group = new Group("Engineers");
        System.out.println("Group Created: " + group);

        if (!group.isActive()){
            System.err.println("Error: Group should be active by default!");
        }

        group.disable();
        if (group.isActive()){
            System.err.println("Error: Group should be inactive after calling disable()!");
        }
        System.out.println("Group after disable(): " + group);

        group.enable();
        if (!group.isActive()){
            System.err.println("Error: Group should be active after calling enable()!");
        }
        System.out.println("Group after enable(): " + group);

        try{
            Group exampleGroup = new Group("");
            System.err.println("Error: Group name can't be empty!");
        } catch (IllegalArgumentException e){
            System.err.println("Caught expected exception for empty group name: " + e.getMessage());
        }
    }    
}