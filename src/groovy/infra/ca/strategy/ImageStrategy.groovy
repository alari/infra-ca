package infra.ca.strategy

import infra.ca.AtomPush
import infra.file.storage.FilesHolder
import infra.images.ImageManager
import infra.images.ImagesService
import infra.images.annotations.BaseFormat
import infra.images.annotations.Format
import infra.images.annotations.Image
import infra.images.annotations.ImageHolder
import infra.images.util.ImageCropPolicy
import infra.images.util.ImageType
import infra.ca.Atom

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


    @ImageHolder(
            image = @Image(
                    name = "atom",
                    baseFormat = @BaseFormat(type = ImageType.JPG, crop = ImageCropPolicy.CENTER),
                    formats = [
                            @Format(name = "max", width = 1920, height = 1920, crop = ImageCropPolicy.NONE),
                    @Format(name = "standard", width = 980, height = 750, crop = ImageCropPolicy.NONE),

                    @Format(name = "medium", width = 180, height = 180),
                    @Format(name = "small", width = 120, height = 120),
                    @Format(name = "thumb", width = 90, height = 90),
                    ]
            ),
    filesHolder = @FilesHolder(
            path = {imagesPath},
            bucket = {"mirariimages"}
    )
    )
    private class AtomImageHolder {
        public final String imagesPath

        AtomImageHolder(String id) {
            imagesPath = "i/".concat(id)
        }

    }

    private ImageManager getImageManager(final Atom atom) {
        imagesService.getImageManager(new AtomImageHolder(atom.id))
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
