package src.aristoxenus;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * A collection of functions pertaining to generating, encoding, and decoding
 * alphabetic musical nomenclature.
 */
public class Nomenclature {


    /**
     * Return a chromatic octave with the given accidentals.
     * 
     * @param accidental_notes A list of 5 note names, one of the SHARPS,
     *                         FLATS, or BINOMIALS in the Constants class.
     * 
     * @return A list of 12 note names; the NATURALS plus
     *         the given accidentals.
     */
    public static List<String> chromatic(List<String> accidental_notes) {

        List<String> new_scale = new ArrayList<String>();
        int accidentals_in_scale = 0;

        for (String note : Constants.NATURALS) {
            new_scale.add(note);
            if (!Constants.HALFSTEPS.containsKey(note)) {
                new_scale.add(accidental_notes.get(accidentals_in_scale));
                accidentals_in_scale++;
            }
        }
        return new_scale;
    }


    /**
     * Return a table for decoding the binomial form of a note with any number
     * of sharps or flats.
     * 
     * @return A map of all possible note and accidental combinations
     *         and their binomial equivalents.
     */
    public static Map<String, String> enharmonic_decoder() {

        Map<String, String> decoder = new HashMap<>();
        List<String> chromatic_binomials = chromatic(Constants.BINOMIALS);

        for (String accidental : Constants.ACCIDENTAL_SYMBOLS) {
            List<String> dummy_binomials = new ArrayList<String>(chromatic_binomials);
            int shift_degree = Constants.SHARP_VALUE;

            // Reverse sequence if using flats.
            if (accidental == Constants.FLAT_SYMBOL) {
                shift_degree = Constants.FLAT_VALUE + Constants.TONES;
                Collections.reverse(dummy_binomials);
                Collections.rotate(dummy_binomials, -shift_degree);
            }

            /* Flats should correspond to right shift, sharps to left shift, 
            but java  uses +1 to right shift, and -1 to left shift, so we 
            reverse the sign. */
            shift_degree *= -1;

            for (String binomial : chromatic_binomials) {
                if (!Constants.BINOMIALS.contains(binomial)) {
                    for (int i = 0; i < Constants.TONES; i++) {
                        decoder.put(
                            binomial + accidental.repeat(i), 
                            dummy_binomials.get(i));
                    }
                }
                Collections.rotate(dummy_binomials, shift_degree); 
            }
        }
        return decoder;
    }


    /**
     * Get all enharmonic variant names, with up to 11 sharps/flats, for a 
     * given name.
     * 
     * @param note_name     Any legal note name.
     * @return              A list of enharmonically-equivalent names.
     */
    public static List<String> get_enharmonic_equivalents(String note_name) {
        List<String> equivalents = new ArrayList<String>();
        for (Map.Entry<String, String> pair : enharmonic_decoder().entrySet()) {
            if (pair.getValue() == note_name) {
                equivalents.add(pair.getKey());
            }
        }
        return equivalents;
    }

    /**
     * Defines the symbols that the program recognizes as legal chord names.
     * 
     * @return      A list of all legal chord symbols.
     */
    public static List<String> legal_chord_names() {
        List<String> names = new ArrayList<String>();
        for (String key : enharmonic_decoder().keySet()){
            if (key.length() < 2) {
                names.add(key);
            }
        }
        return names;
    }

    /**
     * Reduce a note name with accidentals to a single alphabetic symbol.
     * 
     * @param note_name     A note name with any number of accidentals,
     *                      but not a binomial.
     * @return              A note name consisting of a single letter.
     */
    private static String identity(String note_name) throws Exception {
        if (Constants.BINOMIALS.contains(note_name)){
            throw new Exception("Cannot resolve a binomial name.");
        }
        else {
            return note_name.substring(0, 1);
        }
    }

    
    /**
     * Check whether two notes are variants of the same alphabetic letter.
     * 
     * @param note_one      Any note name.
     * @param note_two      Any note name.
     * @return              true, if notes have the same alphabetic letter 
     *                      symbol.
     */
    private static boolean is_homonomous(String note_one, String note_two){
        try {
            return identity(note_one) == identity(note_two);
        } 
        catch (Exception e) {
            return false;
        }
    }

    /**
     * Return the binomial form of a given note name with up to 12 
     * accidentals.
     * 
     * @param note_name     Any note name.
     * @return              A binomial note name.
     * @throws Exception    If the note name is not a recognized symbol.
     */
    public static String decode_enharmonic(String note_name) throws Exception {
        if (Character.isDigit(note_name.charAt(note_name.length()-1))){
            note_name = note_name.substring(note_name.length()-1, note_name.length());
        }
        Map<String, String> decoder = enharmonic_decoder();
        if (chromatic(Constants.BINOMIALS).contains(note_name)){
            return note_name;
        }
        if (!decoder.containsKey(note_name)){
            throw new Exception(String.format("Note name %s not recognized.", note_name));
        }
        else {
            return decoder.get(note_name);
        }
    }

    /**
     * Return a note with the same enharmonic value as the given note,
     * but under the new given alphabetic name.
     * 
     * @param note_value    A note name with 0 to 1 accidentals 
     *                      (binomials count as 1 accental).
     * @param note_name     One of the 7 natural notes.
     * @return              A note with between 0 and 6 accidentals. 
     *                      No note name is more than six steps from any 
     *                      other name, depending on the direction.
     * @throws Exception    If the note value is not a natural note name.
     */
    public static String encode_enharmonic(String note_value, String note_name) throws Exception{
        if (! Constants.NATURALS.contains(note_value)){
            throw new Exception("Target note name must be from the naturals.");
        }
        note_value = decode_enharmonic(note_value);
        List<String> options = get_enharmonic_equivalents(note_value);
        List<String> homonymous_options = new ArrayList<String>();
        for (String option : options){
            if (is_homonomous(note_name, note_name)){
                homonymous_options.add(option);
            }
        }
        return homonymous_options.stream()
                .sorted(Comparator.comparingInt(String::length))
                .collect(Collectors.toList())
                .get(0);
    }


    /**
     * Return a scientific chromatic scale in the given style and octave.
     * 
     * @param accidental_notes      A list of 5 strings, one Constants.SHARPS, 
     *                              FLATS, or BINOMIALS. This defines the style 
     *                              of accidentals that the result will use.
     * @param octave                The numeral to append to the note name.
     *                              (Overload default is 0).
     * @return                      A scientific chromatic scale in the given style.
     */
    public static List<String> scientific_octave(List<String> accidental_notes, int octave){

        return chromatic(accidental_notes)
            .stream()
            .map(i -> i + Integer.toString(octave))
            .toList();
    }
    public static List<String> scientific_octave(List<String> accidental_notes){
        return scientific_octave(accidental_notes, 0);
    }


}
