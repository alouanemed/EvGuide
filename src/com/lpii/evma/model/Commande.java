package com.lpii.evma.model;

public class Commande {
	
	private String commande_id = "commande_id";
	private int commande_user_id ;
	private String commande_dateTime = "commande_dateTime";
	private String commande_statut = "commande_statut";
	
	
	public Commande(String commande_id, int commande_user_id,
			String commande_dateTime, String commande_statut) {
		super();
		this.commande_id = commande_id;
		this.commande_user_id = commande_user_id;
		this.commande_dateTime = commande_dateTime;
		this.commande_statut = commande_statut;
	}


	public String getCommande_id() {
		return commande_id;
	}


	public void setCommande_id(String commande_id) {
		this.commande_id = commande_id;
	}


	public int getCommande_user_id() {
		return commande_user_id;
	}


	public void setCommande_user_id(int commande_user_id) {
		this.commande_user_id = commande_user_id;
	}


	public String getCommande_dateTime() {
		return commande_dateTime;
	}


	public void setCommande_dateTime(String commande_dateTime) {
		this.commande_dateTime = commande_dateTime;
	}


	public String getCommande_statut() {
		return commande_statut;
	}


	public void setCommande_statut(String commande_statut) {
		this.commande_statut = commande_statut;
	}


	@Override
	public String toString() {
		return "Commande [commande_id=" + commande_id + ", commande_user_id="
				+ commande_user_id + ", commande_dateTime=" + commande_dateTime
				+ ", commande_statut=" + commande_statut + "]";
	}
	
	
	

}
