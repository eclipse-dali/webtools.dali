/*******************************************************************************
 * Copyright (c) 2009, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.context.orm;

import org.eclipse.emf.common.util.EList;
import org.eclipse.jpt.common.utility.internal.iterables.ListIterable;
import org.eclipse.jpt.jpa.core.context.AssociationOverrideContainer;
import org.eclipse.jpt.jpa.core.context.Override_;
import org.eclipse.jpt.jpa.core.context.VirtualOverride;
import org.eclipse.jpt.jpa.core.resource.orm.XmlAssociationOverride;

/**
 * <code>orm.xml</code> association override container
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.3
 * @since 2.3
 */
public interface OrmAssociationOverrideContainer
	extends AssociationOverrideContainer, OrmOverrideContainer
{
	// covariant overrides
	ListIterable<OrmAssociationOverride> getSpecifiedOverrides();
	OrmAssociationOverride getSpecifiedOverride(int index);
	OrmAssociationOverride getSpecifiedOverrideNamed(String name);
	ListIterable<OrmVirtualAssociationOverride> getVirtualOverrides();
	OrmVirtualAssociationOverride convertOverrideToVirtual(Override_ specifiedOverride);
	OrmAssociationOverride convertOverrideToSpecified(VirtualOverride virtualOverride);


	// ********** owner **********

	interface Owner
		extends AssociationOverrideContainer.Owner, OrmOverrideContainer.Owner
	{				
		@SuppressWarnings("unchecked")
		EList<XmlAssociationOverride> getXmlOverrides();
	}
}
