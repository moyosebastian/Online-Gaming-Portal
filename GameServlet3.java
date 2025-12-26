import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GameServlet3 extends HttpServlet {
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws 
	ServletException, IOException{
		
		HttpSession session = request.getSession();
		String userName = (String) session.getAttribute("userName");
		
		if(userName = null) {
			response.sendRedict("login.html");
			return;
		}
		
		response.sendRedirect("home.html");
	}

}
