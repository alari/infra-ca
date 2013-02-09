package infra.ca

import grails.plugin.spock.IntegrationSpec
import infra.ca.impl.AtomPOJO
import infra.ca.impl.AtomPOJOPush
import spock.lang.Stepwise

@Stepwise
class RussiaRuSpec extends IntegrationSpec {

    AtomsManager atomsManager

    def setup() {
    }

    def cleanup() {
    }

    void "test something"() {
        given:
        AtomPush data = new AtomPOJOPush(
                externalUrl: "http://russia.ru/video/diskurs_12854/"
        )
        when:
        Atom atom = atomsManager.build(data)
        then:
        atom.type == "russiaRu"
        atom.externalId == "diskurs_12854"
    }
}