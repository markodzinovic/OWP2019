package servleti;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ConnectionManager;
import dao.KartaDAO;
import dao.KorisnikDAO;
import model.Karta;
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
		
		String ulogovan = (String) request.getSession().getAttribute("ulogovan");
		if(ulogovan == null) {
			request.getRequestDispatcher("./LogoutServlet").forward(request, response);
			return;
		}
		
		try {
			Korisnik uloga = KorisnikDAO.getKorisnik(ulogovan);
			
			String korisnickoIme = request.getParameter("korisnickoIme");
			System.out.println("Ovo je korisnicko ime: " + korisnickoIme );
			
			Korisnik korisnik = KorisnikDAO.getKorisnik(korisnickoIme);
			System.out.println(korisnik.getDatumRegistracije());
			
			Map<String, Object> data = new LinkedHashMap<>();
			data.put("korisnik", korisnik);
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
		String ulogovan = (String) request.getSession().getAttribute("ulogovan");
		
		if(ulogovan == null) {
			request.getRequestDispatcher("./LogoutServlet").forward(request, response);
			return;
		}
		
		try {
			String akcija = request.getParameter("akcija");
			switch (akcija) {
			case "izmena":
				String korisnickoIme = request.getParameter("korisnickoIme");
				
				Korisnik k1 = KorisnikDAO.getKorisnik(ulogovan);
				if(k1.getKorisnickoIme().equals(korisnickoIme)) {
					throw new Exception("Ne mozete sebi menjati ulogu");
				}

				Korisnik kI = KorisnikDAO.getKorisnik(korisnickoIme);
					
				String uloga = request.getParameter("uloga");

				kI.setUloga(Uloga.valueOf(uloga));
				
				KorisnikDAO.izmenaAdmin(kI);
				
				request.getRequestDispatcher("./SuccessServlet").forward(request, response);
				break;

			case "brisanje":
				String korisnickoImeBrisanje = request.getParameter("korisnickoIme");
				Korisnik k = KorisnikDAO.getKorisnik(ulogovan);
				
				if(k.getKorisnickoIme().equals(korisnickoImeBrisanje)) {
					throw new Exception("Ne mozete obrisati sebe");
				}
				List<Karta> sveKorisnickeKarte = KartaDAO.karteJednogKorisnika(korisnickoImeBrisanje);
				System.out.println(sveKorisnickeKarte);
				if(!sveKorisnickeKarte.isEmpty()) {
					KorisnikDAO.logickoBrisanjeKorisnika(korisnickoImeBrisanje);
					throw new Exception("Korisnik ima karte, brisanje je samo logicko");
				}
				if(sveKorisnickeKarte.isEmpty()) {
					KorisnikDAO.brisanjeKorisnika(korisnickoImeBrisanje);
				}

				request.getRequestDispatcher("./SuccessServlet").forward(request, response);	
				break;
			}
			
		} catch (Exception e) {
			String poruka = e.getMessage();
			if(poruka == null) {
				poruka = "Nepredvidjena greska";
				e.printStackTrace();
			}
			Map<String, Object> data = new LinkedHashMap<>();
			data.put("poruka", poruka);
			
			request.setAttribute("data", data);
			request.getRequestDispatcher("./FailureServlet").forward(request, response);	
		}
	}
}
