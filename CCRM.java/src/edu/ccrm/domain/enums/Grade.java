package edu.ccrm.domain.enums;

public enum Grade {
    S(10.0, "S"),
    A(9.0, "A"), 
    B(8.0, "B"), 
    C(7.0, "C"), 
    D(6.0, "D"), 
    E(5.0, "E"), 
    F(0.0, "F"), 
    NOT_GRADED(0.0, "NG");
    
    private final double points;
    private final String symbol;
    
    Grade(double points, String symbol) {
        this.points = points;
        this.symbol = symbol;
    }
    
    public double getPoints() { return points; }
    public String getSymbol() { return symbol; }
    
    public static Grade fromScore(double score) {
        if (score >= 90) return S;
        if (score >= 80) return A;
        if (score >= 70) return B;
        if (score >= 60) return C;
        if (score >= 50) return D;
        if (score >= 40) return E;
        return F;
    }
    
    @Override
    public String toString() {
        return symbol;
    }
}