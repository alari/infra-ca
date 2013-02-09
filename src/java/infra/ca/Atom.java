package infra.ca;

import java.util.Date;
import java.util.Map;

/**
 * @author alari
 * @since 11/13/12 11:41 PM
 */
public interface Atom extends StringIdContainer {
    static final String TEXT_MAIN = "main";

    public String getTitle();

    public void setTitle(String title);


    public String getType();

    public void setType(String type);

    public Map<String, String> getTexts();

    public String getText();

    public void setText(String text);

    public void setTexts(Map<String, String> texts);

    public String getExternalId();

    public void setExternalId(String externalId);

    public Map<String, String> getSounds();

    public void setSounds(Map<String, String> sounds);

    public Map<String, String> getImages();

    public void setImages(Map<String, String> images);

    public Date getDateCreated();

    public void setDateCreated(Date dateCreated);

    public Date getLastUpdated();

    public void setLastUpdated(Date lastUpdated);

    public String getExternalUrl();

    public void setExternalUrl(String externalUrl);
}
