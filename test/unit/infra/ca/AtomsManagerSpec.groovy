package infra.ca

import grails.test.mixin.TestMixin
import grails.test.mixin.support.GrailsUnitTestMixin
import infra.ca.ex.NoTypeStrategyFound
import infra.ca.impl.AtomPOJO
import infra.ca.impl.AtomPOJOPush
import spock.lang.Specification
import spock.lang.Stepwise

/**
 * See the API for {@link grails.test.mixin.support.GrailsUnitTestMixin} for usage instructions
 */
@TestMixin(GrailsUnitTestMixin)
@Stepwise
class AtomsManagerSpec extends Specification {
    AtomsManager atomsManager

    def setup() {
        atomsManager = new AtomsManager()
    }

    def cleanup() {
    }

    void "atoms manager is wired"() {
        expect:
        atomsManager != null
    }

    void "fictive push type fails"() {
        when:
        atomsManager.build(new AtomPOJOPush(type: "fictive"))
        then:
        thrown(NoTypeStrategyFound)
        when:
        atomsManager.build(new AtomPOJOPush())
        then:
        thrown(NoTypeStrategyFound)
        when:
        atomsManager.build(null)
        then:
        thrown(NullPointerException)
    }
}