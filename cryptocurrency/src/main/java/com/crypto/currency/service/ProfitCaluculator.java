package com.crypto.currency.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import com.crypto.currency.pojo.CurrencyPojo;

public interface ProfitCaluculator {

	String generateJsonStringFromFile(File newFile) throws FileNotFoundException;

	List<CurrencyPojo> generateProfitableCurrencyDetails(String jsonString);

}
