package infra.ca

import grails.plugin.spock.IntegrationSpec
import infra.ca.ex.NoTypeStrategyFound
import infra.ca.impl.AtomPOJO
import spock.lang.Stepwise

@Stepwise
class LinkStrategySpec extends IntegrationSpec {

    infra.ca.AtomsManager atomsManager

    def setup() {
    }

    def cleanup() {
    }

    void "ya.ru works ok"() {
        given:
        infra.ca.Atom.Push data = new infra.ca.impl.AtomPOJO.Push(
                externalUrl: "ya.ru"
        )
        when:
        Atom atom = atomsManager.build(data)
        then:
        atom.type == "link"
        atom.externalId == "ya.ru"
        atom.externalUrl == "http://ya.ru"
    }

    void "wrong url doesnt match anything"() {
        given:
        Atom.Push data = new AtomPOJO.Push(
                externalUrl: "fuck cfk"
        )
        when:
        atomsManager.build(data)
        then:
        thrown(NoTypeStrategyFound)
    }
}