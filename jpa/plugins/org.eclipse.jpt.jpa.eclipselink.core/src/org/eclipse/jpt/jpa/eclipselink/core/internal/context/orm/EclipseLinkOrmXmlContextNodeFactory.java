/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
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
import org.eclipse.jpt.jpa.core.context.orm.OrmEntity;
import org.eclipse.jpt.jpa.core.context.orm.OrmIdMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmManyToManyMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmManyToOneMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmMappedSuperclass;
import org.eclipse.jpt.jpa.core.context.orm.OrmOneToManyMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmOneToOneMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.jpa.core.context.orm.OrmVersionMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmXml;
import org.eclipse.jpt.jpa.core.internal.context.orm.AbstractOrmXmlContextNodeFactory;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlBasic;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlBasicCollection;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlBasicMap;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlEmbeddable;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlEntity;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlEntityMappings;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlId;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlManyToMany;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlManyToOne;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlMappedSuperclass;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlOneToMany;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlOneToOne;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlTransformation;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlVariableOneToOne;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlVersion;

public class EclipseLinkOrmXmlContextNodeFactory
	extends AbstractOrmXmlContextNodeFactory
{	

	// ********** EclipseLink-specific ORM Context Model **********
	
	@Override
	public EntityMappings buildEntityMappings(OrmXml parent, org.eclipse.jpt.jpa.core.resource.orm.XmlEntityMappings xmlEntityMappings) {
		return new EclipseLinkEntityMappingsImpl(parent, (XmlEntityMappings) xmlEntityMappings);
	}
	
	@Override
	public OrmPersistentAttribute buildOrmPersistentAttribute(OrmPersistentType parent, org.eclipse.jpt.jpa.core.resource.orm.XmlAttributeMapping resourceMapping) {
		return new OrmEclipseLinkPersistentAttribute(parent, resourceMapping);
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
		return new OrmEclipseLinkMappedSuperclassImpl(type, (XmlMappedSuperclass) resourceMapping);
	}
	
	@Override
	public OrmBasicMapping buildOrmBasicMapping(OrmPersistentAttribute parent, org.eclipse.jpt.jpa.core.resource.orm.XmlBasic resourceMapping) {
		return new OrmEclipseLinkBasicMapping(parent, (XmlBasic) resourceMapping);
	}
	
	@Override
	public OrmIdMapping buildOrmIdMapping(OrmPersistentAttribute parent, org.eclipse.jpt.jpa.core.resource.orm.XmlId resourceMapping) {
		return new OrmEclipseLinkIdMapping(parent, (XmlId) resourceMapping);
	}
	
	@Override
	public OrmManyToManyMapping buildOrmManyToManyMapping(OrmPersistentAttribute parent, org.eclipse.jpt.jpa.core.resource.orm.XmlManyToMany resourceMapping) {
		return new OrmEclipseLinkManyToManyMapping(parent, (XmlManyToMany) resourceMapping);
	}
	
	@Override
	public OrmManyToOneMapping buildOrmManyToOneMapping(OrmPersistentAttribute parent, org.eclipse.jpt.jpa.core.resource.orm.XmlManyToOne resourceMapping) {
		return new OrmEclipseLinkManyToOneMapping(parent, (XmlManyToOne) resourceMapping);
	}
	
	@Override
	public OrmOneToManyMapping buildOrmOneToManyMapping(OrmPersistentAttribute parent, org.eclipse.jpt.jpa.core.resource.orm.XmlOneToMany resourceMapping) {
		return new OrmEclipseLinkOneToManyMapping(parent, (XmlOneToMany) resourceMapping);
	}
	
	@Override
	public OrmOneToOneMapping buildOrmOneToOneMapping(OrmPersistentAttribute parent, org.eclipse.jpt.jpa.core.resource.orm.XmlOneToOne resourceMapping) {
		return new OrmEclipseLinkOneToOneMapping(parent, (XmlOneToOne) resourceMapping);
	}
	
	@Override
	public OrmVersionMapping buildOrmVersionMapping(OrmPersistentAttribute parent, org.eclipse.jpt.jpa.core.resource.orm.XmlVersion resourceMapping) {
		return new OrmEclipseLinkVersionMapping(parent, (XmlVersion) resourceMapping);
	}
	
	public AbstractOrmEclipseLinkBasicCollectionMapping buildOrmEclipseLinkBasicCollectionMapping(OrmPersistentAttribute parent, XmlBasicCollection resourceMapping) {
		return new OrmEclipseLinkBasicCollectionMapping(parent, resourceMapping);
	}
	
	public AbstractOrmEclipseLinkBasicMapMapping buildOrmEclipseLinkBasicMapMapping(OrmPersistentAttribute parent, XmlBasicMap resourceMapping) {
		return new OrmEclipseLinkBasicMapMapping(parent, resourceMapping);
	}
	
	public OrmEclipseLinkTransformationMapping buildOrmEclipseLinkTransformationMapping(OrmPersistentAttribute parent, XmlTransformation resourceMapping) {
		return new OrmEclipseLinkTransformationMapping(parent, resourceMapping);
	}
	
	public OrmEclipseLinkVariableOneToOneMapping buildOrmEclipseLinkVariableOneToOneMapping(OrmPersistentAttribute parent, XmlVariableOneToOne resourceMapping) {
		return new OrmEclipseLinkVariableOneToOneMapping(parent, resourceMapping);
	}
}
