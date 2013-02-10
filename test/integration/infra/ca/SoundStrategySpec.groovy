package infra.ca

import grails.plugin.spock.IntegrationSpec
import org.apache.commons.lang.RandomStringUtils
import org.springframework.core.io.ClassPathResource
import spock.lang.Shared
import spock.lang.Stepwise

@Stepwise
class SoundStrategySpec extends IntegrationSpec {

    AtomsManager atomsManager
    def atomFactory

    @Shared File soundFile
    @Shared String id = RandomStringUtils.randomAlphanumeric(5)

    def setupSpec() {
        soundFile = new ClassPathResource("sound.mp3", this.class).getFile();
    }

    void "can upload a sound"() {
        given:
        AtomPush data = atomFactory.buildPushAtom(
                file: soundFile,
                id: id,
                originalFilename: "test.mp3"
        )
        atomsManager.prepareExtendedInfo(data)
        if (data.fileType != "audio/mpeg") {
            println "Ubuntu 11.04 has known bug with determining mime type"
            data.fileType = "audio/mpeg"
        }

        when:
        Atom atom = atomsManager.build(data)
        then:
        atom.type == "sound"
        atom.title == "test"
        atom.id == id
        atom.sounds == [mpeg: "sound.mp3"]
    }

    void "can delete a sound"() {
        given:
        Atom atom = atomFactory.buildAtom(id: id, sounds: [mpeg: "sound.mp3"], type: "sound")
        when:
        atomsManager.delete(atom)
        then:
        atom.sounds == null
    }
}