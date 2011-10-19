/*******************************************************************************
 *  Copyright (c) 2011  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.common.core.utility.jdt;

import org.eclipse.jdt.core.dom.Expression;

/**
 * A type of {@link ExpressionConverter} that can further retrieve sub-expressions
 * based on indices.
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * This interface is not intended to be implemented by clients.
 */
public interface IndexedExpressionConverter<T>
		extends ExpressionConverter<T[]> {
	
	/**
	 * Return the expression for the sub-value at the given index.
	 * @throws ArrayIndexOutOfBoundsException if the index is out of range
	 */
	Expression getSubexpression(int index, Expression expression);
}
