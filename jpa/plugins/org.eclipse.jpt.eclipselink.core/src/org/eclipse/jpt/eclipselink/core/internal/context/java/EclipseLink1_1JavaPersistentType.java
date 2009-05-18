/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.context.java;

import java.util.Iterator;
import org.eclipse.jpt.core.context.AccessType;
import org.eclipse.jpt.core.context.PersistentType;
import org.eclipse.jpt.core.internal.context.java.AbstractJavaPersistentType;
import org.eclipse.jpt.core.resource.java.AccessAnnotation;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;

public class EclipseLink1_1JavaPersistentType
	extends AbstractJavaPersistentType
{
	protected AccessType specifiedAccess;
	
	public EclipseLink1_1JavaPersistentType(PersistentType.Owner parent, JavaResourcePersistentType jrpt) {
		super(parent, jrpt);
	}
	
	protected AccessAnnotation getAccessAnnotation() {
		return (AccessAnnotation) this.resourcePersistentType.getNonNullSupportingAnnotation(getAccessAnnotationName());
	}

	protected String getAccessAnnotationName() {
		return AccessAnnotation.ANNOTATION_NAME;
	}
	
	public AccessType getSpecifiedAccess() {
		return this.specifiedAccess;
	}
	
	public void setSpecifiedAccess(AccessType specifiedAccess) {
		AccessType old = this.specifiedAccess;
		this.specifiedAccess = specifiedAccess;
		this.getAccessAnnotation().setValue(AccessType.toJavaResourceModel(specifiedAccess));
		this.firePropertyChanged(SPECIFIED_ACCESS_PROPERTY, old, specifiedAccess);
	}
	
	protected void setSpecifiedAccess_(AccessType specifiedAccess) {
		AccessType old = this.specifiedAccess;
		this.specifiedAccess = specifiedAccess;
		this.firePropertyChanged(SPECIFIED_ACCESS_PROPERTY, old, specifiedAccess);
	}
	
	@Override
	protected Iterator<JavaResourcePersistentAttribute> resourceAttributes() {
		return (this.specifiedAccess == null)
			? super.resourceAttributes()
			: this.resourcePersistentType.persistableAttributes(AccessType.toJavaResourceModel(this.specifiedAccess));
	}
	
	@Override
	protected void initializeAccess() {
		super.initializeAccess();
		this.specifiedAccess = this.getResourceAccess();
	}
	
	@Override
	public void updateAccess() {
		super.updateAccess();
		this.setSpecifiedAccess_(this.getResourceAccess());
	}

	protected AccessType getResourceAccess() {
		return AccessType.fromJavaResourceModel(this.getAccessAnnotation().getValue());
	}

}
