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
	
	public static Film getFilm(int id) throws Exception{
		
		Connection conn = ConnectionManager.getConnection();
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		try {
			
			String upit = "SELECT id,naziv,reziser,glumci,zanrovi,trajanje,distributer,zemljaPorekla,godina,opis,obrisan FROM film WHERE id = ?";
			
			pstmt = conn.prepareStatement(upit);
			pstmt.setInt(1, id);
			
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				int index = 1;
				int idF = rset.getInt(index++);
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
				
				return new Film(idF, naziv, reziser, glumci, zanrovi, trajanje, distributer, zemljaPorekla, godina, opis, obrisan);
				
				
			}
			
			
		} finally {
			try {pstmt.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {rset.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {conn.close();} catch (Exception ex1) {ex1.printStackTrace();}
		}
		
		return null;
	}
	
	public static boolean dodavanjeFilma(Film film) throws Exception{
		
		Connection conn = ConnectionManager.getConnection();
		
		PreparedStatement pstmt = null;
		
		try {
			String upit = "INSERT INTO film (naziv,reziser,glumci,zanrovi,trajanje,distributer,zemljaPorekla,godina,opis,obrisan)"
					+ "VALUES (?,?,?,?,?,?,?,?,?,?)";
			
			pstmt = conn.prepareStatement(upit);
			int index = 1;
			pstmt.setString(index++, film.getNaziv());
			pstmt.setString(index++, film.getReziser());
			pstmt.setString(index++, film.getGlumci());
			pstmt.setString(index++, film.getZanrovi());
			pstmt.setInt(index++, film.getTrajanje());;
			pstmt.setString(index++, film.getDistributer());
			pstmt.setString(index++, film.getZemljaPorekla());
			pstmt.setInt(index++, film.getGodinaProizvodnje());
			pstmt.setString(index++, film.getOpis());
			pstmt.setBoolean(index++, film.isObrisan());
			
			System.out.println(pstmt);
			
			return pstmt.executeUpdate() == 1;
			
		} finally {
			try {pstmt.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {conn.close();} catch (Exception ex1) {ex1.printStackTrace();}
		}	
	}
	
	public static boolean izmenaFilma(Film film) throws Exception{
		
		Connection conn = ConnectionManager.getConnection();
		
		PreparedStatement pstmt = null;
		
		try {
			String upit = "UPDATE film SET naziv = ?,reziser=?,glumci=?,zanrovi=?,trajanje=?,distributer=?,"
					+ "zemljaPorekla=?,godina=?,opis=?,obrisan=? WHERE id = ? ";
			
			pstmt = conn.prepareStatement(upit);
			
			int index = 1;
			pstmt.setString(index++, film.getNaziv());
			pstmt.setString(index++, film.getReziser());
			pstmt.setString(index++, film.getGlumci());
			pstmt.setString(index++, film.getZanrovi());
			pstmt.setInt(index++, film.getTrajanje());;
			pstmt.setString(index++, film.getDistributer());
			pstmt.setString(index++, film.getZemljaPorekla());
			pstmt.setInt(index++, film.getGodinaProizvodnje());
			pstmt.setString(index++, film.getOpis());
			pstmt.setBoolean(index++, film.isObrisan());
			pstmt.setInt(index++, film.getId());

			System.out.println(pstmt);
			
			return pstmt.executeUpdate() == 1;
			
			
		} finally {
			try {pstmt.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {conn.close();} catch (Exception ex1) {ex1.printStackTrace();}
		}
	}
	
	public static boolean brisanjeFilma(int id) throws Exception {
		
		Connection conn = ConnectionManager.getConnection();
		
		PreparedStatement pstmt = null; 
		try {
			String upit = "DELETE FROM film WHERE id = ?";
			
			pstmt = conn.prepareStatement(upit);
			
			pstmt.setInt(1, id);
			System.out.println(pstmt);
			return pstmt.executeUpdate() == 1;
			
		} finally {
			try {pstmt.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {conn.close();} catch (Exception ex1) {ex1.printStackTrace();}
		}
	}

}
