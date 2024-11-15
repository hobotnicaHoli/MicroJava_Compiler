// generated with ast extension for cup
// version 0.8
// 26/7/2024 17:2:35


package rs.ac.bg.etf.pp1.ast;

public class VarElem implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    public rs.etf.pp1.symboltable.concepts.Obj obj = null;

    private String varName;
    private VarPossibility VarPossibility;

    public VarElem (String varName, VarPossibility VarPossibility) {
        this.varName=varName;
        this.VarPossibility=VarPossibility;
        if(VarPossibility!=null) VarPossibility.setParent(this);
    }

    public String getVarName() {
        return varName;
    }

    public void setVarName(String varName) {
        this.varName=varName;
    }

    public VarPossibility getVarPossibility() {
        return VarPossibility;
    }

    public void setVarPossibility(VarPossibility VarPossibility) {
        this.VarPossibility=VarPossibility;
    }

    public SyntaxNode getParent() {
        return parent;
    }

    public void setParent(SyntaxNode parent) {
        this.parent=parent;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line=line;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(VarPossibility!=null) VarPossibility.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(VarPossibility!=null) VarPossibility.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(VarPossibility!=null) VarPossibility.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("VarElem(\n");

        buffer.append(" "+tab+varName);
        buffer.append("\n");

        if(VarPossibility!=null)
            buffer.append(VarPossibility.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [VarElem]");
        return buffer.toString();
    }
}
