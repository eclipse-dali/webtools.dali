/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa2.context.java;

import java.util.Iterator;

import org.eclipse.core.resources.IFile;
import org.eclipse.jpt.core.context.AccessType;
import org.eclipse.jpt.core.context.PersistentType;
import org.eclipse.jpt.core.internal.context.java.AbstractJavaPersistentType;
import org.eclipse.jpt.core.jpa2.JpaFactory2_0;
import org.eclipse.jpt.core.jpa2.context.PersistentType2_0;
import org.eclipse.jpt.core.jpa2.context.java.JavaPersistentType2_0;
import org.eclipse.jpt.core.jpa2.resource.java.Access2_0Annotation;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;

/**
 * JPA 2.0 Java persistent type.
 * Support for specified access and metamodel generation.
 */
public class GenericJavaPersistentType2_0
	extends AbstractJavaPersistentType
	implements JavaPersistentType2_0
{
	protected final PersistentType2_0.MetamodelSynchronizer metamodelSynchronizer;

	public GenericJavaPersistentType2_0(PersistentType.Owner parent, JavaResourcePersistentType jrpt) {
		super(parent, jrpt);
		this.metamodelSynchronizer = this.buildMetamodelSynchronizer();
	}

	protected PersistentType2_0.MetamodelSynchronizer buildMetamodelSynchronizer() {
		return ((JpaFactory2_0) this.getJpaFactory()).buildPersistentTypeMetamodelSynchronizer(this);
	}


	// ********** access **********

	protected Access2_0Annotation getAccessAnnotation() {
		return (Access2_0Annotation) this.resourcePersistentType.getNonNullAnnotation(this.getAccessAnnotationName());
	}
	
	protected String getAccessAnnotationName() {
		return Access2_0Annotation.ANNOTATION_NAME;
	}
	
	@Override
	protected AccessType buildSpecifiedAccess() {
		return AccessType.fromJavaResourceModel(this.getAccessAnnotation().getValue());
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
		return (this.specifiedAccess == null) ?
				super.resourceAttributes() :
				this.resourcePersistentType.persistableAttributes(AccessType.toJavaResourceModel(this.specifiedAccess));
	}
	
	@Override
	public void updateAccess() {
		super.updateAccess();
		this.setSpecifiedAccess_(this.buildSpecifiedAccess());
	}


	// ********** metamodel **********

	public IFile getMetamodelFile() {
		return this.metamodelSynchronizer.getFile();
	}

	public void initializeMetamodel() {
		// do nothing - probably shouldn't be called...
	}

	public void synchronizeMetamodel() {
		this.metamodelSynchronizer.synchronize();
	}
	
	public void disposeMetamodel() {
		// do nothing - probably shouldn't be called...
	}

}
