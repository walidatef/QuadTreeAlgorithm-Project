
public class SkipNode<K, V> {

    V element;

    K key;

    public SkipNode<K, V>[] forward;


    public K key() {
        return key;
    }


    public V element() {
        return element;
    }


    @SuppressWarnings("unchecked")
    public SkipNode(K key, V element, int level) {
        this.key = key;
        this.element = element;
        forward = (SkipNode<K, V>[]) new SkipNode[level + 1];
        for (int i = 0; i < level; i++)
            forward[i] = null;
    }



}