package org.sinhala.wordnet.css.model.wordnet;

import net.didion.jwnl.JWNLException;
import net.didion.jwnl.data.POS;
import net.didion.jwnl.data.Synset;
import net.didion.jwnl.data.Word;
import net.didion.jwnl.dictionary.Dictionary;
import org.sinhala.wordnet.css.jwnl.WordNetDictionary;

import java.util.ArrayList;
import java.util.List;

public class AdverbSynset extends SinhalaWordNetSynset {

    public AdverbSynset(String id, long offset, String definition, String example, SinhalaWordNetWord gender,
                               List<SinhalaWordNetWord> words) {
        super(id, offset, definition, example, gender, words);
    }

    public AdverbSynset(Synset synset) {
        super(synset);
    }

    public AdverbSynset() {
        super();
    }

    public List<SinhalaWordNetWord> getWords() {
        if (this.words != null) {

            return this.words;

        } else {
            Dictionary dict = WordNetDictionary.getInstance();
            Synset synset = null;
            try {
                synset = dict.getSynsetAt(POS.ADVERB, this.getOffset());
            } catch (NumberFormatException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (JWNLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            List<SinhalaWordNetWord> words = new ArrayList<SinhalaWordNetWord>();
            Word[] originalWords = synset.getWords();
            for (Word w : originalWords) {
                words.add(new NounWord(w));
            }

            this.words = words;
            return words;
        }
    }

    public String getDefinition() {
        if (this.definition != null) {
            return this.definition;
        } else {
            Dictionary dict = WordNetDictionary.getInstance();
            Synset synset = null;
            try {
                //synset = dict.getSynsetAt(arg0, arg1)
                synset = dict.getSynsetAt(POS.ADVERB, this.getOffset());
            } catch (NumberFormatException e) {

                // TODO Auto-generated catch block
                //e.printStackTrace();
            } catch (JWNLException e) {
                // TODO Auto-generated catch block
                //e.printStackTrace();
            }

            String out = "";

            try {
                out = synset.getGloss().split(";")[0];
            } catch (ArrayIndexOutOfBoundsException e) {
                //e.printStackTrace();
            }

            return out;
        }
    }

    public String getExample() {
        if (this.example != null) {
            return this.example;
        } else {
            Dictionary dict = WordNetDictionary.getInstance();
            Synset synset = null;

            try {
                synset = dict.getSynsetAt(POS.ADVERB, this.getOffset());
            } catch (NumberFormatException e) {
                // TODO Auto-generated catch block
                //e.printStackTrace();
            } catch (JWNLException e) {
                // TODO Auto-generated catch block
                //e.printStackTrace();
            }

            String out = "";

            try {
                out = synset.getGloss().split(";")[1];
            } catch (ArrayIndexOutOfBoundsException e) {
                //e.printStackTrace();
            }

            return out;
        }
    }

}
