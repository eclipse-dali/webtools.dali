/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal;

import org.eclipse.jpt.core.context.JpaContextNode;
import org.eclipse.jpt.core.context.XmlContextNode;
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
import org.eclipse.jpt.core.context.orm.OrmBasicMapping;
import org.eclipse.jpt.core.context.orm.OrmEmbeddable;
import org.eclipse.jpt.core.context.orm.OrmIdMapping;
import org.eclipse.jpt.core.context.orm.OrmMappedSuperclass;
import org.eclipse.jpt.core.context.orm.OrmOneToManyMapping;
import org.eclipse.jpt.core.context.orm.OrmOneToOneMapping;
import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.core.context.orm.OrmVersionMapping;
import org.eclipse.jpt.core.context.persistence.MappingFileRef;
import org.eclipse.jpt.core.context.persistence.Persistence;
import org.eclipse.jpt.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.core.internal.platform.GenericJpaFactory;
import org.eclipse.jpt.core.resource.common.JpaXmlResource;
import org.eclipse.jpt.core.resource.persistence.XmlPersistenceUnit;
import org.eclipse.jpt.eclipselink.core.context.java.EclipseLinkJavaEntity;
import org.eclipse.jpt.eclipselink.core.context.java.EclipseLinkJavaMappedSuperclass;
import org.eclipse.jpt.eclipselink.core.internal.context.EclipseLinkPersistenceUnit;
import org.eclipse.jpt.eclipselink.core.internal.context.java.EclipseLinkJavaBasicMappingImpl;
import org.eclipse.jpt.eclipselink.core.internal.context.java.EclipseLinkJavaEmbeddableImpl;
import org.eclipse.jpt.eclipselink.core.internal.context.java.EclipseLinkJavaEntityImpl;
import org.eclipse.jpt.eclipselink.core.internal.context.java.EclipseLinkJavaIdMappingImpl;
import org.eclipse.jpt.eclipselink.core.internal.context.java.EclipseLinkJavaManyToManyMappingImpl;
import org.eclipse.jpt.eclipselink.core.internal.context.java.EclipseLinkJavaManyToOneMappingImpl;
import org.eclipse.jpt.eclipselink.core.internal.context.java.EclipseLinkJavaMappedSuperclassImpl;
import org.eclipse.jpt.eclipselink.core.internal.context.java.EclipseLinkJavaOneToManyMappingImpl;
import org.eclipse.jpt.eclipselink.core.internal.context.java.EclipseLinkJavaOneToOneMappingImpl;
import org.eclipse.jpt.eclipselink.core.internal.context.java.EclipseLinkJavaVersionMappingImpl;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.EclipseLinkOrmBasicMapping;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.EclipseLinkOrmEmbeddable;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.EclipseLinkOrmEntity;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.EclipseLinkOrmIdMapping;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.EclipseLinkOrmMappedSuperclass;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.EclipseLinkOrmOneToManyMapping;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.EclipseLinkOrmOneToOneMapping;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.EclipseLinkOrmVersionMapping;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.EclipseLinkOrmXml;
import org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmResource;

public class EclipseLinkJpaFactory
	extends GenericJpaFactory
{
	protected EclipseLinkJpaFactory() {
		super();
	}
		
	
	// **************** Context objects ****************************************
	
	@Override
	public XmlContextNode buildContextNode(JpaContextNode parent, JpaXmlResource resource) {
		if (resource instanceof EclipseLinkOrmResource) {
			return buildEclipseLinkOrmXml((MappingFileRef) parent, (EclipseLinkOrmResource) resource);
		}
		return super.buildContextNode(parent, resource);
	}
	
	protected EclipseLinkOrmXml buildEclipseLinkOrmXml(MappingFileRef parent, EclipseLinkOrmResource resource) {
		return new EclipseLinkOrmXml(parent, resource);
	}
	
	
	// **************** persistence context objects ****************************
	
	@Override
	public PersistenceUnit buildPersistenceUnit(Persistence parent, XmlPersistenceUnit persistenceUnit) {
		return new EclipseLinkPersistenceUnit(parent, persistenceUnit);
	}
	
	
	// **************** eclipselink orm context objects ************************
	
	@Override
	public OrmEmbeddable buildOrmEmbeddable(OrmPersistentType parent) {
		return new EclipseLinkOrmEmbeddable(parent);
	}
	
	@Override
	public EclipseLinkOrmEntity buildOrmEntity(OrmPersistentType parent) {
		return new EclipseLinkOrmEntity(parent);
	}
	
	@Override
	public OrmMappedSuperclass buildOrmMappedSuperclass(OrmPersistentType parent) {
		return new EclipseLinkOrmMappedSuperclass(parent);
	}
	
	@Override
	public OrmIdMapping buildOrmIdMapping(OrmPersistentAttribute parent) {
		return new EclipseLinkOrmIdMapping(parent);
	}
	
	@Override
	public OrmBasicMapping buildOrmBasicMapping(OrmPersistentAttribute parent) {
		return new EclipseLinkOrmBasicMapping(parent);
	}
	
	@Override
	public OrmVersionMapping buildOrmVersionMapping(OrmPersistentAttribute parent) {
		return new EclipseLinkOrmVersionMapping(parent);
	}
	
	@Override
	public OrmOneToOneMapping buildOrmOneToOneMapping(OrmPersistentAttribute parent) {
		return new EclipseLinkOrmOneToOneMapping(parent);
	}
	
	@Override
	public OrmOneToManyMapping buildOrmOneToManyMapping(OrmPersistentAttribute parent) {
		return new EclipseLinkOrmOneToManyMapping(parent);
	}
	
	
	// **************** java context objects ***********************************

	@Override
	public JavaBasicMapping buildJavaBasicMapping(JavaPersistentAttribute parent) {
		return new EclipseLinkJavaBasicMappingImpl(parent);
	}
	
	@Override
	public JavaEmbeddable buildJavaEmbeddable(JavaPersistentType parent) {
		return new EclipseLinkJavaEmbeddableImpl(parent);
	}
	
	@Override
	public EclipseLinkJavaEntity buildJavaEntity(JavaPersistentType parent) {
		return new EclipseLinkJavaEntityImpl(parent);
	}
	
	@Override
	public JavaIdMapping buildJavaIdMapping(JavaPersistentAttribute parent) {
		return new EclipseLinkJavaIdMappingImpl(parent);
	}
	
	@Override
	public EclipseLinkJavaMappedSuperclass buildJavaMappedSuperclass(JavaPersistentType parent) {
		return new EclipseLinkJavaMappedSuperclassImpl(parent);
	}
	
	@Override
	public JavaVersionMapping buildJavaVersionMapping(JavaPersistentAttribute parent) {
		return new EclipseLinkJavaVersionMappingImpl(parent);
	}
	
	@Override
	public JavaOneToManyMapping buildJavaOneToManyMapping(JavaPersistentAttribute parent) {
		return new EclipseLinkJavaOneToManyMappingImpl(parent);
	}
	
	@Override
	public JavaOneToOneMapping buildJavaOneToOneMapping(JavaPersistentAttribute parent) {
		return new EclipseLinkJavaOneToOneMappingImpl(parent);
	}
	
	@Override
	public JavaManyToManyMapping buildJavaManyToManyMapping(JavaPersistentAttribute parent) {
		return new EclipseLinkJavaManyToManyMappingImpl(parent);
	}
	
	@Override
	public JavaManyToOneMapping buildJavaManyToOneMapping(JavaPersistentAttribute parent) {
		return new EclipseLinkJavaManyToOneMappingImpl(parent);
	}
}
