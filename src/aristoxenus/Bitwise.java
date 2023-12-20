package src.aristoxenus;

import java.math.BigInteger;

public class Bitwise {


    /**
     * Checks whether an integer can be used as a valid interval.
     * 
     * This entails that there are exactly 2 flipped bits, and that the 
     * integer is an odd number.
     * 
     * @param interval      A number you want to check.
     * @return              True, if the number can be used as an interval.
     */
    public static boolean validate_interval(BigInteger interval){
        return (interval.bitCount() == 2 & interval
                                            .mod(BigInteger.valueOf(2))
                                            .equals(BigInteger.valueOf(1))
                );
    }


    /**
     * Check whether the interval structure is within the specified maximum
     * number of bits.
     * 
     * @param interval_structure    A number representing an interval structure.
     * @param max_bits              The maximum length of the structure in bits.
     * @param flipped_bits          The expected number of flipped bits 
     *                              (0 = no expectation).
     * @return                      True, if the structure meets these 
     *                              conditions.
     */
    public static boolean validate_interval_structure(BigInteger interval_structure, int max_bits, int flipped_bits){
        boolean max = interval_structure.bitLength() <= max_bits;
        boolean flip = interval_structure.bitCount() == flipped_bits;
        if (flipped_bits == 0){
            flip = true;
        }
        return max & flip;
    }
    public static boolean validate_interval_structure(BigInteger interval_structure, int max_bits){
        return validate_interval_structure(interval_structure, max_bits, 0);
    }


    /**
     * Check whether an interval structure contains a given interval.
     * 
     * @param interval_structure    A number representing an interval structure.
     * @param interval              A number representing an interval.
     * @return                      True, if the interval is in the structure.
     */
    public static boolean has_interval(BigInteger interval_structure, BigInteger interval){
        return interval.and(interval_structure).equals(interval);
    }
    

    /** 
     * 
     * 
    */
    public static BigInteger transpose_interval(BigInteger interval, int octave, boolean echo){
        BigInteger modifier = BigInteger.valueOf((echo == false) ? -1 : 0);
        return (interval.add(modifier)
                    .shiftLeft(Constants.TONES * octave)
                    .add(BigInteger.valueOf(1)));

    }
    
}
