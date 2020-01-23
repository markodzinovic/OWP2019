package servleti;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.FilmDAO;
import model.Film;

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

		String idFilma = request.getParameter("idFilma");
		int id = Integer.parseInt(idFilma);
		
		try {
			Film film = FilmDAO.getFilm(id);
			Map<String, Object> data = new LinkedHashMap<>();
			data.put("film", film);

			
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
					
					FilmDAO.brisanjeFilma(idBrisanje);
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
