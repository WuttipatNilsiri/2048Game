package MODEL;

import java.util.Comparator;

public class ScoreDataCom implements Comparator<String> {

	@Override
	public int compare(String arg0, String arg1) {
		String[] s1 = arg0.split(" Score: ");
		String[] s2 = arg1.split(" Score: ");
		return Integer.parseInt(s2[1]) - Integer.parseInt(s1[1]);
	}
	
}
