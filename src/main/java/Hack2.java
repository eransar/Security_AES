import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Hack2 {
    private List<byte[][]> msg;
    private List<byte[][]> cipher;
    private List<byte[][]> keys;

    public Hack2(List<byte[][]> msg, List<byte[][]> cipher) {
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

    private byte[][] randomKey() {
        Random randomno = new Random();
        byte[][] key_array = new byte[4][4];
        for (int j = 0; j < key_array.length; j++) {
            randomno.nextBytes(key_array[j]);
        }
        return key_array;
    }

    private void hackByRandomKeys() {
        keys.add(randomKey());
        List<byte[][]> keys1 = new ArrayList<>(keys);
        keys.add(shiftRows(keys1).get(0));
        desifer(shiftRows(shiftRows(shiftRows(msg))));
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

    private List<byte[][]> shiftRows(List<byte[][]> keyNumber) {
        for (int i = 0; i < keyNumber.size(); i++) {
            for (int j = 0; j < keyNumber.get(0).length; j++) {
                keyNumber.get(i)[j] = shift(j, keyNumber.get(i)[j]);
            }
        }
        return keyNumber;
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
