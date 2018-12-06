import java.nio.file.*;
import java.io.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class TextDatabase extends Database {
    private Path filePath;
    private File file;

    public TextDatabase(String fileName) {
        file = new File(fileName);
        this.filePath = Paths.get(fileName);

        if (!file.exists()) {
            try {
                Files.createFile(filePath);
            } catch (Exception e) { }
        }
    }

    @Override
    public void add(String name, String number) {
        try {
            Files.write(filePath, (name + ',' + number + '\n').getBytes(), StandardOpenOption.APPEND);
        } catch (Exception e) { }
    }

    @Override
    public void delete(String name) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = null;
            String data = "";
            while ((line = reader.readLine()) != null) {
                if (!line.startsWith(name + ",")) {
                    data = data + line + "\n";
                }
            }
            reader.close();
            Files.write(filePath, data.getBytes());
        } catch (Exception e) { }
    }

    @Override
    public void update(String name, String number) {
        try {
            this.delete(name);
            this.add(name, number);
        } catch (Exception e) { }
    }

    @Override
    public String search(String name) {
        try {
            return Files.lines(filePath).filter(line -> line.startsWith(name + ",")).findFirst().get().split(",")[1];
        } catch (Exception e) { }
        return null;
    }
}