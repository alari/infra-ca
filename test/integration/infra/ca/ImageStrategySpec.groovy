package infra.ca

import grails.plugin.spock.IntegrationSpec
import infra.ca.impl.AtomPOJOPush
import org.apache.commons.lang.RandomStringUtils
import org.springframework.core.io.ClassPathResource
import infra.ca.impl.AtomPOJO
import spock.lang.Stepwise

@Stepwise
class ImageStrategySpec extends IntegrationSpec {

    AtomsManager atomsManager

    File imageFile
    String id = RandomStringUtils.randomAlphanumeric(5)

    def setup() {
        imageFile = new ClassPathResource("image.jpg", this.class).getFile();
    }


    def cleanup() {
    }

    void "can upload an image"() {
        given:
        AtomPush data = new AtomPOJOPush(
                file: imageFile,
                id: id,
                originalFilename: "test.jpg"
        )
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
        Atom atom = new AtomPOJO(id: id, type: "image")
        when:
        atomsManager.delete(atom)
        then:
        atom.images == null
    }
}