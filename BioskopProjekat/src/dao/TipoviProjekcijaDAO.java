package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import model.TipoviProjekcije;

public class TipoviProjekcijaDAO {
	
	public static TipoviProjekcije getTip(int id) throws Exception{
		
		Connection conn = ConnectionManager.getConnection();
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		try {
			String upit = "SELECT naziv FROM tipoviProjekcije WHERE id = ?";
			
			pstmt = conn.prepareStatement(upit);
			pstmt.setInt(1, id);
			System.out.println(pstmt);
			
			rset = pstmt.executeQuery();
			if(rset.next()) {
				String naziv = rset.getString(1);
				
				return new TipoviProjekcije(id, naziv);
				
			}
			
			
		} finally {
			try {pstmt.close();} catch (Exception e1) {e1.printStackTrace();}
			try {rset.close();} catch (Exception e1) {e1.printStackTrace();}
			try {conn.close();} catch (Exception e1) {e1.printStackTrace();}
		}
		
		return null;
	}

}
