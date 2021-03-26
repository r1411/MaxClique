package me.r1411.maxcliques.objects;

import java.util.Objects;

public class Vertex {
    private int index;

    public Vertex(int index) {
        this.index = index;
    }

    public Vertex(int index, int color) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vertex vertex = (Vertex) o;
        return getIndex() == vertex.getIndex();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIndex());
    }

    @Override
    public String toString() {
        return String.valueOf(this.getIndex());
    }

}
