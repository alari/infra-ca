package infra.ca;

import java.io.File;
import java.net.URL;

/**
 * @author alari
 * @since 2/10/13 2:45 AM
 */
public interface AtomPush extends Atom {
    public File getFile();

    public void setFile(File file);

    public String getOriginalFilename();

    public void setOriginalFilename(String originalFilename);


    public URL getUrl();

    public void setUrl(URL url);

    public String getFileType();

    public void setFileType(String fileType);
}
