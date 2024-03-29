/*******************************************************************************
 * Copyright (c) 2007, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.core.internal.utility.jdt;

import java.util.Arrays;
import org.eclipse.jpt.common.core.utility.jdt.AnnotatedElement;
import org.eclipse.jpt.common.core.utility.jdt.AnnotationElementAdapter;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationElementAdapter;

/**
 * Wrap another annotation element adapter and short-circuit the
 * #setValue method if the value has not changed. Overrides #valuesAreEqual()
 * to check equality on arrays
 */
public class ShortCircuitArrayAnnotationElementAdapter<T>
	extends ShortCircuitAnnotationElementAdapter<T[]>
{
	// ********** constructor **********

	public ShortCircuitArrayAnnotationElementAdapter(AnnotatedElement annotatedElement, DeclarationAnnotationElementAdapter<T[]> daea) {
		super(annotatedElement, daea);
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
