package com.lpii.evma.model;

public class EventPack {
	
	String TAG_pack_Title = "pack_Title";
	String TAG_pack_id = "TAG_pack_id";
    String TAG_pack_Description = "pack_Description";
    String TAG_pack_Debut_Vente_Date = "pack_Debut_Vente_Date";
    String TAG_pack_Fin_Vente_Date = "pack_Fin_Vente_Date";
    String TAG_pack_isVisible= "pack_isVisible";
    String TAG_pack_Price = "pack_Price";
    String TAG_pack_Qty = "pack_Qty";
    String TAG_pack_event_id = "Organisateur";
    String TAG_pack_fees = "pack_fees";
    String TAG_pack_Solde = "pack_Solde";
    String TAG_pack_Statut_Pack = "pack_Statut_Pack";
	public String getTAG_pack_Title() {
		return TAG_pack_Title;
	}
	public void setTAG_pack_Title(String tAG_pack_Title) {
		TAG_pack_Title = tAG_pack_Title;
	}
	public String getTAG_pack_id() {
		return TAG_pack_id;
	}
	public void setTAG_pack_id(String tAG_pack_id) {
		TAG_pack_id = tAG_pack_id;
	}
	public String getTAG_pack_Description() {
		return TAG_pack_Description;
	}
	public void setTAG_pack_Description(String tAG_pack_Description) {
		TAG_pack_Description = tAG_pack_Description;
	}
	public String getTAG_pack_Debut_Vente_Date() {
		return TAG_pack_Debut_Vente_Date;
	}
	public void setTAG_pack_Debut_Vente_Date(String tAG_pack_Debut_Vente_Date) {
		TAG_pack_Debut_Vente_Date = tAG_pack_Debut_Vente_Date;
	}
	public String getTAG_pack_Fin_Vente_Date() {
		return TAG_pack_Fin_Vente_Date;
	}
	public void setTAG_pack_Fin_Vente_Date(String tAG_pack_Fin_Vente_Date) {
		TAG_pack_Fin_Vente_Date = tAG_pack_Fin_Vente_Date;
	}
	public String getTAG_pack_isVisible() {
		return TAG_pack_isVisible;
	}
	public void setTAG_pack_isVisible(String tAG_pack_isVisible) {
		TAG_pack_isVisible = tAG_pack_isVisible;
	}
	public String getTAG_pack_Price() {
		return TAG_pack_Price;
	}
	public void setTAG_pack_Price(String tAG_pack_Price) {
		TAG_pack_Price = tAG_pack_Price;
	}
	public String getTAG_pack_Qty() {
		return TAG_pack_Qty;
	}
	public void setTAG_pack_Qty(String tAG_pack_Qty) {
		TAG_pack_Qty = tAG_pack_Qty;
	}
	public String getTAG_pack_event_id() {
		return TAG_pack_event_id;
	}
	public void setTAG_pack_event_id(String tAG_pack_event_id) {
		TAG_pack_event_id = tAG_pack_event_id;
	}
	public String getTAG_pack_fees() {
		return TAG_pack_fees;
	}
	public void setTAG_pack_fees(String tAG_pack_fees) {
		TAG_pack_fees = tAG_pack_fees;
	}
	public String getTAG_pack_Solde() {
		return TAG_pack_Solde;
	}
	public void setTAG_pack_Solde(String tAG_pack_Solde) {
		TAG_pack_Solde = tAG_pack_Solde;
	}
	public String getTAG_pack_Statut_Pack() {
		return TAG_pack_Statut_Pack;
	}
	public void setTAG_pack_Statut_Pack(String tAG_pack_Statut_Pack) {
		TAG_pack_Statut_Pack = tAG_pack_Statut_Pack;
	}
	public EventPack(String tAG_pack_Title, String tAG_pack_id,
			String tAG_pack_Description, String tAG_pack_Debut_Vente_Date,
			String tAG_pack_Fin_Vente_Date, String tAG_pack_isVisible,
			String tAG_pack_Price, String tAG_pack_Qty,
			String tAG_pack_event_id, String tAG_pack_fees,
			String tAG_pack_Solde, String tAG_pack_Statut_Pack) {
		super();
		TAG_pack_Title = tAG_pack_Title;
		TAG_pack_id = tAG_pack_id;
		TAG_pack_Description = tAG_pack_Description;
		TAG_pack_Debut_Vente_Date = tAG_pack_Debut_Vente_Date;
		TAG_pack_Fin_Vente_Date = tAG_pack_Fin_Vente_Date;
		TAG_pack_isVisible = tAG_pack_isVisible;
		TAG_pack_Price = tAG_pack_Price;
		TAG_pack_Qty = tAG_pack_Qty;
		TAG_pack_event_id = tAG_pack_event_id;
		TAG_pack_fees = tAG_pack_fees;
		TAG_pack_Solde = tAG_pack_Solde;
		TAG_pack_Statut_Pack = tAG_pack_Statut_Pack;
	}
	@Override
	public String toString() {
		return "EventPack [TAG_pack_Title=" + TAG_pack_Title + ", TAG_pack_id="
				+ TAG_pack_id + ", TAG_pack_Description="
				+ TAG_pack_Description + ", TAG_pack_Debut_Vente_Date="
				+ TAG_pack_Debut_Vente_Date + ", TAG_pack_Fin_Vente_Date="
				+ TAG_pack_Fin_Vente_Date + ", TAG_pack_isVisible="
				+ TAG_pack_isVisible + ", TAG_pack_Price=" + TAG_pack_Price
				+ ", TAG_pack_Qty=" + TAG_pack_Qty + ", TAG_pack_event_id="
				+ TAG_pack_event_id + ", TAG_pack_fees=" + TAG_pack_fees
				+ ", TAG_pack_Solde=" + TAG_pack_Solde
				+ ", TAG_pack_Statut_Pack=" + TAG_pack_Statut_Pack + "]";
	}
     
    

}
