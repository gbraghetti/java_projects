package br.com.caelum.util;

import java.sql.Date;
import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public final class DataConverter {

	public static String converteCalendarParaString(Calendar data) {
		SimpleDateFormat s = new SimpleDateFormat("yyyy-mm-dd");
		String dataFormatada = s.format(data.getTime());
		return dataFormatada;
	}

	public static Date converteCalendarParaDate(Calendar data) throws Exception {
		String dataF = converteCalendarParaString(data);
		if (data == null || data.equals(""))
			return null;
		Date date = null;
		try {
			DateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");
			date = (Date) formatter.parse(dataF);
		} catch (ParseException e) {
			throw e;
		}
		return date;
	}

	public static Date converteStringParaDate(String data, String formato) {
		SimpleDateFormat sd = new SimpleDateFormat();

		try {

			sd.applyPattern(formato);
			Date d = (Date) sd.parse(data);
			return d;

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public static Date converteCalendarParaDateOther(Calendar calendar) {
		return (Date) calendar.getTime();
	}

	public static String converteDateParaString(Date data) {
		Format formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dataFormatada = formatter.format(data);
		return dataFormatada;
	}

	public static Calendar converteStringParaCalendar(String dataTexto) {
		Date date = null;
		Calendar dataCalendar = null;
		try {
			date = (Date) new SimpleDateFormat("dd/MM/yyyy").parse(dataTexto);
			dataCalendar = Calendar.getInstance();
			dataCalendar.setTime(date);
			return dataCalendar;
		} catch (ParseException e) {
			System.out.println("Erro de conversão da data. ");
			return dataCalendar;
		}

	}
	
	public static Calendar converteDateParaCalendar(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar;
	}
	


}
