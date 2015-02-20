package guestbook2;

import java.io.IOException;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;
import java.util.Date;

import javax.mail.Address;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.cmd.Query;

import static com.googlecode.objectify.ObjectifyService.ofy;


@SuppressWarnings("serial")
public class CronServlet extends HttpServlet {
private static final Logger _logger = Logger.getLogger(CronServlet.class.getName());
//@SuppressWarnings("null")
public void doGet(HttpServletRequest req, HttpServletResponse resp)
throws IOException {

	Properties props = new Properties();
	Session session = Session.getDefaultInstance(props, null);
	
try {
_logger.info("Cron has been executed");

//get the current date
//Date currentdate = new Date();

Calendar cal = Calendar.getInstance();

//System.out.println("Current date: " + cal.getTime());
cal.add(Calendar.HOUR_OF_DAY, -24);
//System.out.println("24 hours back: " + cal.getTime());

//get list of emails
ObjectifyService.register(Email.class);
Query<Email> query = ofy().load().type(Email.class);
List<Email> emailList = query.list();

//get blog posts
ObjectifyService.register(Greeting.class);

List<Greeting> greetings = ObjectifyService.ofy().load().type(Greeting.class).list();   
Collections.sort(greetings);
//List<Greeting> send = null;

//List<Greeting> greetings = ObjectifyService.ofy().load().type(Greeting.class).filter("Date >", cal.getTime()).list();

String strCallResult = "";

for(Greeting send: greetings){
//	System.out.println("testseoise;f: " + x);
//	System.out.println("date of greeting: " + x.getDate());
//	System.out.println("24hr back: " + cal.getTime());
	if(send.getDate().after(cal.getTime())){
		//System.out.println("the IF statement works!");
		strCallResult += "Name: ";
		strCallResult += send.getUser() + "\r\n";
		strCallResult += "Date: ";
		strCallResult += send.getDate() + "\r\n";
		strCallResult += "Title: " + "\r\n";
		strCallResult += send.getTitle() + "\r\n"; 
		strCallResult += "Content: " + "\r\n";
		strCallResult += send.getContent() + "\r\n"; 
		strCallResult += "==============================================" + "\r\n";
	}
}

//if new posts were made, email has content, so start sending out emails
//otherwise, no emails will be sent
if(strCallResult != ""){
	strCallResult += "Thank you for subscribing to our daily blog updates!" + "\r\n";
	for(Email email: emailList){
		MimeMessage outMessage = new MimeMessage(session);
		outMessage.setFrom(new InternetAddress("admin@blogpostblogging.appspotmail.com"));
		outMessage.addRecipient(MimeMessage.RecipientType.TO, new InternetAddress(email.getEmail()));
		outMessage.setSubject("Daily Blog Updates");
		outMessage.setText(strCallResult);
		Transport.send(outMessage);
		}
}


//Body of the email
//
//for(Greeting body: send){
////strCallResult += "Testing" + "\r\n";
//strCallResult += "Name: ";
//strCallResult += body.getUser() + "\r\n";
//strCallResult += "Date: ";
//strCallResult += body.getDate() + "\r\n";
//strCallResult += "Title: " + "\r\n";
//strCallResult += body.getTitle() + "\r\n"; 
//strCallResult += "Body: " + "\r\n";
//strCallResult += body.getContent() + "\r\n"; 
//strCallResult += "==============================================" + "\r\n";
//}
//strCallResult += "Thank you for subscribing to our daily blog updates!" + "\r\n";

	
//Send out Email
//for(Email email: emailList){
//MimeMessage outMessage = new MimeMessage(session);
//outMessage.setFrom(new InternetAddress("admin@blogpostblogging.appspotmail.com"));
//outMessage.addRecipient(MimeMessage.RecipientType.TO, new InternetAddress(email.getEmail()));
//outMessage.setSubject("Daily Blog Updates");
//outMessage.setText(strCallResult);
//Transport.send(outMessage);
//}

}
catch (Exception ex) {
	_logger.info("ERROR: Could not send out Email Results response : " + ex.getMessage());
}

}

@Override
public void doPost(HttpServletRequest req, HttpServletResponse resp)
throws ServletException, IOException {
doGet(req, resp);
}

}