package example;

import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner read = null;
        try {
            read = new Scanner(new File("log/distribution_trace.log"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        read.useDelimiter("\\|");
        HashMap<Integer,Integer> mapRepetitions = new HashMap<>();

        while (read.hasNext())
        {
            String dataTreat = read.next();
            System.out.println("DataTreat is "+dataTreat);
            String[] data = dataTreat.split(",");
            System.out.println(Arrays.toString(data));
            int numId;
            int value;
            try {
                numId = (int)Float.parseFloat(data[0]);
                value = (int)Float.parseFloat(data[1]);
                if (!mapRepetitions.containsKey(numId)) {
                    mapRepetitions.put(numId,0);
                }

                if (value == 1) {
                    mapRepetitions.put(numId,mapRepetitions.get(numId)+1);
                }
            } catch (ArrayIndexOutOfBoundsException arraxExc) {
                System.out.println("Ignoring the last data as it wasn't saved properly");
            }


        }
        read.close();

        File statText = new File("log/distribution.csv");
        FileOutputStream is = null;
        try {
            is = new FileOutputStream(statText);
            OutputStreamWriter osw = new OutputStreamWriter(is);
            Writer w = new BufferedWriter(osw);
            for (Map.Entry<Integer, Integer> entry : mapRepetitions.entrySet()) {
                w.write(entry.getKey()+","+entry.getValue()+"\n");
            }
            w.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
