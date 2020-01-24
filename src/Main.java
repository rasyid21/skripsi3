import regex.Regex;

public class Main {

    public static void main(String[] args) {
        Regex regex = Regex.use();
        regex.match("sample.txt","sample2.txt","sample3.docx");
        regex.most(4);
        regex.create_csv();
        regex = Regex.close();
        if (regex == null)
            System.out.println("FINISH");
    }

}
#include <stdio.h>
