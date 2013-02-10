package infra.ca

import grails.plugin.spock.IntegrationSpec
import infra.ca.ex.NoTypeStrategyFound
import spock.lang.Stepwise

@Stepwise
class LinkStrategySpec extends IntegrationSpec {

    AtomsManager atomsManager
    def atomRepoService

    def setup() {
    }

    def cleanup() {
    }

    void "ya.ru works ok"() {
        given:
        AtomPush data = atomRepoService.buildPushAtom(
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
        AtomPush data = atomRepoService.buildPushAtom(
                externalUrl: "fuck cfk"
        )
        when:
        atomsManager.build(data)
        then:
        thrown(NoTypeStrategyFound)
    }
}