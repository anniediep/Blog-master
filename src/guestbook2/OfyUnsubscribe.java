package guestbook2;
 
import com.googlecode.objectify.ObjectifyService;
import static com.googlecode.objectify.ObjectifyService.ofy;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
 
import java.io.IOException;
import java.util.Date;
 
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class OfyUnsubscribe extends HttpServlet {
	static {
        ObjectifyService.register(Email.class);
    }
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
                throws IOException {
        UserService userService = UserServiceFactory.getUserService();
        User user = userService.getCurrentUser();
 
        
        	
        String emailaddress = req.getParameter("emailunsubscribe");
        String firstname = req.getParameter("firstnameunsubscribe");
        String lastname = req.getParameter("lastnameunsubscribe");
        Email email = new Email(emailaddress, firstname, lastname);
        ofy().delete().entity(email).now(); 
        
 
        resp.sendRedirect("/unsubscribed.jsp");
        }
        

    }
