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

import java.util.Iterator;
import java.util.List;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.MarkerAnnotation;
import org.eclipse.jdt.core.dom.MemberValuePair;
import org.eclipse.jdt.core.dom.NormalAnnotation;
import org.eclipse.jdt.core.dom.SingleMemberAnnotation;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.utility.jdt.ModifiedDeclaration;
import org.eclipse.jpt.utility.internal.StringTools;

/**
 * Most obvious implementation of the interface.
 * Assume the element's value is an Expression.
 */
public class ExpressionDeclarationAnnotationElementAdapter<E extends Expression>
	implements DeclarationAnnotationElementAdapter<E>
{
	/**
	 * Adapter used to manipulate the element's annotation.
	 */
	private final DeclarationAnnotationAdapter annotationAdapter;

	/**
	 * The name of the relevant annotation element.
	 */
	private final String elementName;

	/**
	 * Flag to indicate whether the element's annotation is to be
	 * completely removed if, when the element itself is removed,
	 * the annotation has no remaining elements.
	 */
	private final boolean removeAnnotationWhenEmpty;


	// ********** constructors **********

	/**
	 * The default element name is "value"; the default behavior is to
	 * remove the annotation when the last element is removed.
	 */
	public ExpressionDeclarationAnnotationElementAdapter(DeclarationAnnotationAdapter annotationAdapter) {
		this(annotationAdapter, "value");
	}

	/**
	 * The default element name is "value".
	 */
	public ExpressionDeclarationAnnotationElementAdapter(DeclarationAnnotationAdapter annotationAdapter, boolean removeAnnotationWhenEmpty) {
		this(annotationAdapter, "value", removeAnnotationWhenEmpty);
	}

	/**
	 * The default behavior is to remove the annotation when the last
	 * element is removed.
	 */
	public ExpressionDeclarationAnnotationElementAdapter(DeclarationAnnotationAdapter annotationAdapter, String elementName) {
		this(annotationAdapter, elementName, true);
	}

	public ExpressionDeclarationAnnotationElementAdapter(DeclarationAnnotationAdapter annotationAdapter, String elementName, boolean removeAnnotationWhenEmpty) {
		super();
		this.annotationAdapter = annotationAdapter;
		this.elementName = elementName;
		this.removeAnnotationWhenEmpty = removeAnnotationWhenEmpty;
	}


	// ********** DeclarationAnnotationElementAdapter implementation **********

	public E getValue(ModifiedDeclaration declaration) {
		// return the expression unmodified
		return this.expression(declaration);
	}

	public void setValue(E value, ModifiedDeclaration declaration) {
		this.setValue(value, this.annotationAdapter.getAnnotation(declaration), declaration);
	}

	public E expression(ModifiedDeclaration declaration) {
		return this.expression(this.annotationAdapter.getAnnotation(declaration));
	}

	public ASTNode astNode(ModifiedDeclaration declaration) {
		Expression exp = this.expression(declaration);
		return (exp != null) ? exp : this.annotationAdapter.astNode(declaration);
	}

	@Override
	public String toString() {
		return StringTools.buildToStringFor(this, this.elementName);
	}


	// ********** expression **********

	/**
	 * Return the expression value of the *first* annotation element
	 * with the adapter's element name.
	 * Return null if the annotation has no such element.
	 * (An element name of "value" will return the value of a single
	 * member annotation.)
	 */
	protected E expression(Annotation annotation) {
		if (annotation == null) {
			return this.expressionNoAnnotation();
		}
		if (annotation.isMarkerAnnotation()) {
			return this.expressionMarkerAnnotation((MarkerAnnotation) annotation);
		}
		if (annotation.isSingleMemberAnnotation()) {
			return this.expressionSingleMemberAnnotation((SingleMemberAnnotation) annotation);
		}
		if (annotation.isNormalAnnotation()) {
			return this.expressionNormalAnnotation((NormalAnnotation) annotation);
		}
		throw new IllegalArgumentException("unknown annotation type: " + annotation);
	}

	protected E expressionNoAnnotation() {
		return null;
	}

	/**
	 * Return the expression value of the *first* annotation element
	 * with the adapter's element name.
	 * Return null if the annotation has no such element.
	 */
	protected E expressionMarkerAnnotation(MarkerAnnotation annotation) {
		return null;
	}

	/**
	 * Return the expression value of the *first* annotation element
	 * with the adapter's element name.
	 * Return null if the annotation has no such element.
	 */
	protected E expressionSingleMemberAnnotation(SingleMemberAnnotation annotation) {
		return this.downcast(this.elementName.equals("value") ? annotation.getValue() : null);
	}

	@SuppressWarnings("unchecked")
	private E downcast(Expression e) {
		return (E) e;
	}

	/**
	 * Return the expression value of the *first* annotation element
	 * with the adapter's element name.
	 * Return null if the annotation has no such element.
	 */
	protected E expressionNormalAnnotation(NormalAnnotation annotation) {
		MemberValuePair pair = this.memberValuePair(annotation);
		return this.downcast((pair == null) ? null : pair.getValue());
	}


	// ********** set value **********

	/**
	 * set non-null, non-empty value
	 */
	protected void setValue(Expression value, Annotation annotation, ModifiedDeclaration declaration) {
		if (value == null) {
			this.removeElement(annotation, declaration);
		}
		else if (annotation == null) {
			this.setValueNoAnnotation(value, declaration);
		}
		else if (annotation.isMarkerAnnotation()) {
			this.setValueMarkerAnnotation(value, (MarkerAnnotation) annotation, declaration);
		}
		else if (annotation.isSingleMemberAnnotation()) {
			this.setValueSingleMemberAnnotation(value, (SingleMemberAnnotation) annotation, declaration);
		}
		else if (annotation.isNormalAnnotation()) {
			this.setValueNormalAnnotation(value, (NormalAnnotation) annotation, declaration);
		}
		else {
			throw new IllegalArgumentException("unknown annotation type: " + annotation);
		}
	}

	/**
	 * add non-null, non-empty value
	 */
	protected void setValueNoAnnotation(Expression value, ModifiedDeclaration declaration) {
		if (this.elementName.equals("value")) {
			// @Foo("xxx")
			this.annotationAdapter.newSingleMemberAnnotation(declaration).setValue(value);
		} else {
			// @Foo(bar="xxx")
			this.addValue(value, this.annotationAdapter.newNormalAnnotation(declaration));
		}
	}

	protected void addValue(Expression value, NormalAnnotation annotation) {
		this.addValue(value, annotation, this.elementName);
	}

	protected void addValue(Expression value, NormalAnnotation annotation, String annotationElementName) {
		AST ast = annotation.getAST();
		MemberValuePair pair = ast.newMemberValuePair();
		pair.setName(ast.newSimpleName(annotationElementName));
		pair.setValue(value);
		List<MemberValuePair> values = this.values(annotation);
		values.add(pair);
	}
	
	protected void setValueMarkerAnnotation(Expression value, MarkerAnnotation annotation, ModifiedDeclaration declaration) {
		// @Foo => @Foo("xxx")
		//     or
		// @Foo => @Foo(bar="xxx")
		this.setValueNoAnnotation(value, declaration);
	}

	protected void setValueSingleMemberAnnotation(Expression value, SingleMemberAnnotation annotation, ModifiedDeclaration declaration) {
		if (this.elementName.equals("value")) {
			// @Foo("yyy") => @Foo("xxx")
			annotation.setValue(value);
		} else {
			// @Foo("yyy") => @Foo(value="yyy", bar="xxx")
			Expression vv = annotation.getValue();
			vv = (Expression) ASTNode.copySubtree(vv.getAST(), vv);
			NormalAnnotation normalAnnotation = this.annotationAdapter.newNormalAnnotation(declaration);
			this.addValue(vv, normalAnnotation, "value");
			this.addValue(value, normalAnnotation);
		}
	}

	protected void setValueNormalAnnotation(Expression value, NormalAnnotation annotation, ModifiedDeclaration declaration) {
		MemberValuePair pair = this.memberValuePair(annotation);
		if (pair == null) {
			this.addValue(value, annotation);
		} else {
			pair.setValue(value);
		}
	}


	// ********** remove element **********

	protected void removeElement(Annotation annotation, ModifiedDeclaration declaration) {
		if (annotation == null) {
			this.removeElementNoAnnotation(declaration);
		}
		else if (annotation.isMarkerAnnotation()) {
			this.removeElementMarkerAnnotation((MarkerAnnotation) annotation, declaration);
		}
		else if (annotation.isSingleMemberAnnotation()) {
			this.removeElementSingleMemberAnnotation((SingleMemberAnnotation) annotation, declaration);
		}
		else if (annotation.isNormalAnnotation()) {
			this.removeElementNormalAnnotation((NormalAnnotation) annotation, declaration);
		}
		else {
			throw new IllegalArgumentException("unknown annotation type: " + annotation);
		}
	}

	protected void removeElementNoAnnotation(ModifiedDeclaration declaration) {
		// the element is already gone (?)
	}

	protected void removeElementMarkerAnnotation(MarkerAnnotation annotation, ModifiedDeclaration declaration) {
		// the element is already gone (?)
	}

	protected void removeElementSingleMemberAnnotation(SingleMemberAnnotation annotation, ModifiedDeclaration declaration) {
		if (this.elementName.equals("value")) {
			if (this.removeAnnotationWhenEmpty) {
				// @Foo("xxx") => 
				this.annotationAdapter.removeAnnotation(declaration);
			} else {
				// @Foo("xxx") => @Foo
				this.annotationAdapter.newMarkerAnnotation(declaration);
			}
		} else {
			// the [non-'value'] element is already gone (?)
		}
	}

	protected void removeElementNormalAnnotation(NormalAnnotation annotation, ModifiedDeclaration declaration) {
		List<MemberValuePair> values = this.values(annotation);
		if ((values.size() == 1) && values.get(0).getName().getFullyQualifiedName().equals(this.elementName)) {
			if (this.removeAnnotationWhenEmpty) {
				// @Foo(bar="xxx") => 
				this.annotationAdapter.removeAnnotation(declaration);
			} else {
				// @Foo(bar="xxx") => @Foo
				this.annotationAdapter.newMarkerAnnotation(declaration);
			}
		} else {
			this.removeElement(annotation);
			if (values.size() == 1) {
				MemberValuePair pair = values.get(0);
				if (pair.getName().getFullyQualifiedName().equals("value")) {
					// @Foo(bar="xxx", value="yyy") => @Foo("yyy")
					Expression vv = pair.getValue();
					vv = (Expression) ASTNode.copySubtree(vv.getAST(), vv);
					this.annotationAdapter.newSingleMemberAnnotation(declaration).setValue(vv);
				} else {
					// @Foo(bar="xxx", baz="yyy") => @Foo(baz="yyy")
				}
			} else {
				// @Foo(bar="xxx", baz="yyy", joo="xxx") => @Foo(baz="yyy", joo="xxx")
			}
		}
	}

	/**
	 * Remove the *first* member value pair from the specified annotation element
	 * with the adapter's element name.
	 */
	protected void removeElement(NormalAnnotation annotation) {
		for (Iterator<MemberValuePair> stream = this.values(annotation).iterator(); stream.hasNext(); ) {
			MemberValuePair pair = stream.next();
			if (pair.getName().getFullyQualifiedName().equals(this.elementName)) {
				stream.remove();
			}
		}
	}


	// ********** convenience methods **********

	/**
	 * Return the *first* member value pair for the specified annotation element
	 * with the adapter's element name.
	 * Return null if the annotation has no such element.
	 */
	protected MemberValuePair memberValuePair(NormalAnnotation annotation) {
		for (MemberValuePair pair : this.values(annotation)) {
			if (pair.getName().getFullyQualifiedName().equals(this.elementName)) {
				return pair;
			}
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	protected List<MemberValuePair> values(NormalAnnotation na) {
		return na.values();
	}

}
