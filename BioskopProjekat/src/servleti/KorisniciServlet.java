package servleti;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.KorisnikDAO;
import model.Korisnik;

/**
 * Servlet implementation class KorisniciServlet
 */
public class KorisniciServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public KorisniciServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String ulogovan = (String) request.getSession().getAttribute("ulogovan");
		if(ulogovan == null) {
			
			request.getRequestDispatcher("./LogoutServlet").forward(request, response);
			return;
		}
		
		
		try {
			Korisnik uloga =KorisnikDAO.getKorisnik(ulogovan);
			if(uloga.isObrisan() == true) {
				request.getRequestDispatcher("./LogoutServlet").forward(request, response);
				return;
			}
			
			List<Korisnik> sviKorisnici = KorisnikDAO.getAll();
			
			Map<String, Object> data = new LinkedHashMap<>();
			data.put("sviKorisnici", sviKorisnici);
			data.put("uloga", uloga.getUloga().toString());
	
			request.setAttribute("data", data);
			request.getRequestDispatcher("./SuccessServlet").forward(request, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
