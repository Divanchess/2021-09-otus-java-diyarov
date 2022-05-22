package ru.otus.dataprocessor;

import com.google.gson.Gson;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class FileSerializer implements Serializer {
    private String fileName;

    public FileSerializer(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void serialize(Map<String, Double> data) throws FileProcessException {
        Gson gson = new Gson();
        try (var writer = new FileWriter(fileName)) {
            gson.toJson(data, writer);
        } catch (IOException e) {
            throw new FileProcessException(e);
        }
    }
}
