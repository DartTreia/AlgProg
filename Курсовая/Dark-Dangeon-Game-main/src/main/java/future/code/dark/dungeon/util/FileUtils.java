package future.code.dark.dungeon.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class FileUtils {
    public static List<String> readFile(String path) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(path));
        List<String> lines = new ArrayList<>();

        while (scanner.hasNextLine()) {
            lines.add(scanner.nextLine());
        }

        return lines;
    }
    public static void writeFile(String path, long time, boolean win) throws FileNotFoundException {
        String result = "Defeat";
        long minutes = time / 60;
        Date date = new Date();

        if(win) result = "Win";

        try{
            FileWriter fileWriter = new FileWriter(path,true);
            fileWriter.write(date+"|| Result: " + result + " Time: " + minutes + "m " + time % 60+"s\n");
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("Ошибка при записи в файл");
            throw new RuntimeException(e);
        }
    }
}
