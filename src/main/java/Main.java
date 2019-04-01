import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) throws URISyntaxException {
        FileHandler fh = new FileHandler();
        Path k = Paths.get(fh.open_file("key_short").getAbsolutePath());
        Path m = Paths.get(fh.open_file("message_short").getAbsolutePath());
//        Path k = Paths.get("C:\\Users\\idanf\\IdeaProjects\\Security_AES\\Resources\\key_short");
//        Path m = Paths.get("C:\\Users\\idanf\\IdeaProjects\\Security_AES\\Resources\\message_long");
        Path cipherFile = Paths.get("C:\\Users\\idanf\\IdeaProjects\\Security_AES\\src\\main\\java\\Resources\\cipher_short");
        byte[][] key = new byte[4][4];
        List<byte[][]> msglist = new ArrayList<byte[][]>();
        List<byte[][]> keylist = new ArrayList<byte[][]>();
        List<byte[][]> ciflist = new ArrayList<byte[][]>();


        try {
            byte[] keybyte = Files.readAllBytes(k);
            byte[] msgbyte = Files.readAllBytes(m);
            byte[] cifbyte = Files.readAllBytes(cipherFile);

            tolist(keybyte, keylist);
            tolist(msgbyte, msglist);
            tolist(cifbyte, ciflist);

        } catch (IOException e) {
            System.out.println(e);
        }

        AESEncryption e1 = new AESEncryption(keylist.get(0), msglist);
        List<byte[][]> c1 = e1.init();
        e1 = new AESEncryption(keylist.get(1), c1);
        List<byte[][]> c2 = e1.init();
        e1 = new AESEncryption(keylist.get(2), c2);
        List<byte[][]> c = e1.init();

        comp1(c, ciflist);

        //AESDecryption d1 = new AESDecryption(keylist.get(2),c);
        //        List<byte[][]> m3 = d1.init();
        //        d1 = new AESDecryption(keylist.get(1),m3);
        //        List<byte[][]> m2 = d1.init();
        //        d1 = new AESDecryption(keylist.get(0),m2);
        //        List<byte[][]> m1 = d1.init();

    }

    public static void comp1(List<byte[][]> list1, List<byte[][]> list2) {

        for (int i = 0; i < list1.size(); i++) {
            if (Arrays.deepEquals(list1.get(i), list2.get(i)))
                System.out.println("true");
            else
                System.out.println("false");
        }
    }

    public static List<byte[][]> tolist(byte[] data, List<byte[][]> list) {
        int counter = 0;
        for (int j = 0; j < data.length / 16; j++) {
            byte[][] msg = new byte[4][4];
            for (int l = 0; l < msg.length; l++) {
                for (int i = 0; i < msg[0].length; i++) {
                    msg[i][l] = data[counter];
                    counter++;
                }

            }
            list.add(msg);
        }
        return list;
    }
}
