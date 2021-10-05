/*
 * @author DivaevAM
 * @since 05.10.2021
 */

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MainStreams {

    public static int[] generateValues() {
        final Random rnd = new Random();
        return rnd.ints(1 + rnd.nextInt(10), 1, 10).toArray();
    }


    public static List<Integer> oddOrEven(List<Integer> integers) {
        Integer sum = integers.stream().mapToInt(Integer::intValue).sum();

        List<Integer> resultList = null;
        resultList = integers.stream().filter(elem -> {
            if (sum.intValue() % 2 == 0) {
                return elem % 2 == 0;
            } else {
                return elem % 2 != 0;
            }
        }).collect(Collectors.toList());

        return resultList;
    }

    public static int minValue(int[] values) {
//        System.out.println("Уникальные значения!");
        Set<Integer> uniqueSet = new HashSet<Integer>(Arrays.asList(convertToObject(values)));
        final StringBuilder sb = new StringBuilder();
        uniqueSet.stream()
                .forEach(num ->
                        sb.append(num.toString())
                );

        return new Integer(sb.toString()).intValue();
    }

    public static Integer[] convertToObject(int[] values) {
        Integer[] arrayOfObject = new Integer[values.length];
        for (int i = 0; i < values.length; i++) {
            arrayOfObject[i] = new Integer(values[i]);
        }
        return arrayOfObject;
    }

    public static void main(String[] args) {
//        for (int i = 0; i < 10; i++) {
//            System.out.println("Массив номер № " + i);
//            int[] array = generateValues();
//            Arrays.stream(array).forEach(System.out::println);
        /*System.out.println(minValue(array));*/
//        }

        oddOrEven(Arrays.asList(1, 2, 3, 4)).forEach(System.out::println);
    }
}
