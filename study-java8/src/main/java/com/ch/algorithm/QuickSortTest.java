package com.ch.algorithm;

import java.util.Arrays;

/**
 * @author cj-ch
 * @date 2018/2/27 上午9:30
 */
public class QuickSortTest {

    public static void main(String[] args) {
        int[] ints = new int[]{9,3,95,33,2,7,77,21,76,4,3};
        int base_index=0;
        int l,r;
        l=base_index+1;r=ints.length-1;
        int l_=l,r_=r;
        for(int i=0,len=ints.length;i<len;i++){

            //从右往左找出比基数小的数
            while(r_ > base_index){
                if(ints[r_] <= ints[base_index]){
                    break;
                }
                r_--;
            }
            if(r_ == base_index){
                //说明基数的右边没有大于或等于基数的数
                //本轮次查找结束
                //交换
                ints[base_index] = ints[base_index]^ints[r];
                ints[r] = ints[base_index]^ints[r];
                ints[base_index] = ints[base_index]^ints[r];
                break;
            } else {
                while(l_ < r){

                    if(ints[l_] >= ints[base_index]){
                        break;
                    }
                    l_++;
                    //可能会和右哨兵碰头
                    if(l_ == r_){
                        //碰头拉,
                    }
                }
                if(l_ == r){
                    //说明基数的右边没有小于或等于基数的数
                    //不变
                    break;
                }

                //--此时说明在基数的右边找到了大于等于基数的数  和 小于等于基数的数
                //交换
                ints[l_] = ints[l_]^ints[r_];
                ints[r_] = ints[l_]^ints[r_];
                ints[l_] = ints[l_]^ints[r_];

            }
        }
        System.out.println(Arrays.toString(ints));


        //7,9,6,3,4
        //7,4,6,3,9
    }
}
