package servleti;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.FilmDAO;
import dao.ProjekcijeDAO;
import model.Film;
import model.Projekcije;

/**
 * Servlet implementation class PocetnaStranicaServlet
 */
public class PocetnaStranicaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PocetnaStranicaServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {

			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");  
        	LocalDate now = LocalDate.now();
        	System.out.println(dtf.format(now));
        	List<Projekcije> sveProjekcije = ProjekcijeDAO.getDanasnjeProjekcije(dtf.format(now));
        	
			List<Film> sviFilmovi = FilmDAO.getAll();
			
			Map<String, Object> data = new LinkedHashMap<>();
			data.put("sveProjekcije", sveProjekcije);
			data.put("sviFilmovi", sviFilmovi);
			
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
		doGet(request, response);
	}

}
