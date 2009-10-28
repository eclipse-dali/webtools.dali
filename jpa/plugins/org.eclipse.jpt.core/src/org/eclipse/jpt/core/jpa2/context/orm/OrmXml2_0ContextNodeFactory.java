/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.jpa2.context.orm;

import org.eclipse.jpt.core.context.orm.OrmAssociationOverrideContainer;
import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.context.orm.OrmTypeMapping;
import org.eclipse.jpt.core.context.orm.OrmXmlContextNodeFactory;
import org.eclipse.jpt.core.jpa2.context.java.JavaElementCollectionMapping2_0;
import org.eclipse.jpt.core.resource.orm.XmlAssociationOverrideContainer;
import org.eclipse.jpt.core.resource.orm.XmlElementCollection;
import org.eclipse.jpt.core.resource.orm.v2_0.XmlCacheable2_0;
import org.eclipse.jpt.core.resource.orm.v2_0.XmlDerivedId_2_0;
import org.eclipse.jpt.core.resource.orm.v2_0.XmlMapsId_2_0;

public interface OrmXml2_0ContextNodeFactory extends OrmXmlContextNodeFactory
{
	// ********** ORM Context Model **********
	
	OrmAssociationOverrideContainer buildOrmAssociationOverrideContainer(
			OrmEmbeddedMapping2_0 parent, 
			OrmAssociationOverrideContainer.Owner owner, 
			XmlAssociationOverrideContainer resourceAssociationOverrideContainer);
	
	OrmDerivedId2_0 buildOrmDerivedId(
			OrmSingleRelationshipMapping2_0 parent, XmlDerivedId_2_0 resource);
	
	OrmElementCollectionMapping2_0 buildOrmElementCollectionMapping2_0(
			OrmPersistentAttribute parent, XmlElementCollection resourceMapping);
	
	OrmMapsId2_0 buildOrmMapsId(
			OrmSingleRelationshipMapping2_0 parent, XmlMapsId_2_0 resource);
	
	XmlElementCollection buildVirtualXmlElementCollection2_0(
			OrmTypeMapping ormTypeMapping, JavaElementCollectionMapping2_0 javaMapping);
	
	OrmCacheable2_0 buildOrmCacheable(OrmCacheableHolder2_0 parent, XmlCacheable2_0 resource);
}
