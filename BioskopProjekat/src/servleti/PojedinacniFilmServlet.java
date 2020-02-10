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
import dao.ProjekcijeDAO;
import model.Film;
import model.Korisnik;

/**
 * Servlet implementation class PojedinacniFilmServlet
 */
public class PojedinacniFilmServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PojedinacniFilmServlet() {
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
			Korisnik uloga = KorisnikDAO.getKorisnik(ulogovan);

			String idFilma = request.getParameter("idFilma");
			int id = Integer.parseInt(idFilma);

			
			Film film = FilmDAO.getFilm(id);
			Map<String, Object> data = new LinkedHashMap<>();
			data.put("film", film);
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
			case "izmena":
				String idFilma = request.getParameter("idFilma");
				int id = Integer.parseInt(idFilma);
				

				Film f =FilmDAO.getFilm(id);
				String naziv = request.getParameter("naziv");
				if(naziv.equals("")) {
					throw new Exception("Naizv filma je prazan");
				}
				String reziser = request.getParameter("reziser");
				if(reziser.equals("")) {
					throw new Exception("Reziser filma je prazan");
				}
				String glumci = request.getParameter("glumci");
				if(glumci.equals("")) {
					throw new Exception("Glumci filma je prazan");
				}
				String zanrovi = request.getParameter("zanrovi");
				if(zanrovi.equals("")) {
					throw new Exception("Zanrovi filma je prazan");
				}
				String trajanje = request.getParameter("trajanje");
				int traja = Integer.parseInt(trajanje);
				if(traja < 0) {
					throw new Exception("Trajanje filma treba da bude vece 0");
				}
				String distributer = request.getParameter("distributer");
				if(distributer.equals("")) {
					throw new Exception("Distributer filma je prazan");
				}
				String zemlja = request.getParameter("zemlja");
				if(zemlja.equals("")) {
					throw new Exception("Zemlja porekla filma je prazan");
				}
				String godina = request.getParameter("godina");
				int god = Integer.parseInt(godina);
				if(god < 0) {
					throw new Exception("Godina filma treba da bude vece 0");
				}
				String opis = request.getParameter("opis");
				if(opis.equals("")) {
					throw new Exception("Opis porekla filma je prazan");
				}
				
				f.setNaziv(naziv);
				f.setReziser(reziser);
				f.setGlumci(glumci);
				f.setZanrovi(zanrovi);
				f.setTrajanje(traja);
				f.setDistributer(distributer);
				f.setZemljaPorekla(zemlja);
				f.setGodinaProizvodnje(god);
				f.setOpis(opis);
				
				FilmDAO.izmenaFilma(f);
				request.getRequestDispatcher("./SuccessServlet").forward(request, response);
				break;

			case "brisanje":

					String idFilmaBrisanje = request.getParameter("idFilma");
					int idBrisanje = Integer.parseInt(idFilmaBrisanje);
					
					Film fa = ProjekcijeDAO.proveraFilmaUProjekcijama(idBrisanje);
					
					if(fa == null) {
						FilmDAO.brisanjeFilma(idBrisanje);
					}else {
						FilmDAO.logickoBrisanjeFilma(idBrisanje);
						throw new Exception("Film ima projekcije, obrisan je samo logicki");
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
