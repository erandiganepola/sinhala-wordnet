package org.sinhala.wordnet.css.jwnl;

import net.didion.jwnl.JWNL;
import net.didion.jwnl.JWNLException;
import net.didion.jwnl.dictionary.Dictionary;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import static org.sinhala.wordnet.css.common.Constants.Properties.WORDNET_PROPS_FILE;

public class WordNetDictionary {

    private static Dictionary dict;

    private WordNetDictionary() {
    }

    public static Dictionary getInstance() {
        if (dict != null) {
            return dict;
        } else {
            String propertiesFile = System.getProperty(WORDNET_PROPS_FILE);
            try {
                JWNL.initialize(new FileInputStream(propertiesFile));
            } catch (FileNotFoundException | JWNLException e) {
                e.printStackTrace();
            }

            dict = Dictionary.getInstance();
            return dict;
        }
    }
}
