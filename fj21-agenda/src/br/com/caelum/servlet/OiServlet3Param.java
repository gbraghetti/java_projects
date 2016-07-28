package br.com.caelum.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sun.java2d.pipe.ParallelogramPipe;

@WebServlet(name = "OiServlet3Param", urlPatterns = { "/hi" }, initParams = {
		@WebInitParam(name = "param1", value = "value1"), @WebInitParam(name = "param2", value = "value2") })
public class OiServlet3Param extends HttpServlet {

	private String parametro1;
	private String parametro2;

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		out.println("<h2>Exemplo com InitParam Servlet </h2>");

		ServletConfig config = getServletConfig();

		String parametro1 = config.getInitParameter("param1");
		out.println("Valor do parâmetro 1: " + parametro1);

		String parametro2 = config.getInitParameter("param2");
		out.println("<br>Valor do parâmetro 1: " + parametro2);

		out.println("<h2>Usando direto na saída.</h2> ");
		out.println("<br>Valor do parâmetro 1 : " + getServletConfig().getInitParameter("param1"));

		out.close();

		// qlqerCoisa(response);
	}

	public void qlqerCoisa(HttpServletResponse response) throws IOException {
		PrintWriter out = response.getWriter();

		out.println("<html>");
		out.println("<head>");
		out.println("<title>Primeira Servlet</title>");
		out.println("</head>");
		out.println("<body>");
		out.println("<h1>Oi mundo servlet!</h1>");
		out.println("</body>");
		out.println("</html>");
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		this.parametro1 = config.getInitParameter("param1");
		this.parametro2 = config.getInitParameter("param2");
	}
}
