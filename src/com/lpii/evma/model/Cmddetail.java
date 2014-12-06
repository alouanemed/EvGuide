package com.lpii.evma.model;

public class Cmddetail {
	
	private String cmddetail_id = "cmddetail_id";
	private String cmddetail_Qty = "cmddetail_Qty";
	private String cmddetail_commande_id = "cmddetail_commande_id";
	public Cmddetail(String cmddetail_id, String cmddetail_Qty,
			String cmddetail_commande_id) {
		super();
		this.cmddetail_id = cmddetail_id;
		this.cmddetail_Qty = cmddetail_Qty;
		this.cmddetail_commande_id = cmddetail_commande_id;
	}
	public String getCmddetail_id() {
		return cmddetail_id;
	}
	public void setCmddetail_id(String cmddetail_id) {
		this.cmddetail_id = cmddetail_id;
	}
	public String getCmddetail_Qty() {
		return cmddetail_Qty;
	}
	public void setCmddetail_Qty(String cmddetail_Qty) {
		this.cmddetail_Qty = cmddetail_Qty;
	}
	public String getCmddetail_commande_id() {
		return cmddetail_commande_id;
	}
	public void setCmddetail_commande_id(String cmddetail_commande_id) {
		this.cmddetail_commande_id = cmddetail_commande_id;
	}
	@Override
	public String toString() {
		return "Cmddetail [cmddetail_id=" + cmddetail_id + ", cmddetail_Qty="
				+ cmddetail_Qty + ", cmddetail_commande_id="
				+ cmddetail_commande_id + "]";
	}


	
}
