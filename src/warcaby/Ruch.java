package warcaby;

public class Ruch {
	private int stareX, stareY, noweX, noweY, zbicieX, zbicieY;
	private boolean czyBicie;
	private Ruch nastepneBicie = null, poprzednieBicie = null;
	public int ileBic;
	
	public Ruch (int stareX, int stareY, int noweX, int noweY) {
		this.stareX = stareX;
		this.stareY = stareY;
		this.noweX = noweX;
		this.noweY = noweY;
		czyBicie = false;
	} 
	
	public Ruch (int stareX, int stareY, int noweX, int noweY, int zbicieX, int zbicieY) {
		this.stareX = stareX;
		this.stareY = stareY;
		this.noweX = noweX;
		this.noweY = noweY;
		this.zbicieX = zbicieX;
		this.zbicieY = zbicieY;
		czyBicie = true;
	}
	
	public Ruch (int stareX, int stareY, int noweX, int noweY, int zbicieX, int zbicieY, Ruch ruch) {
		this.stareX = stareX;
		this.stareY = stareY;
		this.noweX = noweX;
		this.noweY = noweY;
		this.zbicieX = zbicieX;
		this.zbicieY = zbicieY;
		czyBicie = true;
		poprzednieBicie = ruch;
	}
	
	public Ruch (Ruch ruch) {
		stareX = ruch.getStareX();
		stareY = ruch.getStareY();
		noweX = ruch.getNoweX();
		noweY = ruch.getNoweY();
		zbicieX = ruch.getZbicieX();
		zbicieY = ruch.getZbicieY();
		czyBicie = ruch.getCzyBicie();
		poprzednieBicie = ruch.getPoprzednieBicie();
		nastepneBicie = ruch.getNastepneBicie();
	}
	
	public int getStareX() {
		return stareX;
	}
	
	public int getStareY() {
		return stareY;
	}
	
	public int getNoweX() {
		return noweX;
	}
	
	public int getNoweY() {
		return noweY;
	}
	
	public int getZbicieX() {
		return zbicieX;
	}
	
	public int getZbicieY() {
		return zbicieY;
	}
	
	public boolean getCzyBicie() {
		return czyBicie;
	}
	
	public Ruch getNastepneBicie() {
		return nastepneBicie;
	}
	
	public void setNastepneBicie (Ruch ruch) {
		nastepneBicie = ruch;
	}
	
	public Ruch getPoprzednieBicie() {
		return poprzednieBicie;
	}
}