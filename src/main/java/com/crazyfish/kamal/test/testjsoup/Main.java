package com.crazyfish.kamal.test.testjsoup;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;


public class Main
{
    public static void main(String args[])
    {
        final String BASEURL = "http://piaofang.maoyan.com/cinema";
        final String TAGS = "_v_";
        String currentTAGS = "yes";
        try {
            Document document = Jsoup.connect(BASEURL)
                    .timeout(10000)//?_v_=yes
                    .data(TAGS, currentTAGS)
                    .get();
            System.out.println(String.format("%.20f",3.23f));
            System.out.println(String.format("%.20f",3.23d));
            List<CinemaData> data = new ArrayList<CinemaData>();
            Elements lis = document.select("tbody#cinema-tbody tr");
            System.out.println("lis.size() = " + lis.size());
            for (int i = 0; i < lis.size(); i++) {
                CinemaData cinemaData = new CinemaData();
                String id = lis.get(i).attr("data-cinemaid");
                Elements oneData = lis.get(i).select("td");

                cinemaData.setName(oneData.get(1).html());
                cinemaData.setBox(oneData.get(2).html());
                cinemaData.setPeopleNum(oneData.get(3).html());
                cinemaData.setAvgPeople(oneData.get(4).html());
                cinemaData.setAvgPrice(oneData.get(5).html());

                data.add(cinemaData);
            }

            for(CinemaData cinemaData : data){
                System.out.println("name:" + cinemaData.getName());
                System.out.println("box:" + cinemaData.getBox());
                System.out.println("人次:" + cinemaData.getPeopleNum());
                System.out.println("场均人次:" + cinemaData.getAvgPeople());
                System.out.println("单座票房:" + cinemaData.getAvgPrice());
                System.out.println();
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    static class CinemaData{
        String name;
        String box;
        String peopleNum;
        String avgPeople;
        String avgPrice;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getBox() {
            return box;
        }

        public void setBox(String box) {
            this.box = box;
        }

        public String getPeopleNum() {
            return peopleNum;
        }

        public void setPeopleNum(String peopleNum) {
            this.peopleNum = peopleNum;
        }

        public String getAvgPeople() {
            return avgPeople;
        }

        public void setAvgPeople(String avgPeople) {
            this.avgPeople = avgPeople;
        }

        public String getAvgPrice() {
            return avgPrice;
        }

        public void setAvgPrice(String avgPrice) {
            this.avgPrice = avgPrice;
        }
    }
}
