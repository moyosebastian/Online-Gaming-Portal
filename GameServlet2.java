

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GameServlet2 extends HttpServlet {
	

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws 
	ServletException, IOException{
		
		// user inputs login details
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");
	
		
		//
		Connection connection = null;
		boolean loginSuccess = false;
		int credits = 0;
		int playerId = 0;
		
		try {
			connection = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/gameportal?serverTimezone=UTC","root", "root");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			PreparedStatement checkLogin = connection.prepareStatement(
					"Select id, credits FROM players WHERE userName = ? AND password = ? ");
					
			
			checkLogin.setString(1, userName);
			checkLogin.setString(2, password);
			ResultSet result = checkLogin.executeQuery();
			
			if(result.next()) {
				loginSuccess = true;
				playerId = result.getInt("id");
				credits = result.getInt("credits");
			}
			checkLogin.close();
		}catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		if(loginSuccess) {
			HttpSession session = request.getSession();
			session.setAttribute("userName", userName);
			session.setAttribute("credits", credits);
			session.setAttribute("playerId", playerId);
			
			response.sendRedirect("home.html");
		}else {
			response.sendRedirect("login.html?error=invalidLogin");
		}
		//points to the login page
		response.sendRedirect("login.html?error=invalidLogin");
	

 }
}

