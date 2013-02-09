package infra.ca.strategy

import infra.ca.Atom
import infra.ca.AtomPush

/**
 * @author alari
 * @since 11/13/12 11:42 PM
 */
abstract class AtomStrategy {
    abstract boolean isContentSupported(final AtomPush data);

    private volatile String name;

    final public String getName() {
        if (name == null) {
            synchronized (this) {
                if (name == null) {
                    name = getClass().simpleName - "Strategy"
                    name = name.charAt(0).toLowerCase().toString() + name.substring(1)
                }
            }
        }
        name
    }

    public boolean checkLast() {
        false
    }

    final void update(Atom atom, final AtomPush data) {
        if (atom.id && data.id && data.id != atom.id) {
            throw new IllegalAccessException("Trying to set data to an atom with different id")
        }
        if (data.id) atom.id = data.id
        atom.title = data.title
        atom.lastUpdated = new Date()
        setContent(atom, data)
    }

    abstract void setContent(Atom atom, final AtomPush data);

    void forUpdate(Atom atom) {}

    void forRender(Atom atom) {}

    void delete(Atom atom) {}
}
