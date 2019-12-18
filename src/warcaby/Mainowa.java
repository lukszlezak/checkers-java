package warcaby;

public class Mainowa {
	private static Komputer kompA, kompB;
	private static boolean czyPCPC = true, czyPierwszyGracz = true, czyGUI = true, czyMinMax = false, czySortowac = true;
	private static Plansza plansza;
	private static Ruch ruch;
	private static GUI gui;
	private static int poziomA = 12, poziomB = 1, stareX, stareY, noweX, noweY, zbicieX, zbicieY, fOcenyA = 1, fOcenyB = 1;
	
	private static void czekajNaKlik () {
		do {
			try {
				Thread.sleep (200);
			} catch (InterruptedException e) {}
		} while (!gui.getCzyKliknal());
		gui.setCzyKliknal (false);
	}
	
	public static void main (String[] args) throws InterruptedException {
		if (czyGUI) gui = new GUI();
		plansza = new Plansza();
		kompB = new Komputer (poziomB, czyMinMax, 2, false, fOcenyB, czySortowac);
		if (czyPCPC) kompA = new Komputer (poziomA, czyMinMax, 1, true, fOcenyA, czySortowac);
		if (czyGUI) gui.pokazPlansze (plansza.getPlansza());

		long start = System.nanoTime();
		while (plansza.koniecGry() < 0) {
			//Thread.sleep (1000);
			if (czyPierwszyGracz) { //na dole planszy
				if (czyPCPC) { // komputer
					ruch = kompA.znajdzRuch (plansza, czyPierwszyGracz); // true - pierwszy gracz
					plansza.wykonajRuch (ruch);
				}
				else {
					czekajNaKlik();
					stareX = gui.getXKlik(); stareY = gui.getYKlik();
					czekajNaKlik();
					noweX = gui.getXKlik(); noweY = gui.getYKlik();
					if (Math.abs (stareX - noweX) == 2) {
						zbicieX = stareX > noweX ? stareX-1 : stareX+1;
						zbicieY = stareY > noweY ? stareY-1 : stareY+1;
						plansza.wykonajRuch (new Ruch (stareX, stareY, noweX, noweY, zbicieX, zbicieY));
						while (plansza.czyJeszczeBicieCzleka (noweX, noweY)) {
							czekajNaKlik();
							stareX = noweX; stareY = noweY;
							noweX = gui.getXKlik(); noweY = gui.getYKlik();
							zbicieX = stareX > noweX ? stareX-1 : stareX+1;
							zbicieY = stareY > noweY ? stareY-1 : stareY+1;
							plansza.wykonajRuch (new Ruch (stareX, stareY, noweX, noweY, zbicieX, zbicieY));
						}
					}
					else plansza.wykonajRuch (new Ruch (stareX, stareY, noweX, noweY));
				}
			}
			else { // na gorze planszy
				//Thread.sleep(3000);
				ruch = kompB.znajdzRuch (plansza, czyPierwszyGracz); // false - drugi gracz
				plansza.wykonajRuch (ruch);
			}
			czyPierwszyGracz = !czyPierwszyGracz;
			if (czyGUI) gui.pokazPlansze (plansza.getPlansza());
			
			if (!plansza.czyMaRuch (czyPierwszyGracz)) {
				if (czyPierwszyGracz) plansza.setIleFigurPierwszego (0);
				else  plansza.setIleFigurDrugiego (0);
			}
		}
		System.out.println ("Przegral gracz: " + plansza.koniecGry());
		long stop = System.nanoTime();
		System.out.println ("Trwalo to - " + (double)(stop-start)/1000000000 + "s - ilosc wezlow - " + kompA.iloscWezlow);
		System.out.println ("czasSzukania " + kompA.czasSzukania + " iloscSzukan " + kompA.ileSzukan 
				+ " srednia " + (kompA.czasSzukania / kompA.ileSzukan));
	}
}