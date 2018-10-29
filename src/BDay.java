import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Properties;
import java.util.Random;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class BDay 
{
	public static void main(String[] args) throws SQLException, ClassNotFoundException, FileNotFoundException 
	{		
			DateFormat dd = new SimpleDateFormat("dd-MM");
			Calendar caldd = Calendar.getInstance();
			String date = dd.format(caldd.getTime());
			
			System.out.println(date);
			
			final String JDBC_DRIVER = "oracle.jdbc.driver.OracleDriver";
		    String DB_URL = "jdbc:oracle:thin:@localhost:1521:xe";

		    // Database credentials
		    final String USER = "system";//getGlobalVariable("DB_Username");
		    final String PASS = "root";//getGlobalVariable("DB_Password");
	        Connection con = null;  
	        Class.forName(JDBC_DRIVER);
	        con = DriverManager.getConnection(DB_URL, USER, PASS);
			
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select * from friends"); 

			while(rs.next())  
			{
				String db_date = rs.getString("DOB");
				String name = rs.getString("NAME");
				String email = rs.getString("EMAIL");
				
				if(db_date.equals(date))
					send(email, name);
			}
		con.close(); 
	}

	public static void send(String mail, String name) throws java.io.FileNotFoundException, FileNotFoundException
	{
		  ArrayList<String> img = new ArrayList<String>();
		  img.add("https://s19.postimg.cc/9p60sh3ar/Happy-birthday-7.jpg");
		  img.add("https://s19.postimg.cc/mfa95kb8z/Happy-birthday-6.jpg");
		  img.add("https://s19.postimg.cc/anmdurgmr/HappyBirthday-3.jpg");
		  img.add("https://s19.postimg.cc/jqav1sgdv/Happy-Birthday-5.jpg");
		  img.add("https://s19.postimg.cc/km7ch8q2b/HappyBirthday-4.jpg");
		  img.add("https://s19.postimg.cc/r9zcq3k6r/HappyBirthday-Plain.jpg");
		  img.add("https://s19.postimg.cc/cpi9v9p83/Happy-Birthday-With-Quotes-1.gif");
		  
		  int l = img.size();
		 
		  String host = "";//email host like google server or something
	      String to = mail;//change accordingly
	      final String user="";//username of the email from which you want to send the email
	      final String password="";//password for your email account

	      Properties properties = System.getProperties();
	      properties.setProperty("mail.smtp.host", host);

	      Session session = Session.getDefaultInstance(properties, new javax.mail.Authenticator() 
	      {
	    	  protected PasswordAuthentication getPasswordAuthentication() 
	    	  {
	    		  return new PasswordAuthentication(user,password);
	    	  }
	      });
	      
	      try
	      {
	         MimeMessage message = new MimeMessage(session);
	         message.setFrom(new InternetAddress(user));
	         message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
	         message.setSubject("Wish you a very happy birthday "+name);
	 		 int rand = new Random().nextInt(l);
	 		 String content = "<!DOCTYPE html><html><head><meta name=\"viewport\"content=\"width=device-width, initial-scale=1\"><style>div.a{text-align:center;}img{display:block;margin-left:auto;margin-right:auto;}</style></head><body><div class=\"a\"><h2><font face=\"Comic Sans MS\" color=\"green\">Dear "+name+"</font></h2><img src=\""+img.get(rand)+"\" alt=\"Paris\" style=\"width:75%;\"><div class=\"a\"><h2><font face=\"Comic Sans MS\" color=\"green\">From the SEQA Family</font></h2></body></html>";
			 System.out.println(content);
	         message.setContent(content,"text/html");
	         Transport.send(message);
	         System.out.println("Wish sent!!!!!!");
	      }
	      catch (MessagingException ex) 
	      {
	    	  ex.printStackTrace();
	    	  }
	}

}
