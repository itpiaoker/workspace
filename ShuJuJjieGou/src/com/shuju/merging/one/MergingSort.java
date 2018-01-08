package com.shuju.merging.one;

public class MergingSort {
	private static final int MAXSIZE = 0;
	static int[] array;
	public static int[] mergingSort(int[] r){
		mergeSort(r,r,1,r.length);
	}

	private static void mergeSort(int[] sr, int[] tr1, int s, int t) {
		int m;
		int[] tr2=new int[MAXSIZE+1];
		if(s==t) tr1[s]=sr[s];
		else{
			m=(s=t)/2;
			mergeSort(sr, tr2, s, m);
			mergeSort(sr, tr2, m+1, t);
			mergeSort(tr2, tr1, s, m);
		}
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
		array=mergingSort(arr);
		for (int i : array) {
			System.out.print(i);
			System.out.print("\t");
		}
	}
	
	public static void main(String[] args) {
		int[] aa={50,10,90,30,70,40,80,60,20};
		printArr(mergingSort(aa));
	}

}

