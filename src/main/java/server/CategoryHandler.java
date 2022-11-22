package server;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CategoryHandler {

    protected String[] categoriesToChoose() {
        return new String[] {"data", "mat", "film"};
    }

    public List<String[]> getQuestions(String filePath) {
        try {
            Path path = Path.of("src", "main", "resources", "server", filePath);

            String strFile = Files.readString(path);

            String[] questionsArray;
            String[] question;
            List<String[]> listOfQuestions = new ArrayList<>();

            questionsArray = strFile.split("-");
            for (int i = 0; i < questionsArray.length; i++) {
                question = questionsArray[i].split("\n");
                listOfQuestions.add(question);
            }
            int randIndex = 0;
            ArrayList<String[]> finalQuestions = new ArrayList<>();
            for (int i = 0; i < 3; i++) {
                Random r = new Random();
                randIndex = r.nextInt(0, listOfQuestions.size() - 1);
                finalQuestions.add(listOfQuestions.get(randIndex));
            }
            return finalQuestions;
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }
}

