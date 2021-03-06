package org.sinhala.wordnet.css.model.wordnet;

import net.didion.jwnl.JWNLException;
import net.didion.jwnl.data.POS;
import net.didion.jwnl.data.Synset;
import net.didion.jwnl.data.Word;
import net.didion.jwnl.dictionary.Dictionary;
import org.sinhala.wordnet.css.jwnl.WordNetDictionary;

import java.util.ArrayList;
import java.util.List;

public class NounSynset extends SinhalaWordNetSynset {

    List<SinhalaWordNetSynset> hypernyms;
    List<SinhalaWordNetSynset> hyponyms;
    List<SinhalaWordNetSynset> meronyms;
    List<SinhalaWordNetSynset> holonyms;
    List<SinhalaWordNetSynset> attributes;

    public NounSynset(String id, long offset, String definition, String example, SinhalaWordNetWord gender,
                             List<SinhalaWordNetWord> words, List<SinhalaWordNetSynset> hypernyms,
                             List<SinhalaWordNetSynset> hyponyms, List<SinhalaWordNetSynset> meronyms,
                             List<SinhalaWordNetSynset> holonyms, List<SinhalaWordNetSynset> attributes) {
        super(id, offset, definition, example, gender, words);
        this.hypernyms = hypernyms;
        this.hyponyms = hyponyms;
        this.meronyms = meronyms;
        this.holonyms = holonyms;
        this.attributes = attributes;
    }

    public NounSynset(String id, long offset, String definition, String example, List<SinhalaWordNetWord> words,
                             SinhalaWordNetWord gender) {
        super(id, offset, definition, example, words, gender);
    }

    public NounSynset(Synset synset) {
        super(synset);
        this.offset = synset.getOffset();
    }

    /*public NounSynset(SinhalaWordNetSynset synset){
        super(synset);
        Dictionary dict = WordNetDictionary.getInstance();
        Synset synset1 = null;
        try {
            synset1 = dict.getSynsetAt(POS.NOUN, this.getOffset());
        } catch (NumberFormatException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JWNLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        List<SinhalaWordNetSynset> hyp = new ArrayList<SinhalaWordNetSynset>();
        Pointer[] p = synset1.getPointers(PointerType.HYPERNYM);
        try {
            for(int i=0;i<p.length;i++){
            PointerTarget target = p[i].getTarget();
            Synset synset2 = (Synset)target;
            SinhalaWordNetSynset hypsyn = new SinhalaWordNetSynset(synset2);
            hyp.add(hypsyn);
            }
            this.hypernyms = hyp;
        } catch (JWNLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        List<SinhalaWordNetSynset> hypo = new ArrayList<SinhalaWordNetSynset>();
        Pointer[] p1 = synset1.getPointers(PointerType.HYPONYM);
        try {
            for(int i=0;i<p.length;i++){
                System.out.println("pointr size"+p1.length);
            PointerTarget target = p1[i].getTarget();
            Synset synset2 = (Synset)target;
            SinhalaWordNetSynset hyposyn = new SinhalaWordNetSynset(synset2);
            hypo.add(hyposyn);
            }
            this.hyponyms = hypo;
        } catch (JWNLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }




    }
    */
    public NounSynset() {
        super();

    }

    public List<SinhalaWordNetSynset> getHypernyms() {
        return hypernyms;
    }

    public void setHypernyms(List<SinhalaWordNetSynset> hypernyms) {
        this.hypernyms = hypernyms;
    }

    public List<SinhalaWordNetSynset> getHyponyms() {
        return hyponyms;
    }

    public void setHyponyms(List<SinhalaWordNetSynset> hyponyms) {
        this.hyponyms = hyponyms;
    }

    public List<SinhalaWordNetSynset> getMeronyms() {
        return meronyms;
    }

    public void setMeronyms(List<SinhalaWordNetSynset> meronyms) {
        this.meronyms = meronyms;
    }

    public List<SinhalaWordNetSynset> getHolonyms() {
        return holonyms;
    }

    public void setHolonyms(List<SinhalaWordNetSynset> holonyms) {
        this.holonyms = holonyms;
    }

    public List<SinhalaWordNetSynset> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<SinhalaWordNetSynset> attributes) {
        this.attributes = attributes;
    }

    public List<SinhalaWordNetWord> getWords() {
        if (this.words != null) {

            return this.words;

        } else {
            Dictionary dict = WordNetDictionary.getInstance();
            Synset synset = null;
            try {
                synset = dict.getSynsetAt(POS.NOUN, this.getOffset());
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

    public void setWords(List<SinhalaWordNetWord> words) {

        this.words = words;
    }

    public List<SinhalaWordNetWord> getMyWords() {
        return super.getWords();
    }

    public String getDefinition() {
        if (this.definition != null) {
            return this.definition;
        } else {
            Dictionary dict = WordNetDictionary.getInstance();
            Synset synset = null;
            try {
                //synset = dict.getSynsetAt(arg0, arg1)
                synset = dict.getSynsetAt(POS.NOUN, this.getOffset());
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
                synset = dict.getSynsetAt(POS.NOUN, this.getOffset());
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
