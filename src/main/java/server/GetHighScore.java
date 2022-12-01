package server;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GetHighScore {
    private List<String[]> highScoreList;
    private boolean isSorted = false;

    public List<String[]> getHighScore(List<Boolean[]> playerScore, String playerNickname, int playerAvatar) {

        getHighScoreList(); // fetches a saved list or creates a new one.

        int lowestScore = 999;
        int getLowestIndex = 0;
        int counter = 0;
        int correctAnswer = 0;
        int wrongAnswer = 0;
        int numberOfQuestions = 0;

        for (Boolean[] aBoolean : playerScore) {
            for (Boolean aBoolean1 : aBoolean) {
                if (aBoolean1) {
                    correctAnswer++;
                } else {
                    wrongAnswer++;
                }
                numberOfQuestions = correctAnswer + wrongAnswer;
            }
        }
        double correctAnswerTemp = correctAnswer;
        double numberOfQuestionsTemp = numberOfQuestions;
        int percentageCorrect = (int) Math.round(((correctAnswerTemp / numberOfQuestionsTemp) * 100));

        if (highScoreList.size() < 10) {
            int count = 0;
            String[] s = {Integer.toString(playerAvatar), playerNickname,
                    correctAnswer + "/" + numberOfQuestions, percentageCorrect + "%"};
            if (highScoreList.size() == 0) {
                highScoreList.add(0, s);
            } else {
                int lengthOfList = highScoreList.size();
                for (String[] strings : highScoreList) {
                    if (percentageCorrect >= Integer.parseInt(strings[3].substring(0, strings[3].length() - 1))) {
                        highScoreList.add(count, s);
                        break;
                    }
                    count++;
                }
                if (highScoreList.size() == lengthOfList) {
                    highScoreList.add(count, s);
                }
            }
        } else {

            int[] arrayToCompare = new int[highScoreList.size()];

            for (String[] strings : highScoreList) {
                arrayToCompare[counter] = Integer.parseInt(strings[3].substring(0, strings[3].length() - 1));
                counter++;
            }

            for (int i = 0; i < arrayToCompare.length; i++) {
                if (lowestScore >= arrayToCompare[i]) {
                    lowestScore = arrayToCompare[i];
                    getLowestIndex = i;
                }
            }
            boolean isValidHighScore;
            if (correctAnswer >= lowestScore) {
                isValidHighScore = true;
            } else {
                isValidHighScore = false;
            }
            if (isValidHighScore) {
                highScoreList.remove(getLowestIndex);
                String[] s = {Integer.toString(playerAvatar), playerNickname,
                        correctAnswer + "/" + numberOfQuestions, percentageCorrect + "%"};
                int count = 0;
                while (!isSorted) {
                    for (String[] strings : highScoreList) {
                        if (percentageCorrect >= Integer.parseInt(strings[3].substring(0, strings[3].length() - 1))) {
                            highScoreList.add(count, s);
                            break;
                        }
                        count++;
                    }
                    if (count == 9) {
                        highScoreList.add(count, s);
                    }
                    isSorted = true;
                }
            }
        }
        saveHighScoreList(); // saves the high score to file, replaces existing one.
        return highScoreList;
    }

    private void saveHighScoreList() {
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(
                "src/main/resources/server/HighScore/HighScoreList", false))) {
            objectOutputStream.writeObject(highScoreList);
        } // try with recourses closes stream automatically
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked") //because java can't ensure the file fetched is of type "List<String[]>"
    private void getHighScoreList() {
        List<String[]> deserializedList = null;
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(
                "src/main/resources/server/HighScore/HighScoreList"))) {
            deserializedList = (List<String[]>) objectInputStream.readObject();
        } // try with recourses closes steam automatically
        catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
        if (deserializedList != null) { // if file with list exists, use it or else create new list.
            highScoreList = deserializedList;
        } else {
            highScoreList = new ArrayList<>();
        }
    }
}