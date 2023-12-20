package src.aristoxenus;

import java.math.BigInteger;
import java.util.Iterator;

/**
 * General class to represent numbers from 1 to 96 bits.
 * 
 * We represent musical structures using binary code. For abstract structures,
 * like a scale, we only need 12 bits. But if we represent the range of the 
 * piano, we need at least 88 bits. 96 bits (12 bytes) is a convenient maximum
 * size. 
 */
public class IntervalBase implements Iterable<IntervalBase>{
    private BigInteger value;

    /**
     * Initialize the object. 
     */
    public IntervalBase(){
        value = BigInteger.valueOf(0);
    }
    public IntervalBase(BigInteger interval){
        value = interval;
    }
    public IntervalBase(IntervalBase interval){
        value = interval.value;
    }
    public IntervalBase(byte interval){
        value = BigInteger.valueOf(interval);
    }
    public IntervalBase(short interval){
        value = BigInteger.valueOf(interval);
    }
    public IntervalBase(int interval){
        value = BigInteger.valueOf(interval);
    }
    public IntervalBase(long interval){
        value = BigInteger.valueOf(interval);
    }



    public int bitLength(){
        return this.value.bitLength();
    }
    public int bitCount(){
        return this.value.bitCount();
    }
    public void shiftLeft(int n){
        this.value = this.value.shiftLeft(n);
    }
    public void shiftRIght(int n){
        this.value = this.value.shiftRight(n);
    }
    public String toBinaryString(){
        return this.value.toString(2);
    }
    public String toString(){
        return this.value.toString();
    }


    /**
     * Return an iterator that iterates through the individual bits that make
     * up the structure.
     */
    @Override
    public Iterator<IntervalBase> iterator(){
        Iterator<IntervalBase> it = new Iterator<IntervalBase>(){
            private IntervalBase currentnumber = IntervalBase.valueOf(value);

            @Override
            public boolean hasNext(){
                return ! (currentnumber.equals(BigInteger.ZERO));
            }
            @Override
            public IntervalBase next(){
                IntervalBase current_interval = currentnumber.and(IntervalBase.not(currentnumber).add(1));
                currentnumber = IntervalBase.valueOf(currentnumber.value.xor(current_interval.value));
                return current_interval;
            }
            @Override
            public void remove(){
                throw new UnsupportedOperationException();
            }
        };
        return it;
    }


    /**
     * Secondary constructor following BigInteger method.
     * 
     * @param interval  A whole number or class instance.
     * @return          Instance with the given value.
     */
    public static IntervalBase valueOf(BigInteger interval){
        return new IntervalBase(interval);
    }
    public static IntervalBase valueOf(IntervalBase interval){
        return IntervalBase.valueOf(interval.value);
    }
    public static IntervalBase valueOf(byte interval){
        return IntervalBase.valueOf(BigInteger.valueOf(interval));
    }
    public static IntervalBase valueOf(short interval){
        return IntervalBase.valueOf(BigInteger.valueOf(interval));
    }
    public static IntervalBase valueOf(int interval){
        return IntervalBase.valueOf(BigInteger.valueOf(interval));
    }
    public static IntervalBase valueOf(long interval){
        return IntervalBase.valueOf(BigInteger.valueOf(interval));
    }



    public boolean equals(IntervalBase comparandum){
        return this.value.equals(comparandum.value);
    }
    public boolean equals(BigInteger comparandum){
        return this.equals(IntervalBase.valueOf(comparandum));
    }
    public boolean equals(byte comparandum){
        return this.equals(IntervalBase.valueOf(comparandum));
    }
    public boolean equals(short comparandum){
        return this.equals(IntervalBase.valueOf(comparandum));
    }
    public boolean equals(int comparandum){
        return this.equals(IntervalBase.valueOf(comparandum));
    }
    public boolean equals(long comparandum){
        return this.equals(IntervalBase.valueOf(comparandum));
    }
    

    
    public IntervalBase add(BigInteger interval){
        return IntervalBase.valueOf(this.value.add(interval));
    }
    public IntervalBase add(IntervalBase interval){
        return IntervalBase.valueOf(this.value.add(interval.value));
    }
    public IntervalBase add(byte interval){
        return IntervalBase.valueOf(this.add(IntervalBase.valueOf(interval)));
    }
    public IntervalBase add(short interval){
        return IntervalBase.valueOf(this.add(IntervalBase.valueOf(interval)));
    }
    public IntervalBase add(long interval){
        return IntervalBase.valueOf(this.add(IntervalBase.valueOf(interval)));
    }
    public IntervalBase add(int interval){
        return IntervalBase.valueOf(this.add(IntervalBase.valueOf(interval)));
    }


    public IntervalBase and(IntervalBase interval){
        return IntervalBase.valueOf(this.value.and(interval.value));
    }
    public IntervalBase and(BigInteger interval){
        return IntervalBase.valueOf(this.value.and(interval));
    }
    public IntervalBase and(byte interval){
        return this.and(BigInteger.valueOf(interval));
    }
    public IntervalBase and(short interval){
        return this.and(BigInteger.valueOf(interval));
    }
    public IntervalBase and(int interval){
        return this.and(BigInteger.valueOf(interval));
    }
    public IntervalBase and(long interval){
        return this.and(BigInteger.valueOf(interval));
    }


    public IntervalBase or(IntervalBase interval){
        return IntervalBase.valueOf(this.value.or(interval.value));
    }
    public IntervalBase or(BigInteger interval){
        return IntervalBase.valueOf(this.value.or(interval));
    }
    public IntervalBase or(byte interval){
        return this.or(BigInteger.valueOf(interval));
    }
    public IntervalBase or(short interval){
        return this.or(BigInteger.valueOf(interval));
    }
    public IntervalBase or(int interval){
        return this.or(BigInteger.valueOf(interval));
    }
    public IntervalBase or(long interval){
        return this.or(BigInteger.valueOf(interval));
    }


    public IntervalBase not(){
        return IntervalBase.valueOf(this.value.not());
    } 
    public static IntervalBase not(IntervalBase interval){
        return IntervalBase.valueOf(interval.value.not());
    }




}