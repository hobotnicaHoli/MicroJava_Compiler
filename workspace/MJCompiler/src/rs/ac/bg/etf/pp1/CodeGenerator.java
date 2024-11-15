package rs.ac.bg.etf.pp1;

import rs.ac.bg.etf.pp1.ast.VisitorAdaptor;
import rs.etf.pp1.mj.runtime.Code;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;
import rs.ac.bg.etf.pp1.ast.*;

public class CodeGenerator extends VisitorAdaptor {
	public int mainPC;
	
	public int obicanminus = 0;
	public int zagradaminus = 0;
	
	public int getPC() {
		return mainPC;
	}
	
	public void setPC(int PC) {
		mainPC = PC;
	}
	
	public void visit(MethodName methName) {
		
		if("main".equals(methName.getMethodName())){
			mainPC = Code.pc;
		}
		methName.obj.setAdr(Code.pc);
		Code.put(Code.enter);
		Code.put(methName.obj.getLevel());
		Code.put(methName.obj.getLocalSymbols().size());
	}
	
	public void visit(MethodDeclaration methDecl) {
		Code.put(Code.exit);
		Code.put(Code.return_);
	}
	
	public void visit(FactoruNm number) {
		Code.loadConst(number.getNVal());
		
		SyntaxNode s2 = number.getParent();

		if(s2 instanceof Term) {
			SyntaxNode s3 = s2.getParent();
			if(s3 instanceof Expr2) {
				//u pitanju je pocetni -
				if(obicanminus > 0) {
					Code.put(Code.neg);
					obicanminus = 0;
				}
			
			}
			else if(s3 instanceof AddopTerms) {
				if(((AddopTerms) s3).getAddop() instanceof AddopMinus) {
					Code.put(Code.neg);
				}
			}
		}
	}
	
	public void visit(FactorChar charc) {
		Code.loadConst(charc.getCVal());
	}
	
	public void visit(FactorBool fbool) {
		Code.loadConst(fbool.getBVal() ? 1 : 0);
	}
	
	public void visit(Factor7 f7) {
		//new array
		//na steku se vec nalazi int koliko elemenata ce niz imati tj Expr
		Code.put(Code.newarray);
		int c = f7.getType().struct == Tab.charType ? 0 : 1;
		Code.put(c);
	}
	
	public void visit(Factor9 f9) {
		//NEW Type LUPAR Expr RUPAR LUPAR Expr RUPAR
		//new MATRIX
		Obj width = Tab.insert(Obj.Var, "width", new Struct(Struct.Int));
		Obj height = Tab.insert(Obj.Var, "height", new Struct(Struct.Int));
		Obj index = Tab.insert(Obj.Var, "index", new Struct(Struct.Int));
		Obj arr = Tab.insert(Obj.Var, "arr", new Struct(Struct.Array));
		// niz[2][3]
		// 2 3
		Code.store(width);
		Code.store(height);
		Code.loadConst(0);
		Code.store(index); 
		Code.load(height);
		Code.put(Code.newarray); 
		Code.put(1); 

		Code.store(arr); 
		int labela = Code.pc;
		Code.load(index);
		Code.load(height);
		Code.putFalseJump(Code.ne, 0);
		int fix = Code.pc - 2;
		Code.load(arr);
		Code.load(index);
		Code.load(width);
		Code.put(Code.newarray);
		int c = f9.getType().struct == Tab.charType ? 0 : 1;
		Code.put(c);
		Code.put(Code.astore);
		Code.load(index);
		Code.loadConst(1);
		Code.put(Code.add);
		Code.store(index);
		Code.putJump(labela);
		Code.fixup(fix);
		Code.load(arr);
		
	}
	
	public void visit(Factor1 f1) {
		//designator 
		Code.load(f1.getDesignator().obj);
		SyntaxNode s2 = f1.getParent();

		if(s2 instanceof Term) {
			SyntaxNode s3 = s2.getParent();
			if(s3 instanceof Expr2) {
				//u pitanju je pocetni -
				if(obicanminus > 0) {
					Code.put(Code.neg);
					obicanminus = 0;
				}
			
			}
			else if(s3 instanceof AddopTerms) {
				if(((AddopTerms) s3).getAddop() instanceof AddopMinus) {
					Code.put(Code.neg);
				}
			}
		}
	}
	
	public void visit(Factor8 f8) {
		// (expr)
		SyntaxNode s2 = f8.getParent();

			if(s2 instanceof Term) {
				SyntaxNode s3 = s2.getParent();
				if(s3 instanceof Expr2) {
					//u pitanju je pocetni -
					if(zagradaminus > 0) {
						Code.put(Code.neg);
						zagradaminus = 0;
					}
				
			}
				else if(s3 instanceof AddopTerms) {
					if(((AddopTerms) s3).getAddop() instanceof AddopMinus) {
						Code.put(Code.neg);
					}
				}
		}
		
	}
	
	public void visit(MulopFactors mfs) {
		Mulop m = mfs.getMulop();
		if(m instanceof MulopMul) {
			Code.put(Code.mul);
		}
		else if(m instanceof MulopDiv) {
			Code.put(Code.div);
		}
		else if(m instanceof MulopPercent) {
			Code.put(Code.rem);
		}
	}
	
	public void visit(AddopTerms ats) {
		Addop a = ats.getAddop();
		if(a instanceof AddopPlus) {
			Code.put(Code.add);
		}
		else if(a instanceof AddopMinus) {
			//kad se - nalazi pre broja, simbola itd samo ga negiram i ovde normalno saberem
			Code.put(Code.add);
		}
	}
	
	public void visit(DesignStmt1 ds1) {
		Obj desObj = ds1.getDesignator().obj;
		DesStmtOptions opt = ds1.getDesStmtOptions();
		if(opt instanceof DesStmtOpt1) {
				Code.store(desObj);
		}
		else if(opt instanceof DesStmtOpt4) {
			//designator ++
			Designator des = ds1.getDesignator();
			IdentOrExprList iel = des.getIdentOrExprList();
			if(iel instanceof Express) {
				Code.put(Code.dup2);
				Code.put(Code.aload);
			}
			else if(iel instanceof ExpressMatrix) {
				Code.put(Code.dup2);
				Code.put(Code.aload);
			}
			else {
				Code.load(desObj);
			}
			Code.loadConst(1);
			Code.put(Code.add);
			Code.store(desObj);
		}
		else if(opt instanceof DesStmtOpt5) {
			//designator --
			Designator des = ds1.getDesignator();
			IdentOrExprList iel = des.getIdentOrExprList();
			if(iel instanceof Express) {
				Code.put(Code.dup2);
				Code.put(Code.aload);
			}
			else if(iel instanceof ExpressMatrix) {
				Code.put(Code.dup2);
				Code.put(Code.aload);
			}
			else {
				Code.load(desObj);
			}
			Code.loadConst(1);
			Code.put(Code.sub);
			Code.store(desObj);
		}
	}
	
	public void visit(DesignatorName desName) {
		SyntaxNode parent = desName.getParent();
		if(parent instanceof Designator) {
			//mada ne moze nista drugo ni da bude?
			IdentOrExprList dodatakzaniz = ((Designator) parent).getIdentOrExprList();
			if(dodatakzaniz instanceof Express) {
				Code.load(desName.obj);
			}
			else if(dodatakzaniz instanceof ExpressMatrix) {
				Code.load(desName.obj);
			}
		}
	}
	
	
	public void visit(Statement8 read) {
		//READ LPAREN Designator RPAREN SEMI
		Obj desObj = read.getDesignator().obj;
		if(desObj.getType() == Tab.intType) {
			Code.put(Code.read);
		}
		else if(desObj.getType() == Tab.charType) {
			Code.put(Code.bread);
		}
		Code.store(desObj);
	}
	
	public void visit(Statement9 printwithoutcomma) {
		//PRINT LPAREN Expr RPAREN SEMI
		Expr ex = printwithoutcomma.getExpr();
		if(ex.struct == Tab.intType) {
			Code.loadConst(5);
			Code.put(Code.print);
		}
		else if(ex.struct == Tab.charType) {
			Code.loadConst(1);
			Code.put(Code.bprint);
		}
	}
	
	public void visit(Statement10 printwithcomma) {
		//PRINT LPAREN Expr COMMA NUMBER RPAREN SEMI
		Expr ex = printwithcomma.getExpr();
		int num = printwithcomma.getN2();
		if(ex.struct == Tab.intType) {
			Code.loadConst(num);
			Code.put(Code.print);
		}
		else if(ex.struct == Tab.charType) {
			Code.loadConst(num);
			Code.put(Code.bprint);
		}
	}
	
	public void visit(Minus m) {
		SyntaxNode e2 = m.getParent();
		if(e2 instanceof Expr2) {
			Term t = ((Expr2) e2).getTerm();
			Factor f = t.getFactor();
			if(f instanceof Factor8) {
				//sledi zagrada posle
				zagradaminus++;
			}
			else {
				obicanminus++;
			}
		}
	}
	
	public void visit(Designator designator) {
		IdentOrExprList iel = designator.getIdentOrExprList();
		if(iel instanceof ExpressMatrix) {
			//pristup elementu matrice
			//nalaze se na steku 8 0 0
			Code.put(Code.dup_x2);
			// 0 8 0 0
			Code.put(Code.pop);
			//0 8 0
			Code.put(Code.aload);
			//0 8[0]
			Code.put(Code.dup_x1);
			//8[0] 0 8[0]
			Code.put(Code.pop);
			//8[0] 0
		}
	}
	
}
