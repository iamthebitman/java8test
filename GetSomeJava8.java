package no.shit.done.java8;

import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GetSomeJava8 {

    public static void main(String[] args) {
        String chars = "qwerty1"; //small vocabulary means more collisions
        String[] strings = new String[4000000];
        for (int i = 0; i < strings.length; i++) {
            strings[i] = Character.toString(chars.charAt(new Random().nextInt(chars.length()))); //looks owful. takes random char and puts into array
        }
        for (int i = 0; i < 20; i++) {
            check(strings, chars); //runs both toConcurrentMap and toMap and compares them
        }
    }

    private static void check(String[] strings, String chars) {
        Stream<String> words = Stream.of(strings);
        Map<String, Integer> wordsCount = words.collect(Collectors.toMap(s -> s, s -> 1,
                (i, j) -> i + j));

        Stream<String> words2 = Stream.of(strings);
        Map<String, Integer> wordsCount2 = words2.collect(Collectors.toConcurrentMap(s -> s, s -> 1,
                (i, j) -> i + j));

        for (char ch : chars.toCharArray()) {
            String s = Character.toString(ch);
            final Integer v1 = wordsCount.get(s);
            final Integer v2 = wordsCount2.get(s);

            if (v1 == null || !v1.equals(v2)) { //yeah NullPointer. not a chance
                System.out.println("s: " + s + " v1: " + v1 + " v2 " + v2);
            }
        }
    }
}
