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

import org.eclipse.core.resources.IFile;
import org.eclipse.jpt.core.JpaProject;
import org.eclipse.jpt.core.ResourceModel;
import org.eclipse.jpt.core.context.JpaContextNode;
import org.eclipse.jpt.core.context.XmlContextNode;
import org.eclipse.jpt.core.context.java.JavaAttributeMapping;
import org.eclipse.jpt.core.context.java.JavaBasicMapping;
import org.eclipse.jpt.core.context.java.JavaEmbeddable;
import org.eclipse.jpt.core.context.java.JavaIdMapping;
import org.eclipse.jpt.core.context.java.JavaJpaContextNode;
import org.eclipse.jpt.core.context.java.JavaManyToManyMapping;
import org.eclipse.jpt.core.context.java.JavaManyToOneMapping;
import org.eclipse.jpt.core.context.java.JavaOneToManyMapping;
import org.eclipse.jpt.core.context.java.JavaOneToOneMapping;
import org.eclipse.jpt.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.core.context.java.JavaPersistentType;
import org.eclipse.jpt.core.context.java.JavaTypeMapping;
import org.eclipse.jpt.core.context.java.JavaVersionMapping;
import org.eclipse.jpt.core.context.persistence.MappingFileRef;
import org.eclipse.jpt.core.context.persistence.Persistence;
import org.eclipse.jpt.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.core.internal.platform.GenericJpaFactory;
import org.eclipse.jpt.core.resource.common.JpaXmlResource;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentMember;
import org.eclipse.jpt.core.resource.persistence.XmlPersistenceUnit;
import org.eclipse.jpt.eclipselink.core.EclipseLinkJpaFactory;
import org.eclipse.jpt.eclipselink.core.context.java.EclipseLinkJavaCaching;
import org.eclipse.jpt.eclipselink.core.context.java.EclipseLinkJavaConversionValue;
import org.eclipse.jpt.eclipselink.core.context.java.EclipseLinkJavaConvert;
import org.eclipse.jpt.eclipselink.core.context.java.EclipseLinkJavaConverter;
import org.eclipse.jpt.eclipselink.core.context.java.EclipseLinkJavaEntity;
import org.eclipse.jpt.eclipselink.core.context.java.EclipseLinkJavaMappedSuperclass;
import org.eclipse.jpt.eclipselink.core.context.java.EclipseLinkJavaObjectTypeConverter;
import org.eclipse.jpt.eclipselink.core.context.java.EclipseLinkJavaStructConverter;
import org.eclipse.jpt.eclipselink.core.context.java.EclipseLinkJavaTypeConverter;
import org.eclipse.jpt.eclipselink.core.context.java.JavaConverterHolder;
import org.eclipse.jpt.eclipselink.core.context.java.JavaJoinFetchable;
import org.eclipse.jpt.eclipselink.core.context.java.JavaPrivateOwnable;
import org.eclipse.jpt.eclipselink.core.context.orm.EclipseLinkOrmXml;
import org.eclipse.jpt.eclipselink.core.internal.context.EclipseLinkPersistenceUnit;
import org.eclipse.jpt.eclipselink.core.internal.context.java.EclipseLinkJavaBasicMappingImpl;
import org.eclipse.jpt.eclipselink.core.internal.context.java.EclipseLinkJavaCachingImpl;
import org.eclipse.jpt.eclipselink.core.internal.context.java.EclipseLinkJavaConversionValueImpl;
import org.eclipse.jpt.eclipselink.core.internal.context.java.EclipseLinkJavaConvertImpl;
import org.eclipse.jpt.eclipselink.core.internal.context.java.EclipseLinkJavaConverterHolder;
import org.eclipse.jpt.eclipselink.core.internal.context.java.EclipseLinkJavaConverterImpl;
import org.eclipse.jpt.eclipselink.core.internal.context.java.EclipseLinkJavaEmbeddableImpl;
import org.eclipse.jpt.eclipselink.core.internal.context.java.EclipseLinkJavaEntityImpl;
import org.eclipse.jpt.eclipselink.core.internal.context.java.EclipseLinkJavaIdMappingImpl;
import org.eclipse.jpt.eclipselink.core.internal.context.java.EclipseLinkJavaJoinFetchable;
import org.eclipse.jpt.eclipselink.core.internal.context.java.EclipseLinkJavaManyToManyMappingImpl;
import org.eclipse.jpt.eclipselink.core.internal.context.java.EclipseLinkJavaManyToOneMappingImpl;
import org.eclipse.jpt.eclipselink.core.internal.context.java.EclipseLinkJavaMappedSuperclassImpl;
import org.eclipse.jpt.eclipselink.core.internal.context.java.EclipseLinkJavaObjectTypeConverterImpl;
import org.eclipse.jpt.eclipselink.core.internal.context.java.EclipseLinkJavaOneToManyMappingImpl;
import org.eclipse.jpt.eclipselink.core.internal.context.java.EclipseLinkJavaOneToOneMappingImpl;
import org.eclipse.jpt.eclipselink.core.internal.context.java.EclipseLinkJavaPrivateOwnable;
import org.eclipse.jpt.eclipselink.core.internal.context.java.EclipseLinkJavaStructConverterImpl;
import org.eclipse.jpt.eclipselink.core.internal.context.java.EclipseLinkJavaTypeConverterImpl;
import org.eclipse.jpt.eclipselink.core.internal.context.java.EclipseLinkJavaVersionMappingImpl;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.EclipseLinkOrmXmlImpl;
import org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmResource;
import org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmResourceModel;

public class EclipseLinkJpaFactoryImpl extends GenericJpaFactory 
	implements EclipseLinkJpaFactory
{
	protected EclipseLinkJpaFactoryImpl() {
		super();
	}
	
	
	// **************** Resource objects ***************************************
	
	@Override
	public ResourceModel buildResourceModel(JpaProject jpaProject, IFile file, String contentTypeId) {
		if (JptEclipseLinkCorePlugin.ECLIPSELINK_ORM_XML_CONTENT_TYPE.equals(contentTypeId)) {
			return buildEclipseLinkOrmResourceModel(file);
		}
		return super.buildResourceModel(jpaProject, file, contentTypeId);
	}
	
	protected ResourceModel buildEclipseLinkOrmResourceModel(IFile file) {
		return new EclipseLinkOrmResourceModel(file);
	}
	
	
	// **************** Context objects ****************************************
	
	@Override
	public XmlContextNode buildContext(JpaContextNode parent, JpaXmlResource resource) {
		if (resource instanceof EclipseLinkOrmResource) {
			return buildEclipseLinkOrmXml((MappingFileRef) parent, (EclipseLinkOrmResource) resource);
		}
		return super.buildContext(parent, resource);
	}
	
	public EclipseLinkOrmXml buildEclipseLinkOrmXml(MappingFileRef parent, EclipseLinkOrmResource resource) {
		return new EclipseLinkOrmXmlImpl(parent, resource);
	}

	@Override
	public PersistenceUnit buildPersistenceUnit(Persistence parent, XmlPersistenceUnit persistenceUnit) {
		return new EclipseLinkPersistenceUnit(parent, persistenceUnit);
	}
	
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
	
	public EclipseLinkJavaCaching buildEclipseLinkJavaCaching(JavaTypeMapping parent) {
		return new EclipseLinkJavaCachingImpl(parent);
	}
	
	public EclipseLinkJavaConvert buildEclipseLinkJavaConvert(JavaAttributeMapping parent, JavaResourcePersistentAttribute jrpa) {
		return new EclipseLinkJavaConvertImpl(parent, jrpa);
	}
	
	public EclipseLinkJavaConverter buildEclipseLinkJavaConverter(JavaJpaContextNode parent, JavaResourcePersistentMember jrpm) {
		return new EclipseLinkJavaConverterImpl(parent, jrpm);
	}
	
	public EclipseLinkJavaObjectTypeConverter buildEclipseLinkJavaObjectTypeConverter(JavaJpaContextNode parent, JavaResourcePersistentMember jrpm) {
		return new EclipseLinkJavaObjectTypeConverterImpl(parent, jrpm);
	}
	
	public EclipseLinkJavaStructConverter buildEclipseLinkJavaStructConverter(JavaJpaContextNode parent, JavaResourcePersistentMember jrpm) {
		return new EclipseLinkJavaStructConverterImpl(parent, jrpm);
	}
	
	public EclipseLinkJavaTypeConverter buildEclipseLinkJavaTypeConverter(JavaJpaContextNode parent, JavaResourcePersistentMember jrpm) {
		return new EclipseLinkJavaTypeConverterImpl(parent, jrpm);
	}
	
	public EclipseLinkJavaConversionValue buildJavaConversionValue(EclipseLinkJavaObjectTypeConverter parent) {
		return new EclipseLinkJavaConversionValueImpl(parent);
	}
	
	public JavaJoinFetchable buildJavaJoinFetchable(JavaAttributeMapping parent) {
		return new EclipseLinkJavaJoinFetchable(parent);
	}
	
	public JavaPrivateOwnable buildJavaPrivateOwnable(JavaAttributeMapping parent) {
		return new EclipseLinkJavaPrivateOwnable(parent);
	}
	
	public JavaConverterHolder buildJavaConverterHolder(JavaTypeMapping parent) {
		return new EclipseLinkJavaConverterHolder(parent);
	}
}
