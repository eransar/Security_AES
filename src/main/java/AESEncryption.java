import java.util.ArrayList;
import java.util.List;

public class AESEncryption {

    private byte[] k;

    private List<byte[][]> message;




    public AESEncryption(byte[] k ,List<byte[][]> message) {
        message=new ArrayList<byte[][]>();
        this.message=message;
        k = new byte[128];
        this.k=k;


    }

    private void shiftRows() {
        for (int i = 0; i <message.size() ; i++) {
            for (int j = 0; j <message.get(0).length ; j++) {
                shift(j,message.get(i)[j]);
            }
        }
    }

    private void roundKey(){}



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

            case 2:
                tmp[0]=bytes[2];
                tmp[1]=bytes[3];
                tmp[2]=bytes[0];
                tmp[3]=bytes[1];


            case 3:
                tmp[0]=bytes[3];
                tmp[1]=bytes[0];
                tmp[2]=bytes[1];
                tmp[3]=bytes[2];
        }

        return tmp;
    }



}

