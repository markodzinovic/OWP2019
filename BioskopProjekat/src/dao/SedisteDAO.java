package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import model.Sala;
import model.Sedista;

public class SedisteDAO {

	public static Sedista brojSedista(int idSale) throws Exception{
		
		Connection conn = ConnectionManager.getConnection();
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		try {
			String upit = "SELECT redniBroj,sala FROM sedista WHERE sala = ?";
			
			pstmt = conn.prepareStatement(upit);
			pstmt.setInt(1, idSale);
			System.out.println(pstmt);
			
			rset = pstmt.executeQuery();
			
			if (rset.next()) {
				int index = 1;
				int redniBroj = rset.getInt(index++);
				Sala s = SalaDao.getSala(idSale);
				
				return new Sedista(redniBroj, s);
			}
			
		} finally {
			try {rset.close();} catch (Exception e1) {e1.printStackTrace();}
			try {pstmt.close();} catch (Exception e1) {e1.printStackTrace();}
			try {conn.close();} catch (Exception e1) {e1.printStackTrace();}
		}

		return null;
	}
	
	
}
