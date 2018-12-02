package net.sf.extjwnl.utilities;

import net.sf.extjwnl.JWNL;
import net.sf.extjwnl.JWNLException;
import net.sf.extjwnl.data.IndexWord;
import net.sf.extjwnl.data.POS;
import net.sf.extjwnl.data.Pointer;
import net.sf.extjwnl.data.PointerType;
import net.sf.extjwnl.data.PointerUtils;
import net.sf.extjwnl.data.Synset;
import net.sf.extjwnl.data.Word;
import net.sf.extjwnl.data.list.PointerTargetTree;
import net.sf.extjwnl.dictionary.Dictionary;
import org.sinhala.wordnet.wordnetDB.model.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

@SuppressWarnings("Duplicates")
public class Shakshara {
    /*
     * This to add a new synsets and relations to Sinhala WorNet
     */
    private final String propertiesXml;

    private Dictionary dictionary;

    public Shakshara(String propertiesXml) {
        this.propertiesXml = propertiesXml;
    }

    //Add new synset to the text file
    public void addSynsetToText(MongoSinhalaSynset mongoSynset, POS pos) throws FileNotFoundException, JWNLException {
        dictionary = Dictionary.getInstance();
        dictionary.edit();

        Synset newSynset = dictionary.createSynset(pos);
        for (int i = 0; i < mongoSynset.getWords().size(); i++) {
            mongoSynset.getWords().get(0).getLemma();
            newSynset.getWords().add(new Word(dictionary, newSynset, i + 1, mongoSynset.getWords().get(i).getLemma()));
        }
        newSynset.setGloss(mongoSynset.getGloss());
    }

    // add relationships to the text file
    public void addRelations(List<MongoSinhalaSynset> nounSynset, List<MongoSinhalaSynset> verbSynset, List<MongoSinhalaSynset> adjSynset, List<MongoSinhalaSynset> advSynset, HashMap<String, Integer> rootOrder) throws JWNLException {
        dictionary = Dictionary.getInstance();
        dictionary.edit();


        //geting the order of synsets from mongoDB
        HashMap<Long, Integer> nOrder = new HashMap<Long, Integer>();
        int nord = 0;
        for (MongoSinhalaSynset s : nounSynset) {
            nOrder.put(s.getEWNId(), nord);
            nord++;
        }
        HashMap<Long, Integer> vOrder = new HashMap<Long, Integer>();
        int vord = 0;
        for (MongoSinhalaSynset s : verbSynset) {
            vOrder.put(s.getEWNId(), vord);
            vord++;
        }
        HashMap<Long, Integer> adjOrder = new HashMap<Long, Integer>();
        int adjord = 0;
        for (MongoSinhalaSynset s : adjSynset) {
            adjOrder.put(s.getEWNId(), adjord);
            adjord++;
        }
        HashMap<Long, Integer> advOrder = new HashMap<Long, Integer>();
        int advord = 0;
        for (MongoSinhalaSynset s : advSynset) {
            advOrder.put(s.getEWNId(), adjord);
            advord++;
        }

        // get all the current synsets from text files
        Iterator<Synset> nsynsets = dictionary.getSynsetIterator(POS.NOUN);
        List<Synset> nsynsetlist = new ArrayList<Synset>();

        while (nsynsets.hasNext()) {
            nsynsetlist.add(nsynsets.next());

        }

        Collections.sort(nsynsetlist, new Comparator<Synset>() {
            @Override
            public int compare(Synset o1, Synset o2) {
                return Long.compare(o1.getOffset(), o2.getOffset());
            }
        });

        Iterator<Synset> vsynsets = dictionary.getSynsetIterator(POS.VERB);
        List<Synset> vsynsetlist = new ArrayList<Synset>();
        while (vsynsets.hasNext()) {
            vsynsetlist.add(vsynsets.next());

        }

        Collections.sort(vsynsetlist, new Comparator<Synset>() {

            @Override
            public int compare(Synset o1, Synset o2) {
                return Long.compare(o1.getOffset(), o2.getOffset());
            }
        });
        Iterator<Synset> adjsynsets = dictionary.getSynsetIterator(POS.ADJECTIVE);
        List<Synset> adjsynsetlist = new ArrayList<Synset>();
        while (adjsynsets.hasNext()) {
            adjsynsetlist.add(adjsynsets.next());

        }

        Collections.sort(adjsynsetlist, new Comparator<Synset>() {

            @Override
            public int compare(Synset o1, Synset o2) {
                return Long.compare(o1.getOffset(), o2.getOffset());
            }
        });

        Iterator<Synset> advsynsets = dictionary.getSynsetIterator(POS.ADVERB);
        List<Synset> advsynsetlist = new ArrayList<Synset>();
        while (advsynsets.hasNext()) {
            advsynsetlist.add(advsynsets.next());

        }

        Collections.sort(advsynsetlist, new Comparator<Synset>() {

            @Override
            public int compare(Synset o1, Synset o2) {
                return Long.compare(o1.getOffset(), o2.getOffset());
            }
        });

        Iterator<Synset> rsynsets = dictionary.getSynsetIterator(POS.ROOT);
        List<Synset> rsynsetlist = new ArrayList<Synset>();
        while (rsynsets.hasNext()) {
            rsynsetlist.add(rsynsets.next());

        }

        Collections.sort(rsynsetlist, new Comparator<Synset>() {

            @Override
            public int compare(Synset o1, Synset o2) {
                return Long.compare(o1.getOffset(), o2.getOffset());
            }
        });

        Iterator<Synset> gsynsets = dictionary.getSynsetIterator(POS.GENDER);
        List<Synset> gsynsetlist = new ArrayList<Synset>();
        while (gsynsets.hasNext()) {
            gsynsetlist.add(gsynsets.next());

        }

        Collections.sort(gsynsetlist, new Comparator<Synset>() {

            @Override
            public int compare(Synset o1, Synset o2) {
                return Long.compare(o1.getOffset(), o2.getOffset());
            }
        });

        Iterator<Synset> dsynsets = dictionary.getSynsetIterator(POS.DERIVATIONLANG);
        List<Synset> dsynsetlist = new ArrayList<Synset>();
        while (dsynsets.hasNext()) {
            dsynsetlist.add(dsynsets.next());

        }

        Collections.sort(dsynsetlist, new Comparator<Synset>() {

            @Override
            public int compare(Synset o1, Synset o2) {
                return Long.compare(o1.getOffset(), o2.getOffset());
            }
        });

        Iterator<Synset> usynsets = dictionary.getSynsetIterator(POS.USAGE);
        List<Synset> usynsetlist = new ArrayList<Synset>();
        while (usynsets.hasNext()) {
            usynsetlist.add(usynsets.next());

        }

        Collections.sort(usynsetlist, new Comparator<Synset>() {

            @Override
            public int compare(Synset o1, Synset o2) {
                return Long.compare(o1.getOffset(), o2.getOffset());
            }
        });

        Iterator<Synset> osynsets = dictionary.getSynsetIterator(POS.ORIGIN);
        List<Synset> osynsetlist = new ArrayList<Synset>();
        while (osynsets.hasNext()) {
            osynsetlist.add(osynsets.next());

        }

        Collections.sort(osynsetlist, new Comparator<Synset>() {

            @Override
            public int compare(Synset o1, Synset o2) {
                return Long.compare(o1.getOffset(), o2.getOffset());
            }
        });

        //Add relationships to Noun synsets.

        PointerType jwnlpType = null;
        List<Synset> tempSynsetlist = new ArrayList<Synset>();
        List<MongoSinhalaSynset> tempSynsets = new ArrayList<MongoSinhalaSynset>();
        HashMap<Long, Integer> tempOrder = new HashMap<Long, Integer>();
        for (int j = 0; j < nounSynset.size(); j++) {
            MongoSinhalaNoun noun = (MongoSinhalaNoun) nounSynset.get(j);
            List<MongoSinhalaSencePointer> pointers = noun.getSencePointers();

            //Add sence pointers
            if (pointers.size() > 0) {
                for (int i = 0; i < pointers.size(); i++) {
                    MongoSinhalaPointerTyps ptype = pointers.get(i).getPointerType();

                    if (ptype == MongoSinhalaPointerTyps.GENDER) {            // genders should take speciale care

                        try {
                            Long pid = pointers.get(i).getSynsetId();
                            Integer intSynID = (int) (long) pid;
                            Pointer newPointer5 = new Pointer(PointerType.GENDER, nsynsetlist.get(j), gsynsetlist.get(intSynID - 1));
                            nsynsetlist.get(j).getPointers().add(newPointer5);
                        } catch (Exception e) {

                        }
                    } else {                                                    // other relations

                        if (pointers.get(i).getPointedFile().equals("n")) {
                            tempSynsetlist = nsynsetlist;
                            tempOrder = nOrder;
                            tempSynsets = nounSynset;
                        } else if (pointers.get(i).getPointedFile().equals("v")) {
                            tempSynsetlist = vsynsetlist;
                            tempOrder = vOrder;
                            tempSynsets = verbSynset;
                        } else if (pointers.get(i).getPointedFile().equals("adj")) {
                            tempSynsetlist = adjsynsetlist;
                            tempOrder = adjOrder;
                            tempSynsets = adjSynset;
                        } else if (pointers.get(i).getPointedFile().equals("adv")) {
                            tempSynsetlist = advsynsetlist;
                            tempOrder = advOrder;
                            tempSynsets = advSynset;
                        }


                        if (ptype == MongoSinhalaPointerTyps.HYPERNYM) {
                            jwnlpType = PointerType.HYPERNYM;
                        } else if (ptype == MongoSinhalaPointerTyps.HYPONYM) {
                            jwnlpType = PointerType.HYPONYM;
                        } else if (ptype == MongoSinhalaPointerTyps.MEMBER_HOLONYM) {
                            jwnlpType = PointerType.MEMBER_HOLONYM;
                        } else if (ptype == MongoSinhalaPointerTyps.SUBSTANCE_HOLONYM) {
                            jwnlpType = PointerType.SUBSTANCE_HOLONYM;
                        } else if (ptype == MongoSinhalaPointerTyps.PART_HOLONYM) {
                            jwnlpType = PointerType.PART_HOLONYM;
                        } else if (ptype == MongoSinhalaPointerTyps.MEMBER_MERONYM) {
                            jwnlpType = PointerType.MEMBER_MERONYM;
                        } else if (ptype == MongoSinhalaPointerTyps.SUBSTANCE_MERONYM) {
                            jwnlpType = PointerType.SUBSTANCE_MERONYM;
                        } else if (ptype == MongoSinhalaPointerTyps.PART_MERONYM) {
                            jwnlpType = PointerType.PART_MERONYM;
                        } else if (ptype == MongoSinhalaPointerTyps.ATTRIBUTE) {
                            jwnlpType = PointerType.ATTRIBUTE;
                        } else if (ptype == MongoSinhalaPointerTyps.DERIVATION) {
                            jwnlpType = PointerType.DERIVATION;
                        }


                        //Long pid = pointers.get(i).getSynsetId();
                        //int nposision = tempOrder.get(pid);
                        //Integer intSynID = (int) (long) pid;
                        // to avoid null pointer exception due to null key retrieval
                        Long pid = pointers.get(i).getSynsetId();
                        int nposision = 0;
                        if (tempOrder.containsKey(pid)) {
                            nposision = tempOrder.get(pid);
                        } else {
                            continue;
                        }
                        Integer intSynID = (int) (long) pid;

                        Pointer newPointer5 = new Pointer(jwnlpType, nsynsetlist.get(j), tempSynsetlist.get(nposision));
                        nsynsetlist.get(j).getPointers().add(newPointer5);
                        MongoSinhalaPoinertTypeSemetric SymPointerGenarator = new MongoSinhalaPoinertTypeSemetric();
                        MongoSinhalaPointerTyps symPointer = SymPointerGenarator.getSymetric(ptype);
                        List<MongoSinhalaSencePointer> symPoynterList = tempSynsets.get(nposision).getSencePointers();
                        List<MongoSinhalaSencePointer> newSymPointerList = new ArrayList<MongoSinhalaSencePointer>();
                        for (int k = 0; k < symPoynterList.size(); k++) {
                            if (symPoynterList.get(k).getSynsetId().equals(nounSynset.get(j).getEWNId()) && symPoynterList.get(k).getPointerType().equals(symPointer)) {

                            } else {
                                newSymPointerList.add(symPoynterList.get(k));
                            }
                        }

                        if (pointers.get(i).getPointedFile().equals("n")) {
                            nounSynset.get(nposision).SetSencePointers(newSymPointerList);
                        } else if (pointers.get(i).getPointedFile().equals("v")) {
                            verbSynset.get(nposision).SetSencePointers(newSymPointerList);
                        } else if (pointers.get(i).getPointedFile().equals("adj")) {
                            adjSynset.get(nposision).SetSencePointers(newSymPointerList);
                        } else if (pointers.get(i).getPointedFile().equals("adv")) {
                            advSynset.get(nposision).SetSencePointers(newSymPointerList);
                        }

                    }

                }
            }

            List<MongoSinhalaWord> words = noun.getWords();
            for (int i = 0; i < words.size(); i++) {
                List<MongoSinhalaWordPointer> wPointers = words.get(i).getWordPointerList();

                //Add word relations
                for (int k = 0; k < wPointers.size(); k++) {
                    MongoSinhalaPointerTyps ptype = wPointers.get(k).getPointerType();
                    if (ptype == MongoSinhalaPointerTyps.ROOT) {                        // add root

                        try {
                            String rId = wPointers.get(k).getSynsetIDasString();
                            int rposision = rootOrder.get(rId);
                            if (rsynsetlist.get(rposision).getWords().size() > 0) {
                                Pointer newPointer = new Pointer(PointerType.ROOT, nsynsetlist.get(j).getWords().get(i), rsynsetlist.get(rposision).getWords().get(0));
                                nsynsetlist.get(j).getPointers().add(newPointer);
                            }
                        } catch (Exception e) {

                        }
                    } else if (ptype == MongoSinhalaPointerTyps.DERIVATION_TYPE) {        // add derivation type

                        try {
                            Long pid = wPointers.get(k).getSynsetId();
                            Integer intSynID = (int) (long) pid;
                            Pointer newPointer = new Pointer(PointerType.DERIVATION_LANG, nsynsetlist.get(j).getWords().get(i), dsynsetlist.get(intSynID - 1).getWords().get(0));
                            nsynsetlist.get(j).getPointers().add(newPointer);
                        } catch (Exception e) {

                        }
                    } else if (ptype == MongoSinhalaPointerTyps.USAGE) {                // Add usage

                        try {
                            Long pid = wPointers.get(k).getSynsetId();
                            Integer intSynID = (int) (long) pid;
                            Pointer newPointer = new Pointer(PointerType.USAGE_MODE, nsynsetlist.get(j).getWords().get(i), usynsetlist.get(intSynID - 1).getWords().get(0));
                            nsynsetlist.get(j).getPointers().add(newPointer);
                        } catch (Exception e) {

                        }
                    } else if (ptype == MongoSinhalaPointerTyps.ORIGIN) {                // Add Origin

                        try {
                            Long pid = wPointers.get(k).getSynsetId();
                            Integer intSynID = (int) (long) pid;
                            Pointer newPointer = new Pointer(PointerType.ORIGIN, nsynsetlist.get(j).getWords().get(i), osynsetlist.get(intSynID - 1).getWords().get(0));
                            nsynsetlist.get(j).getPointers().add(newPointer);
                        } catch (Exception e) {

                        }
                    }
                }
            }


        }


        // Add relations to verb synsets
        jwnlpType = null;
        tempSynsetlist = new ArrayList<Synset>();
        tempSynsets = new ArrayList<MongoSinhalaSynset>();
        tempOrder = new HashMap<Long, Integer>();
        for (int j = 0; j < verbSynset.size(); j++) {

            MongoSinhalaVerb verb = (MongoSinhalaVerb) verbSynset.get(j);
            List<MongoSinhalaSencePointer> pointers = verb.getSencePointers();

            // Add sence pointers
            if (pointers.size() > 0) {
                for (int i = 0; i < pointers.size(); i++) {
                    MongoSinhalaPointerTyps ptype = pointers.get(i).getPointerType();

                    if (ptype == MongoSinhalaPointerTyps.GENDER) {                // genders should take speciale care
                        try {
                            Long pid = pointers.get(i).getSynsetId();
                            Integer intSynID = (int) (long) pid;
                            Pointer newPointer5 = new Pointer(PointerType.GENDER, vsynsetlist.get(j), gsynsetlist.get(intSynID - 1));
                            vsynsetlist.get(j).getPointers().add(newPointer5);
                        } catch (Exception e) {

                        }
                    } else {                                                        // Other sence relations
                        if (pointers.get(i).getPointedFile().equals("n")) {
                            tempSynsetlist = nsynsetlist;
                            tempOrder = nOrder;
                            tempSynsets = nounSynset;
                        } else if (pointers.get(i).getPointedFile().equals("v")) {
                            tempSynsetlist = vsynsetlist;
                            tempOrder = vOrder;
                            tempSynsets = verbSynset;
                        } else if (pointers.get(i).getPointedFile().equals("adj")) {
                            tempSynsetlist = adjsynsetlist;
                            tempOrder = adjOrder;
                            tempSynsets = adjSynset;
                        } else if (pointers.get(i).getPointedFile().equals("adv")) {
                            tempSynsetlist = advsynsetlist;
                            tempOrder = advOrder;
                            tempSynsets = advSynset;
                        }


                        if (ptype == MongoSinhalaPointerTyps.HYPERNYM) {
                            jwnlpType = PointerType.HYPERNYM;
                        } else if (ptype == MongoSinhalaPointerTyps.HYPONYM) {
                            jwnlpType = PointerType.HYPONYM;
                        } else if (ptype == MongoSinhalaPointerTyps.ENTAILMENT) {
                            jwnlpType = PointerType.ENTAILMENT;
                        } else if (ptype == MongoSinhalaPointerTyps.CAUSE) {
                            jwnlpType = PointerType.CAUSE;
                        } else if (ptype == MongoSinhalaPointerTyps.DERIVATION) {
                            jwnlpType = PointerType.DERIVATION;
                        }


                        Long pid = pointers.get(i).getSynsetId();
                        int vposision = tempOrder.get(pid);
                        Integer intSynID = (int) (long) pid;
                        Pointer newPointer5 = new Pointer(jwnlpType, vsynsetlist.get(j), tempSynsetlist.get(vposision));
                        vsynsetlist.get(j).getPointers().add(newPointer5);
                        MongoSinhalaPoinertTypeSemetric SymPointerGenarator = new MongoSinhalaPoinertTypeSemetric();
                        MongoSinhalaPointerTyps symPointer = SymPointerGenarator.getSymetric(ptype);
                        List<MongoSinhalaSencePointer> symPoynterList = tempSynsets.get(vposision).getSencePointers();
                        List<MongoSinhalaSencePointer> newSymPointerList = new ArrayList<MongoSinhalaSencePointer>();
                        for (int k = 0; k < symPoynterList.size(); k++) {
                            if (symPoynterList.get(k).getSynsetId().equals(verbSynset.get(j).getEWNId()) && symPoynterList.get(k).getPointerType().equals(symPointer)) {

                            } else {
                                newSymPointerList.add(symPoynterList.get(k));
                            }
                        }

                        if (pointers.get(i).getPointedFile().equals("n")) {
                            nounSynset.get(vposision).SetSencePointers(newSymPointerList);
                        } else if (pointers.get(i).getPointedFile().equals("v")) {
                            verbSynset.get(vposision).SetSencePointers(newSymPointerList);
                        } else if (pointers.get(i).getPointedFile().equals("adj")) {
                            adjSynset.get(vposision).SetSencePointers(newSymPointerList);
                        } else if (pointers.get(i).getPointedFile().equals("adv")) {
                            advSynset.get(vposision).SetSencePointers(newSymPointerList);
                        }

                    }

                }
            }

            //Add word relations
            List<MongoSinhalaWord> words = verb.getWords();
            for (int i = 0; i < words.size(); i++) {
                List<MongoSinhalaWordPointer> wPointers = words.get(i).getWordPointerList();
                for (int k = 0; k < wPointers.size(); k++) {
                    MongoSinhalaPointerTyps ptype = wPointers.get(k).getPointerType();
                    if (ptype == MongoSinhalaPointerTyps.ROOT) {                    // Add root
                        try {
                            String rId = wPointers.get(k).getSynsetIDasString();
                            int rposision = rootOrder.get(rId);
                            Pointer newPointer = new Pointer(PointerType.ROOT, vsynsetlist.get(j).getWords().get(i), rsynsetlist.get(rposision).getWords().get(0));
                            vsynsetlist.get(j).getPointers().add(newPointer);
                        } catch (Exception e) {

                        }
                    } else if (ptype == MongoSinhalaPointerTyps.DERIVATION_TYPE) {        // Add derivation type
                        try {
                            Long pid = wPointers.get(k).getSynsetId();
                            Integer intSynID = (int) (long) pid;
                            Pointer newPointer = new Pointer(PointerType.DERIVATION_LANG, vsynsetlist.get(j).getWords().get(i), dsynsetlist.get(intSynID - 1).getWords().get(0));
                            vsynsetlist.get(j).getPointers().add(newPointer);
                        } catch (Exception e) {

                        }
                    } else if (ptype == MongoSinhalaPointerTyps.USAGE) {            // Add usage

                        try {
                            Long pid = wPointers.get(k).getSynsetId();
                            Integer intSynID = (int) (long) pid;
                            Pointer newPointer = new Pointer(PointerType.USAGE_MODE, vsynsetlist.get(j).getWords().get(i), usynsetlist.get(intSynID - 1).getWords().get(0));
                            vsynsetlist.get(j).getPointers().add(newPointer);
                        } catch (Exception e) {

                        }
                    } else if (ptype == MongoSinhalaPointerTyps.ORIGIN) {            // Add origine

                        try {
                            Long pid = wPointers.get(k).getSynsetId();
                            Integer intSynID = (int) (long) pid;
                            Pointer newPointer = new Pointer(PointerType.ORIGIN, vsynsetlist.get(j).getWords().get(i), osynsetlist.get(intSynID - 1).getWords().get(0));
                            vsynsetlist.get(j).getPointers().add(newPointer);
                        } catch (Exception e) {

                        }
                    }
                }
            }


        }

        // Add relations to Adjective synsets
        jwnlpType = null;
        tempSynsetlist = new ArrayList<Synset>();
        tempSynsets = new ArrayList<MongoSinhalaSynset>();
        tempOrder = new HashMap<Long, Integer>();
        for (int j = 0; j < adjSynset.size(); j++) {
            MongoSinhalaAdjective adjective = (MongoSinhalaAdjective) adjSynset.get(j);
            List<MongoSinhalaSencePointer> pointers = adjective.getSencePointers();

            if (pointers.size() > 0) {
                for (int i = 0; i < pointers.size(); i++) {
                    MongoSinhalaPointerTyps ptype = pointers.get(i).getPointerType();

                    if (ptype == MongoSinhalaPointerTyps.GENDER) {                    // genders should take speciale care
                        try {
                            Long pid = pointers.get(i).getSynsetId();
                            Integer intSynID = (int) (long) pid;
                            Pointer newPointer5 = new Pointer(PointerType.GENDER, adjsynsetlist.get(j), gsynsetlist.get(intSynID - 1));
                            adjsynsetlist.get(j).getPointers().add(newPointer5);
                        } catch (Exception e) {

                        }
                    } else {
                        if (pointers.get(i).getPointedFile().equals("n")) {
                            tempSynsetlist = nsynsetlist;
                            tempOrder = nOrder;
                            tempSynsets = nounSynset;
                        } else if (pointers.get(i).getPointedFile().equals("v")) {
                            tempSynsetlist = vsynsetlist;
                            tempOrder = vOrder;
                            tempSynsets = verbSynset;
                        } else if (pointers.get(i).getPointedFile().equals("adj")) {
                            tempSynsetlist = adjsynsetlist;
                            tempOrder = adjOrder;
                            tempSynsets = adjSynset;
                        } else if (pointers.get(i).getPointedFile().equals("adv")) {
                            tempSynsetlist = advsynsetlist;
                            tempOrder = advOrder;
                            tempSynsets = advSynset;
                        }


                        if (ptype == MongoSinhalaPointerTyps.HYPERNYM) {
                            jwnlpType = PointerType.HYPERNYM;
                        } else if (ptype == MongoSinhalaPointerTyps.HYPONYM) {
                            jwnlpType = PointerType.HYPONYM;
                        } else if (ptype == MongoSinhalaPointerTyps.ENTAILMENT) {
                            jwnlpType = PointerType.ENTAILMENT;
                        } else if (ptype == MongoSinhalaPointerTyps.CAUSE) {
                            jwnlpType = PointerType.CAUSE;
                        } else if (ptype == MongoSinhalaPointerTyps.DERIVATION) {
                            jwnlpType = PointerType.DERIVATION;
                        }


                        Long pid = pointers.get(i).getSynsetId();
                        int adjposision = tempOrder.get(pid);
                        Integer intSynID = (int) (long) pid;
                        Pointer newPointer5 = new Pointer(jwnlpType, adjsynsetlist.get(j), tempSynsetlist.get(adjposision));
                        adjsynsetlist.get(j).getPointers().add(newPointer5);
                        MongoSinhalaPoinertTypeSemetric SymPointerGenarator = new MongoSinhalaPoinertTypeSemetric();
                        MongoSinhalaPointerTyps symPointer = SymPointerGenarator.getSymetric(ptype);
                        List<MongoSinhalaSencePointer> symPoynterList = tempSynsets.get(adjposision).getSencePointers();
                        List<MongoSinhalaSencePointer> newSymPointerList = new ArrayList<MongoSinhalaSencePointer>();
                        //System.out.println("befor"+tempSynsets.get(nposision).toString());
                        for (int k = 0; k < symPoynterList.size(); k++) {
                            if (symPoynterList.get(k).getSynsetId().equals(adjSynset.get(j).getEWNId()) && symPoynterList.get(k).getPointerType().equals(symPointer)) {

                            } else {
                                newSymPointerList.add(symPoynterList.get(k));
                            }
                        }

                        if (pointers.get(i).getPointedFile().equals("n")) {
                            nounSynset.get(adjposision).SetSencePointers(newSymPointerList);
                        } else if (pointers.get(i).getPointedFile().equals("v")) {
                            verbSynset.get(adjposision).SetSencePointers(newSymPointerList);
                        } else if (pointers.get(i).getPointedFile().equals("adj")) {
                            adjSynset.get(adjposision).SetSencePointers(newSymPointerList);
                        } else if (pointers.get(i).getPointedFile().equals("adv")) {
                            adjSynset.get(adjposision).SetSencePointers(newSymPointerList);
                        }

                    }

                }
            }

            //Add word relations
            List<MongoSinhalaWord> words = adjective.getWords();
            for (int i = 0; i < words.size(); i++) {
                List<MongoSinhalaWordPointer> wPointers = words.get(i).getWordPointerList();
                for (int k = 0; k < wPointers.size(); k++) {
                    MongoSinhalaPointerTyps ptype = wPointers.get(k).getPointerType();
                    if (ptype == MongoSinhalaPointerTyps.ROOT) {                    // Add root
                        try {
                            String rId = wPointers.get(k).getSynsetIDasString();
                            int rposision = rootOrder.get(rId);
                            Pointer newPointer = new Pointer(PointerType.ROOT, adjsynsetlist.get(j).getWords().get(i), rsynsetlist.get(rposision).getWords().get(0));
                            adjsynsetlist.get(j).getPointers().add(newPointer);
                        } catch (Exception e) {

                        }
                    } else if (ptype == MongoSinhalaPointerTyps.DERIVATION_TYPE) {        // Add derivation type

                        try {
                            Long pid = wPointers.get(k).getSynsetId();
                            Integer intSynID = (int) (long) pid;
                            Pointer newPointer = new Pointer(PointerType.DERIVATION_LANG, adjsynsetlist.get(j).getWords().get(i), dsynsetlist.get(intSynID - 1).getWords().get(0));
                            adjsynsetlist.get(j).getPointers().add(newPointer);
                        } catch (Exception e) {

                        }
                    } else if (ptype == MongoSinhalaPointerTyps.USAGE) {            // Add usage

                        try {
                            Long pid = wPointers.get(k).getSynsetId();
                            Integer intSynID = (int) (long) pid;
                            Pointer newPointer = new Pointer(PointerType.USAGE_MODE, adjsynsetlist.get(j).getWords().get(i), usynsetlist.get(intSynID - 1).getWords().get(0));
                            adjsynsetlist.get(j).getPointers().add(newPointer);
                        } catch (Exception e) {

                        }
                    } else if (ptype == MongoSinhalaPointerTyps.ORIGIN) {            // Add origin

                        try {
                            Long pid = wPointers.get(k).getSynsetId();
                            Integer intSynID = (int) (long) pid;
                            Pointer newPointer = new Pointer(PointerType.ORIGIN, adjsynsetlist.get(j).getWords().get(i), osynsetlist.get(intSynID - 1).getWords().get(0));
                            adjsynsetlist.get(j).getPointers().add(newPointer);
                        } catch (Exception e) {

                        }
                    }
                }
            }


        }

        // Add relations to Adjective synsets
        jwnlpType = null;
        tempSynsetlist = new ArrayList<Synset>();
        tempSynsets = new ArrayList<MongoSinhalaSynset>();
        tempOrder = new HashMap<Long, Integer>();
        for (int j = 0; j < advSynset.size(); j++) {
            MongoSinhalaAdverb adverb = (MongoSinhalaAdverb) advSynset.get(j);
            List<MongoSinhalaSencePointer> pointers = adverb.getSencePointers();

            if (pointers.size() > 0) {
                for (int i = 0; i < pointers.size(); i++) {
                    MongoSinhalaPointerTyps ptype = pointers.get(i).getPointerType();

                    if (ptype == MongoSinhalaPointerTyps.GENDER) {                    // genders should take speciale care
                        try {
                            Long pid = pointers.get(i).getSynsetId();
                            Integer intSynID = (int) (long) pid;
                            Pointer newPointer5 = new Pointer(PointerType.GENDER, advsynsetlist.get(j), gsynsetlist.get(intSynID - 1));
                            advsynsetlist.get(j).getPointers().add(newPointer5);
                        } catch (Exception e) {

                        }
                    } else {
                        if (pointers.get(i).getPointedFile().equals("n")) {
                            tempSynsetlist = nsynsetlist;
                            tempOrder = nOrder;
                            tempSynsets = nounSynset;
                        } else if (pointers.get(i).getPointedFile().equals("v")) {
                            tempSynsetlist = vsynsetlist;
                            tempOrder = vOrder;
                            tempSynsets = verbSynset;
                        } else if (pointers.get(i).getPointedFile().equals("adj")) {
                            tempSynsetlist = adjsynsetlist;
                            tempOrder = adjOrder;
                            tempSynsets = adjSynset;
                        } else if (pointers.get(i).getPointedFile().equals("adv")) {
                            tempSynsetlist = advsynsetlist;
                            tempOrder = advOrder;
                            tempSynsets = advSynset;
                        }


                        if (ptype == MongoSinhalaPointerTyps.HYPERNYM) {
                            jwnlpType = PointerType.HYPERNYM;
                        } else if (ptype == MongoSinhalaPointerTyps.HYPONYM) {
                            jwnlpType = PointerType.HYPONYM;
                        } else if (ptype == MongoSinhalaPointerTyps.ENTAILMENT) {
                            jwnlpType = PointerType.ENTAILMENT;
                        } else if (ptype == MongoSinhalaPointerTyps.CAUSE) {
                            jwnlpType = PointerType.CAUSE;
                        } else if (ptype == MongoSinhalaPointerTyps.DERIVATION) {
                            jwnlpType = PointerType.DERIVATION;
                        }


                        Long pid = pointers.get(i).getSynsetId();
                        int advposision = tempOrder.get(pid);
                        Integer intSynID = (int) (long) pid;
                        Pointer newPointer5 = new Pointer(jwnlpType, advsynsetlist.get(j), tempSynsetlist.get(advposision));
                        advsynsetlist.get(j).getPointers().add(newPointer5);
                        MongoSinhalaPoinertTypeSemetric SymPointerGenarator = new MongoSinhalaPoinertTypeSemetric();
                        MongoSinhalaPointerTyps symPointer = SymPointerGenarator.getSymetric(ptype);
                        List<MongoSinhalaSencePointer> symPoynterList = tempSynsets.get(advposision).getSencePointers();
                        List<MongoSinhalaSencePointer> newSymPointerList = new ArrayList<MongoSinhalaSencePointer>();
                        //System.out.println("befor"+tempSynsets.get(nposision).toString());
                        for (int k = 0; k < symPoynterList.size(); k++) {
                            if (symPoynterList.get(k).getSynsetId().equals(advSynset.get(j).getEWNId()) && symPoynterList.get(k).getPointerType().equals(symPointer)) {

                            } else {
                                newSymPointerList.add(symPoynterList.get(k));
                            }
                        }

                        if (pointers.get(i).getPointedFile().equals("n")) {
                            nounSynset.get(advposision).SetSencePointers(newSymPointerList);
                        } else if (pointers.get(i).getPointedFile().equals("v")) {
                            verbSynset.get(advposision).SetSencePointers(newSymPointerList);
                        } else if (pointers.get(i).getPointedFile().equals("adj")) {
                            adjSynset.get(advposision).SetSencePointers(newSymPointerList);
                        } else if (pointers.get(i).getPointedFile().equals("adv")) {
                            adjSynset.get(advposision).SetSencePointers(newSymPointerList);
                        }

                    }

                }
            }

            //Add word relations
            List<MongoSinhalaWord> words = adverb.getWords();
            for (int i = 0; i < words.size(); i++) {
                List<MongoSinhalaWordPointer> wPointers = words.get(i).getWordPointerList();
                for (int k = 0; k < wPointers.size(); k++) {
                    MongoSinhalaPointerTyps ptype = wPointers.get(k).getPointerType();
                    if (ptype == MongoSinhalaPointerTyps.ROOT) {                    // Add root
                        try {
                            String rId = wPointers.get(k).getSynsetIDasString();
                            int rposision = rootOrder.get(rId);
                            Pointer newPointer = new Pointer(PointerType.ROOT, advsynsetlist.get(j).getWords().get(i), rsynsetlist.get(rposision).getWords().get(0));
                            advsynsetlist.get(j).getPointers().add(newPointer);
                        } catch (Exception e) {

                        }
                    } else if (ptype == MongoSinhalaPointerTyps.DERIVATION_TYPE) {        // Add derivation type

                        try {
                            Long pid = wPointers.get(k).getSynsetId();
                            Integer intSynID = (int) (long) pid;
                            Pointer newPointer = new Pointer(PointerType.DERIVATION_LANG, advsynsetlist.get(j).getWords().get(i), dsynsetlist.get(intSynID - 1).getWords().get(0));
                            advsynsetlist.get(j).getPointers().add(newPointer);
                        } catch (Exception e) {

                        }
                    } else if (ptype == MongoSinhalaPointerTyps.USAGE) {            // Add usage

                        try {
                            Long pid = wPointers.get(k).getSynsetId();
                            Integer intSynID = (int) (long) pid;
                            Pointer newPointer = new Pointer(PointerType.USAGE_MODE, advsynsetlist.get(j).getWords().get(i), usynsetlist.get(intSynID - 1).getWords().get(0));
                            advsynsetlist.get(j).getPointers().add(newPointer);
                        } catch (Exception e) {

                        }
                    } else if (ptype == MongoSinhalaPointerTyps.ORIGIN) {            // Add origin

                        try {
                            Long pid = wPointers.get(k).getSynsetId();
                            Integer intSynID = (int) (long) pid;
                            Pointer newPointer = new Pointer(PointerType.ORIGIN, advsynsetlist.get(j).getWords().get(i),
                                    osynsetlist.get(intSynID - 1).getWords().get(0));
                            advsynsetlist.get(j).getPointers().add(newPointer);
                        } catch (Exception e) {

                        }
                    }
                }
            }


        }


    }


    public void initialize() {
        try {
            JWNL.initialize(new FileInputStream(propertiesXml));
        } catch (FileNotFoundException | JWNLException e) {
            e.printStackTrace();
        }
    }

    public void delete() {
        try {
            dictionary = Dictionary.getInstance();
            dictionary.close();
            dictionary.delete();

            dictionary = Dictionary.getInstance();
        } catch (JWNLException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            dictionary.save();
        } catch (JWNLException e) {
            e.printStackTrace();
        }
    }

    public void printWord() throws FileNotFoundException, JWNLException {
        initialize();
        Dictionary dictionary = Dictionary.getInstance();
        IndexWord indexWord = dictionary.getIndexWord(POS.NOUN, "sinhala");
        System.out.println("inword" + indexWord);
        PointerTargetTree hyponyms = PointerUtils.getHypernymTree(indexWord.getSenses().get(0));
        System.out.println("Hyponyms of \"" + indexWord.getLemma() + "\":");
        hyponyms.print();


    }

    public void addNewWord(int offset, String newWd) throws FileNotFoundException, JWNLException {
        initialize();
        Dictionary dictionary = Dictionary.getInstance();
        dictionary.edit();
        Synset selectedSynset = dictionary.getSynsetAt(POS.NOUN, offset);
        IndexWord newWord3 = new IndexWord(dictionary, newWd, POS.NOUN, selectedSynset);
        dictionary.save();
    }

    public void editWord(int offset, int wordNo, String newWd) throws FileNotFoundException, JWNLException {
        initialize();
        Dictionary dictionary = Dictionary.getInstance();
        dictionary.edit();

        Synset selectedSynset = dictionary.getSynsetAt(POS.NOUN, offset);
        List<Word> nextword = selectedSynset.getWords();
        Word newWord = new Word(dictionary, selectedSynset, wordNo, newWd);
        nextword.set(wordNo, newWord);
        dictionary.save();
    }

    public Dictionary getDictionary() {
        initialize();
        dictionary = Dictionary.getInstance();
        return dictionary;
    }

}