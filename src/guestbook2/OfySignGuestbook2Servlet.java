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

public class OfySignGuestbook2Servlet extends HttpServlet {
	static {
        ObjectifyService.register(Greeting.class);
    }
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
                throws IOException {
        UserService userService = UserServiceFactory.getUserService();
        User user = userService.getCurrentUser();
 
        if(user != null){
        // We have one entity group per Guestbook with all Greetings residing
        // in the same entity group as the Guestbook to which they belong.
        // This lets us run a transactional ancestor query to retrieve all
        // Greetings for a given Guestbook.  However, the write rate to each
        // Guestbook should be limited to ~1/second.
        //String guestbookName = req.getParameter("guestbookName");
        //Key guestbookKey = KeyFactory.createKey("Guestbook", guestbookName);
        String content = req.getParameter("content");
        String title = req.getParameter("title");
        //Date date = new Date();
        Greeting greeting = new Greeting(user, content, title);
        ofy().save().entity(greeting).now(); 
        //greeting.setProperty("user", user);
        //greeting.setProperty("date", date);
        //greeting.setProperty("content", content);
 
        //DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        //datastore.put(greeting);
 
        resp.sendRedirect("/ofyguestbook2.jsp");
        }
        
        else{
        	resp.sendRedirect("/mustlogin.jsp");
        }
    }
}