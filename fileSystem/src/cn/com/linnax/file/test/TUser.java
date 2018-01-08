package cn.com.linnax.file.test;

public class TUser {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int[][] arr = new int[4][5];
		arr[0][0] = 1;
		
		for(int i = 0; i < 4; i ++){
			for(int j = 0; j < 5; j ++){
				System.out.print(arr[i][j] + "\t");
				int current = arr[i][j];
				//arr[i][j] = 2;
				// 与左前方的值比较
				if(i == 0){
					if(j == 0){
						continue;
					} else if(j == 5){
						
					} else {
						
					}
				} else if(i == 4){
					if(j == 0){
						continue;
					} else if(j == 5){
						
					} else {
						
					}
					int x = 0;
				} else {
					if(j == 0){
						continue;
					} else if(j == 5){
						
					} else {
						
					}
				}
				
			}
			
			System.out.println();
			System.out.println();
			
		}
	}
}
