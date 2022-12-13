package ru.otus.dataprocessor;

import com.google.common.io.Resources;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import ru.otus.model.Measurement;

import java.io.*;
import java.util.List;

public class ResourcesFileLoader implements Loader {

    private final String fileName;

    public ResourcesFileLoader(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public List<Measurement> load() {
        //читает файл, парсит и возвращает результат
        try {
            Gson gson = new Gson();
            JsonReader reader = new JsonReader(new FileReader(Resources.getResource(fileName).getFile()));
            return List.of(gson.fromJson(reader, Measurement[].class));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
