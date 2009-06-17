/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt2_0.core.internal.context.java;

import org.eclipse.jpt.core.context.AccessType;
import org.eclipse.jpt.core.context.PersistentType;
import org.eclipse.jpt.core.internal.context.java.AbstractJavaPersistentAttribute;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt2_0.core.resource.java.AccessAnnotation;

/**
 * EclipseLink 1.1 persistent attribute
 */
public class Generic2_0JavaPersistentAttribute
	extends AbstractJavaPersistentAttribute
{
	protected AccessType specifiedAccess;


	public Generic2_0JavaPersistentAttribute(PersistentType parent, JavaResourcePersistentAttribute resourcePersistentAttribute) {
		super(parent, resourcePersistentAttribute);
		this.specifiedAccess = this.buildSpecifiedAccess();
	}


	// ********** AccessHolder implementation **********

	public AccessType getSpecifiedAccess() {
		return this.specifiedAccess;
	}

	/**
	 * Don't support changing to specified access on a java persistent attribute, this
	 * involves a larger process of moving the annotations to the corresponding field/property
	 * which may or may not exist or might need to be created.
	 */
	public void setSpecifiedAccess(AccessType specifiedAccess) {
		throw new UnsupportedOperationException();
	}

	protected void setSpecifiedAccess_(AccessType specifiedAccess) {
		AccessType old = this.specifiedAccess;
		this.specifiedAccess = specifiedAccess;
		this.firePropertyChanged(SPECIFIED_ACCESS_PROPERTY, old, specifiedAccess);
	}

	protected AccessType buildSpecifiedAccess() {
		AccessAnnotation accessAnnotation = (AccessAnnotation) this.resourcePersistentAttribute.getSupportingAnnotation(AccessAnnotation.ANNOTATION_NAME);
		return accessAnnotation == null ? null : AccessType.fromJavaResourceModel(accessAnnotation.getValue());
	}

	@Override
	public void update() {
		super.update();
		this.setSpecifiedAccess_(this.buildSpecifiedAccess());
	}

}
