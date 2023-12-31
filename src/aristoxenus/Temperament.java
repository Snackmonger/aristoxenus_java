package src.aristoxenus;

import java.util.List;
import java.util.ArrayList;

/**
 * This module supplies the frequency data used to render alphabetic notation
 * as actual sounds.
 */
public class Temperament {
    

    /**
     * Generate 12-tone equal tempered tuning.
     * 
     * @return A list of frequencies corresponding to the range of the 
     * scientific chromatic scales generated by the Nomenclature module.
     */
    public static List<Double> equalTemperament(){
        String centre_name = Constants.CENTRAL_REFERENCE_NAME;
        int centre_freq = Constants.CENTRAL_REFERENCE_FREQUENCY;
        int limit = Constants.FREQUENCY_DECIMAL_LIMITER;
        int equivalence = Constants.OCTAVE_EQUIVALENCE_FACTOR;
        List<Double> frequencies = new ArrayList<Double>();
        double frequency;
        double direction;
        double semitones;

        List<String> full_range = Nomenclature.scientificRange();
        for (String note:full_range){
            if (full_range.indexOf(note) < full_range.indexOf(centre_name)){
                direction = Constants.FLAT_VALUE;
                semitones = full_range.indexOf(centre_name) - full_range.indexOf(note);
            }
            else{
                direction = Constants.SHARP_VALUE;
                semitones = full_range.indexOf(note) - full_range.indexOf(centre_name);
            }

            /* 12 Tone Equal Temperament: 
            next = prev * 2 ** (+/-) 1/12 (+=higher, -=lower) */
            frequency = centre_freq * Math.pow(equivalence, (direction * (semitones / Constants.TONES)));
            frequency = Math.round(frequency*Math.pow(10, limit)) / Math.pow(10, limit);
            if (! frequencies.contains(frequency)){
                frequencies.add(frequency);
            }
        }
        return frequencies;
    }




}
