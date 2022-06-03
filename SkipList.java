import java.lang.reflect.Array;
import java.util.Random;


public class SkipList<K extends Comparable<K>, V> {

    private SkipNode<K, V> head;
    private int level;
    private int size;
    private final Random rnd;


    public SkipList() {
        head = new SkipNode<K, V>(null, null, 0);
        rnd = new Random();
        level = 0;
        size = 0;
    }


    private void adjustHead(int newLevel) {
        SkipNode<K, V> temp = head;
        head = new SkipNode<K, V>(null, null, newLevel);
        for (int i = 0; i <= level; i++)
            head.forward[i] = temp.forward[i];
        level = newLevel;
    }


    public int randomLevel() {
        int lev;
        for (lev = 0; rnd.nextInt(2) == 0; lev++) {
        }
        return lev;
    }


    public void insert(K key, V element) {

        int newLevel = randomLevel();
        if (newLevel > level)
            adjustHead(newLevel);


        @SuppressWarnings("unchecked")
        SkipNode<K, V>[] update = (SkipNode<K, V>[])
                Array.newInstance(SkipNode.class, level + 1);

        SkipNode<K, V> x = head;
        for (int i = level; i >= 0; i--) {
            while ((x.forward[i] != null) &&
                    (key.compareTo(x.forward[i].key()) > 0))
                x = x.forward[i];
            update[i] = x;
        }
        x = new SkipNode<K, V>(key, element, newLevel);
        for (int i = 0; i <= newLevel; i++) {
            x.forward[i] = update[i].forward[i];
            update[i].forward[i] = x;
        }
        size++;
    }


    public SkipNode<K, V> remove(K key) {
        SkipNode<K, V> x = head;
        SkipNode<K, V> y = searchNode(key);
        if (y == null)
            return y;
        SkipNode<K, V>[] replace = y.forward;
        int currentLevel = x.forward.length - 1;
        while (x != null) {

            for (int i = currentLevel; i >= 0; i--) {
                if (x.forward[i] != null) {

                    if ((x.forward[i] == y))
                        x.forward[i] = replace[i];
                }
            }

            x = x.forward[0];
            if (x != null)
                currentLevel = x.forward.length - 1;
        }

        size--;
        return y;
    }


    public SkipNode<K, V> remove(Object remove) {
        SkipNode<K, V> check = head;
        SkipNode<K, V> toRemove = searchNode(remove);
        if (toRemove == null)
            return toRemove;
        SkipNode<K, V>[] replace = toRemove.forward;
        int currentLevel = check.forward.length - 1;
        while (check != null) {

            for (int i = currentLevel; i >= 0; i--) {
                if (check.forward[i] != null) {

                    if ((check.forward[i] == toRemove))
                        check.forward[i] = replace[i];
                }

            }

            check = check.forward[0];
            if (check != null)
                currentLevel = check.forward.length - 1;
        }

        size--;
        return toRemove;
    }


    private SkipNode<K, V> searchNode(K searchKey) {
        SkipNode<K, V> x = head;
        for (int i = level; i >= 0; i--)
            while ((x.forward[i] != null) &&
                    (searchKey.compareTo(x.forward[i].key()) > 0))
                x = x.forward[i];
        x = x.forward[0];
        if ((x != null) && (searchKey.compareTo(x.key()) == 0))
            return x;
        else
            return null;
    }


    private SkipNode<K, V> searchNode(Object find) {
        SkipNode<K, V> node = head;
        int currentLevel = level - 1;


        if (node.forward.length != 1) {
            while (node != null) {


                for (int i = currentLevel; i >= 0; i--) {
                    if (node.forward[i] != null) {

                        if (find.equals(node.forward[i].element())) {
                            node = node.forward[i];
                            return node;
                        }
                    }
                }

                node = node.forward[0];

                if (node != null) {
                    currentLevel = node.forward.length - 1;
                }
            }
        }

        else {
            while (node != null) {
                if (node.forward[0] == null)
                    return null;
                if (find.equals(node.forward[0].element())) {
                    node = node.forward[0];
                    return node;
                }
                node = node.forward[0];

            }
        }
        return null;
    }


    public SkipNode<K, V>[] dump() {
        SkipNode<K, V> temp = head;
        @SuppressWarnings("unchecked")
        SkipNode<K, V>[] list = new SkipNode[1];
        int length = 0;
        while (temp != null) {
            if (length == 0) {
                list[0] = new SkipNode<K, V>(temp.key(), temp.element(),
                        temp.forward.length);
                length++;
            }
            else {
                @SuppressWarnings("unchecked")
                SkipNode<K, V>[] tempArray = new SkipNode[list.length + 1];
                System.arraycopy(list, 0, tempArray, 0, list.length);
                tempArray[tempArray.length - 1] = new SkipNode<K, V>(
                        temp.key(), temp.element(), temp.forward.length);
                list = tempArray;
            }
            temp = temp.forward[0];
        }
        return list;

    }


    public SkipNode<K, V>[] find(K searchKey) {
        @SuppressWarnings("unchecked")
        SkipNode<K, V>[] result = new SkipNode[1];
        SkipNode<K, V> x = head;
        int startLevel = level;
        while (x != null) {
            for (int i = startLevel; i >= 0; i--) {


                try {
                    while ((x.forward[i] != null) &&
                            (searchKey.compareTo(x.forward[i].key()) > 0))
                        x = x.forward[i];
                }
                catch (Exception e) {
                    return result;
                }
            }
            x = x.forward[0];


            if ((x != null) && (searchKey.compareTo(x.key()) == 0)) {
                @SuppressWarnings("unchecked")
                SkipNode<K, V>[] tempArray = new SkipNode[result.length + 1];
                System.arraycopy(result, 0, tempArray, 0, result.length);
                tempArray[tempArray.length - 1] =
                        new SkipNode<K, V>(searchKey, x.element(), 0);
                result = tempArray;
                startLevel = x.forward.length - 1;
            }

        }
        return result;

    }

}