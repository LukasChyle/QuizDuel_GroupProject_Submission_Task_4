package server;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import data.Data;
public class GetHighScore {
    private boolean isValidHighScore;

    protected GetHighScore(Data )

    public List<String[]> getHighScore(List<String[]> highScore, List<Boolean[]> aBooleans){
        if(highScore.size()<10){
            isValidHighScore = true;
        }
        else {
            int lowestScore = 999;
            int getLowestIndex = 0;
            int counter = 0;
            int correctAnswer = 0;
            int wrongAnswer = 0;
            int numberOfQuestions = 0;
            int[] arrayToCompare = new int[highScore.size()];

            for (String[] strings : highScore) {
                arrayToCompare[counter] = Integer.parseInt(strings[2]);
                counter++;
            }

            for (int i = 0; i < arrayToCompare.length; i++) {
                if(lowestScore>=arrayToCompare[i]){
                    lowestScore = arrayToCompare[i];
                    getLowestIndex = i;
                }

            }

            for (Boolean[] aBoolean : aBooleans) {
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

            for (int i = 0; i < arrayToCompare.length; i++) {
                if (correctAnswer >= arrayToCompare[i]) {
                    isValidHighScore = true;
                } else {
                    isValidHighScore = false;
                }
            }
            if(isValidHighScore){

                highScore.remove(getLowestIndex);
                String[] s = {"name","5",Integer.toString(correctAnswer)};
                highScore.add(s);
            }
        }
        return highScore;


        /*HashMap<String, String> capitalCities = new HashMap<String, String>();
        // Add keys and values (Country, City)
        capitalCities.put("England", "London");
        capitalCities.put("Germany", "Berlin");
        capitalCities.put("Norway", "Oslo");
        capitalCities.put("USA", "Washington DC");
        System.out.println(capitalCities);*/

    }

    public static void main(String[] args) {

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

        List<String[]> h1 = new ArrayList<>();
        h1.add(s1);
        h1.add(s2);
        h1.add(s3);
        h1.add(s4);
        h1.add(s5);
        h1.add(s6);
        h1.add(s7);
        h1.add(s8);
        h1.add(s9);
        h1.add(s10);

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
        g.getHighScore(h1,b);

    }
}
