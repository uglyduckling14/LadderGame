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
        if(!allWords.contains(end) || !allWords.contains(start)){
            System.out.println(start+" -> "+ end);
        }
        Queue<String> queue = new Queue<>();
        WordInfo startWord = new WordInfo(start,1,start);
        queue.enqueue(start);
        queue.enqueue(end);
        int counter = 1;
        int moves = 1;
        String current = " ";
        String history = start+ ", ";
        loop:while(!queue.isEmpty() && !current.equals(end)){
            //System.out.println("'x");
            current = queue.dequeue();
            history = history + current;
            ArrayList<String> one = oneAway(current,true);
            //System.out.println(one);
            if(one.size()<1){
                break loop;
            }
            for(String check:one){
                //System.out.println('x');
                if(check.equals(end)){
                    //System.out.print('x');
                    counter++;
                    System.out.println(start+ " -> "+ end + " : "+ startWord.getMoves()+ " Moves ["+
                           startWord.getHistory() +", "+ end+ "] total enqueues " + counter);
                    return;
                }
                //System.out.print(check+" ");
                WordInfo newWord = new WordInfo(check,moves,history);
                history = history + check + ", ";
                //System.out.println(check);
                queue.enqueue(newWord.getWord());
                counter++;
                //check1 =;
            }
            //System.out.println(current);
            //history = history + check1 + ", ";
            moves = startWord.getMoves()+1;
        }
        if(current.equals(end)){
            counter++;
            //System.out.println('x');
            System.out.println(start+ " -> "+ current + " : "+ moves + " Moves ["+
                    history+ end + "] total enqueues " + counter);
        }else {
            //System.out.println(history);
            System.out.println(start + " -> " + end + " : No ladder was found");
        }

    }

    public ArrayList<String> oneAway(String word, boolean withRemoval) {
        allWords.remove(word);
        char[] testWord = word.toCharArray();
        ArrayList<String> oneAway = new ArrayList<>();
        for(int i =0; i< word.length();i++){
            char original = testWord[i];
            for(char c = 'a'; c<='z'; c++) {
                testWord[i] = c;
                if (!String.valueOf(testWord).equals(word)) {
                    if (!allWords.contains(String.valueOf(testWord))) {
                        continue;
                    } else {
                        oneAway.add(String.valueOf((testWord)));
                    }
                    //System.out.println(testWord);
                    if (withRemoval) {
                        allWords.remove(String.valueOf(testWord));
                    }
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