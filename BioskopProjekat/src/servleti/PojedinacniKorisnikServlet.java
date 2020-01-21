package servleti;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.KorisnikDAO;
import model.Korisnik;
import model.Korisnik.Uloga;

/**
 * Servlet implementation class PojedinacniKorisnikServlet
 */
public class PojedinacniKorisnikServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PojedinacniKorisnikServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String korisnickoIme = request.getParameter("korisnickoIme");
		System.out.println("Ovo je korisnicko ime: " + korisnickoIme );
		
		try {
			Korisnik korisnik = KorisnikDAO.getKorisnik(korisnickoIme);
			System.out.println(korisnik.getDatumRegistracije());
			
			Map<String, Object> data = new LinkedHashMap<>();
			data.put("korisnik", korisnik);
			
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
		String akcija = request.getParameter("akcija");
		switch (akcija) {
		case "izmena":
			String korisnickoIme = request.getParameter("korisnickoIme");
			try {
				Korisnik k = KorisnikDAO.getKorisnik(korisnickoIme);
				
				String uloga = request.getParameter("uloga");
				
				
				k.setUloga(Uloga.valueOf(uloga));
				System.out.println(k.getUloga());
				request.getRequestDispatcher("./SuccessServlet").forward(request, response);
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			break;

		case "brisanje":
			String korisnickoImeBrisanje = request.getParameter("korisnickoIme");
			
			try {
				Korisnik k = KorisnikDAO.getKorisnik(korisnickoImeBrisanje);
				
				request.getRequestDispatcher("./SuccessServlet").forward(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			break;
		}
	}

}
