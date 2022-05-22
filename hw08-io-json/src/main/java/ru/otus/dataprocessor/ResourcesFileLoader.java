package ru.otus.dataprocessor;

import com.google.gson.Gson;
import ru.otus.model.Measurement;

import java.io.*;
import java.util.List;

public class ResourcesFileLoader implements Loader {
    private String fileName;

    public ResourcesFileLoader(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public List<Measurement> load() throws FileProcessException {
        Gson gson = new Gson();
        try (InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
             InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
             BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        ) {
            Measurement[] measurements = gson.fromJson(bufferedReader, Measurement[].class);
            return List.of(measurements);
        } catch (IOException e) {
            throw new FileProcessException(e);
        }
    }
}
