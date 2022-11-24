package server;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import data.Data;
public class GetHighScore {
    private boolean isValidHighScore;


    public List<String[]> getHighScore(Data data){
        int counter = 0;
        int playerScore = 0;
        int[] arrayToCompare = new int[data.highScore.size()];
        for (String[] strings : data.highScore) {
            arrayToCompare[counter] = Integer.parseInt(strings[2]);
            counter++;
        }
        for (Boolean[] booleans : data.playerOneScore) {
            for (Boolean aBoolean : booleans) {
                if(aBoolean){
                    playerScore++;
                }
            }
        }
        Arrays.sort(arrayToCompare);
        for (int i = 0; i < arrayToCompare.length; i++) {
            if(playerScore>=arrayToCompare[i]){
                isValidHighScore = true;
                break;
            }
            else{
                isValidHighScore = false;
            }
        }
        return new ArrayList<>();
    }

}
