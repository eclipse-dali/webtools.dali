/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jdtutility;

import java.util.Arrays;

/**
 * Wrap another annotation element adapter and short-circuit the
 * #setValue method if the value has not changed. Overrides #valuesAreEqual()
 * to check equality on arrays
 */
public class ShortCircuitArrayAnnotationElementAdapter<T>
	extends ShortCircuitAnnotationElementAdapter<T[]>
{
	// ********** constructor **********

	public ShortCircuitArrayAnnotationElementAdapter(Member member, DeclarationAnnotationElementAdapter<T[]> daea) {
		super(member, daea);
	}

	public ShortCircuitArrayAnnotationElementAdapter(AnnotationElementAdapter<T[]> adapter) {
		super(adapter);
	}


	// ********** AnnotationElementAdapter implementation **********

	@Override
	protected boolean valuesAreEqual(T[] oldValue, T[] newValue) {
		return Arrays.equals(newValue, oldValue);
	}

}
