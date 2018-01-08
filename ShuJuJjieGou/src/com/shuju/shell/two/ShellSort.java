package com.shuju.shell.two;

public class ShellSort {
	public static void main(String[] args) {
		int[] a=new int[]{9,1,5,2,7,3,8,4,6};
		shellSort(a);
	}
	
	public static void shellSort(int[] a){
		int j;
		for(int gap=a.length/2;gap>0;gap/=2){
			for(int i=gap;i<a.length;i++){
				int tmp=a[i];
				for(j=i;j>=gap && tmp<a[j-gap];j-=gap){
					a[j]=a[j-gap];
				}
				a[j]=tmp;
			}
		}
	}
}
