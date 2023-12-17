package src.aristoxenus;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A collection of functions pertaining to generating, encoding, and decoding
 * alphabetic musical nomenclature.
 */
public class NomenclatureFunctions {
    
    /**
     * Return a chromatic octave with the given accidentals.
     * 
     * @param accidental_notes  A list of 5 note names, one of the SHARPS,
     *                          FLATS, or BINOMIALS in the Constants class.
     * @return                  A list of 12 note names; the NATURALS plus
     *                          the given accidentals.
     */
    public static List<String> chromatic (List<String> accidental_notes) {
        
        List<String> new_scale;
        int accidentals_in_scale = 0;

        for (String note : Constants.NATURALS) {
            new_scale.add(note)
            if (!Constants.HALFSTEPS.containsKey(note)) {
                new_scale.add(accidental_notes.get(accidentals_in_scale))
                accidentals_in_scale++;
            }
        }
        return new_scale;
    }

    /**
     * Return a table for decoding the binomial form of a note with any number
     * of sharps or flats.
     * 
     * @return      A map of all possible note and accidental combinations
     *              and their binomial equivalents.
     */
    public static Map<String, String> enharmonic_decoder () {

        Map<String, String> decoder = new HashMap<>();
        List<String> chromatic_binomials = chromatic(Constants.BINOMIALS);
        for (String accidental : Constants.ACCIDENTAL_SYMBOLS){
            List<String> dummy_binomials = List.copyOf(chromatic_binomials);
            int shift_degree = Constants.SHARP_VALUE;

            if (accidental == Constants.FLAT_SYMBOL) {
                shift_degree = Constants.FLAT_VALUE + Constants.TONES;
                dummy_binomials = dummy_binomials.reversed();
                dummy_binomials = dummy_binomials.subList(shift_degree, dummy_binomials.size());
                dummy_binomials.addAll(dummy_binomials.subList(0, shift_degree));
            }

            for (String binomial : chromatic_binomials) {
                if (!Constants.BINOMIALS.contains(binomial)) {
                    for (int added_accidental = 0; added_accidental < Constants.TONES; added_accidental++) {
                        decoder.put(binomial + accidental.repeat(added_accidental), dummy_binomials.get(added_accidental));
                        dummy_binomials = dummy_binomials.subList(shift_degree, dummy_binomials.size());
                        dummy_binomials.addAll(dummy_binomials.subList(0, shift_degree));
                    }
                }
            }
        }
        return decoder;
    }

    
}
