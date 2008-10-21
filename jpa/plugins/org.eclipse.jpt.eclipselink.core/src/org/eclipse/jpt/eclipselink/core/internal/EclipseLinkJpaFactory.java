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
import org.eclipse.jpt.core.JpaFile;
import org.eclipse.jpt.core.JpaProject;
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
import org.eclipse.jpt.core.context.orm.OrmBasicMapping;
import org.eclipse.jpt.core.context.orm.OrmEmbeddable;
import org.eclipse.jpt.core.context.orm.OrmIdMapping;
import org.eclipse.jpt.core.context.orm.OrmMappedSuperclass;
import org.eclipse.jpt.core.context.orm.OrmOneToManyMapping;
import org.eclipse.jpt.core.context.orm.OrmOneToOneMapping;
import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.core.context.orm.OrmTypeMapping;
import org.eclipse.jpt.core.context.orm.OrmVersionMapping;
import org.eclipse.jpt.core.context.persistence.MappingFileRef;
import org.eclipse.jpt.core.context.persistence.Persistence;
import org.eclipse.jpt.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.core.internal.XmlJpaFile;
import org.eclipse.jpt.core.internal.platform.GenericJpaFactory;
import org.eclipse.jpt.core.resource.common.JpaXmlResource;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentMember;
import org.eclipse.jpt.core.resource.persistence.XmlPersistenceUnit;
import org.eclipse.jpt.eclipselink.core.context.java.EclipseLinkJavaEntity;
import org.eclipse.jpt.eclipselink.core.context.java.EclipseLinkJavaMappedSuperclass;
import org.eclipse.jpt.eclipselink.core.context.java.JavaCaching;
import org.eclipse.jpt.eclipselink.core.context.java.JavaChangeTracking;
import org.eclipse.jpt.eclipselink.core.context.java.JavaConversionValue;
import org.eclipse.jpt.eclipselink.core.context.java.JavaConvert;
import org.eclipse.jpt.eclipselink.core.context.java.JavaConverter;
import org.eclipse.jpt.eclipselink.core.context.java.JavaConverterHolder;
import org.eclipse.jpt.eclipselink.core.context.java.JavaCustomizer;
import org.eclipse.jpt.eclipselink.core.context.java.JavaExpiryTimeOfDay;
import org.eclipse.jpt.eclipselink.core.context.java.JavaJoinFetchable;
import org.eclipse.jpt.eclipselink.core.context.java.JavaMutable;
import org.eclipse.jpt.eclipselink.core.context.java.JavaObjectTypeConverter;
import org.eclipse.jpt.eclipselink.core.context.java.JavaPrivateOwned;
import org.eclipse.jpt.eclipselink.core.context.java.JavaReadOnly;
import org.eclipse.jpt.eclipselink.core.context.java.JavaStructConverter;
import org.eclipse.jpt.eclipselink.core.context.java.JavaTypeConverter;
import org.eclipse.jpt.eclipselink.core.internal.context.EclipseLinkPersistenceUnit;
import org.eclipse.jpt.eclipselink.core.internal.context.java.EclipseLinkJavaBasicMappingImpl;
import org.eclipse.jpt.eclipselink.core.internal.context.java.EclipseLinkJavaCaching;
import org.eclipse.jpt.eclipselink.core.internal.context.java.EclipseLinkJavaChangeTracking;
import org.eclipse.jpt.eclipselink.core.internal.context.java.EclipseLinkJavaConversionValue;
import org.eclipse.jpt.eclipselink.core.internal.context.java.EclipseLinkJavaConvert;
import org.eclipse.jpt.eclipselink.core.internal.context.java.EclipseLinkJavaConverter;
import org.eclipse.jpt.eclipselink.core.internal.context.java.EclipseLinkJavaConverterHolder;
import org.eclipse.jpt.eclipselink.core.internal.context.java.EclipseLinkJavaCustomizer;
import org.eclipse.jpt.eclipselink.core.internal.context.java.EclipseLinkJavaEmbeddableImpl;
import org.eclipse.jpt.eclipselink.core.internal.context.java.EclipseLinkJavaEntityImpl;
import org.eclipse.jpt.eclipselink.core.internal.context.java.EclipseLinkJavaExpiryTimeOfDay;
import org.eclipse.jpt.eclipselink.core.internal.context.java.EclipseLinkJavaIdMappingImpl;
import org.eclipse.jpt.eclipselink.core.internal.context.java.EclipseLinkJavaJoinFetchable;
import org.eclipse.jpt.eclipselink.core.internal.context.java.EclipseLinkJavaManyToManyMappingImpl;
import org.eclipse.jpt.eclipselink.core.internal.context.java.EclipseLinkJavaManyToOneMappingImpl;
import org.eclipse.jpt.eclipselink.core.internal.context.java.EclipseLinkJavaMappedSuperclassImpl;
import org.eclipse.jpt.eclipselink.core.internal.context.java.EclipseLinkJavaMutable;
import org.eclipse.jpt.eclipselink.core.internal.context.java.EclipseLinkJavaObjectTypeConverter;
import org.eclipse.jpt.eclipselink.core.internal.context.java.EclipseLinkJavaOneToManyMappingImpl;
import org.eclipse.jpt.eclipselink.core.internal.context.java.EclipseLinkJavaOneToOneMappingImpl;
import org.eclipse.jpt.eclipselink.core.internal.context.java.EclipseLinkJavaPrivateOwned;
import org.eclipse.jpt.eclipselink.core.internal.context.java.EclipseLinkJavaReadOnly;
import org.eclipse.jpt.eclipselink.core.internal.context.java.EclipseLinkJavaStructConverter;
import org.eclipse.jpt.eclipselink.core.internal.context.java.EclipseLinkJavaTypeConverter;
import org.eclipse.jpt.eclipselink.core.internal.context.java.EclipseLinkJavaVersionMappingImpl;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.EclipseLinkOrmBasicMapping;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.EclipseLinkOrmCustomizer;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.EclipseLinkOrmEmbeddable;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.EclipseLinkOrmEntity;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.EclipseLinkOrmIdMapping;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.EclipseLinkOrmMappedSuperclass;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.EclipseLinkOrmOneToManyMapping;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.EclipseLinkOrmOneToOneMapping;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.EclipseLinkOrmVersionMapping;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.EclipseLinkOrmXml;
import org.eclipse.jpt.eclipselink.core.internal.resource.orm.EclipseLinkOrmResourceModelProvider;
import org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmResource;

public class EclipseLinkJpaFactory
	extends GenericJpaFactory
{
	protected EclipseLinkJpaFactory() {
		super();
	}
	
	
	// **************** Resource objects ***************************************
	
	public JpaFile buildEclipseLinkOrmJpaFile(JpaProject jpaProject, IFile file) {
		return new XmlJpaFile(jpaProject, file, EclipseLinkOrmResourceModelProvider.getModelProvider(file).getResource());
	}
	
	
	// **************** Context objects ****************************************
	
	@Override
	public XmlContextNode buildContextNode(JpaContextNode parent, JpaXmlResource resource) {
		if (resource instanceof EclipseLinkOrmResource) {
			return buildEclipseLinkOrmXml((MappingFileRef) parent, (EclipseLinkOrmResource) resource);
		}
		return super.buildContextNode(parent, resource);
	}
	
	
	// **************** persistence context objects ****************************
	
	@Override
	public PersistenceUnit buildPersistenceUnit(Persistence parent, XmlPersistenceUnit persistenceUnit) {
		return new EclipseLinkPersistenceUnit(parent, persistenceUnit);
	}
	
	
	// **************** eclipselink orm context objects ************************
	
	public EclipseLinkOrmXml buildEclipseLinkOrmXml(MappingFileRef parent, EclipseLinkOrmResource resource) {
		return new EclipseLinkOrmXml(parent, resource);
	}
	
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
	
	public EclipseLinkOrmCustomizer buildOrmCustomizer(OrmTypeMapping parent) {
		return new EclipseLinkOrmCustomizer(parent);
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
	
	public JavaCaching buildJavaCaching(JavaTypeMapping parent) {
		return new EclipseLinkJavaCaching(parent);
	}
	
	public JavaExpiryTimeOfDay buildJavaExpiryTimeOfDay(JavaCaching parent) {
		return new EclipseLinkJavaExpiryTimeOfDay(parent);
	}
	
	public JavaConvert buildJavaConvert(JavaAttributeMapping parent, JavaResourcePersistentAttribute jrpa) {
		return new EclipseLinkJavaConvert(parent, jrpa);
	}
	
	public JavaConverter buildJavaConverter(JavaJpaContextNode parent, JavaResourcePersistentMember jrpm) {
		return new EclipseLinkJavaConverter(parent, jrpm);
	}
	
	public JavaObjectTypeConverter buildJavaObjectTypeConverter(JavaJpaContextNode parent, JavaResourcePersistentMember jrpm) {
		return new EclipseLinkJavaObjectTypeConverter(parent, jrpm);
	}
	
	public JavaStructConverter buildJavaStructConverter(JavaJpaContextNode parent, JavaResourcePersistentMember jrpm) {
		return new EclipseLinkJavaStructConverter(parent, jrpm);
	}
	
	public JavaTypeConverter buildJavaTypeConverter(JavaJpaContextNode parent, JavaResourcePersistentMember jrpm) {
		return new EclipseLinkJavaTypeConverter(parent, jrpm);
	}
	
	public JavaConversionValue buildJavaConversionValue(JavaObjectTypeConverter parent) {
		return new EclipseLinkJavaConversionValue(parent);
	}
	
	public JavaJoinFetchable buildJavaJoinFetchable(JavaAttributeMapping parent) {
		return new EclipseLinkJavaJoinFetchable(parent);
	}
	
	public JavaPrivateOwned buildJavaPrivateOwnable(JavaAttributeMapping parent) {
		return new EclipseLinkJavaPrivateOwned(parent);
	}
	
	public JavaConverterHolder buildJavaConverterHolder(JavaTypeMapping parent) {
		return new EclipseLinkJavaConverterHolder(parent);
	}
	
	public JavaCustomizer buildJavaCustomizer(JavaTypeMapping parent) {
		return new EclipseLinkJavaCustomizer(parent);
	}
	
	public JavaMutable buildJavaMutable(JavaAttributeMapping parent) {
		return new EclipseLinkJavaMutable(parent);
	}
	
	public JavaReadOnly buildJavaReadOnly(JavaTypeMapping parent) {
		return new EclipseLinkJavaReadOnly(parent);
	}
	
	public JavaChangeTracking buildJavaChangeTracking(JavaTypeMapping parent) {
		return new EclipseLinkJavaChangeTracking(parent);
	}

}
