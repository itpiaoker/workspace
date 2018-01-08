package com.shuju.heap.one;

public class HeapSort {
	static int[] array;
	public static int[] shellSort(int[] r){
		int i;
		for (i = r.length/2; i > 0; i--) {
			heapAdjust(r,i,r.length);
		}
		for (i = r.length; i > 1; i--) {
			swap(r, 1, i);
			heapAdjust(r,1,i-1);
		}
		return r;
	}

	private static void heapAdjust(int[] r, int s, int m) {
		int temp;
		temp=r[s];
		for (int j = 2*s; j <= m; j*=2) {
			if(j<m && r[j]<r[j+1]) ++j;
			if(temp>=r[j]) break;
			r[s]=r[j];
			s=j;
		}
		r[s]=temp;
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
		int[] aa={50,10,90,30,70,40,80,60,20};
		printArr(shellSort(aa));
	}

}

