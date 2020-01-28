package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.Sala;
import model.TipoviProjekcije;

public class SalaDao {
	
	public static Sala getSala(int id) throws Exception{
		
		Connection conn = ConnectionManager.getConnection();
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		try {
			
			String upit = "SELECT id,naziv FROM sala WHERE id = ?";
			
			pstmt = conn.prepareStatement(upit);
			pstmt.setInt(1, id);
			System.out.println(pstmt);
			
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				int idSale = rset.getInt(1);
				String naziv = rset.getString(2);
				
				List<TipoviProjekcije> t = SalaDao.getTipoviSale(idSale);
				
				return new Sala(idSale, naziv, (ArrayList<TipoviProjekcije>) t);
				
			}
			
		} finally {
			try {pstmt.close();} catch (Exception e1) {e1.printStackTrace();}
			try {rset.close();} catch (Exception e1) {e1.printStackTrace();}
			try {conn.close();} catch (Exception e1) {e1.printStackTrace();}
		}
		
		return null;
	}
	
	public static List<Sala> getAll() throws Exception{
		
		List<Sala> sveSale = new ArrayList<>();
		
		Connection conn = ConnectionManager.getConnection();
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		try {
			String upit = "SELECT id,naziv FROM sala";
			
			pstmt = conn.prepareStatement(upit);
			
			System.out.println(pstmt);
			
			rset = pstmt.executeQuery();
			
			while (rset.next()) {
				int index = 1;
				int id = rset.getInt(index++);
				String naziv = rset.getString(index++);
				
				List<TipoviProjekcije> tip = SalaDao.getTipoviSale(id);
				
				Sala s = new Sala(id, naziv, (ArrayList<TipoviProjekcije>) tip);
				sveSale.add(s);
			}
			
			
		} finally {
			try {pstmt.close();} catch (Exception e1) {e1.printStackTrace();}
			try {rset.close();} catch (Exception e1) {e1.printStackTrace();}
			try {conn.close();} catch (Exception e1) {e1.printStackTrace();}
		}
		
		
		return sveSale;
		
	}
	
	public static List<TipoviProjekcije> getTipoviSale(int idSale) throws Exception{
		
		List<TipoviProjekcije> tipoviSale = new ArrayList<>();
		
		Connection conn = ConnectionManager.getConnection();
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		try {
			
			String upit = "SELECT sala,tipProjekcije FROM salaTipProjekcije WHERE sala = ?";
			
			pstmt = conn.prepareStatement(upit);
			pstmt.setInt(1, idSale);
			System.out.println(pstmt);
			
			rset = pstmt.executeQuery();
			while (rset.next()) {
				int sala = rset.getInt(1);
				System.out.println(sala);
				int tip = rset.getInt(2);
				TipoviProjekcije t = TipoviProjekcijaDAO.getTip(tip);
				tipoviSale.add(t);
			}
			
		} finally {
			try {pstmt.close();} catch (Exception e1) {e1.printStackTrace();}
			try {rset.close();} catch (Exception e1) {e1.printStackTrace();}
			try {conn.close();} catch (Exception e1) {e1.printStackTrace();}
		}
		
		
		return tipoviSale;
	}

}
