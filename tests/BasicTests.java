package tests;
import java.math.BigInteger;
import java.util.concurrent.TimeUnit;

import src.aristoxenus.Bitwise;
import src.aristoxenus.IntervalBase;
import src.aristoxenus.Constants;
import src.aristoxenus.Constants.IntervalValues;
import src.aristoxenus.Nomenclature;


public class BasicTests {
    

    public static void main(String[] args) {
        /**
         * Run through a series of tests to see whether the functions and 
         * constants are returning the expected values
         * 
         */

        //  test_constants();
        //  System.out.println("\n");
        //  test_nomenclature();
        //  System.out.println("\n");
        //  test_conversions();
        //  System.out.println("\n");
        test_bitwise();
    }

    /**
    * Test that the constants have been correctly derived from the precursors.
    */
    public static void test_constants() {
        System.out.println("\nNaturals:");
        System.out.println(Constants.NATURALS);
        System.out.println("\nSharps:");
        System.out.println(Constants.SHARPS);
        System.out.println("\nFlats:");
        System.out.println(Constants.FLATS);
        System.out.println("\nBinomials:");
        System.out.println(Constants.BINOMIALS);
        System.out.println("\nAll real accidental notes:");
        System.out.println(Constants.ACCIDENTAL_NOTES);
        System.out.println("\nSets of real and abstract accidental types: ");
        System.out.println(Constants.ACCIDENTAL_TYPES);
    }

        /**
        * Test that the conversion functions generate the expected values.
        */
    public static void test_conversions() {
    
    }

    public static void test_nomenclature() {
        System.out.println("\nChromatic scale, binomial: ");
        System.out.println(Nomenclature.chromatic(Constants.BINOMIALS));
        System.out.println("\nChromatic scale, sharp: ");
        System.out.println(Nomenclature.chromatic(Constants.SHARPS));
        System.out.println("\nChromatic scale, flat: ");
        System.out.println(Nomenclature.chromatic(Constants.FLATS));
        System.out.println("\nEnharmonic decoder map: ");
        System.out.println(Nomenclature.enharmonic_decoder());
        System.out.println("\nChromatic scale, binomial, scientific octave=0: ");
        System.out.println(Nomenclature.scientific_octave(Constants.BINOMIALS));
        System.out.println("\nChromatic scale, sharp, scientific octave=4: ");
        System.out.println(Nomenclature.scientific_octave(Constants.SHARPS, 4));

    }



    public static void test_bitwise(){
        
        // System.out.println("\nExpect DITONE | DIAPENTE = 145, actual =");
        // System.out.println(IntervalValues.DITONE | IntervalValues.DIAPENTE);
        // System.out.println("\nExpect DITONE | DIAPENTE = 145, actual =");
        // System.out.println(Integer.toBinaryString(IntervalValues.DITONE | IntervalValues.DIAPENTE));

        // System.out.println("\nExpect HEMIOLION | DIAPENTE = 137");
        // System.out.println(IntervalValues.HEMIOLION | IntervalValues.DIAPENTE);

        // System.out.println("\nExpect ~DITONE = 14 or ");
        // System.out.println(~IntervalValues.DITONE);

        // System.out.println(Integer.toBinaryString(~IntervalValues.DITONE));
        // System.out.println(Integer.toBinaryString(~IntervalValues.DITONE + 1));
        // System.out.println(Integer.toBinaryString((IntervalValues.DITONE) & (~IntervalValues.DITONE + 1)));

        BigInteger x = BigInteger.valueOf(IntervalValues.DITONE|IntervalValues.DIAPENTE|IntervalValues.DIAPASON);
        
        IntervalBase yamahama = new IntervalBase(x);

        yamahama.shiftLeft(4);
        yamahama = yamahama.add(1);
        System.out.println(yamahama.toBinaryString());
        for (IntervalBase interval : yamahama){
            System.out.println(interval);
            System.out.println(interval.toBinaryString());

            try {
            TimeUnit.SECONDS.sleep(1);
            }
            catch (InterruptedException e){}
        }



        // System.out.println("\nTesting validate_interval(0b1000) (expect false) ");
        // System.out.println(Bitwise.validate_interval(BigInteger.valueOf(0b1000)));
        // System.out.println("\nTesting validate_interval(0b1001) (expect true) ");
        // System.out.println(Bitwise.validate_interval(BigInteger.valueOf(0b1001)));
    }







}
