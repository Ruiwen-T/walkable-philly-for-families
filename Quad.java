import java.util.Objects;

public class Quad<K, V extends Comparable<V>, P, B> implements Comparable<Quad<K,V,P,B>> {
    K key;
    V value;
    P parent;
    B boolArr;

    public Quad(K key, V value, P parent, B boolArr) {
        this.key = key;
        this.value = value;
        this.parent = parent;
        this.boolArr = boolArr;
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }

    public P getParent() {
        return parent;
    }

    public B getBoolArr() {
        return boolArr;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Quad<?, ?, ?, ?> quad = (Quad<?, ?, ?, ?>) o;
        return Objects.equals(key, quad.key) && Objects.equals(value, quad.value) && Objects.equals(parent, quad.parent) && Objects.equals(boolArr, quad.boolArr);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, value, parent, boolArr);
    }

    @Override
    public int compareTo(Quad<K, V, P, B> o) {
        return value.compareTo(o.getValue());
    }
}
