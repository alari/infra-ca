package infra.ca

import grails.plugin.spock.IntegrationSpec
import spock.lang.Stepwise

@Stepwise
class TextStrategySpec extends IntegrationSpec {

    AtomsManager atomsManager
    def atomRepoService

    def setup() {
    }

    def cleanup() {
    }

    void "is autowired"() {
        expect:
        atomsManager != null
    }

    void "text type is ok"() {
        given:
        AtomPush data = atomRepoService.buildPushAtom(
                title: "test text",
                text: "a"
        )
        when:
        Atom atom = atomsManager.build(data)
        then:
        atom.type == "text"
        atom.title == data.title
        atom.text == "a"
        when:
        atomsManager.forRender(atom)
        then:
        atom.text == "<p>a</p>"
    }
}