package servleti;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import dao.FilmDAO;
import dao.KorisnikDAO;
import dao.ProjekcijeDAO;
import dao.SalaDao;
import dao.TipoviProjekcijaDAO;
import model.Film;
import model.Korisnik;
import model.Projekcije;
import model.Sala;
import model.TipoviProjekcije;

/**
 * Servlet implementation class DodavanjeProjekcijeServlet
 */
public class DodavanjeProjekcijeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DodavanjeProjekcijeServlet() {
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
			Korisnik k = KorisnikDAO.getKorisnik(ulogovan);
			
			
			List<Film> sviFilmovi = FilmDAO.getAll();
			List<Sala> sveSale = SalaDao.getAll();
			
			Map<String, Object> data = new LinkedHashMap<>();
			data.put("uloga", k.getUloga().toString());
			data.put("sviFilmovi", sviFilmovi);
			data.put("sveSale", sveSale);
			
			request.setAttribute("data", data);
			request.getRequestDispatcher("./SuccessServlet").forward(request, response);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
			
		String akcija = request.getParameter("akcija");
		try {
		switch (akcija) {
			case "dodavanje":
				String f = request.getParameter("film");
				if(f.equals("")) {
					throw new Exception("Niste izabrali film");
				}
				Film film = FilmDAO.getFilm(Integer.parseInt(f));
				System.out.println(film.getNaziv());
				
				String datum = request.getParameter("datum");
				if(datum.equals("")) {
					throw new Exception("Datum je prazan");
				}
				String vreme = request.getParameter("vreme");
				if(vreme.equals("")) {
					throw new Exception("Vreme je prazno");
				}
				
				String s = request.getParameter("sala");
				if(s.equals("") || s.equals("0")) {
					throw new Exception("Sala nije izabrana");
				}
				Sala sala = SalaDao.getSala(Integer.parseInt(s));
				System.out.println(sala.getNaziv());
				
				String t = request.getParameter("tipProjekcije");
				if(t.equals("")) {
					throw new Exception("Tip projekcije nije izabran");
				}
				TipoviProjekcije tip = TipoviProjekcijaDAO.getTip(Integer.parseInt(t));
				System.out.println(tip.getNaziv());
				
				double cena = Double.parseDouble(request.getParameter("cena"));
				if(cena < 0) {
					throw new Exception("Cena ne sme biti manja od nule");
				}
				
				String datumIvreme = datum+ " " + vreme;
				List<Projekcije> projek = ProjekcijeDAO.getAll();
				
				if(zauzetaSala(sala.getId(), datumIvreme, projek)) {
					throw new Exception("Sala: "+sala.getNaziv()+", je zauzeta u to vreme" +datumIvreme);
				}
				
				
				Projekcije projekcija = new Projekcije();
				projekcija.setNazivFilma(film);
				projekcija.setDatum(datumIvreme);
				projekcija.setSala(sala);
				projekcija.setTipProjekcije(tip);
				Korisnik admin = KorisnikDAO.getKorisnik("a");
				projekcija.setAdministrator(admin);
				projekcija.setCena(cena);
				
				ProjekcijeDAO.dodavanjeProjekcije(projekcija);
				
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
	
	public boolean zauzetaSala(int idSale, String datumIvreme, List<Projekcije> projekcije) {
		
		for (Projekcije p : projekcije) {
			if(p.getSala().getId() == idSale) {
				if(p.getDatum().equals(datumIvreme)) {
					return true;
				}else {
					continue;
				}
			}
		}
		
		return false;
		
	}

}
