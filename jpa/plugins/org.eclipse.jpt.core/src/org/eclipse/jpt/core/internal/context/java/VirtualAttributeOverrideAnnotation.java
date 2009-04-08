/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.java;

import org.eclipse.jpt.core.context.Column;
import org.eclipse.jpt.core.internal.resource.java.NullOverrideAnnotation;
import org.eclipse.jpt.core.resource.java.AttributeOverrideAnnotation;
import org.eclipse.jpt.core.resource.java.ColumnAnnotation;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentMember;

/**
 * javax.persistence.AssociationOverride
 */
public class VirtualAttributeOverrideAnnotation
	extends NullOverrideAnnotation
	implements AttributeOverrideAnnotation 
{
	private final VirtualAttributeOverrideColumnAnnotation column;


	public VirtualAttributeOverrideAnnotation(JavaResourcePersistentMember parent, String name, Column column) {
		super(parent, name);
		this.column = new VirtualAttributeOverrideColumnAnnotation(this, column);
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}

	@Override
	protected AttributeOverrideAnnotation buildSupportingAnnotation() {
		return (AttributeOverrideAnnotation) super.buildSupportingAnnotation();
	}

	// ****** column
	public ColumnAnnotation getColumn() {
		return this.column;
	}

	public ColumnAnnotation getNonNullColumn() {
		return this.getColumn();
	}

	public ColumnAnnotation addColumn() {
		throw new UnsupportedOperationException();
	}

	public void removeColumn() {
		throw new UnsupportedOperationException();
	}

}
