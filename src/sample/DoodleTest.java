package sample;

import java.util.LinkedList;
import java.util.ListIterator;

/**
 * Created by Someon on 06.01.2016.
 */
public class DoodleTest {
    public static void main(String[] args) {
        final LinkedList<String> song = new LinkedList();
        song.add("Push me");
        song.add("And then just touch me");
        song.add("Do I can’t get my satisfaction");
        song.add("Satisfaction, satisfaction,");
        song.add("Satisfaction, satisfaction");

        ListIterator<String> itr = song.listIterator();

        System.out.println(itr.next());
        System.out.println(itr.next());
        System.out.println(itr.next());

    }
}
