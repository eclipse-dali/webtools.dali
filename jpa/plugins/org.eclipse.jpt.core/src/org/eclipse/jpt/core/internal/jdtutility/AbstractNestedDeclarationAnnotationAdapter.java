/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jdtutility;

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

/**
 * Pull together some of the behavior common to NestedDeclarationAnnotationAdapter
 * and IndexedNestedDeclarationAnnotationAdapter
 */
public abstract class AbstractNestedDeclarationAnnotationAdapter extends AbstractDeclarationAnnotationAdapter {
	private final DeclarationAnnotationAdapter outerAnnotationAdapter;
	private final String elementName;
	private final boolean removeOuterAnnotationWhenEmpty;


	// ********** constructors **********

	/**
	 * default element name is "value";
	 * default behavior is to remove the outer annotation when it is empty
	 */
	protected AbstractNestedDeclarationAnnotationAdapter(DeclarationAnnotationAdapter outerAnnotationAdapter, String annotationName) {
		this(outerAnnotationAdapter, "value", annotationName);
	}

	/**
	 * default behavior is to remove the outer annotation when it is empty
	 */
	protected AbstractNestedDeclarationAnnotationAdapter(DeclarationAnnotationAdapter outerAnnotationAdapter, String elementName, String annotationName) {
		this(outerAnnotationAdapter, elementName, annotationName, true);
	}

	protected AbstractNestedDeclarationAnnotationAdapter(DeclarationAnnotationAdapter outerAnnotationAdapter, String elementName, String annotationName, boolean removeOuterAnnotationWhenEmpty) {
		super(annotationName);
		this.outerAnnotationAdapter = outerAnnotationAdapter;
		this.elementName = elementName;
		this.removeOuterAnnotationWhenEmpty = removeOuterAnnotationWhenEmpty;
	}


	// ********** AbstractDeclarationAnnotationAdapter implementation **********

	@Override
	public Annotation getAnnotation(ModifiedDeclaration declaration) {
		Annotation outer = this.outerAnnotationAdapter.getAnnotation(declaration);
		if (outer == null) {
			return null;
		}
		Expression value = this.elementValue(outer);
		if (value == null) {
			return null;
		}
		Annotation inner = this.getAnnotation(value);
		if (inner == null) {
			return null;
		}
		// return the annotation only if it has a matching name(?)
		return this.nameMatches(declaration, inner) ? inner : null;
	}

	@Override
	public void removeAnnotation(ModifiedDeclaration declaration) {
		Annotation outer = this.outerAnnotationAdapter.getAnnotation(declaration);
		if (outer == null) {
			return;
		}
		Expression value = this.elementValue(outer);
		if (value == null) {
			return;
		}
		// hack to allow short-circuit when the value is an array initializer
		if (this.removeAnnotation(declaration, outer, value)) {
			return;
		}
		Annotation inner = this.annotationValue(value);
		if (inner == null) {
			return;
		}
		// remove the annotation only if it has a matching name(?)
		if (this.nameMatches(declaration, inner)) {
			this.removeElementAndNormalize(declaration, outer);
		}
	}

	@Override
	public ASTNode astNode(ModifiedDeclaration declaration) {
		// if the annotation is missing, return the outer annotation's node
		Annotation annotation = this.getAnnotation(declaration);
		return (annotation != null) ? annotation : this.outerAnnotationAdapter.astNode(declaration);
	}

	@Override
	protected void addAnnotation(ModifiedDeclaration declaration, Annotation inner) {
		Annotation outer = this.outerAnnotationAdapter.getAnnotation(declaration);
		if (outer == null) {
			this.buildNewOuterAnnotation(declaration, inner);
		} else if (outer.isMarkerAnnotation()) {
			this.modifyAnnotation(declaration, (MarkerAnnotation) outer, inner);
		} else if (outer.isSingleMemberAnnotation()) {
			this.modifyAnnotation(declaration, (SingleMemberAnnotation) outer, inner);
		} else if (outer.isNormalAnnotation()) {
			this.modifyAnnotation(declaration, (NormalAnnotation) outer, inner);
		} else {
			throw new IllegalStateException("unknown annotation type: " + outer);
		}
	}


	// ********** abstract methods **********

	/**
	 * Return an annotation extracted from the specified expression,
	 * which is the value of the adapter's element.
	 */
	protected abstract Annotation getAnnotation(Expression value);

	/**
	 * Remove the annotation from the specified expression,
	 * which is the value of the adapter's element.
	 * Return whether the removal was successful.
	 */
	protected abstract boolean removeAnnotation(ModifiedDeclaration declaration, Annotation outer, Expression value);

	/**
	 * Set the value of the specified outer annotation to the
	 * specified inner annotation.
	 */
	protected abstract void modifyAnnotationValue(SingleMemberAnnotation outer, Annotation inner);

	/**
	 * Set the value of the specified member value pair to the
	 * specified inner annotation.
	 */
	protected abstract void modifyMemberValuePair(MemberValuePair pair, Annotation inner);


	// ********** public methods **********

	public DeclarationAnnotationAdapter getOuterAnnotationAdapter() {
		return this.outerAnnotationAdapter;
	}

	public String getElementName() {
		return this.elementName;
	}


	// ********** internal methods **********

	/**
	 * If the specified expression is an annotation, cast it to an annotation;
	 * otherwise return null.
	 */
	protected Annotation annotationValue(Expression expression) {
		switch (expression.getNodeType()) {
			case ASTNode.NORMAL_ANNOTATION:
			case ASTNode.SINGLE_MEMBER_ANNOTATION:
			case ASTNode.MARKER_ANNOTATION:
				return (Annotation) expression;
			default:
				return null;
		}
	}

	/**
	 * Remove the *first* annotation element with the specified name
	 * from the specified annotation, converting the annotation as appropriate.
	 */
	protected void removeElementAndNormalize(ModifiedDeclaration declaration, Annotation outer) {
		if (outer.isNormalAnnotation()) {
			this.removeElementAndNormalize(declaration, (NormalAnnotation) outer);
		} else if (outer.isSingleMemberAnnotation()) {
			this.removeElementAndNormalize(declaration, (SingleMemberAnnotation) outer);
		} else if (outer.isMarkerAnnotation()) {
			this.removeElementAndNormalize(declaration, (MarkerAnnotation) outer);
		} else {
			throw new IllegalArgumentException("unknown annotation type: " + outer);
		}
	}

	/**
	 * Remove the *first* annotation element with the adapter's element name
	 * from the specified annotation. Convert the annotation to
	 * a marker annotation or single member annotation if appropriate.
	 * <pre>
	 * &#64;Outer(name="Fred", foo=&#64;Inner) => &#64;Outer(name="Fred")
	 * &#64;Outer(foo=&#64;Inner) => &#64;Outer
	 * </pre>
	 */
	protected void removeElementAndNormalize(ModifiedDeclaration declaration, NormalAnnotation outer) {
		this.removeElement(outer);
		this.normalizeAnnotation(declaration, outer);
	}

	/**
	 * Remove from the specified annotation the element with
	 * the adapter's element name.
	 */
	protected void removeElement(NormalAnnotation annotation) {
		for (Iterator<MemberValuePair> stream = this.values(annotation).iterator(); stream.hasNext(); ) {
			MemberValuePair pair = stream.next();
			if (pair.getName().getFullyQualifiedName().equals(this.elementName)) {
				stream.remove();
				break;
			}
		}
	}

	/**
	 * Convert the specified normal annotation to a marker annotation or
	 * single member annotation if appropriate.
	 */
	protected void normalizeAnnotation(ModifiedDeclaration declaration, NormalAnnotation outer) {
		List<MemberValuePair> values = this.values(outer);
		switch (values.size()) {
			case 0:
				// if the elements are all gone, remove the annotation or convert it to a marker annotation
				if (this.removeOuterAnnotationWhenEmpty) {
					this.outerAnnotationAdapter.removeAnnotation(declaration);
				} else {
					// convert the annotation to a marker annotation
					this.outerAnnotationAdapter.newMarkerAnnotation(declaration);
				}
				break;
			case 1:
				MemberValuePair pair = values.get(0);
				if (pair.getName().getFullyQualifiedName().equals("value")) {
					// if the last remaining element is 'value', convert the annotation to a single member annotation
					Expression vv = pair.getValue();
					vv = (Expression) ASTNode.copySubtree(vv.getAST(), vv);
					this.outerAnnotationAdapter.newSingleMemberAnnotation(declaration).setValue(vv);
				}
				break;
			default:
				// do nothing
				break;
		}
	}

	/**
	 * Convert the specified single member annotation to a marker annotation
	 * if the adapter's element name is "value".
	 */
	protected void removeElementAndNormalize(ModifiedDeclaration declaration, SingleMemberAnnotation outer) {
		if (this.elementName.equals("value")) {
			if (this.removeOuterAnnotationWhenEmpty) {
				this.outerAnnotationAdapter.removeAnnotation(declaration);
			} else {
				// convert the annotation to a marker annotation
				this.outerAnnotationAdapter.newMarkerAnnotation(declaration);
			}
		}
	}

	protected void removeElementAndNormalize(ModifiedDeclaration declaration, MarkerAnnotation outer) {
		if (this.removeOuterAnnotationWhenEmpty) {
			this.outerAnnotationAdapter.removeAnnotation(declaration);
		}
	}

	/**
	 * Return the value of the *first* annotation element
	 * with the adapter's element name.
	 * Return null if the annotation has no such element.
	 * (An element name of "value" will return the value of a single
	 * member annotation.)
	 */
	protected Expression elementValue(Annotation annotation) {
		if (annotation.isNormalAnnotation()) {
			return this.elementValue((NormalAnnotation) annotation);
		}
		if (annotation.isSingleMemberAnnotation()) {
			return this.elementValue((SingleMemberAnnotation) annotation);
		}
		return null;
	}

	protected Expression elementValue(NormalAnnotation annotation) {
		MemberValuePair pair = this.memberValuePair(annotation);
		return (pair == null) ? null : pair.getValue();
	}

	/**
	 * If the adapter's element name is "value", return the value of the
	 * annotation, otherwise return null.
	 */
	protected Expression elementValue(SingleMemberAnnotation annotation) {
		return this.elementName.equals("value") ? annotation.getValue() : null;
	}

	/**
	 * Return the *first* member value pair for the adapter's element name.
	 * Return null if the specified annotation has no such element.
	 */
	protected MemberValuePair memberValuePair(NormalAnnotation annotation) {
		for (MemberValuePair pair : this.values(annotation)) {
			if (pair.getName().getFullyQualifiedName().equals(this.elementName)) {
				return pair;
			}
		}
		return null;
	}

	/**
	 * Build a new outer annotation and add the specified
	 * inner annotation to it:
	 * <pre>
	 *     &#64;Outer(&#64;Inner)
	 * </pre>
	 * or
	 * <pre>
	 *     &#64;Outer(foo=&#64;Inner)
	 * </pre>
	 */
	protected void buildNewOuterAnnotation(ModifiedDeclaration declaration, Annotation inner) {
		if (this.elementName.equals("value")) {
			this.outerAnnotationAdapter.newSingleMemberAnnotation(declaration).setValue(this.buildNewInnerExpression(inner));
		} else {
			List<MemberValuePair> values = this.values(this.outerAnnotationAdapter.newNormalAnnotation(declaration));
			values.add(this.newMemberValuePair(this.buildNewInnerExpression(inner)));
		}
	}

	/**
	 * Build an expression to be added to a new outer annotation
	 * for the specified inner annotation.
	 */
	protected abstract Expression buildNewInnerExpression(Annotation inner);

	/**
	 * Build a new member value pair with the specified name and value.
	 */
	protected MemberValuePair newMemberValuePair(String name, Expression value) {
		AST ast = value.getAST();
		MemberValuePair pair = ast.newMemberValuePair();
		pair.setName(ast.newSimpleName(name));
		pair.setValue(value);
		return pair;
	}

	/**
	 * Build a new member value pair with the adapter's element name
	 * and the specified inner annotation.
	 */
	protected MemberValuePair newMemberValuePair(Expression value) {
		return this.newMemberValuePair(this.elementName, value);
	}

	/**
	 * Add the specified inner annotation to the marker annotation.
	 */
	protected void modifyAnnotation(ModifiedDeclaration declaration, MarkerAnnotation outer, Annotation inner) {
		this.buildNewOuterAnnotation(declaration, inner);
	}

	/**
	 * Add the specified inner annotation to the single member annotation.
	 */
	protected void modifyAnnotation(ModifiedDeclaration declaration, SingleMemberAnnotation outer, Annotation inner) {
		if (this.elementName.equals("value")) {
			this.modifyAnnotationValue(outer, inner);
		} else {
			this.modifyAnnotationNonValue(declaration, outer, inner);
		}
	}

	/**
	 * Add the specified inner annotation to the single member annotation,
	 * converting it to a normal annotation:
	 * <pre>
	 *     &#64;Outer("lorem ipsum") => &#64;Outer(value="lorem ipsum", foo=&#64;Inner)
	 * </pre>
	 */
	protected void modifyAnnotationNonValue(ModifiedDeclaration declaration, SingleMemberAnnotation outer, Annotation inner) {
		Expression vv = outer.getValue();
		vv = (Expression) ASTNode.copySubtree(vv.getAST(), vv);
		NormalAnnotation newOuter = this.outerAnnotationAdapter.newNormalAnnotation(declaration);
		List<MemberValuePair> values = this.values(newOuter);
		values.add(this.newMemberValuePair("value", vv));
		values.add(this.newMemberValuePair(this.buildNewInnerExpression(inner)));
	}

	/**
	 * Add the specified inner annotation to the normal annotation:
	 * <pre>
	 *     &#64;Outer(bar="lorem ipsum") => &#64;Outer(bar="lorem ipsum", foo=&#64;Inner)
	 * </pre>
	 * or
	 * <pre>
	 *     &#64;Outer(foo=&#64;Inner("lorem ipsum")) => &#64;Outer(foo=&#64;Inner)
	 * </pre>
	 */
	protected void modifyAnnotation(ModifiedDeclaration declaration, NormalAnnotation outer, Annotation inner) {
		MemberValuePair pair = this.memberValuePair(outer);
		if (pair == null) {
			List<MemberValuePair> values = this.values(outer);
			values.add(this.newMemberValuePair(inner));
		} else {
			this.modifyMemberValuePair(pair, inner);
		}
	}

}
