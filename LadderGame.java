import java.io.File;
import java.util.Objects;
import java.util.Scanner;
import java.util.ArrayList;

public class LadderGame extends Queue {
    ArrayList<ArrayList<String>> organized = new ArrayList<>();
    ArrayList<String> allWords = new ArrayList<>();
    public LadderGame(String dictionaryFile) {

        readDictionary(dictionaryFile);
    }
    public void play(String start, String end) {
        if(start.equals(end)){
            System.out.println(start+ " -> "+ end);
        }
        if(!allWords.contains(end) || !allWords.contains(start)){
            System.out.println(start+" -> "+ end);
        }
        Queue<String> queue = new Queue<>();
        WordInfo startWord = new WordInfo(start,0," ");
        queue.enqueue(startWord.getWord());
        queue.enqueue(end);
        int counter = 0;
        String current = " ";
        loop:while(!queue.isEmpty() && !current.equals(end)){
            current = queue.dequeue();
            System.out.println(current);
            System.out.println(oneAway(current,true).get(0));
            for(String check:oneAway(current,true)){
                System.out.println('x');
                if(check.equals(end)){
                    System.out.println(start+ " -> "+ end + " : "+ startWord.getMoves()+ " Moves [ "+
                           startWord.getHistory() + " ] total enqueues " + counter);
                    break loop;
                }
                int moves = startWord.getMoves()+1;
                String history = startWord.getHistory() + " "+ startWord.getWord();
                startWord = new WordInfo(start,moves,history);
                System.out.println(startWord.getWord());
                queue.enqueue(startWord.getWord());
                counter++;
            }
        }
        if(current.equals(end)){
            System.out.println(start+ " -> "+ end + " : "+ startWord.getMoves()+ " Moves [ "+
                    startWord.getHistory() + " ] total enqueues " + counter);
        }else {
            System.out.println(start + " -> " + end + " : No ladder was found");
        }
//        Queue<String> partSolution = new Queue<>();
//        partSolution.enqueque(end);
//        organized.get(start.length()-2).remove(start);
//        String newWord = "";
//        while(!partSolution.isEmpty() && !newWord.equals(start) && !newWord.equals(end)){
//            char[] testWord = start.toCharArray();
//            //ArrayList<String> oneAway = new ArrayList<>();
//            outer: for(int i =0; i< start.length();i++){
//                char original = testWord[i];
//                for(char c = 'a'; c<='z'; c++){
//                    testWord[i] = c;
//                    newWord = String.valueOf(testWord);
//                    if(!allWords.contains(String.valueOf(testWord))){
//                        continue;
//                    }
//                    //System.out.println(testWord);
//                    allWords.remove(String.valueOf(testWord));
//                    if(newWord.equals(end)){
//                        partSolution.enqueque(newWord);
//                        break outer;
//                    }
//                }
//                testWord[i]=original;
//            }
//        }

    }

    public ArrayList<String> oneAway(String word, boolean withRemoval) {
        allWords.remove(word);
        char[] testWord = word.toCharArray();
        ArrayList<String> oneAway = new ArrayList<>();
        for(int i =0; i< word.length();i++){
            char original = testWord[i];
            for(char c = 'a'; c<='z'; c++){
                testWord[i] = c;
                if(!allWords.contains(String.valueOf(testWord))){
                    continue;
                }else{
                    oneAway.add(String.valueOf((testWord)));
                }
                //System.out.println(testWord);
                if(withRemoval) {
                    allWords.remove(String.valueOf(testWord));
                }
            }
            testWord[i]=original;
        }
        return oneAway;
    }

    public void listWords(int length, int howMany) {
        for(int i =0; i<howMany;i++){
            System.out.println(organized.get(length-2).get(i));
        }
    }

    /*
        Reads a list of words from a file, putting all words of the same length into the same array.
     */
    private void readDictionary(String dictionaryFile) {
        File file = new File(dictionaryFile);
//        ArrayList<String> allWords = new ArrayList<>();

        //
        // Track the longest word, because that tells us how big to make the array.
        int longestWord = 0;
        try (Scanner input = new Scanner(file)) {
            //
            // Start by reading all the words into memory.
            while (input.hasNextLine()) {
                String word = input.nextLine().toLowerCase();
                allWords.add(word);
                longestWord = Math.max(longestWord, word.length());
            }
            for(int i = 0; i< longestWord; i++){
                ArrayList<String> row = new ArrayList<>();
                for(String item: allWords){
                    if(item.length()-2==i){
                        row.add(item);
                    }
                }
                organized.add(row);
            }

        }
        catch (java.io.IOException ex) {
            System.out.println("An error occurred trying to read the dictionary: " + ex);
        }
    }
}