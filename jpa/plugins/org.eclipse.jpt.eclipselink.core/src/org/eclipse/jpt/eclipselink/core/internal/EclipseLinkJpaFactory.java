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

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jpt.core.JpaProject;
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
import org.eclipse.jpt.core.context.orm.OrmXml;
import org.eclipse.jpt.core.context.persistence.MappingFileRef;
import org.eclipse.jpt.core.context.persistence.Persistence;
import org.eclipse.jpt.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.core.internal.platform.GenericJpaFactory;
import org.eclipse.jpt.core.resource.orm.XmlTypeMapping;
import org.eclipse.jpt.core.resource.persistence.XmlPersistenceUnit;
import org.eclipse.jpt.core.resource.xml.JpaXmlResource;
import org.eclipse.jpt.eclipselink.core.EclipseLinkJpaProject;
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
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlBasic;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlBasicCollection;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlBasicMap;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlEmbeddable;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlEmbedded;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlEmbeddedId;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlEntity;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlEntityMappings;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlId;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlManyToMany;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlManyToOne;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlMappedSuperclass;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlOneToMany;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlOneToOne;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlTransformation;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlVariableOneToOne;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlVersion;

public class EclipseLinkJpaFactory
	extends GenericJpaFactory
{
	protected EclipseLinkJpaFactory() {
		super();
	}
		
	// ********** Core Model **********
	
	@Override
	public EclipseLinkJpaProject buildJpaProject(JpaProject.Config config) throws CoreException {
		return new EclipseLinkJpaProjectImpl(config);
	}
	
	
	// ********** Context Nodes **********
	
	public MappingFile buildEclipseLinkMappingFile(MappingFileRef parent, JpaXmlResource resource) {
		return this.buildEclipseLinkOrmXml(parent, resource);
	}
	
	protected EclipseLinkOrmXml buildEclipseLinkOrmXml(MappingFileRef parent, JpaXmlResource resource) {
		return new EclipseLinkOrmXml(parent, resource);
	}
	
	
	// ********** Persistence Context Model **********
	
	@Override
	public PersistenceUnit buildPersistenceUnit(Persistence parent, XmlPersistenceUnit xmlPersistenceUnit) {
		return new EclipseLinkPersistenceUnit(parent, xmlPersistenceUnit);
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
	
	public EntityMappings buildEclipseLinkEntityMappings(OrmXml parent, XmlEntityMappings xmlEntityMappings) {
		return new EclipseLinkEntityMappingsImpl(parent, xmlEntityMappings);
	}

	public OrmPersistentType buildEclipseLinkOrmPersistentType(EclipseLinkEntityMappings parent , XmlTypeMapping resourceMapping) {
		return new EclipseLinkOrmPersistentType(parent, resourceMapping);
	}

	public OrmEmbeddable buildEclipseLinkOrmEmbeddable(OrmPersistentType type, XmlEmbeddable resourceMapping) {
		return new EclipseLinkOrmEmbeddableImpl(type, resourceMapping);
	}

	public OrmEntity buildEclipseLinkOrmEntity(OrmPersistentType type, XmlEntity resourceMapping) {
		return new EclipseLinkOrmEntityImpl(type, resourceMapping);
	}
	
	public OrmMappedSuperclass buildEclipseLinkOrmMappedSuperclass(OrmPersistentType type, XmlMappedSuperclass resourceMapping) {
		return new EclipseLinkOrmMappedSuperclassImpl(type, resourceMapping);
	}
	
	public OrmBasicMapping buildEclipseLinkOrmBasicMapping(OrmPersistentAttribute parent, XmlBasic resourceMapping) {
		return new EclipseLinkOrmBasicMapping(parent, resourceMapping);
	}
	
	public OrmIdMapping buildEclipseLinkOrmIdMapping(OrmPersistentAttribute parent, XmlId resourceMapping) {
		return new EclipseLinkOrmIdMapping(parent, resourceMapping);
	}
	
	public OrmEmbeddedIdMapping buildEclipseLinkOrmEmbeddedIdMapping(OrmPersistentAttribute parent, XmlEmbeddedId resourceMapping) {
		return buildOrmEmbeddedIdMapping(parent, resourceMapping);
	}
	
	public OrmEmbeddedMapping buildEclipseLinkOrmEmbeddedMapping(OrmPersistentAttribute parent, XmlEmbedded resourceMapping) {
		return buildOrmEmbeddedMapping(parent, resourceMapping);
	}

	public OrmManyToManyMapping buildEclipseLinkOrmManyToManyMapping(OrmPersistentAttribute parent, XmlManyToMany resourceMapping) {
		return new EclipseLinkOrmManyToManyMapping(parent, resourceMapping);
	}
	
	public OrmManyToOneMapping buildEclipseLinkOrmManyToOneMapping(OrmPersistentAttribute parent, XmlManyToOne resourceMapping) {
		return new EclipseLinkOrmManyToOneMapping(parent, resourceMapping);
	}
	
	public OrmOneToManyMapping buildEclipseLinkOrmOneToManyMapping(OrmPersistentAttribute parent, XmlOneToMany resourceMapping) {
		return new EclipseLinkOrmOneToManyMapping(parent, resourceMapping);
	}
	
	public OrmOneToOneMapping buildEclipseLinkOrmOneToOneMapping(OrmPersistentAttribute parent, XmlOneToOne resourceMapping) {
		return new EclipseLinkOrmOneToOneMapping(parent, resourceMapping);
	}
	
	public OrmVersionMapping buildEclipseLinkOrmVersionMapping(OrmPersistentAttribute parent, XmlVersion resourceMapping) {
		return new EclipseLinkOrmVersionMapping(parent, resourceMapping);
	}
		
	public OrmBasicCollectionMapping buildOrmBasicCollectionMapping(OrmPersistentAttribute parent, XmlBasicCollection resourceMapping) {
		return new OrmBasicCollectionMapping(parent, resourceMapping);
	}
	
	public OrmBasicMapMapping buildOrmBasicMapMapping(OrmPersistentAttribute parent, XmlBasicMap resourceMapping) {
		return new OrmBasicMapMapping(parent, resourceMapping);
	}
	
	public OrmTransformationMapping buildOrmTransformationMapping(OrmPersistentAttribute parent, XmlTransformation resourceMapping) {
		return new OrmTransformationMapping(parent, resourceMapping);
	}
	
	public OrmVariableOneToOneMapping buildOrmVariableOneToOneMapping(OrmPersistentAttribute parent, XmlVariableOneToOne resourceMapping) {
		return new OrmVariableOneToOneMapping(parent, resourceMapping);
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
