import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GameServlet1 extends HttpServlet {
	

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws 
	ServletException, IOException{
		
		// Get the user's input from the HTML Page
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");
		String confirmPassword = request.getParameter("confirmPassword");
	
		//check if the password matches
		if(!password.equals(confirmPassword))
		{
			response.sendRedirect("register.html?error=passwordMismatch");
		}
		
		//
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/gameportal?serverTimezone=UTC","root", "root");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			PreparedStatement registerPlayer = connection.prepareStatement(
					"INSERT into players "
					+ "(userName, password, credits)" +" VALUES (?, ?, ?)");
					//pass in the values as parameters
			
			registerPlayer.setString(1, userName);
			registerPlayer.setString(2, password);
			registerPlayer.setInt(3, 500);
			int rowsUpdated = registerPlayer.executeUpdate();
			registerPlayer.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
			response.sendRedirect("register.html?error=databaseError");
			return;
		}
		
		//points them to the login page
		response.sendRedirect("login.html?success=registered");
	

 }
}


