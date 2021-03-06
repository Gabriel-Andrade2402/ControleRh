package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Entidades.Funcionario;

public class Querys {
	//M?todo que retorna toda lista de funcionarios cadastrados
	public static List<Funcionario> returnAll(){
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		conn = dbCon.getConnection();
		try {
			st = conn.createStatement();
			rs = st.executeQuery("select * from funcionarios");
			List<Funcionario> list=new ArrayList<>();
			while (rs.next()) {
				Funcionario func= new Funcionario();
				func.setId(rs.getInt("id"));
				func.setNome(rs.getString("nome"));
				func.setSobrenome(rs.getString("sobrenome"));
				func.setTelefone(rs.getString("telefone"));
				func.setEmail(rs.getString("email"));
				func.setPathImage(rs.getString("path_image"));
				list.add(func);
			}
			return list;
		} catch (SQLException e) {
			System.out.println("Error: "+e.getMessage());
		}
		return null;
	}
	//Metodo para inserir um funcionario novo
	public static Funcionario addFuncionario(Funcionario func) {
		Connection conn = null;
		PreparedStatement st = null;
		try {
			conn = dbCon.getConnection();
			st = conn.prepareStatement(
					"INSERT INTO funcionarios "
					+ "(nome,sobrenome,telefone,email,path_image) "
					+ "VALUES "
					+ "(?, ?, ?, ?,?)", 
					Statement.RETURN_GENERATED_KEYS);

			st.setString(1, func.getNome());
			st.setString(2, func.getSobrenome());
			st.setString(3,func.getTelefone());
			st.setString(4, func.getEmail());
			st.setString(5,func.getPathImage());
			st.executeUpdate();
			ResultSet rs = st.getGeneratedKeys();
			Funcionario funcionario=findByEmail(func.getEmail());
			return funcionario;
		}
		catch (SQLException e) {
			e.printStackTrace();
		} 
		/*finally {
			dbCon.closeStatement(st);
			dbCon.closeConnection();
		}*/
		return func;
	}
	//Atualizar funcionario
	public static boolean atualizarFuncionario(Funcionario func) {
		Connection conn = null;
		PreparedStatement st = null;
		try {
			conn = dbCon.getConnection();
	
			st = conn.prepareStatement(
					"UPDATE funcionarios "
					+ "SET nome = ?, "
					+ "sobrenome=?,"
					+ "telefone=?,"
					+ "email=?,"
					+ "path_image=? "
					+ "WHERE"
					+ "(id = ?)");

			st.setString(1,func.getNome());
			st.setString(2,func.getSobrenome());
			st.setString(3,func.getTelefone());
			st.setString(4,func.getEmail());
			st.setString(5,func.getPathImage());
			st.setInt(6,func.getId());
			st.executeUpdate();
			
			return true;
		}
		catch (SQLException e) {
			System.out.println("Error: "+e.getMessage());
			return false;
		} 
		/*finally {
		dbCon.closeStatement(st);
		dbCon.closeConnection();
		}	
		*/
	}
	//Deletar funcionario
	public static boolean deletarFuncionario(Funcionario func) {
		Connection conn = null;
		PreparedStatement st = null;
		try {
			conn = dbCon.getConnection();
	
			st = conn.prepareStatement(
					"DELETE FROM funcionarios "
					+ "WHERE "
					+ "Id = ?");

			st.setInt(1,func.getId());
			st.executeUpdate();
		}
		catch (SQLException e) {
			System.out.println("Error: "+e.getMessage());
		} 
		/*finally {
		dbCon.closeStatement(st);
		dbCon.closeConnection();
		}*/
		return true;
	}
	//Recuperar funcionario por id
	public static Funcionario findById(int id) {
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		conn = dbCon.getConnection();
		try {
			st = conn.prepareStatement("select * from funcionarios where id=?");
			st.setInt(1,id);
			rs = st.executeQuery();
			rs.next();
			Funcionario func= new Funcionario();
			func.setId(rs.getInt("id"));
			func.setNome(rs.getString("nome"));
			func.setSobrenome(rs.getString("sobrenome"));
			func.setTelefone(rs.getString("telefone"));
			func.setEmail(rs.getString("email"));
			func.setPathImage(rs.getString("path_image"));
			return func;
		} catch (SQLException e) {
			System.out.println("Error: "+e.getMessage());
		}
		return null;
	}
	//Recuperar funcionario por email
		public static Funcionario findByEmail(String email) {
			Connection conn = null;
			PreparedStatement st = null;
			ResultSet rs = null;
			conn = dbCon.getConnection();
			try {
				st = conn.prepareStatement("select * from funcionarios where email=?");
				st.setString(1,email);
				rs = st.executeQuery();
				rs.next();
				Funcionario func= new Funcionario();
				func.setId(rs.getInt("id"));
				func.setNome(rs.getString("nome"));
				func.setSobrenome(rs.getString("sobrenome"));
				func.setTelefone(rs.getString("telefone"));
				func.setEmail(rs.getString("email"));
				func.setPathImage(rs.getString("path_image"));
				return func;
			} catch (SQLException e) {
				System.out.println("Error: "+e.getMessage());
			}
			return null;
		}
}
