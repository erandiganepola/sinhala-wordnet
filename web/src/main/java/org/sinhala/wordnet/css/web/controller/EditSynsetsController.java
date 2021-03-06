package org.sinhala.wordnet.css.web.controller;

import net.didion.jwnl.JWNLException;
import net.didion.jwnl.data.POS;
import net.didion.jwnl.data.Synset;
import net.didion.jwnl.dictionary.Dictionary;
import org.sinhala.wordnet.css.common.util.MeaningRequestHandler;
import org.sinhala.wordnet.css.jwnl.WordNetDictionary;
import org.sinhala.wordnet.css.model.wordnet.NounSynset;
import org.sinhala.wordnet.css.model.wordnet.SinhalaWordNetSynset;
import org.sinhala.wordnet.css.model.wordnet.SinhalaWordNetWord;
import org.sinhala.wordnet.css.model.wordnet.VerbSynset;
import org.sinhala.wordnet.css.web.model.BreadCrumb;
import org.sinhala.wordnet.wordnetDB.core.SinhalaSynsetMongoSynsetConvertor;
import org.sinhala.wordnet.wordnetDB.core.SynsetMongoDbHandler;
import org.sinhala.wordnet.wordnetDB.model.MongoSinhalaSynset;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/EditSynsetsnoun")
public class EditSynsetsController {

    @RequestMapping(method = RequestMethod.GET, params = {"action=ShowEditSynset", "type=noun"})
    public String showEditSynset(@RequestParam(value = "id", required = false) String id, ModelMap model,
            @RequestParam(value = "type", required = false) String type,
            @RequestParam(value = "mongoid", required = false) String mongoid) {
        Long sId = Long.parseLong(id);
        Long tId = sId;
        boolean check = false;
        if (!"".equals(id)) {
            if (sId > 99999999) {
                sId = (long) 1740;
                check = true;
            }
            BreadCrumb breadCrumb = new BreadCrumb(sId, POS.NOUN);
            Dictionary dict = WordNetDictionary.getInstance();
            Synset synset = null;
            try {
                synset = dict.getSynsetAt(POS.NOUN, sId);
            } catch (NumberFormatException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (JWNLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }


            NounSynset castSynset = new NounSynset(synset);
            NounSynset mongoCastSynset = new NounSynset();
            SinhalaSynsetMongoSynsetConvertor mongoSynsetConvertor = new SinhalaSynsetMongoSynsetConvertor();
            if (check) {
                SynsetMongoDbHandler dbHandler = new SynsetMongoDbHandler();
                MongoSinhalaSynset tempSyn = dbHandler.findBySynsetId(tId, POS.NOUN);
                mongoCastSynset = (NounSynset) mongoSynsetConvertor.OverWriteByMongo(castSynset, tempSyn.getId());
            } else {
                mongoCastSynset = (NounSynset) mongoSynsetConvertor.OverWriteByMongo(castSynset, mongoid);
            }

            MeaningRequestHandler meaningRequestHandler = new MeaningRequestHandler();
            List<String> wordList = castSynset.getWordArrayList();
            List<String> mongoWordList = mongoCastSynset.getWordArrayList();

            List<List<String>> meaningsList = meaningRequestHandler.getMeaningLists(wordList);
            List<String> intersection = meaningRequestHandler.getIntersection(meaningsList);

            model.addAttribute("synset", mongoCastSynset);
            model.addAttribute("enSynset", castSynset);
            model.addAttribute("meaningsList", meaningsList);
            model.addAttribute("intersection", intersection);
            model.addAttribute("wordList", mongoWordList);
            model.addAttribute("enWordList", wordList);
            model.addAttribute("type", type);
            model.addAttribute("breadCrumb", breadCrumb);

            return "EditSynset";
        } else {
            return "error";
        }
    }

    @RequestMapping(method = RequestMethod.GET, params = {"action=ShowEditSynset", "type=verb"})
    public String showVerbEditSynset(@RequestParam(value = "id", required = false) String id,
            ModelMap model,
            @RequestParam(value = "type", required = false) String type,
            @RequestParam(value = "mongoid", required = false) String mongoid) {

        if (id != null && !"".equals(id)) {
            BreadCrumb breadCrumb = new BreadCrumb(Long.parseLong(id), POS.VERB);
            Dictionary dict = WordNetDictionary.getInstance();
            Synset synset = null;
            try {
                synset = dict.getSynsetAt(POS.VERB, Long.parseLong(id));
            } catch (NumberFormatException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (JWNLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            VerbSynset castSynset = new VerbSynset(synset);
            SinhalaSynsetMongoSynsetConvertor mongoSynsetConvertor = new SinhalaSynsetMongoSynsetConvertor();
            VerbSynset mongoCastSynset = (VerbSynset) mongoSynsetConvertor.OverWriteByMongo(castSynset, mongoid);

            MeaningRequestHandler meaningRequestHandler = new MeaningRequestHandler();
            List<String> wordList = castSynset.getWordArrayList();
            List<String> mongoWordList = mongoCastSynset.getWordArrayList();
            List<List<String>> meaningsList = meaningRequestHandler.getMeaningLists(wordList);
            List<String> intersection = meaningRequestHandler.getIntersection(meaningsList);

            model.addAttribute("synset", mongoCastSynset);
            model.addAttribute("enSynset", castSynset);
            model.addAttribute("meaningsList", meaningsList);
            model.addAttribute("intersection", intersection);
            model.addAttribute("wordList", mongoWordList);
            model.addAttribute("enWordList", wordList);
            model.addAttribute("type", type);
            model.addAttribute("breadCrumb", breadCrumb);

            return "EditSynset";
        } else {
            return "error";
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    public String editSynset(@ModelAttribute NounSynset synset, ModelMap model) {


        SinhalaWordNetSynset CommSynset = synset;

        List<SinhalaWordNetWord> words = synset.getWords();


        for (int i = 0; i < words.size(); i++) {
            if (words.get(i).isLemmaNull()) {
                words.remove(i);
            }
        }


        NounSynset nSynset = (NounSynset) synset;
        System.out.println(synset.getEvaluated() + "rating");
        SynsetMongoDbHandler synsetdb = new SynsetMongoDbHandler();
        //System.out.println("user name"+nSynset.getUserName());
        synsetdb.addSynset(nSynset);
        //String rating = nSynset.getRating();

        // App app = new App();
        // synsetdb.test();

        // model.addAttribute("synset", synset);

        return showEditSynset(String.valueOf(synset.getOffset()), model, "noun", "");
    }


}
