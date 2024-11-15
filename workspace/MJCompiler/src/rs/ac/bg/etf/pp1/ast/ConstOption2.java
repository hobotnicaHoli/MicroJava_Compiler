// generated with ast extension for cup
// version 0.8
// 26/7/2024 17:2:35


package rs.ac.bg.etf.pp1.ast;

public class ConstOption2 extends ConstOption {

    private Character cVal;

    public ConstOption2 (Character cVal) {
        this.cVal=cVal;
    }

    public Character getCVal() {
        return cVal;
    }

    public void setCVal(Character cVal) {
        this.cVal=cVal;
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
        buffer.append("ConstOption2(\n");

        buffer.append(" "+tab+cVal);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ConstOption2]");
        return buffer.toString();
    }
}
