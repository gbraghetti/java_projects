package br.com.caelum.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.caelum.jdbc.dao.ContatoDao;
import br.com.caelum.jdbc.modelo.Contato;
import br.com.caelum.util.DataConverter;

@WebServlet("/adicionaContato")
public class AdicionaContatoServlet extends HttpServlet {

	
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		PrintWriter out = response.getWriter();

		String nome = request.getParameter("nome");
		String endereco = request.getParameter("endereco");
		String email = request.getParameter("email");
		String dataEmTexto = request.getParameter("dataNascimento");
		Contato contato = new Contato();
		Calendar dataNascimento = DataConverter.converteStringParaCalendar(dataEmTexto);

		contato.setNome(nome);
		contato.setEndereco(endereco);
		contato.setEmail(email);
		contato.setDataNascimento(dataNascimento);

		ContatoDao dao = new ContatoDao();
		dao.adiciona(contato);

		out.println("<htm>");
		out.println("<body>");
		out.println("Contato " + contato.getNome() + " adicionado com sucesso");
		out.println("</body>");
		out.println("</htm>");
	}
}
