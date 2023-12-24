package src.aristoxenus;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

public class Rendering {


    /**
     * Return a human-readable list of strings representing an 
     * [interval_structure] using the notes of a [chromatic_scale].
     * 
     * @param interval_structure    An interval collection to be rendered.
     * @param chromatic_scale       A 12-note chromatic scale that will mask
     *                              the interval structure. 
     * @return                      A list of note names representing the
     *                              given structure.
     */
    public static List<String> renderPlain(IntervalBase interval_structure, List<String> chromatic_scale){
        if (interval_structure.bitLength() > chromatic_scale.size()){
            int n = (int) ((double) chromatic_scale.size() / (double) Constants.TONES) + 1;
            chromatic_scale = Functions.repeatList(chromatic_scale, n);
        }
        int column;
        List<String> rendering = new ArrayList<String>();
        for (int i=0; i < interval_structure.bitLength(); i++){
            column = 1 << i;
            if (interval_structure.and(column).equals(column)){
                rendering.add(chromatic_scale.get(i));
            }
        }
        return rendering;
    }
    /**
     * Return a human-readable list of strings representing an 
     * [interval_structure] using the notes of a binomial chromatic scale.
     * 
     * @param interval_structure    An interval collection to be rendered.
     * @param chromatic_scale       A 12-note chromatic scale that will mask
     *                              the interval structure. 
     * @return                      A list of note names representing the
     *                              given structure.
     */
    public static List<String> renderPlain(IntervalBase interval_structure){
        List<String> chromatic_scale = Nomenclature.chromatic();
        if (interval_structure.bitLength() > chromatic_scale.size()){
            int n = (int) ((double) chromatic_scale.size() / (double) Constants.TONES) + 1;
            chromatic_scale = Functions.repeatList(chromatic_scale, n);
        }
        int column;
        List<String> rendering = new ArrayList<String>();
        for (int i=0; i < interval_structure.bitLength(); i++){
            column = 1 << i;
            if (interval_structure.and(column).equals(column)){
                rendering.add(chromatic_scale.get(i));
            }
        }
        return rendering;
    }
    
}
