/*******************************************************************************
 * Copyright (c) 2008, 2012 Oracle. All rights reserved.
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
import org.eclipse.jpt.jpa.core.context.java.JavaSpecifiedPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentType;
import org.eclipse.jpt.jpa.core.context.java.JavaVersionMapping;
import org.eclipse.jpt.jpa.core.internal.AbstractJpaFactory;
import org.eclipse.jpt.jpa.core.resource.java.EmbeddableAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.EntityAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.MappedSuperclassAnnotation;
import org.eclipse.jpt.jpa.eclipselink.core.EclipseLinkJpaProject;
import org.eclipse.jpt.jpa.eclipselink.core.context.java.EclipseLinkJavaEntity;
import org.eclipse.jpt.jpa.eclipselink.core.context.java.EclipseLinkJavaMappedSuperclass;
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
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.java.EclipseLinkSpecifiedJavaPersistentAttribute;
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
	public JavaSpecifiedPersistentAttribute buildJavaPersistentAttribute(PersistentType parent, Accessor accessor) {
		return new EclipseLinkSpecifiedJavaPersistentAttribute(parent, accessor);
	}

	@Override
	public JavaSpecifiedPersistentAttribute buildJavaPersistentField(PersistentType parent, JavaResourceField resourceField) {
		return new EclipseLinkSpecifiedJavaPersistentAttribute(parent, resourceField);
	}

	@Override
	public JavaSpecifiedPersistentAttribute buildJavaPersistentProperty(PersistentType parent, JavaResourceMethod resourceGetter, JavaResourceMethod resourceSetter) {
		return new EclipseLinkSpecifiedJavaPersistentAttribute(parent, resourceGetter, resourceSetter);
	}

	@Override
	public JavaBasicMapping buildJavaBasicMapping(JavaSpecifiedPersistentAttribute parent) {
		return new JavaEclipseLinkBasicMapping(parent);
	}
	
	@Override
	public JavaEmbeddable buildJavaEmbeddable(JavaPersistentType parent, EmbeddableAnnotation embeddableAnnotation) {
		return new JavaEclipseLinkEmbeddableImpl(parent, embeddableAnnotation);
	}
	
	@Override
	public EclipseLinkJavaEntity buildJavaEntity(JavaPersistentType parent, EntityAnnotation entityAnnotation) {
		return new JavaEclipseLinkEntityImpl(parent, entityAnnotation);
	}
	
	@Override
	public JavaIdMapping buildJavaIdMapping(JavaSpecifiedPersistentAttribute parent) {
		return new JavaEclipseLinkIdMapping(parent);
	}
	
	@Override
	public EclipseLinkJavaMappedSuperclass buildJavaMappedSuperclass(JavaPersistentType parent, MappedSuperclassAnnotation mappedSuperclassAnnotation) {
		return new JavaEclipseLinkMappedSuperclassImpl(parent, mappedSuperclassAnnotation);
	}
	
	@Override
	public JavaVersionMapping buildJavaVersionMapping(JavaSpecifiedPersistentAttribute parent) {
		return new JavaEclipseLinkVersionMapping(parent);
	}
	
	@Override
	public JavaOneToManyMapping buildJavaOneToManyMapping(JavaSpecifiedPersistentAttribute parent) {
		return new JavaEclipseLinkOneToManyMapping(parent);
	}
	
	@Override
	public JavaOneToOneMapping buildJavaOneToOneMapping(JavaSpecifiedPersistentAttribute parent) {
		return new JavaEclipseLinkOneToOneMapping(parent);
	}
	
	@Override
	public JavaManyToManyMapping buildJavaManyToManyMapping(JavaSpecifiedPersistentAttribute parent) {
		return new JavaEclipseLinkManyToManyMapping(parent);
	}
	
	@Override
	public JavaManyToOneMapping buildJavaManyToOneMapping(JavaSpecifiedPersistentAttribute parent) {
		return new JavaEclipseLinkManyToOneMapping(parent);
	}


	// ********** EclipseLink-specific Java Context Model **********

	public JavaEclipseLinkBasicCollectionMapping buildJavaEclipseLinkBasicCollectionMapping(JavaSpecifiedPersistentAttribute parent) {
		return new JavaEclipseLinkBasicCollectionMapping(parent);
	}
	
	public JavaEclipseLinkBasicMapMapping buildJavaEclipseLinkBasicMapMapping(JavaSpecifiedPersistentAttribute parent) {
		return new JavaEclipseLinkBasicMapMapping(parent);
	}
	
	public JavaEclipseLinkTransformationMapping buildJavaEclipseLinkTransformationMapping(JavaSpecifiedPersistentAttribute parent) {
		return new JavaEclipseLinkTransformationMapping(parent);
	}

	public JavaEclipseLinkVariableOneToOneMapping buildJavaEclipseLinkVariableOneToOneMapping(JavaSpecifiedPersistentAttribute parent) {
		return new JavaEclipseLinkVariableOneToOneMapping(parent);
	}
}
