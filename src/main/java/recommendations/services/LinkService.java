package recommendations.services;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import recommendations.domain.Link;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import recommendations.dao.ReaderDao;
import recommendations.domain.Course;
import recommendations.domain.Tag;

public class LinkService {

    private ReaderDao linkDao;

    public LinkService(ReaderDao dao) {
        this.linkDao = dao;
    }

    public boolean addLink(Link link) throws IOException, SQLException {
        return linkDao.save(link);
    }

    public boolean addLinkWithMeta(int id, String title, String url, String type, ArrayList<Tag> tags, ArrayList<Course> courses, String comment) throws SQLException, IOException {
        String data = getDescription(url);
        Link l = new Link(id, title, url, type, data, tags, courses, comment);
        
        return linkDao.save(l);

    }
    
    public ArrayList<Link> list() throws SQLException {
        return (ArrayList<Link>) linkDao.findAll();
    }

    public String getMeta(String url) {
        try {
            Document document = Jsoup.connect(url).get();
            Elements metaData = document.getElementsByTag("meta");
            return metaData.toString();

        } catch (IOException ex) {
            System.out.println("Check internet connection! " + ex.toString());

        }
        return "";

    }

    public String getDescription(String url) throws IOException {
        Document document = Jsoup.connect(url).get();
        
        try {
            String description = document.select("meta[name=description]").get(0).attr("content");
            return description;
        } catch (IndexOutOfBoundsException ex) {
            System.out.println("Metadata description not found!" );
        }

        return "No metadata description";
    }

    public ArrayList<Link> listLinks() throws SQLException {
        ArrayList<Link> links = (ArrayList<Link>) linkDao.findAll();
        return links;
    }

}
