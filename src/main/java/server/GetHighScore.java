package server;


import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class GetHighScore {
    private List<String[]> highScoreList;
    private boolean isSorted = false;

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
            int count = 0;
            String[] s = {Integer.toString(playerAvatar),playerNickname,Integer.toString(correctAnswer)};
            if(highScoreList.size()==0) {
                highScoreList.add(0, s);
            }
            else{
                for (String[] strings : highScoreList) {

                    if(correctAnswer>=Integer.parseInt(strings[2])){
                        highScoreList.add(count, s);
                    }
                    count++;
                }
            }
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
                String[] s = {Integer.toString(playerAvatar),playerNickname,Integer.toString(correctAnswer)};
                int count= 0;
                while(!isSorted){
                    for (String[] strings : highScoreList) {
                            if(correctAnswer>=Integer.parseInt(strings[2])){
                                highScoreList.add(count, s);
                                break;
                            }
                            count++;
                    }isSorted = true;
                }
               /* Collections.sort(highScoreList, new Comparator<String[]>() {;
                    @Override
                    public int compare(String[] o1, String[] o2) {
                        int o1Compare = Integer.parseInt(o1[2]);
                        int o2Compare = Integer.parseInt(o2[2]);
                        if(o1Compare>o2Compare){
                            return 1;
                        }
                        else{
                            return 0;
                        }
                    }
                });*/
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

    public static void main(String[] args) {

        Boolean[] b = {true,true,true};
        Boolean[] b1 = {true,true,true};
        Boolean[] b2 = {true,false,false};

        List<Boolean[]> bool = new ArrayList<>();
        bool.add(b);
        bool.add(b1);
        bool.add(b2);

        GetHighScore g = new GetHighScore();
        g.getHighScore(bool, "marcus",1);

    }
}
