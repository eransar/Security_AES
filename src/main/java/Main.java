import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main (String[] args)  {
        Path k = Paths.get("C:\\Users\\eransar\\IdeaProjects\\SecurityAES3decrypt\\Resources\\key_short");
        Path m = Paths.get("C:\\Users\\eransar\\IdeaProjects\\SecurityAES3decrypt\\Resources\\message_long");
        byte[][] key = new byte[4][4];
        List<byte[][]> msglist = new ArrayList<byte[][]>();
        List<byte[][]> keylist = new ArrayList<byte[][]>();

        try {
            byte[] keybyte = Files.readAllBytes(k);
            byte[] msgbyte = Files.readAllBytes(m);
//            for (int i = 0; i <key.length ; i++) {
//                for (int j = 0; j <key[0].length ; j++) {
//                    key[i][j]=keybyte[i*4+j];
//
//                }
//            }
        tolist(keybyte,keylist);
        tolist(msgbyte,msglist);


            key=key;
        } catch (IOException e) {
            System.out.println(e);
        }
        AESEncryption e1 = new AESEncryption(keylist.get(0),msglist);
        List<byte[][]> c1 = e1.init();
        e1 = new AESEncryption(keylist.get(1),c1);
        List<byte[][]> c2 = e1.init();
        e1 = new AESEncryption(keylist.get(2),c2);
        List<byte[][]> c = e1.init();


    }

    public static List<byte[][]> tolist(byte[] data, List<byte[][]> list){
        int counter =0;
        for (int j = 0; j < data.length/16  ; j++) {
            byte[][] msg = new byte[4][4];
            for (int l = 0; l < msg.length; l++) {
                for (int i = 0; i <msg[0].length ; i++) {
                    msg[l][i]=data[counter];
                    counter++;
                }

            }
            list.add(msg);
        }
        return list;
    }
}
