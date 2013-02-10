package infra.ca;

import java.util.Map;

/**
 * @author alari
 * @since 2/10/13 7:17 PM
 */
public interface AtomFactory<A extends Atom, P extends AtomPush> {
    public P buildPushAtom();

    public P buildPushAtom(Map props);

    public A buildAtom();

    public A buildAtom(Map props);
}
