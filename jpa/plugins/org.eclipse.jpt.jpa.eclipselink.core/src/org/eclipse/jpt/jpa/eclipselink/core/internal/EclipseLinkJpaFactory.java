/*******************************************************************************
 * Copyright (c) 2008, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal;

import org.eclipse.core.runtime.IProgressMonitor;
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
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.java.EclipseLinkJavaBasicCollectionMapping;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.java.EclipseLinkJavaBasicMapMapping;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.java.EclipseLinkJavaBasicMapping;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.java.EclipseLinkJavaEmbeddableImpl;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.java.EclipseLinkJavaEntityImpl;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.java.EclipseLinkJavaIdMapping;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.java.EclipseLinkJavaManyToManyMapping;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.java.EclipseLinkJavaManyToOneMapping;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.java.EclipseLinkJavaMappedSuperclassImpl;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.java.EclipseLinkJavaOneToManyMapping;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.java.EclipseLinkJavaOneToOneMapping;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.java.EclipseLinkSpecifiedJavaPersistentAttribute;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.java.EclipseLinkJavaTransformationMapping;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.java.EclipseLinkJavaVariableOneToOneMapping;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.java.EclipseLinkJavaVersionMapping;

public class EclipseLinkJpaFactory
	extends AbstractJpaFactory
{
	public EclipseLinkJpaFactory() {
		super();
	}


	// ********** Core Model **********
	
	@Override
	public EclipseLinkJpaProject buildJpaProject(JpaProject.Config config, IProgressMonitor monitor) {
		return new EclipseLinkJpaProjectImpl(config, monitor);
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
		return new EclipseLinkJavaBasicMapping(parent);
	}
	
	@Override
	public JavaEmbeddable buildJavaEmbeddable(JavaPersistentType parent, EmbeddableAnnotation embeddableAnnotation) {
		return new EclipseLinkJavaEmbeddableImpl(parent, embeddableAnnotation);
	}
	
	@Override
	public EclipseLinkJavaEntity buildJavaEntity(JavaPersistentType parent, EntityAnnotation entityAnnotation) {
		return new EclipseLinkJavaEntityImpl(parent, entityAnnotation);
	}
	
	@Override
	public JavaIdMapping buildJavaIdMapping(JavaSpecifiedPersistentAttribute parent) {
		return new EclipseLinkJavaIdMapping(parent);
	}
	
	@Override
	public EclipseLinkJavaMappedSuperclass buildJavaMappedSuperclass(JavaPersistentType parent, MappedSuperclassAnnotation mappedSuperclassAnnotation) {
		return new EclipseLinkJavaMappedSuperclassImpl(parent, mappedSuperclassAnnotation);
	}
	
	@Override
	public JavaVersionMapping buildJavaVersionMapping(JavaSpecifiedPersistentAttribute parent) {
		return new EclipseLinkJavaVersionMapping(parent);
	}
	
	@Override
	public JavaOneToManyMapping buildJavaOneToManyMapping(JavaSpecifiedPersistentAttribute parent) {
		return new EclipseLinkJavaOneToManyMapping(parent);
	}
	
	@Override
	public JavaOneToOneMapping buildJavaOneToOneMapping(JavaSpecifiedPersistentAttribute parent) {
		return new EclipseLinkJavaOneToOneMapping(parent);
	}
	
	@Override
	public JavaManyToManyMapping buildJavaManyToManyMapping(JavaSpecifiedPersistentAttribute parent) {
		return new EclipseLinkJavaManyToManyMapping(parent);
	}
	
	@Override
	public JavaManyToOneMapping buildJavaManyToOneMapping(JavaSpecifiedPersistentAttribute parent) {
		return new EclipseLinkJavaManyToOneMapping(parent);
	}


	// ********** EclipseLink-specific Java Context Model **********

	public EclipseLinkJavaBasicCollectionMapping buildJavaEclipseLinkBasicCollectionMapping(JavaSpecifiedPersistentAttribute parent) {
		return new EclipseLinkJavaBasicCollectionMapping(parent);
	}
	
	public EclipseLinkJavaBasicMapMapping buildJavaEclipseLinkBasicMapMapping(JavaSpecifiedPersistentAttribute parent) {
		return new EclipseLinkJavaBasicMapMapping(parent);
	}
	
	public EclipseLinkJavaTransformationMapping buildJavaEclipseLinkTransformationMapping(JavaSpecifiedPersistentAttribute parent) {
		return new EclipseLinkJavaTransformationMapping(parent);
	}

	public EclipseLinkJavaVariableOneToOneMapping buildJavaEclipseLinkVariableOneToOneMapping(JavaSpecifiedPersistentAttribute parent) {
		return new EclipseLinkJavaVariableOneToOneMapping(parent);
	}
}
