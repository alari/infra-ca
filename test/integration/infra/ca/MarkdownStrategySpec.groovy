package infra.ca

import grails.plugin.spock.IntegrationSpec
import spock.lang.Stepwise

@Stepwise
class MarkdownStrategySpec extends IntegrationSpec {

    AtomsManager atomsManager
    def atomFactory

    void "is autowired"() {
        expect:
        atomsManager != null
    }

    void "markdown type is ok"() {
        given:
        AtomPush data = atomFactory.buildPushAtom(
                title: "test text",
                text: "a"
        )
        when:
        Atom atom = atomsManager.build(data)
        then:
        atom.type == "markdown"
        atom.title == data.title
        atom.text == "a"
        when:
        atomsManager.forRender(atom)
        then:
        atom.text == "<p>a</p>"
    }
}