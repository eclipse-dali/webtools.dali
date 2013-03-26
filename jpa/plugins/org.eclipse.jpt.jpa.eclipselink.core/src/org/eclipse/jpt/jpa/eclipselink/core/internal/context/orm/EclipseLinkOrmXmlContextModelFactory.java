/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.context.orm;

import org.eclipse.jpt.jpa.core.context.orm.EntityMappings;
import org.eclipse.jpt.jpa.core.context.orm.OrmBasicMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmEmbeddable;
import org.eclipse.jpt.jpa.core.context.orm.OrmEmbeddedIdMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmEmbeddedMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmEntity;
import org.eclipse.jpt.jpa.core.context.orm.OrmIdMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmManyToManyMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmManyToOneMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmMappedSuperclass;
import org.eclipse.jpt.jpa.core.context.orm.OrmOneToManyMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmOneToOneMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistenceUnitDefaults;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistenceUnitMetadata;
import org.eclipse.jpt.jpa.core.context.orm.OrmSpecifiedPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.jpa.core.context.orm.OrmVersionMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmXml;
import org.eclipse.jpt.jpa.core.internal.context.orm.AbstractOrmXmlContextModelFactory;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlAttributeMapping;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlBasic;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlBasicCollection;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlBasicMap;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlEmbeddable;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlEmbedded;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlEmbeddedId;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlEntity;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlEntityMappings;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlId;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlManyToMany;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlManyToOne;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlMappedSuperclass;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlOneToMany;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlOneToOne;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlTransformation;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlTypeMapping;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlVariableOneToOne;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlVersion;

public class EclipseLinkOrmXmlContextModelFactory
	extends AbstractOrmXmlContextModelFactory
{
	// ********** EclipseLink-specific ORM Context Model **********
	
	@Override
	public EntityMappings buildEntityMappings(OrmXml parent, org.eclipse.jpt.jpa.core.resource.orm.XmlEntityMappings xmlEntityMappings) {
		return new EclipseLinkEntityMappingsImpl(parent, (XmlEntityMappings) xmlEntityMappings);
	}
	
	@Override
	public OrmSpecifiedPersistentAttribute buildOrmPersistentAttribute(OrmPersistentType parent, org.eclipse.jpt.jpa.core.resource.orm.XmlAttributeMapping resourceMapping) {
		return new EclipseLinkOrmPersistentAttribute(parent, (XmlAttributeMapping) resourceMapping);
	}

	@Override
	public OrmPersistentType buildOrmPersistentType(EntityMappings parent, org.eclipse.jpt.jpa.core.resource.orm.XmlTypeMapping resourceMapping) {
		return new EclipseLinkOrmPersistentTypeImpl(parent, (XmlTypeMapping) resourceMapping);
	}

	@Override
	public OrmEmbeddable buildOrmEmbeddable(OrmPersistentType type, org.eclipse.jpt.jpa.core.resource.orm.XmlEmbeddable resourceMapping) {
		return new OrmEclipseLinkEmbeddableImpl(type, (XmlEmbeddable) resourceMapping);
	}

	@Override
	public OrmEntity buildOrmEntity(OrmPersistentType type, org.eclipse.jpt.jpa.core.resource.orm.XmlEntity resourceMapping) {
		return new OrmEclipseLinkEntityImpl(type, (XmlEntity) resourceMapping);
	}
	
	@Override
	public OrmMappedSuperclass buildOrmMappedSuperclass(OrmPersistentType type, org.eclipse.jpt.jpa.core.resource.orm.XmlMappedSuperclass resourceMapping) {
		return new EclipseLinkOrmMappedSuperclassImpl(type, (XmlMappedSuperclass) resourceMapping);
	}
	
	@Override
	public OrmBasicMapping buildOrmBasicMapping(OrmSpecifiedPersistentAttribute parent, org.eclipse.jpt.jpa.core.resource.orm.XmlBasic resourceMapping) {
		return new OrmEclipseLinkBasicMapping(parent, (XmlBasic) resourceMapping);
	}
	
	@Override
	public OrmIdMapping buildOrmIdMapping(OrmSpecifiedPersistentAttribute parent, org.eclipse.jpt.jpa.core.resource.orm.XmlId resourceMapping) {
		return new EclipseLinkOrmIdMapping(parent, (XmlId) resourceMapping);
	}

	@Override
	public OrmEmbeddedMapping buildOrmEmbeddedMapping(OrmSpecifiedPersistentAttribute parent,  org.eclipse.jpt.jpa.core.resource.orm.XmlEmbedded resourceMapping) {
		return new OrmEclipseLinkEmbeddedMapping(parent, (XmlEmbedded) resourceMapping);
	}

	@Override
	public OrmEmbeddedIdMapping buildOrmEmbeddedIdMapping(OrmSpecifiedPersistentAttribute parent,  org.eclipse.jpt.jpa.core.resource.orm.XmlEmbeddedId resourceMapping) {
		return new OrmEclipseLinkEmbeddedIdMapping(parent, (XmlEmbeddedId) resourceMapping);
	}
	
	@Override
	public OrmManyToManyMapping buildOrmManyToManyMapping(OrmSpecifiedPersistentAttribute parent, org.eclipse.jpt.jpa.core.resource.orm.XmlManyToMany resourceMapping) {
		return new EclipseLinkOrmManyToManyMapping(parent, (XmlManyToMany) resourceMapping);
	}
	
	@Override
	public OrmManyToOneMapping buildOrmManyToOneMapping(OrmSpecifiedPersistentAttribute parent, org.eclipse.jpt.jpa.core.resource.orm.XmlManyToOne resourceMapping) {
		return new EclipseLinkOrmManyToOneMapping(parent, (XmlManyToOne) resourceMapping);
	}
	
	@Override
	public OrmOneToManyMapping buildOrmOneToManyMapping(OrmSpecifiedPersistentAttribute parent, org.eclipse.jpt.jpa.core.resource.orm.XmlOneToMany resourceMapping) {
		return new EclipseLinkOrmOneToManyMapping(parent, (XmlOneToMany) resourceMapping);
	}
	
	@Override
	public OrmOneToOneMapping buildOrmOneToOneMapping(OrmSpecifiedPersistentAttribute parent, org.eclipse.jpt.jpa.core.resource.orm.XmlOneToOne resourceMapping) {
		return new EclipseLinkOrmOneToOneMapping(parent, (XmlOneToOne) resourceMapping);
	}
	
	@Override
	public OrmVersionMapping buildOrmVersionMapping(OrmSpecifiedPersistentAttribute parent, org.eclipse.jpt.jpa.core.resource.orm.XmlVersion resourceMapping) {
		return new EclipseLinkOrmVersionMapping(parent, (XmlVersion) resourceMapping);
	}
	
	public EclipseLinkAbstractOrmBasicCollectionMapping buildOrmEclipseLinkBasicCollectionMapping(OrmSpecifiedPersistentAttribute parent, XmlBasicCollection resourceMapping) {
		return new OrmEclipseLinkBasicCollectionMapping(parent, resourceMapping);
	}
	
	public EclipseLinkAbstractOrmBasicMapMapping buildOrmEclipseLinkBasicMapMapping(OrmSpecifiedPersistentAttribute parent, XmlBasicMap resourceMapping) {
		return new OrmEclipseLinkBasicMapMapping(parent, resourceMapping);
	}
	
	public EclipseLinkOrmTransformationMapping buildOrmEclipseLinkTransformationMapping(OrmSpecifiedPersistentAttribute parent, XmlTransformation resourceMapping) {
		return new EclipseLinkOrmTransformationMapping(parent, resourceMapping);
	}
	
	public EclipseLinkOrmVariableOneToOneMapping buildOrmEclipseLinkVariableOneToOneMapping(OrmSpecifiedPersistentAttribute parent, XmlVariableOneToOne resourceMapping) {
		return new EclipseLinkOrmVariableOneToOneMapping(parent, resourceMapping);
	}

	@Override
	public OrmPersistenceUnitDefaults buildOrmPersistenceUnitDefaults(OrmPersistenceUnitMetadata parent) {
		return new EclipseLinkOrmPersistenceUnitDefaults(parent);
	}

	@Override
	public OrmPersistenceUnitMetadata buildOrmPersistenceUnitMetadata(EntityMappings parent) {
		return new EclipseLinkOrmPersistenceUnitMetadata(parent);
	}
}
