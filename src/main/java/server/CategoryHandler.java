package server;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CategoryHandler {

    private final int numberOfQuestions;

    protected CategoryHandler(int numberOfQuestions) {
        this.numberOfQuestions = numberOfQuestions;
    }

    protected String[] categoriesToChoose() {
        return new String[]{"Sport", "Vetenskap", "Musik"};
    }

    public List<String[]> getQuestions(String filePath) {
        try {
            Path path = Path.of("src", "main", "resources", "server", filePath);

            String strFile = Files.readString(path);

            String[] questionsArray;
            String[] question;
            List<String[]> listOfQuestions = new ArrayList<>();

            questionsArray = strFile.split("¤");
            for (int i = 0; i < questionsArray.length; i++) {
                question = questionsArray[i].split("\n");
                listOfQuestions.add(question);
            }

            int randIndex;
            ArrayList<String[]> finalQuestions = new ArrayList<>();
            List<Integer> validQuestion = new ArrayList<>();
            while(validQuestion.size() < numberOfQuestions) {
                Random r = new Random();
                randIndex = r.nextInt(1, listOfQuestions.size() - 1); // origin have to be 1.
                if(!validQuestion.contains(randIndex)){
                    validQuestion.add(randIndex);
                    finalQuestions.add(listOfQuestions.get(randIndex));
                }
            }

            return finalQuestions;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

