package ua.yandex.shad.tries;

public class Tuple {
    public final String term;
    public final int weight;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tuple tuple = (Tuple) o;

        if (weight != tuple.weight) return false;
        return !(term != null ? !term.equals(tuple.term) : tuple.term != null);

    }

    @Override
    public int hashCode() {
        int result = term != null ? term.hashCode() : 0;
        result = 31 * result + weight;
        return result;
    }

    public Tuple(String term, int weight) {
        this.term = term;
        this.weight = weight;
    }
}
