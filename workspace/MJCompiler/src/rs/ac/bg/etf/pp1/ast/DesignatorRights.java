// generated with ast extension for cup
// version 0.8
// 26/7/2024 17:2:35


package rs.ac.bg.etf.pp1.ast;

public class DesignatorRights extends DesignListRight {

    private Designator Designator;
    private DesignListRight DesignListRight;

    public DesignatorRights (Designator Designator, DesignListRight DesignListRight) {
        this.Designator=Designator;
        if(Designator!=null) Designator.setParent(this);
        this.DesignListRight=DesignListRight;
        if(DesignListRight!=null) DesignListRight.setParent(this);
    }

    public Designator getDesignator() {
        return Designator;
    }

    public void setDesignator(Designator Designator) {
        this.Designator=Designator;
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
        if(Designator!=null) Designator.accept(visitor);
        if(DesignListRight!=null) DesignListRight.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Designator!=null) Designator.traverseTopDown(visitor);
        if(DesignListRight!=null) DesignListRight.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Designator!=null) Designator.traverseBottomUp(visitor);
        if(DesignListRight!=null) DesignListRight.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DesignatorRights(\n");

        if(Designator!=null)
            buffer.append(Designator.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(DesignListRight!=null)
            buffer.append(DesignListRight.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DesignatorRights]");
        return buffer.toString();
    }
}
