package main;

import java.util.ArrayList;
import java.util.List;

public class SockShoe {

    public record Token(String token, int amount){}
    public record TreeNode(int sockCount, int shoeCount){}

    public static void solve(){
        List<String> input = new ArrayList<>();
        List<String> spaceLessInput = new ArrayList<>();
        List<Token> tokenList = new ArrayList<>();

        for (String character : input){
            if (!(character.equals(" "))){
                spaceLessInput.add(character);
            }
        }

        String mostRecentCharacter = " ";
        int currentCount = 0;
        for (String character : spaceLessInput){
            if (character.equals(mostRecentCharacter)){
                currentCount++;

            } else {
                if (currentCount > 0){
                    tokenList.add(new Token(mostRecentCharacter, currentCount));
                }

                mostRecentCharacter = character;
                currentCount = 1;
            }
        }

        List<TreeNode> previousLayer = new ArrayList<>();
        List<TreeNode> currentLayer = new ArrayList<>();

        previousLayer.add(new TreeNode(0, 0));

        for (Token token : tokenList) {
            for (TreeNode treeNode : previousLayer){
                int sockCount = 0;
                int shoeCount = 0;
                int initialSockCount = treeNode.sockCount;
                int initialShoeCount = treeNode.shoeCount;

                if (token.token.equals("\uD83E\uDDE6")){
                    sockCount = token.amount;
                } else {
                    shoeCount = token.amount;
                }

                if (token.amount == 1){
                    if (sockCount > shoeCount){

                    } else {

                    }

                } else if (token.amount == 2){
                    if (sockCount > shoeCount){

                    } else {

                    }

                } else {
                    if (sockCount > shoeCount){

                    } else {

                    }
                }
            }
        }
    }
}
