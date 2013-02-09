package infra.ca.strategy

import infra.ca.Atom
import infra.ca.AtomPush
import org.springframework.stereotype.Component

/**
 * @author alari
 * @since 11/18/12 12:54 AM
 */
@Component
class RussiaRuStrategy extends AtomStrategy {
    @Override
    boolean isContentSupported(AtomPush data) {
        (data.url && data.url.host in ["russia.ru", "tv.russia.ru", "www.russia.ru"])
    }

    @Override
    void setContent(Atom atom, AtomPush data) {
        //http://russia.ru/video/diskurs_12854/
        // TODO: validate characters in external id!
        atom.externalId = data.url.path.substring(7, data.url.path.size() - 1)
    }
}
