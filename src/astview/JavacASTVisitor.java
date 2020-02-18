package astview;

import com.sun.source.tree.*;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.JCTree.*;
import com.sun.tools.javac.util.List;
import com.sun.tools.javac.util.ListBuffer;

public class JavacASTVisitor implements TreeVisitor<JCTree,JavacASTNode> {
	
	//  public abstract <R,D> R accept(TreeVisitor<R,D> v, D d);
	
	
    public <T extends JCTree> T traverse(T tree, JavacASTNode p) {
        if (tree == null)
            return null;
        return (T) (tree.accept(this, p));
    }

    public <T extends JCTree> List<T> traverse(List<T> trees, JavacASTNode p) {
        if (trees == null)
            return null;
        ListBuffer<T> lb = new ListBuffer<T>();
        for (T tree: trees)
            lb.append(traverse(tree, p));
        return lb.toList();
    }
    
    
    @Override
	public JCTree visitCompilationUnit(CompilationUnitTree node, JavacASTNode curr) {
	   JCCompilationUnit t = (JCCompilationUnit) node;
//       List<JCAnnotation> packageAnnotations = copy(t.packageAnnotations, p);
	   JavacASTNode pid =  new JavacASTNode("pid","JCExpression");
	   JavacASTNode defs = new JavacASTNode("defs","List<JCTree>");
	   
//       List<JCTree> defs = copy(t.defs, p);
		return null;
	}

	@Override
	public JCTree visitAnnotatedType(AnnotatedTypeTree arg0, JavacASTNode arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JCTree visitAnnotation(AnnotationTree arg0, JavacASTNode arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JCTree visitArrayAccess(ArrayAccessTree arg0, JavacASTNode arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JCTree visitArrayType(ArrayTypeTree arg0, JavacASTNode arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JCTree visitAssert(AssertTree arg0, JavacASTNode arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JCTree visitAssignment(AssignmentTree arg0, JavacASTNode arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JCTree visitBinary(BinaryTree arg0, JavacASTNode arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JCTree visitBlock(BlockTree arg0, JavacASTNode arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JCTree visitBreak(BreakTree arg0, JavacASTNode arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JCTree visitCase(CaseTree arg0, JavacASTNode arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JCTree visitCatch(CatchTree arg0, JavacASTNode arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JCTree visitClass(ClassTree arg0, JavacASTNode arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JCTree visitCompoundAssignment(CompoundAssignmentTree arg0, JavacASTNode arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JCTree visitConditionalExpression(ConditionalExpressionTree arg0, JavacASTNode arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JCTree visitContinue(ContinueTree arg0, JavacASTNode arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JCTree visitDoWhileLoop(DoWhileLoopTree arg0, JavacASTNode arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JCTree visitEmptyStatement(EmptyStatementTree arg0, JavacASTNode arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JCTree visitEnhancedForLoop(EnhancedForLoopTree arg0, JavacASTNode arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JCTree visitErroneous(ErroneousTree arg0, JavacASTNode arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JCTree visitExpressionStatement(ExpressionStatementTree arg0, JavacASTNode arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JCTree visitForLoop(ForLoopTree arg0, JavacASTNode arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JCTree visitIdentifier(IdentifierTree arg0, JavacASTNode arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JCTree visitIf(IfTree arg0, JavacASTNode arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JCTree visitImport(ImportTree arg0, JavacASTNode arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JCTree visitInstanceOf(InstanceOfTree arg0, JavacASTNode arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JCTree visitIntersectionType(IntersectionTypeTree arg0, JavacASTNode arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JCTree visitLabeledStatement(LabeledStatementTree arg0, JavacASTNode arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JCTree visitLambdaExpression(LambdaExpressionTree arg0, JavacASTNode arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JCTree visitLiteral(LiteralTree arg0, JavacASTNode arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JCTree visitMemberReference(MemberReferenceTree arg0, JavacASTNode arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JCTree visitMemberSelect(MemberSelectTree arg0, JavacASTNode arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JCTree visitMethod(MethodTree arg0, JavacASTNode arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JCTree visitMethodInvocation(MethodInvocationTree arg0, JavacASTNode arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JCTree visitModifiers(ModifiersTree arg0, JavacASTNode arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JCTree visitNewArray(NewArrayTree arg0, JavacASTNode arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JCTree visitNewClass(NewClassTree arg0, JavacASTNode arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JCTree visitOther(Tree arg0, JavacASTNode arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JCTree visitParameterizedType(ParameterizedTypeTree arg0, JavacASTNode arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JCTree visitParenthesized(ParenthesizedTree arg0, JavacASTNode arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JCTree visitPrimitiveType(PrimitiveTypeTree arg0, JavacASTNode arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JCTree visitReturn(ReturnTree arg0, JavacASTNode arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JCTree visitSwitch(SwitchTree arg0, JavacASTNode arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JCTree visitSynchronized(SynchronizedTree arg0, JavacASTNode arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JCTree visitThrow(ThrowTree arg0, JavacASTNode arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JCTree visitTry(TryTree arg0, JavacASTNode arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JCTree visitTypeCast(TypeCastTree arg0, JavacASTNode arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JCTree visitTypeParameter(TypeParameterTree arg0, JavacASTNode arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JCTree visitUnary(UnaryTree arg0, JavacASTNode arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JCTree visitUnionType(UnionTypeTree arg0, JavacASTNode arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JCTree visitVariable(VariableTree arg0, JavacASTNode arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JCTree visitWhileLoop(WhileLoopTree arg0, JavacASTNode arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JCTree visitWildcard(WildcardTree arg0, JavacASTNode arg1) {
		// TODO Auto-generated method stub
		return null;
	}
    
    
    	

}
