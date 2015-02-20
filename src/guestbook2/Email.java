package guestbook2;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
public class Email {
   @Id String emailaddress;
    String firstname;
    String lastname;
    
    private Email() {}
    
    public Email(String emailaddress, String firstname, String lastname) {
        this.emailaddress = emailaddress;
        this.firstname = firstname;
        this.lastname = lastname;
    }
    public String getEmail(){
    	return emailaddress;
    }
    public String getFirstName(){
    	return firstname;
    }
    public String getLastName(){
    	return lastname;
    }
    
}