package session;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

@WebServlet("/loginServlet")
public class SessionStart extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public String oldUser = "###"; 

    public SessionStart() {
        super();
       
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
        String password = request.getParameter("password");
         
        System.out.println("username: " + username);
        System.out.println("password: " + password);
       
        HttpSession session = request.getSession(true);
        
        Date createTime = new Date(session.getCreationTime());
           
        Date lastAccessTime = new Date(session.getLastAccessedTime());

        String title = "Continuing Session";
        String visitCountKey = new String("visitCount");
        String userIDKey = new String("userID");
        Integer visitCount = new Integer(0);

        if (session.isNew()) {
           title = "New Session";
           session.setAttribute(userIDKey, username);
           oldUser = username;
        } 
        else {
           visitCount = (Integer)session.getAttribute(visitCountKey);
           visitCount = visitCount + 1;
        }
        session.setAttribute(visitCountKey,  visitCount);

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String docType =
           "<!doctype html public \"-//w3c//dtd html 4.0 " +
           "transitional//en\">\n";

        out.println(docType +
           "<html>\n" +
              "<head><title>" + title + "</title></head>\n" +
              
              "<body bgcolor = \"#f0f0f0\">\n" +
                 "<h1 align = \"center\">" + title + "</h1>\n" +
                 "<h2 align = \"center\">Session Infomation</h2>\n" +
                 "<table border = \"1\" align = \"center\">\n" +
                    
                    "<tr bgcolor = \"#949494\">\n" +
                       "  <th>Session info</th><th>value</th>"+
                    "</tr>\n" +
                       
                    "<tr>\n" +
                       "  <td>id</td>\n" +
                       "  <td>" + session.getId() + "</td>"+
                    "</tr>\n" +
                    
                    "<tr>\n" +
                       "  <td>Creation Time</td>\n" +
                       "  <td>" + createTime + "  </td>"+
                    "</tr>\n" +
                    
                    "<tr>\n" +
                       "  <td>Time of Last Access</td>\n" +
                       "  <td>" + lastAccessTime + "  </td>"+
                    "</tr>\n" +
                    
                    "<tr>\n" +
                       "  <td>User ID</td>\n" +
                       "  <td>" + username + "  </td>"+
                    "</tr>\n" +
                    
                    "<tr>\n" +
                       "  <td>Number of visits</td>\n" +
                       "  <td>" + visitCount + "</td>"+
                    "</tr>\n" +
                 "</table>\n" +
              "</body>"+
           "</html>"
        );
         
		doGet(request, response);
	}

}
