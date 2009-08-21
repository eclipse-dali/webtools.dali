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
import org.eclipse.jpt.core.context.PersistentType;
import org.eclipse.jpt.core.context.java.JavaAttributeMapping;
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
import org.eclipse.jpt.core.context.java.JavaTransientMapping;
import org.eclipse.jpt.core.context.java.JavaVersionMapping;
import org.eclipse.jpt.core.context.orm.EntityMappings;
import org.eclipse.jpt.core.context.orm.OrmAttributeMapping;
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
import org.eclipse.jpt.core.context.orm.OrmTransientMapping;
import org.eclipse.jpt.core.context.orm.OrmTypeMapping;
import org.eclipse.jpt.core.context.orm.OrmVersionMapping;
import org.eclipse.jpt.core.context.orm.OrmXml;
import org.eclipse.jpt.core.context.orm.PersistenceUnitMetadata;
import org.eclipse.jpt.core.context.persistence.JarFileRef;
import org.eclipse.jpt.core.context.persistence.MappingFileRef;
import org.eclipse.jpt.core.context.persistence.Persistence;
import org.eclipse.jpt.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.core.internal.platform.AbstractJpaFactory;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.resource.orm.XmlAttributeMapping;
import org.eclipse.jpt.core.resource.orm.XmlNullAttributeMapping;
import org.eclipse.jpt.core.resource.orm.XmlTypeMapping;
import org.eclipse.jpt.core.resource.persistence.XmlJarFileRef;
import org.eclipse.jpt.core.resource.persistence.XmlPersistenceUnit;
import org.eclipse.jpt.core.resource.xml.JpaXmlResource;
import org.eclipse.jpt.eclipselink.core.EclipseLinkJpaProject;
import org.eclipse.jpt.eclipselink.core.context.java.JavaEclipseLinkEntity;
import org.eclipse.jpt.eclipselink.core.context.java.JavaEclipseLinkMappedSuperclass;
import org.eclipse.jpt.eclipselink.core.context.orm.EclipseLinkEntityMappings;
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
import org.eclipse.jpt.eclipselink.core.internal.context.orm.EclipseLinkEntityMappingsImpl;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.EclipseLinkOrmXml;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.EclipseLinkPersistenceUnitMetadata;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.OrmEclipseLinkBasicCollectionMapping;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.OrmEclipseLinkBasicMapMapping;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.OrmEclipseLinkBasicMapping;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.OrmEclipseLinkEmbeddableImpl;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.OrmEclipseLinkEntityImpl;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.OrmEclipseLinkIdMapping;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.OrmEclipseLinkManyToManyMapping;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.OrmEclipseLinkManyToOneMapping;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.OrmEclipseLinkMappedSuperclassImpl;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.OrmEclipseLinkOneToManyMapping;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.OrmEclipseLinkOneToOneMapping;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.OrmEclipseLinkPersistentAttribute;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.OrmEclipseLinkPersistentType;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.OrmEclipseLinkTransformationMapping;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.OrmEclipseLinkVariableOneToOneMapping;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.OrmEclipseLinkVersionMapping;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.VirtualEclipseLinkXmlBasic;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.VirtualEclipseLinkXmlBasicCollection;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.VirtualEclipseLinkXmlBasicMap;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.VirtualEclipseLinkXmlEmbedded;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.VirtualEclipseLinkXmlEmbeddedId;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.VirtualEclipseLinkXmlId;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.VirtualEclipseLinkXmlManyToMany;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.VirtualEclipseLinkXmlManyToOne;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.VirtualEclipseLinkXmlNullAttributeMapping;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.VirtualEclipseLinkXmlOneToMany;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.VirtualEclipseLinkXmlOneToOne;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.VirtualEclipseLinkXmlTransformation;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.VirtualEclipseLinkXmlTransient;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.VirtualEclipseLinkXmlVariableOneToOne;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.VirtualEclipseLinkXmlVersion;
import org.eclipse.jpt.eclipselink.core.internal.context.persistence.EclipseLinkJarFileRef;
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
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlTransient;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlVariableOneToOne;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlVersion;

public class EclipseLinkJpaFactory
	extends AbstractJpaFactory
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
	
	@Override
	public JarFileRef buildJarFileRef(PersistenceUnit parent, XmlJarFileRef xmlJarFileRef) {
		return new EclipseLinkJarFileRef(parent, xmlJarFileRef);
	}
	
	
	// ********** EclipseLink-specific ORM Virtual Resource Model **********
	
	public XmlBasic buildVirtualEclipseLinkXmlBasic(OrmTypeMapping ormTypeMapping, JavaBasicMapping javaBasicMapping) {
		return new VirtualEclipseLinkXmlBasic(ormTypeMapping, javaBasicMapping);
	}
	
	public XmlId buildVirtualEclipseLinkXmlId(OrmTypeMapping ormTypeMapping, JavaIdMapping javaIdMapping) {
		return new VirtualEclipseLinkXmlId(ormTypeMapping, javaIdMapping);
	}
	
	public XmlEmbeddedId buildVirtualEclipseLinkXmlEmbeddedId(OrmTypeMapping ormTypeMapping, JavaEmbeddedIdMapping javaEmbeddedIdMapping) {
		return new VirtualEclipseLinkXmlEmbeddedId(ormTypeMapping, javaEmbeddedIdMapping);
	}
	
	public XmlEmbedded buildVirtualEclipseLinkXmlEmbedded(OrmTypeMapping ormTypeMapping, JavaEmbeddedMapping javaEmbeddedMapping) {
		return new VirtualEclipseLinkXmlEmbedded(ormTypeMapping, javaEmbeddedMapping);
	}
	
	public XmlManyToMany buildVirtualEclipseLinkXmlManyToMany(OrmTypeMapping ormTypeMapping, JavaManyToManyMapping javaManyToManyMapping) {
		return new VirtualEclipseLinkXmlManyToMany(ormTypeMapping, javaManyToManyMapping);
	}
	
	public XmlManyToOne buildVirtualEclipseLinkXmlManyToOne(OrmTypeMapping ormTypeMapping, JavaManyToOneMapping javaManyToOneMapping) {
		return new VirtualEclipseLinkXmlManyToOne(ormTypeMapping, javaManyToOneMapping);
	}
	
	public XmlOneToMany buildVirtualEclipseLinkXmlOneToMany(OrmTypeMapping ormTypeMapping, JavaEclipseLinkOneToManyMapping javaOneToManyMapping) {
		return new VirtualEclipseLinkXmlOneToMany(ormTypeMapping, javaOneToManyMapping);
	}
	
	public XmlOneToOne buildVirtualEclipseLinkXmlOneToOne(OrmTypeMapping ormTypeMapping, JavaOneToOneMapping javaOneToOneMapping) {
		return new VirtualEclipseLinkXmlOneToOne(ormTypeMapping, javaOneToOneMapping);
	}
	
	public XmlVersion buildVirtualEclipseLinkXmlVersion(OrmTypeMapping ormTypeMapping, JavaVersionMapping javaVersionMapping) {
		return new VirtualEclipseLinkXmlVersion(ormTypeMapping, javaVersionMapping);
	}
	
	public XmlTransient buildVirtualEclipseLinkXmlTransient(OrmTypeMapping ormTypeMapping, JavaTransientMapping javaTransientMapping) {
		return new VirtualEclipseLinkXmlTransient(ormTypeMapping, javaTransientMapping);
	}
	
	public XmlBasicCollection buildVirtualEclipseLinkXmlBasicCollection(OrmTypeMapping ormTypeMapping, JavaEclipseLinkBasicCollectionMapping javaBasicCollectionMapping) {
		return new VirtualEclipseLinkXmlBasicCollection(ormTypeMapping, javaBasicCollectionMapping);
	}
	
	public XmlBasicMap buildVirtualEclipseLinkXmlBasicMap(OrmTypeMapping ormTypeMapping, JavaEclipseLinkBasicMapMapping javaBasicMapMapping) {
		return new VirtualEclipseLinkXmlBasicMap(ormTypeMapping, javaBasicMapMapping);
	}
	
	public XmlTransformation buildVirtualEclipseLinkXmlTransformation(OrmTypeMapping ormTypeMapping, JavaEclipseLinkTransformationMapping javaTransformationMapping) {
		return new VirtualEclipseLinkXmlTransformation(ormTypeMapping, javaTransformationMapping);
	}
	
	public XmlVariableOneToOne buildVirtualEclipseLinkXmlVariableOneToOne(OrmTypeMapping ormTypeMapping, JavaEclipseLinkVariableOneToOneMapping javaVariableOneToOneMapping) {
		return new VirtualEclipseLinkXmlVariableOneToOne(ormTypeMapping, javaVariableOneToOneMapping);
	}
	
	public XmlNullAttributeMapping buildVirtualEclipseLinkXmlNullAttributeMapping(OrmTypeMapping ormTypeMapping, JavaAttributeMapping javaAttributeMapping) {
		return new VirtualEclipseLinkXmlNullAttributeMapping(ormTypeMapping, javaAttributeMapping);
	}
	
	// ********** EclipseLink-specific ORM Context Model **********
	
	public EntityMappings buildEclipseLinkEntityMappings(OrmXml parent, XmlEntityMappings xmlEntityMappings) {
		return new EclipseLinkEntityMappingsImpl(parent, xmlEntityMappings);
	}
	
	public PersistenceUnitMetadata buildEclipseLinkPersistenceUnitMetadata(EntityMappings parent, XmlEntityMappings xmlEntityMappings) {
		return new EclipseLinkPersistenceUnitMetadata(parent, xmlEntityMappings);
	}

	public OrmPersistentType buildOrmEclipseLinkPersistentType(EclipseLinkEntityMappings parent , XmlTypeMapping resourceMapping) {
		return new OrmEclipseLinkPersistentType(parent, resourceMapping);
	}
	
	public OrmPersistentAttribute buildOrmEclipseLinkPersistentAttribute(OrmPersistentType parent, OrmPersistentAttribute.Owner owner, XmlAttributeMapping resourceMapping) {
		return new OrmEclipseLinkPersistentAttribute(parent, owner, resourceMapping);
	}

	public OrmEmbeddable buildOrmEclipseLinkEmbeddable(OrmPersistentType type, XmlEmbeddable resourceMapping) {
		return new OrmEclipseLinkEmbeddableImpl(type, resourceMapping);
	}

	public OrmEntity buildOrmEclipseLinkEntity(OrmPersistentType type, XmlEntity resourceMapping) {
		return new OrmEclipseLinkEntityImpl(type, resourceMapping);
	}
	
	public OrmMappedSuperclass buildOrmEclipseLinkMappedSuperclass(OrmPersistentType type, XmlMappedSuperclass resourceMapping) {
		return new OrmEclipseLinkMappedSuperclassImpl(type, resourceMapping);
	}
	
	public OrmBasicMapping buildOrmEclipseLinkBasicMapping(OrmPersistentAttribute parent, XmlBasic resourceMapping) {
		return new OrmEclipseLinkBasicMapping(parent, resourceMapping);
	}
	
	public OrmIdMapping buildOrmEclipseLinkIdMapping(OrmPersistentAttribute parent, XmlId resourceMapping) {
		return new OrmEclipseLinkIdMapping(parent, resourceMapping);
	}
	
	public OrmEmbeddedIdMapping buildOrmEclipseLinkEmbeddedIdMapping(OrmPersistentAttribute parent, XmlEmbeddedId resourceMapping) {
		return buildOrmEmbeddedIdMapping(parent, resourceMapping);
	}
	
	public OrmEmbeddedMapping buildOrmEclipseLinkEmbeddedMapping(OrmPersistentAttribute parent, XmlEmbedded resourceMapping) {
		return buildOrmEmbeddedMapping(parent, resourceMapping);
	}

	public OrmManyToManyMapping buildOrmEclipseLinkManyToManyMapping(OrmPersistentAttribute parent, XmlManyToMany resourceMapping) {
		return new OrmEclipseLinkManyToManyMapping(parent, resourceMapping);
	}
	
	public OrmManyToOneMapping buildOrmEclipseLinkManyToOneMapping(OrmPersistentAttribute parent, XmlManyToOne resourceMapping) {
		return new OrmEclipseLinkManyToOneMapping(parent, resourceMapping);
	}
	
	public OrmOneToManyMapping buildOrmEclipseLinkOneToManyMapping(OrmPersistentAttribute parent, XmlOneToMany resourceMapping) {
		return new OrmEclipseLinkOneToManyMapping(parent, resourceMapping);
	}
	
	public OrmOneToOneMapping buildOrmEclipseLinkOneToOneMapping(OrmPersistentAttribute parent, XmlOneToOne resourceMapping) {
		return new OrmEclipseLinkOneToOneMapping(parent, resourceMapping);
	}
	
	public OrmVersionMapping buildOrmEclipseLinkVersionMapping(OrmPersistentAttribute parent, XmlVersion resourceMapping) {
		return new OrmEclipseLinkVersionMapping(parent, resourceMapping);
	}
	
	public OrmTransientMapping buildOrmEclipseLinkTransientMapping(OrmPersistentAttribute parent, XmlTransient resourceMapping) {
		return buildOrmTransientMapping(parent, resourceMapping);
	}
	
	public OrmEclipseLinkBasicCollectionMapping buildOrmEclipseLinkBasicCollectionMapping(OrmPersistentAttribute parent, XmlBasicCollection resourceMapping) {
		return new OrmEclipseLinkBasicCollectionMapping(parent, resourceMapping);
	}
	
	public OrmEclipseLinkBasicMapMapping buildOrmEclipseLinkBasicMapMapping(OrmPersistentAttribute parent, XmlBasicMap resourceMapping) {
		return new OrmEclipseLinkBasicMapMapping(parent, resourceMapping);
	}
	
	public OrmEclipseLinkTransformationMapping buildOrmEclipseLinkTransformationMapping(OrmPersistentAttribute parent, XmlTransformation resourceMapping) {
		return new OrmEclipseLinkTransformationMapping(parent, resourceMapping);
	}
	
	public OrmEclipseLinkVariableOneToOneMapping buildOrmEclipseLinkVariableOneToOneMapping(OrmPersistentAttribute parent, XmlVariableOneToOne resourceMapping) {
		return new OrmEclipseLinkVariableOneToOneMapping(parent, resourceMapping);
	}
	
	public OrmAttributeMapping buildOrmEclipseLinkNullAttributeMapping(OrmPersistentAttribute parent, XmlNullAttributeMapping resourceMapping) {
		return buildOrmNullAttributeMapping(parent, resourceMapping);
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
