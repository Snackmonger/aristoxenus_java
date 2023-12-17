package src.aristoxenus;

public class BitwiseFunctions {

    public static boolean hasInterval(int interval_structure, int interval){
        return (interval_structure & interval) == interval;
    }

    
    
}
