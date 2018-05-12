package game;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;

public class DrawUtil {

	private DrawUtil() { }
	
	public static int getMessageWidth(String message, Font font, Graphics2D g) {
		g.setFont(font);
		Rectangle2D bound = g.getFontMetrics().getStringBounds(message, g);
		return (int) bound.getWidth();
	}
	
	public static int getMessageHeight(String message, Font font, Graphics2D g) {
		g.setFont(font);
		if (message.length() == 0) return 0;
		TextLayout tl = new TextLayout(message, font, g.getFontRenderContext());
		return (int) tl.getBounds().getHeight();
	}

	public static String formatTime(long millis) {
		String formattedTime;
		//hour
		String hourFormat = "";
		int hour = (int) (millis/3600000);
		if (hour >= 1) {
			millis -= hour*3600000;
			if (hour < 10) hourFormat = "0" + hour;
			else hourFormat = "" + hour;
			hourFormat += ":";
		}
		
		//minute
		String minuteFormat;
		int minute = (int) (millis/60000);
		if (minute >= 1) {
			millis -= minute*60000;
			if (minute < 10) minuteFormat = "0" + minute;
			else minuteFormat = "" + minute;
		}
		else minuteFormat = "00";
		
		//second
		String secondFormat;
		int second = (int) (millis/1000);
		if (second >= 1) {
			millis -= second*1000;
			if (second < 10) secondFormat = "0" + second;
			else secondFormat = "" + second;
		}
		else secondFormat = "00";
		
		//millisecond
		String millisFormat;
		if (millis > 99) millisFormat = "" + millis;
		else if (millis > 9) millisFormat = "0" + millis;
		else millisFormat = "00" + millis;
		
		formattedTime = hourFormat + minuteFormat + ":" + secondFormat + ":" + millisFormat;
		return formattedTime;
	}
}
