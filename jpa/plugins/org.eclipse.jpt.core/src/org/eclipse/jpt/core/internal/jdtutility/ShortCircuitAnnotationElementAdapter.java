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

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jpt.utility.internal.StringTools;

/**
 * Wrap another annotation element adapter and short-circuit the
 * #setValue method if the value has not changed.
 */
public class ShortCircuitAnnotationElementAdapter
	implements AnnotationElementAdapter
{
	/** the wrapped adapter */
	private final AnnotationElementAdapter adapter;


	// ********** constructor **********

	public ShortCircuitAnnotationElementAdapter(Member member, DeclarationAnnotationElementAdapter daea) {
		this(new MemberAnnotationElementAdapter(member, daea));
	}

	public ShortCircuitAnnotationElementAdapter(AnnotationElementAdapter adapter) {
		super();
		this.adapter = adapter;
	}


	// ********** AnnotationElementAdapter implementation **********

	public Object getValue() {
		return this.adapter.getValue();
	}

	public Object getValue(CompilationUnit astRoot) {
		return this.adapter.getValue(astRoot);
	}

	public void setValue(Object value) {
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
	protected void setValue(Object oldValue, Object newValue) {
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
				if (newValue.equals(oldValue)) {
					// do nothing
				} else {
					this.adapter.setValue(newValue);
				}
			}
		}
	}

}
