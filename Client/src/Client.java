import javax.swing.*;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;

public class Client {

    private static final String URL = "ftp://<USR>:>PASSWD>@ftp.<HOST>/files/server.txt";

    public static void main(String[] args) throws IOException {

        String remoteIP = getRemoteServerIP(URL);
        System.out.println(remoteIP);
        Socket s = new Socket(InetAddress.getByName(remoteIP), 9090);

        //Receives DateTime from server and print it
        BufferedReader input =
                new BufferedReader(new InputStreamReader(s.getInputStream()));
        String answer = input.readLine();
        JOptionPane.showMessageDialog(null, answer);
        System.exit(0);
    }

    public static String getRemoteServerIP(String url) throws IOException {
        byte[] b = new byte[15];
        URL serverURL = new URL(url);
        InputStream is = serverURL.openStream();
        BufferedInputStream bis = new BufferedInputStream(is);
        bis.read(b, 0, 15);
        return new String(b).split("-")[0];
    }
}
