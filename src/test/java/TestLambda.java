import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TestLambda {

    ArrayList<Integer> integerList = new ArrayList<Integer>(){
    };


    public TestLambda() {
        initIntegerList();
    }

    private void initIntegerList() {
        try (Scanner scanner = new Scanner(new File(getClass().getResource("data.csv").getFile()));) {
            while (scanner.hasNextLine()) {
                integerList.addAll(getRecordFromLine(scanner.nextLine()));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private List<Integer> getRecordFromLine(String line) {
        List<Integer> values = new ArrayList<>();
        try (Scanner rowScanner = new Scanner(line)) {
            rowScanner.useDelimiter(",");
            while (rowScanner.hasNext()) {
                values.add(Integer.parseInt(rowScanner.next()));
            }
        }
        return values;
    }


    @Test
    public void testForSumNumbers() {
        long initTime = System.currentTimeMillis();
        int sum = 0;
        for (int i = 0; i< integerList.size(); i++) {
            sum += integerList.get(i);
        }
        long endTime = System.currentTimeMillis() - initTime;
        System.out.println("".format("La suma con 'for' %d tardó %d milisegundos", sum, endTime));
    }


    @Test
    public void testStreamSumNumbers() {
        long initTime = System.currentTimeMillis();

        int sum = integerList.stream().parallel().mapToInt(Integer::intValue).sum();

        long endTime = System.currentTimeMillis() - initTime;
        System.out.println("".format("La suma con 'stream' %d tardó %d milisegundos", sum, endTime));
    }

    @Test
    public void testReduceSumNumbers() {
        long initTime = System.currentTimeMillis();

        int sum = integerList.stream().reduce(0, (a, b) -> a + b);

        long endTime = System.currentTimeMillis() - initTime;
        System.out.println("".format("La suma con 'stream.reduce' %d tardó %d milisegundos", sum, endTime));
    }
}
