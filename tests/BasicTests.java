package tests;
import java.util.ArrayList;
import java.util.List;

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
        // test_bitwise();

        String z = Nomenclature.encode_scientific_enharmonic("A4", "G", "below");
        System.out.println(z);
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
        
        System.out.println(String.format(
            "\nExpect DITONE = 17 (%s), binary = 10001 (%s).", 
            IntervalValues.DITONE, 
            Integer.toBinaryString(IntervalValues.DITONE)
        ));

        System.out.println(String.format(
            "\nExpect DITONE | DIAPENTE = 145 (%s), binary = 10010001 (%s).", 
            IntervalValues.DITONE | IntervalValues.DIAPENTE,
            Integer.toBinaryString(IntervalValues.DITONE | IntervalValues.DIAPENTE)
        ));

        IntervalBase starting_value = new IntervalBase(IntervalValues.DITONE | IntervalValues.DIAPENTE | IntervalValues.DIAPASON);
        System.out.println(String.format(
            "\nExpect DITONE | DIAPENTE | DIAPASON = 4241 (%s), binary = 1000010010001 (%s)", 
            starting_value, 
            starting_value.toBinaryString()));

        System.out.println("Test interval permutations for 4241...");
        List<IntervalBase> newlist = new ArrayList<IntervalBase>();
        for (IntervalBase interval:starting_value){
            System.out.println(String.format("Decimal: %s ", interval));
            System.out.println(String.format("Binary: %s", interval.toBinaryString()));
            newlist.add(interval);
        }
        System.out.println(String.format("Collected the intervals in a list: %s", newlist.toString()));
        System.out.println("Attempting to reduce intervals with bitwise OR...");
        IntervalBase compounded_number = IntervalBase.reduce(newlist);
        System.out.println(
            String.format(
                "Result is %s: %s", 
                compounded_number, compounded_number.equals(starting_value) 
                ? "Success!" : "Failure!"
            )
        );

        
    }







}
