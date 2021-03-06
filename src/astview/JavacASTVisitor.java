package astview;

import java.util.Set;

import javax.lang.model.element.Modifier;

import com.sun.source.tree.*;
import com.sun.tools.javac.code.TypeTag;
import com.sun.tools.javac.tree.EndPosTable;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.JCTree.*;
import com.sun.tools.javac.util.List;

public class JavacASTVisitor implements TreeVisitor<JavacASTNode, Void> {

	public JavacASTNode traverse(JCTree tree) {
		if (tree == null)
			return null;
		return tree.accept(this, null);
	}

	
	public void traverse(JavacASTNode parent, String property, JCTree currnode) {
		if (currnode == null)
			return;

		JavacASTNode sub = currnode.accept(this, null);
		sub.setProperty(property);
		if (sub.getType() == null) {
			sub.setType(currnode.getClass().getSimpleName());
		}
		sub.setParent(parent);
		parent.addChild(sub);

	}

	public <T extends JCTree> void traverse(JavacASTNode parent, String property, List<T> trees) {
		if (trees == null || trees.size() == 0)
			return;

		JavacASTNode defs = new JavacASTNode(property, trees.getClass().getSimpleName());
		defs.setParent(parent);
		parent.addChild(defs);

		for (int i = 0; i < trees.size(); i++) {
			JCTree tree = trees.get(i);
			JavacASTNode def_n = tree.accept(this, null);
			def_n.setProperty(i + "");
			if (def_n.getType() == null) {
				def_n.setType(tree.getClass().getSimpleName());
			}
			def_n.setParent(defs);

			defs.addChild(def_n);
		}
	}
	
	

	@Override
	public JavacASTNode visitCompilationUnit(CompilationUnitTree node, Void p) {
		JCCompilationUnit t = (JCCompilationUnit) node;
//		ept = t.endPositions;
		JavacASTNode currnode = new JavacASTNode(t.getStartPosition(),t.getEndPosition(JavacASTViewer.ept));
		currnode.setProperty("root");
		currnode.setType(t.getClass().getSimpleName());
		
		traverse(currnode,"packageAnnotations",t.packageAnnotations);
		traverse(currnode,"pid",t.pid);
		traverse(currnode,"defs",t.defs);

		return currnode;
	}

	@Override
	public JavacASTNode visitClass(ClassTree node, Void p) {
		JCClassDecl t = (JCClassDecl) node;
		JavacASTNode currnode = new JavacASTNode(t.getStartPosition(),t.getEndPosition(JavacASTViewer.ept));

		traverse(currnode,"extending",t.extending);
		traverse(currnode,"implementing",t.implementing);
		traverse(currnode,"defs",t.defs);
		
		return currnode;
	}

	public JavacASTNode visitImport(ImportTree node, Void curr) {
		JCImport t = (JCImport) node;
		JavacASTNode currnode = new JavacASTNode(t.getStartPosition(),t.getEndPosition(JavacASTViewer.ept));
		
		traverse(currnode,"qualid",t.qualid);

		return currnode;
	}

	@Override
	public JavacASTNode visitIdentifier(IdentifierTree node, Void p) {
		JCIdent t = (JCIdent) node;
		JavacASTNode currnode = new JavacASTNode(t.getStartPosition(),t.getEndPosition(JavacASTViewer.ept));
		JavacASTNode name = new JavacASTNode("name", t.name.getClass().getSimpleName(), t.name.toString());
		currnode.addChild(name);
		name.setParent(currnode);
		return currnode;
	}

	@Override
	public JavacASTNode visitAnnotatedType(AnnotatedTypeTree node, Void p) {
		JCAnnotatedType t = (JCAnnotatedType) node;
		JavacASTNode currnode = new JavacASTNode(t.getStartPosition(),t.getEndPosition(JavacASTViewer.ept));
		
		traverse(currnode, "annotations", t.annotations);
		traverse(currnode, "underlyingType", t.underlyingType);
		
		return currnode;
	}

	@Override
	public JavacASTNode visitAnnotation(AnnotationTree node, Void p) {
		JCAnnotation t = (JCAnnotation) node;
		JavacASTNode currnode = new JavacASTNode(t.getStartPosition(),t.getEndPosition(JavacASTViewer.ept));

		traverse(currnode, "annotationType", t.annotationType);
		traverse(currnode, "args", t.args);

		return currnode;
	}

	@Override
	public JavacASTNode visitArrayAccess(ArrayAccessTree node, Void p) {
		JCArrayAccess t = (JCArrayAccess) node;
		JavacASTNode currnode = new JavacASTNode(t.getStartPosition(),t.getEndPosition(JavacASTViewer.ept));
		
		traverse(currnode,"indexed",t.indexed);
		traverse(currnode,"index",t.index);

		return currnode;
	}

	@Override
	public JavacASTNode visitArrayType(ArrayTypeTree node, Void p) {
		JCArrayTypeTree t = (JCArrayTypeTree) node;
		JavacASTNode currnode = new JavacASTNode(t.getStartPosition(),t.getEndPosition(JavacASTViewer.ept));
		
		traverse(currnode,"elemtype",t.elemtype);

		return currnode;
	}

	@Override
	public JavacASTNode visitAssert(AssertTree node, Void p) {
		JCAssert t = (JCAssert) node;
		JavacASTNode currnode = new JavacASTNode(t.getStartPosition(),t.getEndPosition(JavacASTViewer.ept));
		
		traverse(currnode,"cond",t.cond);
		traverse(currnode,"detail",t.detail);

		return currnode;
	}

	@Override
	public JavacASTNode visitAssignment(AssignmentTree node, Void p) {
		JCAssign t = (JCAssign) node;
		JavacASTNode currnode = new JavacASTNode(t.getStartPosition(),t.getEndPosition(JavacASTViewer.ept));
		
		traverse(currnode,"lhs",t.lhs);
		traverse(currnode,"rhs",t.rhs);

		return currnode;
	}

	@Override
	public JavacASTNode visitBinary(BinaryTree node, Void p) {
		JCBinary t = (JCBinary) node;
		JavacASTNode currnode = new JavacASTNode(t.getStartPosition(),t.getEndPosition(JavacASTViewer.ept));
		
		traverse(currnode,"lhs",t.lhs);
		traverse(currnode,"rhs",t.rhs);

		return currnode;
	}

	@Override
	public JavacASTNode visitBlock(BlockTree node, Void p) {
		JCBlock t = (JCBlock) node;
		JavacASTNode currnode = new JavacASTNode(t.getStartPosition(),t.getEndPosition(JavacASTViewer.ept));
		
		traverse(currnode,"stats",t.stats);

		return currnode;
	}

	@Override
	public JavacASTNode visitBreak(BreakTree node, Void p) {
		JCBreak t = (JCBreak) node;
		JavacASTNode currnode = new JavacASTNode(t.getStartPosition(),t.getEndPosition(JavacASTViewer.ept));
		
		JavacASTNode label = new JavacASTNode("label", t.label.getClass().getSimpleName(), t.label.toString());
		currnode.addChild(label);
		label.setParent(currnode);
		return currnode;
	}

	@Override
	public JavacASTNode visitCase(CaseTree node, Void p) {
		JCCase t = (JCCase) node;
		JavacASTNode currnode = new JavacASTNode(t.getStartPosition(),t.getEndPosition(JavacASTViewer.ept));
		
		traverse(currnode,"pat",t.pat);
		traverse(currnode,"stats",t.stats);

		return currnode;
	}

	@Override
	public JavacASTNode visitCatch(CatchTree node, Void p) {
		JCCatch t = (JCCatch) node;
		JavacASTNode currnode = new JavacASTNode(t.getStartPosition(),t.getEndPosition(JavacASTViewer.ept));

		traverse(currnode,"param",t.param);
		traverse(currnode,"body",t.body);

		return currnode;
	}

	@Override
	public JavacASTNode visitCompoundAssignment(CompoundAssignmentTree node, Void p) {
		JCAssignOp t = (JCAssignOp) node;
		JavacASTNode currnode = new JavacASTNode(t.getStartPosition(),t.getEndPosition(JavacASTViewer.ept));
		
		traverse(currnode,"lhs",t.lhs);
		traverse(currnode,"rhs",t.rhs);

		return currnode;
	}

	@Override
	public JavacASTNode visitConditionalExpression(ConditionalExpressionTree node, Void p) {
		JCConditional t = (JCConditional) node;
		JavacASTNode currnode = new JavacASTNode(t.getStartPosition(),t.getEndPosition(JavacASTViewer.ept));

		traverse(currnode,"cond",t.cond);
		traverse(currnode,"truepart",t.truepart);
		traverse(currnode,"falsepart",t.falsepart);

		return currnode;
	}

	@Override
	public JavacASTNode visitContinue(ContinueTree node, Void p) {
		JCContinue t = (JCContinue) node;
		JavacASTNode currnode = new JavacASTNode(t.getStartPosition(),t.getEndPosition(JavacASTViewer.ept));
		
		JavacASTNode label = new JavacASTNode("label", t.label.getClass().getSimpleName(), t.label.toString());
		currnode.addChild(label);
		label.setParent(currnode);
		return currnode;		
	}

	@Override
	public JavacASTNode visitDoWhileLoop(DoWhileLoopTree node, Void p) {
		JCDoWhileLoop t = (JCDoWhileLoop) node;
		JavacASTNode currnode = new JavacASTNode(t.getStartPosition(),t.getEndPosition(JavacASTViewer.ept));
		
		traverse(currnode,"cond",t.cond);
		traverse(currnode,"body",t.body);

		return currnode;
	}

	@Override
	public JavacASTNode visitEmptyStatement(EmptyStatementTree node, Void p) {
		JCSkip t = (JCSkip) node;
		JavacASTNode currnode = new JavacASTNode(t.getStartPosition(),t.getEndPosition(JavacASTViewer.ept));
		currnode.setValue(";");
		return currnode;
	}

	@Override
	public JavacASTNode visitEnhancedForLoop(EnhancedForLoopTree node, Void p) {
		JCEnhancedForLoop t = (JCEnhancedForLoop) node;
		JavacASTNode currnode = new JavacASTNode(t.getStartPosition(),t.getEndPosition(JavacASTViewer.ept));
		
		traverse(currnode,"var",t.var);
		traverse(currnode,"expr",t.expr);
		traverse(currnode,"body",t.body);

		return currnode;
	}

	@Override
	public JavacASTNode visitErroneous(ErroneousTree node, Void p) {
		JCErroneous t = (JCErroneous) node;
		JavacASTNode currnode = new JavacASTNode(t.getStartPosition(),t.getEndPosition(JavacASTViewer.ept));
		
		traverse(currnode,"errs",t.errs);

		return currnode;
	}

	@Override
	public JavacASTNode visitExpressionStatement(ExpressionStatementTree node, Void p) {
		JCExpressionStatement t = (JCExpressionStatement) node;
		JavacASTNode currnode = new JavacASTNode(t.getStartPosition(),t.getEndPosition(JavacASTViewer.ept));
		
		traverse(currnode,"expr",t.expr);

		return currnode;
	}

	@Override
	public JavacASTNode visitForLoop(ForLoopTree node, Void p) {
		JCForLoop t = (JCForLoop) node;
		JavacASTNode currnode = new JavacASTNode(t.getStartPosition(),t.getEndPosition(JavacASTViewer.ept));
		
		traverse(currnode,"init",t.init);
		traverse(currnode,"cond",t.cond);
		traverse(currnode,"step",t.step);
		traverse(currnode,"body",t.body);

		return currnode;
	}

	@Override
	public JavacASTNode visitIf(IfTree node, Void p) {
		JCIf t = (JCIf) node;
		JavacASTNode currnode = new JavacASTNode(t.getStartPosition(),t.getEndPosition(JavacASTViewer.ept));
		
		traverse(currnode,"cond",t.cond);
		traverse(currnode,"thenpart",t.thenpart);
		traverse(currnode,"elsepart",t.elsepart);

		return currnode;
	}

	@Override
	public JavacASTNode visitInstanceOf(InstanceOfTree node, Void p) {
		JCInstanceOf t = (JCInstanceOf) node;
		JavacASTNode currnode = new JavacASTNode(t.getStartPosition(),t.getEndPosition(JavacASTViewer.ept));
		
		traverse(currnode,"expr",t.expr);
		traverse(currnode,"clazz",t.clazz);

		return currnode;
	}

	@Override
	public JavacASTNode visitIntersectionType(IntersectionTypeTree node, Void p) {
		JCTypeIntersection t = (JCTypeIntersection) node;
		JavacASTNode currnode = new JavacASTNode(t.getStartPosition(),t.getEndPosition(JavacASTViewer.ept));
		
		traverse(currnode,"bounds",t.bounds);
		
		return currnode;
	}

	@Override
	public JavacASTNode visitLabeledStatement(LabeledStatementTree node, Void p) {
		JCLabeledStatement t = (JCLabeledStatement) node;
		JavacASTNode currnode = new JavacASTNode(t.getStartPosition(),t.getEndPosition(JavacASTViewer.ept));
		
		JavacASTNode label = new JavacASTNode("label", t.label.getClass().getSimpleName(), t.label.toString());
		currnode.addChild(label);
		label.setParent(currnode);
		
		traverse(currnode,"body",t.body);

		return currnode;
	}

	@Override
	public JavacASTNode visitLambdaExpression(LambdaExpressionTree node, Void p) {
		JCLambda t = (JCLambda) node;
		JavacASTNode currnode = new JavacASTNode(t.getStartPosition(),t.getEndPosition(JavacASTViewer.ept));

		traverse(currnode, "params", t.params);
		traverse(currnode, "body", t.body);

		return currnode;
	}

	@Override
	public JavacASTNode visitLiteral(LiteralTree node, Void p) {
		JCLiteral t = (JCLiteral) node;
		JavacASTNode currnode = new JavacASTNode(t.getStartPosition(),t.getEndPosition(JavacASTViewer.ept));

		JavacASTNode value;
		if (t.value == null) {
			value = new JavacASTNode("value", "", "null");
		} else {
			value = new JavacASTNode("value", t.value.getClass().getSimpleName(), t.value.toString());

		}
		currnode.addChild(value);
		value.setParent(currnode);

		return currnode;
	}

	@Override
	public JavacASTNode visitMemberReference(MemberReferenceTree node, Void p) {
		JCMemberReference t = (JCMemberReference) node;
		JavacASTNode currnode = new JavacASTNode(t.getStartPosition(),t.getEndPosition(JavacASTViewer.ept));
		
		traverse(currnode, "expr", t.expr);
		traverse(currnode, "typeargs", t.typeargs);
		
		return currnode;
	}

	@Override
	public JavacASTNode visitMemberSelect(MemberSelectTree node, Void p) {
		JCFieldAccess t = (JCFieldAccess) node;
		JavacASTNode currnode = new JavacASTNode(t.getStartPosition(),t.getEndPosition(JavacASTViewer.ept));
		
		traverse(currnode,"selected",t.selected);

		return currnode;
	}

	@Override
	public JavacASTNode visitMethod(MethodTree node, Void p) {
		JCMethodDecl t = (JCMethodDecl) node;
		JavacASTNode currnode = new JavacASTNode(t.getStartPosition(),t.getEndPosition(JavacASTViewer.ept));
		
		traverse(currnode,"mods",t.mods);
		traverse(currnode,"restype",t.restype);
		traverse(currnode,"typarams",t.typarams);
		traverse(currnode,"params",t.params);
		traverse(currnode,"thrown",t.thrown);
		traverse(currnode,"body",t.body);
		traverse(currnode,"defaultValue",t.defaultValue);

		return currnode;
	}

	@Override
	public JavacASTNode visitMethodInvocation(MethodInvocationTree node, Void p) {
		JCMethodInvocation t = (JCMethodInvocation) node;
		JavacASTNode currnode = new JavacASTNode(t.getStartPosition(),t.getEndPosition(JavacASTViewer.ept));
		
		traverse(currnode,"typeargs",t.typeargs);
		traverse(currnode,"meth",t.meth);
		traverse(currnode,"args",t.args);

		return currnode;
	}

	@Override
	public JavacASTNode visitModifiers(ModifiersTree node, Void p) {
		JCModifiers t = (JCModifiers) node;
		JavacASTNode currnode = new JavacASTNode(t.getStartPosition(),t.getEndPosition(JavacASTViewer.ept));
		
		StringBuffer tmp = new StringBuffer();
		Set<Modifier>  mdfs = t.getFlags();
		for(Modifier md:mdfs) {
			tmp.append(" ");
			if(md == Modifier.ABSTRACT) {
				tmp.append("abstract");
			}else if(md == Modifier.FINAL) {
				tmp.append("final");
			}else if(md == Modifier.NATIVE) {
				tmp.append("native");
			}else if(md == Modifier.PRIVATE) {
				tmp.append("private");
			}else if(md == Modifier.PROTECTED) {
				tmp.append("protected");
			}else if(md == Modifier.PUBLIC) {
				tmp.append("public");
			}else if(md == Modifier.STATIC) {
				tmp.append("static");
			}else if(md == Modifier.STRICTFP) {
				tmp.append("strictfp");
			}else if(md == Modifier.SYNCHRONIZED) {
				tmp.append("synchronized");
			}else if(md == Modifier.TRANSIENT) {
				tmp.append("transient");
			}else if(md == Modifier.VOLATILE) {
				tmp.append("volatile");
			}else {
				break;
			}
		}
		currnode.setValue(tmp.toString());
		
		// todo
		traverse(currnode,"annotations",t.annotations);

		return currnode;
	}

	@Override
	public JavacASTNode visitNewArray(NewArrayTree node, Void p) {
		JCNewArray t = (JCNewArray) node;
		JavacASTNode currnode = new JavacASTNode(t.getStartPosition(),t.getEndPosition(JavacASTViewer.ept));
		
		traverse(currnode,"elemtype",t.elemtype);
		traverse(currnode,"dims",t.dims);
		traverse(currnode,"elems",t.elems);

		return currnode;
	}

	@Override
	public JavacASTNode visitNewClass(NewClassTree node, Void p) {
		JCNewClass t = (JCNewClass) node;
		JavacASTNode currnode = new JavacASTNode(t.getStartPosition(),t.getEndPosition(JavacASTViewer.ept));

		traverse(currnode,"encl",t.encl);
		traverse(currnode,"typeargs",t.typeargs);
		traverse(currnode,"clazz",t.clazz);
		traverse(currnode,"args",t.args);
		traverse(currnode,"def",t.def);

		return currnode;
	}

	@Override
	public JavacASTNode visitOther(Tree node, Void p) {
		JCTree t = (JCTree) node;
		JavacASTNode currnode = new JavacASTNode(t.getStartPosition(),t.getEndPosition(JavacASTViewer.ept));

		switch (t.getTag()) {
		case LETEXPR: {
			LetExpr le = (LetExpr) node;
			traverse(currnode, "defs", le.defs);
			traverse(currnode, "expr", le.expr);
			return currnode;
		}
		default:
			throw new AssertionError("unknown tree tag: " + t.getTag());
		}

	}

	@Override
	public JavacASTNode visitParameterizedType(ParameterizedTypeTree node, Void p) {
		JCTypeApply t = (JCTypeApply) node;
		JavacASTNode currnode = new JavacASTNode(t.getStartPosition(),t.getEndPosition(JavacASTViewer.ept));
		
		traverse(currnode,"clazz",t.clazz);
		traverse(currnode,"arguments",t.arguments);

		return currnode;
	}

	@Override
	public JavacASTNode visitParenthesized(ParenthesizedTree node, Void p) {
		JCParens t = (JCParens) node;
		JavacASTNode currnode = new JavacASTNode(t.getStartPosition(),t.getEndPosition(JavacASTViewer.ept));
		
		traverse(currnode,"expr",t.expr);

		return currnode;
	}

	@Override
	public JavacASTNode visitPrimitiveType(PrimitiveTypeTree node, Void p) {
		JCPrimitiveTypeTree t = (JCPrimitiveTypeTree) node;
		JavacASTNode currnode = new JavacASTNode(t.getStartPosition(),t.getEndPosition(JavacASTViewer.ept));

		TypeTag tag = t.typetag;
		switch (tag) {
		case BOOLEAN:
			currnode.setType("boolean");
			return currnode;
		case BYTE:
			currnode.setType("byte");
			return currnode;
		case SHORT:
			currnode.setType("short");
			return currnode;
		case INT:
			currnode.setType("int");
			return currnode;
		case LONG:
			currnode.setType("long");
			return currnode;
		case CHAR:
			currnode.setType("char");
			return currnode;
		case FLOAT:
			currnode.setType("float");
			return currnode;
		case DOUBLE:
			currnode.setType("double");
			return currnode;
		case VOID:
			currnode.setType("void");
			return currnode;
		default:
			throw new AssertionError("unknown primitive type " + this);
		}
	}

	@Override
	public JavacASTNode visitReturn(ReturnTree node, Void p) {
		JCReturn t = (JCReturn) node;
		JavacASTNode currnode = new JavacASTNode(t.getStartPosition(),t.getEndPosition(JavacASTViewer.ept));
		
		traverse(currnode,"expr",t.expr);

		return currnode;
	}

	@Override
	public JavacASTNode visitSwitch(SwitchTree node, Void p) {
		JCSwitch t = (JCSwitch) node;
		JavacASTNode currnode = new JavacASTNode(t.getStartPosition(),t.getEndPosition(JavacASTViewer.ept));
		
		traverse(currnode,"selector",t.selector);
		traverse(currnode,"cases",t.cases);

		return currnode;
	}

	@Override
	public JavacASTNode visitSynchronized(SynchronizedTree node, Void p) {
		JCSynchronized t = (JCSynchronized) node;
		JavacASTNode currnode = new JavacASTNode(t.getStartPosition(),t.getEndPosition(JavacASTViewer.ept));
		
		traverse(currnode,"lock",t.lock);
		traverse(currnode,"body",t.body);

		return currnode;
	}

	@Override
	public JavacASTNode visitThrow(ThrowTree node, Void p) {
		JCThrow t = (JCThrow) node;
		JavacASTNode currnode = new JavacASTNode(t.getStartPosition(),t.getEndPosition(JavacASTViewer.ept));
		
		traverse(currnode,"expr",t.expr);

		return currnode;
	}

	@Override
	public JavacASTNode visitTry(TryTree node, Void p) {
		JCTry t = (JCTry) node;
		JavacASTNode currnode = new JavacASTNode(t.getStartPosition(),t.getEndPosition(JavacASTViewer.ept));
		
		traverse(currnode,"resources",t.resources);
		traverse(currnode,"body",t.body);
		traverse(currnode,"resources",t.resources);
		traverse(currnode,"finalizer",t.finalizer);

		return currnode;
	}

	@Override
	public JavacASTNode visitTypeCast(TypeCastTree node, Void p) {
		JCTypeCast t = (JCTypeCast) node;
		JavacASTNode currnode = new JavacASTNode(t.getStartPosition(),t.getEndPosition(JavacASTViewer.ept));
		
		traverse(currnode,"clazz",t.clazz);
		traverse(currnode,"expr",t.expr);

		return currnode;
	}

	@Override
	public JavacASTNode visitTypeParameter(TypeParameterTree node, Void p) {
		JCTypeParameter t = (JCTypeParameter) node;
		JavacASTNode currnode = new JavacASTNode(t.getStartPosition(),t.getEndPosition(JavacASTViewer.ept));
		
		traverse(currnode,"bounds",t.bounds);

		return currnode;
	}

	@Override
	public JavacASTNode visitUnary(UnaryTree node, Void p) {
		JCUnary t = (JCUnary) node;
		JavacASTNode currnode = new JavacASTNode(t.getStartPosition(),t.getEndPosition(JavacASTViewer.ept));
		
		traverse(currnode,"arg",t.arg);

		return currnode;
	}

	@Override
	public JavacASTNode visitUnionType(UnionTypeTree node, Void p) {
		JCTypeUnion t = (JCTypeUnion) node;
		JavacASTNode currnode = new JavacASTNode(t.getStartPosition(),t.getEndPosition(JavacASTViewer.ept));

		traverse(currnode,"alternatives",t.alternatives);
		
		return currnode;
	}

	@Override
	public JavacASTNode visitVariable(VariableTree node, Void p) {
		JCVariableDecl t = (JCVariableDecl) node;
		JavacASTNode currnode = new JavacASTNode(t.getStartPosition(),t.getEndPosition(JavacASTViewer.ept));
		
		traverse(currnode,"mods",t.mods);
		traverse(currnode,"vartype",t.vartype);
		traverse(currnode,"init",t.init);

		return currnode;
	}

	@Override
	public JavacASTNode visitWhileLoop(WhileLoopTree node, Void p) {
		JCWhileLoop t = (JCWhileLoop) node;
		JavacASTNode currnode = new JavacASTNode(t.getStartPosition(),t.getEndPosition(JavacASTViewer.ept));
		
		traverse(currnode,"body",t.body);
		traverse(currnode,"cond",t.cond);

		return currnode;
	}

	@Override
	public JavacASTNode visitWildcard(WildcardTree node, Void p) {
		JCWildcard t = (JCWildcard) node;
		JavacASTNode currnode = new JavacASTNode(t.getStartPosition(),t.getEndPosition(JavacASTViewer.ept));
		
		// todo
		traverse(currnode,"inner",t.inner);

		return currnode;
	}

}
