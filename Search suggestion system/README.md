# Java Search Suggestions System (Trie)

This is a simple and efficient command-line implementation of a search suggestions system (autocomplete) in Java. It uses a **Trie (Prefix Tree)** data structure to store a dictionary of words and find all words that match a given prefix.

This project is a clean, single-file implementation suitable for demonstrating the practical application of a Trie.

## Features

* **Trie-Based:** Uses a `TrieNode` class with a `HashMap` for efficient, case-insensitive prefix lookups.
* **Case-Insensitive:** All inserted words and prefixes are handled in lowercase.
* **Interactive:** Includes a `main` method that allows you to load a dictionary and test prefixes in real-time.
* **Loads from File:** Reads its dictionary from an external `dictionary.txt` file.

## How to Compile and Run

This project consists of a single Java file and a dictionary text file.

**1. Compile the Code:**
Open your terminal in the project directory and run:

```bash
javac SearchSuggestions.java
```

**2. Run the Program:**
After compiling, run the program:

```bash
java SearchSuggestions
```

**3. Example Session:**
The program will first load the words from `dictionary.txt`.

```text
Loading dictionary from 'dictionary.txt'...
Successfully loaded 10 words.

Enter prefix to search (or 'q' to quit): pro
Suggestions for 'pro':
 -> profile
 -> program
 -> project
 -> python

Enter prefix to search (or 'q' to quit): app
Suggestions for 'app':
 -> app
 -> apple
 -> apply
 -> application

Enter prefix to search (or 'q' to quit): q
Goodbye!
```

## How It Works

* `TrieNode`: A private inner class holds a `Map<Character, TrieNode>` for its children and a `boolean` to mark the end of a word.
* `insert(String word)`: Iterates through each character of the word, creating new nodes in the Trie as needed.
* `getSuggestions(String prefix)`:
  1.  Traverses the Trie to the node corresponding to the last character of the prefix.
  2.  If the prefix path doesn't exist, it returns an empty list.
  3.  If it does, it calls a recursive helper `findAllWords()` to explore all child paths from that node and collect all complete words.