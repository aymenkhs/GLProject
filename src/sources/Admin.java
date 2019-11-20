package sources;

public class Admin extends User {

    public Admin(String userName, String password, String email,Langue lang) {
        super(userName, password, email,lang);
    }

    @Override
    public void edit() {

    }
}
