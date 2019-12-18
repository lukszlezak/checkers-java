package warcaby;

import java.util.LinkedList;

public class Komputer {
	private int maxGlebokoscDrzewa;
	private Ruch najlepszyRuch;
	private boolean czyMinMax, czyBadac, czySortowac;
	public int iloscWezlow = 0, ileSzukan = 0, fOceny;
	public double czasSzukania = 0.0;
	
	public Komputer (int maxGlebokoscDrzewa, boolean czyMinMax, int ktoryPC, boolean czyBadac, int fOceny, boolean czySortowac) {
		this.maxGlebokoscDrzewa = maxGlebokoscDrzewa;
		this.czyMinMax = czyMinMax;
		this.czyBadac = czyBadac;
		this.fOceny = fOceny;
		this.czySortowac = czySortowac;
	}
	
	public Ruch znajdzRuch (Plansza plansza, boolean czyPierwszyGracz) {
		if (czyBadac) {
			long start = System.nanoTime();
			if (czyMinMax) minMax (plansza, czyPierwszyGracz, 0);
			else alfaBeta (plansza, czyPierwszyGracz, 0, Integer.MIN_VALUE, Integer.MAX_VALUE);
			long stop = System.nanoTime();
			czasSzukania += (double)(stop-start)/1000000;
			++ileSzukan;
		}
		else {
			if (czyMinMax) minMax (plansza, czyPierwszyGracz, 0);
			else alfaBeta (plansza, czyPierwszyGracz, 0, Integer.MIN_VALUE, Integer.MAX_VALUE);
		}
		return najlepszyRuch;
	}

	private int minMax (Plansza plansza, boolean czyPierwszyGracz, int aktGlebokosc) {
		if (czyBadac) ++iloscWezlow;
		if (plansza.koniecGry() == 1) return -88888888;
		if (plansza.koniecGry() == 0) return 88888888;
		if (aktGlebokosc == maxGlebokoscDrzewa) {
			if (!plansza.czyMaRuch (czyPierwszyGracz)) {
				if (czyPierwszyGracz) return -88888888;
				else return 88888888;
			}
			else return plansza.ocenSytuacje (fOceny);
		}
		
		LinkedList <Ruch> mozliweRuchy = new LinkedList <Ruch> ();
		plansza.wesBicia (czyPierwszyGracz, mozliweRuchy);
		if (mozliweRuchy.isEmpty()) plansza.wesRuchy (czyPierwszyGracz, mozliweRuchy);
		
		if (aktGlebokosc == 0 && mozliweRuchy.size() == 1) {
			najlepszyRuch = mozliweRuchy.getFirst();
			return 0;
		}
		else {
			if (aktGlebokosc == 0) {
				if (czyPierwszyGracz) {
					int ocena = Integer.MIN_VALUE;
					for (Ruch ruch : mozliweRuchy) {
						int tmpOcena = minMax (new Plansza (plansza, ruch), !czyPierwszyGracz, aktGlebokosc+1);
						if (tmpOcena > ocena) {
							ocena = tmpOcena;
							najlepszyRuch = ruch;
						}
					}
					return ocena;
				}
				else {
					int ocena = Integer.MAX_VALUE;
					for (Ruch ruch : mozliweRuchy) {
						int tmpOcena = minMax (new Plansza (plansza, ruch), !czyPierwszyGracz, aktGlebokosc+1);
						if (tmpOcena < ocena) {
							ocena = tmpOcena;
							najlepszyRuch = ruch;
						}
					}
					return ocena;
				}
			}
			else {
				if (czyPierwszyGracz) {
					int ocena = Integer.MIN_VALUE;
					for (Ruch ruch : mozliweRuchy) 
						ocena = Math.max (ocena, minMax (new Plansza (plansza, ruch), !czyPierwszyGracz, aktGlebokosc+1));
					return ocena;
				}
				else {
					int ocena = Integer.MAX_VALUE;
					for (Ruch ruch : mozliweRuchy) 
						ocena = Math.min (ocena, minMax (new Plansza (plansza, ruch), !czyPierwszyGracz, aktGlebokosc+1));
					return ocena;
				}
			}
		}
	}
	
	private int alfaBeta (Plansza plansza, boolean czyPierwszyGracz, int aktGlebokosc, int alfa, int beta) {
		if (czyBadac) ++iloscWezlow;
		if (plansza.koniecGry() == 1) return -88888888;
		if (plansza.koniecGry() == 0) return 88888888;
		if (aktGlebokosc == maxGlebokoscDrzewa) {
			if (!plansza.czyMaRuch (czyPierwszyGracz)) {
				if (czyPierwszyGracz) return -88888888;
				else return 88888888;
			}
			else return plansza.ocenSytuacje (fOceny);
		}
		
		LinkedList <Ruch> mozliweRuchy = new LinkedList <Ruch> ();
		plansza.wesBicia (czyPierwszyGracz, mozliweRuchy);
		if (mozliweRuchy.isEmpty()) plansza.wesRuchy (czyPierwszyGracz, mozliweRuchy);
		else if (mozliweRuchy.size() > 1 && czySortowac) {
			LinkedList <Ruch> mozliweRuchy2 = new LinkedList <Ruch> ();
			int maxIle = 1;
			for (Ruch ruch : mozliweRuchy) {
				int ile = 1;
				Ruch temp = ruch;
				while (temp.getNastepneBicie() != null) {
					++ile;
					temp = temp.getNastepneBicie();
				}
				ruch.ileBic = ile;
				if (ile > maxIle) maxIle = ile;
			}
			while (maxIle > 0) {
				for (Ruch ruch : mozliweRuchy) {
					if (ruch.ileBic == maxIle) {
						mozliweRuchy2.add (ruch);
					}
				}
				--maxIle;
			}
			mozliweRuchy = mozliweRuchy2;
		}
		
		if (aktGlebokosc == 0 && mozliweRuchy.size() == 1) {
			najlepszyRuch = mozliweRuchy.getFirst();
			return 0;
		}
		else {
			if (aktGlebokosc == 0) {
				if (czyPierwszyGracz) {
					for (Ruch ruch : mozliweRuchy) {
						int tmpOcena = alfaBeta (new Plansza (plansza, ruch), !czyPierwszyGracz, aktGlebokosc+1, alfa, beta);
						if (tmpOcena > alfa) {
							alfa = tmpOcena;
							najlepszyRuch = ruch;
						}
						if (alfa >= beta) return beta;
					}
					return alfa;
				}
				else {
					for (Ruch ruch : mozliweRuchy) {
						int tmpOcena = alfaBeta (new Plansza (plansza, ruch), !czyPierwszyGracz, aktGlebokosc+1, alfa, beta);
						if (tmpOcena < beta) {
							beta = tmpOcena;
							najlepszyRuch = ruch;
						}
						if (beta <= alfa) return alfa;
					}
					return beta;
				}
			}
			else {
				if (czyPierwszyGracz) {
					for (Ruch ruch : mozliweRuchy) {
						alfa = Math.max (alfa, alfaBeta (new Plansza (plansza, ruch), !czyPierwszyGracz, aktGlebokosc+1, alfa, beta));
						if (alfa >= beta) return beta;
					}
					return alfa;
				}
				else {
					for (Ruch ruch : mozliweRuchy) {
						beta = Math.min (beta, alfaBeta (new Plansza (plansza, ruch), !czyPierwszyGracz, aktGlebokosc+1, alfa, beta));
						if (beta <= alfa) return alfa;
					}
					return beta;
				}
			}
		}
	}
}