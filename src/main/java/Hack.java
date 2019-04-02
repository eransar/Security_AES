import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Hack {
    private List<byte[][]> msg;
    private List<byte[][]> cipher;
    private List<byte[][]> keys;

    public Hack(List<byte[][]> msg, List<byte[][]> cipher) {
        this.msg = msg;
        this.cipher = cipher;
        keys = new ArrayList<>();
    }

    public List<byte[][]> getKeys() {
        return keys;
    }

    public List<byte[][]> init() {
        hackByRandomKeys();
        return getKeys();
    }

    private void hackByRandomKeys() {
        keys.add(randomKey());
        keys.add(randomKey());
        AESEncryption aesEncryption = new AESEncryption(keys.get(0), msg);
        List<byte[][]> m3 = aesEncryption.init();
        aesEncryption = new AESEncryption(keys.get(1), m3);
        List<byte[][]> m2 = aesEncryption.init();
        generateKey(m2);

    }

    private byte[][] randomKey() {
        Random randomno = new Random();
        byte[][] key_array = new byte[4][4];
        for (int j = 0; j < key_array.length; j++) {
            randomno.nextBytes(key_array[j]);
        }
        return key_array;
    }

    private byte[][] generateKey(List<byte[][]> cifer2) {
        byte[][] genKey = new byte[4][4];
        shiftRows(cifer2);
        desifer(cifer2);
        return genKey;
    }

    private void desifer(List<byte[][]> cifer2) {
        byte[][] roundKey = new byte[4][4];
        for (int j = 0; j < cifer2.get(0).length; j++) {
            for (int l = 0; l < cifer2.get(0)[0].length; l++) {
                roundKey[j][l] = (byte) (cifer2.get(0)[j][l] ^ cipher.get(0)[j][l]);
            }
        }
        keys.add(roundKey);
    }

    private void shiftRows(List<byte[][]> message) {
        for (int i = 0; i < message.size(); i++) {
            for (int j = 0; j < message.get(0).length; j++) {
                message.get(i)[j] = shift(j, message.get(i)[j]);
            }
        }
    }

    private byte[] shift(int i, byte[] bytes) {
        byte[] tmp = new byte[4];
        switch (i) {
            case 0:
                return bytes;
            case 1:
                tmp[0] = bytes[1];
                tmp[1] = bytes[2];
                tmp[2] = bytes[3];
                tmp[3] = bytes[0];
                break;
            case 2:
                tmp[0] = bytes[2];
                tmp[1] = bytes[3];
                tmp[2] = bytes[0];
                tmp[3] = bytes[1];
                break;

            case 3:
                tmp[0] = bytes[3];
                tmp[1] = bytes[0];
                tmp[2] = bytes[1];
                tmp[3] = bytes[2];
                break;
        }

        return tmp;
    }


}
