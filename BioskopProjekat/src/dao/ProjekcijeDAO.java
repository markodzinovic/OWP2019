package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.Film;
import model.Korisnik;
import model.Projekcije;
import model.Sala;
import model.TipoviProjekcije;

public class ProjekcijeDAO {
	
	public static Projekcije getProjekcija(int id) throws Exception{
		
		Connection conn = ConnectionManager.getConnection();
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		try {
			String upit = "SELECT id,nazivFilma,tipProjekcije,sala,datum,cena,admin,obrisan FROM projekcije WHERE id = ?";
			
			pstmt = conn.prepareStatement(upit);
			pstmt.setInt(1, id);
			System.out.println(pstmt);
			
			rset = pstmt.executeQuery();
			if(rset.next()) {
				int index = 1;
				int idProjekcije = rset.getInt(index++);
				int idFilma = rset.getInt(index++);
				int idTipaProjekcije = rset.getInt(index++);
				int idSale = rset.getInt(index++);
				String datum = rset.getString(index++);
				double cena = rset.getDouble(index++);
				String korisnickoImeAdmina = rset.getString(index++);
				boolean obrisan = rset.getBoolean(index++);
				
				Film naziv = FilmDAO.getFilm(idFilma);
				TipoviProjekcije tip = TipoviProjekcijaDAO.getTip(idTipaProjekcije);
				Sala sala = SalaDao.getSala(idSale);
				Korisnik admin = KorisnikDAO.getKorisnik(korisnickoImeAdmina);
				
				return new Projekcije(idProjekcije, naziv, tip, sala, datum, cena, admin, obrisan);
				
			}
			
		} finally {
			try {pstmt.close();} catch (Exception e1) {e1.printStackTrace();}
			try {rset.close();} catch (Exception e1) {e1.printStackTrace();}
			try {conn.close();} catch (Exception e1) {e1.printStackTrace();}
		}
		
		
		return null;
	}
	
	
	public static List<Projekcije> getAll() throws Exception {
		List<Projekcije> sveProjekcije = new ArrayList<>();
		
		Connection conn = ConnectionManager.getConnection();
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		try {
			String upit = "SELECT id,nazivFilma,tipProjekcije,sala,datum,cena,admin,obrisan FROM projekcije";
			
			pstmt = conn.prepareStatement(upit);
			
			System.out.println(pstmt);
			
			rset = pstmt.executeQuery();
			while (rset.next()) {
				int index = 1;
				int idProjekcije = rset.getInt(index++);
				int idFilma = rset.getInt(index++);
				int idTipaProjekcije = rset.getInt(index++);
				int idSale = rset.getInt(index++);
				String datum = rset.getString(index++);
				double cena = rset.getDouble(index++);
				String korisnickoImeAdmina = rset.getString(index++);
				boolean obrisan = rset.getBoolean(index++);
				
				Film naziv = FilmDAO.getFilm(idFilma);
				TipoviProjekcije tip = TipoviProjekcijaDAO.getTip(idTipaProjekcije);
				Sala sala = SalaDao.getSala(idSale);
				Korisnik admin = KorisnikDAO.getKorisnik(korisnickoImeAdmina);
				
				Projekcije p = new Projekcije(idProjekcije, naziv, tip, sala, datum, cena, admin, obrisan);
				sveProjekcije.add(p);
				
			}
			
		} finally {
			try {pstmt.close();} catch (Exception e1){e1.printStackTrace();}
			try {rset.close();} catch (Exception e1){e1.printStackTrace();}
			try {conn.close();} catch (Exception e1){e1.printStackTrace();}
		}
		
		return sveProjekcije;
	}
	

}
