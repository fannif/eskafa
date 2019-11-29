package recommendations.services;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import recommendations.domain.Link;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.ArrayList;

import java.util.Scanner;

import recommendations.dao.ReaderDao;
import recommendations.domain.Course;
import recommendations.domain.Tag;

import java.net.URL;
import java.net.URLConnection;
import recommendations.io.IO;

public class LinkService {

    private IO io;
    private ReaderDao linkDao;

    public LinkService(ReaderDao dao, IO io) {
        this.io = io;
        this.linkDao = dao;
    }

    public boolean addLinkWithMeta(int id, String title, String url, String type, ArrayList<Tag> tags, ArrayList<Course> courses, String comment) throws SQLException, IOException, MalformedURLException, URISyntaxException {

        String descriptionMetaData = getDescription(url);
        Link l = new Link(id, title, url, type, descriptionMetaData, tags, courses, comment);

        return linkDao.save(l);
    }

    public ArrayList<Link> listLinks() throws SQLException {
        return (ArrayList<Link>) linkDao.findAll();
    }

//   Tämän voi poistaa, jos metatiedoista ei käytetä kuin descriptionia
//
//    public String getMeta(String url) {
//        try {
//            Document document = Jsoup.connect(url).get();
//            Elements metaData = document.getElementsByTag("meta");
//            return metaData.toString();
//
//        } catch (IOException ex) {
//            System.out.println("Check internet connection! " + ex.toString());
//
//        }
//        return "";
//
//    }
    public String getDescription(String url) throws IOException {

        if (!connected(url)) {
            String noConnectionDesc = askForDescription();
            return noConnectionDesc;
        }

        try {
            Document document = Jsoup.connect(url).get();
            String description = document.select("meta[name=description]").get(0).attr("content");
            return description;
        } catch (IndexOutOfBoundsException ex) {
            io.print("Metadata description not found!");
        }

        String desc = askForDescription();
        return desc;
    }

    public void remove(String title) throws Exception {
        boolean go = true;
        String input = title;
        while (go) {
            if (input.equals("q")) {
                return;
            }
            if (linkDao.findOne(input) == null) {
                io.print("No such link found. Please check the spelling and try again: ");
                io.print("To return back to main menu, enter q");
                input = io.read();
            } else {
                linkDao.delete(input);
                go = false;
            }
        }
        io.print("The link has been successfully removed");
    }

    public String askForDescription() {
        io.print("Metadata description not found!");
        io.print("Enter description manually or press enter for no description:");
        String descr = io.read();
        return descr;

    }

    public boolean connected(String url) {
        try {
            URL check = new URL(url);
            URLConnection connection = check.openConnection();
            connection.connect();
            return true;
        } catch (MalformedURLException e) {
            return false;
        } catch (IOException e) {
            return false;
        }
    }
}
