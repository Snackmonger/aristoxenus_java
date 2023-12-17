package tests;
import src.aristoxenus.Constants;
import src.aristoxenus.NomenclatureFunctions;

public class ConstantTests {
    

    public static void main(String[] args) {
        /**
         * Run through a series of tests to see whether the functions and 
         * constants are returning the expected values
         * 
         */

         test_constants();
    }

    /**
    * Test that the precursors are generating the correct constants.
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
        System.out.println("\nEnharmonic decoder table: ");
        System.out.println(Constants.ACCIDENTAL_TYPES);
    }














}
