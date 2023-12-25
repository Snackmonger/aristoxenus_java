package src.aristoxenus;

import java.lang.IllegalArgumentException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import src.aristoxenus.Constants.Keywords;
import src.aristoxenus.Classes.ScaleSynopsis;

/**
 * A collection of functions pertaining to generating, encoding, and decoding
 * alphabetic musical nomenclature.
 */
public class Nomenclature {

    /**
     * Return a chromatic octave with the given [accidental_notes].
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
     * Return a chromatic octave with binomials.
     * 
     * @return A chromatic scale with binomial accidentals.
     */
    public static List<String> chromatic() {

        List<String> accidental_notes = Constants.BINOMIALS;
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
     * Return a table mapping all possible enharmonic notes names to their
     * binomial name equivalents.
     * 
     * @return A map of all possible note and accidental combinations
     *         and their binomial equivalents.
     */
    public static Map<String, String> enharmonicDecoder() {

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

            /*
             * Flats should correspond to right shift, sharps to left shift,
             * but java uses +1 to right shift, and -1 to left shift, so we
             * reverse the sign.
             */
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
     * given [note_name].
     * 
     * @param note_name Any legal note name.
     * @return A list of enharmonically-equivalent names.
     */
    public static List<String> getEnharmonicEquivalents(String note_name) {
        List<String> equivalents = new ArrayList<String>();
        for (Map.Entry<String, String> pair : enharmonicDecoder().entrySet()) {
            if (pair.getValue() == note_name) {
                equivalents.add(pair.getKey());
            }
        }
        return equivalents;
    }

    /**
     * Define the symbols that the program recognizes as legal chord names.
     * 
     * @return A list of all legal chord symbols.
     */
    public static List<String> legalChordNames() {
        List<String> names = new ArrayList<String>();
        for (String key : enharmonicDecoder().keySet()) {
            if (key.length() < 2) {
                names.add(key);
            }
        }
        return names;
    }

    /**
     * Reduce a [note_name] with accidentals to a single alphabetic symbol.
     * 
     * @param note_name A note name with any number of accidentals,
     *                  but not a binomial.
     * @return A note name consisting of a single letter.
     */
    private static String identity(String note_name) {
        if (Constants.BINOMIALS.contains(note_name)) {
            throw new IllegalArgumentException("Cannot resolve a binomial name.");
        } else {
            return note_name.substring(0, 1);
        }
    }

    /**
     * Check whether two notes are variants of the same alphabetic letter.
     * 
     * @param note_one Any note name.
     * @param note_two Any note name.
     * @return true, if notes have the same alphabetic letter
     *         symbol.
     */
    private static boolean isHomonymous(String note_one, String note_two) {
        try {
            return identity(note_one) == identity(note_two);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Return the binomial form of a given [note_name] with up to 12
     * accidentals.
     * 
     * @param note_name Any note name.
     * @return A binomial note name.
     * @throws Exception If the note name is not a recognized symbol.
     */
    public static String decodeEnharmonic(String note_name) {
        if (Character.isDigit(note_name.charAt(note_name.length() - 1))) {
            note_name = note_name.substring(note_name.length() - 1, note_name.length());
        }
        Map<String, String> decoder = enharmonicDecoder();
        if (chromatic(Constants.BINOMIALS).contains(note_name)) {
            return note_name;
        }
        if (!decoder.containsKey(note_name)) {
            throw new IllegalArgumentException(String.format("Note name %s not recognized.", note_name));
        } else {
            return decoder.get(note_name);
        }
    }

    /**
     * Return a note with the same enharmonic value as the given [note_value],
     * but under the new given alphabetic [note_name].
     * 
     * @param note_value A note name with 0 to 1 accidentals
     *                   (binomials count as 1 accental).
     * @param note_name  One of the 7 natural notes.
     * @return A note with between 0 and 6 accidentals.
     *         No note name is more than six steps from any
     *         other name, depending on the direction.
     */
    public static String encodeEnharmonic(String note_value, String note_name) {
        if (!Constants.NATURALS.contains(note_value)) {
            throw new IllegalArgumentException("Target note name must be from the naturals.");
        }
        note_value = decodeEnharmonic(note_value);
        List<String> options = getEnharmonicEquivalents(note_value);
        List<String> homonymous_options = new ArrayList<String>();
        for (String option : options) {
            if (isHomonymous(note_name, note_name)) {
                homonymous_options.add(option);
            }
        }
        return homonymous_options.stream()
                .sorted(Comparator.comparingInt(String::length))
                .collect(Collectors.toList())
                .get(0);
    }

    /**
     * Return a scientific chromatic scale with the given type of
     * [accidental_notes] and [octave].
     * 
     * @param accidental_notes A list of 5 strings, one Constants.SHARPS,
     *                         FLATS, or BINOMIALS. This defines the style
     *                         of accidentals that the result will use.
     * @param octave           The numeral to append to the note name.
     *                         (Overload default is 0).
     * @return A scientific chromatic scale in the given style.
     */
    public static List<String> scientificOctave(List<String> accidental_notes,
            int octave) {

        return chromatic(accidental_notes)
                .stream()
                .map(i -> i + Integer.toString(octave))
                .toList();
    }

    /**
     * Return a scientific chromatic scale with the given type of
     * [accidental_notes] in the 0 octave.
     */
    public static List<String> scientificOctave(List<String> accidental_notes) {
        return scientificOctave(accidental_notes, 0);
    }

    /**
     * Return a full range of scientific notation for a given type of
     * [accidental_notes].
     * 
     * @param accidental_notes Constants.SHARPS, .FLATS, or .BINOMIALS
     * @return
     */
    public static List<String> scientificRange(List<String> accidental_notes) {
        List<String> full_range = new ArrayList<String>();
        for (int octave = 0; octave < Constants.NUMBER_OF_OCTAVES; octave++) {
            List<String> new_octave = scientificOctave(accidental_notes, octave);
            full_range.addAll(new_octave);
        }
        return full_range;
    }

    /**
     * Return a full range of scientific notation with binomial accidentals.
     */
    public static List<String> scientificRange() {
        List<String> full_range = new ArrayList<String>();
        for (int octave = 0; octave < Constants.NUMBER_OF_OCTAVES; octave++) {
            List<String> new_octave = scientificOctave(Constants.BINOMIALS, octave);
            full_range.addAll(new_octave);
        }
        return full_range;
    }

    /**
     * Return a list of 5 accidentals for the given [note_name]'s accidental
     * type.
     */
    public static List<String> getAccidentals(String note_name) {
        if (note_name.contains(Constants.BINOMIAL_DIVIDER_SYMBOL)) {
            return Constants.BINOMIALS;
        } else if (note_name.contains(Constants.SHARP_SYMBOL)) {
            return Constants.SHARPS;
        } else if (note_name.contains(Constants.FLAT_SYMBOL)) {
            return Constants.FLATS;
        } else {
            throw new IllegalArgumentException("Unknown note name.");
        }
    }

    /**
     * Encode a [note_value] with a [note_name] that occupies a [position]
     * ("above" or "below") it.
     * 
     * E.g. ("A4", "G", "below") -> "G##4"
     * ("A4", "G", "above") -> "Gbbbbbbbbbb5"
     * 
     * @param note_value Any scientific note name (sharps, flats, naturals,
     *                   or binomials), e.g. "B#4", "Db2", "F#/Gb6".
     * @param note_name  A natural note name, e.g. "A", "C", "F".
     * @param position   A position "above" or "below".
     * @return A note with the given name, plus any necessary
     *         accidentals to bring it to the given value.
     * 
     */
    public static String encodeScientificEnharmonic(String note_value,
            String note_name,
            String position) {

        int index = scientificRange(getAccidentals(note_value))
                .indexOf(note_value);
        List<String> octave = new ArrayList<String>();
        String symbol = "";

        switch (position) {
            case Keywords.BELOW -> {
                index++; // so that last note (i.e. note value) is included
                octave = scientificRange()
                        .subList(index - Constants.TONES, index);
                octave = octave.reversed();
                symbol = Constants.SHARP_SYMBOL;
            }
            case Keywords.ABOVE -> {
                octave = scientificRange()
                        .subList(index, index + Constants.TONES);
                symbol = Constants.FLAT_SYMBOL;
            }
            default -> {
                throw new IllegalArgumentException(
                        String.format("Unknown position argument %s", position));
            }
        }

        for (int i = 0; i < octave.size(); i++) {
            String note = octave.get(i);
            if (note.substring(0, note.length() - 1).equals(note_name)) {
                // "G4" -> "G" + "####" + "4"
                return note.substring(0, note.length() - 1)
                        + symbol.repeat(i)
                        + note.substring(note.length() - 1, note.length());
            }
        }
        throw new IllegalArgumentException(
                String.format("Unable to resolve name %s", note_name));
    }

    /**
     * Return the enharmonically-equivalent scientific binomial for the
     * given scientific multi-accidental or halfstep [note_name].
     * 
     * E.g. "B#4" -> "C5" "A######7" -> "D#|Eb8"
     * 
     * @param note_name : A scientific note name.
     * @return : An enharmonically-equivalent scientific binomial.
     */
    public static String decodeScientificEnharmonic(String note_name) {
        List<String> scientific_chromatic_binomials = scientificRange();
        // A binomial is already the needed note.
        if (scientific_chromatic_binomials.contains(note_name)) {
            return note_name;
        }
        // Accept only notes with a terminal numeral.
        if (!note_name.substring(note_name.length() - 1,
                note_name.length()).matches("\\d")) {
            throw new IllegalArgumentException(
                    "Must be a scientific note name in octave 0 to 8");
        }
        // Get some information about the note name.
        String alphabetic_name = note_name.substring(0)
                + note_name.substring(note_name.length() - 1,
                        note_name.length());
        int index = scientific_chromatic_binomials.indexOf(alphabetic_name);
        int sharps = Functions.countSubstring(note_name,
                Constants.SHARP_SYMBOL);
        int flats = Functions.countSubstring(note_name,
                Constants.FLAT_SYMBOL);
        if (sharps != 0 & flats != 0) {
            throw new IllegalArgumentException("Cannot mix flats and sharps");
        }
        // Find the new index +/- the number of accidentals.
        int adjustment = Constants.SHARP_VALUE * sharps;
        if (sharps == 0) {
            adjustment = Constants.FLAT_VALUE * flats;
        }
        index += adjustment;
        if (index < 0 | index > scientific_chromatic_binomials.size() - 1) {
            throw new IndexOutOfBoundsException(
                    "Target is out of the legal range.");
        }
        return scientific_chromatic_binomials.get(index);
    }

    public static String convertFrequencyToNote(double frequency, List<String> accidental_notes) {
        List<Double> frequencies = Temperament.equalTemperament();

        if (frequencies.contains(frequency)) {
            return scientificRange(accidental_notes).get(frequencies.indexOf(frequency));
        }
        for (double num : frequencies) {
            if ((int) num == (int) frequency) {
                return scientificRange(accidental_notes).get(frequencies.indexOf(num));
            }
        }
        throw new IllegalArgumentException(String.format("Frequency %s not recognized."));
    }

    /**
     * Return the frequency associated with a given [note_name] in 12-tone
     * equal temperament.
     * 
     * @param note_name Any scientific note name.
     * @return A frequency corresponding to the note name.
     */
    public static double convertNoteToFrequency(String note_name) {
        note_name = decodeScientificEnharmonic(note_name);
        int i = scientificRange().indexOf(note_name);
        return Temperament.equalTemperament().get(i);
    }

    /**
     * Force a heptatonic scale pattern to conform to ABCDEFG nomenclature.
     * 
     * For a given [note_name] and [interval_structure], return an alphabetic
     * spelling in which each of ABCDEFG (or a variant) appears exactly once.
     * The interval structure must have exactly 7 flipped bits.
     * 
     * @param note_name          A real note name (i.e. not one of the 5
     *                           binomials).
     * @param interval_structure A structure of 12 bits, of which exactly
     *                           7 are flipped.
     * @return A list of 7 strings, representing 1 each
     *         of ABCDEFG, plus accidentals.
     */
    public static List<String> forceHeptatonic(String note_name,
            IntervalBase interval_structure) {

        if (Constants.BINOMIALS.contains(note_name)) {
            throw new IllegalArgumentException(
                    "Operation cannot be performed on binomial note names");
        }
        if (interval_structure.bitCount() != Constants.NOTES |
                interval_structure.bitLength() > Constants.TONES) {
            throw new IllegalArgumentException(
                    "Operation can only be performed on heptatonic scales in 12 tone style.");
        }
        // Assemble basic alphabetic order to enforce.
        List<String> plain = Functions.rotateList(new ArrayList<String>(
                Constants.NATURALS), identity(note_name));
        // Create binomial version of the requested scale pattern.
        List<String> binomial = Rendering.renderPlain(
                interval_structure, Functions.rotateList(
                        chromatic(), decodeEnharmonic(note_name)));
        // Force each binomial note value to adopt the next alphabetic note
        // name.
        List<String> heptatonic = new ArrayList<String>();
        for (int i = 0; i < Constants.NOTES; i++) {
            heptatonic.add(encodeEnharmonic(binomial.get(i), plain.get(i)));
        }
        return heptatonic;
    }

    /**
     * Choose the best set of alphabetic note names for a given heptatonic 
     * scale.
     * 
     * Given a [note_name] and an [interval_structure] representing a 
     * heptatonic scale, return the better spelling of the two accidental 
     * types (fewest total accidentals). 
     * @param note_name             Any natural, sharp, flat, or binomial,
     *                              but not a scientific note name.
     * @param interval_structure    
     * @return
     */
    public static List<String> bestHeptatonic(String note_name,
            IntervalBase interval_structure) {

        note_name = decodeEnharmonic(note_name);
        // Naturals' default name is always the best for them.
        if (Constants.NATURALS.contains(note_name)) {
            return forceHeptatonic(note_name, interval_structure);
        }

        int note_index = Constants.BINOMIALS.indexOf(note_name);
        ScaleSynopsis sharp_scale = new ScaleSynopsis(
                forceHeptatonic(Constants.SHARPS.get(note_index), interval_structure));
        ScaleSynopsis flat_scale = new ScaleSynopsis(
                forceHeptatonic(Constants.FLATS.get(note_index), interval_structure));
        List<ScaleSynopsis> synopseis = new ArrayList<ScaleSynopsis>();
        synopseis.add(sharp_scale);
        synopseis.add(flat_scale);

        // Count the accidentals in each version
        for (ScaleSynopsis synopsis : synopseis) {
            for (String note : synopsis.scale) {
                synopsis.sharps = Functions.countSubstring(note, Constants.SHARP_SYMBOL);
                synopsis.flats = Functions.countSubstring(note, Constants.FLAT_SYMBOL);
                if (synopsis.sharps > 0 & synopsis.flats > 0) {
                    synopsis.mixed = true;
                }
            }
        }
        // Return the version with fewest accidentals.
        int s_total = sharp_scale.sharps + sharp_scale.flats;
        int f_total = flat_scale.sharps + flat_scale.flats;
        if (s_total > f_total) {
            return flat_scale.scale;
        }
        if (f_total > s_total) {
            return sharp_scale.scale;
        }

        // Both scales have the same number of accidentals. Return the
        // pure scale.
        if (sharp_scale.mixed & !flat_scale.mixed) {
            return flat_scale.scale;
        }
        if (!sharp_scale.mixed & flat_scale.mixed) {
            return sharp_scale.scale;
        }

        // Scales are both pure with the same number of accidentals.
        // Arbitrarily default to sharps.
        return sharp_scale.scale;
    }

    /**
     * Check if a given collection of [note_names] adheres to the heptatonic
     * ABCDEFG nomenclature, in which each alphebetic name appears once and
     * only once.
     * 
     * @param note_names    A list of alphabetic note names.
     * @return              True, if each of ABCDEFG appears exactly once.
     */
    public static boolean isABCDEFG(List<String> note_names){
        List<String> naturals = new ArrayList<String>(Constants.NATURALS);
        Set<String> approved = new HashSet<String>();
        for (String note: note_names){
            if (Constants.BINOMIALS.contains(note)){
                throw new IllegalArgumentException("Cannot resolve a binomial.");
            }
            if (naturals.contains(identity(note))){
                naturals.remove(identity(note));
                // Approve the decoded form so that we don't count e.g.
                // C# and Db as two separate notes.
                approved.add(decodeEnharmonic(note));
            }
        }
        return naturals.size() == 0 & approved.size() == Constants.NOTES;
    }

}
