package Main;

public class Tile {
	int pow;
	
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
