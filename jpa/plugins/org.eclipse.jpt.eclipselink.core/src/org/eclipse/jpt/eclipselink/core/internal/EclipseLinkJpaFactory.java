/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal;

import org.eclipse.jpt.core.JpaProject;
import org.eclipse.jpt.core.context.PersistentType;
import org.eclipse.jpt.core.context.java.JavaBasicMapping;
import org.eclipse.jpt.core.context.java.JavaEmbeddable;
import org.eclipse.jpt.core.context.java.JavaIdMapping;
import org.eclipse.jpt.core.context.java.JavaManyToManyMapping;
import org.eclipse.jpt.core.context.java.JavaManyToOneMapping;
import org.eclipse.jpt.core.context.java.JavaOneToManyMapping;
import org.eclipse.jpt.core.context.java.JavaOneToOneMapping;
import org.eclipse.jpt.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.core.context.java.JavaPersistentType;
import org.eclipse.jpt.core.context.java.JavaVersionMapping;
import org.eclipse.jpt.core.internal.AbstractJpaFactory;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.eclipselink.core.EclipseLinkJpaProject;
import org.eclipse.jpt.eclipselink.core.context.java.JavaEclipseLinkEntity;
import org.eclipse.jpt.eclipselink.core.context.java.JavaEclipseLinkMappedSuperclass;
import org.eclipse.jpt.eclipselink.core.internal.context.java.JavaEclipseLinkBasicCollectionMapping;
import org.eclipse.jpt.eclipselink.core.internal.context.java.JavaEclipseLinkBasicMapMapping;
import org.eclipse.jpt.eclipselink.core.internal.context.java.JavaEclipseLinkBasicMapping;
import org.eclipse.jpt.eclipselink.core.internal.context.java.JavaEclipseLinkEmbeddableImpl;
import org.eclipse.jpt.eclipselink.core.internal.context.java.JavaEclipseLinkEntityImpl;
import org.eclipse.jpt.eclipselink.core.internal.context.java.JavaEclipseLinkIdMapping;
import org.eclipse.jpt.eclipselink.core.internal.context.java.JavaEclipseLinkManyToManyMapping;
import org.eclipse.jpt.eclipselink.core.internal.context.java.JavaEclipseLinkManyToOneMapping;
import org.eclipse.jpt.eclipselink.core.internal.context.java.JavaEclipseLinkMappedSuperclassImpl;
import org.eclipse.jpt.eclipselink.core.internal.context.java.JavaEclipseLinkOneToManyMapping;
import org.eclipse.jpt.eclipselink.core.internal.context.java.JavaEclipseLinkOneToOneMapping;
import org.eclipse.jpt.eclipselink.core.internal.context.java.JavaEclipseLinkPersistentAttribute;
import org.eclipse.jpt.eclipselink.core.internal.context.java.JavaEclipseLinkTransformationMapping;
import org.eclipse.jpt.eclipselink.core.internal.context.java.JavaEclipseLinkVariableOneToOneMapping;
import org.eclipse.jpt.eclipselink.core.internal.context.java.JavaEclipseLinkVersionMapping;

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
	
	
	
	// ********** Java Context Model **********

	@Override
	public JavaPersistentAttribute buildJavaPersistentAttribute(PersistentType parent, JavaResourcePersistentAttribute jrpa) {
		return new JavaEclipseLinkPersistentAttribute(parent, jrpa);
	}
	
	@Override
	public JavaBasicMapping buildJavaBasicMapping(JavaPersistentAttribute parent) {
		return new JavaEclipseLinkBasicMapping(parent);
	}
	
	@Override
	public JavaEmbeddable buildJavaEmbeddable(JavaPersistentType parent) {
		return new JavaEclipseLinkEmbeddableImpl(parent);
	}
	
	@Override
	public JavaEclipseLinkEntity buildJavaEntity(JavaPersistentType parent) {
		return new JavaEclipseLinkEntityImpl(parent);
	}
	
	@Override
	public JavaIdMapping buildJavaIdMapping(JavaPersistentAttribute parent) {
		return new JavaEclipseLinkIdMapping(parent);
	}
	
	@Override
	public JavaEclipseLinkMappedSuperclass buildJavaMappedSuperclass(JavaPersistentType parent) {
		return new JavaEclipseLinkMappedSuperclassImpl(parent);
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
