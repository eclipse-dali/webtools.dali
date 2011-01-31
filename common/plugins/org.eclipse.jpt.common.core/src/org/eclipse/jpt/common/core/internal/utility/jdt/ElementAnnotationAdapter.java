/*******************************************************************************
 * Copyright (c) 2006, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
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
