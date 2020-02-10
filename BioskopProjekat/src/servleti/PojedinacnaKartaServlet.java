package servleti;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.LinkedHashMap;
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
 * Servlet implementation class PojedinacnaKartaServlet
 */
public class PojedinacnaKartaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PojedinacnaKartaServlet() {
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

			String idKarte = request.getParameter("idKarte");
			int id = Integer.parseInt(idKarte);

			
			Karta karta = KartaDAO.getKarta(id);
			Map<String, Object> data = new LinkedHashMap<>();
			data.put("karta", karta);
			data.put("uloga", uloga.getUloga().toString());
			
			request.setAttribute("data", data);
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
		try {
			String idKarte = request.getParameter("idKarte");
			
			int id = Integer.parseInt(idKarte);
			Karta karta = KartaDAO.getKarta(id);
			
			String datumIvreme = karta.getProjekcija().getDatum();
			String[] splitovan = datumIvreme.split(" ");
			
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");  
	    	LocalDate now = LocalDate.now();  
	    	
	    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	    	Date date1 = sdf.parse(splitovan[0]);
			Date date2 = sdf.parse(dtf.format(now));
			if(date1.before(date2)) {
				throw new Exception("Karta je u proslosti i ne moze da se obrise");
			}else {
				KartaDAO.brisanjeKarte(id);
			}
			
			request.getRequestDispatcher("./SuccessServlet").forward(request, response);
				
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
