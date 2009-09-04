/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.v1_1;

import org.eclipse.jpt.core.context.java.JavaAttributeMapping;
import org.eclipse.jpt.core.context.java.JavaBasicMapping;
import org.eclipse.jpt.core.context.java.JavaEmbeddedIdMapping;
import org.eclipse.jpt.core.context.java.JavaEmbeddedMapping;
import org.eclipse.jpt.core.context.java.JavaIdMapping;
import org.eclipse.jpt.core.context.java.JavaManyToManyMapping;
import org.eclipse.jpt.core.context.java.JavaManyToOneMapping;
import org.eclipse.jpt.core.context.java.JavaOneToManyMapping;
import org.eclipse.jpt.core.context.java.JavaOneToOneMapping;
import org.eclipse.jpt.core.context.java.JavaTransientMapping;
import org.eclipse.jpt.core.context.java.JavaVersionMapping;
import org.eclipse.jpt.core.context.orm.EntityMappings;
import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.core.context.orm.OrmTypeMapping;
import org.eclipse.jpt.core.context.orm.OrmXml;
import org.eclipse.jpt.core.context.persistence.MappingFileRef;
import org.eclipse.jpt.core.resource.orm.XmlNullAttributeMapping;
import org.eclipse.jpt.core.resource.orm.XmlTypeMapping;
import org.eclipse.jpt.core.resource.xml.JpaXmlResource;
import org.eclipse.jpt.eclipselink.core.context.orm.EclipseLinkEntityMappings;
import org.eclipse.jpt.eclipselink.core.internal.EclipseLinkOrmXmlContextNodeFactory;
import org.eclipse.jpt.eclipselink.core.internal.context.java.JavaEclipseLinkBasicCollectionMapping;
import org.eclipse.jpt.eclipselink.core.internal.context.java.JavaEclipseLinkBasicMapMapping;
import org.eclipse.jpt.eclipselink.core.internal.context.java.JavaEclipseLinkOneToManyMapping;
import org.eclipse.jpt.eclipselink.core.internal.context.java.JavaEclipseLinkTransformationMapping;
import org.eclipse.jpt.eclipselink.core.internal.context.java.JavaEclipseLinkVariableOneToOneMapping;
import org.eclipse.jpt.eclipselink.core.internal.v1_1.context.orm.EclipseLinkOrmXml1_1;
import org.eclipse.jpt.eclipselink.core.internal.v1_1.context.orm.OrmEclipseLinkPersistentAttribute1_1;
import org.eclipse.jpt.eclipselink.core.internal.v1_1.context.orm.OrmEclipseLinkPersistentType1_1;
import org.eclipse.jpt.eclipselink.core.internal.v1_1.context.orm.VirtuaEclipseLinklXmlNullAttributeMapping1_1;
import org.eclipse.jpt.eclipselink.core.internal.v1_1.context.orm.VirtualEclipseLinkXmlBasic1_1;
import org.eclipse.jpt.eclipselink.core.internal.v1_1.context.orm.VirtualEclipseLinkXmlBasicCollection1_1;
import org.eclipse.jpt.eclipselink.core.internal.v1_1.context.orm.VirtualEclipseLinkXmlBasicMap1_1;
import org.eclipse.jpt.eclipselink.core.internal.v1_1.context.orm.VirtualEclipseLinkXmlEmbedded1_1;
import org.eclipse.jpt.eclipselink.core.internal.v1_1.context.orm.VirtualEclipseLinkXmlEmbeddedId1_1;
import org.eclipse.jpt.eclipselink.core.internal.v1_1.context.orm.VirtualEclipseLinkXmlId1_1;
import org.eclipse.jpt.eclipselink.core.internal.v1_1.context.orm.VirtualEclipseLinkXmlManyToMany1_1;
import org.eclipse.jpt.eclipselink.core.internal.v1_1.context.orm.VirtualEclipseLinkXmlManyToOne1_1;
import org.eclipse.jpt.eclipselink.core.internal.v1_1.context.orm.VirtualEclipseLinkXmlOneToMany1_1;
import org.eclipse.jpt.eclipselink.core.internal.v1_1.context.orm.VirtualEclipseLinkXmlOneToOne1_1;
import org.eclipse.jpt.eclipselink.core.internal.v1_1.context.orm.VirtualEclipseLinkXmlTransformation1_1;
import org.eclipse.jpt.eclipselink.core.internal.v1_1.context.orm.VirtualEclipseLinkXmlTransient1_1;
import org.eclipse.jpt.eclipselink.core.internal.v1_1.context.orm.VirtualEclipseLinkXmlVariableOneToOne1_1;
import org.eclipse.jpt.eclipselink.core.internal.v1_1.context.orm.VirtualEclipseLinkXmlVersion1_1;
import org.eclipse.jpt.eclipselink.core.v1_1.resource.orm.XmlAttributeMapping;
import org.eclipse.jpt.eclipselink.core.v1_1.resource.orm.XmlBasic;
import org.eclipse.jpt.eclipselink.core.v1_1.resource.orm.XmlBasicCollection;
import org.eclipse.jpt.eclipselink.core.v1_1.resource.orm.XmlBasicMap;
import org.eclipse.jpt.eclipselink.core.v1_1.resource.orm.XmlEmbedded;
import org.eclipse.jpt.eclipselink.core.v1_1.resource.orm.XmlEmbeddedId;
import org.eclipse.jpt.eclipselink.core.v1_1.resource.orm.XmlId;
import org.eclipse.jpt.eclipselink.core.v1_1.resource.orm.XmlManyToMany;
import org.eclipse.jpt.eclipselink.core.v1_1.resource.orm.XmlManyToOne;
import org.eclipse.jpt.eclipselink.core.v1_1.resource.orm.XmlOneToMany;
import org.eclipse.jpt.eclipselink.core.v1_1.resource.orm.XmlOneToOne;
import org.eclipse.jpt.eclipselink.core.v1_1.resource.orm.XmlTransformation;
import org.eclipse.jpt.eclipselink.core.v1_1.resource.orm.XmlTransient;
import org.eclipse.jpt.eclipselink.core.v1_1.resource.orm.XmlVariableOneToOne;
import org.eclipse.jpt.eclipselink.core.v1_1.resource.orm.XmlVersion;


public class EclipseLink1_1OrmXmlContextNodeFactory extends EclipseLinkOrmXmlContextNodeFactory
{	

	
	@Override
	public OrmXml buildMappingFile(MappingFileRef parent, JpaXmlResource resource) {
		return new EclipseLinkOrmXml1_1(parent, resource);
	}
	
	// ********** EclipseLink1.1-specific ORM Context Model **********

	@Override
	public OrmPersistentType buildOrmPersistentType(EntityMappings parent, XmlTypeMapping resourceMapping) {
		return new OrmEclipseLinkPersistentType1_1((EclipseLinkEntityMappings) parent, resourceMapping);
	}

	@Override
	public OrmPersistentAttribute buildOrmPersistentAttribute(OrmPersistentType parent, OrmPersistentAttribute.Owner owner, org.eclipse.jpt.core.resource.orm.XmlAttributeMapping resourceMapping) {
		return new OrmEclipseLinkPersistentAttribute1_1(parent, owner, (XmlAttributeMapping) resourceMapping);
	}

	
	// ********** EclipseLink-specific ORM Virtual Resource Model **********
	
	@Override
	public XmlBasic buildVirtualXmlBasic(OrmTypeMapping ormTypeMapping, JavaBasicMapping javaBasicMapping) {
		return new VirtualEclipseLinkXmlBasic1_1(ormTypeMapping, javaBasicMapping);
	}
	
	@Override
	public XmlId buildVirtualXmlId(OrmTypeMapping ormTypeMapping, JavaIdMapping javaIdMapping) {
		return new VirtualEclipseLinkXmlId1_1(ormTypeMapping, javaIdMapping);
	}
	
	@Override
	public XmlEmbeddedId buildVirtualXmlEmbeddedId(OrmTypeMapping ormTypeMapping, JavaEmbeddedIdMapping javaEmbeddedIdMapping) {
		return new VirtualEclipseLinkXmlEmbeddedId1_1(ormTypeMapping, javaEmbeddedIdMapping);
	}
	
	@Override
	public XmlEmbedded buildVirtualXmlEmbedded(OrmTypeMapping ormTypeMapping, JavaEmbeddedMapping javaEmbeddedMapping) {
		return new VirtualEclipseLinkXmlEmbedded1_1(ormTypeMapping, javaEmbeddedMapping);
	}
	
	@Override
	public XmlManyToMany buildVirtualXmlManyToMany(OrmTypeMapping ormTypeMapping, JavaManyToManyMapping javaManyToManyMapping) {
		return new VirtualEclipseLinkXmlManyToMany1_1(ormTypeMapping, javaManyToManyMapping);
	}
	
	@Override
	public XmlManyToOne buildVirtualXmlManyToOne(OrmTypeMapping ormTypeMapping, JavaManyToOneMapping javaManyToOneMapping) {
		return new VirtualEclipseLinkXmlManyToOne1_1(ormTypeMapping, javaManyToOneMapping);
	}
	
	@Override
	public XmlOneToMany buildVirtualXmlOneToMany(OrmTypeMapping ormTypeMapping, JavaOneToManyMapping javaOneToManyMapping) {
		return new VirtualEclipseLinkXmlOneToMany1_1(ormTypeMapping, (JavaEclipseLinkOneToManyMapping) javaOneToManyMapping);
	}
	
	@Override
	public XmlOneToOne buildVirtualXmlOneToOne(OrmTypeMapping ormTypeMapping, JavaOneToOneMapping javaOneToOneMapping) {
		return new VirtualEclipseLinkXmlOneToOne1_1(ormTypeMapping, javaOneToOneMapping);
	}
	
	@Override
	public XmlVersion buildVirtualXmlVersion(OrmTypeMapping ormTypeMapping, JavaVersionMapping javaVersionMapping) {
		return new VirtualEclipseLinkXmlVersion1_1(ormTypeMapping, javaVersionMapping);
	}
	
	@Override
	public XmlBasicCollection buildVirtualEclipseLinkXmlBasicCollection(OrmTypeMapping ormTypeMapping, JavaEclipseLinkBasicCollectionMapping javaBasicCollectionMapping) {
		return new VirtualEclipseLinkXmlBasicCollection1_1(ormTypeMapping, javaBasicCollectionMapping);
	}
	
	@Override
	public XmlBasicMap buildVirtualEclipseLinkXmlBasicMap(OrmTypeMapping ormTypeMapping, JavaEclipseLinkBasicMapMapping javaBasicMapMapping) {
		return new VirtualEclipseLinkXmlBasicMap1_1(ormTypeMapping, javaBasicMapMapping);
	}
	
	@Override
	public XmlTransformation buildVirtualEclipseLinkXmlTransformation(OrmTypeMapping ormTypeMapping, JavaEclipseLinkTransformationMapping javaTransformationMapping) {
		return new VirtualEclipseLinkXmlTransformation1_1(ormTypeMapping, javaTransformationMapping);
	}
	
	@Override
	public XmlVariableOneToOne buildVirtualEclipseLinkXmlVariableOneToOne(OrmTypeMapping ormTypeMapping, JavaEclipseLinkVariableOneToOneMapping javaVariableOneToOneMapping) {
		return new VirtualEclipseLinkXmlVariableOneToOne1_1(ormTypeMapping, javaVariableOneToOneMapping);
	}
	
	@Override
	public XmlTransient buildVirtualXmlTransient(OrmTypeMapping ormTypeMapping, JavaTransientMapping javaAttributeMapping) {
		return new VirtualEclipseLinkXmlTransient1_1(ormTypeMapping, javaAttributeMapping);
	}
	
	@Override
	public XmlNullAttributeMapping buildVirtualXmlNullAttributeMapping(OrmTypeMapping ormTypeMapping, JavaAttributeMapping javaAttributeMapping) {
		return new VirtuaEclipseLinklXmlNullAttributeMapping1_1(ormTypeMapping, javaAttributeMapping);
	}

}
