package regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex {

    private static Regex instance;

    private Out output;
    private In input;
    private Hash hash;

    private String url;


    private Regex(){
        output = Out.use();
        input  = In.use();
        hash   = Hash.use();
        url    = null;
    }

    private static void init(){
        if (instance == null)
            instance = new Regex();
    }

    private static void deallocate(){
        if (instance != null){
            instance.output = Out.close();
            instance.input  = In.close();
            instance.hash   = Hash.close();
            instance.url    = null;
            instance = null;
        }
    }

    public static Regex use(){
        init();
        return instance;
    }

    public static Regex close(){
        deallocate();
        return instance;
    }

    public void match(String...file_urls){
        process_url(file_urls[0]);
        String text;
        String[] tmp;
        for (String file:file_urls){
            tmp = file.split("\\.");
            switch (tmp[tmp.length - 1]) {
                case "txt":
                case "csv":
                    text = input.getText(file);
                    regex(text);
                    break;
                case "doc":
                case "docx":
                    text = input.getDocx(file);
                    regex(text);
                    break;
                case "pdf":
                    text = input.getPdf(file);
                    regex(text);
                    break;
            }

        }
        hash.print();
    }

    private void regex(String text){
        String[] words = text.split("\\s+");
        Pattern pattern;
        Matcher matcher;
        for (String word:words){
            if (!hash.hasSearched(word)){
                pattern = Pattern.compile("\\b("+word+")\\b");
                matcher = pattern.matcher(text);
                while (matcher.find()){
                    hash.add(matcher.group(1));
                }
            }
        }
    }

    private void process_url(String url){
        StringBuilder processed_url = new StringBuilder();
        String file_url = "regex_word_frequency_result.csv";
        String[] val;
        if (url.contains("\\")){
            val = url.split("\\\\");
            val[val.length-1] = file_url;
            for (int i = 0; i < val.length-1;i++)
                processed_url.append(val[i]).append("\\");

            processed_url.append(val[val.length-1]);
            this.url = processed_url.toString();
            return;
        }
        if (url.contains("/")){
            val = url.split("/");
            val[val.length-1] = file_url;
            for (int i = 0;i < val.length-1;i++)
                processed_url.append(val[i]).append("/");
            processed_url.append(val[val.length-1]);
            this.url = processed_url.toString();
            return;
        }
        this.url = file_url;
    }

    public void most(int index){
        hash.most(index);
    }

    public void create_csv(){
        output.generate(url,hash.getList());
    }

}
