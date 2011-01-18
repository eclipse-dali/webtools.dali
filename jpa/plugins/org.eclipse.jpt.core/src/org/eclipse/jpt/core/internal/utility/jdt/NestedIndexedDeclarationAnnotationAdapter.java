/*******************************************************************************
 * Copyright (c) 2006, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.utility.jdt;

import java.util.List;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.jdt.core.dom.ArrayInitializer;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.MemberValuePair;
import org.eclipse.jdt.core.dom.NormalAnnotation;
import org.eclipse.jdt.core.dom.SingleMemberAnnotation;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.IndexedDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.ModifiedDeclaration;

/**
 * Manipulate an annotation that is embedded in an element array within
 * another annotation, e.g.
 * <pre>
 *     &#64;Outer(foo={&#64;Inner("zero"), &#64;Inner("one"), &#64;Inner("two")})
 *     private int id;
 *         outerAnnotationAdapter = AnnotationAdapter<&#64;Outer>
 *         elementName = "foo"
 *         index = 0-2
 *         annotationName = "Inner"
 * </pre>
 */
public class NestedIndexedDeclarationAnnotationAdapter
	extends AbstractNestedDeclarationAnnotationAdapter
	implements IndexedDeclarationAnnotationAdapter
{
	private int index;


	// ********** constructors **********

	/**
	 * The default element name is <code>value</code>.
	 */
	public NestedIndexedDeclarationAnnotationAdapter(DeclarationAnnotationAdapter annotationAdapter, int index, String annotationName) {
		super(annotationAdapter, annotationName);
		this.index = index;
	}

	public NestedIndexedDeclarationAnnotationAdapter(DeclarationAnnotationAdapter annotationAdapter, String elementName, int index, String annotationName) {
		super(annotationAdapter, elementName, annotationName);
		this.index = index;
	}


	// ********** AbstractNestedDeclarationAnnotationAdapter implementation **********

	@Override
	protected Annotation getAnnotation(Expression value) {
		if (value.getNodeType() == ASTNode.ARRAY_INITIALIZER) {
			return this.annotation((ArrayInitializer) value);
		}
		return (this.index == 0) ? this.annotationValue(value) : null;
	}

	@Override
	protected Expression buildNewInnerExpression(Annotation inner) {
		return (this.index == 0) ? inner : (Expression) this.buildNewInnerArrayInitializer(inner);
	}

	@Override
	protected boolean removeAnnotation(ModifiedDeclaration declaration, Annotation outer, Expression value) {
		if (value.getNodeType() == ASTNode.ARRAY_INITIALIZER) {
			this.removeAnnotation(declaration, outer, (ArrayInitializer) value);
			return true;
		}
		// if our index is greater than zero, but we don't have an array,
		// then the annotation must already be gone
		return (this.index != 0);
	}

	/**
	 * <pre>
	 * &#64;Outer({&#64;Inner(0), &#64;Inner(1)}) => &#64;Outer({&#64;Inner(0), &#64;Inner(1), &#64;Inner(2)})
	 *     or
	 * &#64;Outer("lorem ipsum") => &#64;Outer(&#64;Inner(0))
	 *     or
	 * &#64;Outer(&#64;Inner(0)) => &#64;Outer({&#64;Inner(0), &#64;Inner(1)})
	 * </pre>
	 */
	@Override
	protected void modifyAnnotationValue(SingleMemberAnnotation outer, Annotation inner) {
		this.modifyExpression(outer, SINGLE_MEMBER_ANNOTATION_EXPRESSION_PROVIDER, inner);
	}

	/**
	 * <pre>
	 * &#64;Outer(text="lorem ipsum") => &#64;Outer(text="lorem ipsum", foo=&#64;Inner(0))
	 *     or
	 * &#64;Outer(foo={&#64;Inner(0), &#64;Inner(1)}) => &#64;Outer(foo={&#64;Inner(0), &#64;Inner(1), &#64;Inner(2)})
	 *     or
	 * &#64;Outer(foo="lorem ipsum") => &#64;Outer(foo=&#64;Inner(0))
	 *     or
	 * &#64;Outer(foo=&#64;NotInner) => &#64;Outer(foo=&#64;Inner(0))
	 *     or
	 * &#64;Outer(foo=&#64;Inner(0)) => &#64;Outer(foo={&#64;Inner(0), &#64;Inner(1)})
	 * </pre>
	 */
	@Override
	protected void modifyMemberValuePair(MemberValuePair pair, Annotation inner) {
		this.modifyExpression(pair, MEMBER_VALUE_PAIR_EXPRESSION_PROVIDER, inner);
	}


	// ********** IndexedDeclarationAnnotationAdapter implementation **********

	public int getIndex() {
		return this.index;
	}

	/**
	 * Move the annotation to the specified index, leaving its original
	 * position cleared out.
	 */
	public void moveAnnotation(int newIndex, ModifiedDeclaration declaration) {
		int oldIndex = this.index;
		if (newIndex == oldIndex) {
			return;
		}

		Annotation original = this.getAnnotation(declaration);
		if (original == null) {
			this.index = newIndex;
			this.removeAnnotation(declaration);  // clear out the new location (?)
		} else {
			Annotation copy = (Annotation) ASTNode.copySubtree(original.getAST(), original);
			this.index = newIndex;
			this.addAnnotation(declaration, copy);  // install the copy in the new location
			this.index = oldIndex;
			this.removeAnnotation(declaration);  // go back and clear out the original location (AFTER the move)
			this.index = newIndex;
		}
	}


	// ********** internal methods **********

	/**
	 * Return the adapter's annotation from the specified array initializer.
	 */
	private Annotation annotation(ArrayInitializer value) {
		List<Expression> expressions = this.expressions(value);
		return (this.index >= expressions.size()) ? null : this.annotationValue(expressions.get(this.index));
	}

	/**
	 * Build a new array initializer to hold the specified annotation,
	 * padding it with 'null' literals as necessary
	 */
	private ArrayInitializer buildNewInnerArrayInitializer(Annotation inner) {
		ArrayInitializer ai = inner.getAST().newArrayInitializer();
		this.addInnerToExpressions(inner, this.expressions(ai));
		return ai;
	}

	/**
	 * Add the specified annotation to the specified array initializer,
	 * padding it with 'null' literals as necessary
	 */
	private void addInnerToExpressions(Annotation inner, List<Expression> expressions) {
		if (expressions.size() > this.index) {
			throw new IllegalStateException("expressions size is greater than index (size: " + expressions.size() + "  index: " + this.index + ")"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		}
		while (expressions.size() < this.index) {
			expressions.add(inner.getAST().newNullLiteral());
		}
		expressions.add(inner);
	}

	/**
	 * Remove the adapter's annotation from the specified array initializer.
	 */
	private void removeAnnotation(ModifiedDeclaration declaration, Annotation outer, ArrayInitializer value) {
		List<Expression> expressions = this.expressions(value);
		if (this.index >= expressions.size()) {
			return;  // avoid IndexOutOfBoundsException(?)
		}
		Annotation inner = this.annotationValue(expressions.get(this.index));
		if (inner == null) {
			return;
		}
		if ( ! this.nameMatches(declaration, inner)) {
			return;
		}
		if (this.index == (expressions.size() - 1)) {
			expressions.remove(this.index);
		} else {
			expressions.set(this.index, value.getAST().newNullLiteral());
		}
		this.trimExpressions(declaration, outer, expressions);
	}

	/**
	 * Strip all the null literals off the end of the specified list of expressions
	 * and normalize the specified outer annotation.
	 */
	private void trimExpressions(ModifiedDeclaration declaration, Annotation outer, List<Expression> expressions) {
		// start at the end of the list
		for (int i = expressions.size(); i-- > 0; ) {
			if (expressions.get(i).getNodeType() == ASTNode.NULL_LITERAL) {
				expressions.remove(i);
			} else {
				break;  // stop with the first non-null literal encountered
			}
		}
		switch (expressions.size()) {
			case 0:
				this.removeElementAndNormalize(declaration, outer);
				break;
			case 1:
				this.convertArrayToLastRemainingExpression(outer, expressions.get(0));
				break;
			default:
				break;
		}
	}

	/**
	 * When there is only a single element in an array initializer, convert the
	 * expression to be just the single element; e.g.
	 * <pre>
	 *     &#64;Foo(xxx={"abc"}) => &#64;Foo(xxx="abc")
	 * or
	 *     &#64;Foo({"abc"}) => &#64;Foo("abc")
	 * </pre>
	 */
	private void convertArrayToLastRemainingExpression(Annotation outer, Expression lastValue) {
		lastValue = (Expression) ASTNode.copySubtree(lastValue.getAST(), lastValue);
		if (outer.isNormalAnnotation()) {
			this.memberValuePair((NormalAnnotation) outer).setValue(lastValue);
		} else if (outer.isSingleMemberAnnotation()) {
			((SingleMemberAnnotation) outer).setValue(lastValue);
		} else {
			throw new IllegalArgumentException("unexpected annotation type: " + outer); //$NON-NLS-1$
		}
	}

	/**
	 * Manipulate the specified expression appropriately.
	 * If it is an array initializer, add the specified annotation to it.
	 * If it is not, replace the expression or convert it into an array
	 * initializer.
	 */
	private void modifyExpression(ASTNode node, ExpressionProvider expProvider, Annotation inner) {
		Expression value = expProvider.getExpression(node);
		if (value.getNodeType() == ASTNode.ARRAY_INITIALIZER) {
			// ignore the other entries in the array initializer(?) - they may not be matching Annotations...
			List<Expression> expressions = this.expressions((ArrayInitializer) value);
			if (this.index >= expressions.size()) {
				this.addInnerToExpressions(inner, expressions);
			} else {
				expressions.set(this.index, inner);
			}
		} else {
			if (this.index == 0) {
				// replace whatever was there before
				expProvider.setExpression(node, inner);
			} else {
				// convert to an array
				ArrayInitializer ai = inner.getAST().newArrayInitializer();
				List<Expression> expressions = this.expressions(ai);
				expressions.add((Expression) ASTNode.copySubtree(value.getAST(), value));
				this.addInnerToExpressions(inner, expressions);
				expProvider.setExpression(node, ai);
			}
		}
	}

	@SuppressWarnings("unchecked")
	protected List<Expression> expressions(ArrayInitializer ai) {
		return ai.expressions();
	}


	// ********** expression providers **********

	/**
	 * define interface that allows us to "re-use" the code in
	 * #modifyExpression(ASTNode, ExpressionProvider, Annotation)
	 */
	private interface ExpressionProvider {
		Expression getExpression(ASTNode node);
		void setExpression(ASTNode node, Expression expression);
	}

	private static final ExpressionProvider MEMBER_VALUE_PAIR_EXPRESSION_PROVIDER = new ExpressionProvider() {
		public Expression getExpression(ASTNode node) {
			return ((MemberValuePair) node).getValue();
		}
		public void setExpression(ASTNode node, Expression expression) {
			((MemberValuePair) node).setValue(expression);
		}
		@Override
		public String toString() {
			return "MemberValuePairExpressionProvider"; //$NON-NLS-1$
		}
	};

	private static final ExpressionProvider SINGLE_MEMBER_ANNOTATION_EXPRESSION_PROVIDER = new ExpressionProvider() {
		public Expression getExpression(ASTNode node) {
			return ((SingleMemberAnnotation) node).getValue();
		}
		public void setExpression(ASTNode node, Expression expression) {
			((SingleMemberAnnotation) node).setValue(expression);
		}
		@Override
		public String toString() {
			return "SingleMemberAnnotationExpressionProvider"; //$NON-NLS-1$
		}
	};

}
