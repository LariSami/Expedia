package com.expedia.entities;
public class BigOffer 
{
	private OfferInfo offerInfo;
	private UserInfo userInfo;
	private Offers offers;

	public Offers getOffers() {
		return offers;
	}

	public void setOffers(Offers offers) {
		this.offers = offers;
	}

	public UserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	public OfferInfo getOfferInfo() {
		return offerInfo;
	}

	public void setOfferInfo(OfferInfo offerInfo) {
		this.offerInfo = offerInfo;
	}
}
