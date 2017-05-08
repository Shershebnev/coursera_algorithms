import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.DirectedCycle;
import java.util.Hashtable;
import java.util.ArrayList;


public class WordNet {
    /**
     * Creates a WordNet digraph and provides API for getting distance between two nouns and shortest
     * common ancestor
     */
    
    private Hashtable<Integer, ArrayList<String>> synsetTable = new Hashtable<Integer, ArrayList<String>>();
    private Hashtable<String, String> nouns = new Hashtable<String, String>();
    private Digraph digraph;
    private SAP sap;
    
    /**
     * Constructor, takes as input filenames for synsets file and hypernyms file
     * @throws NullPointerException if any of files is null
     * @throws IllegalArgumentException if digraph has at least one cycle
     */
    public WordNet(String synsets, String hypernyms) {
        if (synsets == null || hypernyms == null) throw new NullPointerException();
        In S = new In(synsets);
        while (S.hasNextLine()) {
            String synset = S.readLine();
            String[] parsed = synset.split(",");
            ArrayList<String> list = new ArrayList<String>();
            for (String s : parsed[1].split(" ")) {
                list.add(s);
            }
            synsetTable.put(Integer.valueOf(parsed[0]), list);
        }
        In H = new In(hypernyms);
        digraph = new Digraph(synsetTable.size());
        while (H.hasNextLine()) {
            String hypernym = H.readLine();
            String[] parsed = hypernym.split(",");
            for (int i = 1; i < parsed.length; i++) {
                digraph.addEdge(Integer.valueOf(parsed[0]), Integer.valueOf(parsed[i]));
            }
        }
        DirectedCycle dc = new DirectedCycle(digraph);
        if (dc.hasCycle()) {
            throw new IllegalArgumentException();
        }
        boolean rooted = false;
        for (int v = 0; v < digraph.V(); v++) {
            int outdegree = digraph.outdegree(v);
            if (outdegree == 0 && !rooted) {
                rooted = true;
            } else if (outdegree == 0 && rooted) {
                throw new IllegalArgumentException();
            }
        }
        sap = new SAP(digraph);
        // generate a hashtable with noun as a key and all ids as a value (in the form
        // of string "id, id, id, id..."
        reverseHashtable(); 
    }
    
//    public int getV() {
//        return digraph.V();
//    }
//    
//    public int getE() {
//        return digraph.E();
//    }
//    
//    public int getLength() {
//        return nouns.size();
//    }
    
    /**
     * Reverse hashtable synsetTable
     * Values format is string "id, id, id..."
     */
    private void reverseHashtable() {
        for (Integer i : synsetTable.keySet()) {
            for (String s : synsetTable.get(i)) {
                if (nouns.containsKey(s)) {
                    nouns.put(s, nouns.get(s) + ", " + i);
                } else {
                    nouns.put(s, "" + i);
                }
            }
        }
    }
    
    /**
     * Convert value from string to an ArrayList containing all ids of given noun
     */
    private Iterable<Integer> getIDs(String noun) {
        String ids = nouns.get(noun);
        ArrayList<Integer> list = new ArrayList<Integer>();
        String[] a = ids.split(", ");
        for (String s : a) {
            list.add(Integer.valueOf(s));
        }
        return list;
    }
    
    /**
     * Return all nouns from the wordnet
     */
    public Iterable<String> nouns() {
        return nouns.keySet();
    }
    
    /**
     * Check if word is in synsetTable
     * @throws NullPointerException if word is null
     */
    public boolean isNoun(String word) {
        if (word == null) throw new NullPointerException();
        return nouns.containsKey(word);
    }
    
    /**
     * Calculate distance between two nouns, uses SAP
     * @throws NullPointerException if any of nouns is null
     * @throws IllegalArgumentException if any of words is not a noun
     */
    public int distance(String nounA, String nounB) {
        if (nounA == null || nounB == null) throw new NullPointerException();
        if (!isNoun(nounA) || !isNoun(nounB)) throw new IllegalArgumentException();
        if (nounA.equals(nounB)) {
            return 0;
        }
        return sap.length(getIDs(nounA), getIDs(nounB));
    }
    
    /**
     * @return closest common ancestor of both nouns
     * @throws NullPointerException if any of nouns is null
     * @throws IllegalArgumentException if and of words is not a noun
     */
    public String sap(String nounA, String nounB) {
        if (nounA == null || nounB == null) throw new NullPointerException();
        if (!isNoun(nounA) || !isNoun(nounB)) throw new IllegalArgumentException();
        int ancestor = sap.ancestor(getIDs(nounA), getIDs(nounB));
        ArrayList<String> a = synsetTable.get(ancestor);
        StringBuilder sb = new StringBuilder();
        for (String s : a) {
            sb.append(s);
            sb.append(" ");
        }
        return sb.toString().trim();
    }
    
    public static void main(String[] args) {
//        WordNet a = new WordNet("/Users/shershebnev/Documents/Code/Java/WordNet/wordnet/synsets15.txt", 
//                "/Users/shershebnev/Documents/Code/Java/WordNet/wordnet/hypernyms15Path.txt");
//        System.out.println(a.isNoun("a"));
//        System.out.println(a.getV());
//        System.out.println(a.getE());
//        System.out.println(a.sap("worm", "bird"));
    }
}
