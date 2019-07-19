package com.dawn.maple.test;

/**
 * Created by Dawn on 2018/12/13.
 */
public class AlgorithmTest {

    public static void main(String[] args) {

        int[] arr = new int[]{1, 3, 4, 5, 8, 9, 10, 19};
        int n = 4;
        int[] after = loopLeft(arr, n);
        String numStr = "";
        for (int num : after) {
            numStr = numStr + num + ",";
        }
        System.out.println(numStr.substring(0, numStr.length() - 1));
    }

    //逆序函数
    public static void reverse(int[] arr, int left, int right) {
        int temp;
        while (left < right) {
            //两数交换
            temp = arr[left];
            arr[left] = arr[right];
            arr[right] = temp;
            //左下标加一，右下标减一
            left++;
            right--;
        }

    }

    //循环左移函数，arr：数组，n：左移位数
    public static int[] loopLeft(int[] arr, int n) {
        if (arr == null || arr.length == 0) {
            return new int[]{};
        }
        //对左半部分进行逆序
        reverse(arr, 0, n - 1);
        //对右半部分进行逆序
        reverse(arr, n, arr.length - 1);
        //对整个数组进行逆序
        reverse(arr, 0, arr.length - 1);
        return arr;

    }


}
