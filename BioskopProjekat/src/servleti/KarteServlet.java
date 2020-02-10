package servleti;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.KartaDAO;
import dao.KorisnikDAO;
import model.Karta;
import model.Korisnik;

/**
 * Servlet implementation class KarteServlet
 */
public class KarteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public KarteServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
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
			
			if(uloga.getUloga().toString().equals("KORISNIK")) {
				List<Karta> sveKarte = KartaDAO.karteJednogKorisnika(uloga.getKorisnickoIme());
				
				Map<String, Object> data = new LinkedHashMap<>();
				data.put("uloga", uloga.getUloga().toString());
				data.put("sveKarte", sveKarte);
				request.setAttribute("data", data);
			}else {
				List<Karta> sveKarte = KartaDAO.getAll();
				
				Map<String, Object> data = new LinkedHashMap<>();
				data.put("uloga", uloga.getUloga().toString());
				data.put("sveKarte", sveKarte);
				request.setAttribute("data", data);
			}

			request.getRequestDispatcher("./SuccessServlet").forward(request, response);
		} catch (Exception e) {
			// TODO: handle exception
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
