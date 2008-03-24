/*******************************************************************************
 * Copyright (c) 2006, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jdtutility;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jpt.core.utility.jdt.AnnotationElementAdapter;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.utility.jdt.Member;
import org.eclipse.jpt.utility.internal.StringTools;

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

	public ShortCircuitAnnotationElementAdapter(Member member, DeclarationAnnotationElementAdapter<T> daea) {
		this(new MemberAnnotationElementAdapter<T>(member, daea));
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

	public void setValue(T value) {
		this.setValue(this.adapter.getValue(), value);
	}

	public Expression expression() {
		return this.adapter.expression();
	}

	public Expression expression(CompilationUnit astRoot) {
		return this.adapter.expression(astRoot);
	}

	public ASTNode astNode() {
		return this.adapter.astNode();
	}

	public ASTNode astNode(CompilationUnit astRoot) {
		return this.adapter.astNode(astRoot);
	}

	@Override
	public String toString() {
		return StringTools.buildToStringFor(this, this.adapter);
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
				this.adapter.setValue(newValue);
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
