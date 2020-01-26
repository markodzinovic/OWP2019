package servleti;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.KorisnikDAO;
import jdk.nashorn.internal.ir.RuntimeNode.Request;
import model.Korisnik;

/**
 * Servlet implementation class LoginServlet
 */
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String korisnickoIme = request.getParameter("korisnickoIme");
		String lozinka = request.getParameter("lozinka");
		
		try {
			Korisnik kor = KorisnikDAO.get(korisnickoIme, lozinka);
			if (kor == null) {
				request.getRequestDispatcher("./FailureServlet").forward(request, response);
				return;
			}
			
			request.getSession().setAttribute("ulogovan", kor.getKorisnickoIme());
			
			request.getRequestDispatcher("./SuccessServlet").forward(request, response);
			
//			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	
		
	}

}
