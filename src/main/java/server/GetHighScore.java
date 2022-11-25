package server;


import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GetHighScore implements Serializable {
    private boolean isValidHighScore;
    private List<String[]> highScoreList = new ArrayList<>();

    public List<String[]> getHighScore(List<Boolean[]> playerScore,String playerNickname,int playerAvatar){

        List<String[]> deserializedList = new ArrayList<>();
        Path path = Paths.get("src/main/resources/server/HighScore/HighScoreList");

        if (Files.exists(path)) {
            try {
                FileInputStream fileInputStream = new FileInputStream("src/main/resources/server/HighScore/HighScoreList");
                ObjectInputStream objectInputStream= new ObjectInputStream(fileInputStream);
                deserializedList = (ArrayList)objectInputStream.readObject();
                objectInputStream.close();

            }
            catch (ClassNotFoundException | IOException ce){
                ce.printStackTrace();
            }
        }
        else if (Files.notExists(path)) {
            try {
                FileOutputStream fileOutputStream = new FileOutputStream("src/main/resources/server/HighScore/HighScoreList");
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
                objectOutputStream.writeObject(highScoreList);
                objectOutputStream.close();
            }
            catch(IOException e){
                e.printStackTrace();
            }
        }

        highScoreList = deserializedList;
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
        try {
            FileOutputStream fileOutputStream = new FileOutputStream("src/main/resources/server/HighScore/HighScoreList");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(highScoreList);
            objectOutputStream.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }
        return highScoreList;

    }
}
