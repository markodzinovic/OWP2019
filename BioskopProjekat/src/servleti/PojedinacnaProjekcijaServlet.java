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
import dao.ProjekcijeDAO;
import dao.SedisteDAO;
import model.Karta;
import model.Korisnik;
import model.Projekcije;
import model.Sedista;

/**
 * Servlet implementation class PojedinacnaProjekcijaServlet
 */
public class PojedinacnaProjekcijaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PojedinacnaProjekcijaServlet() {
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
			
			String idProjekcije = request.getParameter("idProjekcije");
			int id = Integer.parseInt(idProjekcije);
			
			Projekcije p = ProjekcijeDAO.getProjekcija(id);
			List<Karta> sveKarteZaProjekciju = KartaDAO.proveraProjekcijeUKartama(id);
			
			Sedista sed = SedisteDAO.brojSedista(p.getSala().getId());
			int slobodnaSedista = sed.getRedniBroj() - sveKarteZaProjekciju.size();
			
			Map<String, Object> data = new LinkedHashMap<>();
			data.put("uloga", uloga.getUloga().toString());
			data.put("projekcija", p);
			data.put("sveKarteZaProjekciju", sveKarteZaProjekciju);
			data.put("slobodnaSedista", slobodnaSedista);
			
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
			case "brisanje":
				String idProjekcije = request.getParameter("idProjekcije");
				int id = Integer.parseInt(idProjekcije);
				
				List<Karta> pr = KartaDAO.proveraProjekcijeUKartama(id);
				if(pr.isEmpty()) {
					ProjekcijeDAO.brisanjeProjekcije(id);
				}else {
					ProjekcijeDAO.logickoBrisanjeProjekcije(id);
					throw new Exception("Projekcija ima kupljene karte, logicki obrisana");
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
