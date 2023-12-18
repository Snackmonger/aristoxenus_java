x =""" // Precursor values 
    public staticfinal String ALPHABETIC_PRECURSOR = "ABCDEFG";
    public final int TONES = 12;
    public final int NOTES = 7;
    public final int SHARP_VALUE = 1;
    public final int FLAT_VALUE = -1;
    public final int NUMBER_OF_OCTAVES = 9;
    public final int OCTAVE_EQUIVALENCE_FACTOR = 2;
    public final int FREQUENCY_DECIMAL_LIMITER = 3;
    public final int CENTRAL_REFERENCE_FREQUENCY = 440;
    public final String CENTRAL_REFERENCE_NAME = "A4";

    public final class IntervalValues{
        public final static int UNISON = 1;
        public final static int HEMITONE = 3;
        public final static int TONE = 5;
        public final static int HEMIOLION = 9;
        public final static int DITONE = 17;
        public final static int DIATESSARON = 33;
        public final static int TRITONE = 65;
        public final static int DIAPENTE = 129;
        public final static int COMPOUND_HEMITONE = 257;
        public final static int COMPOUND_TONE = 513;
        public final static int COMPOUND_HEMIOLION = 1025;
        public final static int COMPOUND_DITONE = 2049;
        public final static int DIAPASON = 4097;
    } 

    // Precursor symbols
    public final String SHARP_SYMBOL = "#";
    public final String FLAT_SYMBOL = "b";
    public final String BINOMIAL_DIVIDER_SYMBOL = "|";
    public final String SLASH_CHORD_DIVIDER_SYMBOL = "/";
    public final String POLYCHORD_DIVIDER_SYMBOL = "@";
    public final String POLYCHORD_OCTAVE_SYMBOL = "^";
    public final List<String> ACCIDENTAL_SYMBOLS = List.of(SHARP_SYMBOL, FLAT_SYMBOL);

    // Descriptive strings
    public final class Modes{
        public final static String IONIAN = "ionian";
        public final static String DORIAN = "dorian";
        public final static String PHRYGIAN = "phrygian";
        public final static String LYDIAN = "lydian";
        public final static String MIXOLYDIAN = "mixolydian";
        public final static String AEOLIAN = "aeolian";
        public final static String LOCRIAN = "locrian";
    }
    public final class IntervalNames{
        public final static String HEMITONE = "hemitone";
        public final static String TONE = "tone";
        public final static String HEMIOLION = "hemiolion";
        public final static String DITONE = "ditone";
        public final static String DIATESSARON = "diatessaron";
        public final static String TRITONE = "tritone";
        public final static String DIAPENTE = "diapente";
        public final static String COMPOUND_HEMITONE = "compound_hemitone";
        public final static String COMPOUND_TONE = "compound_tone";
        public final static String COMPOUND_HEMIOLION = "compound_hemiolion";
        public final static String COMPOUND_DITONE = "compound_ditone";
        public final static String DIAPASON = "diapason";
    }
    public final class IntervalQualities{
        public final static String NATURAL = "natural";
        public final static String MINOR = "minor";
        public final static String DIMINISHED = "diminished";
        public final static String MAJOR = "major";
        public final static String AUGMENTED = "augmented";
        public final static String PERFECT = "perfect";
    }
    public final class OrdinalNumbers{
        public final static String TONIC = "first";
        public final static String SECOND = "second";
        public final static String THIRD = "third";
        public final static String FOURTH = "fourth";
        public final static String FIFTH = "fifth";
        public final static String SIXTH = "sixth";
        public final static String SEVENTH = "seventh";
        public final static String EIGHTH = "eighth";
        public final static String NINTH = "ninth";
        public final static String TENTH = "tenth";
        public final static String ELEVENTH = "eleventh";
        public final static String TWELFTH = "twelfth";
        public final static String THIRTEENTH = "thirteenth";
        public final static String FOURTEENTH = "fourteenth";
        public final static String FIFTEENTH = "fifteenth";
    }
    public final class CardinalNumbers{
        public final static String ONE= "one";
        public final static String TWO= "two";
        public final static String THREE= "three";
        public final static String FOUR= "four";
        public final static String FIVE= "five";
        public final static String SIX= "six";
        public final static String SEVEN= "seven";
        public final static String EIGHT= "eight";
        public final static String NINE= "nine";
        public final static String TEN= "ten";
        public final static String ELEVEN= "eleven";
        public final static String TWELVE= "twelve";
        public final static String THIRTEEN= "thirteen";
        public final static String FOURTEEN= "fourteen";
        public final static String FIFTEEN= "fifteen";
    }
    public final class TupleNumerationNames{
        public final static String SINGLE= "single";
        public final static String DOUBLE= "double";
        public final static String TRIPLE= "triple";
        public final static String QUADRUPLE= "quadruple";
        public final static String QUINTUPLE= "quintuple";
        public final static String SEXTUPLE= "sextuple";
        public final static String SEPTUPLE= "septuple";
        public final static String OCTUPLE= "octuple";
        public final static String NONUPLE= "nonuple";
        public final static String DECUPLE= "decuple";
        public final static String UNDECUPLE= "undecuple";
        public final static String DUODECUPLE= "duopdecuple";
        public final static String TREDECUPLE= "tredecuple";
        public final static String QUATTUORDECUPLE= "quattuordecuple";
        public final static String QUINDECUPLE= "quindecuple";
    }
    public final class TonalNumerationNames{
        public final static String MONOTONIC= "monotonic";
        public final static String DITONIC= "ditonic";
        public final static String TRITONIC= "tritonic";
        public final static String TETRATONIC= "tetratonic";
        public final static String PENTATONIC= "pentatonic";
        public final static String HEXATONIC= "hexatonic";
        public final static String HEPTATONIC= "heptatonic";
        public final static String OCTATONIC= "octatonic";
        public final static String ENNEATONIC= "enneatonic";
        public final static String DECATONIC= "decatonic";
        public final static String HENDECATONIC= "hendecatonic";
        public final static String DUODECATONIC= "duodecatonic";
    }
    public final class BasalNumerationNames{
        public final static String PRIMAL= "primal";
        public final static String SECUNDAL= "secundal";
        public final static String TERTIAL= "tertial";
        public final static String QUARTAL= "quartal";
        public final static String QUINTAL= "quintal";
        public final static String SEXTAL= "sextal";
        public final static String SEPTIMAL= "septimal";
        public final static String OCTONAL= "octonal";
        public final static String NONAL= "nonal";
        public final static String DECIMAL= "decimal";
        public final static String UNDECIMAL= "undecimal";
        public final static String DUODECIMAL= "duodecimal";
    }


    // Nomenclature constants generated from precursor constants.
    public final List<String> NATURALS = List.of(
        ALPHABETIC_PRECURSOR.substring(2) + 
        ALPHABETIC_PRECURSOR.substring(0, 3)
    );

    public final Map<String, String> HALFSTEPS = Map.ofEntries(
        Map.entry(NATURALS.get(2), NATURALS.get(3)), 
        Map.entry(NATURALS.get(6), NATURALS.get(0))
    );

    public final List<String> SHARPS = NATURALS.stream()
        .filter(i -> ! HALFSTEPS.containsKey(i))
        .map(i -> i + SHARP_SYMBOL)
        .collect(Collectors.toList())
    ;

    public final List<String> FLATS = NATURALS.stream()
        .filter(i -> ! HALFSTEPS.containsValue(i))
        .map(i -> i + FLAT_SYMBOL)
        .collect(Collectors.toList())
    ;"""




def javafy_pyconstants(text: str) -> str:
    text = text.replace("'", '"').replace(": str ", "")
    list_ = text.split("\n")
    list_ = ["public final static String " + x + ";\n" for x in list_]
    return ''.join(list_)


print(x.replace("public final", "public static final"))