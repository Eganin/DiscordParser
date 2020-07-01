package com.company;

import org.jsoup.nodes.Document;

import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.HashMap;
import java.util.LinkedList;

public class Parser {
    public int numberPage;
    private String mainPage = new String("https://discord-server.com/ru?language=all&page=%s&rowsOnPage=100&sort=popular");
    private Formatter formatterMainPage;
    private String baseUrl = new String("https://discord-server.com");

    public Parser() {// обычный конструктор
        createFormatter();
    }

    public Parser(Integer numberPage) {// конструктор со страницей
        this.numberPage = numberPage;
        createFormatter();
    }

    public void setNumberPage(Integer numberPage) {// setter from numberPage
        this.numberPage = numberPage;
    }

    public Integer getNumberPage() { // getter from numberPage
        return numberPage;
    }

    public String getUrl() {// return formatter url-> String
        return formatterMainPage.toString();
    }

    public void createFormatter() { // init formatter url
        this.formatterMainPage = new Formatter().format(mainPage, numberPage);
    }

    public Document getPage() throws IOException {
        // данный метод возвращает содержимое веб страницы
        // получаем formatter но преобоазуем в String
        Document page = Jsoup.parse(new URL(formatterMainPage.toString()), 60000); // получаем страницу
        return page;
    }

    public String getPagination() throws IOException {
        // Метод возвращает количество всех страниц->String
        Document firstPage = getPage();
        Element pagination = firstPage.select("div[class=serverpages]").first();
        String numPage = pagination.text().trim();
        String allPage = numPage.split(" ")[2];
        return allPage;
    }

    public Elements channels() throws IOException {
        // метод возвращает все каналы на текущей странице
        Document page = getPage();
        Elements listChannel = page.select("div[class=servers-shadow]");
        return listChannel;
    }

    public StringBuffer parserUsersInfo(String usersInfo) {
        StringBuffer stringBuffer = new StringBuffer();
        String[] usersList = usersInfo.split(" ");
        for (String element : usersList) {
            if (element.equals("Online")) {
                stringBuffer.append(" Онлайн подписчиков: ");
            } else if (element.equals("Members")) {
                stringBuffer.append(" Подписчиков всего ");
            } else {
                stringBuffer.append(element);
            }
        }
        stringBuffer.append(" Лайков");

        return stringBuffer;
    }

    public HashMap<String, String> parsingChannels(Elements chanells) throws IOException {
        LinkedList<String> ArrayTags = new LinkedList<String>(); // односвязный список для тэгов

        HashMap<String , String> discordChannel = new HashMap<String , String>(); // типа dict

        Elements infoServerChannel = chanells.select("td[class=servers_info]");

        String name = infoServerChannel.select("a").first().text().trim();

        // с помощью attr получаем аттрибут href
        String href = infoServerChannel.select("a").first().attr("href");
        String newhref = href.replace("/ru/", baseUrl); // дополняем строку чтобы она была рабочей

        String description = infoServerChannel.select("div[class=about_server]").first().text();

        Element users = infoServerChannel.select("div[class=user_number]").first();
        String userDop = users.select("span").text(); // информация о участниках
        StringBuffer usersInfo = parserUsersInfo(userDop);

        Element tagsList = infoServerChannel.select("div[class=tags]").first();
        Elements arrayTags = tagsList.select("a");
        // проходимся по тэгам и в linkedlist добавляем текст тэга
        for (Element element : arrayTags) {
            ArrayTags.add(element.text());
        }
        // добавление в HashMap данных
        discordChannel.put("name",name);
        discordChannel.put("href",newhref);
        discordChannel.put("description",description);
        discordChannel.put("users",String.valueOf(usersInfo));// используем valueOf чтобы привести к строке
        discordChannel.put("tags",String.valueOf(ArrayTags));

        return discordChannel;



    }

}
