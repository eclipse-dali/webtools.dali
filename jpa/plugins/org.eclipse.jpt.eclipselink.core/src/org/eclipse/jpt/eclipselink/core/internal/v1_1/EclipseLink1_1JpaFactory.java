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
import org.eclipse.jpt.eclipselink.core.internal.EclipseLinkJpaFactory;
import org.eclipse.jpt.eclipselink.core.internal.context.java.EclipseLinkJavaOneToManyMapping;
import org.eclipse.jpt.eclipselink.core.internal.context.java.EclipseLinkJavaBasicCollectionMapping;
import org.eclipse.jpt.eclipselink.core.internal.context.java.EclipseLinkJavaBasicMapMapping;
import org.eclipse.jpt.eclipselink.core.internal.context.java.EclipseLinkJavaTransformationMapping;
import org.eclipse.jpt.eclipselink.core.internal.context.java.EclipseLinkJavaVariableOneToOneMapping;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.EclipseLinkEntityMappings;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.EclipseLinkOrmBasicCollectionMapping;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.EclipseLinkOrmBasicMapMapping;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.EclipseLinkOrmTransformationMapping;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.EclipseLinkOrmVariableOneToOneMapping;
import org.eclipse.jpt.eclipselink.core.internal.v1_1.context.orm.EclipseLinkEntityMappings1_1;
import org.eclipse.jpt.eclipselink.core.internal.v1_1.context.orm.EclipseLinkOrmPersistentAttribute1_1;
import org.eclipse.jpt.eclipselink.core.internal.v1_1.context.orm.EclipseLinkOrmPersistentType1_1;
import org.eclipse.jpt.eclipselink.core.internal.v1_1.context.orm.EclipseLinkOrmXml1_1;
import org.eclipse.jpt.eclipselink.core.internal.v1_1.context.orm.EclipseLinkVirtualXmlBasic1_1;
import org.eclipse.jpt.eclipselink.core.internal.v1_1.context.orm.EclipseLinkVirtualXmlBasicCollection1_1;
import org.eclipse.jpt.eclipselink.core.internal.v1_1.context.orm.EclipseLinkVirtualXmlBasicMap1_1;
import org.eclipse.jpt.eclipselink.core.internal.v1_1.context.orm.EclipseLinkVirtualXmlEmbedded1_1;
import org.eclipse.jpt.eclipselink.core.internal.v1_1.context.orm.EclipseLinkVirtualXmlEmbeddedId1_1;
import org.eclipse.jpt.eclipselink.core.internal.v1_1.context.orm.EclipseLinkVirtualXmlId1_1;
import org.eclipse.jpt.eclipselink.core.internal.v1_1.context.orm.EclipseLinkVirtualXmlManyToMany1_1;
import org.eclipse.jpt.eclipselink.core.internal.v1_1.context.orm.EclipseLinkVirtualXmlManyToOne1_1;
import org.eclipse.jpt.eclipselink.core.internal.v1_1.context.orm.EclipseLinkVirtualXmlNullAttributeMapping1_1;
import org.eclipse.jpt.eclipselink.core.internal.v1_1.context.orm.EclipseLinkVirtualXmlOneToMany1_1;
import org.eclipse.jpt.eclipselink.core.internal.v1_1.context.orm.EclipseLinkVirtualXmlOneToOne1_1;
import org.eclipse.jpt.eclipselink.core.internal.v1_1.context.orm.EclipseLinkVirtualXmlTransformation1_1;
import org.eclipse.jpt.eclipselink.core.internal.v1_1.context.orm.EclipseLinkVirtualXmlTransient1_1;
import org.eclipse.jpt.eclipselink.core.internal.v1_1.context.orm.EclipseLinkVirtualXmlVariableOneToOne1_1;
import org.eclipse.jpt.eclipselink.core.internal.v1_1.context.orm.EclipseLinkVirtualXmlVersion1_1;
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
	
	@Override
	public MappingFile buildEclipseLinkMappingFile(MappingFileRef parent, JpaXmlResource resource) {
		return this.buildEclipseLinkOrmXml(parent, resource);
	}
	
	public MappingFile buildEclipseLink1_1MappingFile(MappingFileRef parent, JpaXmlResource resource) {
		return this.buildEclipseLink1_1OrmXml(parent, resource);
	}
	
	protected EclipseLinkOrmXml1_1 buildEclipseLink1_1OrmXml(MappingFileRef parent, JpaXmlResource resource) {
		return new EclipseLinkOrmXml1_1(parent, resource);
	}
	
	
	// ********** EclipseLink-specific ORM Virtual Resource Model **********
	
	public XmlBasic buildEclipseLinkVirtualXmlBasic1_1(OrmTypeMapping ormTypeMapping, JavaBasicMapping javaBasicMapping) {
		return new EclipseLinkVirtualXmlBasic1_1(ormTypeMapping, javaBasicMapping);
	}
	
	public XmlId buildEclipseLinkVirtualXmlId1_1(OrmTypeMapping ormTypeMapping, JavaIdMapping javaIdMapping) {
		return new EclipseLinkVirtualXmlId1_1(ormTypeMapping, javaIdMapping);
	}
	
	public XmlEmbeddedId buildEclipseLinkVirtualXmlEmbeddedId1_1(OrmTypeMapping ormTypeMapping, JavaEmbeddedIdMapping javaEmbeddedIdMapping) {
		return new EclipseLinkVirtualXmlEmbeddedId1_1(ormTypeMapping, javaEmbeddedIdMapping);
	}
	
	public XmlEmbedded buildEclipseLinkVirtualXmlEmbedded1_1(OrmTypeMapping ormTypeMapping, JavaEmbeddedMapping javaEmbeddedMapping) {
		return new EclipseLinkVirtualXmlEmbedded1_1(ormTypeMapping, javaEmbeddedMapping);
	}
	
	public XmlManyToMany buildEclipseLinkVirtualXmlManyToMany1_1(OrmTypeMapping ormTypeMapping, JavaManyToManyMapping javaManyToManyMapping) {
		return new EclipseLinkVirtualXmlManyToMany1_1(ormTypeMapping, javaManyToManyMapping);
	}
	
	public XmlManyToOne buildEclipseLinkVirtualXmlManyToOne1_1(OrmTypeMapping ormTypeMapping, JavaManyToOneMapping javaManyToOneMapping) {
		return new EclipseLinkVirtualXmlManyToOne1_1(ormTypeMapping, javaManyToOneMapping);
	}
	
	public XmlOneToMany buildEclipseLinkVirtualXmlOneToMany1_1(OrmTypeMapping ormTypeMapping, EclipseLinkJavaOneToManyMapping javaOneToManyMapping) {
		return new EclipseLinkVirtualXmlOneToMany1_1(ormTypeMapping, javaOneToManyMapping);
	}
	
	public XmlOneToOne buildEclipseLinkVirtualXmlOneToOne1_1(OrmTypeMapping ormTypeMapping, JavaOneToOneMapping javaOneToOneMapping) {
		return new EclipseLinkVirtualXmlOneToOne1_1(ormTypeMapping, javaOneToOneMapping);
	}
	
	public XmlVersion buildEclipseLinkVirtualXmlVersion1_1(OrmTypeMapping ormTypeMapping, JavaVersionMapping javaVersionMapping) {
		return new EclipseLinkVirtualXmlVersion1_1(ormTypeMapping, javaVersionMapping);
	}
	
	public XmlBasicCollection buildEclipseLinkVirtualXmlBasicCollection1_1(OrmTypeMapping ormTypeMapping, EclipseLinkJavaBasicCollectionMapping javaBasicCollectionMapping) {
		return new EclipseLinkVirtualXmlBasicCollection1_1(ormTypeMapping, javaBasicCollectionMapping);
	}
	
	public XmlBasicMap buildEclipseLinkVirtualXmlBasicMap1_1(OrmTypeMapping ormTypeMapping, EclipseLinkJavaBasicMapMapping javaBasicMapMapping) {
		return new EclipseLinkVirtualXmlBasicMap1_1(ormTypeMapping, javaBasicMapMapping);
	}
	
	public XmlTransformation buildEclipseLinkVirtualXmlTransformation1_1(OrmTypeMapping ormTypeMapping, EclipseLinkJavaTransformationMapping javaTransformationMapping) {
		return new EclipseLinkVirtualXmlTransformation1_1(ormTypeMapping, javaTransformationMapping);
	}
	
	public XmlVariableOneToOne buildEclipseLinkVirtualXmlVariableOneToOne1_1(OrmTypeMapping ormTypeMapping, EclipseLinkJavaVariableOneToOneMapping javaVariableOneToOneMapping) {
		return new EclipseLinkVirtualXmlVariableOneToOne1_1(ormTypeMapping, javaVariableOneToOneMapping);
	}
	
	public XmlTransient buildEclipseLinkVirtualXmlTransient1_1(OrmTypeMapping ormTypeMapping, JavaTransientMapping javaAttributeMapping) {
		return new EclipseLinkVirtualXmlTransient1_1(ormTypeMapping, javaAttributeMapping);
	}
	
	public XmlNullAttributeMapping buildEclipseLinkVirtualXmlNullAttributeMapping1_1(OrmTypeMapping ormTypeMapping, JavaAttributeMapping javaAttributeMapping) {
		return new EclipseLinkVirtualXmlNullAttributeMapping1_1(ormTypeMapping, javaAttributeMapping);
	}
	
	// ********** EclipseLink1.1-specific ORM Context Model **********
	
	public EntityMappings buildEclipseLinkEntityMappings1_1(EclipseLinkOrmXml1_1 parent, XmlEntityMappings xmlEntityMappings) {
		return new EclipseLinkEntityMappings1_1(parent, xmlEntityMappings);
	}

	public OrmPersistentType buildEclipseLinkOrmPersistentType1_1(EclipseLinkEntityMappings parent, XmlTypeMapping resourceMapping) {
		return new EclipseLinkOrmPersistentType1_1(parent, resourceMapping);
	}

	public OrmPersistentAttribute buildEclipseLinkOrmPersistentAttribute1_1(OrmPersistentType parent, OrmPersistentAttribute.Owner owner, XmlAttributeMapping resourceMapping) {
		return new EclipseLinkOrmPersistentAttribute1_1(parent, owner, resourceMapping);
	}
	
	public OrmBasicMapping buildEclipseLinkOrmBasicMapping1_1(OrmPersistentAttribute parent, XmlBasic resourceMapping) {
		return buildEclipseLinkOrmBasicMapping(parent, resourceMapping);
	}
	
	public OrmIdMapping buildEclipseLinkOrmIdMapping1_1(OrmPersistentAttribute parent, XmlId resourceMapping) {
		return buildEclipseLinkOrmIdMapping(parent, resourceMapping);
	}
	
	public OrmEmbeddedIdMapping buildEclipseLinkOrmEmbeddedIdMapping1_1(OrmPersistentAttribute parent, XmlEmbeddedId resourceMapping) {
		return buildEclipseLinkOrmEmbeddedIdMapping(parent, resourceMapping);
	}
	
	public OrmEmbeddedMapping buildEclipseLinkOrmEmbeddedMapping1_1(OrmPersistentAttribute parent, XmlEmbedded resourceMapping) {
		return buildEclipseLinkOrmEmbeddedMapping(parent, resourceMapping);
	}

	public OrmManyToManyMapping buildEclipseLinkOrmManyToManyMapping1_1(OrmPersistentAttribute parent, XmlManyToMany resourceMapping) {
		return buildEclipseLinkOrmManyToManyMapping(parent, resourceMapping);
	}
	
	public OrmManyToOneMapping buildEclipseLinkOrmManyToOneMapping1_1(OrmPersistentAttribute parent, XmlManyToOne resourceMapping) {
		return buildEclipseLinkOrmManyToOneMapping(parent, resourceMapping);
	}
	
	public OrmOneToManyMapping buildEclipseLinkOrmOneToManyMapping1_1(OrmPersistentAttribute parent, XmlOneToMany resourceMapping) {
		return buildEclipseLinkOrmOneToManyMapping(parent, resourceMapping);
	}
	
	public OrmOneToOneMapping buildEclipseLinkOrmOneToOneMapping1_1(OrmPersistentAttribute parent, XmlOneToOne resourceMapping) {
		return buildEclipseLinkOrmOneToOneMapping(parent, resourceMapping);
	}
	
	public OrmVersionMapping buildEclipseLinkOrmVersionMapping1_1(OrmPersistentAttribute parent, XmlVersion resourceMapping) {
		return buildEclipseLinkOrmVersionMapping(parent, resourceMapping);
	}
	
	public OrmTransientMapping buildEclipseLinkOrmTransientMapping1_1(OrmPersistentAttribute parent, XmlTransient resourceMapping) {
		return buildOrmTransientMapping(parent, resourceMapping);
	}
				
	public EclipseLinkOrmBasicCollectionMapping buildEclipseLinkOrmBasicCollectionMapping1_1(OrmPersistentAttribute parent, XmlBasicCollection resourceMapping) {
		return buildEclipseLinkOrmBasicCollectionMapping(parent, resourceMapping);
	}
	
	public EclipseLinkOrmBasicMapMapping buildEclipseLinkOrmBasicMapMapping1_1(OrmPersistentAttribute parent, XmlBasicMap resourceMapping) {
		return buildEclipseLinkOrmBasicMapMapping(parent, resourceMapping);
	}
	
	public EclipseLinkOrmTransformationMapping buildEclipseLinkOrmTransformationMapping1_1(OrmPersistentAttribute parent, XmlTransformation resourceMapping) {
		return buildEclipseLinkOrmTransformationMapping(parent, resourceMapping);
	}
	
	public EclipseLinkOrmVariableOneToOneMapping buildEclipseLinkOrmVariableOneToOneMapping1_1(OrmPersistentAttribute parent, XmlVariableOneToOne resourceMapping) {
		return buildEclipseLinkOrmVariableOneToOneMapping(parent, resourceMapping);
	}
	
	public OrmAttributeMapping buildEclipseLinkOrmNullAttributeMapping1_1(OrmPersistentAttribute parent, XmlNullAttributeMapping resourceMapping) {
		return buildEclipseLinkOrmNullAttributeMapping(parent, resourceMapping);
	}

}
