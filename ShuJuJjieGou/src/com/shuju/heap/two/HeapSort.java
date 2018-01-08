package com.shuju.heap.two;

public class HeapSort {
	public static void main(String[] args) {
		int[] a=new int[]{26,41,58,31,53,59,97};
		heapSort(a);
	}
	public static int leftChild(int i){
		return 2*i+1;
	}
	
	public static void perDown(int[] a,int i,int n){
		int child=0;
		int tmp;
		for(tmp=a[i];leftChild(i)<n;i=child){
			child=leftChild(i);
			if(child!=n-1 && a[child]<a[child+1])
				child++;
			if(tmp<a[child])
				a[i]=a[child];
			else
			break;
		}
		a[i]=tmp;
	}
	
	public static void heapSort(int[] a){
		for(int i=a.length/2;i>=0;i--){
			perDown(a, i, a.length);
		}
		for(int i=a.length-1;i>0;i--){
			swapReferences(a,0,i);
			perDown(a, 0, i);
		}
	}

	private static void swapReferences(int[] arr, int a, int b) {
		int c=arr[a];
		arr[a]=arr[b];
		arr[b]=c;
	}
}
