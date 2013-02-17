package infra.ca.strategy

import infra.ca.Atom
import infra.ca.AtomPush
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements
import org.springframework.stereotype.Component

import javax.imageio.ImageIO
import java.awt.image.BufferedImage

/**
 * @author alari
 * @since 11/19/12 1:00 AM
 */
@Component
class LinkStrategy extends AtomStrategy {
    @Override
    boolean checkLast() {
        true
    }

    @Override
    boolean isContentSupported(AtomPush data) {
        if (data.url && data.url.protocol in ["http", "https"]) {
            HttpURLConnection connection = (HttpURLConnection) data.url.openConnection();
            connection.setRequestMethod("HEAD");
            return connection.getResponseCode() == 200
        }
        false
    }

    @Override
    void setContent(Atom atom, AtomPush data) {
        if (data.url) {
            try {
                final Document document = Jsoup.connect(data.url.toString()).get();

                atom.text = getDetails(document)
                atom.title = getTitle(document)

                // TODO: load and cache image
                atom.images = [remote: getImageUrl(document)]

            } catch (Exception e) {
                return
            }
            atom.externalUrl = data.url.toString()
            atom.externalId = atom.externalUrl.substring(data.url.protocol.size() + 3)
        }
    }

    private String getTitle(final Document document) {
        Elements titles = document.getElementsByTag("meta");
        for (Element e in titles) if (e.attr("name") == "title" && e.attr("content")) {
            return e.attr("content")
        }
        titles = document.getElementsByTag("title")
        if (titles.size()) {
            return titles[0].text()
        }
        ""
    }

    private String getDetails(final Document document) {
        Elements titles = document.getElementsByTag("meta");
        for (Element e in titles) if (e.attr("name") == "description" && e.attr("content")) {
            return e.attr("content")
        }
        return ""
    }

    private String getImageUrl(final Document document) {
        Elements images = document.getElementsByTag("link")
        for (Element im in images) if (im.attr("rel") == "image_src") {
            return im.absUrl("href")
        }

        images = document.body().getElementsByTag("img")
        int i = 0
        for (Element im in images) {
            if (i > 7) break;
            String src = im.absUrl("src")
            if (!src) continue;
            if (!isImageSizeOk(src)) continue;
            return src
        }
        return ""
    }

    private boolean isImageSizeOk(final String absoluteUrl) {
        URL url = new URL(absoluteUrl)
        final BufferedImage bimg = ImageIO.read(url);
        ((bimg.width >= 100 || bimg.height >= 100) && bimg.width >= 34 && bimg.height >= 34)
    }
}
