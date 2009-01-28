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

public class EclipseLink1_1JavaPersistentTypeImpl extends AbstractJavaPersistentType
{
	protected AccessType specifiedAccess;
	
	public EclipseLink1_1JavaPersistentTypeImpl(PersistentType.Owner parent, JavaResourcePersistentType jrpt) {
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
	
	public void setSpecifiedAccess(AccessType newSpecifiedAccess) {
		AccessType oldAccess = this.specifiedAccess;
		this.specifiedAccess = newSpecifiedAccess;
		getAccessAnnotation().setValue(AccessType.toJavaResourceModel(newSpecifiedAccess));
		firePropertyChanged(SPECIFIED_ACCESS_PROPERTY, oldAccess, newSpecifiedAccess);
	}
	
	protected void setSpecifiedAccess_(AccessType newSpecifiedAccess) {
		AccessType oldAccess = this.specifiedAccess;
		this.specifiedAccess = newSpecifiedAccess;
		firePropertyChanged(SPECIFIED_ACCESS_PROPERTY, oldAccess, newSpecifiedAccess);
	}
	
	@Override
	protected Iterator<JavaResourcePersistentAttribute> persistentAttributes() {
		AccessType specifiedAccess = getSpecifiedAccess();
		if (specifiedAccess == AccessType.FIELD) {
			//TODO attribute mappings with @Access
			//return this.resourcePersistentType.persistableFields() +  this.resourcePersistentType.persistableProperties() that have @Access(Property) on them
			return this.resourcePersistentType.persistableFields();
		
		}
		else if (specifiedAccess == AccessType.PROPERTY) {
			//TODO attribute mappings with @Access
			//return this.resourcePersistentType.persistableProperties() +  this.resourcePersistentType.persistableFields() that have @Access(FIELD) on them
			return this.resourcePersistentType.persistableProperties();
		}
		return super.persistentAttributes();
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
		return AccessType.fromJavaResourceModel(getAccessAnnotation().getValue());
	}
}
