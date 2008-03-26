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

import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.MemberValuePair;
import org.eclipse.jdt.core.dom.SingleMemberAnnotation;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.ModifiedDeclaration;

/**
 * Manipulate an annotation that is embedded as an element within
 * another annotation, e.g.
 * <pre>
 *     &#64;Outer(foo=&#64;Inner)
 *     private int id;
 *         outerAnnotationAdapter = AnnotationAdapter<&#64;Outer>
 *         elementName = "foo"
 *         annotationName = "Inner"
 * </pre>
 */
public class NestedDeclarationAnnotationAdapter extends AbstractNestedDeclarationAnnotationAdapter {


	// ********** constructors **********

	/**
	 * default element name is "value";
	 * default behavior is to remove the outer annotation when it is empty
	 */
	public NestedDeclarationAnnotationAdapter(DeclarationAnnotationAdapter outerAnnotationAdapter, String annotationName) {
		super(outerAnnotationAdapter, annotationName);
	}

	/**
	 * default behavior is to remove the outer annotation when it is empty
	 */
	public NestedDeclarationAnnotationAdapter(DeclarationAnnotationAdapter outerAnnotationAdapter, String elementName, String annotationName) {
		super(outerAnnotationAdapter, elementName, annotationName);
	}

	public NestedDeclarationAnnotationAdapter(DeclarationAnnotationAdapter outerAnnotationAdapter, String elementName, String annotationName, boolean removeOuterAnnotationWhenEmpty) {
		super(outerAnnotationAdapter, elementName, annotationName, removeOuterAnnotationWhenEmpty);
	}


	// ********** AbstractNestedDeclarationAnnotationAdapter implementation **********

	@Override
	protected Annotation getAnnotation(Expression value) {
		return this.annotationValue(value);
	}

	@Override
	protected Expression buildNewInnerExpression(Annotation inner) {
		return inner;
	}

	/**
	 * the annotation is the expression itself, so the annotation cannot be
	 * "removed" from itself - return 'false'
	 */
	@Override
	protected boolean removeAnnotation(ModifiedDeclaration declaration, Annotation outer, Expression value) {
		return false;
	}

	/**
	 * <pre>
	 * &#64;Outer("lorem ipsum") => &#64;Outer(&#64;Inner)
	 * </pre>
	 */
	@Override
	protected void modifyAnnotationValue(SingleMemberAnnotation outer, Annotation inner) {
		// replace(?) the current element value
		outer.setValue(inner);
	}

	/**
	 * Simply set the pair's value.
	 */
	@Override
	protected void modifyMemberValuePair(MemberValuePair pair, Annotation inner) {
		pair.setValue(inner);
	}

}
