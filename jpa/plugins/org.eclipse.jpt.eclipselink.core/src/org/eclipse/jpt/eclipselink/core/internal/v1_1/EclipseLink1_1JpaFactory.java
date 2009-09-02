/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.v1_1;

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
import org.eclipse.jpt.eclipselink.core.context.orm.EclipseLinkEntityMappings;
import org.eclipse.jpt.eclipselink.core.internal.EclipseLinkJpaFactory;
import org.eclipse.jpt.eclipselink.core.internal.context.java.JavaEclipseLinkOneToManyMapping;
import org.eclipse.jpt.eclipselink.core.internal.context.java.JavaEclipseLinkBasicCollectionMapping;
import org.eclipse.jpt.eclipselink.core.internal.context.java.JavaEclipseLinkBasicMapMapping;
import org.eclipse.jpt.eclipselink.core.internal.context.java.JavaEclipseLinkTransformationMapping;
import org.eclipse.jpt.eclipselink.core.internal.context.java.JavaEclipseLinkVariableOneToOneMapping;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.OrmEclipseLinkBasicCollectionMapping;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.OrmEclipseLinkBasicMapMapping;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.OrmEclipseLinkTransformationMapping;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.OrmEclipseLinkVariableOneToOneMapping;
import org.eclipse.jpt.eclipselink.core.internal.v1_1.context.orm.EclipseLinkEntityMappings1_1;
import org.eclipse.jpt.eclipselink.core.internal.v1_1.context.orm.OrmEclipseLinkPersistentAttribute1_1;
import org.eclipse.jpt.eclipselink.core.internal.v1_1.context.orm.OrmEclipseLinkPersistentType1_1;
import org.eclipse.jpt.eclipselink.core.internal.v1_1.context.orm.EclipseLinkOrmXml1_1;
import org.eclipse.jpt.eclipselink.core.internal.v1_1.context.orm.VirtualEclipseLinkXmlBasic1_1;
import org.eclipse.jpt.eclipselink.core.internal.v1_1.context.orm.VirtualEclipseLinkXmlBasicCollection1_1;
import org.eclipse.jpt.eclipselink.core.internal.v1_1.context.orm.VirtualEclipseLinkXmlBasicMap1_1;
import org.eclipse.jpt.eclipselink.core.internal.v1_1.context.orm.VirtualEclipseLinkXmlEmbedded1_1;
import org.eclipse.jpt.eclipselink.core.internal.v1_1.context.orm.VirtualEclipseLinkXmlEmbeddedId1_1;
import org.eclipse.jpt.eclipselink.core.internal.v1_1.context.orm.VirtualEclipseLinkXmlId1_1;
import org.eclipse.jpt.eclipselink.core.internal.v1_1.context.orm.VirtualEclipseLinkXmlManyToMany1_1;
import org.eclipse.jpt.eclipselink.core.internal.v1_1.context.orm.VirtualEclipseLinkXmlManyToOne1_1;
import org.eclipse.jpt.eclipselink.core.internal.v1_1.context.orm.VirtuaEclipseLinklXmlNullAttributeMapping1_1;
import org.eclipse.jpt.eclipselink.core.internal.v1_1.context.orm.VirtualEclipseLinkXmlOneToMany1_1;
import org.eclipse.jpt.eclipselink.core.internal.v1_1.context.orm.VirtualEclipseLinkXmlOneToOne1_1;
import org.eclipse.jpt.eclipselink.core.internal.v1_1.context.orm.VirtualEclipseLinkXmlTransformation1_1;
import org.eclipse.jpt.eclipselink.core.internal.v1_1.context.orm.VirtualEclipseLinkXmlTransient1_1;
import org.eclipse.jpt.eclipselink.core.internal.v1_1.context.orm.VirtualEclipseLinkXmlVariableOneToOne1_1;
import org.eclipse.jpt.eclipselink.core.internal.v1_1.context.orm.VirtualEclipseLinkXmlVersion1_1;
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
import org.eclipse.jpt.eclipselink.core.v1_1.resource.orm.XmlAttributeMapping;
import org.eclipse.jpt.eclipselink.core.v1_1.resource.orm.XmlEntityMappings;
import org.eclipse.jpt.eclipselink.core.v1_1.resource.orm.XmlTransient;

public class EclipseLink1_1JpaFactory
	extends EclipseLinkJpaFactory
{
	protected EclipseLink1_1JpaFactory() {
		super();
	}
	
	// **************** Context Nodes ******************************************
	
	public MappingFile buildEclipseLink1_1MappingFile(MappingFileRef parent, JpaXmlResource resource) {
		return this.buildEclipseLink1_1OrmXml(parent, resource);
	}
	
	protected EclipseLinkOrmXml1_1 buildEclipseLink1_1OrmXml(MappingFileRef parent, JpaXmlResource resource) {
		return new EclipseLinkOrmXml1_1(parent, resource);
	}
	
	
	// ********** EclipseLink-specific ORM Virtual Resource Model **********
	
	public XmlBasic buildVirtualEclipseLinkXmlBasic1_1(OrmTypeMapping ormTypeMapping, JavaBasicMapping javaBasicMapping) {
		return new VirtualEclipseLinkXmlBasic1_1(ormTypeMapping, javaBasicMapping);
	}
	
	public XmlId buildVirtualEclipseLinkXmlId1_1(OrmTypeMapping ormTypeMapping, JavaIdMapping javaIdMapping) {
		return new VirtualEclipseLinkXmlId1_1(ormTypeMapping, javaIdMapping);
	}
	
	public XmlEmbeddedId buildVirtualEclipseLinkXmlEmbeddedId1_1(OrmTypeMapping ormTypeMapping, JavaEmbeddedIdMapping javaEmbeddedIdMapping) {
		return new VirtualEclipseLinkXmlEmbeddedId1_1(ormTypeMapping, javaEmbeddedIdMapping);
	}
	
	public XmlEmbedded buildVirtualEclipseLinkXmlEmbedded1_1(OrmTypeMapping ormTypeMapping, JavaEmbeddedMapping javaEmbeddedMapping) {
		return new VirtualEclipseLinkXmlEmbedded1_1(ormTypeMapping, javaEmbeddedMapping);
	}
	
	public XmlManyToMany buildVirtualEclipseLinkXmlManyToMany1_1(OrmTypeMapping ormTypeMapping, JavaManyToManyMapping javaManyToManyMapping) {
		return new VirtualEclipseLinkXmlManyToMany1_1(ormTypeMapping, javaManyToManyMapping);
	}
	
	public XmlManyToOne buildVirtualEclipseLinkXmlManyToOne1_1(OrmTypeMapping ormTypeMapping, JavaManyToOneMapping javaManyToOneMapping) {
		return new VirtualEclipseLinkXmlManyToOne1_1(ormTypeMapping, javaManyToOneMapping);
	}
	
	public XmlOneToMany buildVirtualEclipseLinkXmlOneToMany1_1(OrmTypeMapping ormTypeMapping, JavaEclipseLinkOneToManyMapping javaOneToManyMapping) {
		return new VirtualEclipseLinkXmlOneToMany1_1(ormTypeMapping, javaOneToManyMapping);
	}
	
	public XmlOneToOne buildVirtualEclipseLinkXmlOneToOne1_1(OrmTypeMapping ormTypeMapping, JavaOneToOneMapping javaOneToOneMapping) {
		return new VirtualEclipseLinkXmlOneToOne1_1(ormTypeMapping, javaOneToOneMapping);
	}
	
	public XmlVersion buildVirtualEclipseLinkXmlVersion1_1(OrmTypeMapping ormTypeMapping, JavaVersionMapping javaVersionMapping) {
		return new VirtualEclipseLinkXmlVersion1_1(ormTypeMapping, javaVersionMapping);
	}
	
	public XmlBasicCollection buildVirtualEclipseLinkXmlBasicCollection1_1(OrmTypeMapping ormTypeMapping, JavaEclipseLinkBasicCollectionMapping javaBasicCollectionMapping) {
		return new VirtualEclipseLinkXmlBasicCollection1_1(ormTypeMapping, javaBasicCollectionMapping);
	}
	
	public XmlBasicMap buildVirtualEclipseLinkXmlBasicMap1_1(OrmTypeMapping ormTypeMapping, JavaEclipseLinkBasicMapMapping javaBasicMapMapping) {
		return new VirtualEclipseLinkXmlBasicMap1_1(ormTypeMapping, javaBasicMapMapping);
	}
	
	public XmlTransformation buildVirtualEclipseLinkXmlTransformation1_1(OrmTypeMapping ormTypeMapping, JavaEclipseLinkTransformationMapping javaTransformationMapping) {
		return new VirtualEclipseLinkXmlTransformation1_1(ormTypeMapping, javaTransformationMapping);
	}
	
	public XmlVariableOneToOne buildVirtualEclipseLinkXmlVariableOneToOne1_1(OrmTypeMapping ormTypeMapping, JavaEclipseLinkVariableOneToOneMapping javaVariableOneToOneMapping) {
		return new VirtualEclipseLinkXmlVariableOneToOne1_1(ormTypeMapping, javaVariableOneToOneMapping);
	}
	
	public XmlTransient buildVirtualEclipseLinkXmlTransient1_1(OrmTypeMapping ormTypeMapping, JavaTransientMapping javaAttributeMapping) {
		return new VirtualEclipseLinkXmlTransient1_1(ormTypeMapping, javaAttributeMapping);
	}
	
	public XmlNullAttributeMapping buildVirtualEclipseLinkXmlNullAttributeMapping1_1(OrmTypeMapping ormTypeMapping, JavaAttributeMapping javaAttributeMapping) {
		return new VirtuaEclipseLinklXmlNullAttributeMapping1_1(ormTypeMapping, javaAttributeMapping);
	}
	
	// ********** EclipseLink1.1-specific ORM Context Model **********
	
	public EntityMappings buildEclipseLinkEntityMappings1_1(EclipseLinkOrmXml1_1 parent, XmlEntityMappings xmlEntityMappings) {
		return new EclipseLinkEntityMappings1_1(parent, xmlEntityMappings);
	}

	public OrmPersistentType buildOrmEclipseLinkPersistentType1_1(EclipseLinkEntityMappings parent, XmlTypeMapping resourceMapping) {
		return new OrmEclipseLinkPersistentType1_1(parent, resourceMapping);
	}

	public OrmPersistentAttribute buildOrmEclipseLinkPersistentAttribute1_1(OrmPersistentType parent, OrmPersistentAttribute.Owner owner, XmlAttributeMapping resourceMapping) {
		return new OrmEclipseLinkPersistentAttribute1_1(parent, owner, resourceMapping);
	}
	
	public OrmBasicMapping buildOrmEclipseLinkBasicMapping1_1(OrmPersistentAttribute parent, XmlBasic resourceMapping) {
		return buildOrmEclipseLinkBasicMapping(parent, resourceMapping);
	}
	
	public OrmIdMapping buildOrmEclipseLinkIdMapping1_1(OrmPersistentAttribute parent, XmlId resourceMapping) {
		return buildOrmEclipseLinkIdMapping(parent, resourceMapping);
	}
	
	public OrmEmbeddedIdMapping buildOrmEclipseLinkEmbeddedIdMapping1_1(OrmPersistentAttribute parent, XmlEmbeddedId resourceMapping) {
		return buildOrmEclipseLinkEmbeddedIdMapping(parent, resourceMapping);
	}
	
	public OrmEmbeddedMapping buildOrmEclipseLinkEmbeddedMapping1_1(OrmPersistentAttribute parent, XmlEmbedded resourceMapping) {
		return buildOrmEclipseLinkEmbeddedMapping(parent, resourceMapping);
	}

	public OrmManyToManyMapping buildOrmEclipseLinkManyToManyMapping1_1(OrmPersistentAttribute parent, XmlManyToMany resourceMapping) {
		return buildOrmEclipseLinkManyToManyMapping(parent, resourceMapping);
	}
	
	public OrmManyToOneMapping buildOrmEclipseLinkManyToOneMapping1_1(OrmPersistentAttribute parent, XmlManyToOne resourceMapping) {
		return buildOrmEclipseLinkManyToOneMapping(parent, resourceMapping);
	}
	
	public OrmOneToManyMapping buildOrmEclipseLinkOneToManyMapping1_1(OrmPersistentAttribute parent, XmlOneToMany resourceMapping) {
		return buildOrmEclipseLinkOneToManyMapping(parent, resourceMapping);
	}
	
	public OrmOneToOneMapping buildOrmEclipseLinkOneToOneMapping1_1(OrmPersistentAttribute parent, XmlOneToOne resourceMapping) {
		return buildOrmEclipseLinkOneToOneMapping(parent, resourceMapping);
	}
	
	public OrmVersionMapping buildOrmEclipseLinkVersionMapping1_1(OrmPersistentAttribute parent, XmlVersion resourceMapping) {
		return buildOrmEclipseLinkVersionMapping(parent, resourceMapping);
	}
	
	public OrmTransientMapping buildOrmEclipseLinkTransientMapping1_1(OrmPersistentAttribute parent, XmlTransient resourceMapping) {
		return buildOrmTransientMapping(parent, resourceMapping);
	}
				
	public OrmEclipseLinkBasicCollectionMapping buildOrmEclipseLinkBasicCollectionMapping1_1(OrmPersistentAttribute parent, XmlBasicCollection resourceMapping) {
		return buildOrmEclipseLinkBasicCollectionMapping(parent, resourceMapping);
	}
	
	public OrmEclipseLinkBasicMapMapping buildOrmEclipseLinkBasicMapMapping1_1(OrmPersistentAttribute parent, XmlBasicMap resourceMapping) {
		return buildOrmEclipseLinkBasicMapMapping(parent, resourceMapping);
	}
	
	public OrmEclipseLinkTransformationMapping buildOrmEclipseLinkTransformationMapping1_1(OrmPersistentAttribute parent, XmlTransformation resourceMapping) {
		return buildOrmEclipseLinkTransformationMapping(parent, resourceMapping);
	}
	
	public OrmEclipseLinkVariableOneToOneMapping buildOrmEclipseLinkVariableOneToOneMapping1_1(OrmPersistentAttribute parent, XmlVariableOneToOne resourceMapping) {
		return buildOrmEclipseLinkVariableOneToOneMapping(parent, resourceMapping);
	}
	
	public OrmAttributeMapping buildOrmEclipseLinkNullAttributeMapping1_1(OrmPersistentAttribute parent, XmlNullAttributeMapping resourceMapping) {
		return buildOrmEclipseLinkNullAttributeMapping(parent, resourceMapping);
	}

}
