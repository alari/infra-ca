package infra.ca

import grails.plugin.spock.IntegrationSpec
import spock.lang.Ignore
import spock.lang.Stepwise

@Stepwise
class HtmlStrategySpec extends IntegrationSpec {

    AtomsManager atomsManager
    def atomFactory

    void "is autowired"() {
        expect:
        atomsManager != null
    }

    void "html type is ok"() {
        given:
        AtomPush data = atomFactory.buildPushAtom(
                title: "test text",
                text: "<p>a</p>",
                type: "html"
        )
        when:
        Atom atom = atomsManager.build(data)
        then:
        atom.type == "html"
        atom.title == data.title
        atom.text == "<p>a</p>"
        when:
        atomsManager.forRender(atom)
        then:
        atom.text == "<p>a</p>"
    }
}