package infra.ca

import grails.plugin.spock.IntegrationSpec

class YouTubeSpec extends IntegrationSpec {

    AtomsManager atomsManager
    def atomRepoService

    void "short link works ok"() {
        given:
        AtomPush data = atomRepoService.buildPushAtom(
                externalUrl: "http://youtu.be/zi3AqicZgEk"
        )
        when:
        Atom atom = atomsManager.build(data)
        then:
        atom.type == "youTube"
        atom.externalId == "zi3AqicZgEk"
    }

    void "long link works ok"() {
        given:
        AtomPush data = atomRepoService.buildPushAtom(
                externalUrl: "http://www.youtube.com/watch?v=zi3AqicZgEk&feature=g-logo&context=G2e33cabFOAAAAAAABAA"
        )
        when:
        Atom atom = atomsManager.build(data)
        then:
        atom.type == "youTube"
        atom.externalId == "zi3AqicZgEk"
    }
}