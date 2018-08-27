package com.crazyfish.kamal.test.testsort;

/**
 * Created by kamal on 15/10/20.
 */
public class HeapSort {
    private static void AjustHeap(int a[],int length,int i){
        int left = left(i);
        int right = right(i);
        int bigger = i;//最大下标
        if( (left < length )) {
            if (a[left] > a[i])
                bigger = left;
        }
        if( (right < length)) {
            if (a[right] > a[bigger])
                bigger = right;
        }
        if( bigger == i ) return;
        int tmp = a[i];
        a[i] = a[bigger];
        a[bigger] = tmp;
        AjustHeap(a,length,bigger);//该值变化有可能失去堆得特点，所以该节点再进行调整
    }

    private static int left(int i){
        return 2 * i + 1;
    }
    private static int right(int i){
        return 2 * i + 2;
    }
    public static void main(String args[]){
        int a[] = {1,2,3,4,7,8,9,10,14,16};
        //建堆
        int length = a.length;
        for(int i = a.length / 2 -  1;i >= 0;i --){
            AjustHeap(a,length,i);
        }
        for(int i = 0;i < a.length;i ++){
            System.out.print(a[i] + " ");
        }
        System.out.println();
        for(int i = 0;i < a.length;i ++){
            int tmp = a[0];
            a[0] = a[length - 1];
            a[length - 1] = tmp;
            length --;
            AjustHeap(a,length,0);
        }
        for(int i = 0;i < a.length;i ++){
            System.out.print(a[i] + " ");
        }
    }

}
