/*******************************************************************************
 * Copyright (c) 2006, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.core.internal.utility.jdt;

import org.eclipse.jpt.common.core.utility.jdt.AnnotatedElement;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationAdapter;

/**
 * Adapt an annotated element and a declaration annotation adapter.
 */
public class ElementAnnotationAdapter extends AbstractAnnotationAdapter {


	// ********** constructor **********

	public ElementAnnotationAdapter(AnnotatedElement annotatedElement, DeclarationAnnotationAdapter daa) {
		super(annotatedElement, daa);
	}

}
