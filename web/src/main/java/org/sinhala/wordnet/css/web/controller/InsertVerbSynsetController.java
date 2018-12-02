package org.sinhala.wordnet.css.web.controller;

import net.didion.jwnl.JWNLException;
import net.didion.jwnl.data.POS;
import net.didion.jwnl.data.Synset;
import net.didion.jwnl.dictionary.Dictionary;
import org.sinhala.wordnet.css.common.util.MeaningRequestHandler;
import org.sinhala.wordnet.css.jwnl.WordNetDictionary;
import org.sinhala.wordnet.css.model.wordnet.SinhalaWordNetSynset;
import org.sinhala.wordnet.css.model.wordnet.SinhalaWordNetWord;
import org.sinhala.wordnet.css.model.wordnet.VerbSynset;
import org.sinhala.wordnet.css.web.model.BreadCrumb;
import org.sinhala.wordnet.wordnetDB.core.SinhalaSynsetMongoSynsetConvertor;
import org.sinhala.wordnet.wordnetDB.core.SynsetMongoDbHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
@Controller
@RequestMapping("/InsetNewSynsetverb")
public class InsertVerbSynsetController {
	@RequestMapping(method = RequestMethod.GET, params = {
			"action=InsertASynset", "type=verb" })
	public String insertVerbSynset(
			@RequestParam(value = "id", required = false) String id,
			ModelMap model,
			@RequestParam(value = "type", required = false) String type,
			@RequestParam(value = "mongoid", required = false) String mongoid) {

		if (id != null && !"".equals(id)) {
			BreadCrumb breadCrumb = new BreadCrumb(Long.parseLong(id), POS.VERB);
			Dictionary dict = WordNetDictionary.getInstance();
			Synset synset = null;
			try {
				synset = dict.getSynsetAt(POS.VERB, 2724);
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JWNLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			VerbSynset castSynset = new VerbSynset(synset);
			VerbSynset mongoCastSynset = new VerbSynset();
			SinhalaSynsetMongoSynsetConvertor mongoSynsetConvertor = new SinhalaSynsetMongoSynsetConvertor();
			mongoCastSynset = (VerbSynset) mongoSynsetConvertor
					.OverWriteByMongo(castSynset, mongoid);

			MeaningRequestHandler meaningRequestHandler = new MeaningRequestHandler();
			List<String> wordList = castSynset.getWordArrayList();
			List<String> mongoWordList = mongoCastSynset.getWordArrayList();

			List<List<String>> meaningsList = meaningRequestHandler
					.getMeaningLists(wordList);
			List<String> intersection = meaningRequestHandler
					.getIntersection(meaningsList);

			mongoCastSynset.setDefinition(" ");
			mongoCastSynset.setExample(" ");
			mongoCastSynset.setOffset(Long.parseLong(id));
			List<SinhalaWordNetWord> mongoWordLlist = mongoCastSynset.getWords();
			for (int i = 0; i < mongoWordLlist.size(); i++) {
				mongoWordLlist.get(i).setLemma(" ");
				mongoWordLlist.get(i).setRoot(null);
			}
			mongoCastSynset.setWords(mongoWordLlist);

			castSynset.setDefinition(" ");
			castSynset.setExample(" ");
			List<SinhalaWordNetWord> castWordLlist = castSynset.getWords();
			for (int i = 0; i < castWordLlist.size(); i++) {
				castWordLlist.get(i).setLemma(" ");
				castWordLlist.get(i).setRoot(null);
			}
			castSynset.setWords(castWordLlist);

			model.addAttribute("synset", mongoCastSynset);
			model.addAttribute("enSynset", castSynset);
			model.addAttribute("meaningsList", meaningsList);
			model.addAttribute("intersection", intersection);
			model.addAttribute("wordList", mongoWordList);
			model.addAttribute("enWordList", wordList);
			model.addAttribute("type", type);
			model.addAttribute("breadCrumb", breadCrumb);

			return "InsertNewSynsets";
		}

		else {
			return "error";
		}
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String editSynset(@ModelAttribute VerbSynset synset, ModelMap model) {

		SinhalaWordNetSynset CommSynset = synset;

//		System.out.println("Check Line : Verb");
//		System.out.println(synset.getWordArrayList().get(0));

		List<SinhalaWordNetWord> words = synset.getWords();

		for (int i = 0; i < words.size(); i++) {
			if (words.get(i).isLemmaNull()) {
				words.remove(i);
			}
		}

		VerbSynset nSynset = (VerbSynset) synset;
		Long parent = nSynset.getOffset();
		SynsetMongoDbHandler synsetdb = new SynsetMongoDbHandler();
//		System.out.println(nSynset+" "+parent);
		synsetdb.addNewSynset(nSynset,parent);

		return insertVerbSynset(String.valueOf(synset.getOffset()), model,
				"verb", "");
	}

}
