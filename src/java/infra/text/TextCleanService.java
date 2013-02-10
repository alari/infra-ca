package infra.text;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.springframework.stereotype.Service;

/**
 * @author alari
 * @since 2/10/13 10:39 PM
 */
@Service
public class TextCleanService {
    public String cleanHtml(String unsafe) {
        return unsafe == null || unsafe.isEmpty() ? "" : Jsoup.clean(unsafe, Whitelist.basic());
    }
}
