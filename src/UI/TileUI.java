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
			new Color(0x701710), new Color(0xFFE4C3), new Color(0xfff4d3),
	        new Color(0xffdac3), new Color(0xe7b08e), new Color(0xe7bf8e),
	        new Color(0xffc4c3), new Color(0xE7948e), new Color(0xbe7e56),
	        new Color(0xbe5e56), new Color(0x9c3931), new Color(0x701710)
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
	
	final Font _55px = new Font("SansSerif", Font.BOLD, 55);
	
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
			int index = tile.getLog2();
			if(index >= COLOR.length) index = COLOR.length-1;;
			g.setColor(COLOR[index]);
//			g.setColor(COLOR[tColorScheme]);
			g.fillRoundRect(5, 5, 90, 90, 5, 5);
			g.setFont(FONT[index]);
			String str = tile.getValue()+"";
			g.setColor(index < 8 ? COLOR[0] : COLOR[1]);
//			g.setColor(FONT_COLOR[tColorScheme]);
			FontMetrics font = g.getFontMetrics();
			g.drawString(str, 50 - font.stringWidth(str)/2 , 50 + (font.getAscent() - font.getDescent() + font.getLeading())/2);
		}else{
			g.setColor(new Color(204, 192, 179));
			g.fillRoundRect(5, 5, 90, 90, 5, 5);
		}
	}
	
	public void tilePos(int x, int y){
		setLocation(100*x, 100*y);
	}
	
}
