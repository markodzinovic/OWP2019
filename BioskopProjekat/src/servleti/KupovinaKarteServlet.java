package servleti;

import java.io.IOException;
import java.util.ArrayList;
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
 * Servlet implementation class KupovinaKarteServlet
 */
public class KupovinaKarteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public KupovinaKarteServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		try {
			String idProjekcije = request.getParameter("idProjekcije");
			
			int idP = Integer.parseInt(idProjekcije);
			Projekcije p = ProjekcijeDAO.getProjekcija(idP);
			
			List<Integer> slobodnaSedista = new ArrayList<>();
			Sedista s = SedisteDAO.brojSedista(p.getSala().getId());
			List<Karta> zauzetaSedista = KartaDAO.zauzetaSedistaZaProjekciju(p.getId());
			
			for (int i = 1; i <= s.getRedniBroj(); i++) {				

					if(validacijaSedista(i, zauzetaSedista)) {
						System.out.println("Zauzeta sedista: "+i);
						continue;
					}else {
						System.out.println("Slobodna sedista: "+i);
						slobodnaSedista.add(i);
					}
				
			}
			
			Map<String, Object> data = new LinkedHashMap<>();
			data.put("projekcija", p);
			data.put("ss", slobodnaSedista);

			
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
		String idProjekcije = request.getParameter("idProjekcije");
		String sedisteString = request.getParameter("sediste");
		try {
			int id = Integer.parseInt(idProjekcije);
			int sediste = Integer.parseInt(sedisteString);
			Projekcije p = ProjekcijeDAO.getProjekcija(id);
			Korisnik k = KorisnikDAO.getKorisnik("c");
			
			Karta karta = new Karta();
			karta.setProjekcija(p);
			karta.setDatum("15.02.2020");
			karta.setSediste(sediste);
			karta.setKorisnik(k);
			
			KartaDAO.dodavanjeKarte(karta);
			
			request.getRequestDispatcher("./SuccessServlet").forward(request, response);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private  boolean validacijaSedista(int i, List<Karta> sveKarte) {
		
		for (Karta k : sveKarte) {
			if(k.getSediste() == i) {
				return true;
			}
		}
		return false;
		
	}

}
