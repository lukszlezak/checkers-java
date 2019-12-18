package warcaby;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;

public class GUI extends JFrame implements ActionListener {
	private static final long serialVersionUID = -2367034285696426887L;
	private JButton[][] jpola;
	private boolean czyKliknal = false;
	private int xKlik, yKlik;
	private Icon pionek1, pionek2, damka1, damka2;

	public GUI() {
		super ("Warcaby Splotta");
		setBounds (300, 200, 460, 480);
		setLayout (null);
		
		pionek1 = new ImageIcon ("pionek1.png");
		pionek2 = new ImageIcon ("pionek2.png");
		damka1 = new ImageIcon ("damka1.png");
		damka2 = new ImageIcon ("damka2.png");
		
		jpola = new JButton[8][8];
		boolean czyBialePole = true;
		for (int x = 0, pozx = 0 ; x < 8 ; ++x, pozx += 55) {
			for (int y = 0, pozy = 0 ; y < 8 ; ++y, pozy += 55) {
				jpola[x][y] = new JButton();
				jpola[x][y].setBounds (5+pozy, 5+pozx, 50, 50);
				//jpola[x][y].setEnabled (false);
				if (czyBialePole) jpola[x][y].setBackground (Color.WHITE);
				else {
					jpola[x][y].setBackground (Color.BLACK);
					jpola[x][y].addActionListener (this);
				}
				czyBialePole = !czyBialePole;
				add (jpola[x][y]);
			}
			czyBialePole = !czyBialePole;
		}
		
		setVisible (true);
		setResizable (false);
		setDefaultCloseOperation (javax.swing.WindowConstants.EXIT_ON_CLOSE);
	}
	
	public void pokazPlansze (Pionek[][] plansza) {
		for (int x = 0 ; x < 8 ; ++x) {
			for (int y = 0 ; y < 8 ; ++y) {
				if ((x+y) % 2 == 1) {
					if (plansza[x][y] != null) {
						if (plansza[x][y].getCzyPierwszegoGracza()) {
							if (plansza[x][y].getCzyDamka()) jpola[x][y].setIcon (damka1);
							else jpola[x][y].setIcon (pionek1);
						}
						else {
							if (plansza[x][y].getCzyDamka()) jpola[x][y].setIcon (damka2);
							else jpola[x][y].setIcon (pionek2);
						}
					}
					else jpola[x][y].setIcon (null);
				}
			}
		}
	}
	
	public JButton[][] getJPola () {
		return jpola;
	}
	
	public boolean getCzyKliknal() {
		return czyKliknal;
	}
	
	public void setCzyKliknal (boolean czyKliknal) {
		this.czyKliknal = czyKliknal;
	}
	
	public int getXKlik() {
		return xKlik;
	}
	
	public int getYKlik() {
		return yKlik;
	}

	@Override
	public void actionPerformed (ActionEvent ae) {
		Object zrodlo = ae.getSource();
		for (int x = 0 ; x < 8 ; ++x) {
			for (int y = 0 ; y < 8 ; ++y) {
				if (zrodlo == jpola[x][y]) {
					xKlik = x;
					yKlik = y;
					czyKliknal = true;
					return;
				}
			}
		}
	}
}