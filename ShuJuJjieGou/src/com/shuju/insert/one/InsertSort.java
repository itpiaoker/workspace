package com.shuju.insert.one;

public class InsertSort {
	static int[] array;
	public static int[] boubbleSort(int[] arr){
		for(int i=2;i<arr.length;i++){
			if(arr[i]<arr[i-1]){
				arr[0]=arr[i];
				for (int j = i-1; arr[j] > arr[0]; j--) {
					arr[j+1]=arr[j];
					arr[j]=arr[0];
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
		int[] aa={0,5,3,4,6,2};
		printArr(boubbleSort(aa));
	}

}

