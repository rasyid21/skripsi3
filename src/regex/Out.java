package regex;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

class Out {

    private static Out instance;

    private DecimalFormat df;

    private Out(){
        df = new DecimalFormat("#.##");
    }

    private static void init(){
        if (instance == null)
            instance = new Out();
    }

    private static void deallocate(){
        if (instance != null){
            instance.df = null;
            instance    = null;
        }
    }

    static Out use(){
        init();
        return instance;
    }

    static Out close(){
        deallocate();
        return instance;
    }

    void generate(String url, ArrayList<Compare> list){
        File file = new File(url);
        try {
            FileWriter fw = new FileWriter(file);
            BufferedWriter writer = new BufferedWriter(fw);
            writer.write("kata,frekuensi,persentase\n");
            for (Compare compare:list){
                writer.write(compare.getKata()+","+compare.getTotal()+","+(df.format(compare.getFrekuensi())+"").replace(",",".")+"%\n");
            }
            writer.close();
            fw.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

}
