package infra.ca.strategy

import infra.ca.Atom
import infra.ca.AtomPush
import infra.text.MarkdownService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * @author alari
 * @since 11/18/12 12:43 AM
 */
@Component
class MarkdownStrategy extends AtomStrategy {
    @Autowired private MarkdownService markdownService

    @Override
    boolean isContentSupported(AtomPush data) {
        if (data.file) {
            return (data.originalFilename.substring(data.originalFilename.lastIndexOf('.') + 1)) in ["txt", "md", "markdown"]
        }
        data.text?.size() > 0
    }

    @Override
    void setContent(Atom atom, AtomPush data) {
        if (data.file) {
            data.text = data.file.text
            if (!atom.title) {
                atom.title = data.originalFilename.substring(0, data.originalFilename.lastIndexOf('.'))
            }
        }
        atom.text = data.text
    }

    @Override
    void forRender(Atom atom) {
        atom.text = markdownService.markdownToHtml(atom.text)
    }
}
