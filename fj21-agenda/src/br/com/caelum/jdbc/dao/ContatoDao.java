package br.com.caelum.jdbc.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.management.RuntimeErrorException;

import com.sun.openpisces.Dasher;

import br.com.caelum.excepetions.DAOException;
import br.com.caelum.jdbc.ConnectionFactory;
import br.com.caelum.jdbc.modelo.Contato;
import br.com.caelum.jdbc.modelo.Mensagens;
import br.com.caelum.util.DataConverter;

public class ContatoDao {

	private Connection connection;

	public ContatoDao() {
		this.connection = new ConnectionFactory().getConnection();
	}

	public void adiciona(Contato contato) {
		String sql = "INSERT INTO CONTATOS (nome, email, endereco, dataNascimento) VALUES (?, ?, ?, ?); ";
		PreparedStatement stmt;
		try {
			stmt = this.connection.prepareStatement(sql);
			stmt.setString(1, contato.getNome());
			stmt.setString(2, contato.getEmail());
			stmt.setString(3, contato.getEndereco());
			stmt.setDate(4, new Date(contato.getDataNascimento().getTimeInMillis()));

			stmt.execute();
			stmt.close();
		} catch (SQLException e) {
			throw new DAOException(e);
		} catch (Exception e) {
			throw new DAOException(e);
		}
	}

	public void alterar(Contato contato) {
		String sql = "UPDATE contatos SET nome = ?, email = ?, endereco = ?, dataNascimento = ? WHERE id = ?; ";
		PreparedStatement stmt;
		try {
			stmt = this.connection.prepareStatement(sql);
			stmt.setString(1, contato.getNome());
			stmt.setString(2, contato.getEmail());
			stmt.setString(3, contato.getEndereco());
			stmt.setDate(4, new Date(contato.getDataNascimento().getTimeInMillis()));
			stmt.setLong(5, contato.getId());

			stmt.execute();
			stmt.close();

		} catch (SQLException e) {
			throw new DAOException(e);
		} catch (Exception e) {
			throw new DAOException(e);
		}
	}

	public void excluir(Long id) {
		String sql = "DELETE FROM contatos WHERE id = ?; ";
		PreparedStatement stmt;
		Contato contato;
		try {
			contato = getPorId(id);
			if (contato != null) {
				stmt = this.connection.prepareStatement(sql);
				stmt.setLong(1, id);

				stmt.execute();
				stmt.close();

			} else {
				System.out.println(Mensagens.ID_NAO_LOCALIZADO);
			}

		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public Contato getPorId(Long id) {
		String sql = "SELECT * FROM contatos WHERE id = ?; ";
		PreparedStatement stmt;
		
		try {
			stmt = this.connection.prepareStatement(sql);
			stmt.setLong(1, id);
			ResultSet rs = stmt.executeQuery();
			Contato contato = null;
			while (rs.next()) {
				contato = new Contato();
				contato = devolveObjPreenchido(rs);
			}
			rs.close();
			stmt.close();

			return contato;

		} catch (SQLException e) {
			throw new DAOException(e);

		}

	}

	public List<Contato> getLista() {
		List<Contato> contatos = new ArrayList<>();
		String sql = "select * from contatos; ";
		PreparedStatement stmt;

		try {
			stmt = this.connection.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				contatos.add(devolveObjPreenchido(rs));
			}
			rs.close();
			stmt.close();
			return contatos;

		} catch (SQLException e) {
			throw new DAOException(e);
		}

	}

	public List<Contato> getListaPorNome(String nomeWhere) {
		List<Contato> contatos = new ArrayList<>();
		String sql = "SELECT * FROM contatos WHERE nome LIKE ?; ";
		PreparedStatement stmt;

		try {
			nomeWhere += "%";
			stmt = this.connection.prepareStatement(sql);
			stmt.setString(1, nomeWhere);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				contatos.add(devolveObjPreenchido(rs));
			}
			
			rs.close();
			stmt.close();
			return contatos;

		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public Contato devolveObjPreenchido(ResultSet rs) throws SQLException {
		Contato contato = new Contato();
		contato.setId(rs.getLong("id"));
		contato.setNome(rs.getString("nome"));
		contato.setEmail(rs.getString("email"));
		contato.setEndereco(rs.getString("endereco"));

		// montado a data através do Calendar
		// Calendar data = Calendar.getInstance();
		// data.setTime(rs.getDate("dataNascimento"));

		contato.setDataNascimento(DataConverter.converteDateParaCalendar(rs.getDate("dataNascimento")));

		return contato;
	}
}
