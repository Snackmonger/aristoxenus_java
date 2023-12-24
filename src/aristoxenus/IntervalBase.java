package src.aristoxenus;

import java.math.BigInteger;
import java.lang.Math;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * General class to assist in treating binary code like an interval
 * map or pitch map.
 * 
 * We use this class to represent individual intervals (10001 = DITONE)
 * and larger structures made up of intervals (10010001 = MAJOR_TRIAD).
 * 
 * The class is initialized from any kind of whole number (byte, int,
 * short, long), as well as the BigInteger class and the IntervalBase
 * class itself. The initializer can also be left blank for a value of
 * zero.
 * 
 * The class can be used as an iterator, and it will return instances
 * with the value of the individual flipped bits of its starting number.
 */
public class IntervalBase implements Iterable<IntervalBase>{
    private BigInteger value;

    /**
     * Initialize the object with a whole number [interval].
     * 
     */
    public IntervalBase(){value = BigInteger.ZERO;}
    public IntervalBase(BigInteger interval){value = interval;}
    public IntervalBase(IntervalBase interval){value = interval.value;}
    public IntervalBase(byte interval){value = BigInteger.valueOf(interval);}
    public IntervalBase(short interval){value = BigInteger.valueOf(interval);}
    public IntervalBase(int interval){value = BigInteger.valueOf(interval);}
    public IntervalBase(long interval){value = BigInteger.valueOf(interval);}

    /**Return the number of bits in the number. */
    public int bitLength(){return this.value.bitLength();}
    /**Return the number of flipped bits in the number. */
    public int bitCount(){return this.value.bitCount();}
    /**Return the binary representation of the number as a string. */
    public String toBinaryString(){return this.value.toString(2);}
    /**Return the decimal representation of the number as a string. */
    public String toString(){return this.value.toString();}

    /**Shift the bits left [n] times. */
    public IntervalBase shiftLeft(int n){return new IntervalBase(this.value.shiftLeft(n));}
    /**Shift the bits right [n] times. */
    public IntervalBase shiftRIght(int n){return new IntervalBase(this.value.shiftRight(n));}
    

    /**
     * Rotate the bits of a [collection] with [max_bits] to the left 1 time. 
     */
    public static IntervalBase rotateLeft(IntervalBase collection, int max_bits){

        IntervalBase left = collection
            .shiftLeft(1)
            .and(new IntervalBase((int) Math.pow(2, max_bits) -1));

        IntervalBase right = collection
            .and(new IntervalBase((int) Math.pow(2, max_bits)))
            .shiftRIght(max_bits - 1);

        return left.or(right);
    }


    /**
     * Rotate the bits of a [collection] with [max_bits] to the right 1 time. 
     */
    public static IntervalBase rotateRight(IntervalBase collection, int max_bits){

        IntervalBase left = collection
            .and(new IntervalBase((int) Math.pow(2, max_bits) - 1))
            .shiftRIght(1);

        IntervalBase right = collection
            .shiftLeft(max_bits - 1)
            .and(new IntervalBase((int) Math.pow(2, max_bits) -1));

        return left.or(right);
    }


    /**
     * Rotate the [collection] right to the previous mode/inversion, assuming 
     * it has [max_bits]. 
     */
    public static IntervalBase previous_inversion(IntervalBase collection, int max_bits){
        IntervalBase interval_structure = rotateRight(collection, max_bits);
        while (interval_structure.value.mod(BigInteger.valueOf(2)).equals(BigInteger.ZERO)){
            interval_structure = rotateRight(interval_structure, max_bits);
        }
        return interval_structure;
    }


    /**
     * Rotate the [collection] right to the previous mode/inversion, assuming 
     * it has [max_bits]. 
     */
    public IntervalBase previous_inversion(int max_bits){
        IntervalBase interval_structure = rotateRight(this, max_bits);
        while (interval_structure.value.mod(BigInteger.valueOf(2)).equals(BigInteger.ZERO)){
            interval_structure = rotateRight(interval_structure, max_bits);
        }
        return interval_structure;
    }


    /**
     * Rotate the [collection] left to the next mode/inversion, assuming 
     * it has [max_bits]. 
     */
    public static IntervalBase next_inversion(IntervalBase collection, int max_bits){
        IntervalBase interval_structure = rotateRight(collection, max_bits);
        while (interval_structure.value.mod(BigInteger.valueOf(2)).equals(BigInteger.ZERO)){
            interval_structure = rotateRight(interval_structure, max_bits);
        }
        return interval_structure;
    }


    /**
     * Rotate the [collection] left to the next mode/inversion, assuming 
     * it has [max_bits]. 
     */
    public IntervalBase next_inversion(int max_bits){
        IntervalBase interval_structure = rotateRight(this, max_bits);
        while (interval_structure.value.mod(BigInteger.valueOf(2)).equals(BigInteger.ZERO)){
            interval_structure = rotateRight(interval_structure, max_bits);
        }
        return interval_structure;
    }


    /**
     * Return true if the wrapped number has the binary structure of a single
     * interval.
     */
    public boolean isValidInterval(){
        return this.bitCount() == 2 & this.value.mod(BigInteger.valueOf(2))
                                                .equals(BigInteger.ONE);
    }


    /**
     * Return true if the wrapped number is valid as an interval structure
     * with the given expected number of [max_bits] and [flipped_bits].
     */
    public boolean isValidIntervalStructure(int max_bits, int flipped_bits){
        boolean max = this.bitLength() <= max_bits;
        boolean flip = this.bitCount() == flipped_bits;
        if (flipped_bits == 0){
            flip = true;
        }
        return max & flip;
    }


    /**
     * Return an iterator that returns the individual bits that make
     * up the structure.
     */
    @Override
    public Iterator<IntervalBase> iterator(){
        Iterator<IntervalBase> it = new Iterator<IntervalBase>(){
            private IntervalBase integer = IntervalBase.valueOf(value);

            @Override
            public boolean hasNext(){
                return ! (integer.equals(BigInteger.ZERO));
            }
            @Override
            public IntervalBase next(){
                IntervalBase current_bit = integer.and(IntervalBase.not(integer).add(1));
                integer = IntervalBase.valueOf(integer.value.xor(current_bit.value));

                if (current_bit.value.mod(BigInteger.valueOf(2)).equals(BigInteger.ZERO)){
                   return current_bit.add(1);
                }
                else
                {
                   return current_bit;
                }
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
    

    /* Addition is mostly just used with +1 and -1 to change the LSB in a 
    couple situations. */
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

    /** Result of bitwise NOT on this instance */
    public IntervalBase not(){
        return IntervalBase.valueOf(this.value.not());
    } 
    /** Result of bitwise NOT for the given [interval]. */
    public static IntervalBase not(IntervalBase interval){
        return IntervalBase.valueOf(interval.value.not());
    }


    /**
     * Reduce the given [intervals] by bitwise OR on their wrapped 
     * values.
     */
    public static IntervalBase reduce(List<IntervalBase> intervals){
        return intervals.stream().reduce(IntervalBase.valueOf(0), (a, b) -> a.or(b));
    }


    /**
     * Return a list of inversions of the given [interval_structure], assuming
     * it has [max_bits].
     */
    public static List<IntervalBase> inversions(IntervalBase interval_structure, int max_bits){
        List<IntervalBase> rotations = new ArrayList<IntervalBase>();
        for (int i=0; i < interval_structure.bitCount(); i++){
            interval_structure = IntervalBase.next_inversion(interval_structure, max_bits);
            rotations.add(interval_structure);
        }
        return rotations;
    }
    /**
     * Return a list of inversions of the instance's current interval structure,
     * assuming it has [max_bits].
     */
    public List<IntervalBase> inversions(int max_bits){
        List<IntervalBase> rotations = new ArrayList<IntervalBase>();
        IntervalBase interval_structure = new IntervalBase(this);
        for (int i=0; i < this.bitCount(); i++){
            interval_structure = IntervalBase.next_inversion(interval_structure, max_bits);
            rotations.add(interval_structure);
        }
        return rotations;
    }


    /** 
     * Return an instance with a value that has been displaced by n [octave]s.
     * 
     * Use the [echo] parameter to decide whether the lowest note is repeated
     * in the higher octave(s) (=true), or omitted (=false).
     * 
     */
    public IntervalBase transpose_interval(int octave, boolean echo){
        BigInteger modifier = BigInteger.valueOf((echo == false) ? -1 : 0);

        return (new IntervalBase(this)
                    .add(modifier)
                    .shiftLeft(Constants.TONES * octave)
                    .add(BigInteger.valueOf(1)));
    }
    /** 
     * Return an instance with a value that has been displaced by n [octave]s.
     * 
     * Use the [echo] parameter to decide whether the lowest note is repeated
     * in the higher octave(s) (=true), or omitted (=false).
     * 
     */
    public static IntervalBase transpose_interval(IntervalBase interval, int octave, boolean echo){
        BigInteger modifier = BigInteger.valueOf((echo == false) ? -1 : 0);

        return (new IntervalBase(interval)
                    .add(modifier)
                    .shiftLeft(Constants.TONES * octave)
                    .add(BigInteger.valueOf(1)));
    }


}