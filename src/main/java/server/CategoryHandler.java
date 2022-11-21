package server;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class CategoryHandler {

    public CategoryHandler() {
    }

    public void getContries(){} // exempli kategori

    public void getAnimal(){} // exempli kategori

    public List<String[]> getSport() throws IOException {
        Path path = Path.of("src", "main", "resources", "server", "Sport");

        String strFile = Files.readString(path);
        String[] questionsArray;
        String[] question;
        List<String[]> listOfQuestions = new ArrayList<>();

        questionsArray = strFile.split("-");
        for (int i = 0; i < questionsArray.length; i++) {
            question = questionsArray[i].split("\n");
            listOfQuestions.add(question);
        }
        return listOfQuestions;
    }

    public static void main(String[] args) {
        new CategoryHandler();
    }
}

