package infra.ca.strategy

import infra.ca.Atom
import infra.ca.AtomPush
import infra.text.TextCleanService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * @author alari
 * @since 11/18/12 12:43 AM
 */
@Component
class HtmlStrategy extends AtomStrategy {
    @Autowired
    private TextCleanService textCleanService

    @Override
    boolean isContentSupported(AtomPush data) {
        if (data.file) {
            return (data.originalFilename.substring(data.originalFilename.lastIndexOf('.') + 1)) in ["htm", "html"]
        }
        println "checking for html"
        data.text?.size() > 0
    }

    @Override
    void setContent(Atom atom, AtomPush data) {
        println "set content"
        if (data.file) {
            data.text = data.file.text
            if (!atom.title) {
                atom.title = data.originalFilename.substring(0, data.originalFilename.lastIndexOf('.'))
            }
        }
        atom.text = textCleanService.cleanHtml(data.text)
        assert atom
        assert atom.text
    }

    boolean checkLast() {
        true
    }
}
