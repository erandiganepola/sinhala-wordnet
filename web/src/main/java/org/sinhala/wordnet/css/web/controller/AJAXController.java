package org.sinhala.wordnet.css.web.controller;

import net.didion.jwnl.data.POS;
import org.sinhala.wordnet.wordnetDB.core.SynsetMongoDbHandler;
import org.sinhala.wordnet.wordnetDB.model.MongoSinhalaRoot;
import org.sinhala.wordnet.wordnetDB.model.MongoSinhalaSynset;
import org.sinhala.wordnet.wordnetDB.model.MongoSinhalaWord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/Ajax")
public class AJAXController {

    private static final Logger logger = LoggerFactory.getLogger(AJAXController.class);

    @RequestMapping(method = RequestMethod.GET, params = {"action=getRoots"})
    @ResponseBody
    public List<MongoSinhalaRoot> returnNounRoot(@RequestParam(value = "term", required = false) String term) {
        byte ptext[] = null;
        String value = null;
        try {
            ptext = term.getBytes("ISO8859_1");
            value = new String(ptext, Charset.forName("UTF-8"));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // load from mongo DB
        List<MongoSinhalaRoot> list = new ArrayList<MongoSinhalaRoot>();
        list = new SynsetMongoDbHandler().findAllRoots();
        List<MongoSinhalaRoot> returnList = new ArrayList<MongoSinhalaRoot>();
        for (MongoSinhalaRoot w : list) {
            if (w.getWords().get(0).getLemma().startsWith(value)) {
                returnList.add(w);
            }
        }

        return returnList;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{word}/synonyms")
    @ResponseBody
    public List<String> getSynonyms(@PathVariable String word) {
        SynsetMongoDbHandler handler = new SynsetMongoDbHandler();
        Set<String> words = new HashSet<>();
        for (POS pos : (List<POS>) POS.getAllPOS()) {
            HashMap<Long, MongoSinhalaSynset> synsetsByLemma = handler.findSynsetsByLemma(word, pos);
            for (MongoSinhalaSynset synset : synsetsByLemma.values()) {
                for (MongoSinhalaWord sinhalaWord : synset.getWords()) {
                    words.add(sinhalaWord.getLemma());
                }
            }
        }

        return new ArrayList<>(words);
    }
}
