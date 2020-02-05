package servleti;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.sun.org.apache.xalan.internal.xsltc.compiler.sym;

import dao.ConnectionManager;
import dao.FilmDAO;
import dao.KartaDAO;
import dao.KorisnikDAO;
import dao.ProjekcijeDAO;
import dao.SalaDao;
import dao.TipoviProjekcijaDAO;
import model.Film;
import model.Karta;
import model.Korisnik;
import model.Projekcije;
import model.Sala;
import model.TipoviProjekcije;

/**
 * Application Lifecycle Listener implementation class InitListener
 *
 */
public class InitListener implements ServletContextListener {

    /**
     * Default constructor. 
     */
    public InitListener() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent arg0)  { 
         // TODO Auto-generated method stub
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent arg0)  { 
    	System.out.println("inicijalizacija...");

    	ConnectionManager.open();
    	
		System.out.println("završeno!");
    }
	
}
