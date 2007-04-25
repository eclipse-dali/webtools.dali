/*******************************************************************************
 * Copyright (c) 2006 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.content.java.mappings;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.jdtutility.AnnotationElementAdapter;
import org.eclipse.jpt.utility.internal.StringTools;

/**
 * Wrap an AnnotationElementAdapter and convert its value to an int,
 * and vice-versa. The public protocol is identical to
 * AnnotationElementAdapter; except the #getValue and #setValue
 * methods deal with an int instead of an Object.
 * 
 * Assumptions:
 *   - the nested adapter returns and expects a String value
 *   - the value should be a non-negative number (value >= 0)
 *   - an invalid value is represented with a -1/null
 * These assumptions work reasonably enough with the JPA requirements.
 */
public class IntAnnotationElementAdapter {
	private final AnnotationElementAdapter adapter;

	public IntAnnotationElementAdapter(AnnotationElementAdapter adapter) {
		super();
		this.adapter = adapter;
	}

	public ASTNode astNode() {
		return this.adapter.astNode();
	}

	public ASTNode astNode(CompilationUnit astRoot) {
		return this.adapter.astNode(astRoot);
	}

	public int getValue() {
		return this.convertValueToInt(this.adapter.getValue());
	}

	public int getValue(CompilationUnit astRoot) {
		return this.convertValueToInt(this.adapter.getValue(astRoot));
	}

	protected int convertValueToInt(Object value) {
		return this.convertStringToInt(this.convertValueToString(value));
	}

	/**
	 * assume the wrapped adapter returns a string
	 */
	protected String convertValueToString(Object value) {
		return (String) value;
	}

	protected int convertStringToInt(String stringValue) {
		if (stringValue == null) {
			return -1;
		}
		try {
			int intValue = Integer.parseInt(stringValue);
			return (intValue >= 0) ? intValue : -1;
		} catch (NumberFormatException ex) {
			return -1;
		}
	}

	public void setValue(int value) {
		this.adapter.setValue(this.convertIntToValue(value));
	}

	protected Object convertIntToValue(int intValue) {
		return this.convertStringToValue(this.convertIntToString(intValue));
	}

	/**
	 * assume the wrapped adapter expects a string
	 */
	protected Object convertStringToValue(String stringValue) {
		return stringValue;
	}

	protected String convertIntToString(int intValue) {
		return (intValue >= 0) ? Integer.toString(intValue) : null;
	}

	@Override
	public String toString() {
		return StringTools.buildToStringFor(this, this.adapter);
	}

}
