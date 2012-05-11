/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.jpa2.context.orm;

import org.eclipse.jpt.core.context.Orderable;
import org.eclipse.jpt.core.context.orm.OrmAssociationOverrideContainer;
import org.eclipse.jpt.core.context.orm.OrmAttributeMapping;
import org.eclipse.jpt.core.context.orm.OrmNamedColumn;
import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.context.orm.OrmTypeMapping;
import org.eclipse.jpt.core.context.orm.OrmXmlContextNodeFactory;
import org.eclipse.jpt.core.jpa2.context.java.JavaElementCollectionMapping2_0;
import org.eclipse.jpt.core.resource.orm.XmlCollectionTable;
import org.eclipse.jpt.core.resource.orm.XmlElementCollection;
import org.eclipse.jpt.core.resource.orm.v2_0.XmlCacheable_2_0;
import org.eclipse.jpt.core.resource.orm.v2_0.XmlOrphanRemovable_2_0;
import org.eclipse.jpt.core.resource.orm.v2_0.XmlSingleRelationshipMapping_2_0;

/**
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 2.3
 * @since 2.3
 */
public interface OrmXml2_0ContextNodeFactory
	extends OrmXmlContextNodeFactory
{
	// ********** ORM Context Model **********
	
	//overloaded because the 2.0 JPA spec supports association overrides on an embedded mapping while the 1.0 spec did not
	OrmAssociationOverrideContainer buildOrmAssociationOverrideContainer(
			OrmEmbeddedMapping2_0 parent, 
			OrmAssociationOverrideContainer.Owner owner);
	
	OrmDerivedIdentity2_0 buildOrmDerivedIdentity(
			OrmSingleRelationshipMapping2_0 parent, XmlSingleRelationshipMapping_2_0 resource);
	
	OrmElementCollectionMapping2_0 buildOrmElementCollectionMapping2_0(
			OrmPersistentAttribute parent, XmlElementCollection resourceMapping);
	
	XmlElementCollection buildVirtualXmlElementCollection2_0(
			OrmTypeMapping ormTypeMapping, JavaElementCollectionMapping2_0 javaMapping);
	
	OrmCacheable2_0 buildOrmCacheable(OrmCacheableHolder2_0 parent, XmlCacheable_2_0 resource);
	
	OrmOrphanRemovable2_0 buildOrmOrphanRemoval(OrmOrphanRemovalHolder2_0 parent, XmlOrphanRemovable_2_0 resource);
	
	OrmOrderable2_0 buildOrmOrderable(OrmAttributeMapping parent, Orderable.Owner owner);
	
	OrmOrderColumn2_0 buildOrmOrderColumn(OrmOrderable2_0 parent, OrmNamedColumn.Owner owner);
	
	OrmCollectionTable2_0 buildOrmCollectionTable(OrmElementCollectionMapping2_0 parent, XmlCollectionTable resource);
}
