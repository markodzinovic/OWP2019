package servleti;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.FilmDAO;
import dao.KorisnikDAO;
import model.Film;
import model.Korisnik;

/**
 * Servlet implementation class DodavanjeFilmaServlet
 */
public class DodavanjeFilmaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DodavanjeFilmaServlet() {
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
			
			Map<String, Object> data = new LinkedHashMap<>();
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
			String akcija = request.getParameter("akcija");
			switch (akcija) {
			case "dodavanje":
				String nazivD = request.getParameter("nazivD");
				if(nazivD.equals("")) {
					throw new Exception("Naizv filma je prazan");
				}
				String reziserD = request.getParameter("reziserD");
				if(reziserD.equals("")) {
					throw new Exception("Reziser filma je prazan");
				}
				String glumciD = request.getParameter("glumciD");
				if(glumciD.equals("")) {
					throw new Exception("Glumci filma je prazan");
				}
				String zanroviD = request.getParameter("zanroviD");
				if(zanroviD.equals("")) {
					throw new Exception("Zanrovi filma je prazan");
				}
				String trajanjeD = request.getParameter("trajanjeD");
				int trajaD = Integer.parseInt(trajanjeD);
				if(trajaD < 0) {
					throw new Exception("Trajanje filma treba da bude vece 0");
				}
				String distributerD = request.getParameter("distributerD");
				if(distributerD.equals("")) {
					throw new Exception("Distributer filma je prazan");
				}
				String zemljaD = request.getParameter("zemljaD");
				if(zemljaD.equals("")) {
					throw new Exception("Zemlja porekla filma je prazan");
				}
				String godinaD = request.getParameter("godinaD");
				int godD = Integer.parseInt(godinaD);
				if(godD < 0) {
					throw new Exception("Godina filma treba da bude vece 0");
				}
				String opisD = request.getParameter("opisD");
				if(opisD.equals("")) {
					throw new Exception("Opis porekla filma je prazan");
				}

				Film film = new Film();
				film.setNaziv(nazivD);
				film.setReziser(reziserD);
				film.setGlumci(glumciD);
				film.setZanrovi(zanroviD);
				film.setTrajanje(trajaD);
				film.setDistributer(distributerD);
				film.setZemljaPorekla(zemljaD);
				film.setGodinaProizvodnje(godD);
				film.setOpis(opisD);
				
				FilmDAO.dodavanjeFilma(film);
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
