package infra.ca

import grails.plugin.spock.IntegrationSpec
import spock.lang.Stepwise

@Stepwise
class RussiaRuSpec extends IntegrationSpec {

    AtomsManager atomsManager
    def atomFactory

    def setup() {
    }

    def cleanup() {
    }

    void "test something"() {
        given:
        AtomPush data = atomFactory.buildPushAtom(
                externalUrl: "http://russia.ru/video/diskurs_12854/"
        )
        when:
        Atom atom = atomsManager.build(data)
        then:
        atom.type == "russiaRu"
        atom.externalId == "diskurs_12854"
    }
}