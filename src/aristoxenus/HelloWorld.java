package src.aristoxenus;


/*  The 'HelloWorld' class must be in a 
file called 'HelloWorld.java'; all
java classes must be segregated into
eponymous files. */ 
public class HelloWorld {
    
    // Note on quotation marks: String type uses "", char type uses ''

    // The ``main`` method is required in every java program.
    // This is the entry point where the program will always start.

    // Note that we accept an array of strings as parameter ``args``
    // representing command line arguments passed to the interpreter.
    public static void main(String[] args){

        // All statements explicitly end with ;
        System.out.print("Hello, World!");
    
        /* We can declare an array of a given type by suffixing
        the type with []. Note, however, that the array itself
        goes in curlies {}. */
        String[] letters = {"a", "b", "c", "d", "e", "f", "g"};

        boolean is_boolean = true;

        // Declare that a variable is final (constant) and cannot be reassigned
        final boolean unchanging_value = false;

        // Integer types
        byte my_bite =  100; // -128 to 127
        short my_short = 5000; // -32768 to 32767
        int my_int = 100000000; // -2147483648 to 2147483647
        // Note that a long must be suffixed with 'L'
        long my_long = 1534343434343L; // -9223372036854775808 to 9223372036854775807

        // Float types
        float my_float = 5.66f; // float is suffixed with 'f'
        double my_double = 13.535345445d; // double is suffixed with 'd'

        // types are 'widened' or 'narrowed' in casting 
        // either automatically, or manually, respectively
        int my_int2 = 9;
        double my_double2 = my_int2; // automatic casting int->double
        int my_int3 = (int) my_double2; // manual casting uses parens before value


        // char is primitive type, but String is an object (hence the
        // capital) with methods:
        String txt = "ABCDEFGABCDEFS";
        int x = txt.length();{}
        txt = txt.toLowerCase();
        txt = txt.toUpperCase();
        int y = txt.indexOf("S");
        int a=1, b=2, c=3; // multiple assignment
        int d, e, f;
        d = e = f = 100;
        }

    }
