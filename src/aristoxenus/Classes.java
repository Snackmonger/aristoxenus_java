package src.aristoxenus;

import java.util.List;

public class Classes {
    
    public static class ScaleSynopsis{
            int sharps = 0;
            int flats = 0;
            List<String> scale;
            boolean mixed = false;
            public ScaleSynopsis (List<String> scale){
                this.scale = scale;
            }
        }
}
