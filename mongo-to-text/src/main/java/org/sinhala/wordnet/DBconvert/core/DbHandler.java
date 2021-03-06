package org.sinhala.wordnet.DBconvert.core;

import net.sf.extjwnl.JWNLException;
import net.sf.extjwnl.data.POS;
import net.sf.extjwnl.utilities.Shakshara;
import org.sinhala.wordnet.wordnetDB.config.SpringMongoConfig;
import org.sinhala.wordnet.wordnetDB.model.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DbHandler {

    private Shakshara sh;

    public DbHandler(Shakshara sh) {
        this.sh = sh;
    }

    public DbHandler() {

    }

    // Add synset to text
    public void addSynsetToText(POS pos) {
        DbHandler dbHandler = new DbHandler();
        List<MongoSinhalaSynset> hm = dbHandler.findAllLatestSynsets(pos);

        for (int i = 0; i < hm.size(); i++) {
            MongoSinhalaSynset s = hm.get(i);
            try {
                sh.addSynsetToText(s, pos);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //Add relations
    public void addRelations() {
        DbHandler dbHandler = new DbHandler();
        List<MongoSinhalaSynset> nounSynset = dbHandler.findAllLatestSynsets(POS.NOUN);
        List<MongoSinhalaSynset> verbSynset = dbHandler.findAllLatestSynsets(POS.VERB);
        List<MongoSinhalaSynset> adjSynset = dbHandler.findAllLatestSynsets(POS.ADJECTIVE);
        List<MongoSinhalaSynset> advSynset = dbHandler.findAllLatestSynsets(POS.ADVERB);
        HashMap<String, Integer> rootOrder = dbHandler.findRootOrder();
        try {
            sh.addRelations(nounSynset, verbSynset, adjSynset, advSynset, rootOrder);
        } catch (JWNLException e) {
            e.printStackTrace();
        }

    }

    // Finding all latest synset to convert to text
    public List<MongoSinhalaSynset> findAllLatestSynsets(POS pos) {
        ApplicationContext ctx =
                new AnnotationConfigApplicationContext(SpringMongoConfig.class);
        MongoOperations mongoOperation = (MongoOperations) ctx.getBean("mongoTemplate");
        List<MongoSinhalaSynset> distinctCollection = new ArrayList<MongoSinhalaSynset>();
        List<String> words = new ArrayList<String>();
        List<Long> ids = new ArrayList<Long>();
        if (pos == POS.NOUN) {
            List<MongoSinhalaNoun> ncollection = mongoOperation.findAll(MongoSinhalaNoun.class);
            for (MongoSinhalaSynset s : ncollection) {

                if (!ids.contains(s.getEWNId())) {
                    ids.add(s.getEWNId());
                    distinctCollection.add(s);
                } else {
                    int position = ids.indexOf(s.getEWNId());
                    ids.remove(position);
                    distinctCollection.remove(position);
                    ids.add(s.getEWNId());
                    distinctCollection.add(s);
                }
            }

        } else if (pos == POS.VERB) {
            List<MongoSinhalaVerb> vcollection = mongoOperation.findAll(MongoSinhalaVerb.class);
            for (MongoSinhalaSynset s : vcollection) {

                if (!ids.contains(s.getEWNId())) {
                    ids.add(s.getEWNId());
                    distinctCollection.add(s);
                } else {
                    int position = ids.indexOf(s.getEWNId());
                    ids.remove(position);
                    distinctCollection.remove(position);
                    ids.add(s.getEWNId());
                    distinctCollection.add(s);
                }

            }

        } else if (pos == POS.ADJECTIVE) {
            List<MongoSinhalaAdjective> acollection = mongoOperation.findAll(MongoSinhalaAdjective.class);
            for (MongoSinhalaSynset s : acollection) {

                if (!ids.contains(s.getEWNId())) {
                    ids.add(s.getEWNId());
                    distinctCollection.add(s);
                } else {
                    int position = ids.indexOf(s.getEWNId());
                    ids.remove(position);
                    distinctCollection.remove(position);
                    ids.add(s.getEWNId());
                    distinctCollection.add(s);
                }

            }

        } else if (pos == POS.ADVERB) {
            List<MongoSinhalaAdverb> acollection = mongoOperation.findAll(MongoSinhalaAdverb.class);
            for (MongoSinhalaSynset s : acollection) {

                if (!ids.contains(s.getEWNId())) {
                    ids.add(s.getEWNId());
                    distinctCollection.add(s);
                } else {
                    int position = ids.indexOf(s.getEWNId());
                    ids.remove(position);
                    distinctCollection.remove(position);
                    ids.add(s.getEWNId());
                    distinctCollection.add(s);
                }

            }

        } else if (pos == POS.ROOT) {
            List<MongoSinhalaRoot> rcollection = mongoOperation.findAll(MongoSinhalaRoot.class);
            for (MongoSinhalaSynset s : rcollection) {
                if (!words.contains(s.getWordsAsString())) {
                    words.add(s.getWordsAsString());
                    distinctCollection.add(s);
                } else {
                    int position = words.indexOf(s.getWordsAsString());
                    words.remove(position);
                    distinctCollection.remove(position);
                    words.add(s.getWordsAsString());
                    distinctCollection.add(s);
                }

            }

        } else if (pos == POS.GENDER) {
            List<MongoSinhalaGender> gcollection = mongoOperation.findAll(MongoSinhalaGender.class);
            for (MongoSinhalaSynset s : gcollection) {
                if (!words.contains(s.getWordsAsString())) {
                    words.add(s.getWordsAsString());
                    distinctCollection.add(s);
                } else {
                    int position = words.indexOf(s.getWordsAsString());
                    words.remove(position);
                    distinctCollection.remove(position);
                    words.add(s.getWordsAsString());
                    distinctCollection.add(s);
                }

            }

        } else if (pos == POS.ORIGIN) {
            List<MongoSinhalaOrigin> ocollection = mongoOperation.findAll(MongoSinhalaOrigin.class);
            for (MongoSinhalaSynset s : ocollection) {
                if (!words.contains(s.getWordsAsString())) {
                    words.add(s.getWordsAsString());
                    distinctCollection.add(s);
                } else {
                    int position = words.indexOf(s.getWordsAsString());
                    words.remove(position);
                    distinctCollection.remove(position);
                    words.add(s.getWordsAsString());
                    distinctCollection.add(s);
                }

            }

        } else if (pos == POS.USAGE) {
            List<MongoSinhalaUsage> ucollection = mongoOperation.findAll(MongoSinhalaUsage.class);
            for (MongoSinhalaSynset s : ucollection) {
                if (!words.contains(s.getWordsAsString())) {
                    words.add(s.getWordsAsString());
                    distinctCollection.add(s);
                } else {
                    int position = words.indexOf(s.getWordsAsString());
                    words.remove(position);
                    distinctCollection.remove(position);
                    words.add(s.getWordsAsString());
                    distinctCollection.add(s);
                }

            }

        } else if (pos == POS.DERIVATIONLANG) {
            List<MongoSinhalaDerivationType> dcollection = mongoOperation.findAll(MongoSinhalaDerivationType.class);
            for (MongoSinhalaSynset s : dcollection) {
                if (!words.contains(s.getWordsAsString())) {
                    words.add(s.getWordsAsString());
                    distinctCollection.add(s);
                } else {
                    int position = words.indexOf(s.getWordsAsString());
                    words.remove(position);
                    distinctCollection.remove(position);
                    words.add(s.getWordsAsString());
                    distinctCollection.add(s);
                }

            }

        }

        return distinctCollection;

    }

    // Find root order to help add relation function
    public HashMap<String, Integer> findRootOrder() {
        ApplicationContext ctx =
                new AnnotationConfigApplicationContext(SpringMongoConfig.class);
        MongoOperations mongoOperation = (MongoOperations) ctx.getBean("mongoTemplate");
        List<MongoSinhalaRoot> collection = mongoOperation.findAll(MongoSinhalaRoot.class);
        HashMap<String, Integer> hm = new HashMap<String, Integer>();

        List<MongoSinhalaRoot> distinctCollection = new ArrayList<MongoSinhalaRoot>();
        List<String> words = new ArrayList<String>();
        for (MongoSinhalaRoot s : collection) {
            if (!words.contains(s.getWordsAsString())) {
                words.add(s.getWordsAsString());
                distinctCollection.add(s);
            } else {
                int position = words.indexOf(s.getWordsAsString());
                words.remove(position);
                distinctCollection.remove(position);
                words.add(s.getWordsAsString());
                distinctCollection.add(s);
            }

        }
        int i = 0;
        for (MongoSinhalaRoot s : distinctCollection) {
            hm.put(s.getId(), i);
            i++;
        }

        return hm;

    }

}
