# SearchEngine
Simple SearchEngine app witch implement *Inverted Indexes* and *TF-IDF* algorithm.
Created for Findwise as interview task.

Jar scan text files in the directory it is in.

## How to use

> You can test app through terminal. 
 * To run app there is need java 17. 
 * In */SearchEngine/SearchEngineTest* run: `java -jar SearchEngine-1.0-SNAPSHOT.jar`
 * Then insert looked for term for ex. `brown`
 * To exit insert `0`

You can add some other text files in this folder.

### Simple documentation

* `TFIDFCalculator` contains TF-IDF algorythm.
* `IndexEntryStorage` contains  logic of Inverted Indexes.
* `SearchEngine` contains implementation main methods of search engine
  * `void indexDocument(String id, String content);` - at the start of run app, Main class use this method to index every document content. 
  After every added document all needed tokens will be recalculated.
  *  `List<IndexEntry> search(String term);` -  return best results for inserted term or empty list if term will be unknown.
* `Main` - initialize SearchEngine and scan text files.

### Task description mistake
There was incorrect documentId order in task description example for *'fox'* term.

### TO-DO
* refactor




