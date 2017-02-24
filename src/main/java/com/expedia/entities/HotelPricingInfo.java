package com.expedia.entities;

public class HotelPricingInfo 
{
	private double averagePriceValue;
	private double totalPriceValue;
	private double originalPricePerNight;
	private double hotelTotalBaseRate;
	private double hotelTotalTaxesAndFees;
	private String currency;
	private double hotelTotalMandatoryTaxesAndFees;
	private double percentSavings;
	private boolean drr;
	
	public double getAveragePriceValue() {
		return averagePriceValue;
	}
	public void setAveragePriceValue(double averagePriceValue) {
		this.averagePriceValue = averagePriceValue;
	}
	public double getTotalPriceValue() {
		return totalPriceValue;
	}
	public void setTotalPriceValue(double totalPriceValue) {
		this.totalPriceValue = totalPriceValue;
	}
	public double getOriginalPricePerNight() {
		return originalPricePerNight;
	}
	public void setOriginalPricePerNight(double originalPricePerNight) {
		this.originalPricePerNight = originalPricePerNight;
	}
	public double getHotelTotalBaseRate() {
		return hotelTotalBaseRate;
	}
	public void setHotelTotalBaseRate(double hotelTotalBaseRate) {
		this.hotelTotalBaseRate = hotelTotalBaseRate;
	}
	public double getHotelTotalTaxesAndFees() {
		return hotelTotalTaxesAndFees;
	}
	public void setHotelTotalTaxesAndFees(double hotelTotalTaxesAndFees) {
		this.hotelTotalTaxesAndFees = hotelTotalTaxesAndFees;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public double getHotelTotalMandatoryTaxesAndFees() {
		return hotelTotalMandatoryTaxesAndFees;
	}
	public void setHotelTotalMandatoryTaxesAndFees(double hotelTotalMandatoryTaxesAndFees) {
		this.hotelTotalMandatoryTaxesAndFees = hotelTotalMandatoryTaxesAndFees;
	}
	public double getPercentSavings() {
		return percentSavings;
	}
	public void setPercentSavings(double percentSavings) {
		this.percentSavings = percentSavings;
	}
	public boolean isDrr() {
		return drr;
	}
	public void setDrr(boolean drr) {
		this.drr = drr;
	}
}
