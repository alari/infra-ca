package infra.ca.strategy

import infra.ca.Atom
import infra.ca.AtomPush
import infra.file.storage.FileStorageService
import infra.file.storage.FilesManager
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * @author alari
 * @since 11/18/12 10:36 PM
 */
@Component
class SoundStrategy extends AtomStrategy {
    private final Map<String, String> TYPES = [
            mpeg: "sound.mp3",
            webm: "sound.webm",
            ogg: "sound.ogg"
    ]

    @Autowired
    FileStorageService fileStorageService
    String filesBucket = "mirarisounds"

    private FilesManager getFilesManager(final Atom atom) {
        FilesManager manager = fileStorageService.getManager(atom.id, filesBucket, false)
        if (atom.sounds) manager.fileNames = atom.sounds.values()
        manager
    }

    @Override
    boolean isContentSupported(AtomPush data) {
        if (!data.file) return false
        assert data.fileType != null
        if (!data.fileType.startsWith("audio/")) return false
        for (t in TYPES.keySet()) {
            if (data.fileType.endsWith("/${t}")) return true
        }
        false
    }

    @Override
    void setContent(Atom atom, AtomPush data) {
        if (data.file) {
            String type = data.fileType.substring(data.fileType.indexOf("/") + 1)
            String fileName = TYPES.get(type)
            if (!fileName) throw new IllegalArgumentException("Wrong data object given")

            getFilesManager(atom).store(data.file, fileName)

            if (!atom.sounds) atom.sounds = [:]
            atom.sounds.put(type, fileName)
            atom.title = data.originalFilename.substring(0, data.originalFilename.lastIndexOf("."))
        }
    }

    @Override
    void delete(Atom atom) {
        if (atom.sounds && atom.sounds.size()) {
            getFilesManager(atom).delete()
            atom.sounds = null
        }
    }
}
