import java.io.*;
import java.net.*;
import java.util.Date;

public class Main {

    private static final String URL = "ftp://parrocchiadirezzanello:semplicissima@ftp.parrocchiadirezzanello.altervista.org/files/server.txt";

    public static void main(String[] args) throws IOException {

        //Public Private/Public server current IP through FTP protocol (authenticated) on URL
        setRemoteServerIP(URL, getPrivateIP());
        System.out.println(getPublicIP());


        //Open serverSocket
        ServerSocket listener = new ServerSocket(9090);

        //Respond to all clients
        try {
            while (true) {
                Socket socket = listener.accept();
                try {
                    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                    out.println(new Date().toString());
                } finally {
                    socket.close();
                }
            }
        }
        finally {
            listener.close();
        }
    }


    /**
     * Get LAN ip of PC
     * @return local IP of current session
     * @throws UnknownHostException if unable to retrieve IP
     */
    public static String getPrivateIP() throws UnknownHostException{
            String addr = InetAddress.getLocalHost().getHostAddress();
            System.out.println(addr);
            return addr;
    }

    //

    /**
     * PublicIP for RMI/Socket server requires that server isn't behind a NAT
     * or NAT have to direct forward ports
     * @return IP of current sessione
     * @throws IOException if unable to receive response from external services
     * @apiNote It uses "checkip.amazona.com"
     */
    public static String getPublicIP() throws IOException {
        URL whatismyip = new URL("http://checkip.amazonaws.com");
        BufferedReader in = new BufferedReader(new InputStreamReader(
                whatismyip.openStream()));
        String ip = in.readLine();
        return ip;
    }

    public static void setRemoteServerIP(String url, String IP) throws IOException {
        byte[] b = new byte[15];
        //Build string
        for (int i = 0; i < b.length; i++) {
            if (i<IP.length()) b[i]= (byte) IP.charAt(i);
            else b[i]= (byte)'-';
        }
        URL serverURL = new URL(url);
        URLConnection uc = serverURL.openConnection();
        uc.setDoOutput(true);
        OutputStream os = uc.getOutputStream();
        os.write(b);
        os.flush();
        os.close();
    }
}
