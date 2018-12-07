package com.dawnyu.maple.test;

import org.springframework.util.DigestUtils;

public class UserTest {

    public static void main(String[] args) {
        System.out.println(DigestUtils.md5DigestAsHex("dawn1991".getBytes()));
        int[] nums = {2, 7, 11, 15};
        int[] arr = twoSum(nums, 9);
        for (int i = 0; i < arr.length; i++) {
            System.out.println(arr[i]);
        }
        int[] input1 = {2, 7, 1, 5};
        int[] input2 = {3, 4, 7, 8};
        ListNode listNode1 = buildListNode(input1);
        ListNode listNode2 = buildListNode(input2);
        ListNode sum = addTwoNumbers(listNode1,listNode2);
        System.out.println(printListNode(sum));
    }

    public static int[] twoSum(int[] nums, int target) {
        int[] arr = new int[2];
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (target == nums[i] + nums[j]) {
                    arr[0] = i;
                    arr[1] = j;
                    break;
                }
            }
        }
        return arr;
    }

    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode dummyNode = new ListNode(0);
        ListNode p = l1, q = l2, curr = dummyNode;
        //进位
        int carry = 0;
        while (p != null || q != null) {
            int x = (p != null) ? p.val : 0;
            int y = (q != null) ? q.val : 0;
            int sum = x + y + carry;
            carry = sum / 10;
            curr.next = new ListNode(sum % 10);
            curr = curr.next;
            if (p != null) {
                p = p.next;
            }
            if (q != null) {
                q = q.next;
            }
        }
        if (carry > 0) {
            curr.next = new ListNode(carry);
        }
        return dummyNode.next;
    }

    public static ListNode buildListNode(int[] input) {
        ListNode first = null, last = null, newNode;
        if (input.length > 0) {
            for (int i = 0; i < input.length; i++) {
                newNode = new ListNode(input[i]);
                newNode.next = null;
                if (first == null) {
                    //将链表first和last赋值为newNode 即2->null
                    first = newNode;
                    last = newNode;
                } else {
                    //将重置后的newNode置为2->3->null
                    last.next = newNode;
                    //last.next=last,节点后移
                    last = newNode;
                }
            }
        }
        return first;
    }
    public static String printListNode(ListNode listNode){
        StringBuilder builder = new StringBuilder();
        while (listNode !=null){
            builder.append(listNode.val);
            builder.append(",");
            listNode = listNode.next;
        }
        return builder.substring(0,builder.length()-1);
    }
}

class ListNode {
    int val;
    ListNode next;

    ListNode(int val) {
        this.val = val;
        this.next = null;
    }
}
