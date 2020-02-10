package servleti;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
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
			String idProjekcije = request.getParameter("idProjekcije");
			
			int idP = Integer.parseInt(idProjekcije);
			Projekcije p = ProjekcijeDAO.getProjekcija(idP);
			
			String datumIvreme = p.getDatum();
			String[] splitovan = datumIvreme.split(" ");
			
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");  
	    	LocalDate now = LocalDate.now();  
   	
	    	String proslost = "";
	    	
	    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	    	Date date1 = sdf.parse(splitovan[0]);
			Date date2 = sdf.parse(dtf.format(now));
			if(date1.before(date2)) {
				proslost = "proslost";
				System.out.println("Datum: "+date2 +" je pre :" +date1);
			}
	
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
			data.put("proslost", proslost);
			
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
		String idProjekcije = request.getParameter("idProjekcije");
		String sedisteString = request.getParameter("sediste");
		try {
			int id = Integer.parseInt(idProjekcije);
			int sediste = Integer.parseInt(sedisteString);
			Projekcije p = ProjekcijeDAO.getProjekcija(id);
			Korisnik k = KorisnikDAO.getKorisnik(ulogovan);
			
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");  
	    	LocalDateTime now = LocalDateTime.now();  
	    	String datum = dtf.format(now);
	    	System.out.println(datum);
			
			Karta karta = new Karta();
			karta.setProjekcija(p);
			karta.setDatum(datum);
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
