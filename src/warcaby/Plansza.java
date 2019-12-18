package warcaby;

import java.util.LinkedList;

public class Plansza {
	private int ileFigurPierwszego = 12, ileFigurDrugiego = 12;
	private Pionek[][] plansza;
	
	public Plansza() {
		plansza = new Pionek[8][8];
		for (int x = 0 ; x < 8 ; ++x) 
			for (int y = 0 ; y < 8 ; ++y) {
				if ((x+y) % 2 == 1 && x != 3 && x != 4) {
					if (x < 3) plansza[x][y] = new Pionek (false);
					else if (x > 4) plansza[x][y] = new Pionek (true);
				}
				else plansza[x][y] = null;
			}
	}
	
	public Plansza (Plansza plansza, Ruch ruch) {
		ileFigurPierwszego = plansza.getIleFigurPierwszego();
		ileFigurDrugiego = plansza.getIleFigurDrugiego();
		this.plansza = new Pionek[8][8];
		for (int x = 0 ; x < 8 ; ++x) 
			for (int y = 0 ; y < 8 ; ++y) 
				this.plansza[x][y] = plansza.getPlansza()[x][y] != null ? new Pionek (plansza.getPlansza()[x][y]) : null;
		wykonajRuch (ruch);
	}
	
	public Pionek[][] getPlansza() {
		return plansza;
	}

	public int getIleFigurPierwszego() {
		return ileFigurPierwszego;
	}
	
	public int getIleFigurDrugiego() {
		return ileFigurDrugiego;
	}
	
	public void setIleFigurPierwszego (int x) {
		ileFigurPierwszego = x;
	}
	
	public void setIleFigurDrugiego (int x) {
		ileFigurDrugiego = x;
	}
	
	private boolean czyWPlanszy (int x, int y) {
		return x > -1 && x < 8 && y > -1 && y < 8;
	}
	
	private void promocjaNaDamke (int x, int y) {
		if (plansza[x][y].getCzyPierwszegoGracza() && !plansza[x][y].getCzyDamka() && x == 0) plansza[x][y].setZrobDamke();
		if (!plansza[x][y].getCzyPierwszegoGracza() && !plansza[x][y].getCzyDamka() && x == 7) plansza[x][y].setZrobDamke();
	}
	
	public int ocenSytuacje (int fOceny) {
		switch (fOceny)  {
			case 1: return fOceny1();
			case 2: return fOceny2();
			case 3: return fOceny3();
			case 4: return fOceny4();
			default: return fOceny1();
		}
	}
	
	private int fOceny1() {
		return  ileFigurPierwszego - ileFigurDrugiego;
	}
	
	private int fOceny2() {
		int a = 0, b = 0;
		for (int x = 0 ; x < 8 ; ++x) {
			for (int y = 0 ; y < 8 ; ++y) {
				if (plansza[x][y] != null) {
					if (plansza[x][y].getCzyPierwszegoGracza())
						a += plansza[x][y].getCzyDamka() ? 2 : 1;
					else
						b += plansza[x][y].getCzyDamka() ? 2 : 1;
				}
			}
		}
		return a-b;
	}
	
	private int fOceny3() {
		int a = 0, b = 0;
		for (int x = 0 ; x < 8 ; ++x) {
			for (int y = 0 ; y < 8 ; ++y) {
				if (plansza[x][y] != null) {
					if (plansza[x][y].getCzyPierwszegoGracza())
						a += plansza[x][y].getCzyDamka() ? 1 : 2;
					else
						b += plansza[x][y].getCzyDamka() ? 1 : 2;
				}
			}
		}
		return a-b;
	}
	
	private int fOceny4() {
		int  a = 0, b = 0;
		for (int x = 0 ; x < 8 ; ++x) {
			for (int y = 0 ; y < 8 ; ++y) {
				if (plansza[x][y] != null) {
					if (plansza[x][y].getCzyPierwszegoGracza()) {
						a += plansza[x][y].getCzyDamka() ? (priorytet(x,y) + bezpieczenstwo(x,y)) * 50 : (priorytet(x,y) + bezpieczenstwo(x,y)) * 10;
					}
					else {
						b += plansza[x][y].getCzyDamka() ? (priorytet(x,y) + bezpieczenstwo(x,y)) * 50 : (priorytet(x,y) + bezpieczenstwo(x,y)) * 10;
					}
				}
			}
		}
		return a-b;
	}
	
	private int priorytet (int x, int y) {
		if (x == 0 || x == 7 || y == 0 || y == 7) return 4;
		else
			if  (x == 1 || x == 6 || y == 1 || y == 6) return 3;
			else
				if (x == 2 || x == 5 || y == 2 || y == 5) return 2;
				else
					return 1;
	}
	
	private int bezpieczenstwo (int x, int y) {
		boolean gracz = plansza[x][y].getCzyPierwszegoGracza();
		int wartosc = 0;
		if (x == 0 || x == 7 || y == 0 || y == 7) return 4;
		else {
			if (plansza[x-1][y-1] != null && plansza[x-1][y-1].getCzyPierwszegoGracza() != gracz && plansza[x+1][y+1] == null) --wartosc;
			if (plansza[x-1][y+1] != null && plansza[x-1][y+1].getCzyPierwszegoGracza() != gracz && plansza[x+1][y-1] == null) --wartosc;
			if (plansza[x+1][y-1] != null && plansza[x+1][y-1].getCzyPierwszegoGracza() != gracz && plansza[x-1][y+1] == null) --wartosc;
			if (plansza[x+1][y+1] != null && plansza[x+1][y+1].getCzyPierwszegoGracza() != gracz && plansza[x-1][y-1] == null) --wartosc;
		}
		return wartosc;
	}
	
	public int koniecGry() {
		if (ileFigurPierwszego == 0) return 1;
		if (ileFigurDrugiego == 0) return 2;
		return -1;
	}
	
	public void wesBicia (boolean czyPierwszyGracz, LinkedList <Ruch> mozliweRuchy) {
		for (int x = 0 ; x < 8 ; ++x) 
			for (int y = 0 ; y < 8 ; ++y) 
				if (plansza[x][y] != null && plansza[x][y].getCzyPierwszegoGracza() == czyPierwszyGracz) {
					if (!plansza[x][y].getCzyDamka()) {
						if (czyPierwszyGracz) {
							if (czyWPlanszy(x-2, y-2) && plansza[x-2][y-2] == null && plansza[x-1][y-1] != null && plansza[x-1][y-1].getCzyPierwszegoGracza() != czyPierwszyGracz)
								wielokrotneBicia (czyPierwszyGracz, mozliweRuchy, new Ruch (x, y, x-2, y-2, x-1, y-1), x, y);
							if (czyWPlanszy(x-2, y+2) && plansza[x-2][y+2] == null && plansza[x-1][y+1] != null && plansza[x-1][y+1].getCzyPierwszegoGracza() != czyPierwszyGracz)
								wielokrotneBicia (czyPierwszyGracz, mozliweRuchy, new Ruch (x, y, x-2, y+2, x-1, y+1), x, y);
						}
						else {
							if (czyWPlanszy(x+2, y-2) && plansza[x+2][y-2] == null && plansza[x+1][y-1] != null && plansza[x+1][y-1].getCzyPierwszegoGracza() != czyPierwszyGracz)
								wielokrotneBicia (czyPierwszyGracz, mozliweRuchy, new Ruch (x, y, x+2, y-2, x+1, y-1), x, y);
							if (czyWPlanszy(x+2, y+2) && plansza[x+2][y+2] == null && plansza[x+1][y+1] != null && plansza[x+1][y+1].getCzyPierwszegoGracza() != czyPierwszyGracz)
								wielokrotneBicia (czyPierwszyGracz, mozliweRuchy, new Ruch (x, y, x+2, y+2, x+1, y+1), x, y);
						}
					}
					else {
						if (czyWPlanszy(x-2, y-2) && plansza[x-2][y-2] == null && plansza[x-1][y-1] != null && plansza[x-1][y-1].getCzyPierwszegoGracza() != czyPierwszyGracz)
							wielokrotneBicia (czyPierwszyGracz, mozliweRuchy, new Ruch (x, y, x-2, y-2, x-1, y-1), x, y);
						if (czyWPlanszy(x-2, y+2) && plansza[x-2][y+2] == null && plansza[x-1][y+1] != null && plansza[x-1][y+1].getCzyPierwszegoGracza() != czyPierwszyGracz)
							wielokrotneBicia (czyPierwszyGracz, mozliweRuchy, new Ruch (x, y, x-2, y+2, x-1, y+1), x, y);
						if (czyWPlanszy(x+2, y-2) && plansza[x+2][y-2] == null && plansza[x+1][y-1] != null && plansza[x+1][y-1].getCzyPierwszegoGracza() != czyPierwszyGracz)
							wielokrotneBicia (czyPierwszyGracz, mozliweRuchy, new Ruch (x, y, x+2, y-2, x+1, y-1), x, y);
						if (czyWPlanszy(x+2, y+2) && plansza[x+2][y+2] == null && plansza[x+1][y+1] != null && plansza[x+1][y+1].getCzyPierwszegoGracza() != czyPierwszyGracz)
							wielokrotneBicia (czyPierwszyGracz, mozliweRuchy, new Ruch (x, y, x+2, y+2, x+1, y+1), x, y);
					}
				}
	}
	
	private void wielokrotneBicia (boolean czyPierwszyGracz, LinkedList <Ruch> mozliweRuchy, Ruch ruch, int startX, int startY) {		
		int x = ruch.getNoweX(), y = ruch.getNoweY(), tmpX = ruch.getZbicieX(), tmpY = ruch.getZbicieY();
		Pionek tmpPionek = plansza[tmpX][tmpY];
		plansza[ruch.getZbicieX()][ruch.getZbicieY()] = null;
		if (!plansza[startX][startY].getCzyDamka()) {
			if (czyPierwszyGracz) {
				if (czyWPlanszy(x-2, y-2) && plansza[x-2][y-2] == null && plansza[x-1][y-1] != null && plansza[x-1][y-1].getCzyPierwszegoGracza() != czyPierwszyGracz ||
						czyWPlanszy(x-2, y+2) && plansza[x-2][y+2] == null && plansza[x-1][y+1] != null && plansza[x-1][y+1].getCzyPierwszegoGracza() != czyPierwszyGracz) {
					if (czyWPlanszy(x-2, y-2) && plansza[x-2][y-2] == null && plansza[x-1][y-1] != null && plansza[x-1][y-1].getCzyPierwszegoGracza() != czyPierwszyGracz) {
						Ruch nowyRuch = new Ruch (x, y, x-2, y-2, x-1, y-1, ruch);
						ruch.setNastepneBicie (nowyRuch);
						wielokrotneBicia (czyPierwszyGracz, mozliweRuchy, nowyRuch, startX, startY);
					}
					if (czyWPlanszy(x-2, y+2) && plansza[x-2][y+2] == null && plansza[x-1][y+1] != null && plansza[x-1][y+1].getCzyPierwszegoGracza() != czyPierwszyGracz) {
						Ruch nowyRuch = new Ruch (x, y, x-2, y+2, x-1, y+1, ruch);
						ruch.setNastepneBicie (nowyRuch);
						wielokrotneBicia (czyPierwszyGracz, mozliweRuchy, nowyRuch, startX, startY);
					}
				}
				else {
					while (ruch.getPoprzednieBicie() != null) ruch = new Ruch (ruch.getPoprzednieBicie());
					mozliweRuchy.add (ruch);
				}
			}
			else {
				if (czyWPlanszy(x+2, y-2) && plansza[x+2][y-2] == null && plansza[x+1][y-1] != null && plansza[x+1][y-1].getCzyPierwszegoGracza() != czyPierwszyGracz ||
						czyWPlanszy(x+2, y+2) && plansza[x+2][y+2] == null && plansza[x+1][y+1] != null && plansza[x+1][y+1].getCzyPierwszegoGracza() != czyPierwszyGracz) {
					if (czyWPlanszy(x+2, y-2) && plansza[x+2][y-2] == null && plansza[x+1][y-1] != null && plansza[x+1][y-1].getCzyPierwszegoGracza() != czyPierwszyGracz) {
						Ruch nowyRuch = new Ruch (x, y, x+2, y-2, x+1, y-1, ruch);
						ruch.setNastepneBicie (nowyRuch);
						wielokrotneBicia (czyPierwszyGracz, mozliweRuchy, nowyRuch, startX, startY);
					}
					if (czyWPlanszy(x+2, y+2) && plansza[x+2][y+2] == null && plansza[x+1][y+1] != null && plansza[x+1][y+1].getCzyPierwszegoGracza() != czyPierwszyGracz) {
						Ruch nowyRuch = new Ruch (x, y, x+2, y+2, x+1, y+1, ruch);
						ruch.setNastepneBicie (nowyRuch);
						wielokrotneBicia (czyPierwszyGracz, mozliweRuchy, nowyRuch, startX, startY);
					}
				}
				else {
					while (ruch.getPoprzednieBicie() != null) ruch = new Ruch (ruch.getPoprzednieBicie());
					mozliweRuchy.add (ruch);
				}
			}
		}
		else {
			if (czyWPlanszy(x-2, y-2) && plansza[x-2][y-2] == null && plansza[x-1][y-1] != null && plansza[x-1][y-1].getCzyPierwszegoGracza() != czyPierwszyGracz ||
					czyWPlanszy(x-2, y+2) && plansza[x-2][y+2] == null && plansza[x-1][y+1] != null && plansza[x-1][y+1].getCzyPierwszegoGracza() != czyPierwszyGracz ||
					czyWPlanszy(x+2, y-2) && plansza[x+2][y-2] == null && plansza[x+1][y-1] != null && plansza[x+1][y-1].getCzyPierwszegoGracza() != czyPierwszyGracz ||
					czyWPlanszy(x+2, y+2) && plansza[x+2][y+2] == null && plansza[x+1][y+1] != null && plansza[x+1][y+1].getCzyPierwszegoGracza() != czyPierwszyGracz) {
				if (czyWPlanszy(x-2, y-2) && plansza[x-2][y-2] == null && plansza[x-1][y-1] != null && plansza[x-1][y-1].getCzyPierwszegoGracza() != czyPierwszyGracz) {
					Ruch nowyRuch = new Ruch (x, y, x-2, y-2, x-1, y-1, ruch);
					ruch.setNastepneBicie (nowyRuch);
					wielokrotneBicia (czyPierwszyGracz, mozliweRuchy, nowyRuch, startX, startY);
				}
				if (czyWPlanszy(x-2, y+2) && plansza[x-2][y+2] == null && plansza[x-1][y+1] != null && plansza[x-1][y+1].getCzyPierwszegoGracza() != czyPierwszyGracz) {
					Ruch nowyRuch = new Ruch (x, y, x-2, y+2, x-1, y+1, ruch);
					ruch.setNastepneBicie (nowyRuch);
					wielokrotneBicia (czyPierwszyGracz, mozliweRuchy, nowyRuch, startX, startY);
				}
				if (czyWPlanszy(x+2, y-2) && plansza[x+2][y-2] == null && plansza[x+1][y-1] != null && plansza[x+1][y-1].getCzyPierwszegoGracza() != czyPierwszyGracz) {
					Ruch nowyRuch = new Ruch (x, y, x+2, y-2, x+1, y-1, ruch);
					ruch.setNastepneBicie (nowyRuch);
					wielokrotneBicia (czyPierwszyGracz, mozliweRuchy, nowyRuch, startX, startY);
				}
				if (czyWPlanszy(x+2, y+2) && plansza[x+2][y+2] == null && plansza[x+1][y+1] != null && plansza[x+1][y+1].getCzyPierwszegoGracza() != czyPierwszyGracz) {
					Ruch nowyRuch = new Ruch (x, y, x+2, y+2, x+1, y+1, ruch);
					ruch.setNastepneBicie (nowyRuch);
					wielokrotneBicia (czyPierwszyGracz, mozliweRuchy, nowyRuch, startX, startY);
				}
			}
			else {
				while (ruch.getPoprzednieBicie() != null) ruch = new Ruch (ruch.getPoprzednieBicie());
				mozliweRuchy.add (ruch);
			}
		}
		plansza[tmpX][tmpY] = tmpPionek; tmpPionek = null;
	}
	
	public void wesRuchy (boolean czyPierwszyGracz, LinkedList <Ruch> mozliweRuchy) {
		for (int x = 0 ; x < 8 ; ++x) 
			for (int y = 0 ; y < 8 ; ++y) 
				if (plansza[x][y] != null && plansza[x][y].getCzyPierwszegoGracza() == czyPierwszyGracz) {
					if (!plansza[x][y].getCzyDamka()) {
						if (czyPierwszyGracz) {
							if (czyWPlanszy ( (x-1),  (y-1)) && plansza[x-1][y-1] == null)  
								mozliweRuchy.add (new Ruch (x, y,  (x-1),  (y-1))); 
							
							if (czyWPlanszy ( (x-1),  (y+1)) && plansza[x-1][y+1] == null)  
								mozliweRuchy.add (new Ruch (x, y,  (x-1),  (y+1)));
						}
						else {
							if (czyWPlanszy ( (x+1),  (y-1)) && plansza[x+1][y-1] == null) 
								mozliweRuchy.add (new Ruch (x, y,  (x+1),  (y-1)));
							if (czyWPlanszy ( (x+1),  (y+1)) && plansza[x+1][y+1] == null) 
								mozliweRuchy.add (new Ruch (x, y,  (x+1),  (y+1)));
						}
					}
					else {
						if (czyWPlanszy(x-1, y-1) && plansza[x-1][y-1] == null)
							mozliweRuchy.add (new Ruch (x, y, x-1, y-1));
						if (czyWPlanszy(x-1, y+1) && plansza[x-1][y+1] == null)
							mozliweRuchy.add (new Ruch (x, y, x-1, y+1));
						if (czyWPlanszy(x+1,y-1) && plansza[x+1][y-1] == null) 
							mozliweRuchy.add (new Ruch (x, y, x+1, y-1));
						if (czyWPlanszy(x+1,y+1) && plansza[x+1][y+1] == null)
							mozliweRuchy.add (new Ruch (x, y, x+1, y+1));
					}
				}
	}
	
	public void wykonajRuch (Ruch ruch) {
		plansza[ruch.getNoweX()][ruch.getNoweY()] = plansza[ruch.getStareX()][ruch.getStareY()];
		plansza[ruch.getStareX()][ruch.getStareY()] = null;
		if (ruch.getCzyBicie()) {
			if (plansza[ruch.getZbicieX()][ruch.getZbicieY()].getCzyPierwszegoGracza()) --ileFigurPierwszego;
			else if (!plansza[ruch.getZbicieX()][ruch.getZbicieY()].getCzyPierwszegoGracza())--ileFigurDrugiego;
			plansza [ruch.getZbicieX()][ruch.getZbicieY()] = null;
			if (ruch.getNastepneBicie() != null) wykonajRuch (ruch.getNastepneBicie());
			else promocjaNaDamke (ruch.getNoweX(), ruch.getNoweY());
		}
		else promocjaNaDamke (ruch.getNoweX(), ruch.getNoweY());
	}

	public boolean czyJeszczeBicieCzleka (int x, int y) {
		if (!plansza[x][y].getCzyDamka()) {
			if (czyWPlanszy(x-2, y-2) && plansza[x-2][y-2] == null && plansza[x-1][y-1] != null && !plansza[x-1][y-1].getCzyPierwszegoGracza()) 
				return true;
			else if (czyWPlanszy(x-2, y+2) && plansza[x-2][y+2] == null && plansza[x-1][y+1] != null && !plansza[x-1][y+1].getCzyPierwszegoGracza())
				return true;
			else return false;
		}
		else {
			if (czyWPlanszy(x-2, y-2) && plansza[x-2][y-2] == null && plansza[x-1][y-1] != null && !plansza[x-1][y-1].getCzyPierwszegoGracza()) 
				return true;
			else if (czyWPlanszy(x-2, y+2) && plansza[x-2][y+2] == null && plansza[x-1][y+1] != null && !plansza[x-1][y+1].getCzyPierwszegoGracza())
				return true;
			else if (czyWPlanszy(x+2, y-2) && plansza[x+2][y-2] == null && plansza[x+1][y-1] != null && !plansza[x+1][y-1].getCzyPierwszegoGracza()) 
				return true;
			else if (czyWPlanszy(x+2, y+2) && plansza[x+2][y+2] == null && plansza[x+1][y+1] != null && !plansza[x+1][y+1].getCzyPierwszegoGracza())
				return true;
			else return false;
		}
	}
	
	public boolean czyMaRuch (boolean czyPierwszyGracz) {
		for (int x = 0 ; x < 8 ; ++x) 
			for (int y = 0 ; y < 8 ; ++y) 
				if (plansza[x][y] != null && plansza[x][y].getCzyPierwszegoGracza() == czyPierwszyGracz) {
					if (!plansza[x][y].getCzyDamka()) {
						if (czyPierwszyGracz) {
							if (czyWPlanszy ( (x-1),  (y-1)) && plansza[x-1][y-1] == null)  
								return true;
							if (czyWPlanszy ( (x-1),  (y+1)) && plansza[x-1][y+1] == null)  
								return true;
							if (czyWPlanszy(x-2, y-2) && plansza[x-2][y-2] == null && plansza[x-1][y-1] != null && plansza[x-1][y-1].getCzyPierwszegoGracza() != czyPierwszyGracz)
								return true;
							if (czyWPlanszy(x-2, y+2) && plansza[x-2][y+2] == null && plansza[x-1][y+1] != null && plansza[x-1][y+1].getCzyPierwszegoGracza() != czyPierwszyGracz)
								return true;
						}
						else {
							if (czyWPlanszy ( (x+1),  (y-1)) && plansza[x+1][y-1] == null) 
								return true;
							if (czyWPlanszy ( (x+1),  (y+1)) && plansza[x+1][y+1] == null) 
								return true;
							if (czyWPlanszy(x+2, y-2) && plansza[x+2][y-2] == null && plansza[x+1][y-1] != null && plansza[x+1][y-1].getCzyPierwszegoGracza() != czyPierwszyGracz)
								return true;
							if (czyWPlanszy(x+2, y+2) && plansza[x+2][y+2] == null && plansza[x+1][y+1] != null && plansza[x+1][y+1].getCzyPierwszegoGracza() != czyPierwszyGracz)
								return true;
						}
					}
					else {
						if (czyWPlanszy(x-1, y-1) && plansza[x-1][y-1] == null)
							return true;
						if (czyWPlanszy(x-1, y+1) && plansza[x-1][y+1] == null)
							return true;
						if (czyWPlanszy(x+1,y-1) && plansza[x+1][y-1] == null) 
							return true;
						if (czyWPlanszy(x+1,y+1) && plansza[x+1][y+1] == null)
							return true;
						if (czyWPlanszy(x-2, y-2) && plansza[x-2][y-2] == null && plansza[x-1][y-1] != null && plansza[x-1][y-1].getCzyPierwszegoGracza() != czyPierwszyGracz)
							return true;
						if (czyWPlanszy(x-2, y+2) && plansza[x-2][y+2] == null && plansza[x-1][y+1] != null && plansza[x-1][y+1].getCzyPierwszegoGracza() != czyPierwszyGracz)
							return true;
						if (czyWPlanszy(x+2, y-2) && plansza[x+2][y-2] == null && plansza[x+1][y-1] != null && plansza[x+1][y-1].getCzyPierwszegoGracza() != czyPierwszyGracz)
							return true;
						if (czyWPlanszy(x+2, y+2) && plansza[x+2][y+2] == null && plansza[x+1][y+1] != null && plansza[x+1][y+1].getCzyPierwszegoGracza() != czyPierwszyGracz)
							return true;
					}
				}
		return false;
	}
}