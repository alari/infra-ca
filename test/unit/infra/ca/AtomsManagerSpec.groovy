package infra.ca

import grails.test.mixin.TestMixin
import grails.test.mixin.support.GrailsUnitTestMixin
import infra.ca.ex.NoTypeStrategyFound
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Stepwise

/**
 * See the API for {@link grails.test.mixin.support.GrailsUnitTestMixin} for usage instructions
 */
@TestMixin(GrailsUnitTestMixin)
@Stepwise
class AtomsManagerSpec extends Specification {
    @Shared AtomsManager atomsManager
    @Shared AtomRepo atomRepoService

    def setupSpec() {
        atomsManager = new AtomsManager()
        atomRepoService = new AtomRepoService()
        atomsManager.atomRepoService = atomRepoService
    }

    void "atoms manager is wired"() {
        expect:
        atomsManager != null
    }

    void "fictive push type fails"() {
        when:
        atomsManager.build(atomRepoService.buildPushAtom(type: "fictive"))
        then:
        thrown(NoTypeStrategyFound)
        when:
        atomsManager.build(atomRepoService.buildPushAtom())
        then:
        thrown(NoTypeStrategyFound)
        when:
        atomsManager.build(null)
        then:
        thrown(NullPointerException)
    }
}