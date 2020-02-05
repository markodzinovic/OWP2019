package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.Karta;
import model.Korisnik;
import model.Projekcije;

public class KartaDAO {

	public static List<Karta> getAll() throws Exception{
		
		List<Karta> sveKarte = new ArrayList<Karta>();
		
		Connection conn = ConnectionManager.getConnection();
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		try {
			String upit = "SELECT * FROM karte";
			
			pstmt = conn.prepareStatement(upit);
			
			System.out.println(pstmt);
			
			rset = pstmt.executeQuery();
			
			while (rset.next()) {
				int index = 1;
				int idKarte = rset.getInt(index++);
				int idProjekcije = rset.getInt(index++);
				int sediste = rset.getInt(index++);
				String datum = rset.getString(index++);
				String korisnickoIme = rset.getString(index++);
				
				Projekcije p = ProjekcijeDAO.getProjekcija(idProjekcije);
				Korisnik k = KorisnikDAO.getKorisnik(korisnickoIme);
				
				Karta karta = new Karta(idKarte, p, sediste, datum, k);
				
				sveKarte.add(karta);
				
			}
			
		} finally {
			try {rset.close();} catch (Exception e1) {e1.printStackTrace();}
			try {pstmt.close();} catch (Exception e1) {e1.printStackTrace();}
			try {conn.close();} catch (Exception e1) {e1.printStackTrace();}
		}
		
		return sveKarte;
		
	}
	
}
