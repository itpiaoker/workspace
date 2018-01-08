package com.shuju.quicksort.one;

public class QuickSort {
	public static void main(String[] args) {
		int[] a=new int[]{1,2,5,3,6};
		median3(a, 0, 4);
		for (int i : a) {
			System.out.println(i);
		}
	}
	
	public static void quickSort(int[] a){
		quickSort(a,0,a.length-1);
	}

	private static void quickSort(int[] a, int i, int j) {
		
	}
	
	private static void median3(int[] a, int left, int right) {
		int center=(left+right)/2;
		if(a[center]<a[left]){
			swap(a,left,center);
		}
		if(a[right]<a[left]){
			swap(a, right, left);
		}
		if(a[right]<a[center]){
			swap(a, right, center);
		}
		swap(a, center, right-1);
	}

	private static void swap(int[] arr, int a, int b) {
		int c=arr[a];
		arr[a]=arr[b];
		arr[b]=c;
	}
}
