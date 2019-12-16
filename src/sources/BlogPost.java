package sources;

import dataBase.Jdbc;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class BlogPost {
    //Jdbc
    private static Jdbc dataBase;
    //Attributes
    private Personne blogger;
    private String title, content;
    private int numPost;
    private String datePost, lastEdited;

    BlogPost(Personne blogger, String title, String content) {
        this.blogger = blogger;
        this.title = title;
        this.content = content;
        this.numPost = nbPosts() + 1;
        this.datePost = getFormattedDate();
        this.lastEdited = getFormattedDate();
    }

    public BlogPost(int numPost, Personne per, String title, String content, String datePost, String lastEdited) {
        this.title = title;
        this.content = content;
        this.numPost = numPost;
        this.blogger = per;
        this.datePost = datePost;
        if(lastEdited == null) {
            this.lastEdited = datePost;
        }else {
            this.lastEdited = lastEdited;
        }
    }

    public static BlogPost add(Personne blogger, String title, String content) {

        BlogPost blogPost = new BlogPost(blogger, title, content);

        String request = "insert into Blog(numPost, userName, title, content, datePost) values(" +
                blogPost.numPost + ", '" + blogger.getUserName() + "','" + title + "','" + content + "','" + blogPost.datePost +"')";

        if(dataBase.insertRequest(request) == 0) {
            return null;
        }
        return blogPost;
    }

    //getters

    public Personne getBlogger() {
        return blogger;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public int getNumPost() {
        return numPost;
    }

    public String getDatePost() {
        return datePost;
    }

    public String getLastEdited() {
        return lastEdited;
    }

    public void editTitle(String newTitle) {
        String request = "Update Blog set title = '" + newTitle + "' AND lastEdited = '" + getFormattedDate() + "'" +
                " where numPost = " + this.numPost + "";
        dataBase.updateRequest(request);
        this.title = newTitle;
        this.lastEdited = getFormattedDate();
    }

    public void editContent(String newContent) {
        String request = "Update Blog set content = '" + newContent + "' AND lastEdited = '" + getFormattedDate() + "'" +
                " where numPost = " + this.numPost + "";
        dataBase.updateRequest(request);
        this.content = newContent;
        this.lastEdited = getFormattedDate();
    }

    public void edit(String newTitle, String newContent) {
        editTitle(newTitle);
        editContent(newContent);
    }

    public void delete() {
        String request = "Delete from Blog where numPost = " + this.numPost + "";
        dataBase.deleteRequest(request);
    }

    //STATIC
    public static void setDataBase(Jdbc db) {
        dataBase = db;
    }

    public static ArrayList<BlogPost> loadBlogPosts(Personne per) {

        String request;
        if(per == null) {
             request = "Select * from Blog";
        }else {
            request = "Select * from Blog where userName = '" + per.getUserName() + "'";
        }

        ResultSet res = dataBase.selectRequest(request);

        ArrayList<BlogPost> posts = new ArrayList<>();

        while (true) {
            try {
                if (!res.next()) break;
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                if(per == null) {
                    String userName = res.getString("userName");
                    String type = User.userType(userName);

                    switch (type.toLowerCase()) {
                        case "etudiant":
                            per = Apprenant.LoadApprenant(userName);
                            break;
                        case "enseignant":
                            per = Instructeur.LoadInstructeur(userName);
                            break;
                    }
                }

                posts.add(new BlogPost(res.getInt("numPost"), per, res.getString("title"), res.getString("content"),
                        res.getString("datePost"), res.getString("lastEdited")));

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return posts;
    }

    public static TableView<BlogPost> blogTable(Personne per) {

        TableColumn<BlogPost, Integer> numPCol = new TableColumn<>("Num Post");
        numPCol.setMinWidth(105);
        numPCol.setCellValueFactory(new PropertyValueFactory<>("numPost"));

        TableColumn<BlogPost, String> titrePCol = new TableColumn<>("Titre Post");
        titrePCol.setMinWidth(290);
        titrePCol.setCellValueFactory(new PropertyValueFactory<>("title"));

        TableColumn<BlogPost, String> datePCol = new TableColumn<>("Date Post");
        datePCol.setMinWidth(230);
        datePCol.setCellValueFactory(new PropertyValueFactory<>("datePost"));

        ObservableList<BlogPost> postsObs = FXCollections.observableArrayList();
        ArrayList<BlogPost> postsArr = loadBlogPosts(per);

        for(BlogPost bp : postsArr) {
            postsObs.add(bp);
        }

        TableView<BlogPost> postsTable = new TableView<>(postsObs);
        postsTable.getColumns().addAll(numPCol, titrePCol, datePCol);


        return postsTable;
    }

    public static int nbPosts() {
        String request = "select max(numPost) from Blog";
        ResultSet res = dataBase.selectRequest(request);
        try{
            if(res.next()){
                return res.getInt(0);
            }
            return 0;
        }catch (SQLException e){return -1;}
    }

    public static String getFormattedDate() {
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
        Date date = new Date(System.currentTimeMillis());

        return formatter.format(date);
    }
}
