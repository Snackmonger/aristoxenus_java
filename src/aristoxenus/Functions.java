package src.aristoxenus;

import java.util.Collections;
import java.util.ArrayList;
import java.util.List;
/**
 * Bunch of miscellaneous functions.
 */
public class Functions {


    /**
     * Return a rotated form of a given [list] so that the sequence remains 
     * the same, but the given [new_first_member] now occupies index 0. 
     * 
     * This function assumes that the list is made up of unique values of
     * the same type.
     * @param <T>               Some type.
     * @param list              A list of said type.
     * @param new_first_member  A value of type.
     * @return                  Copy of the original list, rotated.
     */
    public static <T> List<T> rotateList(List<T> list, T new_first_member){
        list = new ArrayList<T>(list); 
        int modifier = -1; // Java rotates in wrong direction 
        Collections.rotate(list, modifier * list.indexOf(new_first_member));
        return list;
    }


    /**
     * Return a list made up of [list] repeated [n] times.
     * 
     * E.g. ([1, 2, 3], 3) -> [1, 2, 3, 1, 2, 3, 1, 2, 3]
     * 
     * @param <T>   Any type.
     * @param list  A list of said type.
     * @param n     The number of times to repeat the list.
     * @return      The list * n.
     */
    public static <T> List<T> repeatList(List<T> list, int n){
        List<T> dummy = new ArrayList<T>();
        for (int i=0; i<n; i++){
            dummy.addAll(list);
        }
        return dummy;
    }

    /**
     * Count the number of times a given [sub_string] occurs in a given
     * [main_string].
     * 
     * @param main_string
     * @param sub_string
     * @return
     */
    public static int countSubstring(String main_string, String sub_string){
        int count = 0;
        while (main_string.indexOf(sub_string)>-1){
            main_string = main_string.replaceFirst(sub_string, "");
            count++;
        }
            return count ;
    }



    
}
