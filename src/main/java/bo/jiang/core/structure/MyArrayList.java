package bo.jiang.core.structure;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @author shumpert.jiang
 */
public class MyArrayList<T> implements Iterable<T> {

  private static final int DEFAULT_SIZE = 10;
  private int size;
  private T[] array;

  public MyArrayList() {
    clear();
  }

  public void clear() {
    size = 0;
    ensureCapacity(DEFAULT_SIZE);
  }

  private void ensureCapacity(int defaultSize) {
    if (defaultSize < size) {
      return;
    }
    T[] old = array;
    array = (T[])new Object[defaultSize];
    for (int i = 0; i < size; i++) {
      array[i] = old[i];
    }
  }

  public int size() {
    return size;
  }

  public boolean isEmpty() {
    return size == 0;
  }

  public void trimToSize() {
    ensureCapacity(size);
  }

  public T get(int index) {
    if (index < 0 || index >= size) {
      throw new ArrayIndexOutOfBoundsException();
    }
    return array[index];
  }

  public T set(int index, T val) {
    if (index < 0 || index >= size) {
      throw new ArrayIndexOutOfBoundsException();
    }
    T oldVal = array[index];
    array[index] = val;
    return oldVal;
  }

  public void add(T val) {
    add(size(), val);
  }

  public void add(int index, T val) {
    if (index < 0 || index > size) {
      throw new ArrayIndexOutOfBoundsException();
    }
    if (size() == array.length) {
      ensureCapacity(array.length * 2 + 1);
    }
    for (int i = size; i > index; i--) {
      array[i] = array[i - 1];
    }
    array[index] = val;
    size++;
  }

  public T remove(int index) {
    if (index < 0 || index > size) {
      throw new ArrayIndexOutOfBoundsException();
    }
    T oldVal = array[index];
    for (int i = index; i < size - 1; i++) {
      array[i] = array[i + 1];
    }
    size--;
    return oldVal;
  }

  public Iterator<T> iterator() {
    return new MyIterator();
  }

  private class MyIterator implements Iterator<T> {
    int current = 0;
    public boolean hasNext() {
      return current < size;
    }

    public T next() {
      if (!hasNext()) {
        throw new NoSuchElementException();
      }
      return array[current++];
    }

    public void remove() {
      MyArrayList.this.remove(--current);
    }
  }

}
