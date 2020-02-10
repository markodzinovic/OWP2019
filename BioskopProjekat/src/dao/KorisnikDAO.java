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
			String query = "SELECT datum,uloga,obrisan FROM korisnik WHERE korisnickoIme = ? AND lozinka = ? AND obrisan = false";

			pstmt = conn.prepareStatement(query);
			int index = 1;
			pstmt.setString(index++, korisnickoIme);
			pstmt.setString(index++, lozinka);
			System.out.println(pstmt);

			rset = pstmt.executeQuery();

			if (rset.next()) {
				String datum = rset.getString(1);
				Uloga uloga = Uloga.valueOf(rset.getString(2));
				boolean obrisan = rset.getBoolean(3);

				return new Korisnik(korisnickoIme, lozinka, datum, uloga, obrisan);
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
			String query = "SELECT * FROM korisnik WHERE obrisan = false";

			pstmt = conn.prepareStatement(query);

			rset = pstmt.executeQuery();

			while (rset.next()) {
				int index = 1;
				String korisnickoIme = rset.getString(index++);
				String lozinka = rset.getString(index++);
				String datumR = rset.getString(index++);
				Uloga uloga = Uloga.valueOf(rset.getString(index++));
				boolean obrisan = rset.getBoolean(index++);

				Korisnik k = new Korisnik(korisnickoIme, lozinka, datumR, uloga,obrisan);
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
			String upit = "SELECT lozinka,datum,uloga,obrisan FROM korisnik WHERE korisnickoIme = ?";
			
			pstmt = conn.prepareStatement(upit);
			pstmt.setString(1, korisnickoIme);
			System.out.println(pstmt);
			
			rset = pstmt.executeQuery();
			if(rset.next()) {
				int index = 1;
				String lozinka = rset.getString(index++);
				String datum = rset.getString(index++);
				Uloga uloga = Uloga.valueOf(rset.getString(index++));
				boolean obrisan = rset.getBoolean(index++);
				
				System.out.println(korisnickoIme+" "+datum+" "+lozinka+" "+uloga );
				return new Korisnik(korisnickoIme, lozinka, datum, uloga, obrisan);
				
				
			}
			
		} finally {
			try {pstmt.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {rset.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {conn.close();} catch (Exception ex1) {ex1.printStackTrace();}
		}
		
		return null;
	}
	
	public static boolean registracija(Korisnik korisnik) throws Exception {
		Connection conn = ConnectionManager.getConnection();
		
		PreparedStatement pstmt = null;
		try {
			String upit = "INSERT INTO korisnik (korisnickoIme,lozinka,datum,uloga,obrisan)"
					+ "VALUES (?,?,?,?,?)";
			
			pstmt = conn.prepareStatement(upit);
			int index = 1;
			pstmt.setString(index++, korisnik.getKorisnickoIme());
			pstmt.setString(index++, korisnik.getLozinka());
			pstmt.setString(index++, korisnik.getDatumRegistracije());
			pstmt.setString(index++, korisnik.getUloga().toString());
			pstmt.setBoolean(index++, korisnik.isObrisan());
			
			return pstmt.executeUpdate() == 1;
			
		} finally {
			try {pstmt.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {conn.close();} catch (Exception ex1) {ex1.printStackTrace();}
		}
	}
	
	public static boolean izmenaAdmin(Korisnik korisnik) throws Exception {
		
		Connection conn = ConnectionManager.getConnection();
		
		PreparedStatement pstmt = null;
		
		try {
			String upit = "UPDATE korisnik SET uloga = ? WHERE korisnickoIme = ?";
			
			pstmt = conn.prepareStatement(upit);
			
			int index = 1;
			pstmt.setString(index++, korisnik.getUloga().toString());
			pstmt.setString(index++, korisnik.getKorisnickoIme());
			
			return pstmt.executeUpdate() == 1;
			
		} finally {
			try {pstmt.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {conn.close();} catch (Exception ex1) {ex1.printStackTrace();}
		}
	}
	
	public static boolean izmenaProfila(Korisnik korisnik, String korisnickoIme) throws Exception {
		
		Connection conn = ConnectionManager.getConnection();
		
		PreparedStatement pstmt = null;
		
		try {
			String upit = "UPDATE korisnik SET korisnickoIme=?,lozinka=?,datum=?,uloga=?,obrisan=? WHERE korisnickoIme = ?";
			
			pstmt = conn.prepareStatement(upit);
			
			int index = 1;
			pstmt.setString(index++, korisnik.getKorisnickoIme());
			pstmt.setString(index++, korisnik.getLozinka());
			pstmt.setString(index++, korisnik.getDatumRegistracije());
			pstmt.setString(index++, korisnik.getUloga().toString());
			pstmt.setBoolean(index++, korisnik.isObrisan());
			pstmt.setString(index++, korisnickoIme);
			
			return pstmt.executeUpdate() == 1;
			
		} finally {
			try {pstmt.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {conn.close();} catch (Exception ex1) {ex1.printStackTrace();}
		}
	}
	
	public static boolean brisanjeKorisnika(String korisnickoIme) throws Exception{
		
		Connection conn = ConnectionManager.getConnection();
		
		PreparedStatement pstmt = null;
		
		try {
			String upit = "DELETE FROM korisnik WHERE korisnickoIme = ?";
			
			pstmt = conn.prepareStatement(upit);
			
			pstmt.setString(1, korisnickoIme);
			
			System.out.println(pstmt);
			
			return pstmt.executeUpdate() == 1;
			
		} finally {
			try {pstmt.close();} catch (Exception e) {e.printStackTrace();}
			try {conn.close();} catch (Exception e) {e.printStackTrace();}
		}
		
	}
	
public static boolean logickoBrisanjeKorisnika(String korisnickoIme) throws Exception{
		
		Connection conn = ConnectionManager.getConnection();
		
		PreparedStatement pstmt = null;
		
		try {
			String upit = "UPDATE korisnik SET obrisan = true WHERE korisnickoIme = ?";
			
			pstmt = conn.prepareStatement(upit);
			
			int index = 1;
			pstmt.setString(index++, korisnickoIme);
			
			System.out.println(pstmt);
			
			return pstmt.executeUpdate() == 1;
			
		} finally {
			try {pstmt.close();} catch (Exception e) {e.printStackTrace();}
			try {conn.close();} catch (Exception e) {e.printStackTrace();}
		}
		
	}

}
