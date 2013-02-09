package infra.ca.strategy

import infra.ca.Atom

import infra.text.TextProcessService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * @author alari
 * @since 11/18/12 12:43 AM
 */
@Component
class TextStrategy extends AtomStrategy {
    @Autowired private TextProcessService textProcessService

    @Override
    boolean isContentSupported(Atom.Push data) {
        if (data.file) {
            return (data.originalFilename.substring(data.originalFilename.lastIndexOf('.') + 1)) in ["txt", "htm", "html", "md", "markdown"]
        }
        data.text?.size() > 0
    }

    @Override
    void setContent(Atom atom, Atom.Push data) {
        if (data.file) {
            if (data.originalFilename.endsWith("html") || data.originalFilename.endsWith("htm")) {
                data.text = textProcessService.htmlToMarkdown(data.file.text)
            } else {
                data.text = data.file.text
            }
            if (!atom.title) {
                atom.title = data.originalFilename.substring(0, data.originalFilename.lastIndexOf('.'))
            }
        }
        atom.text = data.text
    }

    @Override
    void forRender(Atom atom) {
        atom.text = textProcessService.markdownToHtml(atom.text)
    }
}
