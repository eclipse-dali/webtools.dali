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
 * from an arbitrary type (e.g. Expression <=> String).
 * T1 is the expression type, while T2 is the type of object the expression
 * is convert to/from.
 */
public interface ExpressionConverter<T1 extends Expression, T2> {

	/**
	 * Convert the specified object to an
	 * expression that is owned by the specified AST.
	 * The type of the object is determined by the
	 * contract specified by the client.
	 */
	T1 convert(T2 object, AST ast);

	/**
	 * Convert the specified expression to an object of some
	 * pre-determined type.
	 */
	T2 convert(T1 expression);

}
