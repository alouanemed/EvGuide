package com.lpii.evma.model;

public class Billet {
	 private String Billet_id = "Billet_id";
	 private String Billet_pack_id = "Billet_pack_id";
	 private String billet_code = "billet_code";
	 private String billet_cmddetail_id = "billet_cmddetail_id";
	public String getBillet_id() {
		return Billet_id;
	}
	public void setBillet_id(String billet_id) {
		Billet_id = billet_id;
	}
	public String getBillet_pack_id() {
		return Billet_pack_id;
	}
	public void setBillet_pack_id(String billet_pack_id) {
		Billet_pack_id = billet_pack_id;
	}
	public String getBillet_code() {
		return billet_code;
	}
	public void setBillet_code(String billet_code) {
		this.billet_code = billet_code;
	}
	public String getBillet_cmddetail_id() {
		return billet_cmddetail_id;
	}
	public void setBillet_cmddetail_id(String billet_cmddetail_id) {
		this.billet_cmddetail_id = billet_cmddetail_id;
	}
	public Billet(String billet_id, String billet_pack_id, String billet_code,
			String billet_cmddetail_id) {
		super();
		Billet_id = billet_id;
		Billet_pack_id = billet_pack_id;
		this.billet_code = billet_code;
		this.billet_cmddetail_id = billet_cmddetail_id;
	}
	@Override
	public String toString() {
		return "Billet [Billet_id=" + Billet_id + ", Billet_pack_id="
				+ Billet_pack_id + ", billet_code=" + billet_code
				+ ", billet_cmddetail_id=" + billet_cmddetail_id + "]";
	}


	 
	 
}
