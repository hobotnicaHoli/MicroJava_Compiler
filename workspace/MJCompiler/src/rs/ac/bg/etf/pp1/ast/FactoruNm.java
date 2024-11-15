// generated with ast extension for cup
// version 0.8
// 26/7/2024 17:2:35


package rs.ac.bg.etf.pp1.ast;

public class FactoruNm extends Factor {

    private Integer nVal;

    public FactoruNm (Integer nVal) {
        this.nVal=nVal;
    }

    public Integer getNVal() {
        return nVal;
    }

    public void setNVal(Integer nVal) {
        this.nVal=nVal;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("FactoruNm(\n");

        buffer.append(" "+tab+nVal);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [FactoruNm]");
        return buffer.toString();
    }
}
