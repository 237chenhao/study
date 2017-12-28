package com.ch.stream;

import org.apache.commons.io.IOUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author cj-ch
 * @date 2017/12/19 上午9:50
 *
 */
public class Temperature implements Comparable<Temperature>{
    private Double value = 0D;
    private Unit unit = Unit.C;

    public Temperature() {
    }

    public Temperature(Double value, Unit unit) {
        this.value = value;
        this.unit = unit;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public void convert(Unit unit){
        if(this.unit == unit){
            //
        } else if (this.unit == Unit.C){
            //C  to  F
            cToF();
        } else if (this.unit == Unit.F){
            //F to C
            fToC();
        }else {
            throw new RuntimeException("not support");
        }
    }

    private void cToF(){
        this.unit = Unit.F;
        //
        this.value = 32 + (double)Math.round((this.value * 1.8)*100)/100;
    }

    private void fToC(){
        this.unit = Unit.C;
        this.value = (double)Math.round(((this.value - 32)/1.8)*100)/100;
    }

    /**
     * Greater than 1, equal to 0, less than -1
     * @param o
     * @return
     */
    @Override
    public int compareTo(Temperature o) {
        if(o == null){
            throw new IllegalArgumentException("params not is NULL");
        }
        if(o == this){
            return 0;
        }
        this.convert(Unit.F);
        o.convert(Unit.F);
        return this.value > o.value ? 1 : (this.value < o.value ? -1 : 0);
    }

    public enum Unit{
        /**
         * C
         */
        C,
        /**
         * F
         */
        F;
    }

    /**
     * test
     * @param args
     */
    public static void main(String[] args) throws IOException {
        Temperature t1 = new Temperature(17.8D,Unit.F);
        Temperature t2 = new Temperature(-7.8888889D,Unit.C);
        System.out.println(t1.compareTo(t2));

        Temperature t3 = new Temperature(1D,Unit.C);
        t3.convert(Unit.F);
        System.out.println(t3.getValue());


        InputStream inputStream = new FileInputStream("/Users/cj-ch/Documents/productConfig.txt");

        consumerInputstream(inputStream);
        System.out.println(inputStream.available());

    }
    static void consumerInputstream(InputStream inputStream) throws IOException {
        try(InputStream in = inputStream;){
            System.out.println(IOUtils.toString(in,"utf-8"));
        }

        System.out.println(IOUtils.toString(inputStream,"utf-8"));

    }
}
