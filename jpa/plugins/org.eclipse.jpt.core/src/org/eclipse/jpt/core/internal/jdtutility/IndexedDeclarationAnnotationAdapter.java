/*******************************************************************************
 * Copyright (c) 2006, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jdtutility;

import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.ModifiedDeclaration;

/**
 * Allow clients to manipulate an annotation within an array of annotations.
 */
public interface IndexedDeclarationAnnotationAdapter extends DeclarationAnnotationAdapter {

	/**
	 * Return the the index at which the annotation is situated.
	 */
	int getIndex();

	/**
	 * Move the annotation to the specified index, leaving its original
	 * position cleared out.
	 */
	void moveAnnotation(int newIndex, ModifiedDeclaration declaration);

}
