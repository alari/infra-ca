package infra.ca

import grails.plugin.spock.IntegrationSpec
import infra.ca.ex.NoTypeStrategyFound
import infra.ca.impl.AtomPOJO
import infra.ca.impl.AtomPOJOPush
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
        AtomPush data = new AtomPOJOPush(
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
        AtomPush data = new AtomPOJOPush(
                externalUrl: "fuck cfk"
        )
        when:
        atomsManager.build(data)
        then:
        thrown(NoTypeStrategyFound)
    }
}