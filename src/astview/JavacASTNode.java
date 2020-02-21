package astview;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sun.source.tree.Tree;
import com.sun.source.tree.Tree.Kind;

//ANNOTATION(AnnotationTree.class),
//ARRAY_ACCESS(ArrayAccessTree.class),
//ARRAY_TYPE(ArrayTypeTree.class),
//ASSERT(AssertTree.class),
//ASSIGNMENT(AssignmentTree.class),
//BLOCK(BlockTree.class),
//BREAK(BreakTree.class),
//CASE(CaseTree.class),
//CATCH(CatchTree.class),
//CLASS(ClassTree.class),
//COMPILATION_UNIT(CompilationUnitTree.class),
//CONDITIONAL_EXPRESSION(ConditionalExpressionTree.class),
//CONTINUE(ContinueTree.class),
//DO_WHILE_LOOP(DoWhileLoopTree.class),
//ENHANCED_FOR_LOOP(EnhancedForLoopTree.class),
//EXPRESSION_STATEMENT(ExpressionStatementTree.class),
//MEMBER_SELECT(MemberSelectTree.class),
//FOR_LOOP(ForLoopTree.class),
//IDENTIFIER(IdentifierTree.class),
//IF(IfTree.class),
//IMPORT(ImportTree.class),
//INSTANCE_OF(InstanceOfTree.class),
//LABELED_STATEMENT(LabeledStatementTree.class),
//METHOD(MethodTree.class),
//METHOD_INVOCATION(MethodInvocationTree.class),
//MODIFIERS(ModifiersTree.class),
//NEW_ARRAY(NewArrayTree.class),
//NEW_CLASS(NewClassTree.class),
//PARENTHESIZED(ParenthesizedTree.class),
//PRIMITIVE_TYPE(PrimitiveTypeTree.class),
//RETURN(ReturnTree.class),
//EMPTY_STATEMENT(EmptyStatementTree.class),
//SWITCH(SwitchTree.class),
//SYNCHRONIZED(SynchronizedTree.class),
//THROW(ThrowTree.class),
//TRY(TryTree.class),
//PARAMETERIZED_TYPE(ParameterizedTypeTree.class),
//UNION_TYPE(UnionTypeTree.class),
//TYPE_CAST(TypeCastTree.class),
//TYPE_PARAMETER(TypeParameterTree.class),
//VARIABLE(VariableTree.class),
//WHILE_LOOP(WhileLoopTree.class),
//POSTFIX_INCREMENT(UnaryTree.class),
//POSTFIX_DECREMENT(UnaryTree.class),
//PREFIX_INCREMENT(UnaryTree.class),
//PREFIX_DECREMENT(UnaryTree.class),
//UNARY_PLUS(UnaryTree.class),
//UNARY_MINUS(UnaryTree.class),
//BITWISE_COMPLEMENT(UnaryTree.class),
//LOGICAL_COMPLEMENT(UnaryTree.class),
//MULTIPLY(BinaryTree.class),
//DIVIDE(BinaryTree.class),
//REMAINDER(BinaryTree.class),
//PLUS(BinaryTree.class),
//MINUS(BinaryTree.class),
//LEFT_SHIFT(BinaryTree.class),
//RIGHT_SHIFT(BinaryTree.class),
//UNSIGNED_RIGHT_SHIFT(BinaryTree.class),
//LESS_THAN(BinaryTree.class),
//GREATER_THAN(BinaryTree.class),
//LESS_THAN_EQUAL(BinaryTree.class),
//GREATER_THAN_EQUAL(BinaryTree.class),
//EQUAL_TO(BinaryTree.class),
//NOT_EQUAL_TO(BinaryTree.class),
// * Used for instances of {@link BinaryTree} representing
// * bitwise and logical "and" {@code &}.
// */
//AND(BinaryTree.class),
//
///**
// * Used for instances of {@link BinaryTree} representing
// * bitwise and logical "xor" {@code ^}.
// */
//XOR(BinaryTree.class),
//
///**
// * Used for instances of {@link BinaryTree} representing
// * bitwise and logical "or" {@code |}.
// */
//OR(BinaryTree.class),
//
///**
// * Used for instances of {@link BinaryTree} representing
// * conditional-and {@code &&}.
// */
//CONDITIONAL_AND(BinaryTree.class),
//
///**
// * Used for instances of {@link BinaryTree} representing
// * conditional-or {@code ||}.
// */
//CONDITIONAL_OR(BinaryTree.class),
//
///**
// * Used for instances of {@link CompoundAssignmentTree} representing
// * multiplication assignment {@code *=}.
// */
//MULTIPLY_ASSIGNMENT(CompoundAssignmentTree.class),
//
///**
// * Used for instances of {@link CompoundAssignmentTree} representing
// * division assignment {@code /=}.
// */
//DIVIDE_ASSIGNMENT(CompoundAssignmentTree.class),
//
///**
// * Used for instances of {@link CompoundAssignmentTree} representing
// * remainder assignment {@code %=}.
// */
//REMAINDER_ASSIGNMENT(CompoundAssignmentTree.class),
//
///**
// * Used for instances of {@link CompoundAssignmentTree} representing
// * addition or string concatenation assignment {@code +=}.
// */
//PLUS_ASSIGNMENT(CompoundAssignmentTree.class),
//
///**
// * Used for instances of {@link CompoundAssignmentTree} representing
// * subtraction assignment {@code -=}.
// */
//MINUS_ASSIGNMENT(CompoundAssignmentTree.class),
//
///**
// * Used for instances of {@link CompoundAssignmentTree} representing
// * left shift assignment {@code <<=}.
// */
//LEFT_SHIFT_ASSIGNMENT(CompoundAssignmentTree.class),
//
///**
// * Used for instances of {@link CompoundAssignmentTree} representing
// * right shift assignment {@code >>=}.
// */
//RIGHT_SHIFT_ASSIGNMENT(CompoundAssignmentTree.class),
//
///**
// * Used for instances of {@link CompoundAssignmentTree} representing
// * unsigned right shift assignment {@code >>>=}.
// */
//UNSIGNED_RIGHT_SHIFT_ASSIGNMENT(CompoundAssignmentTree.class),
//
///**
// * Used for instances of {@link CompoundAssignmentTree} representing
// * bitwise and logical "and" assignment {@code &=}.
// */
//AND_ASSIGNMENT(CompoundAssignmentTree.class),
//
///**
// * Used for instances of {@link CompoundAssignmentTree} representing
// * bitwise and logical "xor" assignment {@code ^=}.
// */
//XOR_ASSIGNMENT(CompoundAssignmentTree.class),
//
///**
// * Used for instances of {@link CompoundAssignmentTree} representing
// * bitwise and logical "or" assignment {@code |=}.
// */
//OR_ASSIGNMENT(CompoundAssignmentTree.class),
//
///**
// * Used for instances of {@link LiteralTree} representing
// * an integral literal expression of type {@code int}.
// */
//INT_LITERAL(LiteralTree.class),
//
///**
// * Used for instances of {@link LiteralTree} representing
// * an integral literal expression of type {@code long}.
// */
//LONG_LITERAL(LiteralTree.class),
//
///**
// * Used for instances of {@link LiteralTree} representing
// * a floating-point literal expression of type {@code float}.
// */
//FLOAT_LITERAL(LiteralTree.class),
//
///**
// * Used for instances of {@link LiteralTree} representing
// * a floating-point literal expression of type {@code double}.
// */
//DOUBLE_LITERAL(LiteralTree.class),
//
///**
// * Used for instances of {@link LiteralTree} representing
// * a boolean literal expression of type {@code boolean}.
// */
//BOOLEAN_LITERAL(LiteralTree.class),
//
///**
// * Used for instances of {@link LiteralTree} representing
// * a character literal expression of type {@code char}.
// */
//CHAR_LITERAL(LiteralTree.class),
//
///**
// * Used for instances of {@link LiteralTree} representing
// * a string literal expression of type {@link String}.
// */
//STRING_LITERAL(LiteralTree.class),
//
///**
// * Used for instances of {@link LiteralTree} representing
// * the use of {@code null}.
// */
//NULL_LITERAL(LiteralTree.class),
//
///**
// * Used for instances of {@link WildcardTree} representing
// * an unbounded wildcard type argument.
// */
//UNBOUNDED_WILDCARD(WildcardTree.class),
//
///**
// * Used for instances of {@link WildcardTree} representing
// * an extends bounded wildcard type argument.
// */
//EXTENDS_WILDCARD(WildcardTree.class),
//
///**
// * Used for instances of {@link WildcardTree} representing
// * a super bounded wildcard type argument.
// */
//SUPER_WILDCARD(WildcardTree.class),
//
///**
// * Used for instances of {@link ErroneousTree}.
// */
//ERRONEOUS(ErroneousTree.class),
//
///**
// * Used for instances of {@link ClassTree} representing interfaces.
// */
//INTERFACE(ClassTree.class),
//
///**
// * Used for instances of {@link ClassTree} representing enums.
// */
//ENUM(ClassTree.class),
//
///**
// * Used for instances of {@link ClassTree} representing annotation types.
// */
//ANNOTATION_TYPE(ClassTree.class),
//
///**
// * An implementation-reserved node. This is the not the node
// * you are looking for.
// */
//OTHER(null);
public class JavacASTNode {
	
	private String name;
	private String type;
	private String value;
	
	private List<JavacASTNode> children = null;
	private JavacASTNode parent = null;
	
	public JavacASTNode(String name, String type) {
		this.name = name;
		this.type = type;
		children = new ArrayList<JavacASTNode>();
	}

	public JavacASTNode(String name, String type, String value) {
		this(name, type);
		this.value = value;
	}
	
	
	public JavacASTNode() {
		children = new ArrayList<JavacASTNode>();
	}

	public List<JavacASTNode> getChildren() {
		return children;
	}

	public void setChildren(List<JavacASTNode> children) {
		this.children = children;
	}
	
	public void addChild(JavacASTNode node) {
		children.add(node);
	}

	public JavacASTNode getParent() {
		return parent;
	}

	public void setParent(JavacASTNode parent) {
		this.parent = parent;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	

	public String toString() {
		String display = name + "={" + type + "}";
		if (value != null && value.length() > 0) {
			display = display + " " + value.trim();
		}
	
		return display;
	}

}




