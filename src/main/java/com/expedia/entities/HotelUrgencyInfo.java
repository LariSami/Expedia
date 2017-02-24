package com.expedia.entities;

public class HotelUrgencyInfo 
{
	private int airAttachRemainingTime;
	private int numberOfPeopleViewing;
	private int numberOfPeopleBooked;
	private int numberOfRoomsLeft;
	private long lastBookedTime;
	private String almostSoldStatus;
	private String link;
	private String[] almostSoldOutRoomTypeInfoCollection;
	private boolean airAttachEnabled;
	
	public int getNumberOfRoomsLeft() {
		return numberOfRoomsLeft;
	}
	public void setNumberOfRoomsLeft(int numberOfRoomsLeft) {
		this.numberOfRoomsLeft = numberOfRoomsLeft;
	}
	
	public int getAirAttachRemainingTime() {
		return airAttachRemainingTime;
	}
	public void setAirAttachRemainingTime(int airAttachRemainingTime) {
		this.airAttachRemainingTime = airAttachRemainingTime;
	}
	public int getNumberOfPeopleViewing() {
		return numberOfPeopleViewing;
	}
	public void setNumberOfPeopleViewing(int numberOfPeopleViewing) {
		this.numberOfPeopleViewing = numberOfPeopleViewing;
	}
	public int getNumberOfPeopleBooked() {
		return numberOfPeopleBooked;
	}
	public void setNumberOfPeopleBooked(int numberOfPeopleBooked) {
		this.numberOfPeopleBooked = numberOfPeopleBooked;
	}
	public long getLastBookedTime() {
		return lastBookedTime;
	}
	public void setLastBookedTime(long lastBookedTime) {
		this.lastBookedTime = lastBookedTime;
	}
	public String getAlmostSoldStatus() {
		return almostSoldStatus;
	}
	public void setAlmostSoldStatus(String almostSoldStatus) {
		this.almostSoldStatus = almostSoldStatus;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String[] getAlmostSoldOutRoomTypeInfoCollection() {
		return almostSoldOutRoomTypeInfoCollection;
	}
	public void setAlmostSoldOutRoomTypeInfoCollection(String[] almostSoldOutRoomTypeInfoCollection) {
		this.almostSoldOutRoomTypeInfoCollection = almostSoldOutRoomTypeInfoCollection;
	}
	public boolean isAirAttachEnabled() {
		return airAttachEnabled;
	}
	public void setAirAttachEnabled(boolean airAttachEnabled) {
		this.airAttachEnabled = airAttachEnabled;
	}
	
}
