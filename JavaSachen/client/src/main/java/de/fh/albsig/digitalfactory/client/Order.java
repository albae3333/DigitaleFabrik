package de.fh.albsig.digitalfactory.client;

public class Order {
	
	private String aufnr;
	private String plnbez;
	private String gstrp;
	private String gamng;
	private String gme;
	
	public Order() {}
	
	public Order(final String aufnr, final String plnbez, 
			final String gstrp, final String gamng, 
			final String gme) 
	{
		this.aufnr = aufnr; 	//Auftragsnummer
		this.plnbez = plnbez;	//Produktbezeichnung
		this.gstrp = gstrp;		//Starttermin
		this.gamng = gamng;		//Menge
		this.gme = gme;			//Einheit
	}
	
	@Override
	public String toString()
	{
		return "Order [aufnr=" + this.aufnr
				+ ", plnbez=" + this.plnbez
				+ ", gstrp=" + this.gstrp
				+ ", gamng=" + this.gamng
				+ ", gme=" + this.gme + "]";
	}

	public String getAufnr() {
		return aufnr;
	}

	public void setAufnr(String aufnr) {
		this.aufnr = aufnr;
	}

	public String getPlnbez() {
		return plnbez;
	}

	public void setPlnbez(String plnbez) {
		this.plnbez = plnbez;
	}

	public String getGstrp() {
		return gstrp;
	}

	public void setGstrp(String gstrp) {
		this.gstrp = gstrp;
	}

	public String getGamng() {
		return gamng;
	}

	public void setGamng(String gamng) {
		this.gamng = gamng;
	}

	public String getGme() {
		return gme;
	}

	public void setGme(String gme) {
		this.gme = gme;
	}
	
	
	
}
