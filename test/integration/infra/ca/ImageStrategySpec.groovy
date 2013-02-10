package infra.ca

import grails.plugin.spock.IntegrationSpec
import org.apache.commons.lang.RandomStringUtils
import org.springframework.core.io.ClassPathResource
import spock.lang.Shared
import spock.lang.Stepwise

@Stepwise
class ImageStrategySpec extends IntegrationSpec {

    AtomsManager atomsManager
    def atomFactory

    @Shared File imageFile
    @Shared String id = RandomStringUtils.randomAlphanumeric(5)

    def setupSpec() {
        imageFile = new ClassPathResource("image.jpg", this.class).getFile();
    }


    void "can upload an image"() {
        given:
        AtomPush data = atomFactory.buildPushAtom(
                file: imageFile,
                id: id,
                originalFilename: "test.jpg"
        )
        atomsManager.prepareExtendedInfo(data)
        if (data.fileType != "image/jpeg") {
            println "Ubuntu 11.04 has known bug with determining mime type"
            data.fileType = "image/jpeg"
        }

        when:
        Atom atom = atomsManager.build(data)
        then:
        atom.type == "image"
        atom.title == "test"
        atom.id == id
        atom.images.size() > 1
    }

    void "can delete an image"() {
        given:
        Atom atom = atomFactory.buildAtom(id: id, type: "image")
        when:
        atomsManager.delete(atom)
        then:
        atom.images == null
    }
}