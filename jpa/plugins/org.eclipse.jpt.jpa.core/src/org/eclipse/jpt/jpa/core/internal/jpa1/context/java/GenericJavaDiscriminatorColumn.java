/*******************************************************************************
 * Copyright (c) 2007, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context.java;

import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.jpa.core.context.java.JavaDiscriminatorColumn;
import org.eclipse.jpt.jpa.core.context.java.JavaEntity;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentType;
import org.eclipse.jpt.jpa.core.resource.java.DiscriminatorColumnAnnotation;

/**
 * Java discriminator column
 */
public class GenericJavaDiscriminatorColumn
	extends AbstractJavaNamedDiscriminatorColumn<DiscriminatorColumnAnnotation, JavaDiscriminatorColumn.Owner>
	implements JavaDiscriminatorColumn
{

	public GenericJavaDiscriminatorColumn(JavaEntity parent, JavaDiscriminatorColumn.Owner owner) {
		super(parent, owner);
	}


	// ********** column annotation **********

	@Override
	public DiscriminatorColumnAnnotation getColumnAnnotation() {
		return (DiscriminatorColumnAnnotation) this.getJavaResourceType().getNonNullAnnotation(DiscriminatorColumnAnnotation.ANNOTATION_NAME);
	}

	@Override
	protected void removeColumnAnnotation() {
		this.getJavaResourceType().removeAnnotation(DiscriminatorColumnAnnotation.ANNOTATION_NAME);
	}


	// ********** misc **********

	@Override
	public JavaEntity getParent() {
		return (JavaEntity) super.getParent();
	}

	protected JavaEntity getEntity() {
		return this.getParent();
	}

	protected JavaPersistentType getPersistentType() {
		return this.getEntity().getPersistentType();
	}

	protected JavaResourceType getJavaResourceType() {
		return this.getPersistentType().getJavaResourceType();
	}
}
