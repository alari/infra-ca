package infra.ca.strategy

import infra.ca.Atom
import infra.ca.AtomImageHolderFactory
import infra.ca.AtomPush
import infra.images.ImageManager
import infra.images.ImagesService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * @author alari
 * @since 11/19/12 12:32 AM
 */
@Component
class ImageStrategy extends AtomStrategy {
    @Autowired
    private ImagesService imagesService

    @Autowired
    private AtomImageHolderFactory atomImageHolderFactory

    private ImageManager getImageManager(final Atom atom) {
        imagesService.getImageManager(atomImageHolderFactory.getHolder(atom))
    }

    @Override
    boolean isContentSupported(AtomPush data) {
        (data.file && data.fileType.startsWith("image/"))
    }

    @Override
    void setContent(Atom atom, AtomPush data) {
        if (data.file) {
            atom.title = data.originalFilename.substring(0, data.originalFilename.lastIndexOf("."))
            atom.images = [:]

            ImageManager manager = getImageManager(atom)

            manager.store(data.file)
            manager.formatsBundle.formats.keySet().each {
                atom.images.put(it, manager.getSrc(it))
            }
        }
    }

    @Override
    void delete(Atom atom) {
        getImageManager(atom).delete()
        atom.images = null
    }
}
