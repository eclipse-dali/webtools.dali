/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink1_1.core.internal;

import org.eclipse.jpt.core.context.MappingFile;
import org.eclipse.jpt.core.context.java.JavaAttributeMapping;
import org.eclipse.jpt.core.context.java.JavaBasicMapping;
import org.eclipse.jpt.core.context.java.JavaEmbeddedIdMapping;
import org.eclipse.jpt.core.context.java.JavaEmbeddedMapping;
import org.eclipse.jpt.core.context.java.JavaIdMapping;
import org.eclipse.jpt.core.context.java.JavaManyToManyMapping;
import org.eclipse.jpt.core.context.java.JavaManyToOneMapping;
import org.eclipse.jpt.core.context.java.JavaOneToOneMapping;
import org.eclipse.jpt.core.context.java.JavaTransientMapping;
import org.eclipse.jpt.core.context.java.JavaVersionMapping;
import org.eclipse.jpt.core.context.orm.EntityMappings;
import org.eclipse.jpt.core.context.orm.OrmAttributeMapping;
import org.eclipse.jpt.core.context.orm.OrmBasicMapping;
import org.eclipse.jpt.core.context.orm.OrmEmbeddedIdMapping;
import org.eclipse.jpt.core.context.orm.OrmEmbeddedMapping;
import org.eclipse.jpt.core.context.orm.OrmIdMapping;
import org.eclipse.jpt.core.context.orm.OrmManyToManyMapping;
import org.eclipse.jpt.core.context.orm.OrmManyToOneMapping;
import org.eclipse.jpt.core.context.orm.OrmOneToManyMapping;
import org.eclipse.jpt.core.context.orm.OrmOneToOneMapping;
import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.core.context.orm.OrmTransientMapping;
import org.eclipse.jpt.core.context.orm.OrmTypeMapping;
import org.eclipse.jpt.core.context.orm.OrmVersionMapping;
import org.eclipse.jpt.core.context.persistence.MappingFileRef;
import org.eclipse.jpt.core.resource.orm.XmlNullAttributeMapping;
import org.eclipse.jpt.core.resource.orm.XmlTypeMapping;
import org.eclipse.jpt.core.resource.xml.JpaXmlResource;
import org.eclipse.jpt.eclipselink.core.internal.EclipseLinkJpaFactory;
import org.eclipse.jpt.eclipselink.core.internal.context.java.EclipseLinkJavaOneToManyMapping;
import org.eclipse.jpt.eclipselink.core.internal.context.java.JavaBasicCollectionMapping;
import org.eclipse.jpt.eclipselink.core.internal.context.java.JavaBasicMapMapping;
import org.eclipse.jpt.eclipselink.core.internal.context.java.JavaTransformationMapping;
import org.eclipse.jpt.eclipselink.core.internal.context.java.JavaVariableOneToOneMapping;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.EclipseLinkEntityMappings;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.OrmBasicCollectionMapping;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.OrmBasicMapMapping;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.OrmTransformationMapping;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.OrmVariableOneToOneMapping;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlBasic;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlBasicCollection;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlBasicMap;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlEmbedded;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlEmbeddedId;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlId;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlManyToMany;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlManyToOne;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlOneToMany;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlOneToOne;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlTransformation;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlVariableOneToOne;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlVersion;
import org.eclipse.jpt.eclipselink1_1.core.internal.context.orm.EclipseLink1_1EntityMappingsImpl;
import org.eclipse.jpt.eclipselink1_1.core.internal.context.orm.EclipseLink1_1OrmPersistentAttribute;
import org.eclipse.jpt.eclipselink1_1.core.internal.context.orm.EclipseLink1_1OrmPersistentType;
import org.eclipse.jpt.eclipselink1_1.core.internal.context.orm.EclipseLink1_1OrmXml;
import org.eclipse.jpt.eclipselink1_1.core.internal.context.orm.EclipseLink1_1VirtualXmlBasic;
import org.eclipse.jpt.eclipselink1_1.core.internal.context.orm.EclipseLink1_1VirtualXmlBasicCollection;
import org.eclipse.jpt.eclipselink1_1.core.internal.context.orm.EclipseLink1_1VirtualXmlBasicMap;
import org.eclipse.jpt.eclipselink1_1.core.internal.context.orm.EclipseLink1_1VirtualXmlEmbedded;
import org.eclipse.jpt.eclipselink1_1.core.internal.context.orm.EclipseLink1_1VirtualXmlEmbeddedId;
import org.eclipse.jpt.eclipselink1_1.core.internal.context.orm.EclipseLink1_1VirtualXmlId;
import org.eclipse.jpt.eclipselink1_1.core.internal.context.orm.EclipseLink1_1VirtualXmlManyToMany;
import org.eclipse.jpt.eclipselink1_1.core.internal.context.orm.EclipseLink1_1VirtualXmlManyToOne;
import org.eclipse.jpt.eclipselink1_1.core.internal.context.orm.EclipseLink1_1VirtualXmlNullAttributeMapping;
import org.eclipse.jpt.eclipselink1_1.core.internal.context.orm.EclipseLink1_1VirtualXmlOneToMany;
import org.eclipse.jpt.eclipselink1_1.core.internal.context.orm.EclipseLink1_1VirtualXmlOneToOne;
import org.eclipse.jpt.eclipselink1_1.core.internal.context.orm.EclipseLink1_1VirtualXmlTransformation;
import org.eclipse.jpt.eclipselink1_1.core.internal.context.orm.EclipseLink1_1VirtualXmlTransient;
import org.eclipse.jpt.eclipselink1_1.core.internal.context.orm.EclipseLink1_1VirtualXmlVariableOneToOne;
import org.eclipse.jpt.eclipselink1_1.core.internal.context.orm.EclipseLink1_1VirtualXmlVersion;
import org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlAttributeMapping;
import org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlEntityMappings;
import org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlTransient;

public class EclipseLink1_1JpaFactory
	extends EclipseLinkJpaFactory
{
	protected EclipseLink1_1JpaFactory() {
		super();
	}
	
	// **************** Context Nodes ******************************************
	
	@Override
	public MappingFile buildEclipseLinkMappingFile(MappingFileRef parent, JpaXmlResource resource) {
		return this.buildEclipseLinkOrmXml(parent, resource);
	}
	
	public MappingFile buildEclipseLink1_1MappingFile(MappingFileRef parent, JpaXmlResource resource) {
		return this.buildEclipseLink1_1OrmXml(parent, resource);
	}
	
	protected EclipseLink1_1OrmXml buildEclipseLink1_1OrmXml(MappingFileRef parent, JpaXmlResource resource) {
		return new EclipseLink1_1OrmXml(parent, resource);
	}
	
	
	// ********** EclipseLink-specific ORM Virtual Resource Model **********
	
	public XmlBasic buildEclipseLink1_1VirtualXmlBasic(OrmTypeMapping ormTypeMapping, JavaBasicMapping javaBasicMapping) {
		return new EclipseLink1_1VirtualXmlBasic(ormTypeMapping, javaBasicMapping);
	}
	
	public XmlId buildEclipseLink1_1VirtualXmlId(OrmTypeMapping ormTypeMapping, JavaIdMapping javaIdMapping) {
		return new EclipseLink1_1VirtualXmlId(ormTypeMapping, javaIdMapping);
	}
	
	public XmlEmbeddedId buildEclipseLink1_1VirtualXmlEmbeddedId(OrmTypeMapping ormTypeMapping, JavaEmbeddedIdMapping javaEmbeddedIdMapping) {
		return new EclipseLink1_1VirtualXmlEmbeddedId(ormTypeMapping, javaEmbeddedIdMapping);
	}
	
	public XmlEmbedded buildEclipseLink1_1VirtualXmlEmbedded(OrmTypeMapping ormTypeMapping, JavaEmbeddedMapping javaEmbeddedMapping) {
		return new EclipseLink1_1VirtualXmlEmbedded(ormTypeMapping, javaEmbeddedMapping);
	}
	
	public XmlManyToMany buildEclipseLink1_1VirtualXmlManyToMany(OrmTypeMapping ormTypeMapping, JavaManyToManyMapping javaManyToManyMapping) {
		return new EclipseLink1_1VirtualXmlManyToMany(ormTypeMapping, javaManyToManyMapping);
	}
	
	public XmlManyToOne buildEclipseLink1_1VirtualXmlManyToOne(OrmTypeMapping ormTypeMapping, JavaManyToOneMapping javaManyToOneMapping) {
		return new EclipseLink1_1VirtualXmlManyToOne(ormTypeMapping, javaManyToOneMapping);
	}
	
	public XmlOneToMany buildEclipseLink1_1VirtualXmlOneToMany(OrmTypeMapping ormTypeMapping, EclipseLinkJavaOneToManyMapping javaOneToManyMapping) {
		return new EclipseLink1_1VirtualXmlOneToMany(ormTypeMapping, javaOneToManyMapping);
	}
	
	public XmlOneToOne buildEclipseLink1_1VirtualXmlOneToOne(OrmTypeMapping ormTypeMapping, JavaOneToOneMapping javaOneToOneMapping) {
		return new EclipseLink1_1VirtualXmlOneToOne(ormTypeMapping, javaOneToOneMapping);
	}
	
	public XmlVersion buildEclipseLink1_1VirtualXmlVersion(OrmTypeMapping ormTypeMapping, JavaVersionMapping javaVersionMapping) {
		return new EclipseLink1_1VirtualXmlVersion(ormTypeMapping, javaVersionMapping);
	}
	
	public XmlBasicCollection buildEclipseLink1_1VirtualXmlBasicCollection(OrmTypeMapping ormTypeMapping, JavaBasicCollectionMapping javaBasicCollectionMapping) {
		return new EclipseLink1_1VirtualXmlBasicCollection(ormTypeMapping, javaBasicCollectionMapping);
	}
	
	public XmlBasicMap buildEclipseLink1_1VirtualXmlBasicMap(OrmTypeMapping ormTypeMapping, JavaBasicMapMapping javaBasicMapMapping) {
		return new EclipseLink1_1VirtualXmlBasicMap(ormTypeMapping, javaBasicMapMapping);
	}
	
	public XmlTransformation buildEclipseLink1_1VirtualXmlTransformation(OrmTypeMapping ormTypeMapping, JavaTransformationMapping javaTransformationMapping) {
		return new EclipseLink1_1VirtualXmlTransformation(ormTypeMapping, javaTransformationMapping);
	}
	
	public XmlVariableOneToOne buildEclipseLink1_1VirtualXmlVariableOneToOne(OrmTypeMapping ormTypeMapping, JavaVariableOneToOneMapping javaVariableOneToOneMapping) {
		return new EclipseLink1_1VirtualXmlVariableOneToOne(ormTypeMapping, javaVariableOneToOneMapping);
	}
	
	public XmlTransient buildEclipseLink1_1VirtualXmlTransient(OrmTypeMapping ormTypeMapping, JavaTransientMapping javaAttributeMapping) {
		return new EclipseLink1_1VirtualXmlTransient(ormTypeMapping, javaAttributeMapping);
	}
	
	public XmlNullAttributeMapping buildEclipseLink1_1VirtualXmlNullAttributeMapping(OrmTypeMapping ormTypeMapping, JavaAttributeMapping javaAttributeMapping) {
		return new EclipseLink1_1VirtualXmlNullAttributeMapping(ormTypeMapping, javaAttributeMapping);
	}
	
	// ********** EclipseLink1.1-specific ORM Context Model **********
	
	public EntityMappings buildEclipseLink1_1EntityMappings(EclipseLink1_1OrmXml parent, XmlEntityMappings xmlEntityMappings) {
		return new EclipseLink1_1EntityMappingsImpl(parent, xmlEntityMappings);
	}

	public OrmPersistentType buildEclipseLink1_1OrmPersistentType(EclipseLinkEntityMappings parent, XmlTypeMapping resourceMapping) {
		return new EclipseLink1_1OrmPersistentType(parent, resourceMapping);
	}

	public OrmPersistentAttribute buildEclipseLink1_1OrmPersistentAttribute(OrmPersistentType parent, OrmPersistentAttribute.Owner owner, XmlAttributeMapping resourceMapping) {
		return new EclipseLink1_1OrmPersistentAttribute(parent, owner, resourceMapping);
	}
	
	public OrmBasicMapping buildEclipseLink1_1OrmBasicMapping(OrmPersistentAttribute parent, XmlBasic resourceMapping) {
		return buildEclipseLinkOrmBasicMapping(parent, resourceMapping);
	}
	
	public OrmIdMapping buildEclipseLink1_1OrmIdMapping(OrmPersistentAttribute parent, XmlId resourceMapping) {
		return buildEclipseLinkOrmIdMapping(parent, resourceMapping);
	}
	
	public OrmEmbeddedIdMapping buildEclipseLink1_1OrmEmbeddedIdMapping(OrmPersistentAttribute parent, XmlEmbeddedId resourceMapping) {
		return buildEclipseLinkOrmEmbeddedIdMapping(parent, resourceMapping);
	}
	
	public OrmEmbeddedMapping buildEclipseLink1_1OrmEmbeddedMapping(OrmPersistentAttribute parent, XmlEmbedded resourceMapping) {
		return buildEclipseLinkOrmEmbeddedMapping(parent, resourceMapping);
	}

	public OrmManyToManyMapping buildEclipseLink1_1OrmManyToManyMapping(OrmPersistentAttribute parent, XmlManyToMany resourceMapping) {
		return buildEclipseLinkOrmManyToManyMapping(parent, resourceMapping);
	}
	
	public OrmManyToOneMapping buildEclipseLink1_1OrmManyToOneMapping(OrmPersistentAttribute parent, XmlManyToOne resourceMapping) {
		return buildEclipseLinkOrmManyToOneMapping(parent, resourceMapping);
	}
	
	public OrmOneToManyMapping buildEclipseLink1_1OrmOneToManyMapping(OrmPersistentAttribute parent, XmlOneToMany resourceMapping) {
		return buildEclipseLinkOrmOneToManyMapping(parent, resourceMapping);
	}
	
	public OrmOneToOneMapping buildEclipseLink1_1OrmOneToOneMapping(OrmPersistentAttribute parent, XmlOneToOne resourceMapping) {
		return buildEclipseLinkOrmOneToOneMapping(parent, resourceMapping);
	}
	
	public OrmVersionMapping buildEclipseLink1_1OrmVersionMapping(OrmPersistentAttribute parent, XmlVersion resourceMapping) {
		return buildEclipseLinkOrmVersionMapping(parent, resourceMapping);
	}
	
	public OrmTransientMapping buildEclipseLink1_1OrmTransientMapping(OrmPersistentAttribute parent, XmlTransient resourceMapping) {
		return buildOrmTransientMapping(parent, resourceMapping);
	}
				
	public OrmBasicCollectionMapping buildEclipseLink1_1OrmBasicCollectionMapping(OrmPersistentAttribute parent, XmlBasicCollection resourceMapping) {
		return buildOrmBasicCollectionMapping(parent, resourceMapping);
	}
	
	public OrmBasicMapMapping buildEclipseLink1_1OrmBasicMapMapping(OrmPersistentAttribute parent, XmlBasicMap resourceMapping) {
		return buildOrmBasicMapMapping(parent, resourceMapping);
	}
	
	public OrmTransformationMapping buildEclipseLink1_1OrmTransformationMapping(OrmPersistentAttribute parent, XmlTransformation resourceMapping) {
		return buildOrmTransformationMapping(parent, resourceMapping);
	}
	
	public OrmVariableOneToOneMapping buildEclipseLink1_1OrmVariableOneToOneMapping(OrmPersistentAttribute parent, XmlVariableOneToOne resourceMapping) {
		return buildOrmVariableOneToOneMapping(parent, resourceMapping);
	}
	
	public OrmAttributeMapping buildEclipseLink1_1OrmNullAttributeMapping(OrmPersistentAttribute parent, XmlNullAttributeMapping resourceMapping) {
		return buildOrmNullAttributeMapping(parent, resourceMapping);
	}

}
