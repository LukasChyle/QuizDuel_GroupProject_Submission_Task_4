package server;


import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GetHighScore implements Serializable {
    private boolean isValidHighScore;
    private List<String[]> highScoreList = new ArrayList<>();

    public List<String[]> getHighScore(List<Boolean[]> playerScore,String playerNickname,int playerAvatar){

        List<String[]> deserializedList = new ArrayList<>();
        try {
            FileInputStream fileInputStream = new FileInputStream("src/main/resources/server/HighScore/HighScoreList");
            ObjectInputStream objectInputStream= new ObjectInputStream(fileInputStream);
            deserializedList = (ArrayList)objectInputStream.readObject();
            objectInputStream.close();

        }
        catch (ClassNotFoundException | IOException ce){
            ce.printStackTrace();
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

            for (int i = 0; i < arrayToCompare.length; i++) {
                if (correctAnswer >= arrayToCompare[i]) {
                    isValidHighScore = true;
                } else {
                    isValidHighScore = false;
                }
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

    public static void main(String[] args) {



        List<Boolean[]> b = new ArrayList<>();
        Boolean[] b1 = {true,true,false};
        Boolean[] b2 = {false,true,false};
        Boolean[] b3 = {false,false,false};
        Boolean[] b4 = {false,true,false};
        b.add(b1);
        b.add(b2);
        b.add(b3);
        b.add(b4);

        GetHighScore g = new GetHighScore();
        g.getHighScore(b,"name",1);

        String[] s1 = {"Marcus","2", "4"};
        String[] s2 = {"Dennis","1", "4"};
        String[] s3 = {"Lukas","6", "4"};
        String[] s4 = {"Amar","7", "4"};
        String[] s5 = {"Marcus","2", "4"};
        String[] s6 = {"Dennis","1", "4"};
        String[] s7 = {"Lukas","6", "4"};
        String[] s8 = {"Amar","7", "4"};
        String[] s9 = {"Marcus","2", "4"};
        String[] s10 = {"Dennis","1", "4"};

        List<String[]> highScore = new ArrayList<>();
        highScore.add(s1);
        highScore.add(s2);
        highScore.add(s3);
        highScore.add(s4);
        highScore.add(s5);
        highScore.add(s6);
        highScore.add(s7);
        highScore.add(s8);

        highScore.add(s9);
        highScore.add(s10);

    }
}
