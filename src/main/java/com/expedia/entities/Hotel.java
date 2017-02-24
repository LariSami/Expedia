package com.expedia.entities;

public class Hotel 
{
	private OfferDateRange offerDateRange;
	private Destination destination;
	private HotelInfo hotelInfo;
	private HotelUrgencyInfo hotelUrgencyInfo;
	private HotelPricingInfo hotelPricingInfo;
	private HotelUrls hotelUrls;
	private HotelScores hotelScores;
	
	
	public HotelScores getHotelScores() {
		return hotelScores;
	}
	public void setHotelScores(HotelScores hotelScores) {
		this.hotelScores = hotelScores;
	}
	public HotelUrls getHotelUrls() {
		return hotelUrls;
	}
	public void setHotelUrls(HotelUrls hotelUrls) {
		this.hotelUrls = hotelUrls;
	}
	public HotelPricingInfo getHotelPricingInfo() {
		return hotelPricingInfo;
	}
	public void setHotelPricingInfo(HotelPricingInfo hotelPricingInfo) {
		this.hotelPricingInfo = hotelPricingInfo;
	}
	public OfferDateRange getOfferDateRange() {
		return offerDateRange;
	}
	public void setOfferDateRange(OfferDateRange offerDateRange) {
		this.offerDateRange = offerDateRange;
	}
	public Destination getDestination() {
		return destination;
	}
	public void setDestination(Destination destination) {
		this.destination = destination;
	}
	public HotelInfo getHotelInfo() {
		return hotelInfo;
	}
	public void setHotelInfo(HotelInfo hotelInfo) {
		this.hotelInfo = hotelInfo;
	}
	
	public HotelUrgencyInfo getHotelUrgencyInfo() {
		return hotelUrgencyInfo;
	}
	public void setHotelUrgencyInfo(HotelUrgencyInfo hotelUrgencyInfo) {
		this.hotelUrgencyInfo = hotelUrgencyInfo;
	}
}
