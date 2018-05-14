package MODEL;

public class Tile {
	public int pow;
	
	public Tile(int i) {
		pow = i;
	}
	/**
	 * set shift then we can shift after
	 * @return
	 */
	public int getLog2(){
		return pow;
	}
	/**
	 * Get Value
	 * @return value
	 */
	public int getValue(){
		return 1 << pow;
	}
	
}
