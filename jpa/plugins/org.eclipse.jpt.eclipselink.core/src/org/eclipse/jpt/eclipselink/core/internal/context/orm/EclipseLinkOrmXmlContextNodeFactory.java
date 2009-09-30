/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.context.orm;

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
import org.eclipse.jpt.core.context.orm.OrmBasicMapping;
import org.eclipse.jpt.core.context.orm.OrmEmbeddable;
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
import org.eclipse.jpt.core.internal.context.orm.AbstractOrmXmlContextNodeFactory;
import org.eclipse.jpt.core.resource.orm.XmlNullAttributeMapping;
import org.eclipse.jpt.eclipselink.core.internal.context.java.JavaEclipseLinkBasicCollectionMapping;
import org.eclipse.jpt.eclipselink.core.internal.context.java.JavaEclipseLinkBasicMapMapping;
import org.eclipse.jpt.eclipselink.core.internal.context.java.JavaEclipseLinkOneToManyMapping;
import org.eclipse.jpt.eclipselink.core.internal.context.java.JavaEclipseLinkTransformationMapping;
import org.eclipse.jpt.eclipselink.core.internal.context.java.JavaEclipseLinkVariableOneToOneMapping;
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

public class EclipseLinkOrmXmlContextNodeFactory extends AbstractOrmXmlContextNodeFactory
{	

	// ********** EclipseLink-specific ORM Context Model **********
	
	@Override
	public EntityMappings buildEntityMappings(OrmXml parent, org.eclipse.jpt.core.resource.orm.XmlEntityMappings xmlEntityMappings) {
		return new EclipseLinkEntityMappingsImpl(parent, (XmlEntityMappings) xmlEntityMappings);
	}
	
	@Override
	public OrmPersistentAttribute buildOrmPersistentAttribute(OrmPersistentType parent, OrmPersistentAttribute.Owner owner, org.eclipse.jpt.core.resource.orm.XmlAttributeMapping resourceMapping) {
		return new OrmEclipseLinkPersistentAttribute(parent, owner, resourceMapping);
	}

	@Override
	public OrmEmbeddable buildOrmEmbeddable(OrmPersistentType type, org.eclipse.jpt.core.resource.orm.XmlEmbeddable resourceMapping) {
		return new OrmEclipseLinkEmbeddableImpl(type, (XmlEmbeddable) resourceMapping);
	}

	@Override
	public OrmEntity buildOrmEntity(OrmPersistentType type, org.eclipse.jpt.core.resource.orm.XmlEntity resourceMapping) {
		return new OrmEclipseLinkEntityImpl(type, (XmlEntity) resourceMapping);
	}
	
	@Override
	public OrmMappedSuperclass buildOrmMappedSuperclass(OrmPersistentType type, org.eclipse.jpt.core.resource.orm.XmlMappedSuperclass resourceMapping) {
		return new OrmEclipseLinkMappedSuperclassImpl(type, (XmlMappedSuperclass) resourceMapping);
	}
	
	@Override
	public OrmBasicMapping buildOrmBasicMapping(OrmPersistentAttribute parent, org.eclipse.jpt.core.resource.orm.XmlBasic resourceMapping) {
		return new OrmEclipseLinkBasicMapping(parent, (XmlBasic) resourceMapping);
	}
	
	@Override
	public OrmIdMapping buildOrmIdMapping(OrmPersistentAttribute parent, org.eclipse.jpt.core.resource.orm.XmlId resourceMapping) {
		return new OrmEclipseLinkIdMapping(parent, (XmlId) resourceMapping);
	}
	
	@Override
	public OrmManyToManyMapping buildOrmManyToManyMapping(OrmPersistentAttribute parent, org.eclipse.jpt.core.resource.orm.XmlManyToMany resourceMapping) {
		return new OrmEclipseLinkManyToManyMapping(parent, (XmlManyToMany) resourceMapping);
	}
	
	@Override
	public OrmManyToOneMapping buildOrmManyToOneMapping(OrmPersistentAttribute parent, org.eclipse.jpt.core.resource.orm.XmlManyToOne resourceMapping) {
		return new OrmEclipseLinkManyToOneMapping(parent, (XmlManyToOne) resourceMapping);
	}
	
	@Override
	public OrmOneToManyMapping buildOrmOneToManyMapping(OrmPersistentAttribute parent, org.eclipse.jpt.core.resource.orm.XmlOneToMany resourceMapping) {
		return new OrmEclipseLinkOneToManyMapping(parent, (XmlOneToMany) resourceMapping);
	}
	
	@Override
	public OrmOneToOneMapping buildOrmOneToOneMapping(OrmPersistentAttribute parent, org.eclipse.jpt.core.resource.orm.XmlOneToOne resourceMapping) {
		return new OrmEclipseLinkOneToOneMapping(parent, (XmlOneToOne) resourceMapping);
	}
	
	@Override
	public OrmVersionMapping buildOrmVersionMapping(OrmPersistentAttribute parent, org.eclipse.jpt.core.resource.orm.XmlVersion resourceMapping) {
		return new OrmEclipseLinkVersionMapping(parent, (XmlVersion) resourceMapping);
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
	
	
	// ********** EclipseLink-specific ORM Virtual Resource Model **********
	

	@Override
	public XmlBasic buildVirtualXmlBasic(OrmTypeMapping ormTypeMapping, JavaBasicMapping javaBasicMapping) {
		return new VirtualEclipseLinkXmlBasic(ormTypeMapping, javaBasicMapping);
	}
	
	@Override
	public XmlId buildVirtualXmlId(OrmTypeMapping ormTypeMapping, JavaIdMapping javaIdMapping) {
		return new VirtualEclipseLinkXmlId(ormTypeMapping, javaIdMapping);
	}
	
	@Override
	public XmlEmbeddedId buildVirtualXmlEmbeddedId(OrmTypeMapping ormTypeMapping, JavaEmbeddedIdMapping javaEmbeddedIdMapping) {
		return new VirtualEclipseLinkXmlEmbeddedId(ormTypeMapping, javaEmbeddedIdMapping);
	}
	
	@Override
	public XmlEmbedded buildVirtualXmlEmbedded(OrmTypeMapping ormTypeMapping, JavaEmbeddedMapping javaEmbeddedMapping) {
		return new VirtualEclipseLinkXmlEmbedded(ormTypeMapping, javaEmbeddedMapping);
	}
	
	@Override
	public XmlManyToMany buildVirtualXmlManyToMany(OrmTypeMapping ormTypeMapping, JavaManyToManyMapping javaManyToManyMapping) {
		return new VirtualEclipseLinkXmlManyToMany(ormTypeMapping, javaManyToManyMapping);
	}
	
	@Override
	public XmlManyToOne buildVirtualXmlManyToOne(OrmTypeMapping ormTypeMapping, JavaManyToOneMapping javaManyToOneMapping) {
		return new VirtualEclipseLinkXmlManyToOne(ormTypeMapping, javaManyToOneMapping);
	}
	
	@Override
	public XmlOneToMany buildVirtualXmlOneToMany(OrmTypeMapping ormTypeMapping, JavaOneToManyMapping javaOneToManyMapping) {
		return new VirtualEclipseLinkXmlOneToMany(ormTypeMapping, (JavaEclipseLinkOneToManyMapping) javaOneToManyMapping);
	}
	
	@Override
	public XmlOneToOne buildVirtualXmlOneToOne(OrmTypeMapping ormTypeMapping, JavaOneToOneMapping javaOneToOneMapping) {
		return new VirtualEclipseLinkXmlOneToOne(ormTypeMapping, javaOneToOneMapping);
	}
	
	@Override
	public XmlVersion buildVirtualXmlVersion(OrmTypeMapping ormTypeMapping, JavaVersionMapping javaVersionMapping) {
		return new VirtualEclipseLinkXmlVersion(ormTypeMapping, javaVersionMapping);
	}
	
	@Override
	public XmlTransient buildVirtualXmlTransient(OrmTypeMapping ormTypeMapping, JavaTransientMapping javaTransientMapping) {
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
	
	@Override
	public XmlNullAttributeMapping buildVirtualXmlNullAttributeMapping(OrmTypeMapping ormTypeMapping, JavaAttributeMapping javaAttributeMapping) {
		return new VirtualEclipseLinkXmlNullAttributeMapping(ormTypeMapping, javaAttributeMapping);
	}
}
