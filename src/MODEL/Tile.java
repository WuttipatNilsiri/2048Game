package MODEL;

public class Tile {
	public int pow;
	
	public Tile(int i) {
		pow = i;
	}

	public int getLog2(){
		return pow;
	}
	
	public int getValue(){
		return 1 << pow;
	}
	
}
