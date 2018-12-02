package org.sinhala.wordnet.DBconvert.core;

import net.sf.extjwnl.data.POS;
import net.sf.extjwnl.utilities.Shakshara;

/**
 * Stores the dictionary stored in the given MongoDB database into files specified.
 */
public class MongoDBToText {

    public static void main(String[] args) {
        Shakshara sh = new Shakshara(args[0]);

        sh.initialize();
        sh.delete();
        sh.initialize();

        DbHandler dbHandler = new DbHandler(sh);
        dbHandler.addSynsetToText(POS.NOUN);
        dbHandler.addSynsetToText(POS.VERB);
        dbHandler.addSynsetToText(POS.ADJECTIVE);
        dbHandler.addSynsetToText(POS.ADVERB);
        dbHandler.addSynsetToText(POS.ROOT);
        dbHandler.addSynsetToText(POS.USAGE);
        dbHandler.addSynsetToText(POS.GENDER);
        dbHandler.addSynsetToText(POS.ORIGIN);
        dbHandler.addSynsetToText(POS.DERIVATIONLANG);

        dbHandler.addRelations();

        sh.close();
        System.out.println("done");
    }
}