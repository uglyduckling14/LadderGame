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
            System.out.println(start + "->"+ end + ": No ladder was found");
            return;
        }
        Queue<String> queue = new Queue<>();
        queue.enqueue(start);
        //ArrayList<String> words = new ArrayList<>();
        String history="";
        int counter = 0;
        int enqueue = 1;
        WordInfo wordinfo = new WordInfo(start,counter,history);
        while(!queue.isEmpty()){
            counter++;
            enqueue++;
            int size = queue.getSize();
            //System.out.println(size);
            for(int i = 0; i<size; i++) {
                String testWord = queue.dequeue();
                //history+=testWord;
                ArrayList<String> oneDif = oneAway(testWord, true);
                //System.out.println(oneDif.size());
                for (String one : oneDif) {
                    enqueue++;
                    if (end.equals(one)) {
                        history += one;
                        System.out.println(start + "->" + end + " : " + counter + " Moves [" + history + "] total enqueues " + enqueue);
                        return;
                    }
                    //System.out.println('d');
                    queue.enqueue(one);
                }
            }

        }
        System.out.println(start + "->"+ end + ": No ladder was found");
    }
    public ArrayList<String> oneAway(String word, boolean withRemoval) {
        //allWords.remove(word);
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