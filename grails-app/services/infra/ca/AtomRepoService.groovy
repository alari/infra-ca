package infra.ca

import infra.ca.impl.AtomPOJO
import infra.ca.impl.AtomPOJOPush

class AtomRepoService implements AtomRepo<AtomPOJO,AtomPOJOPush> {
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
