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
	
	public static List<Karta> zauzetaSedistaZaProjekciju(int idProjekcije) throws Exception{
		
		List<Karta> zauzetaSedista = new ArrayList<>();
		
		Connection conn = ConnectionManager.getConnection();
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		try {
			String upit = "SELECT * FROM karte WHERE projekcija = ?";
			
			pstmt = conn.prepareStatement(upit);
			pstmt.setInt(1, idProjekcije);
			System.out.println(pstmt);
			
			rset = pstmt.executeQuery();
			while (rset.next()) {
				int index = 1;
				int idKarte = rset.getInt(index++);
				int idProjek = rset.getInt(index++);
				int sediste = rset.getInt(index++);
				String datum = rset.getString(index++);
				String korisnickoIme = rset.getString(index++);
				
				Projekcije p = ProjekcijeDAO.getProjekcija(idProjek);
				Korisnik k = KorisnikDAO.getKorisnik(korisnickoIme);
				
				Karta karta = new Karta(idKarte, p, sediste, datum, k);
				
				zauzetaSedista.add(karta);
			}
		} finally {
			try {rset.close();} catch (Exception e1) {e1.printStackTrace();}
			try {pstmt.close();} catch (Exception e1) {e1.printStackTrace();}
			try {conn.close();} catch (Exception e1) {e1.printStackTrace();}
		}
		
		
		return zauzetaSedista;
		
	}
	
	public static boolean dodavanjeKarte(Karta karta) throws Exception{
		
		Connection conn = ConnectionManager.getConnection();
		
		PreparedStatement pstmt = null;
		
		try {
			String upit = "INSERT INTO karte (projekcija,sediste,datum,korisnik) VALUES (?,?,?,?)";
			
			int index = 1;
			pstmt = conn.prepareStatement(upit);
			
			pstmt.setInt(index++, karta.getProjekcija().getId());
			pstmt.setInt(index++, karta.getSediste());
			pstmt.setString(index++, karta.getDatum());
			pstmt.setString(index++, karta.getKorisnik().getKorisnickoIme());
			
			return pstmt.executeUpdate() == 1;
			
		} finally {
			try {pstmt.close();} catch (Exception e1) {e1.printStackTrace();}
			try {conn.close();} catch (Exception e1) {e1.printStackTrace();}
		}
		
	}
	
	public static boolean brisanjeKarte(int id) throws Exception{
		
		Connection conn = ConnectionManager.getConnection();
		
		PreparedStatement pstmt = null;
		
		try {
			String upit = "DELETE FROM karte WHERE id = ?";
			
			pstmt = conn.prepareStatement(upit);
			
			pstmt.setInt(1, id);
			
			System.out.println(pstmt);
			
			return pstmt.executeUpdate() == 1;
		} finally {
			try {pstmt.close();} catch (Exception e1) {e1.printStackTrace();}
			try {conn.close();} catch (Exception e1) {e1.printStackTrace();}
		}
	}
	
public static List<Karta> karteJednogKorisnika(String korisnik) throws Exception{
		
		List<Karta> sveKarte = new ArrayList<Karta>();
		
		Connection conn = ConnectionManager.getConnection();
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		try {
			String upit = "SELECT * FROM karte WHERE korisnik = ?";
			
			pstmt = conn.prepareStatement(upit);
			
			pstmt.setString(1, korisnik);
			
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

	public static Karta getKarta(int idKarte) throws Exception{
				
		Connection conn = ConnectionManager.getConnection();
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		try {
			String upit = "SELECT * FROM karte WHERE id = ?";
			
			pstmt = conn.prepareStatement(upit);
			
			pstmt.setInt(1, idKarte);
			
			System.out.println(pstmt);
			
			rset = pstmt.executeQuery();
			
			if (rset.next()) {
				int index = 1;
				int id = rset.getInt(index++);
				int idProjekcije = rset.getInt(index++);
				int sediste = rset.getInt(index++);
				String datum = rset.getString(index++);
				String korisnickoIme = rset.getString(index++);
				
				Projekcije p = ProjekcijeDAO.getProjekcija(idProjekcije);
				Korisnik k = KorisnikDAO.getKorisnik(korisnickoIme);
			
				return new  Karta(id, p, sediste, datum, k);
			}
			
		} finally {
			try {rset.close();} catch (Exception e1) {e1.printStackTrace();}
			try {pstmt.close();} catch (Exception e1) {e1.printStackTrace();}
			try {conn.close();} catch (Exception e1) {e1.printStackTrace();}
		}
		
		return null;
	}
	
public static List<Karta> proveraProjekcijeUKartama(int id) throws Exception{
		
		List<Karta> projekcije = new ArrayList<>();
	
		Connection conn = ConnectionManager.getConnection();
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		try {
			String upit = "SELECT * FROM karte WHERE projekcija = ?";
			
			pstmt = conn.prepareStatement(upit);
			
			pstmt.setInt(1, id);
			
			rset = pstmt.executeQuery();
			while(rset.next()) {
				int index = 1;
				int idKarte = rset.getInt(index++);
				int idProjekcije = rset.getInt(index++);
				int sediste = rset.getInt(index++);
				String datum = rset.getString(index++);
				String korisnickoIme = rset.getString(index++);
				
				Projekcije p = ProjekcijeDAO.getProjekcija(idProjekcije);
				Korisnik k = KorisnikDAO.getKorisnik(korisnickoIme);
				
				Karta karta = new Karta(idKarte, p, sediste, datum, k);
				
				projekcije.add(karta);
			}
			
		} finally {
			try {pstmt.close();} catch (Exception e1) {e1.printStackTrace();}
			try {rset.close();} catch (Exception e1) {e1.printStackTrace();}
			try {conn.close();} catch (Exception e1) {e1.printStackTrace();}
		}
		
		return projekcije;
	}
	
}
