package Week1_Week2;

import java.io.*;
import java.util.*;

class Plagiarism_Detection_System {

    private final int N = 5;
    private final Map<String, Map<String, Integer>> index = new HashMap<>();

    public void analyzeDocument(String docId, String text) {
        List<String> ngrams = extractNGrams(text);
        for (String ng : ngrams) {
            index.putIfAbsent(ng, new HashMap<>());
            index.get(ng).put(docId, index.get(ng).getOrDefault(docId, 0) + 1);
        }
        System.out.println("Extracted " + ngrams.size() + " n-grams");
    }

    public void checkSimilarity(String docId, String text) {
        List<String> ngrams = extractNGrams(text);
        Map<String, Integer> matches = new HashMap<>();

        for (String ng : ngrams) {
            if (index.containsKey(ng)) {
                for (String otherDoc : index.get(ng).keySet()) {
                    if (!otherDoc.equals(docId)) {
                        matches.put(otherDoc, matches.getOrDefault(otherDoc, 0) + 1);
                    }
                }
            }
        }

        for (String otherDoc : matches.keySet()) {
            double similarity = (matches.get(otherDoc) * 100.0) / ngrams.size();
            System.out.printf("Found %d matching n-grams with %s\n", matches.get(otherDoc), otherDoc);
            System.out.printf("Similarity: %.2f%%\n", similarity);
        }
    }

    private List<String> extractNGrams(String text) {
        String[] words = text.toLowerCase().split("\\W+");
        List<String> ngrams = new ArrayList<>();
        for (int i = 0; i <= words.length - N; i++) {
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < N; j++) sb.append(words[i + j]).append(" ");
            ngrams.add(sb.toString().trim());
        }
        return ngrams;
    }

    public static void main(String[] args) throws Exception {
        Plagiarism_Detection_System detector = new Plagiarism_Detection_System();
        Scanner sc = new Scanner(System.in);

        int existingDocs = sc.nextInt();
        sc.nextLine();

        for (int i = 0; i < existingDocs; i++) {
            String id = sc.nextLine();
            String text = sc.nextLine();
            detector.analyzeDocument(id, text);
        }

        String newDocId = sc.nextLine();
        String newText = sc.nextLine();
        detector.checkSimilarity(newDocId, newText);

        sc.close();
    }
}
