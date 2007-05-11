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

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.Expression;

/**
 * Define the protocol for converting an AST expression back and forth
 * from an arbitrary type (e.g. StringLiteral <=> String).
 * E is the expression type.
 * T is the type of the object to be converted to and from an expression.
 */
public interface ExpressionConverter<T, E extends Expression> {

	/**
	 * Convert the specified object to an
	 * expression that is owned by the specified AST.
	 * The type of the object is determined by the
	 * contract specified by the client.
	 */
	E convert(T object, AST ast);

	/**
	 * Convert the specified expression to an object of some
	 * pre-determined type.
	 */
	T convert(E expression);

}
