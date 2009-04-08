/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.resource.java;

import java.util.ListIterator;

import org.eclipse.jpt.core.resource.java.AssociationOverrideAnnotation;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.core.resource.java.JoinColumnAnnotation;
import org.eclipse.jpt.utility.internal.iterators.EmptyListIterator;

/**
 * javax.persistence.AssociationOverride
 */
public final class NullAssociationOverrideAnnotation
	extends NullOverrideAnnotation
	implements AssociationOverrideAnnotation
{

	public NullAssociationOverrideAnnotation(JavaResourcePersistentType parent, String name) {
		super(parent, name);
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}

	@Override
	protected AssociationOverrideAnnotation buildSupportingAnnotation() {
		return (AssociationOverrideAnnotation) super.buildSupportingAnnotation();
	}

	// ***** join columns
	public ListIterator<JoinColumnAnnotation> joinColumns() {
		return EmptyListIterator.instance();
	}
	
	public JoinColumnAnnotation joinColumnAt(int index) {
		throw new UnsupportedOperationException();
	}
	
	public int indexOfJoinColumn(JoinColumnAnnotation joinColumn) {
		throw new UnsupportedOperationException();
	}
	
	public int joinColumnsSize() {
		return 0;
	}
	
	public JoinColumnAnnotation addJoinColumn(int index) {
		throw new UnsupportedOperationException();
	}
	
	public void removeJoinColumn(int index) {
		throw new UnsupportedOperationException();
	}
	
	public void moveJoinColumn(int targetIndex, int sourceIndex) {
		throw new UnsupportedOperationException();
	}

}
