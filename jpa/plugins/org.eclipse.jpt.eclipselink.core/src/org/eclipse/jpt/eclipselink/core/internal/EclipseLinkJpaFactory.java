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

import org.eclipse.jpt.core.context.MappingFile;
import org.eclipse.jpt.core.context.java.JavaBasicMapping;
import org.eclipse.jpt.core.context.java.JavaEmbeddable;
import org.eclipse.jpt.core.context.java.JavaEmbeddedIdMapping;
import org.eclipse.jpt.core.context.java.JavaEmbeddedMapping;
import org.eclipse.jpt.core.context.java.JavaIdMapping;
import org.eclipse.jpt.core.context.java.JavaManyToManyMapping;
import org.eclipse.jpt.core.context.java.JavaManyToOneMapping;
import org.eclipse.jpt.core.context.java.JavaOneToManyMapping;
import org.eclipse.jpt.core.context.java.JavaOneToOneMapping;
import org.eclipse.jpt.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.core.context.java.JavaPersistentType;
import org.eclipse.jpt.core.context.java.JavaVersionMapping;
import org.eclipse.jpt.core.context.orm.EntityMappings;
import org.eclipse.jpt.core.context.orm.OrmBasicMapping;
import org.eclipse.jpt.core.context.orm.OrmEmbeddable;
import org.eclipse.jpt.core.context.orm.OrmEmbeddedIdMapping;
import org.eclipse.jpt.core.context.orm.OrmEmbeddedMapping;
import org.eclipse.jpt.core.context.orm.OrmEntity;
import org.eclipse.jpt.core.context.orm.OrmIdMapping;
import org.eclipse.jpt.core.context.orm.OrmManyToManyMapping;
import org.eclipse.jpt.core.context.orm.OrmManyToOneMapping;
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
import org.eclipse.jpt.core.internal.platform.GenericJpaFactory;
import org.eclipse.jpt.core.resource.orm.XmlBasic;
import org.eclipse.jpt.core.resource.orm.XmlEntityMappings;
import org.eclipse.jpt.core.resource.orm.XmlId;
import org.eclipse.jpt.core.resource.orm.XmlManyToMany;
import org.eclipse.jpt.core.resource.orm.XmlManyToOne;
import org.eclipse.jpt.core.resource.orm.XmlOneToMany;
import org.eclipse.jpt.core.resource.orm.XmlOneToOne;
import org.eclipse.jpt.core.resource.orm.XmlVersion;
import org.eclipse.jpt.core.resource.persistence.XmlPersistenceUnit;
import org.eclipse.jpt.eclipselink.core.context.java.EclipseLinkJavaEntity;
import org.eclipse.jpt.eclipselink.core.context.java.EclipseLinkJavaMappedSuperclass;
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
import org.eclipse.jpt.eclipselink.core.internal.context.java.JavaBasicCollectionMapping;
import org.eclipse.jpt.eclipselink.core.internal.context.java.JavaBasicMapMapping;
import org.eclipse.jpt.eclipselink.core.internal.context.java.JavaTransformationMapping;
import org.eclipse.jpt.eclipselink.core.internal.context.java.JavaVariableOneToOneMapping;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.EclipseLinkEntityMappings;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.EclipseLinkEntityMappingsImpl;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.EclipseLinkOrmBasicMapping;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.EclipseLinkOrmEmbeddableImpl;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.EclipseLinkOrmEmbeddedIdMapping;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.EclipseLinkOrmEmbeddedMapping;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.EclipseLinkOrmEntityImpl;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.EclipseLinkOrmIdMapping;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.EclipseLinkOrmManyToManyMapping;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.EclipseLinkOrmManyToOneMapping;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.EclipseLinkOrmMappedSuperclassImpl;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.EclipseLinkOrmOneToManyMapping;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.EclipseLinkOrmOneToOneMapping;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.EclipseLinkOrmPersistentType;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.EclipseLinkOrmVersionMapping;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.EclipseLinkOrmXml;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.EclipseLinkVirtualXmlBasic;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.EclipseLinkVirtualXmlEmbedded;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.EclipseLinkVirtualXmlEmbeddedId;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.EclipseLinkVirtualXmlId;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.EclipseLinkVirtualXmlManyToMany;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.EclipseLinkVirtualXmlManyToOne;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.EclipseLinkVirtualXmlOneToMany;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.EclipseLinkVirtualXmlOneToOne;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.EclipseLinkVirtualXmlVersion;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.OrmBasicCollectionMapping;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.OrmBasicMapMapping;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.OrmTransformationMapping;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.OrmVariableOneToOneMapping;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.VirtualXmlBasicCollection;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.VirtualXmlBasicMap;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.VirtualXmlTransformation;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.VirtualXmlVariableOneToOne;
import org.eclipse.jpt.eclipselink.core.internal.context.persistence.EclipseLinkPersistenceUnit;
import org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmXmlResource;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlBasicCollection;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlBasicMap;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlEmbedded;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlEmbeddedId;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlTransformation;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlVariableOneToOne;

public class EclipseLinkJpaFactory
	extends GenericJpaFactory
{
	protected EclipseLinkJpaFactory() {
		super();
	}
		
	
	// ********** Context Nodes **********
	
	public MappingFile buildEclipseLinkMappingFile(MappingFileRef parent, EclipseLinkOrmXmlResource resource) {
		return this.buildEclipseLinkOrmXml(parent, resource);
	}
	
	protected EclipseLinkOrmXml buildEclipseLinkOrmXml(MappingFileRef parent, EclipseLinkOrmXmlResource resource) {
		EclipseLinkOrmXml eclipseLinkOrmXml = new EclipseLinkOrmXml(parent);
		eclipseLinkOrmXml.initialize(resource);
		return eclipseLinkOrmXml;
	}
	
	
	// ********** Persistence Context Model **********
	
	@Override
	public PersistenceUnit buildPersistenceUnit(Persistence parent, XmlPersistenceUnit persistenceUnit) {
		return new EclipseLinkPersistenceUnit(parent, persistenceUnit);
	}
	
	
	// ********** EclipseLink-specific ORM Virtual Resource Model **********
	
	public XmlBasic buildEclipseLinkVirtualXmlBasic(OrmTypeMapping ormTypeMapping, JavaBasicMapping javaBasicMapping) {
		return new EclipseLinkVirtualXmlBasic(ormTypeMapping, javaBasicMapping);
	}
	
	public XmlId buildEclipseLinkVirtualXmlId(OrmTypeMapping ormTypeMapping, JavaIdMapping javaIdMapping) {
		return new EclipseLinkVirtualXmlId(ormTypeMapping, javaIdMapping);
	}
	
	public XmlEmbeddedId buildEclipseLinkVirtualXmlEmbeddedId(OrmTypeMapping ormTypeMapping, JavaEmbeddedIdMapping javaEmbeddedIdMapping) {
		return new EclipseLinkVirtualXmlEmbeddedId(ormTypeMapping, javaEmbeddedIdMapping);
	}
	
	public XmlEmbedded buildEclipseLinkVirtualXmlEmbedded(OrmTypeMapping ormTypeMapping, JavaEmbeddedMapping javaEmbeddedMapping) {
		return new EclipseLinkVirtualXmlEmbedded(ormTypeMapping, javaEmbeddedMapping);
	}
	
	public XmlManyToMany buildEclipseLinkVirtualXmlManyToMany(OrmTypeMapping ormTypeMapping, JavaManyToManyMapping javaManyToManyMapping) {
		return new EclipseLinkVirtualXmlManyToMany(ormTypeMapping, javaManyToManyMapping);
	}
	
	public XmlManyToOne buildEclipseLinkVirtualXmlManyToOne(OrmTypeMapping ormTypeMapping, JavaManyToOneMapping javaManyToOneMapping) {
		return new EclipseLinkVirtualXmlManyToOne(ormTypeMapping, javaManyToOneMapping);
	}
	
	public XmlOneToMany buildEclipseLinkVirtualXmlOneToMany(OrmTypeMapping ormTypeMapping, JavaOneToManyMapping javaOneToManyMapping) {
		return new EclipseLinkVirtualXmlOneToMany(ormTypeMapping, javaOneToManyMapping);
	}
	
	public XmlOneToOne buildEclipseLinkVirtualXmlOneToOne(OrmTypeMapping ormTypeMapping, JavaOneToOneMapping javaOneToOneMapping) {
		return new EclipseLinkVirtualXmlOneToOne(ormTypeMapping, javaOneToOneMapping);
	}
	
	public XmlVersion buildEclipseLinkVirtualXmlVersion(OrmTypeMapping ormTypeMapping, JavaVersionMapping javaVersionMapping) {
		return new EclipseLinkVirtualXmlVersion(ormTypeMapping, javaVersionMapping);
	}
	
	public XmlBasicCollection buildVirtualXmlBasicCollection(OrmTypeMapping ormTypeMapping, JavaBasicCollectionMapping javaBasicCollectionMapping) {
		return new VirtualXmlBasicCollection(ormTypeMapping, javaBasicCollectionMapping);
	}
	
	public XmlBasicMap buildVirtualXmlBasicMap(OrmTypeMapping ormTypeMapping, JavaBasicMapMapping javaBasicMapMapping) {
		return new VirtualXmlBasicMap(ormTypeMapping, javaBasicMapMapping);
	}
	
	public XmlTransformation buildVirtualXmlTransformation(OrmTypeMapping ormTypeMapping, JavaTransformationMapping javaTransformationMapping) {
		return new VirtualXmlTransformation(ormTypeMapping, javaTransformationMapping);
	}
	
	public XmlVariableOneToOne buildVirtualXmlVariableOneToOne(OrmTypeMapping ormTypeMapping, JavaVariableOneToOneMapping javaVariableOneToOneMapping) {
		return new VirtualXmlVariableOneToOne(ormTypeMapping, javaVariableOneToOneMapping);
	}
	
	
	// ********** EclipseLink-specific ORM Context Model **********
	
	public EntityMappings buildEclipseLinkEntityMappings(EclipseLinkOrmXml parent, XmlEntityMappings xmlEntityMappings) {
		EntityMappings entityMappings = new EclipseLinkEntityMappingsImpl(parent);
		entityMappings.initialize(xmlEntityMappings);
		return entityMappings;
	}

	public OrmPersistentType buildEclipseLinkOrmPersistentType(EclipseLinkEntityMappings parent, String mappingKey) {
		return new EclipseLinkOrmPersistentType(parent, mappingKey);
	}

	public OrmEmbeddable buildEclipseLinkOrmEmbeddable(OrmPersistentType type) {
		return new EclipseLinkOrmEmbeddableImpl(type);
	}

	public OrmEntity buildEclipseLinkOrmEntity(OrmPersistentType type) {
		return new EclipseLinkOrmEntityImpl(type);
	}
	
	public OrmMappedSuperclass buildEclipseLinkOrmMappedSuperclass(OrmPersistentType type) {
		return new EclipseLinkOrmMappedSuperclassImpl(type);
	}
	
	public OrmBasicMapping buildEclipseLinkOrmBasicMapping(OrmPersistentAttribute parent) {
		return new EclipseLinkOrmBasicMapping(parent);
	}
	
	public OrmIdMapping buildEclipseLinkOrmIdMapping(OrmPersistentAttribute parent) {
		return new EclipseLinkOrmIdMapping(parent);
	}
	
	public OrmEmbeddedIdMapping buildEclipseLinkOrmEmbeddedIdMapping(OrmPersistentAttribute parent) {
		return new EclipseLinkOrmEmbeddedIdMapping(parent);
	}
	
	public OrmEmbeddedMapping buildEclipseLinkOrmEmbeddedMapping(OrmPersistentAttribute parent) {
		return new EclipseLinkOrmEmbeddedMapping(parent);
	}

	public OrmManyToManyMapping buildEclipseLinkOrmManyToManyMapping(OrmPersistentAttribute parent) {
		return new EclipseLinkOrmManyToManyMapping(parent);
	}
	
	public OrmManyToOneMapping buildEclipseLinkOrmManyToOneMapping(OrmPersistentAttribute parent) {
		return new EclipseLinkOrmManyToOneMapping(parent);
	}
	
	public OrmOneToManyMapping buildEclipseLinkOrmOneToManyMapping(OrmPersistentAttribute parent) {
		return new EclipseLinkOrmOneToManyMapping(parent);
	}
	
	public OrmOneToOneMapping buildEclipseLinkOrmOneToOneMapping(OrmPersistentAttribute parent) {
		return new EclipseLinkOrmOneToOneMapping(parent);
	}
	
	public OrmVersionMapping buildEclipseLinkOrmVersionMapping(OrmPersistentAttribute parent) {
		return new EclipseLinkOrmVersionMapping(parent);
	}
		
	public OrmBasicCollectionMapping buildOrmBasicCollectionMapping(OrmPersistentAttribute parent) {
		return new OrmBasicCollectionMapping(parent);
	}
	
	public OrmBasicMapMapping buildOrmBasicMapMapping(OrmPersistentAttribute parent) {
		return new OrmBasicMapMapping(parent);
	}
	
	public OrmTransformationMapping buildOrmTransformationMapping(OrmPersistentAttribute parent) {
		return new OrmTransformationMapping(parent);
	}
	
	public OrmVariableOneToOneMapping buildOrmVariableOneToOneMapping(OrmPersistentAttribute parent) {
		return new OrmVariableOneToOneMapping(parent);
	}
	
	// ********** Java Context Model **********

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

	public JavaBasicCollectionMapping buildJavaBasicCollectionMapping(JavaPersistentAttribute parent) {
		return new JavaBasicCollectionMapping(parent);
	}
	
	public JavaBasicMapMapping buildJavaBasicMapMapping(JavaPersistentAttribute parent) {
		return new JavaBasicMapMapping(parent);
	}
	
	public JavaTransformationMapping buildJavaTransformationMapping(JavaPersistentAttribute parent) {
		return new JavaTransformationMapping(parent);
	}

	public JavaVariableOneToOneMapping buildJavaVariableOneToOneMapping(JavaPersistentAttribute parent) {
		return new JavaVariableOneToOneMapping(parent);
	}
}
