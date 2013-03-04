/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa2.context.orm;

import org.eclipse.jpt.jpa.core.context.JpaContextModel;
import org.eclipse.jpt.jpa.core.context.Orderable;
import org.eclipse.jpt.jpa.core.context.SpecifiedTable;
import org.eclipse.jpt.jpa.core.context.orm.OrmAttributeMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmEmbeddable;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.jpa.core.context.orm.OrmSequenceGenerator;
import org.eclipse.jpt.jpa.core.context.orm.OrmSpecifiedPersistentAttribute;
import org.eclipse.jpt.jpa.core.internal.context.orm.AbstractOrmXmlContextModelFactory;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.orm.GenericOrmOrderable;
import org.eclipse.jpt.jpa.core.jpa2.context.Cacheable2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.Orderable2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.OrphanRemovable2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.OrphanRemovalMapping2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.orm.OrmCacheableReference2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.orm.OrmCollectionTable2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.orm.OrmDerivedIdentity2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.orm.OrmElementCollectionMapping2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.orm.OrmOrderable2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.orm.OrmSingleRelationshipMapping2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.orm.OrmSpecifiedOrderColumn2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.orm.OrmXmlContextModelFactory2_0;
import org.eclipse.jpt.jpa.core.resource.orm.XmlElementCollection;
import org.eclipse.jpt.jpa.core.resource.orm.XmlEmbeddable;
import org.eclipse.jpt.jpa.core.resource.orm.XmlSequenceGenerator;

public class GenericOrmXmlContextModelFactory2_0
	extends AbstractOrmXmlContextModelFactory
	implements OrmXmlContextModelFactory2_0
{	
	@Override
	public Orderable buildOrmOrderable(OrmAttributeMapping parent) {
		throw new UnsupportedOperationException("use #buildOrmOrderable(OrmAttributeMapping parent, Orderable2_0.Owner owner)"); //$NON-NLS-1$
	}

	@Override
	public OrmEmbeddable buildOrmEmbeddable(OrmPersistentType parent, XmlEmbeddable resourceMapping) {
		return new GenericOrmEmbeddable2_0(parent, resourceMapping);
	}
	
	@Override
	public OrmSequenceGenerator buildOrmSequenceGenerator(JpaContextModel parent, XmlSequenceGenerator resourceSequenceGenerator) {
		return new GenericOrmSequenceGenerator2_0(parent, resourceSequenceGenerator);
	}
	
	public OrmDerivedIdentity2_0 buildOrmDerivedIdentity(OrmSingleRelationshipMapping2_0 parent) {
		return new GenericOrmDerivedIdentity2_0(parent);
	}
	
	public OrmElementCollectionMapping2_0 buildOrmElementCollectionMapping2_0(
			OrmSpecifiedPersistentAttribute parent, XmlElementCollection resourceMapping) {
		
		return new GenericOrmElementCollectionMapping2_0(parent, resourceMapping);
	}
	
	public Cacheable2_0 buildOrmCacheable(OrmCacheableReference2_0 parent) {
		return new GenericOrmCacheable2_0(parent);
	}
	
	public OrphanRemovable2_0 buildOrmOrphanRemoval(OrphanRemovalMapping2_0 parent) {
		return new GenericOrmOrphanRemoval2_0(parent);
	}

	public OrmCollectionTable2_0 buildOrmCollectionTable(OrmElementCollectionMapping2_0 parent, SpecifiedTable.Owner owner) {
		return new GenericOrmCollectionTable2_0(parent, owner);
	}
	
	public OrmOrderable2_0 buildOrmOrderable(Orderable2_0.ParentAdapter<OrmAttributeMapping> parentAdapter) {
		return new GenericOrmOrderable(parentAdapter);
	}
	
	public OrmSpecifiedOrderColumn2_0 buildOrmOrderColumn(OrmSpecifiedOrderColumn2_0.ParentAdapter parentAdapter) {
		return new GenericOrmOrderColumn2_0(parentAdapter);
	}
}
