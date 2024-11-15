// generated with ast extension for cup
// version 0.8
// 26/7/2024 17:2:35


package rs.ac.bg.etf.pp1.ast;

public class DesignStmt1 extends DesignatorStatement {

    private Designator Designator;
    private DesStmtOptions DesStmtOptions;

    public DesignStmt1 (Designator Designator, DesStmtOptions DesStmtOptions) {
        this.Designator=Designator;
        if(Designator!=null) Designator.setParent(this);
        this.DesStmtOptions=DesStmtOptions;
        if(DesStmtOptions!=null) DesStmtOptions.setParent(this);
    }

    public Designator getDesignator() {
        return Designator;
    }

    public void setDesignator(Designator Designator) {
        this.Designator=Designator;
    }

    public DesStmtOptions getDesStmtOptions() {
        return DesStmtOptions;
    }

    public void setDesStmtOptions(DesStmtOptions DesStmtOptions) {
        this.DesStmtOptions=DesStmtOptions;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Designator!=null) Designator.accept(visitor);
        if(DesStmtOptions!=null) DesStmtOptions.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Designator!=null) Designator.traverseTopDown(visitor);
        if(DesStmtOptions!=null) DesStmtOptions.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Designator!=null) Designator.traverseBottomUp(visitor);
        if(DesStmtOptions!=null) DesStmtOptions.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DesignStmt1(\n");

        if(Designator!=null)
            buffer.append(Designator.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(DesStmtOptions!=null)
            buffer.append(DesStmtOptions.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DesignStmt1]");
        return buffer.toString();
    }
}
