package com.crypto.currency.servlet;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.crypto.currency.service.ProfitCaluculator;
import com.crypto.currency.service.impl.ProfitCaluculatorImpl;
import com.google.gson.Gson;

/**
 * Servlet implementation class BestProfitCaluculator
 */
@WebServlet("/bestProfitCaluculator")
public class BestProfitCaluculator extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static Logger LOG = Logger.getLogger(BestProfitCaluculator.class);

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		response.setStatus(HttpServletResponse.SC_OK);
		
		//loading json file from resources folder
		ClassLoader classLoader = new BestProfitCaluculator().getClass().getClassLoader();
		File newFile = new File(classLoader.getResource("20180507.json").getFile());
		
		//instantiating interface
		ProfitCaluculator profitCaluculator = new ProfitCaluculatorImpl();
		
		//Getting json string from file
		String jsonString = profitCaluculator.generateJsonStringFromFile(newFile);
		
		response.getWriter().write(new Gson().toJson(profitCaluculator.generateProfitableCurrencyDetails(jsonString)));
	}
}