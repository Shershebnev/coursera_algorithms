import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;


public class Outcast {
    private WordNet wordnet;
    
    public Outcast(WordNet wordnet) {
        if (wordnet == null) throw new NullPointerException();
        this.wordnet = wordnet;
    }
    
    public String outcast(String[] nouns) {
        if (nouns == null) throw new NullPointerException();
        int maxDistance = 0;
        String outcastNoun = "";
        for (String s : nouns) {
            int distance = 0;
            for (String s1 : nouns) {
                distance += this.wordnet.distance(s, s1);
            }
            if (distance > maxDistance) {
                maxDistance = distance;
                outcastNoun = s;
            }
        }
        return outcastNoun;
    }
    
    public static void main(String[] args) {
//        WordNet a = new WordNet("/Users/shershebnev/Documents/Code/Java/WordNet/wordnet/synsets.txt", 
//                "/Users/shershebnev/Documents/Code/Java/WordNet/wordnet/hypernyms.txt");
//        Outcast outcast = new Outcast(a);
//        In in = new In("/Users/shershebnev/Documents/Code/Java/WordNet/wordnet/outcast11.txt");
//        String[] nouns = in.readAllStrings();
//        System.out.println(outcast.outcast(nouns));
//        for (int t = 2; t < args.length; t++) {
//            In in = new In(args[t]);
//            String[] nouns = in.readAllStrings();
//            StdOut.println(args[t] + ": " + outcast.outcast(nouns));
//        }
    }
}
