/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.context.orm;

import org.eclipse.jpt.jpa.core.context.JpaContextModel;
import org.eclipse.jpt.jpa.core.context.Orderable;
import org.eclipse.jpt.jpa.core.context.orm.OrmAttributeMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmSequenceGenerator;
import org.eclipse.jpt.jpa.core.context.orm.OrmSpecifiedPersistentAttribute;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.orm.GenericOrmOrderable;
import org.eclipse.jpt.jpa.core.internal.jpa2.context.orm.GenericOrmCacheable2_0;
import org.eclipse.jpt.jpa.core.internal.jpa2.context.orm.GenericOrmCollectionTable2_0;
import org.eclipse.jpt.jpa.core.internal.jpa2.context.orm.GenericOrmDerivedIdentity2_0;
import org.eclipse.jpt.jpa.core.internal.jpa2.context.orm.GenericOrmOrderColumn2_0;
import org.eclipse.jpt.jpa.core.internal.jpa2.context.orm.GenericOrmOrphanRemoval2_0;
import org.eclipse.jpt.jpa.core.internal.jpa2.context.orm.GenericOrmSequenceGenerator2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.Cacheable2_0;
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
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlElementCollection;

public class EclipseLinkOrmXmlContextModelFactory2_0
	extends EclipseLinkOrmXmlContextModelFactory
	implements OrmXmlContextModelFactory2_0
{	
	@Override
	public OrmSequenceGenerator buildOrmSequenceGenerator(JpaContextModel parent, org.eclipse.jpt.jpa.core.resource.orm.XmlSequenceGenerator resourceSequenceGenerator) {
		return new GenericOrmSequenceGenerator2_0(parent, resourceSequenceGenerator);
	}
	
	public OrmDerivedIdentity2_0 buildOrmDerivedIdentity(OrmSingleRelationshipMapping2_0 parent) {
		return new GenericOrmDerivedIdentity2_0(parent);
	}
	
	public OrmElementCollectionMapping2_0 buildOrmElementCollectionMapping(OrmSpecifiedPersistentAttribute parent, org.eclipse.jpt.jpa.core.resource.orm.XmlElementCollection resourceMapping) {
		return new EclipseLinkOrmElementCollectionMapping2_0(parent, (XmlElementCollection) resourceMapping);
	}
	
	public Cacheable2_0 buildOrmCacheable(OrmCacheableReference2_0 parent) {
		return new GenericOrmCacheable2_0(parent);
	}
	
	public OrphanRemovable2_0 buildOrmOrphanRemoval(OrphanRemovalMapping2_0 parent) {
		return new GenericOrmOrphanRemoval2_0(parent);
	}

	public OrmCollectionTable2_0 buildOrmCollectionTable(OrmCollectionTable2_0.ParentAdapter parentAdapter) {
		return new GenericOrmCollectionTable2_0(parentAdapter);
	}

	@Override
	public Orderable buildOrmOrderable(OrmAttributeMapping parent) {
		throw new UnsupportedOperationException("use #buildOrmOrderable(Orderable2_0.ParentAdapter)"); //$NON-NLS-1$
	}

	public OrmOrderable2_0 buildOrmOrderable(OrmOrderable2_0.ParentAdapter parentAdapter) {
		return new GenericOrmOrderable(parentAdapter);
	}
	
	public OrmSpecifiedOrderColumn2_0 buildOrmOrderColumn(OrmSpecifiedOrderColumn2_0.ParentAdapter parentAdapter) {
		return new GenericOrmOrderColumn2_0(parentAdapter);
	}
}
