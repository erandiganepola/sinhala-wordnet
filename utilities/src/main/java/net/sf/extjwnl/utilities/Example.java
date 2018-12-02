package net.sf.extjwnl.utilities;

public class Example {

    public static void main(String[] args) {
        Shakshara Shakshara = new Shakshara(args[0]);

        try {
            Shakshara.addNewWord(2406, "newWord");
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Shakshara.editWord(2671, 2, "nihal123");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
