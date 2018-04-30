package UI;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JComponent;

import MODEL.Tile;

public class TileUI extends JComponent{

	Tile tile;
	
	static final Timer timer = new Timer();
	
	static final Color BACKGROUND = new Color(0xBBADA0);

	static final Color[] COLOR={
			new Color(204, 192, 179),
			new Color(0xEEE4DA),	//2
			new Color(0xEDE0C8),	//4
			new Color(0xF2B179),	//8
			new Color(0xF59563),	//16
			new Color(0xf67c5f),	//32
			new Color(0xf65e3b),	//64
			new Color(0xedcf72),	//128
			new Color(0xedcc61),	//256
			new Color(0xedc850),	//512
			new Color(0xedc53f),	//1024
			new Color(0xedc22e),	//2048
			new Color(0x3c3a32),	//SUPER
	};
	
	static final Color FONT_1_2 = new Color(0x776E65);
	static final Color FONT_OTHERWISE = new Color(0xF9F6F2);
	
	static final Color[] FONT_COLOR={
		null,
		FONT_1_2,	//2
		FONT_1_2,	//4
		FONT_OTHERWISE,	//8
		FONT_OTHERWISE,	//16
		FONT_OTHERWISE, //32
		FONT_OTHERWISE, //64
		FONT_OTHERWISE, //128
		FONT_OTHERWISE, //256
		FONT_OTHERWISE, //512
		FONT_OTHERWISE, //1024
		FONT_OTHERWISE, //2048
		FONT_OTHERWISE, //SUPER
	};
	
	final Font _55px = new Font("Dialog", Font.BOLD , 55);
	
	final Font _45px = _55px.deriveFont(45f);
	final Font _35px = _55px.deriveFont(35f);
	final Font _30px = _55px.deriveFont(30f);
	
	final Font[] FONT = {
		null,
		_55px,	//2
		_55px,	//4
		_55px,	//8
		_55px,	//16
		_55px,	//32
		_55px,	//64
		_45px,	//128
		_45px,	//256
		_45px,	//512
		_35px,  //1024
		_35px,  //2048
		_30px,	//SUPER
	};
	
	public TileUI(Tile t){
		tile = t;
		setSize(100, 100);
	}
	
	public void paint(Graphics gp){
		Graphics2D g = (Graphics2D)gp;
		g.setColor(BACKGROUND);
		g.fillRect(0, 0, 100, 100);
		if(tile.getLog2()!= 0){
			int tColorScheme = tile.getLog2();
			if(tColorScheme >= COLOR.length) tColorScheme = COLOR.length-1;
			g.setColor(COLOR[tColorScheme]);
			g.fillRoundRect(5, 5, 90, 90, 5, 5);
			
			g.setFont(FONT[tColorScheme]);
			String str = tile.getValue()+"";
			g.setColor(FONT_COLOR[tColorScheme]);
			FontMetrics font = g.getFontMetrics();
			g.drawString(str, 50 - g.getFontMetrics().stringWidth(str)/2 , 50 + (font.getAscent() - font.getDescent() + font.getLeading())/2);
		}else{
			g.setColor(COLOR[0]);
			g.fillRoundRect(5, 5, 90, 90, 5, 5);
		}
	}
	
	public void tilePos(int x, int y){
		setLocation(100*x, 100*y);
	}
	
}
