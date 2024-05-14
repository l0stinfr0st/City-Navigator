package GraphProject;

public class HashMap<Key, Value> {

    map[] a;
    int fullness;

    public HashMap(int size) {
        a = new map[size];
    }

    public void put(Key key, Value value) {
        if (key == null) {
            throw new IllegalArgumentException("Key can't be null");
        }
        if ((double) fullness / a.length >= 0.75) {
            resize();
        }
        int index = Math.abs(compress(hash(key), a.length));
        while (a[index] != null && !a[index].key.equals(key)) {
            index = (index + 1) % a.length;
        }
        a[index] = new map(key, value);
        fullness++;
    }

    public void remove(Key key) {
        if (key == null) {
            throw new IllegalArgumentException("Key can't be null");
        }

        int index = Math.abs(compress(hash(key), a.length));
        int startIndex = index;
        while (a[index] != null && !a[index].key.equals(key)) {
            index = (index + 1) % a.length;
            if (index == startIndex) {
                return;
            }
        }
        a[index] = null;
        index = (index + 1) % a.length;
        while (a[index] != null) {
            Key rehashKey = (Key) a[index].key;
            Value rehashValue = (Value) a[index].value;
            a[index] = null;
            put(rehashKey, rehashValue);
            index = (index + 1) % a.length;
        }
    }

    public map search(Key key) {
        if (key == null) {
            throw new IllegalArgumentException("Key can't be null");
        }

        int index = Math.abs(compress(hash(key), a.length));
        int startIndex = index;
        while (a[index] != null && !a[index].key.equals(key)) {
            index = (index + 1) % a.length;
            if (index == startIndex) {
                return null;
            }
        }
        return a[index];
    }
    
  public int searchIndex(Key key) {
    if (key == null) {
        throw new IllegalArgumentException("Key can't be null");
    }
    int index = Math.abs(compress(hash(key), a.length));
    int startIndex = index;
    while (a[index] != null) {
        if (a[index].key.equals(key)) {
            return index; 
        }
        index = (index + 1) % a.length;
        if (index == startIndex) {
            break; 
        }
    }
    return -1; 
}

    
    public String keyAtIndex(int index){
        if(a[index] == null){
            return null;
        }
        return (String)(a[index].key);
    }

    public int compress(int hashCode, int arraySize) {
        return hashCode % arraySize;
    }

    private void resize() {
        int newSize = a.length * 2;
        map[] newArray = new map[newSize];
        for (map<Key, Value> entry : a) {
            if (entry != null) {
                int newIndex = Math.abs(compress(hash(entry.key), newSize));
                while (newArray[newIndex] != null) {
                    newIndex = (newIndex + 1) % newSize;
                }
                newArray[newIndex] = entry;
            }
        }
        a = newArray;
    }

    public int hash(Key key) {
        if (key instanceof String) {
            String s = (String) key;
            int hash = 0;
            for (int i = 0; i < s.length(); i++) {
                hash = 31 * hash + s.charAt(i);
            }
            return hash;
        } else {
            throw new IllegalArgumentException("Key type not supported");
        }
    }

    public static class map<Key, Value> {

        private Key key;
        private Value value;

        public map(Key key, Value value) {
            this.key = key;
            this.value = value;
        }

        public Value getValue() {
            return value;
        }
    }

    public int size() {
        return a.length;
    }
    

}
