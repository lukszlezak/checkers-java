package warcaby;

public class Pionek {
	private boolean czyPierwszegoGracza, czyDamka;
	
	public Pionek (boolean czyPierwszegoGracza) {
		this.czyPierwszegoGracza = czyPierwszegoGracza;
		czyDamka = false;
	}
	
	public Pionek (Pionek pionek) {
		czyPierwszegoGracza = pionek.getCzyPierwszegoGracza();
		czyDamka = pionek.getCzyDamka();
	}
	
	public boolean getCzyPierwszegoGracza() {
		return czyPierwszegoGracza;
	}
	public boolean getCzyDamka() {
		return czyDamka;
	}
	public void setZrobDamke() {
		czyDamka = true;
	}
}