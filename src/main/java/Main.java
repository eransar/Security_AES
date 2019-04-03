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
        Path k = Paths.get(fh.open_file("key_long").getAbsolutePath());
        Path m = Paths.get(fh.open_file("message_long").getAbsolutePath());
        Path cipherFile = Paths.get(fh.open_file("cipher_long").getAbsolutePath());

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

        /**
         * run AESEncryption
         */
//        AESEncryption e1 = new AESEncryption(keylist.get(0), msglist);
//        List<byte[][]> c1 = e1.init();
//        e1 = new AESEncryption(keylist.get(1), c1);
//        List<byte[][]> c2 = e1.init();
//        e1 = new AESEncryption(keylist.get(2), c2);
//        List<byte[][]> c = e1.init();
//
//        comp1(c, ciflist);
        /**
         * run AESDecryption
         */
//        AESDecryption d1 = new AESDecryption(keylist.get(2),ciflist);
//                List<byte[][]> m3 = d1.init();
//                d1 = new AESDecryption(keylist.get(1),m3);
//                List<byte[][]> m2 = d1.init();
//                d1 = new AESDecryption(keylist.get(0),m2);
//                List<byte[][]> m1 = d1.init();
//        comp1(m1,msglist);


        /**
         * run break
         */
        List<byte[][]> msglist1 = new ArrayList<>();
        for (byte[][] p : msglist) {
            msglist1.add(p.clone());
        }
        List<byte[][]> ciferlist1 = new ArrayList<>(ciflist);
        for (byte[][] p : ciflist) {
            ciferlist1.add(p.clone());
        }
        Hack hack = new Hack(msglist1, ciferlist1);
        List<byte[][]> keys1 = hack.init();
        for (int i = 0; i <keys1.size() ; i++) {
            System.out.println(Arrays.deepToString(keys1.get(i)));
            System.out.println();
        }
        try {
            for (int i = 0; i <keys1.size() ; i++) {
                keys1.set(i,transpose(keys1.get(i)));
            }
            writeTO(keys1,"D:\\key1.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            keys1.clear();
            readTO("D:\\key1.txt",keys1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (int i = 0; i <keys1.size() ; i++) {
            System.out.println(Arrays.deepToString(keys1.get(i)));
            System.out.println();
        }
        AESEncryption e1 = new AESEncryption(keys1.get(0), msglist);
        List<byte[][]> c1 = e1.init();
        e1 = new AESEncryption(keys1.get(1), c1);
        List<byte[][]> c2 = e1.init();
        e1 = new AESEncryption(keys1.get(2), c2);
        List<byte[][]> c = e1.init();

        comp1(ciflist, c);
    }

    private static byte[][] transpose(byte[][] bytes) {
        byte[][] bytes1 = new byte[4][4];
        for (int j = 0; j < bytes.length; j++) {
            for (int k = 0; k < bytes[j].length; k++) {
                bytes1[j][k] = bytes[k][j];
            }
        }
        return bytes1;
    }

    public static void comp1(List<byte[][]> list1, List<byte[][]> list2) {

        for (int i = 0; i < list1.size(); i++) {
            if (Arrays.deepEquals(list1.get(i), list2.get(i)))
                System.out.println("true");
            else
                System.out.println("false");
        }
    }

    public static void writeTO(List<byte[][]> bytes,String path) throws IOException {
        File f = new File(path);
        FileOutputStream out = out = new FileOutputStream(f);
        for (int i = 0; i < bytes.size(); i++) {
            for (int j = 0; j < bytes.get(i).length; j++) {
                for (int k = 0; k < bytes.get(i)[j].length; k++) {
                    out.write(bytes.get(i)[j][k]);
                }
            }

        }
        out.close();
    }

    public static void readTO(String path,List<byte[][]> list) throws URISyntaxException, IOException {
        Path p = Paths.get(new File(path).toURI());
        byte[] keyFIle = Files.readAllBytes(p);
        tolist(keyFIle,list);
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
