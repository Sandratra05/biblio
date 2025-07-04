package com.springjpa.entity;

import java.io.Serializable;
import java.util.Objects;

public class ProlongementStatutId implements Serializable {
    private int prolongement;
    private int statutProlongement;

    public ProlongementStatutId() {}

    public ProlongementStatutId(int prolongement, int statutProlongement) {
        this.prolongement = prolongement;
        this.statutProlongement = statutProlongement;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProlongementStatutId)) return false;
        ProlongementStatutId that = (ProlongementStatutId) o;
        return prolongement == that.prolongement &&
               statutProlongement == that.statutProlongement;
    }

    @Override
    public int hashCode() {
        return Objects.hash(prolongement, statutProlongement);
    }
}
