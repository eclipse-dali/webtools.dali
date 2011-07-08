/*******************************************************************************
 * Copyright (c) 2009, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.context.orm;

import java.util.ListIterator;
import org.eclipse.emf.common.util.EList;
import org.eclipse.jpt.jpa.core.context.AttributeOverrideContainer;
import org.eclipse.jpt.jpa.core.context.Override_;
import org.eclipse.jpt.jpa.core.context.VirtualOverride;
import org.eclipse.jpt.jpa.core.resource.orm.XmlAttributeOverride;

/**
 * <code>orm.xml</code> attribute override container
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
public interface OrmAttributeOverrideContainer
	extends AttributeOverrideContainer, OrmOverrideContainer
{
	void initializeFrom(OrmAttributeOverrideContainer oldContainer);

	// covariant overrides
	ListIterator<OrmReadOnlyAttributeOverride> overrides();
	OrmReadOnlyAttributeOverride getOverrideNamed(String name);
	ListIterator<OrmAttributeOverride> specifiedOverrides();
	OrmAttributeOverride getSpecifiedOverride(int index);
	OrmAttributeOverride getSpecifiedOverrideNamed(String name);
	ListIterator<OrmVirtualAttributeOverride> virtualOverrides();
	OrmVirtualAttributeOverride convertOverrideToVirtual(Override_ specifiedOverride);
	OrmAttributeOverride convertOverrideToSpecified(VirtualOverride virtualOverride);


	// ********** owner **********

	interface Owner
		extends AttributeOverrideContainer.Owner, OrmOverrideContainer.Owner
	{		
		@SuppressWarnings("unchecked")
		EList<XmlAttributeOverride> getXmlOverrides();
	}
}
