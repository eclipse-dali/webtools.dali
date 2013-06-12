/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.context.orm;

import org.eclipse.emf.common.util.EList;
import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.jpa.core.context.AttributeOverrideContainer;
import org.eclipse.jpt.jpa.core.context.SpecifiedOverride;
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
 * @version 3.3
 * @since 2.3
 */
public interface OrmAttributeOverrideContainer
	extends AttributeOverrideContainer, OrmOverrideContainer
{
	/**
	 * Called when converting an embedded mapping to an
	 * embedded ID mapping (and vice-versa);
	 * to preserve any specified attribute overrides.
	 * <p>
	 * <strong>NB:</strong>
	 * There is no corresponding method on the Java container because
	 * Java mapping conversions simply change the mapping annotation and
	 * leave the [sibling] attribute override annotations in place;
	 * while <code>orm.xml</code> mapping conversions must move
	 * the [nested] attribute override XML elements to the new mapping
	 * XML element.
	 * <p>
	 * <strong>NB:</strong>
	 * There is no corresponding method on the association override container
	 * because embedded ID mappings do not have <em>association</em> overrides;
	 * so there is no need to convert those.
	 */
	void initializeFrom(OrmAttributeOverrideContainer oldContainer);

	// covariant overrides
	ListIterable<OrmSpecifiedAttributeOverride> getSpecifiedOverrides();
	OrmSpecifiedAttributeOverride getSpecifiedOverride(int index);
	OrmSpecifiedAttributeOverride getSpecifiedOverrideNamed(String name);
	ListIterable<OrmVirtualAttributeOverride> getVirtualOverrides();
	OrmVirtualAttributeOverride convertOverrideToVirtual(SpecifiedOverride specifiedOverride);
	OrmSpecifiedAttributeOverride convertOverrideToSpecified(VirtualOverride virtualOverride);


	// ********** parent adapter **********

	interface ParentAdapter
		extends AttributeOverrideContainer.ParentAdapter, OrmOverrideContainer.ParentAdapter
	{		
		@SuppressWarnings("unchecked")
		EList<XmlAttributeOverride> getXmlOverrides();
	}
}
