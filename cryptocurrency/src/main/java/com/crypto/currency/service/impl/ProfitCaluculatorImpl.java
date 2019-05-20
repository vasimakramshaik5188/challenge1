package com.crypto.currency.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import com.crypto.currency.pojo.CurrencyPojo;
import com.crypto.currency.service.ProfitCaluculator;

public class ProfitCaluculatorImpl implements ProfitCaluculator {

	static Logger LOG = Logger.getLogger(ProfitCaluculatorImpl.class);

	/**
	 * @param newFile
	 * @return
	 * @throws FileNotFoundException
	 */
	@Override
	public String generateJsonStringFromFile(File newFile) throws FileNotFoundException {
		InputStream jsonInputStream = new FileInputStream(newFile);
		BufferedReader br = null;
		String jsonStringArray = null;
		try {
			br = new BufferedReader(new InputStreamReader(jsonInputStream));
			StringBuilder sb = new StringBuilder();
			String line;
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			jsonStringArray = sb.toString();
		} catch (IOException e) {
			LOG.error(ExceptionUtils.getFullStackTrace(e));
		} finally {
			if (jsonInputStream != null) {
				try {
					jsonInputStream.close();
				} catch (IOException e) {
					LOG.error(ExceptionUtils.getFullStackTrace(e));
				}
			}
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					LOG.error(ExceptionUtils.getFullStackTrace(e));
				}
			}
		}
		return jsonStringArray;
	}

	/**
	 * @param jsonStringArray
	 * @return List<CurrencyPojo>
	 */
	@Override
	public List<CurrencyPojo> generateProfitableCurrencyDetails(String jsonString) {
		List<CurrencyPojo> profitableCurrencyDetails = new ArrayList<CurrencyPojo>();
		JSONArray jsonArray = new JSONArray(jsonString);

		// iterates through currency types
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject currencyData = (JSONObject) jsonArray.get(i);
			CurrencyPojo currencyPojo = new CurrencyPojo();
			currencyPojo.setCurrency(currencyData.getString("currency"));

			// quotes under a currency
			JSONArray quotes = (JSONArray) currencyData.get("quotes");
			int count = quotes.length();

			// map for holding highest trading value
			Map<Float, Map<JSONObject, JSONObject>> profitMap = new HashMap<Float, Map<JSONObject, JSONObject>>();

			// Array for holding all profits of specified currency
			float[] allProfits = new float[count - 1];

			findHighestProfitFromQuotes(currencyPojo, quotes, count, profitMap, allProfits);
			profitableCurrencyDetails.add(currencyPojo);
		}
		return profitableCurrencyDetails;
	}

	/**
	 * @param currencyPojo
	 * @param quotes
	 * @param count
	 * @param profitMap
	 * @param allProfits
	 */
	private void findHighestProfitFromQuotes(CurrencyPojo currencyPojo, JSONArray quotes, int count,
			Map<Float, Map<JSONObject, JSONObject>> profitMap, float[] allProfits) {
		if (count >= 2) {
			for (int j = 0; j < count - 1; j++) {
				JSONObject buyObj = (JSONObject) quotes.get(j);
				float buyPrice = Float.parseFloat(buyObj.getString("price"));
				Map<JSONObject, JSONObject> tradeMap = new HashMap<JSONObject, JSONObject>();
				JSONObject temp = null;
				float highestProfit = 0;
				for (int k = j + 1; k < count; k++) {
					JSONObject sellObj = (JSONObject) quotes.get(k);
					float sellPrice = Float.parseFloat(sellObj.getString("price"));
					if (k == 1) {
						temp = sellObj;
						highestProfit = sellPrice - buyPrice;
					} else {
						float currentProfit = sellPrice - buyPrice;
						if (currentProfit > highestProfit) {
							temp = sellObj;
							highestProfit = currentProfit;
						}
					}
				}
				tradeMap.put(buyObj, temp);
				profitMap.put(highestProfit, tradeMap);
				allProfits[j] = highestProfit;
			}
			Arrays.sort(allProfits);
			Map<JSONObject, JSONObject> highestProfitableMap = profitMap.get(allProfits[allProfits.length - 1]);
			Map.Entry<JSONObject, JSONObject> entry = highestProfitableMap.entrySet().iterator().next();
			JSONObject buyObj = entry.getKey();
			JSONObject sellObj = entry.getValue();
			currencyPojo.setBuyPrice(buyObj.getString("price"));
			currencyPojo.setBuyTime(formateTime(buyObj.getString("time")));
			currencyPojo.setSellPrice(sellObj.getString("price"));
			currencyPojo.setSellTime(formateTime(sellObj.getString("time")));
		}
	}
	
	/**
	 * @param time
	 * @return
	 */
	private String formateTime(String time) {
		float twentyFourHrFormat = Float.parseFloat(time)/100;
		float twelveHrFormat = twentyFourHrFormat - 12;		
		String finalFormat = "";		
		if(twelveHrFormat < 0 ){
			finalFormat = String.format("%.2f", twentyFourHrFormat) + " AM";
		} else if ( (0 <= twelveHrFormat) && (twelveHrFormat< 1)) {
			finalFormat = String.format("%.2f", twentyFourHrFormat) + " PM";
		} else {
			finalFormat = String.format("%.2f", twelveHrFormat) + " PM";
		}
		return finalFormat;
	}

}
