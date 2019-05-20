package com.crypto.currency.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CurrencyPojo {

	@SerializedName("currency")
	@Expose
	private String currency;

	@SerializedName("buyPrice")
	@Expose
	private String buyPrice;
	
	@SerializedName("sellPrice")
	@Expose
	private String sellPrice;

	@SerializedName("buyTime")
	@Expose
	private String buyTime;
	
	@SerializedName("sellTime")
	@Expose
	private String sellTime;

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getBuyPrice() {
		return buyPrice;
	}

	public void setBuyPrice(String buyPrice) {
		this.buyPrice = buyPrice;
	}

	public String getSellPrice() {
		return sellPrice;
	}

	public void setSellPrice(String sellPrice) {
		this.sellPrice = sellPrice;
	}

	public String getBuyTime() {
		return buyTime;
	}

	public void setBuyTime(String buyTime) {
		this.buyTime = buyTime;
	}

	public String getSellTime() {
		return sellTime;
	}

	public void setSellTime(String sellTime) {
		this.sellTime = sellTime;
	}	
}
