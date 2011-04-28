/*******************************************************************************
 * Copyright (c) 2010, 2011 Oracle. All rights reserved.
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
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jpa.core.context.OverrideContainer;
import org.eclipse.jpt.jpa.core.context.Override_;
import org.eclipse.jpt.jpa.core.context.VirtualOverride;
import org.eclipse.jpt.jpa.core.context.XmlContextNode;
import org.eclipse.jpt.jpa.core.resource.orm.XmlOverride;

/**
 * <code>orm.xml</code> attribute or association override container
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
public interface OrmOverrideContainer
	extends OverrideContainer, XmlContextNode
{
	// we need this covariant override because there is no override *container*
	// element in the orm.xml (there is just a list of overrides)
	XmlContextNode getParent();
	ListIterator<? extends OrmReadOnlyOverride> overrides();
	OrmReadOnlyOverride getOverrideNamed(String name);
	ListIterator<? extends OrmOverride> specifiedOverrides();
	OrmOverride getSpecifiedOverride(int index);
	OrmOverride getSpecifiedOverrideNamed(String name);
	ListIterator<? extends OrmVirtualOverride> virtualOverrides();
	OrmVirtualOverride convertOverrideToVirtual(Override_ specifiedOverride);
	OrmOverride convertOverrideToSpecified(VirtualOverride virtualOverride);


	interface Owner
		extends OverrideContainer.Owner
	{		
		<T extends XmlOverride> EList<T> getXmlOverrides();

		OrmTypeMapping getTypeMapping();
		
		TextRange getValidationTextRange();
	}
}
