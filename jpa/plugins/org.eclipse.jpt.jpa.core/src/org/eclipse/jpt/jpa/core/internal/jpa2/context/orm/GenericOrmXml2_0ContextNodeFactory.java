/*******************************************************************************
 * Copyright (c) 2009, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa2.context.orm;

import org.eclipse.jpt.jpa.core.context.Table;
import org.eclipse.jpt.jpa.core.context.XmlContextNode;
import org.eclipse.jpt.jpa.core.context.orm.OrmAssociationOverrideContainer;
import org.eclipse.jpt.jpa.core.context.orm.OrmAttributeMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmEmbeddable;
import org.eclipse.jpt.jpa.core.context.orm.OrmNamedQuery;
import org.eclipse.jpt.jpa.core.context.orm.OrmOrderable;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.jpa.core.context.orm.OrmSequenceGenerator;
import org.eclipse.jpt.jpa.core.internal.context.orm.AbstractOrmXmlContextNodeFactory;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.orm.GenericOrmAssociationOverrideContainer;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.orm.GenericOrmOrderable;
import org.eclipse.jpt.jpa.core.jpa2.context.Orderable2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.orm.OrmAssociationOverrideContainer2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.orm.OrmCacheable2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.orm.OrmCacheableHolder2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.orm.OrmCollectionTable2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.orm.OrmDerivedIdentity2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.orm.OrmElementCollectionMapping2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.orm.OrmEmbeddedMapping2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.orm.OrmOrderColumn2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.orm.OrmOrderable2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.orm.OrmOrphanRemovable2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.orm.OrmOrphanRemovalHolder2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.orm.OrmSingleRelationshipMapping2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.orm.OrmXml2_0ContextNodeFactory;
import org.eclipse.jpt.jpa.core.resource.orm.XmlElementCollection;
import org.eclipse.jpt.jpa.core.resource.orm.XmlEmbeddable;
import org.eclipse.jpt.jpa.core.resource.orm.XmlNamedQuery;
import org.eclipse.jpt.jpa.core.resource.orm.XmlSequenceGenerator;

public class GenericOrmXml2_0ContextNodeFactory
	extends AbstractOrmXmlContextNodeFactory
	implements OrmXml2_0ContextNodeFactory
{	
	@Override
	public OrmOrderable buildOrmOrderable(OrmAttributeMapping parent) {
		throw new UnsupportedOperationException("use #buildOrmOrderable(OrmAttributeMapping parent, Orderable2_0.Owner owner)"); //$NON-NLS-1$
	}

	@Override
	public OrmEmbeddable buildOrmEmbeddable(OrmPersistentType parent, XmlEmbeddable resourceMapping) {
		return new GenericOrmEmbeddable2_0(parent, resourceMapping);
	}
	
	@Override
	public OrmSequenceGenerator buildOrmSequenceGenerator(XmlContextNode parent, XmlSequenceGenerator resourceSequenceGenerator) {
		return new GenericOrmSequenceGenerator2_0(parent, resourceSequenceGenerator);
	}
	
	public OrmAssociationOverrideContainer buildOrmAssociationOverrideContainer(OrmEmbeddedMapping2_0 parent, OrmAssociationOverrideContainer2_0.Owner owner) {
		return new GenericOrmAssociationOverrideContainer(parent, owner);
	}
	
	public OrmDerivedIdentity2_0 buildOrmDerivedIdentity(OrmSingleRelationshipMapping2_0 parent) {
		return new GenericOrmDerivedIdentity2_0(parent);
	}
	
	public OrmElementCollectionMapping2_0 buildOrmElementCollectionMapping2_0(
			OrmPersistentAttribute parent, XmlElementCollection resourceMapping) {
		
		return new GenericOrmElementCollectionMapping2_0(parent, resourceMapping);
	}
	
	public OrmCacheable2_0 buildOrmCacheable(OrmCacheableHolder2_0 parent) {
		return new GenericOrmCacheable2_0(parent);
	}
	
	public OrmOrphanRemovable2_0 buildOrmOrphanRemoval(OrmOrphanRemovalHolder2_0 parent) {
		return new GenericOrmOrphanRemoval2_0(parent);
	}

	@Override
	public OrmNamedQuery buildOrmNamedQuery(XmlContextNode parent, XmlNamedQuery resourceNamedQuery) {
		return new GenericOrmNamedQuery2_0(parent, resourceNamedQuery);
	}

	public OrmCollectionTable2_0 buildOrmCollectionTable(OrmElementCollectionMapping2_0 parent, Table.Owner owner) {
		return new GenericOrmCollectionTable2_0(parent, owner);
	}
	
	public OrmOrderable2_0 buildOrmOrderable(OrmAttributeMapping parent, Orderable2_0.Owner owner) {
		return new GenericOrmOrderable(parent, owner);
	}
	
	public OrmOrderColumn2_0 buildOrmOrderColumn(OrmOrderable2_0 parent, OrmOrderColumn2_0.Owner owner) {
		return new GenericOrmOrderColumn2_0(parent, owner);
	}
}
