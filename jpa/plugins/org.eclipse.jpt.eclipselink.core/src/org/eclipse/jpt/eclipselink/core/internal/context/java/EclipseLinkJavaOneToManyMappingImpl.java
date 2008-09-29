/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.context.java;

import org.eclipse.jpt.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.core.internal.context.java.GenericJavaOneToManyMapping;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.eclipselink.core.EclipseLinkJpaFactory;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkOneToManyMapping;
import org.eclipse.jpt.eclipselink.core.resource.java.PrivateOwnedAnnotation;

public class EclipseLinkJavaOneToManyMappingImpl extends GenericJavaOneToManyMapping implements EclipseLinkOneToManyMapping
{
	
	private boolean privateOwned;
	
	public EclipseLinkJavaOneToManyMappingImpl(JavaPersistentAttribute parent) {
		super(parent);
	}
	
	@Override
	protected EclipseLinkJpaFactory getJpaFactory() {
		return (EclipseLinkJpaFactory) super.getJpaFactory();
	}

	public boolean getPrivateOwned() {
		return this.privateOwned;
	}
	
	protected String getPrivateOwnedAnnotationName() {
		return PrivateOwnedAnnotation.ANNOTATION_NAME;
	}
	
	protected PrivateOwnedAnnotation getResourcePrivateOwned() {
		return (PrivateOwnedAnnotation) this.resourcePersistentAttribute.getAnnotation(getPrivateOwnedAnnotationName());
	}
	
	protected void addResourcePrivateOwned() {
		this.resourcePersistentAttribute.addAnnotation(getPrivateOwnedAnnotationName());
	}
	
	protected void removeResourcePrivateOwned() {
		this.resourcePersistentAttribute.removeAnnotation(getPrivateOwnedAnnotationName());
	}
	
	public void setPrivateOwned(boolean newPrivateOwned) {
		if (this.privateOwned == newPrivateOwned) {
			return;
		}
		boolean oldPrivateOwned = this.privateOwned;
		this.privateOwned = newPrivateOwned;

		if (newPrivateOwned) {
			addResourcePrivateOwned();
		}
		else {
			//have to check if annotation exists in case the change is from false to null or vice versa
			if (getResourcePrivateOwned() != null) {
				removeResourcePrivateOwned();
			}
		}
		firePropertyChanged(PRIVATE_OWNED_PROPERTY, oldPrivateOwned, newPrivateOwned);
	}
	
	protected void setPrivateOwned_(boolean newPrivateOwned) {
		boolean oldPrivateOwned = this.privateOwned;
		this.privateOwned = newPrivateOwned;
		firePropertyChanged(PRIVATE_OWNED_PROPERTY, oldPrivateOwned, newPrivateOwned);
	}
	
	@Override
	public void initialize(JavaResourcePersistentAttribute jrpa) {
		super.initialize(jrpa);
		this.privateOwned = privateOwned();
	}
	
	@Override
	public void update(JavaResourcePersistentAttribute javaResourcePersistentAttribute) {
		super.update(javaResourcePersistentAttribute);
		setPrivateOwned_(privateOwned());
	}
	
	private boolean privateOwned() {
		return getResourcePrivateOwned() != null;
	}
}
