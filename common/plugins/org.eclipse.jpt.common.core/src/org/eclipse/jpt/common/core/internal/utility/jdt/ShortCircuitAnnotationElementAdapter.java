/*******************************************************************************
 * Copyright (c) 2006, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.core.internal.utility.jdt;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jpt.common.core.utility.jdt.AnnotatedElement;
import org.eclipse.jpt.common.core.utility.jdt.AnnotationElementAdapter;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.common.utility.internal.ObjectTools;

/**
 * Wrap another annotation element adapter and short-circuit the
 * #setValue method if the value has not changed.
 */
public class ShortCircuitAnnotationElementAdapter<T>
	implements AnnotationElementAdapter<T>
{
	/** the wrapped adapter */
	private final AnnotationElementAdapter<T> adapter;


	// ********** constructor **********

	public ShortCircuitAnnotationElementAdapter(AnnotatedElement annotatedElement, DeclarationAnnotationElementAdapter<T> daea) {
		this(new AnnotatedElementAnnotationElementAdapter<T>(annotatedElement, daea));
	}

	public ShortCircuitAnnotationElementAdapter(AnnotationElementAdapter<T> adapter) {
		super();
		this.adapter = adapter;
	}


	// ********** AnnotationElementAdapter implementation **********

	public T getValue() {
		return this.adapter.getValue();
	}

	public T getValue(CompilationUnit astRoot) {
		return this.adapter.getValue(astRoot);
	}

	public T getValue(Annotation astAnnotation) {
		return this.adapter.getValue(astAnnotation);
	}

	public void setValue(T value) {
		this.setValue(this.adapter.getValue(), value);
	}

	public Expression getExpression(CompilationUnit astRoot) {
		return this.adapter.getExpression(astRoot);
	}

	public Expression getExpression(Annotation astAnnotation) {
		return this.adapter.getExpression(astAnnotation);
	}

	public ASTNode getAstNode(CompilationUnit astRoot) {
		return this.adapter.getAstNode(astRoot);
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.adapter);
	}


	// ********** internal methods **********

	/**
	 * set the adapter's value to the specified new value if it
	 * is different from the specified old value
	 */
	protected void setValue(T oldValue, T newValue) {
		if (oldValue == null) {
			if (newValue == null) {  // null => null
				// do nothing
			} else {  // null => object
				this.adapter.setValue(newValue);
			}
		} else {
			if (newValue == null) {  // object => null
				this.adapter.setValue(null);
			} else {  // object => object
				if (this.valuesAreEqual(oldValue, newValue)) {
					// do nothing
				} else {
					this.adapter.setValue(newValue);
				}
			}
		}
	}

	/**
	 * both values are non-null when this method is called
	 */
	protected boolean valuesAreEqual(T oldValue, T newValue) {
		return newValue.equals(oldValue);
	}

}
