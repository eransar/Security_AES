import org.apache.commons.cli.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CLI {


    public static void main(String[] args) throws ParseException, URISyntaxException, IOException {
        FileHandler fh = new FileHandler();
        Path m = Paths.get(fh.open_file("cipher_short").getAbsolutePath());
        List<byte[][]> message_check = new ArrayList<byte[][]>();
        tolist(Files.readAllBytes(m),message_check);
        CommandLineParser parser = new DefaultParser();
        Options options = new Options();
        options.addOption("e", false, "instruct the program to encrypt the file");
        options.addOption("d", false, "instruct the program to decrypt the file");
        options.addOption("k", true, "path to the keys");
        options.addOption("i", true, "a file we want to encrypt/decryot");
        options.addOption("o", true, "a path to the output file");
        options.addOption("b", false, "instruct the program to break the encryption algorithm");
        options.addOption("m", true, "denotes the path to the plain-text message ");
        options.addOption("c", true, "denotes the path to the cipher-text message ");



        try {
            CommandLine line = parser.parse(options, args);
            if (line.hasOption("e") && line.hasOption("k") && line.hasOption("i") && line.hasOption("o")) {
                // send to encrypt
                Path key_file = Paths.get(new File(line.getOptionValue("k")).toURI());
                Path input_file = Paths.get(new File(line.getOptionValue("i")).toURI());
                File output = new File(line.getOptionValue("o"));
//                Path output_file = Paths.get(new File(line.getOptionValue("o")).toURI());


                List<byte[][]> msglist = new ArrayList<byte[][]>();
                List<byte[][]> keylist = new ArrayList<byte[][]>();

                byte[] keybyte = Files.readAllBytes(key_file);
                byte[] msgbyte = Files.readAllBytes(input_file);

                tolist(keybyte, keylist);
                tolist(msgbyte, msglist);

                AESEncryption e1 = new AESEncryption(keylist.get(0), msglist);
                List<byte[][]> c1 = e1.init();
                e1 = new AESEncryption(keylist.get(1), c1);
                List<byte[][]> c2 = e1.init();
                e1 = new AESEncryption(keylist.get(2), c2);
                List<byte[][]> cipher = e1.init();
                writeTO(cipher,output.toPath().toString());


            } // encrypt option

            else if (line.hasOption("d") && line.hasOption("k") && line.hasOption("i") && line.hasOption("o")){
                Path key_file = Paths.get(new File(line.getOptionValue("k")).toURI());
                Path cipher_file = Paths.get(new File(line.getOptionValue("i")).toURI());
                File output = new File(line.getOptionValue("o"));
//                Path output_file = Paths.get(new File(line.getOptionValue("o")).toURI());



                List<byte[][]> keylist = new ArrayList<byte[][]>();
                List<byte[][]> ciflist = new ArrayList<byte[][]>();

                byte[] keybyte = Files.readAllBytes(key_file);
                byte[] msgbyte = Files.readAllBytes(cipher_file);

                tolist(keybyte, keylist);
                tolist(msgbyte, ciflist);

                AESDecryption d1 = new AESDecryption(keylist.get(2),ciflist);
                List<byte[][]> m3 = d1.init();
                d1 = new AESDecryption(keylist.get(1),m3);
                List<byte[][]> m2 = d1.init();
                d1 = new AESDecryption(keylist.get(0),m2);
                List<byte[][]> message = d1.init();
                writeTO(message,output.toPath().toString());


            } // decrypt

            else if (line.hasOption("b") && line.hasOption("m") && line.hasOption("c") && line.hasOption("o")){
                Path message_file = Paths.get(new File(line.getOptionValue("m")).toURI());
                Path cipher_file = Paths.get(new File(line.getOptionValue("c")).toURI());
                File output = new File(line.getOptionValue("o"));
//                Path output_file = Paths.get(new File(line.getOptionValue("o")).toURI());



                List<byte[][]> msglist = new ArrayList<byte[][]>();
                List<byte[][]> ciflist = new ArrayList<byte[][]>();

                byte[] cipherbyte = Files.readAllBytes(cipher_file);
                byte[] msgbyte = Files.readAllBytes(message_file);

                tolist(cipherbyte, ciflist);
                tolist(msgbyte, msglist);




                Hack hack = new Hack(msglist, ciflist);
                List<byte[][]> keys1 = hack.init();

                try {
                    for (int i = 0; i <keys1.size() ; i++) {
                        keys1.set(i,transpose(keys1.get(i)));
                    }
                    writeTO(keys1,line.getOptionValue("o"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } // break


        } catch (ParseException | IOException e) {
            e.printStackTrace();
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

    public static void comp1(List<byte[][]> list1, List<byte[][]> list2) {

        for (int i = 0; i < list1.size(); i++) {
            if (Arrays.deepEquals(list1.get(i), list2.get(i)))
                System.out.println("true");
            else
                System.out.println("false");
        }
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


}


