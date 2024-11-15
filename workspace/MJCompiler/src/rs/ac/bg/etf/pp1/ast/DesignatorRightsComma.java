// generated with ast extension for cup
// version 0.8
// 26/7/2024 17:2:35


package rs.ac.bg.etf.pp1.ast;

public class DesignatorRightsComma extends DesignListRight {

    private DesignListRight DesignListRight;

    public DesignatorRightsComma (DesignListRight DesignListRight) {
        this.DesignListRight=DesignListRight;
        if(DesignListRight!=null) DesignListRight.setParent(this);
    }

    public DesignListRight getDesignListRight() {
        return DesignListRight;
    }

    public void setDesignListRight(DesignListRight DesignListRight) {
        this.DesignListRight=DesignListRight;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(DesignListRight!=null) DesignListRight.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(DesignListRight!=null) DesignListRight.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(DesignListRight!=null) DesignListRight.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DesignatorRightsComma(\n");

        if(DesignListRight!=null)
            buffer.append(DesignListRight.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DesignatorRightsComma]");
        return buffer.toString();
    }
}
