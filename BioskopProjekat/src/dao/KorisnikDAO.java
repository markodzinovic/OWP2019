package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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
				String datum = "233";
				Uloga uloga = Uloga.valueOf(rset.getString(1));

				return new Korisnik(korisnickoIme, lozinka, datum, uloga);
			}
		} finally {
			try {pstmt.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {rset.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {conn.close();} catch (Exception ex1) {ex1.printStackTrace();} // ako se koristi DBCP2, konekcija se mora vratiti u pool
		}

		return null;
	}

}
