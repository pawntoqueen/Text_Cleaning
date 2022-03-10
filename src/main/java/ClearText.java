import zemberek.morphology.TurkishMorphology;
import zemberek.morphology.analysis.SentenceAnalysis;
import zemberek.morphology.analysis.SingleAnalysis;
import zemberek.morphology.analysis.WordAnalysis;
import zemberek.tokenization.Token;
import zemberek.tokenization.TurkishTokenizer;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class ClearText {



    public static ArrayList<String> tokenizasyon(String idea){
        ArrayList<String> words = new ArrayList<>();
        TurkishTokenizer tokenizer = TurkishTokenizer
                .builder()
                .ignoreTypes(Token.Type.NewLine, Token.Type.SpaceTab)
                .build();
        List<Token> tokens = tokenizer.tokenize(idea);
        for (Token token : tokens) {
            words.add(token.content);
        }
        return words;
    }


    public static void disambiguateSentence(String idea) throws IOException {
        File file = new File("dosya.txt");
        if (!file.exists())
        {
            file.createNewFile();
        }
        FileWriter fileWriter = new FileWriter(file, true);
        BufferedWriter bWriter = new BufferedWriter(fileWriter);



        TurkishMorphology morphology = TurkishMorphology.createWithDefaults();
        ArrayList<String> tokenler = tokenizasyon(idea);
        int tokenCount = 0;

        List<WordAnalysis> analyses = morphology.analyzeSentence(idea);
        SentenceAnalysis result = morphology.disambiguate(idea, analyses);

        for (SingleAnalysis s : result.bestAnalysis()) {
            String analyzed = "";

            analyzed = s.getDictionaryItem().root;
            if (!analyzed.equals("UNK")){
                System.out.println(analyzed);
                bWriter.write(analyzed + " ");
            }
        }
        bWriter.close();

    }

    public static String dosya_oku() throws IOException {
        FileReader fileReader = new FileReader("hayvanciftligi.txt");
        String line;
        String uzuncumle = "";

        BufferedReader br = new BufferedReader(fileReader);

        while ((line = br.readLine()) != null) {
            if (!line.equals("")){
                uzuncumle += line + " ";
            }

        }

        br.close();
       return uzuncumle;
    }

    public static void main(String[] args) throws IOException {
        disambiguateSentence(dosya_oku());

    }
}
