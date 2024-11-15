// generated with ast extension for cup
// version 0.8
// 26/7/2024 17:2:35


package rs.ac.bg.etf.pp1.ast;

public class FormPar implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    public rs.etf.pp1.symboltable.concepts.Obj obj = null;

    private Type Type;
    private String paramName;
    private FormParsVarPossibility FormParsVarPossibility;

    public FormPar (Type Type, String paramName, FormParsVarPossibility FormParsVarPossibility) {
        this.Type=Type;
        if(Type!=null) Type.setParent(this);
        this.paramName=paramName;
        this.FormParsVarPossibility=FormParsVarPossibility;
        if(FormParsVarPossibility!=null) FormParsVarPossibility.setParent(this);
    }

    public Type getType() {
        return Type;
    }

    public void setType(Type Type) {
        this.Type=Type;
    }

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName=paramName;
    }

    public FormParsVarPossibility getFormParsVarPossibility() {
        return FormParsVarPossibility;
    }

    public void setFormParsVarPossibility(FormParsVarPossibility FormParsVarPossibility) {
        this.FormParsVarPossibility=FormParsVarPossibility;
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
        if(Type!=null) Type.accept(visitor);
        if(FormParsVarPossibility!=null) FormParsVarPossibility.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Type!=null) Type.traverseTopDown(visitor);
        if(FormParsVarPossibility!=null) FormParsVarPossibility.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Type!=null) Type.traverseBottomUp(visitor);
        if(FormParsVarPossibility!=null) FormParsVarPossibility.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("FormPar(\n");

        if(Type!=null)
            buffer.append(Type.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(" "+tab+paramName);
        buffer.append("\n");

        if(FormParsVarPossibility!=null)
            buffer.append(FormParsVarPossibility.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [FormPar]");
        return buffer.toString();
    }
}
