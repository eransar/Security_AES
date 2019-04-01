import java.util.ArrayList;
import java.util.List;

public class AESDecryption {

    private byte[][] k;

    private List<byte[][]> cipher;
    private List<byte[][]> decrypted_content;

    public AESDecryption(byte[][] k, List<byte[][]> cipher) {
        this.k = k;
        this.cipher = cipher;
    }

    public List<byte[][]>  init(){
        shiftRows();
        roundKey();
        return decrypted_content;
    }

    private void shiftRows() {
        for (int i = 0; i < cipher.size() ; i++) {
            for (int j = 0; j < cipher.get(0).length ; j++) {
                cipher.get(i)[j]=shift(j, cipher.get(i)[j]);
            }
        }
    }

    private void roundKey(){
        List<byte[][]> cypher = new ArrayList<byte[][]>();

        for (int i = 0; i <cipher.size() ; i++) {
            byte[][] roundKey = new byte[4][4];
            for (int j = 0; j < cipher.get(0).length; j++) {
                for (int l = 0; l < cipher.get(0)[0].length; l++) {
                    roundKey[j][l] = (byte) (cipher.get(i)[j][l] ^ k[j][l]);
                }
            }
            cypher.add(roundKey);
        }
        this.decrypted_content=cypher;
    }


    /**
     *
     * @param i
     * @param bytes
     */
    private byte[] shift(int i, byte[] bytes) {
        byte[] tmp = new byte[4];
        switch(i){
            case 0: return bytes;
            case 1:
                tmp[0]=bytes[3];
                tmp[1]=bytes[0];
                tmp[2]=bytes[1];
                tmp[3]=bytes[2];
                break;
            case 2:
                tmp[0]=bytes[2];
                tmp[1]=bytes[3];
                tmp[2]=bytes[0];
                tmp[3]=bytes[1];
                break;

            case 3:
                tmp[0]=bytes[1];
                tmp[1]=bytes[2];
                tmp[2]=bytes[3];
                tmp[3]=bytes[0];
                break;
        }

        return tmp;
    }
}
