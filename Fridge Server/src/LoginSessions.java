import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;

public class LoginSessions {
    static LoginSessions instance = null;
    public static LoginSessions getInstance() {
        if (instance == null) {
            instance = new LoginSessions();
        }
        return instance;
    }

    List<Session> sessionList = new LinkedList<Session>();
    Session add(String sesh){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(sesh.substring(2,21), formatter);
        Session session = new Session();
        session.username = sesh.substring(31);
        session.id = Integer.parseInt(sesh.substring(21,31));
        //System.out.println("ID:"+session.id);//LEL
        session.expires = dateTime;
        sessionList.add(session);
        return session;
    }
}
