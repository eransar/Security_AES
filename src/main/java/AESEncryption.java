import java.util.ArrayList;
import java.util.List;

public class AESEncryption {

    private byte[][] k;

    private List<byte[][]> message;
    private List<byte[][]> encrypted_content;




    public AESEncryption(byte[][] k ,List<byte[][]> message) {

        this.message=message;
        this.k=k;


    }
    public List<byte[][]>  init(){
        shiftRows();
        roundKey();
        return encrypted_content;
    }

    private void shiftRows() {
        for (int i = 0; i <message.size() ; i++) {
            for (int j = 0; j <message.get(0).length ; j++) {
                message.get(i)[j]=shift(j,message.get(i)[j]);
            }
        }
    }

    private void roundKey(){
        List<byte[][]> cypher = new ArrayList<byte[][]>();

        for (int i = 0; i <message.size() ; i++) {
            byte[][] roundKey = new byte[4][4];
            for (int j = 0; j <message.get(0).length ; j++) {
                for (int l = 0; l <message.get(0)[0].length ; l++) {
                    roundKey[j][l]=(byte)(message.get(i)[j][l] ^ k[j][l]);
                }
            }
            cypher.add(roundKey);
        }
        this.encrypted_content=cypher;
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
                    tmp[0]=bytes[1];
                    tmp[1]=bytes[2];
                    tmp[2]=bytes[3];
                    tmp[3]=bytes[0];
                    break;
            case 2:
                tmp[0]=bytes[2];
                tmp[1]=bytes[3];
                tmp[2]=bytes[0];
                tmp[3]=bytes[1];
                break;

            case 3:
                tmp[0]=bytes[3];
                tmp[1]=bytes[0];
                tmp[2]=bytes[1];
                tmp[3]=bytes[2];
                break;
        }

        return tmp;
    }



}

