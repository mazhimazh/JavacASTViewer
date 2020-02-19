package astview;

import com.sun.source.tree.*;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.JCTree.*;
import com.sun.tools.javac.util.List;
import com.sun.tools.javac.util.ListBuffer;

public class JavacASTVisitor implements TreeVisitor<JavacASTNode, Void> {

	public JavacASTNode traverse(JCTree tree) {
		if (tree == null)
			return null;
		return tree.accept(this, null);
	}

	public <T extends JCTree> List<JavacASTNode> traverse(List<T> trees) {
		if (trees == null)
			return null;
		ListBuffer<JavacASTNode> lb = new ListBuffer<JavacASTNode>();
		for (JCTree tree : trees) {
			JavacASTNode node = traverse(tree);
			if (node != null)
				lb.append(node);
		}
		return lb.toList();
	}

	@Override
	public JavacASTNode visitCompilationUnit(CompilationUnitTree node, Void p) {
		JCCompilationUnit t = (JCCompilationUnit) node;
		JavacASTNode currnode = new JavacASTNode(t.getKind());
		currnode.setName("root");
		currnode.setType("{JCCompilationUnit}");

		JavacASTNode pid = traverse(t.pid);
		if (pid != null) {
			pid.setName("pid");
			pid.setType(pid.getKind().name());
			pid.setParent(currnode);
			currnode.addChild(pid);
		}

		JavacASTNode defs = new JavacASTNode("defs", "{List<JCTree>}");
		defs.setParent(currnode);
		currnode.addChild(defs);
		List<JavacASTNode> def = traverse(t.defs);
		for (int i = 0; i < def.size(); i++) {
			JavacASTNode sub = def.get(i);
			sub.setName(i + "");
			sub.setType(sub.getKind().name());
			defs.addChild(sub);
			sub.setParent(defs);
		}

		return currnode;
	}

	@Override
	public JavacASTNode visitClass(ClassTree node, Void p) {
		JCClassDecl t = (JCClassDecl) node;
		JavacASTNode currnode = new JavacASTNode(t.getKind());

		JavacASTNode ext = traverse(t.extending);
		if (ext != null) {
			ext.setName("extending");
			ext.setType(ext.getKind().name());
			ext.setParent(currnode);
			currnode.addChild(ext);
		}

		if (t.implementing != null && t.implementing.size() > 0) {
			JavacASTNode impls = new JavacASTNode("implementing", "{List<JCExpression>}");
			impls.setParent(currnode);
			currnode.addChild(impls);
			List<JavacASTNode> impl = traverse(t.implementing);
			for (int i = 0; i < impl.size(); i++) {
				JavacASTNode sub = impl.get(i);
				sub.setName(i + "");
				sub.setType(sub.getKind().name());
				impls.addChild(sub);
				sub.setParent(impls);
			}
		}

		if (t.defs != null && t.defs.size() > 0) {
			JavacASTNode defs = new JavacASTNode("defs", "{List<JCTree>}");
			defs.setParent(currnode);
			currnode.addChild(defs);
			List<JavacASTNode> def = traverse(t.defs);
			for (int i = 0; i < def.size(); i++) {
				JavacASTNode sub = def.get(i);
				sub.setName(i + "");
				sub.setType(sub.getKind().name());
				defs.addChild(sub);
				sub.setParent(defs);
			}
		}

		return currnode;
	}

	public JavacASTNode visitImport(ImportTree node, Void curr) {
		JCImport t = (JCImport) node;
		JavacASTNode currnode = new JavacASTNode(t.getKind());

		JavacASTNode qualid = traverse(t.qualid);
		if (qualid != null) {
			qualid.setName("qualid");
			qualid.setType(qualid.getKind().name());
			qualid.setParent(currnode);
			currnode.addChild(qualid);
		}

		return currnode;
	}

	@Override
	public JavacASTNode visitIdentifier(IdentifierTree node, Void p) {
		JCIdent t = (JCIdent) node;
		JavacASTNode currnode = new JavacASTNode(t.getKind());
		JavacASTNode qualid = new JavacASTNode("name", "{SharedNameTable$NameImpl}", t.name.toString());
		currnode.addChild(qualid);
		qualid.setParent(currnode);
		return currnode;
	}

	@Override
	public JavacASTNode visitAnnotatedType(AnnotatedTypeTree node, Void p) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JavacASTNode visitAnnotation(AnnotationTree node, Void p) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JavacASTNode visitArrayAccess(ArrayAccessTree node, Void p) {
		JCArrayAccess t = (JCArrayAccess) node;
		JavacASTNode currnode = new JavacASTNode(t.getKind());

		JavacASTNode indexed = traverse(t.indexed);
		if (indexed != null) {
			indexed.setName("indexed");
			indexed.setType(indexed.getKind().name());
			indexed.setParent(currnode);
			currnode.addChild(indexed);
		}

		JavacASTNode index = traverse(t.index);
		if (index != null) {
			index.setName("index");
			index.setType(index.getKind().name());
			index.setParent(currnode);
			currnode.addChild(index);
		}

		return currnode;
	}

	@Override
	public JavacASTNode visitArrayType(ArrayTypeTree node, Void p) {
		JCArrayTypeTree t = (JCArrayTypeTree) node;
		JavacASTNode currnode = new JavacASTNode(t.getKind());

		JavacASTNode elemtype = traverse(t.elemtype);
		if (elemtype != null) {
			elemtype.setName("elemtype");
			elemtype.setType(elemtype.getKind().name());
			elemtype.setParent(elemtype);
			currnode.addChild(elemtype);
		}
		return currnode;
	}

	@Override
	public JavacASTNode visitAssert(AssertTree node, Void p) {
		JCAssert t = (JCAssert) node;
		JavacASTNode currnode = new JavacASTNode(t.getKind());

		JavacASTNode cond = traverse(t.cond);
		if (cond != null) {
			cond.setName("cond");
			cond.setType(cond.getKind().name());
			cond.setParent(currnode);
			currnode.addChild(cond);
		}

		JavacASTNode detail = traverse(t.detail);
		if (detail != null) {
			detail.setName("detail");
			detail.setType(detail.getKind().name());
			detail.setParent(currnode);
			currnode.addChild(detail);
		}

		return currnode;
	}

	@Override
	public JavacASTNode visitAssignment(AssignmentTree node, Void p) {
		JCAssign t = (JCAssign) node;
		JavacASTNode currnode = new JavacASTNode(t.getKind());

		JavacASTNode lhs = traverse(t.lhs);
		if (lhs != null) {
			lhs.setName("lhs");
			lhs.setType(lhs.getKind().name());
			lhs.setParent(currnode);
			currnode.addChild(lhs);
		}

		JavacASTNode rhs = traverse(t.rhs);
		if (rhs != null) {
			rhs.setName("rhs");
			rhs.setType(rhs.getKind().name());
			rhs.setParent(currnode);
			currnode.addChild(rhs);
		}

		return currnode;
	}

	@Override
	public JavacASTNode visitBinary(BinaryTree node, Void p) {
		JCBinary t = (JCBinary) node;
		JavacASTNode currnode = new JavacASTNode(t.getKind());

		JavacASTNode lhs = traverse(t.lhs);
		if (lhs != null) {
			lhs.setName("lhs");
			lhs.setType(lhs.getKind().name());
			lhs.setParent(currnode);
			currnode.addChild(lhs);
		}

		JavacASTNode rhs = traverse(t.rhs);
		if (rhs != null) {
			rhs.setName("rhs");
			rhs.setType(rhs.getKind().name());
			rhs.setParent(currnode);
			currnode.addChild(rhs);
		}
		return currnode;
	}

	@Override
	public JavacASTNode visitBlock(BlockTree node, Void p) {
		JCBlock t = (JCBlock) node;
		JavacASTNode currnode = new JavacASTNode(t.getKind());

		if (t.stats != null && t.stats.size() > 0) {
			JavacASTNode stats = new JavacASTNode("stats", "{List<JCExpression>}");
			stats.setParent(currnode);
			currnode.addChild(stats);
			List<JavacASTNode> stat = traverse(t.stats);
			for (int i = 0; i < stat.size(); i++) {
				JavacASTNode sub = stat.get(i);
				sub.setName(i + "");
				sub.setType(sub.getKind().name());
				stats.addChild(sub);
				sub.setParent(stats);
			}
		}

		return currnode;
	}

	@Override
	public JavacASTNode visitBreak(BreakTree node, Void p) {
		JCBreak t = (JCBreak) node;
		JavacASTNode currnode = new JavacASTNode(t.getKind());
		return currnode;
	}

	@Override
	public JavacASTNode visitCase(CaseTree node, Void p) {
		JCCase t = (JCCase) node;
		JavacASTNode currnode = new JavacASTNode(t.getKind());

		JavacASTNode pat = traverse(t.pat);
		if (pat != null) {
			pat.setName("pat");
			pat.setType(pat.getKind().name());
			pat.setParent(currnode);
			currnode.addChild(pat);
		}

		if (t.stats != null && t.stats.size() > 0) {
			JavacASTNode stats = new JavacASTNode("stats", "{List<JCExpression>}");
			stats.setParent(currnode);
			currnode.addChild(stats);
			List<JavacASTNode> stat = traverse(t.stats);
			for (int i = 0; i < stat.size(); i++) {
				JavacASTNode sub = stat.get(i);
				sub.setName(i + "");
				sub.setType(sub.getKind().name());
				stats.addChild(sub);
				sub.setParent(stats);
			}
		}
		return currnode;
	}

	@Override
	public JavacASTNode visitCatch(CatchTree node, Void p) {
		JCCatch t = (JCCatch) node;
		JavacASTNode currnode = new JavacASTNode(t.getKind());

		JavacASTNode param = traverse(t.param);
		if (param != null) {
			param.setName("param");
			param.setType(param.getKind().name());
			param.setParent(currnode);
			currnode.addChild(param);
		}

		JavacASTNode body = traverse(t.body);
		if (body != null) {
			body.setName("body");
			body.setType(body.getKind().name());
			body.setParent(currnode);
			currnode.addChild(body);
		}

		return currnode;
	}

	@Override
	public JavacASTNode visitCompoundAssignment(CompoundAssignmentTree node, Void p) {
		JCAssignOp t = (JCAssignOp) node;
		JavacASTNode currnode = new JavacASTNode(t.getKind());

		JavacASTNode lhs = traverse(t.lhs);
		if (lhs != null) {
			lhs.setName("lhs");
			lhs.setType(lhs.getKind().name());
			lhs.setParent(currnode);
			currnode.addChild(lhs);
		}

		JavacASTNode rhs = traverse(t.rhs);
		if (rhs != null) {
			rhs.setName("rhs");
			rhs.setType(rhs.getKind().name());
			rhs.setParent(currnode);
			currnode.addChild(rhs);
		}

		return currnode;
	}

	@Override
	public JavacASTNode visitConditionalExpression(ConditionalExpressionTree node, Void p) {
		JCConditional t = (JCConditional) node;
		JavacASTNode currnode = new JavacASTNode(t.getKind());

		JavacASTNode cond = traverse(t.cond);
		if (cond != null) {
			cond.setName("cond");
			cond.setType(cond.getKind().name());
			cond.setParent(currnode);
			currnode.addChild(cond);
		}

		JavacASTNode truepart = traverse(t.truepart);
		if (truepart != null) {
			truepart.setName("truepart");
			truepart.setType(truepart.getKind().name());
			truepart.setParent(currnode);
			currnode.addChild(truepart);
		}

		JavacASTNode falsepart = traverse(t.falsepart);
		if (falsepart != null) {
			falsepart.setName("falsepart");
			falsepart.setType(falsepart.getKind().name());
			falsepart.setParent(currnode);
			currnode.addChild(falsepart);
		}

		return currnode;
	}

	@Override
	public JavacASTNode visitContinue(ContinueTree node, Void p) {
		JCContinue t = (JCContinue) node;
		JavacASTNode currnode = new JavacASTNode(t.getKind());
		return currnode;
	}

	@Override
	public JavacASTNode visitDoWhileLoop(DoWhileLoopTree node, Void p) {
		JCDoWhileLoop t = (JCDoWhileLoop) node;
		JavacASTNode currnode = new JavacASTNode(t.getKind());

		JavacASTNode cond = traverse(t.cond);
		if (cond != null) {
			cond.setName("cond");
			cond.setType(cond.getKind().name());
			cond.setParent(currnode);
			currnode.addChild(cond);
		}

		JavacASTNode body = traverse(t.body);
		if (body != null) {
			body.setName("body");
			body.setType(body.getKind().name());
			body.setParent(currnode);
			currnode.addChild(body);
		}

		return currnode;
	}

	@Override
	public JavacASTNode visitEmptyStatement(EmptyStatementTree node, Void p) {
		JCSkip t = (JCSkip) node;
		JavacASTNode currnode = new JavacASTNode(t.getKind());
		return currnode;
	}

	@Override
	public JavacASTNode visitEnhancedForLoop(EnhancedForLoopTree node, Void p) {
		JCEnhancedForLoop t = (JCEnhancedForLoop) node;
		JavacASTNode currnode = new JavacASTNode(t.getKind());

		JavacASTNode var = traverse(t.var);
		if (var != null) {
			var.setName("var");
			var.setType(var.getKind().name());
			var.setParent(currnode);
			currnode.addChild(var);
		}

		JavacASTNode expr = traverse(t.expr);
		if (expr != null) {
			expr.setName("expr");
			expr.setType(expr.getKind().name());
			expr.setParent(currnode);
			currnode.addChild(expr);
		}

		JavacASTNode body = traverse(t.body);
		if (body != null) {
			body.setName("body");
			body.setType(body.getKind().name());
			body.setParent(currnode);
			currnode.addChild(body);
		}

		return currnode;
	}

	@Override
	public JavacASTNode visitErroneous(ErroneousTree node, Void p) {
		JCErroneous t = (JCErroneous) node;
		JavacASTNode currnode = new JavacASTNode(t.getKind());

		if (t.errs != null && t.errs.size() > 0) {
			JavacASTNode errs = new JavacASTNode("errs", "{List<? extends JCTree>}");
			errs.setParent(currnode);
			currnode.addChild(errs);
			List<JavacASTNode> err = traverse(t.errs);
			for (int i = 0; i < err.size(); i++) {
				JavacASTNode sub = err.get(i);
				sub.setName(i + "");
				sub.setType(sub.getKind().name());
				errs.addChild(sub);
				sub.setParent(errs);
			}
		}
		return currnode;
	}

	@Override
	public JavacASTNode visitExpressionStatement(ExpressionStatementTree node, Void p) {
		JCExpressionStatement t = (JCExpressionStatement) node;
		JavacASTNode currnode = new JavacASTNode(t.getKind());

		JavacASTNode expr = traverse(t.expr);
		if (expr != null) {
			expr.setName("expr");
			expr.setType(expr.getKind().name());
			expr.setParent(currnode);
			currnode.addChild(expr);
		}

		return currnode;
	}

	@Override
	public JavacASTNode visitForLoop(ForLoopTree node, Void p) {
		JCForLoop t = (JCForLoop) node;
		JavacASTNode currnode = new JavacASTNode(t.getKind());

		if (t.init != null && t.init.size() > 0) {
			JavacASTNode init = new JavacASTNode("init", "{List<JCStatement>}");
			init.setParent(currnode);
			currnode.addChild(init);
			List<JavacASTNode> it = traverse(t.init);
			for (int i = 0; i < it.size(); i++) {
				JavacASTNode sub = it.get(i);
				sub.setName(i + "");
				sub.setType(sub.getKind().name());
				init.addChild(sub);
				sub.setParent(init);
			}
		}

		JavacASTNode cond = traverse(t.cond);
		if (cond != null) {
			cond.setName("cond");
			cond.setType(cond.getKind().name());
			cond.setParent(currnode);
			currnode.addChild(cond);
		}

		if (t.step != null && t.step.size() > 0) {
			JavacASTNode step = new JavacASTNode("step", "{List<JCExpressionStatement>}");
			step.setParent(currnode);
			currnode.addChild(step);
			List<JavacASTNode> sp = traverse(t.init);
			for (int i = 0; i < sp.size(); i++) {
				JavacASTNode sub = sp.get(i);
				sub.setName(i + "");
				sub.setType(sub.getKind().name());
				step.addChild(sub);
				sub.setParent(step);
			}
		}

		JavacASTNode body = traverse(t.body);
		if (body != null) {
			body.setName("body");
			body.setType(body.getKind().name());
			body.setParent(currnode);
			currnode.addChild(body);
		}

		return currnode;
	}

	@Override
	public JavacASTNode visitIf(IfTree node, Void p) {
		JCIf t = (JCIf) node;
		JavacASTNode currnode = new JavacASTNode(t.getKind());

		JavacASTNode cond = traverse(t.cond);
		if (cond != null) {
			cond.setName("cond");
			cond.setType(cond.getKind().name());
			cond.setParent(currnode);
			currnode.addChild(cond);
		}

		JavacASTNode thenpart = traverse(t.thenpart);
		if (thenpart != null) {
			thenpart.setName("thenpart");
			thenpart.setType(thenpart.getKind().name());
			thenpart.setParent(currnode);
			currnode.addChild(thenpart);
		}

		JavacASTNode elsepart = traverse(t.elsepart);
		if (elsepart != null) {
			elsepart.setName("elsepart");
			elsepart.setType(elsepart.getKind().name());
			elsepart.setParent(currnode);
			currnode.addChild(elsepart);
		}

		return currnode;
	}

	@Override
	public JavacASTNode visitInstanceOf(InstanceOfTree node, Void p) {
		JCInstanceOf t = (JCInstanceOf) node;
		JavacASTNode currnode = new JavacASTNode(t.getKind());

		JavacASTNode expr = traverse(t.expr);
		if (expr != null) {
			expr.setName("expr");
			expr.setType(expr.getKind().name());
			expr.setParent(currnode);
			currnode.addChild(expr);
		}

		JavacASTNode clazz = traverse(t.clazz);
		if (clazz != null) {
			clazz.setName("clazz");
			clazz.setType(clazz.getKind().name());
			clazz.setParent(currnode);
			currnode.addChild(clazz);
		}

		return currnode;
	}

	@Override
	public JavacASTNode visitIntersectionType(IntersectionTypeTree node, Void p) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JavacASTNode visitLabeledStatement(LabeledStatementTree node, Void p) {
		JCLabeledStatement t = (JCLabeledStatement) node;
		JavacASTNode currnode = new JavacASTNode(t.getKind());

		JavacASTNode body = traverse(t.body);
		if (body != null) {
			body.setName("body");
			body.setType(body.getKind().name());
			body.setParent(currnode);
			currnode.addChild(body);
		}

		return currnode;
	}

	@Override
	public JavacASTNode visitLambdaExpression(LambdaExpressionTree node, Void p) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JavacASTNode visitLiteral(LiteralTree node, Void p) {
		JCLiteral t = (JCLiteral) node;
		JavacASTNode currnode = new JavacASTNode(t.getKind());
		return currnode;
	}

	@Override
	public JavacASTNode visitMemberReference(MemberReferenceTree node, Void p) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JavacASTNode visitMemberSelect(MemberSelectTree node, Void p) {
		JCFieldAccess t = (JCFieldAccess) node;
		JavacASTNode currnode = new JavacASTNode(t.getKind());

		JavacASTNode selected = traverse(t.selected);
		if (selected != null) {
			selected.setName("selected");
			selected.setType(selected.getKind().name());
			selected.setParent(currnode);
			currnode.addChild(selected);
		}
		return currnode;
	}

	@Override
	public JavacASTNode visitMethod(MethodTree node, Void p) {
		JCMethodDecl t = (JCMethodDecl) node;
		JavacASTNode currnode = new JavacASTNode(t.getKind());

		JavacASTNode mods = traverse(t.mods);
		if (mods != null) {
			mods.setName("mods");
			mods.setType(mods.getKind().name());
			mods.setParent(currnode);
			currnode.addChild(mods);
		}

		JavacASTNode restype = traverse(t.restype);
		if (restype != null) {
			restype.setName("restype");
			restype.setType(restype.getKind().name());
			restype.setParent(currnode);
			currnode.addChild(restype);
		}

		if (t.typarams != null && t.typarams.size() > 0) {
			JavacASTNode typarams = new JavacASTNode("typarams", "{List<JCTypeParameter>}");
			typarams.setParent(currnode);
			currnode.addChild(typarams);
			List<JavacASTNode> sp = traverse(t.typarams);
			for (int i = 0; i < sp.size(); i++) {
				JavacASTNode sub = sp.get(i);
				sub.setName(i + "");
				sub.setType(sub.getKind().name());
				typarams.addChild(sub);
				sub.setParent(typarams);
			}
		}

		if (t.params != null && t.params.size() > 0) {
			JavacASTNode params = new JavacASTNode("params", "{List<JCVariableDecl>}");
			params.setParent(currnode);
			currnode.addChild(params);
			List<JavacASTNode> sp = traverse(t.params);
			for (int i = 0; i < sp.size(); i++) {
				JavacASTNode sub = sp.get(i);
				sub.setName(i + "");
				sub.setType(sub.getKind().name());
				params.addChild(sub);
				sub.setParent(params);
			}
		}

		if (t.thrown != null && t.thrown.size() > 0) {
			JavacASTNode thrown = new JavacASTNode("thrown", "{List<JCExpression>}");
			thrown.setParent(currnode);
			currnode.addChild(thrown);
			List<JavacASTNode> sp = traverse(t.thrown);
			for (int i = 0; i < sp.size(); i++) {
				JavacASTNode sub = sp.get(i);
				sub.setName(i + "");
				sub.setType(sub.getKind().name());
				thrown.addChild(sub);
				sub.setParent(thrown);
			}
		}

		JavacASTNode body = traverse(t.body);
		if (body != null) {
			body.setName("body");
			body.setType(body.getKind().name());
			body.setParent(currnode);
			currnode.addChild(body);
		}

		JavacASTNode defaultValue = traverse(t.defaultValue);
		if (defaultValue != null) {
			defaultValue.setName("defaultValue");
			defaultValue.setType(defaultValue.getKind().name());
			defaultValue.setParent(currnode);
			currnode.addChild(defaultValue);
		}

		return currnode;

	}

	@Override
	public JavacASTNode visitMethodInvocation(MethodInvocationTree node, Void p) {
		JCMethodInvocation t = (JCMethodInvocation) node;
		JavacASTNode currnode = new JavacASTNode(t.getKind());

		if (t.typeargs != null && t.typeargs.size() > 0) {
			JavacASTNode typeargs = new JavacASTNode("typeargs", "{List<JCExpression>}");
			typeargs.setParent(currnode);
			currnode.addChild(typeargs);
			List<JavacASTNode> sp = traverse(t.typeargs);
			for (int i = 0; i < sp.size(); i++) {
				JavacASTNode sub = sp.get(i);
				sub.setName(i + "");
				sub.setType(sub.getKind().name());
				typeargs.addChild(sub);
				sub.setParent(typeargs);
			}
		}

		JavacASTNode meth = traverse(t.meth);
		meth.setName("meth");
		meth.setType(meth.getKind().name());
		meth.setParent(currnode);
		currnode.addChild(meth);

		if (t.args != null && t.args.size() > 0) {
			JavacASTNode args = new JavacASTNode("args", "{List<JCExpression>}");
			args.setParent(currnode);
			currnode.addChild(args);
			List<JavacASTNode> sp = traverse(t.typeargs);
			for (int i = 0; i < sp.size(); i++) {
				JavacASTNode sub = sp.get(i);
				sub.setName(i + "");
				sub.setType(sub.getKind().name());
				args.addChild(sub);
				sub.setParent(args);
			}
		}

		return currnode;
	}

	@Override
	public JavacASTNode visitModifiers(ModifiersTree node, Void p) {
		JCModifiers t = (JCModifiers) node;
		JavacASTNode currnode = new JavacASTNode(t.getKind());

		if (t.annotations != null && t.annotations.size() > 0) {
			JavacASTNode annotations = new JavacASTNode("annotations", "{List<JCAnnotation>}");
			annotations.setParent(currnode);
			currnode.addChild(annotations);
			List<JavacASTNode> sp = traverse(t.annotations);
			for (int i = 0; i < sp.size(); i++) {
				JavacASTNode sub = sp.get(i);
				sub.setName(i + "");
				sub.setType(sub.getKind().name());
				annotations.addChild(sub);
				sub.setParent(annotations);
			}
		}

		return currnode;
	}

	@Override
	public JavacASTNode visitNewArray(NewArrayTree node, Void p) {
		JCNewArray t = (JCNewArray) node;
		JavacASTNode currnode = new JavacASTNode(t.getKind());

		JavacASTNode elemtype = traverse(t.elemtype);
		elemtype.setName("elemtype");
		elemtype.setType(elemtype.getKind().name());
		elemtype.setParent(currnode);
		currnode.addChild(elemtype);

		if (t.dims != null && t.dims.size() > 0) {
			JavacASTNode dims = new JavacASTNode("dims", "{List<JCExpression>}");
			dims.setParent(currnode);
			currnode.addChild(dims);
			List<JavacASTNode> sp = traverse(t.dims);
			for (int i = 0; i < sp.size(); i++) {
				JavacASTNode sub = sp.get(i);
				sub.setName(i + "");
				sub.setType(sub.getKind().name());
				dims.addChild(sub);
				sub.setParent(dims);
			}
		}

		if (t.elems != null && t.elems.size() > 0) {
			JavacASTNode elems = new JavacASTNode("elems", "{List<JCExpression>}");
			elems.setParent(currnode);
			currnode.addChild(elems);
			List<JavacASTNode> sp = traverse(t.elems);
			for (int i = 0; i < sp.size(); i++) {
				JavacASTNode sub = sp.get(i);
				sub.setName(i + "");
				sub.setType(sub.getKind().name());
				elems.addChild(sub);
				sub.setParent(elems);
			}
		}

		return currnode;
	}

	@Override
	public JavacASTNode visitNewClass(NewClassTree node, Void p) {
		JCNewClass t = (JCNewClass) node;
		JavacASTNode currnode = new JavacASTNode(t.getKind());

		JavacASTNode encl = traverse(t.encl);
		if (encl != null) {
			encl.setName("encl");
			encl.setType(encl.getKind().name());
			encl.setParent(currnode);
			currnode.addChild(encl);
		}

		if (t.typeargs != null && t.typeargs.size() > 0) {
			JavacASTNode typeargs = new JavacASTNode("typeargs", "{List<JCExpression>}");
			typeargs.setParent(currnode);
			currnode.addChild(typeargs);
			List<JavacASTNode> sp = traverse(t.typeargs);
			for (int i = 0; i < sp.size(); i++) {
				JavacASTNode sub = sp.get(i);
				sub.setName(i + "");
				sub.setType(sub.getKind().name());
				typeargs.addChild(sub);
				sub.setParent(typeargs);
			}
		}

		JavacASTNode clazz = traverse(t.clazz);
		if (clazz != null) {
			clazz.setName("clazz");
			clazz.setType(clazz.getKind().name());
			clazz.setParent(currnode);
			currnode.addChild(clazz);
		}

		if (t.args != null && t.args.size() > 0) {
			JavacASTNode args = new JavacASTNode("args", "{List<JCExpression>}");
			args.setParent(currnode);
			currnode.addChild(args);
			List<JavacASTNode> sp = traverse(t.args);
			for (int i = 0; i < sp.size(); i++) {
				JavacASTNode sub = sp.get(i);
				sub.setName(i + "");
				sub.setType(sub.getKind().name());
				args.addChild(sub);
				sub.setParent(args);
			}
		}

		JavacASTNode def = traverse(t.def);
		if (def != null) {
			def.setName("def");
			def.setType(encl.getKind().name());
			def.setParent(currnode);
			currnode.addChild(def);
		}

		return currnode;

	}

	@Override
	public JavacASTNode visitOther(Tree node, Void p) {
		JCTree tree = (JCTree) node;
		JavacASTNode currnode = new JavacASTNode(tree.getKind());

//		switch (tree.getTag()) {
//		case JCTree.LETEXPR: {
//			LetExpr t = (LetExpr) node;
//			List<JCVariableDecl> defs = copy(t.defs, p);
//			JCTree expr = copy(t.expr, p);
//			return M.at(t.pos).LetExpr(defs, expr);
//		}
//		default:
//			throw new AssertionError("unknown tree tag: " + tree.getTag());
//		}
		return currnode;
	}

	@Override
	public JavacASTNode visitParameterizedType(ParameterizedTypeTree node, Void p) {
		JCTypeApply t = (JCTypeApply) node;
		JavacASTNode currnode = new JavacASTNode(t.getKind());

		JavacASTNode clazz = traverse(t.clazz);
		if (clazz != null) {
			clazz.setName("clazz");
			clazz.setType(clazz.getKind().name());
			clazz.setParent(currnode);
			currnode.addChild(clazz);
		}

		if (t.arguments != null && t.arguments.size() > 0) {
			JavacASTNode arguments = new JavacASTNode("arguments", "{List<JCExpression>}");
			arguments.setParent(currnode);
			currnode.addChild(arguments);
			List<JavacASTNode> sp = traverse(t.arguments);
			for (int i = 0; i < sp.size(); i++) {
				JavacASTNode sub = sp.get(i);
				sub.setName(i + "");
				sub.setType(sub.getKind().name());
				arguments.addChild(sub);
				sub.setParent(arguments);
			}
		}

		return currnode;
	}

	@Override
	public JavacASTNode visitParenthesized(ParenthesizedTree node, Void p) {
		JCParens t = (JCParens) node;
		JavacASTNode currnode = new JavacASTNode(t.getKind());

		JavacASTNode expr = traverse(t.expr);
		if (expr != null) {
			expr.setName("expr");
			expr.setType(expr.getKind().name());
			expr.setParent(currnode);
			currnode.addChild(expr);
		}

		return currnode;
	}

	@Override
	public JavacASTNode visitPrimitiveType(PrimitiveTypeTree node, Void p) {
		JCPrimitiveTypeTree t = (JCPrimitiveTypeTree) node;
		JavacASTNode currnode = new JavacASTNode(t.getKind());
		return currnode;
	}

	@Override
	public JavacASTNode visitReturn(ReturnTree node, Void p) {
		JCReturn t = (JCReturn) node;
		JavacASTNode currnode = new JavacASTNode(t.getKind());

		JavacASTNode expr = traverse(t.expr);
		if (expr != null) {
			expr.setName("expr");
			expr.setType(expr.getKind().name());
			expr.setParent(currnode);
			currnode.addChild(expr);
		}

		return currnode;
	}

	@Override
	public JavacASTNode visitSwitch(SwitchTree node, Void p) {
		JCSwitch t = (JCSwitch) node;
		JavacASTNode currnode = new JavacASTNode(t.getKind());

		JavacASTNode selector = traverse(t.selector);
		if (selector != null) {
			selector.setName("selector");
			selector.setType(selector.getKind().name());
			selector.setParent(currnode);
			currnode.addChild(selector);
		}

		if (t.cases != null && t.cases.size() > 0) {
			JavacASTNode cases = new JavacASTNode("cases", "{List<JCCase>}");
			cases.setParent(currnode);
			currnode.addChild(cases);
			List<JavacASTNode> sp = traverse(t.cases);
			for (int i = 0; i < sp.size(); i++) {
				JavacASTNode sub = sp.get(i);
				sub.setName(i + "");
				sub.setType(sub.getKind().name());
				cases.addChild(sub);
				sub.setParent(cases);
			}
		}

		return currnode;
	}

	@Override
	public JavacASTNode visitSynchronized(SynchronizedTree node, Void p) {
		JCSynchronized t = (JCSynchronized) node;
		JavacASTNode currnode = new JavacASTNode(t.getKind());

		JavacASTNode lock = traverse(t.lock);
		if (lock != null) {
			lock.setName("lock");
			lock.setType(lock.getKind().name());
			lock.setParent(currnode);
			currnode.addChild(lock);
		}

		JavacASTNode body = traverse(t.body);
		if (body != null) {
			body.setName("body");
			body.setType(body.getKind().name());
			body.setParent(currnode);
			currnode.addChild(body);
		}

		return currnode;

	}

	@Override
	public JavacASTNode visitThrow(ThrowTree node, Void p) {
		JCThrow t = (JCThrow) node;
		JavacASTNode currnode = new JavacASTNode(t.getKind());

		JavacASTNode expr = traverse(t.expr);
		if (expr != null) {
			expr.setName("expr");
			expr.setType(expr.getKind().name());
			expr.setParent(currnode);
			currnode.addChild(expr);
		}

		return currnode;
	}

	@Override
	public JavacASTNode visitTry(TryTree node, Void p) {
		JCTry t = (JCTry) node;
		JavacASTNode currnode = new JavacASTNode(t.getKind());

		if (t.resources != null && t.resources.size() > 0) {
			JavacASTNode resources = new JavacASTNode("resources", "{List<JCTree>}");
			resources.setParent(currnode);
			currnode.addChild(resources);
			List<JavacASTNode> sp = traverse(t.resources);
			for (int i = 0; i < sp.size(); i++) {
				JavacASTNode sub = sp.get(i);
				sub.setName(i + "");
				sub.setType(sub.getKind().name());
				resources.addChild(sub);
				sub.setParent(resources);
			}
		}

		JavacASTNode body = traverse(t.body);
		if (body != null) {
			body.setName("body");
			body.setType(body.getKind().name());
			body.setParent(currnode);
			currnode.addChild(body);
		}

		if (t.resources != null && t.resources.size() > 0) {
			JavacASTNode catchers = new JavacASTNode("catchers", "{List<JCCatch>}");
			catchers.setParent(currnode);
			currnode.addChild(catchers);
			List<JavacASTNode> sp = traverse(t.catchers);
			for (int i = 0; i < sp.size(); i++) {
				JavacASTNode sub = sp.get(i);
				sub.setName(i + "");
				sub.setType(sub.getKind().name());
				catchers.addChild(sub);
				sub.setParent(catchers);
			}
		}

		JavacASTNode finalizer = traverse(t.finalizer);
		if (finalizer != null) {
			finalizer.setName("finalizer");
			finalizer.setType(finalizer.getKind().name());
			finalizer.setParent(currnode);
			currnode.addChild(finalizer);
		}

		return currnode;
	}

	@Override
	public JavacASTNode visitTypeCast(TypeCastTree node, Void p) {
		JCTypeCast t = (JCTypeCast) node;
		JavacASTNode currnode = new JavacASTNode(t.getKind());

		JavacASTNode clazz = traverse(t.clazz);
		if (clazz != null) {
			clazz.setName("clazz");
			clazz.setType(clazz.getKind().name());
			clazz.setParent(currnode);
			currnode.addChild(clazz);
		}

		JavacASTNode expr = traverse(t.expr);
		if (expr != null) {
			expr.setName("expr");
			expr.setType(expr.getKind().name());
			expr.setParent(currnode);
			currnode.addChild(expr);
		}

		return currnode;
	}

	@Override
	public JavacASTNode visitTypeParameter(TypeParameterTree node, Void p) {
		JCTypeParameter t = (JCTypeParameter) node;
		JavacASTNode currnode = new JavacASTNode(t.getKind());

		if (t.bounds != null && t.bounds.size() > 0) {
			JavacASTNode bounds = new JavacASTNode("bounds", "{List<JCExpression>}");
			bounds.setParent(currnode);
			currnode.addChild(bounds);
			List<JavacASTNode> sp = traverse(t.bounds);
			for (int i = 0; i < sp.size(); i++) {
				JavacASTNode sub = sp.get(i);
				sub.setName(i + "");
				sub.setType(sub.getKind().name());
				bounds.addChild(sub);
				sub.setParent(bounds);
			}
		}
		return currnode;
	}

	@Override
	public JavacASTNode visitUnary(UnaryTree node, Void p) {
		JCUnary t = (JCUnary) node;
		JavacASTNode currnode = new JavacASTNode(t.getKind());

		JavacASTNode arg = traverse(t.arg);
		if (arg != null) {
			arg.setName("arg");
			arg.setType(arg.getKind().name());
			arg.setParent(currnode);
			currnode.addChild(arg);
		}

		return currnode;
	}

	@Override
	public JavacASTNode visitUnionType(UnionTypeTree node, Void p) {
		JCTypeUnion t = (JCTypeUnion) node;
		JavacASTNode currnode = new JavacASTNode(t.getKind());

		if (t.alternatives != null && t.alternatives.size() > 0) {
			JavacASTNode alternatives = new JavacASTNode("alternatives", "{List<JCExpression>}");
			alternatives.setParent(currnode);
			currnode.addChild(alternatives);
			List<JavacASTNode> sp = traverse(t.alternatives);
			for (int i = 0; i < sp.size(); i++) {
				JavacASTNode sub = sp.get(i);
				sub.setName(i + "");
				sub.setType(sub.getKind().name());
				alternatives.addChild(sub);
				sub.setParent(alternatives);
			}
		}
		return currnode;
	}

	@Override
	public JavacASTNode visitVariable(VariableTree node, Void p) {
		JCVariableDecl t = (JCVariableDecl) node;
		JavacASTNode currnode = new JavacASTNode(t.getKind());

		JavacASTNode mods = traverse(t.mods);
		if (mods != null) {
			mods.setName("mods");
			mods.setType(mods.getKind().name());
			mods.setParent(currnode);
			currnode.addChild(mods);
		}

		JavacASTNode vartype = traverse(t.vartype);
		if (vartype != null) {
			vartype.setName("vartype");
			vartype.setType(vartype.getKind().name());
			vartype.setParent(currnode);
			currnode.addChild(vartype);
		}

		JavacASTNode init = traverse(t.init);
		if (init != null) {
			init.setName("init");
			init.setType(init.getKind().name());
			init.setParent(currnode);
			currnode.addChild(init);
		}

		return currnode;
	}

	@Override
	public JavacASTNode visitWhileLoop(WhileLoopTree node, Void p) {
		JCWhileLoop t = (JCWhileLoop) node;
		JavacASTNode currnode = new JavacASTNode(t.getKind());

		JavacASTNode body = traverse(t.body);
		if (body != null) {
			body.setName("body");
			body.setType(body.getKind().name());
			body.setParent(currnode);
			currnode.addChild(body);
		}

		JavacASTNode cond = traverse(t.cond);
		if (cond != null) {
			cond.setName("cond");
			cond.setType(cond.getKind().name());
			cond.setParent(currnode);
			currnode.addChild(cond);
		}

		return currnode;

	}

	@Override
	public JavacASTNode visitWildcard(WildcardTree node, Void p) {
		JCWildcard t = (JCWildcard) node;
		JavacASTNode currnode = new JavacASTNode(t.getKind());

		JavacASTNode inner = traverse(t.inner);
		if (inner != null) {
			inner.setName("inner");
			inner.setType(inner.getKind().name());
			inner.setParent(currnode);
			currnode.addChild(inner);
		}

		/*
		 * TypeBoundKind kind = M.at(t.kind.pos).TypeBoundKind(t.kind.kind); JCTree
		 * inner = copy(t.inner, p);
		 */
		return currnode;
	}

}
