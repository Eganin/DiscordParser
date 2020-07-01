package com.company;

import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashMap;

public class Main {

    public static void main(String[] args) {
        // write your code here
        try {
            Parser parser = new Parser(1);
            String allPagination = parser.getPagination();
            int pagination = Integer.parseInt(allPagination);// получаем кол-во страниц->int
            for(int i =1;i<=pagination;i++){
                Parser newParser = new Parser(i);
                Elements listchannel = newParser.channels();
                HashMap result = newParser.parsingChannels(listchannel);
                System.out.println(result);
            }


        } catch (IOException e) {
            System.out.println("Произошла какая-то ошибка");
            e.printStackTrace();
        }
    }
}
