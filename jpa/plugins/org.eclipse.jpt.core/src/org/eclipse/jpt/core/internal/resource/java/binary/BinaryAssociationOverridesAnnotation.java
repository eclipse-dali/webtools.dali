/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.resource.java.binary;

import java.util.ListIterator;
import java.util.Vector;

import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jpt.core.resource.java.AssociationOverridesAnnotation;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.core.resource.java.NestableAssociationOverrideAnnotation;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;

/**
 * javax.persistence.AssociationOverrides
 */
public final class BinaryAssociationOverridesAnnotation
	extends BinaryContainerAnnotation<NestableAssociationOverrideAnnotation>
	implements AssociationOverridesAnnotation
{
	private final Vector<NestableAssociationOverrideAnnotation> associationOverrides;


	public BinaryAssociationOverridesAnnotation(JavaResourceNode parent, IAnnotation jdtAnnotation) {
		super(parent, jdtAnnotation);
		this.associationOverrides = this.buildAssociationOverrides();
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}

	public ListIterator<NestableAssociationOverrideAnnotation> nestedAnnotations() {
		return new CloneListIterator<NestableAssociationOverrideAnnotation>(this.associationOverrides);
	}

	public int nestedAnnotationsSize() {
		return this.associationOverrides.size();
	}

	private Vector<NestableAssociationOverrideAnnotation> buildAssociationOverrides() {
		Object[] jdtAssociationOverrides = this.getJdtMemberValues(JPA.ASSOCIATION_OVERRIDES__VALUE);
		Vector<NestableAssociationOverrideAnnotation> result = new Vector<NestableAssociationOverrideAnnotation>(jdtAssociationOverrides.length);
		for (Object jdtAssociationOverride : jdtAssociationOverrides) {
			result.add(new BinaryAssociationOverrideAnnotation(this, (IAnnotation) jdtAssociationOverride));
		}
		return result;
	}

	@Override
	public void update() {
		super.update();
		this.updateAssociationOverrides();
	}

	// TODO
	private void updateAssociationOverrides() {
		throw new UnsupportedOperationException();
	}

}
