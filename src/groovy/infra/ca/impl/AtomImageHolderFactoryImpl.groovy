package infra.ca.impl

import infra.ca.Atom
import infra.ca.AtomImageHolderFactory
import infra.file.storage.FilesHolder
import infra.images.annotations.BaseFormat
import infra.images.annotations.Format
import infra.images.annotations.Image
import infra.images.annotations.ImageHolder
import infra.images.util.ImageCropPolicy
import infra.images.util.ImageType
import org.springframework.stereotype.Service

/**
 * @author alari
 * @since 2/18/13 12:46 AM
 */
@Service
class AtomImageHolderFactoryImpl implements AtomImageHolderFactory {
    @Override
    def getHolder(final Atom atom) {
        new Holder(atom.id)
    }

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
                    path = { imagesPath },
                    bucket = { "mirariimages" }
            )
    )
    private class Holder {
        public final String imagesPath

        Holder(String id) {
            imagesPath = "i/".concat(id)
        }
    }
}
