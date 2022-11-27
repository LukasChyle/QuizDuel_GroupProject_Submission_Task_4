package server;


import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GetHighScore {
    private List<String[]> highScoreList;

    public List<String[]> getHighScore(List<Boolean[]> playerScore,String playerNickname,int playerAvatar){

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
                }
                else{
                    wrongAnswer++;
                }
                numberOfQuestions = correctAnswer + wrongAnswer;
            }
        }

        if(highScoreList.size()<10){
            String[] s = {playerNickname,Integer.toString(playerAvatar),Integer.toString(correctAnswer)};
            highScoreList.add(s);
        }
        else {

            int[] arrayToCompare = new int[highScoreList.size()];

            for (String[] strings : highScoreList) {
                arrayToCompare[counter] = Integer.parseInt(strings[2]);
                counter++;
            }

            for (int i = 0; i < arrayToCompare.length; i++) {
                if(lowestScore>=arrayToCompare[i]){
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
            if(isValidHighScore){

                highScoreList.remove(getLowestIndex);
                String[] s = {playerNickname,Integer.toString(playerAvatar),Integer.toString(correctAnswer)};
                highScoreList.add(s);
            }
        }

        saveHighScoreList(); // saves the high score to file, replaces existing one.
        return highScoreList;
    }

    private void saveHighScoreList(){
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(
                "src/main/resources/server/HighScore/HighScoreList", false))) {
            objectOutputStream.writeObject(highScoreList);
        } // try with recourses closes stream automatically
        catch(IOException e){
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked") //because java can't ensure the file fetched is of type "List<String[]>"
    private void getHighScoreList() {
        List<String[]> deserializedList = null;
        try (ObjectInputStream objectInputStream= new ObjectInputStream(new FileInputStream(
                "src/main/resources/server/HighScore/HighScoreList"))) {
            deserializedList = (List<String[]>) objectInputStream.readObject();
        } // try with recourses closes steam automatically
        catch (ClassNotFoundException | IOException e){
            e.printStackTrace();
        }
        if (deserializedList != null) { // if file with list exists, use it or else create new list.
            highScoreList = deserializedList;
        } else {
            highScoreList = new ArrayList<>();
        }
    }
}
