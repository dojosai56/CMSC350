/**
 * Name: Sairam Soundararajan
 * Date: 2-8-21
 * Course: CMSC350: Data Structures and Analysis
 * Project 2
 * Description: The OrderedList class determines whether a List object, supplied as a parameter, is in strictly ascending order
 */
import java.util.ArrayList;
import java.util.Comparator;

public class OrderedList {
    public static <E extends Comparable> boolean checkSorted(ArrayList<E> list) {
        return checkSorted(list, new ComparatorObj<E>());
    } // checkSorted

    public static <E, T extends Comparator> boolean checkSorted(ArrayList<E> list, T comparator) {
        for(int i =0; i < list.size() - 1;i++) {
            if(comparator.compare(list.get(i), list.get(i+1)) == 1)
            {
                return false;
            }
        }
        return true;
    } // checkSorted

    static class ComparatorObj<T extends Comparable> implements Comparator<T> {

        @Override
        public int compare(T o1, T o2) {

            return o1.compareTo(o2);
        }


    }
}
