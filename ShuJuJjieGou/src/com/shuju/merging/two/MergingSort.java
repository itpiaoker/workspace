package com.shuju.merging.two;

public class MergingSort {
	private static void mergeSort(int[] a,int[] tmpArray,int left,int right){
		if(left<right){
			int center=(left+right)/2;
			
			mergeSort(a, tmpArray, left, center);
			mergeSort(a, tmpArray, center+1, right);
			merge(a, tmpArray, left,center+1, right);
			
		}
		
		
	}
	
	private static void merge(int[] a,int[] tmpArray,int leftPos,int rightPos,int rightEnd){
		int leftEnd=rightPos-1;
		int tmpPos=leftPos;
		int numElements=rightEnd-leftPos+1;
		
		while(leftPos<=leftEnd && rightPos<=rightEnd)
			if(a[leftPos]-rightPos<=0)
				tmpArray[tmpPos++]=a[leftPos++];
			else
				tmpArray[tmpPos++]=a[rightPos++];
			
		
		while(leftPos<=leftEnd)
			tmpArray[tmpPos++]=a[leftPos++];
		
		
		while(rightPos<=rightEnd)
			tmpArray[tmpPos++]=a[rightPos++];
		
		
		for(int i=0;i<numElements;i++,rightEnd--){
			a[rightEnd]=tmpArray[rightEnd];
		}
		
		
	}
	
	public static void mergeSort(int[] a){
		int[] tmpArray=new int[a.length];
		mergeSort(a, tmpArray, 0, a.length-1);
	}
	
	public static void main(String[] args) {
		int[] a={1,13,24,26,2,15,27,38};
		mergeSort(a);
		for (int i : a) {
			System.out.print(i+"\t");
		}
	}

}

