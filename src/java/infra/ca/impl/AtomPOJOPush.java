package infra.ca.impl;

import infra.ca.AtomPush;

import java.io.File;
import java.net.URL;

/**
 * @author alari
 * @since 2/10/13 2:48 AM
 */
public class AtomPOJOPush extends AtomPOJO implements AtomPush {
    private File file;
    private String originalFilename;

    private URL url;
    private String fileType;

    public String toString() {
        return "Atom.Push:" + this.getId();
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getOriginalFilename() {
        return originalFilename;
    }

    public void setOriginalFilename(String originalFilename) {
        this.originalFilename = originalFilename;
    }

    public URL getUrl() {
        return url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }
}
