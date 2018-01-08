package com.shuju.bouble.two;

public class BoubbleSort {
	static int[] array;
	public static int[] boubbleSort(int[] arr){
		for(int i=0;i<arr.length;i++){
			for (int j = arr.length-1; j > i; j--) {
				if(arr[j-1]>arr[j]){
					swap(arr,j-1,j);
				}
			}
		}
		
		return arr;
	}

	private static int[] swap(int[] arr, int i, int j) {
		int temp=arr[i];
		arr[i]=arr[j];
		arr[j]=temp;
		return arr;
	}
	
	public static void printArr(int[] arr){
		array=boubbleSort(arr);
		for (int i : array) {
			System.out.print(i);
			System.out.print("\t");
		}
	}
	
	public static void main(String[] args) {
		int[] aa={9,1,5,8,3,7,4,6,2};
		printArr(aa);
	}

}

