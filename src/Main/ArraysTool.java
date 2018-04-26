package Main;

public class ArraysTool {


	public static int[][] dup(int[][] arr) {
		int[][] ret = new int[arr.length][arr[0].length];
		for(int i = 0; i<arr.length; i++){
			for(int j = 0; j < arr[0].length; j++){
				ret[i][j] = arr[i][j];
			}
		}
		return ret;
	}
	
	public static void destoryDup(int[][] arr, int[][] ret) {
		for(int i = 0; i<arr.length; i++){
			for(int j = 0; j<arr[0].length; j++){
				ret[i][j] = arr[i][j];
			}
		}
	}

	
}
