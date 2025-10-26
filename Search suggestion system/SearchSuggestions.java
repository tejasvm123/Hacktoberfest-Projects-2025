import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class SearchSuggestions {

    private class TrieNode {
        Map<Character, TrieNode> children = new HashMap<>();
        boolean isEndOfWord;
    }

    private final TrieNode root;

    public SearchSuggestions() {
        root = new TrieNode();
    }

    public void insert(String word) {
        if (word == null || word.isEmpty()) {
            return;
        }
        TrieNode node = root;
        for (char c : word.toLowerCase().toCharArray()) {
            node = node.children.computeIfAbsent(c, k -> new TrieNode());
        }
        node.isEndOfWord = true;
    }

    public List<String> getSuggestions(String prefix) {
        if (prefix == null) {
            return Collections.emptyList();
        }
        String lowerPrefix = prefix.toLowerCase();
        TrieNode node = root;

        for (char c : lowerPrefix.toCharArray()) {
            node = node.children.get(c);
            if (node == null) {
                return Collections.emptyList();
            }
        }
        
        List<String> suggestions = new ArrayList<>();
        findAllWords(node, new StringBuilder(lowerPrefix), suggestions);
        return suggestions;
    }

    private void findAllWords(TrieNode node, StringBuilder currentWord, List<String> suggestions) {
        if (node.isEndOfWord) {
            suggestions.add(currentWord.toString());
        }

        for (Map.Entry<Character, TrieNode> entry : node.children.entrySet()) {
            currentWord.append(entry.getKey());
            findAllWords(entry.getValue(), currentWord, suggestions);
            currentWord.setLength(currentWord.length() - 1); // Backtrack
        }
    }

    /**
     * Loads words from a specified dictionary file into the Trie.
     */
    private static int loadDictionary(SearchSuggestions trie, String filename) {
        int wordCount = 0;
        // The dictionary file is inside the same folder, so the path is simple.
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    trie.insert(line.trim());
                    wordCount++;
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Error: Dictionary file not found: " + filename);
        } catch (IOException e) {
            System.err.println("Error: Failed to read dictionary file: " + e.getMessage());
        }
        return wordCount;
    }

    public static void main(String[] args) {
        SearchSuggestions trie = new SearchSuggestions();
        
        // Looks for the file in the same directory as the .java file.
        // When running, you must be "inside" this folder.
        String dictionaryFile = "dictionary.txt";
        System.out.println("Loading dictionary from '" + dictionaryFile + "'...");
        
        int wordCount = loadDictionary(trie, dictionaryFile);

        if (wordCount == 0) {
            System.err.println("Dictionary is empty or could not be loaded. Exiting.");
            return;
        }
        
        System.out.println("Successfully loaded " + wordCount + " words.");

        try (Scanner scanner = new Scanner(System.in)) {

        while (true) {
            System.out.print("\nEnter prefix to search (or 'q' to quit): ");
            String prefix = scanner.nextLine();

            if (prefix.equalsIgnoreCase("q") || prefix.isEmpty()) {
                break;
            }

            List<String> suggestions = trie.getSuggestions(prefix);

            if (suggestions.isEmpty()) {
                System.out.println("No suggestions found for '" + prefix + "'.");
            } else {
                System.out.println("Suggestions for '" + prefix + "':");
                for (String suggestion : suggestions) {
                    System.out.println(" -> " + suggestion);
                }
            }
        }
    }
    System.out.println("Goodbye!");
    }
}