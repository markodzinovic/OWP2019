package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.Film;

public class FilmDAO {
	
	public static List<Film> getAll() throws Exception{
		
		List<Film> sviFilmovi = new ArrayList<>();
		
		Connection conn = ConnectionManager.getConnection();
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		try {
			String upit = "SELECT * FROM film";
			
			pstmt = conn.prepareStatement(upit);
			
			rset = pstmt.executeQuery();
			while(rset.next()) {
				int index = 1;
				int id = rset.getInt(index++);
				String naziv = rset.getString(index++);
				String reziser = rset.getString(index++);
				String glumci = rset.getString(index++);
				String zanrovi = rset.getString(index++);
				int trajanje = rset.getInt(index++);
				String distributer = rset.getString(index++);
				String zemljaPorekla = rset.getString(index++);
				int godina = rset.getInt(index++);
				String opis = rset.getString(index++);
				boolean obrisan = rset.getBoolean(index++);
				
				Film f = new Film(id, naziv, reziser, glumci, zanrovi, trajanje, distributer, zemljaPorekla, godina, opis, obrisan);
				sviFilmovi.add(f);
			}
			
		} finally {
			try {pstmt.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {rset.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {conn.close();} catch (Exception ex1) {ex1.printStackTrace();} 
		}
		System.out.println(sviFilmovi);
		return sviFilmovi;
		
	}

}
