package com.findwise;

import java.util.Objects;

public class IndexEntryImpl implements IndexEntry {

    private String id;
    private double score;

    public IndexEntryImpl(String id, double score) {
        this.id = id;
        this.score = score;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public double getScore() {
        return score;
    }

    @Override
    public void setScore(double score) {
        this.score = score;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof IndexEntryImpl) {
            return Objects.equals(id, ((IndexEntryImpl) obj).getId());
        }

        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
