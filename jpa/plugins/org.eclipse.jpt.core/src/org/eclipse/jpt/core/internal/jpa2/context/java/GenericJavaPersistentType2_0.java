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

import org.eclipse.jpt.core.context.AccessType;
import org.eclipse.jpt.core.context.PersistentType;
import org.eclipse.jpt.core.internal.context.java.AbstractJavaPersistentType;
import org.eclipse.jpt.core.jpa2.JpaProject2_0;
import org.eclipse.jpt.core.jpa2.context.java.JavaPersistentType2_0;
import org.eclipse.jpt.core.jpa2.resource.java.Access2_0Annotation;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;

public class GenericJavaPersistentType2_0
	extends AbstractJavaPersistentType
	implements JavaPersistentType2_0
{
	public GenericJavaPersistentType2_0(PersistentType.Owner parent, JavaResourcePersistentType jrpt) {
		super(parent, jrpt);
	}
	
	/**
	 * covariant override
	 */
	@Override
	public JpaProject2_0 getJpaProject() {
		return (JpaProject2_0) super.getJpaProject();
	}
	
	protected Access2_0Annotation getAccessAnnotation() {
		return (Access2_0Annotation) this.resourcePersistentType.
				getNonNullAnnotation(getAccessAnnotationName());
	}
	
	protected String getAccessAnnotationName() {
		return Access2_0Annotation.ANNOTATION_NAME;
	}
	
	@Override
	protected AccessType buildSpecifiedAccess() {
		return this.getResourceAccess();
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
	public void updateAccess() {
		super.updateAccess();
		this.setSpecifiedAccess_(this.getResourceAccess());
	}

	protected AccessType getResourceAccess() {
		return AccessType.fromJavaResourceModel(this.getAccessAnnotation().getValue());
	}
	
	public void synchronizeStaticMetamodel() {
		this.getJpaProject().synchronizeStaticMetamodel(this);
	}

}
