package regex;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.*;

class In {

    private static In instance;

    private In(){}

    private static void init(){
        if (instance == null)
            instance = new In();
    }

    private static void deallocate(){
        if (instance != null)
            instance = null;
    }

    static In use(){
        init();
        return instance;
    }

    static In close(){
        deallocate();
        return instance;
    }

    String getText(String url){
        File file = new File(url);
        StringBuilder toReturn = new StringBuilder();
        try {
            FileReader fr = new FileReader(file);
            BufferedReader reader = new BufferedReader(fr);
            String temp;
            while ((temp = reader.readLine()) != null){
                toReturn.append(temp).append(" ");
            }
            reader.close();
            fr.close();
            return toReturn.toString();
        } catch (IOException e){
            e.printStackTrace();
        }
        return "";
    }

    String getDocx(String url){
        File file = new File(url);
        String result = "";
        try {
            FileInputStream fis = new FileInputStream(file);
            XWPFDocument document = new XWPFDocument(fis);
            XWPFWordExtractor extractor = new XWPFWordExtractor(document);
            result = extractor.getText();
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    String getPdf(String url){
        try {
            StringBuilder text = new StringBuilder();
            PdfReader reader = new PdfReader(url);
            int pages = reader.getNumberOfPages();
            for (int i = 0; i <= pages;i++){
                text.append(PdfTextExtractor.getTextFromPage(reader,i)).append(" ");
            }
            reader.close();
            return text.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

}
