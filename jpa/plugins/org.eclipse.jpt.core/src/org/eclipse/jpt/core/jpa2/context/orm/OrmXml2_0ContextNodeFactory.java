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

import org.eclipse.jpt.core.context.Table;
import org.eclipse.jpt.core.context.orm.OrmAssociationOverrideContainer;
import org.eclipse.jpt.core.context.orm.OrmAttributeMapping;
import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.context.orm.OrmXmlContextNodeFactory;
import org.eclipse.jpt.core.jpa2.context.Orderable2_0;
import org.eclipse.jpt.core.resource.orm.XmlElementCollection;

/**
 * JPA 2.0 <code>orm.xml</code> context node factory
 * <p>
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
	
	OrmAssociationOverrideContainer buildOrmAssociationOverrideContainer(OrmEmbeddedMapping2_0 parent, OrmAssociationOverrideContainer.Owner owner);
	
	OrmDerivedIdentity2_0 buildOrmDerivedIdentity(OrmSingleRelationshipMapping2_0 parent);
	
	OrmElementCollectionMapping2_0 buildOrmElementCollectionMapping2_0(OrmPersistentAttribute parent, XmlElementCollection resourceMapping);
	
	OrmCacheable2_0 buildOrmCacheable(OrmCacheableHolder2_0 parent);
	
	OrmOrphanRemovable2_0 buildOrmOrphanRemoval(OrmOrphanRemovalHolder2_0 parent);
	
	OrmOrderable2_0 buildOrmOrderable(OrmAttributeMapping parent, Orderable2_0.Owner owner);
	
	OrmOrderColumn2_0 buildOrmOrderColumn(OrmOrderable2_0 parent, OrmOrderColumn2_0.Owner owner);
	
	OrmCollectionTable2_0 buildOrmCollectionTable(OrmElementCollectionMapping2_0 parent, Table.Owner owner);
}
