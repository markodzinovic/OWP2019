package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.Korisnik;
import model.Korisnik.Uloga;

public class KorisnikDAO {
	
	public static Korisnik get(String korisnickoIme, String lozinka) throws Exception {
		Connection conn = ConnectionManager.getConnection();

		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			String query = "SELECT uloga FROM korisnik WHERE korisnickoIme = ? AND lozinka = ?";

			pstmt = conn.prepareStatement(query);
			int index = 1;
			pstmt.setString(index++, korisnickoIme);
			pstmt.setString(index++, lozinka);
			System.out.println(pstmt);

			rset = pstmt.executeQuery();

			if (rset.next()) {
				Uloga uloga = Uloga.valueOf(rset.getString(1));
				
				Korisnik k1 = new Korisnik();
				k1.setKorisnickoIme(korisnickoIme);
				k1.setLozinka(lozinka);
				k1.setUloga(uloga);

				return  k1;
			}
		} finally {
			try {pstmt.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {rset.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {conn.close();} catch (Exception ex1) {ex1.printStackTrace();} // ako se koristi DBCP2, konekcija se mora vratiti u pool
		}

		return null;
	}
	
	public static List<Korisnik> getAll() throws Exception {
		List<Korisnik> korisnici = new ArrayList<>();

		Connection conn = ConnectionManager.getConnection();

		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			String query = "SELECT * FROM korisnik";

			pstmt = conn.prepareStatement(query);

			rset = pstmt.executeQuery();

			while (rset.next()) {
				int index = 1;
				String korisnickoIme = rset.getString(index++);
				String lozinka = rset.getString(index++);
				String datumR = rset.getString(index++);
				Uloga uloga = Uloga.valueOf(rset.getString(index++));

				Korisnik k = new Korisnik(korisnickoIme, lozinka, datumR, uloga);
				korisnici.add(k);
			}
		} finally {
			try {pstmt.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {rset.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {conn.close();} catch (Exception ex1) {ex1.printStackTrace();} 
		}
		
		System.out.println(korisnici);
		return korisnici;
	}
	
	public static Korisnik getKorisnik(String korisnickoIme) throws Exception {
		Connection conn = ConnectionManager.getConnection();
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		try {
			String upit = "SELECT lozinka,datum,uloga FROM korisnik WHERE korisnickoIme = ?";
			
			pstmt = conn.prepareStatement(upit);
			pstmt.setString(1, korisnickoIme);
			System.out.println(pstmt);
			
			rset = pstmt.executeQuery();
			if(rset.next()) {
				int index = 1;
				String lozinka = rset.getString(index++);
				String datum = rset.getString(index++);
				Uloga uloga = Uloga.valueOf(rset.getString(index++));
				
				System.out.println(korisnickoIme+" "+datum+" "+lozinka+" "+uloga );
				return new Korisnik(korisnickoIme, lozinka, datum, uloga);
				
				
			}
			
		} finally {
			try {pstmt.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {rset.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {conn.close();} catch (Exception ex1) {ex1.printStackTrace();}
		}
		
		return null;
	}

}
