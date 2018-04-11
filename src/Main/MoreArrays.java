package Main;

public class MoreArrays {
	public static void fill(int[] array, int rgb){
		for(int i=0;i<array.length;i++){
			array[i]=rgb;
		}
	}

	public static int[][] dup(int[][] arr) {
		int[][] ret=new int[arr.length][arr[0].length];
		for(int i=0;i<arr.length;i++){
			for(int j=0;j<arr[0].length;j++){
				ret[i][j]=arr[i][j];
			}
		}
		return ret;
	}
	
	public static void destoryDup(int[][] arr, int[][] ret) {
		for(int i=0;i<arr.length;i++){
			for(int j=0;j<arr[0].length;j++){
				ret[i][j]=arr[i][j];
			}
		}
	}

	public static boolean equals(int[][] arr, int[][] cmp) {
		for(int i=0;i<arr.length;i++){
			for(int j=0;j<arr[0].length;j++){
				if(cmp[i][j]!=arr[i][j]){
					return false;
				}
			}
		}
		return true;
	}

	public static int max(int[][] arr) {
		int max=Integer.MIN_VALUE;
		for(int i=0;i<arr.length;i++){
			for(int j=0;j<arr[0].length;j++){
				if(arr[i][j]>max){
					max=arr[i][j];
				}
			}
		}
		return max;
	}
}
