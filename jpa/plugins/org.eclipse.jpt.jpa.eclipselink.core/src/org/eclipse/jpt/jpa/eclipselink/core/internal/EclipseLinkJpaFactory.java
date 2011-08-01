/*******************************************************************************
 * Copyright (c) 2008, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal;

import org.eclipse.jpt.common.core.resource.java.JavaResourceField;
import org.eclipse.jpt.common.core.resource.java.JavaResourceMethod;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.core.context.PersistentType;
import org.eclipse.jpt.jpa.core.context.java.Accessor;
import org.eclipse.jpt.jpa.core.context.java.JavaBasicMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaEmbeddable;
import org.eclipse.jpt.jpa.core.context.java.JavaIdMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaManyToManyMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaManyToOneMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaOneToManyMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaOneToOneMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentType;
import org.eclipse.jpt.jpa.core.context.java.JavaVersionMapping;
import org.eclipse.jpt.jpa.core.internal.AbstractJpaFactory;
import org.eclipse.jpt.jpa.core.resource.java.EmbeddableAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.EntityAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.MappedSuperclassAnnotation;
import org.eclipse.jpt.jpa.eclipselink.core.EclipseLinkJpaProject;
import org.eclipse.jpt.jpa.eclipselink.core.context.java.JavaEclipseLinkEntity;
import org.eclipse.jpt.jpa.eclipselink.core.context.java.JavaEclipseLinkMappedSuperclass;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.java.JavaEclipseLinkBasicCollectionMapping;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.java.JavaEclipseLinkBasicMapMapping;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.java.JavaEclipseLinkBasicMapping;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.java.JavaEclipseLinkEmbeddableImpl;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.java.JavaEclipseLinkEntityImpl;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.java.JavaEclipseLinkIdMapping;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.java.JavaEclipseLinkManyToManyMapping;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.java.JavaEclipseLinkManyToOneMapping;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.java.JavaEclipseLinkMappedSuperclassImpl;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.java.JavaEclipseLinkOneToManyMapping;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.java.JavaEclipseLinkOneToOneMapping;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.java.JavaEclipseLinkPersistentAttribute;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.java.JavaEclipseLinkTransformationMapping;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.java.JavaEclipseLinkVariableOneToOneMapping;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.java.JavaEclipseLinkVersionMapping;

public class EclipseLinkJpaFactory
	extends AbstractJpaFactory
{
	public EclipseLinkJpaFactory() {
		super();
	}


	// ********** Core Model **********
	
	@Override
	public EclipseLinkJpaProject buildJpaProject(JpaProject.Config config) {
		return new EclipseLinkJpaProjectImpl(config);
	}
	

	// ********** Java Context Model overrides **********

	@Override
	public JavaPersistentAttribute buildJavaPersistentAttribute(PersistentType parent, Accessor accessor) {
		return new JavaEclipseLinkPersistentAttribute(parent, accessor);
	}

	@Override
	public JavaPersistentAttribute buildJavaPersistentField(PersistentType parent, JavaResourceField resourceField) {
		return new JavaEclipseLinkPersistentAttribute(parent, resourceField);
	}

	@Override
	public JavaPersistentAttribute buildJavaPersistentProperty(PersistentType parent, JavaResourceMethod resourceGetter, JavaResourceMethod resourceSetter) {
		return new JavaEclipseLinkPersistentAttribute(parent, resourceGetter, resourceSetter);
	}

	@Override
	public JavaBasicMapping buildJavaBasicMapping(JavaPersistentAttribute parent) {
		return new JavaEclipseLinkBasicMapping(parent);
	}
	
	@Override
	public JavaEmbeddable buildJavaEmbeddable(JavaPersistentType parent, EmbeddableAnnotation embeddableAnnotation) {
		return new JavaEclipseLinkEmbeddableImpl(parent, embeddableAnnotation);
	}
	
	@Override
	public JavaEclipseLinkEntity buildJavaEntity(JavaPersistentType parent, EntityAnnotation entityAnnotation) {
		return new JavaEclipseLinkEntityImpl(parent, entityAnnotation);
	}
	
	@Override
	public JavaIdMapping buildJavaIdMapping(JavaPersistentAttribute parent) {
		return new JavaEclipseLinkIdMapping(parent);
	}
	
	@Override
	public JavaEclipseLinkMappedSuperclass buildJavaMappedSuperclass(JavaPersistentType parent, MappedSuperclassAnnotation mappedSuperclassAnnotation) {
		return new JavaEclipseLinkMappedSuperclassImpl(parent, mappedSuperclassAnnotation);
	}
	
	@Override
	public JavaVersionMapping buildJavaVersionMapping(JavaPersistentAttribute parent) {
		return new JavaEclipseLinkVersionMapping(parent);
	}
	
	@Override
	public JavaOneToManyMapping buildJavaOneToManyMapping(JavaPersistentAttribute parent) {
		return new JavaEclipseLinkOneToManyMapping(parent);
	}
	
	@Override
	public JavaOneToOneMapping buildJavaOneToOneMapping(JavaPersistentAttribute parent) {
		return new JavaEclipseLinkOneToOneMapping(parent);
	}
	
	@Override
	public JavaManyToManyMapping buildJavaManyToManyMapping(JavaPersistentAttribute parent) {
		return new JavaEclipseLinkManyToManyMapping(parent);
	}
	
	@Override
	public JavaManyToOneMapping buildJavaManyToOneMapping(JavaPersistentAttribute parent) {
		return new JavaEclipseLinkManyToOneMapping(parent);
	}


	// ********** EclipseLink-specific Java Context Model **********

	public JavaEclipseLinkBasicCollectionMapping buildJavaEclipseLinkBasicCollectionMapping(JavaPersistentAttribute parent) {
		return new JavaEclipseLinkBasicCollectionMapping(parent);
	}
	
	public JavaEclipseLinkBasicMapMapping buildJavaEclipseLinkBasicMapMapping(JavaPersistentAttribute parent) {
		return new JavaEclipseLinkBasicMapMapping(parent);
	}
	
	public JavaEclipseLinkTransformationMapping buildJavaEclipseLinkTransformationMapping(JavaPersistentAttribute parent) {
		return new JavaEclipseLinkTransformationMapping(parent);
	}

	public JavaEclipseLinkVariableOneToOneMapping buildJavaEclipseLinkVariableOneToOneMapping(JavaPersistentAttribute parent) {
		return new JavaEclipseLinkVariableOneToOneMapping(parent);
	}
}
