package br.com.caelum.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import br.com.caelum.excepetions.DAOException;
import br.com.caelum.jdbc.ConnectionFactory;
import br.com.caelum.jdbc.modelo.Funcionario;
import br.com.caelum.jdbc.modelo.Mensagens;

public class FuncionarioDao {
	private Connection connection;

	public FuncionarioDao() {
		this.connection = new ConnectionFactory().getConnection();
	}

	public void adiciona(Funcionario funcionario) {
		String sql = "insert into funcionarios (nome, usuario, senha) values (?, ?, ?);";
		PreparedStatement stmt;

		try {
			stmt = this.connection.prepareStatement(sql);
			stmt.setString(1, funcionario.getNome());
			stmt.setString(2, funcionario.getUsuario());
			stmt.setString(3, funcionario.getSenha());

			stmt.execute();
			stmt.close();
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public void alterar(Funcionario funcionario) {
		String sql = "update funcionarios set nome = ?, usuario = ?, senha = ? where id = ?; ";
		PreparedStatement stmt;
		try {
			stmt = this.connection.prepareStatement(sql);
			stmt.setString(1, funcionario.getNome());
			stmt.setString(2, funcionario.getUsuario());
			stmt.setString(3, funcionario.getSenha());
			stmt.setLong(4, funcionario.getId());

			stmt.execute();
			stmt.close();
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public void excluir(Long id) {
		String sql = "delete from funcionarios where id = ?; ";
		PreparedStatement stmt;
		Funcionario funcionario;
		try {
			funcionario = getPorId(id);
			if (funcionario != null) {
				stmt = this.connection.prepareStatement(sql);
				stmt.setLong(1, id);

				stmt.execute();
				stmt.close();
			} else {
				System.out.println(Mensagens.ID_NAO_LOCALIZADO);
			}

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public Funcionario getPorId(Long id) {
		String sql = "select * from funcionarios where id = ?; ";
		PreparedStatement stmt;
		try {
			stmt = this.connection.prepareStatement(sql);
			stmt.setLong(1, id);
			ResultSet rs = stmt.executeQuery();
			Funcionario funcionario = null;

			while (rs.next()) {
				funcionario = new Funcionario();
				funcionario = devolveObjPreenchido(rs);
			}
			rs.close();
			stmt.close();

			return funcionario;

		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}
	
	public List<Funcionario> getLista(){
		String sql = "select * from funcionarios; ";
		PreparedStatement stmt;
		List<Funcionario> funcionarios = new ArrayList<>();
		
		try {
			stmt = this.connection.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()){
				funcionarios.add(devolveObjPreenchido(rs));
			}
			rs.close();
			stmt.close();
			
			return funcionarios;
			
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public List<Funcionario> getListaPorNome(String nomeWhere){
		String sql = "select * from funcionarios where nome like ?; ";
		PreparedStatement stmt;
		List<Funcionario> funcionarios = new ArrayList<>();
		
		try {
			nomeWhere += "%";
			stmt = this.connection.prepareStatement(sql);
			stmt.setString(1, nomeWhere);
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()){
				funcionarios.add(devolveObjPreenchido(rs));
			}
			
			rs.close();
			stmt.close();
			return funcionarios;
			
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	private Funcionario devolveObjPreenchido(ResultSet rs) throws SQLException {
		Funcionario funcionario = new Funcionario();
		funcionario.setId(rs.getLong("id"));
		funcionario.setNome(rs.getString("nome"));
		funcionario.setUsuario(rs.getString("usuario"));
		funcionario.setSenha(rs.getString("senha"));

		return funcionario;
	}
}
