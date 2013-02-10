package infra.ca.impl

import groovy.transform.TypeChecked
import infra.ca.AtomFactory

/**
 * @author alari
 * @since 2/10/13 10:26 PM
 */
class AtomFactoryImpl implements AtomFactory<AtomPOJO,AtomPOJOPush> {
    @Override
    AtomPOJOPush buildPushAtom() {
        new AtomPOJOPush()
    }

    @Override
    AtomPOJOPush buildPushAtom(Map props) {
        new AtomPOJOPush(props)
    }

    @Override
    AtomPOJO buildAtom() {
        new AtomPOJO()
    }

    @Override
    AtomPOJO buildAtom(Map props) {
        new AtomPOJO(props)
    }
}
