// generated with ast extension for cup
// version 0.8
// 26/7/2024 17:2:34


package rs.ac.bg.etf.pp1.ast;

public class Declarations extends DeclarationList {

    private Declaration Declaration;
    private DeclarationList DeclarationList;

    public Declarations (Declaration Declaration, DeclarationList DeclarationList) {
        this.Declaration=Declaration;
        if(Declaration!=null) Declaration.setParent(this);
        this.DeclarationList=DeclarationList;
        if(DeclarationList!=null) DeclarationList.setParent(this);
    }

    public Declaration getDeclaration() {
        return Declaration;
    }

    public void setDeclaration(Declaration Declaration) {
        this.Declaration=Declaration;
    }

    public DeclarationList getDeclarationList() {
        return DeclarationList;
    }

    public void setDeclarationList(DeclarationList DeclarationList) {
        this.DeclarationList=DeclarationList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Declaration!=null) Declaration.accept(visitor);
        if(DeclarationList!=null) DeclarationList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Declaration!=null) Declaration.traverseTopDown(visitor);
        if(DeclarationList!=null) DeclarationList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Declaration!=null) Declaration.traverseBottomUp(visitor);
        if(DeclarationList!=null) DeclarationList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("Declarations(\n");

        if(Declaration!=null)
            buffer.append(Declaration.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(DeclarationList!=null)
            buffer.append(DeclarationList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [Declarations]");
        return buffer.toString();
    }
}
