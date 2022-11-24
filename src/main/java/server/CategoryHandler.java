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
        int counter = 0;
        String[] categories = {"Sport", "Historia", "Musik", "Vetenskap", "Film", "Geografi"};
        String[] finalCategories = new String[3];
        List<String> randomizedCategories = new ArrayList<>();
        while (counter < 3) {
            Random r = new Random();
            int randomIndex = r.nextInt(0, categories.length);
            if (!randomizedCategories.contains(categories[randomIndex])) {
                randomizedCategories.add(categories[randomIndex]);
                counter++;
            }
        }
        counter = 0;
        for (String randomizedCategory : randomizedCategories) {
            finalCategories[counter] = randomizedCategory;
            counter++;
        }
        return finalCategories;
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
            while (validQuestion.size() < numberOfQuestions) {
                Random r = new Random();
                randIndex = r.nextInt(1, listOfQuestions.size() - 1); // origin have to be 1.
                if (!validQuestion.contains(randIndex)) {
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

  /*  public static void main(String[] args) {
        CategoryHandler c = new CategoryHandler();
        c.randomizeCategories();
    }*/
}

