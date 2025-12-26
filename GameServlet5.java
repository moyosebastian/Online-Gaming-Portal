import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GameServlet5 extends HttpServlet {
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws 
	ServletException, IOException{
		
	
		String amountString = request.getParameter("amount");
		int amountToSpend = Integer.parseInt(amountString);
		
		HttpSession session = request.getSession();
		String userName = (String) session.getAttribute("userName");
		Integer currentCredits = (Integer) session.getAttribute("credits");
		Integer playerId = (Integer) session.getAttribute("playerId");
		
		if(userName == null) {
			response.sendRedirect("login.html");
			return;
		}
		
		if(amountToSpend > currentCredits) {
			response.sendRedirect("home.html?error=insufficientCredits&attempted="+ amountToSpend + "&current=" + currentsCredits);
			return;
		}
			
			Connection connection = null;
			
			try {
				connection = DriverManager.getConnection(
						"jdbc:mysql://localhost:3306/gameportal?serverTimezone=UTC","root", "root");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			try {
				PreparedStatement updateCredits = connection.prepareStatement(
						"UPDATE players SET credits = credits" + "? WHERE id = ?");
				updateCredits.setInt(1, amountToSpend);
				updateCredits.setInt(2, playerId);
				int update = updateCredits.executeUpdate();
				updateCredits.close();
			}catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			int newCredits = currentCredits - amountToSpend;
			session.setAttribute("credits", newCredits);
			
			response.sendRedirect("home.html?message=added&amount=" + amountToSpend + "&balance=" + newCredits);
		}
	}	
		
}


