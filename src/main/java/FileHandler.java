import java.io.File;
import java.net.URISyntaxException;

public class FileHandler {


    public FileHandler(){}


    public File open_file(String file_name) throws URISyntaxException {
        return new File(getClass().getClassLoader().getResource(file_name).toURI());
    }
}
