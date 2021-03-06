package net.sf.extjwnl.utilities;

import net.sf.extjwnl.JWNLException;
import net.sf.extjwnl.data.IndexWord;
import net.sf.extjwnl.data.POS;
import net.sf.extjwnl.data.PointerType;
import net.sf.extjwnl.data.PointerUtils;
import net.sf.extjwnl.data.list.PointerTargetNodeList;
import net.sf.extjwnl.data.list.PointerTargetTree;
import net.sf.extjwnl.data.relationship.AsymmetricRelationship;
import net.sf.extjwnl.data.relationship.Relationship;
import net.sf.extjwnl.data.relationship.RelationshipFinder;
import net.sf.extjwnl.data.relationship.RelationshipList;
import net.sf.extjwnl.dictionary.Dictionary;

import java.io.FileInputStream;

/**
 * A class to demonstrate the functionality of the JWNL package.
 *
 * @author John Didion <jdidion@didion.net>
 * @author <a rel="author" href="http://autayeu.com/">Aliaksandr Autayeu</a>
 */
public class Examples {
    private static final String USAGE = "Usage: Examples <properties file>";

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println(USAGE);
            System.exit(-1);
        }

        String propsFile = args[0];
        try {
            //old-style, static singletone instance
            //JWNL.initialize(new FileInputStream(propsFile));
            //Dictionary dictionary = Dictionary.getInstance();

            //new style, instance dictionary
            Dictionary dictionary = Dictionary.getInstance(new FileInputStream(propsFile));
            new Examples(dictionary).go();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    /**
     * @uml.property name="aCCOMPLISH"
     * @uml.associationEnd multiplicity="(1 1)"
     */
    private IndexWord ACCOMPLISH;
    /**
     * @uml.property name="dOG"
     * @uml.associationEnd multiplicity="(1 1)"
     */
    private IndexWord DOG;
    /**
     * @uml.property name="cAT"
     * @uml.associationEnd multiplicity="(1 1)"
     */
    private IndexWord CAT;
    /**
     * @uml.property name="fUNNY"
     * @uml.associationEnd multiplicity="(1 1)"
     */
    private IndexWord FUNNY;
    /**
     * @uml.property name="dROLL"
     * @uml.associationEnd multiplicity="(1 1)"
     */
    private IndexWord DROLL;
    /**
     * @uml.property name="mORPH_PHRASE"
     */
    private final String MORPH_PHRASE = "running-away";
    /**
     * @uml.property name="dictionary"
     * @uml.associationEnd multiplicity="(1 1)"
     */
    private final Dictionary dictionary;

    public Examples(Dictionary dictionary) throws JWNLException {
        this.dictionary = dictionary;
        ACCOMPLISH = dictionary.getIndexWord(POS.VERB, "accomplish");
        DOG = dictionary.getIndexWord(POS.NOUN, "dog");
        CAT = dictionary.lookupIndexWord(POS.NOUN, "cat");
        FUNNY = dictionary.lookupIndexWord(POS.ADJECTIVE, "funny");
        DROLL = dictionary.lookupIndexWord(POS.ADJECTIVE, "droll");
    }

    public void go() throws JWNLException, CloneNotSupportedException {
        demonstrateMorphologicalAnalysis(MORPH_PHRASE);
        demonstrateListOperation(ACCOMPLISH);
        demonstrateTreeOperation(DOG);
        demonstrateAsymmetricRelationshipOperation(DOG, CAT);
        demonstrateSymmetricRelationshipOperation(FUNNY, DROLL);
    }

    private void demonstrateMorphologicalAnalysis(String phrase) throws JWNLException {
        // "running-away" is kind of a hard case because it involves
        // two words that are joined by a hyphen, and one of the words
        // is not stemmed. So we have to both remove the hyphen and stem
        // "running" before we get to an entry that is in WordNet
        System.out.println("Base form for \"" + phrase + "\": " +
                dictionary.lookupIndexWord(POS.VERB, phrase));
    }

    private void demonstrateListOperation(IndexWord word) throws JWNLException {
        // Get all of the hypernyms (parents) of the first sense of <var>word</var>
        PointerTargetNodeList hypernyms = PointerUtils.getDirectHypernyms(word.getSenses().get(0));
        System.out.println("Direct hypernyms of \"" + word.getLemma() + "\":");
        hypernyms.print();
    }

    private void demonstrateTreeOperation(IndexWord word) throws JWNLException {
        // Get all the hyponyms (children) of the first sense of <var>word</var>
        PointerTargetTree hyponyms = PointerUtils.getHyponymTree(word.getSenses().get(0));
        System.out.println("Hyponyms of \"" + word.getLemma() + "\":");
        hyponyms.print();
    }

    private void demonstrateAsymmetricRelationshipOperation(IndexWord start, IndexWord end) throws JWNLException, CloneNotSupportedException {
        // Try to find a relationship between the first sense of <var>start</var> and the first sense of <var>end</var>
        RelationshipList list = RelationshipFinder.findRelationships(start.getSenses().get(0), end.getSenses().get(0), PointerType.HYPERNYM);
        System.out.println("Hypernym relationship between \"" + start.getLemma() + "\" and \"" + end.getLemma() + "\":");
        for (Object aList : list) {
            ((Relationship) aList).getNodeList().print();
        }
        System.out.println("Common Parent Index: " + ((AsymmetricRelationship) list.get(0)).getCommonParentIndex());
        System.out.println("Depth: " + list.get(0).getDepth());
    }

    private void demonstrateSymmetricRelationshipOperation(IndexWord start, IndexWord end) throws JWNLException, CloneNotSupportedException {
        // find all synonyms that <var>start</var> and <var>end</var> have in common
        RelationshipList list = RelationshipFinder.findRelationships(start.getSenses().get(0), end.getSenses().get(0), PointerType.SIMILAR_TO);
        System.out.println("Synonym relationship between \"" + start.getLemma() + "\" and \"" + end.getLemma() + "\":");
        for (Object aList : list) {
            ((Relationship) aList).getNodeList().print();
        }
        System.out.println("Depth: " + list.get(0).getDepth());
    }
}