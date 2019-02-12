/*******************************************************************************
 * Copyright (c) 2006, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.core.utility.jdt;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.Expression;

/**
 * Define the protocol for converting an AST expression back and forth
 * from an arbitrary type (e.g. StringLiteral <=> String).
 * <p>
 * This interface is not intended to be implemented by clients.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @param <T> the type of the object to be converted to and from an expression
 */
public interface ExpressionConverter<T> {

	/**
	 * Convert the specified object to an
	 * expression that is owned by the specified AST.
	 * The type of the object is determined by the
	 * contract specified by the client.
	 */
	Expression convert(T object, AST ast);

	/**
	 * Convert the specified expression to an object of some
	 * pre-determined type.
	 */
	T convert(Expression expression);
}
