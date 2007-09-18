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


/**
 * Adapt a member and an indexed declaration annotation adapter.
 */
public class NonIndexedMemberIndexedAnnotationAdapter
	extends AbstractAnnotationAdapter
	implements IndexedAnnotationAdapter
{


	// ********** constructor **********

	public NonIndexedMemberIndexedAnnotationAdapter(Member member, DeclarationAnnotationAdapter daa) {
		super(member, daa);
	}


	// ********** IndexedAnnotationAdapter implementation **********

	public int index() {
		return 0;
	}

	public void moveAnnotation(int newIndex) {
		throw new UnsupportedOperationException();
	}
}
