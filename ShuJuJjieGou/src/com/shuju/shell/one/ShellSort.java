package com.shuju.shell.one;

public class ShellSort {
	static int[] array;
	public static int[] shellSort(int[] r){
		int increment=r.length;
		
		do{
			increment=increment/3+1;
			for (int i = increment+1; i < r.length; i++) {
				if(r[i]<r[i-increment]){
					r[0]=r[i];
					for (int j = i-increment; j > 0 && r[0] < r[j]; j-=increment) {
						r[j+increment]=r[j];
						r[j]=r[0];
					}
				}
			}
		}while(increment>1);
		
		return r;
	}

	private static int[] swap(int[] arr, int i, int j) {
		int temp=arr[i];
		arr[i]=arr[j];
		arr[j]=temp;
		return arr;
	}
	
	public static void printArr(int[] arr){
		array=shellSort(arr);
		for (int i : array) {
			System.out.print(i);
			System.out.print("\t");
		}
	}
	
	public static void main(String[] args) {
		int[] aa={0,9,1,5,8,3,7,4,6,2};
		printArr(shellSort(aa));
	}

}

