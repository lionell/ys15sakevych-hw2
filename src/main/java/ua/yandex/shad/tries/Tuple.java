package ua.yandex.shad.tries;

public class Tuple {
    private final String term;
    private final int weight;

    public Tuple(String term, int weight) {
        this.term = term;
        this.weight = weight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Tuple tuple = (Tuple) o;

        if (weight != tuple.weight) {
            return false;
        }
        if (term != null) {
            return term.equals(tuple.term);
        } else {
            return tuple.term == null;
        }
    }

    @Override
    public int hashCode() {
        int result = 0;
        if (term != null) {
            result = term.hashCode();
        }
        result = 31 * result + weight;
        return result;
    }

    public String term() {
        return term;
    }

    public int weight() {
        return weight;
    }
}
