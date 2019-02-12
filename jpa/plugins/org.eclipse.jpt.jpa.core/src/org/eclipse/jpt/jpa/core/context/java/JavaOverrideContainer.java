/*******************************************************************************
 * Copyright (c) 2010, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.context.java;

import org.eclipse.jpt.common.core.resource.java.JavaResourceMember;
import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.jpa.core.context.OverrideContainer;
import org.eclipse.jpt.jpa.core.context.SpecifiedOverride;
import org.eclipse.jpt.jpa.core.context.VirtualOverride;

/**
 * Java attribute or association override container
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
public interface JavaOverrideContainer
	extends OverrideContainer
{
	/**
	 * Return the names of all the container's overrides, specified and virtual.
	 * This is used by the <code>orm.xml</code> override container so any
	 * invalid Java overrides (i.e. overrides for a non-existent or
	 * non-overridable attributes) are included among the <code>orm.xml</code>
	 * virtual overrides.
	 * @see org.eclipse.jpt.jpa.core.internal.context.orm.AbstractOrmEntity.OverrideContainerParentAdapter#getJavaOverrideNames()
	 */
	Iterable<String> getOverrideNames();

	// covariant overrides
	ListIterable<? extends JavaSpecifiedOverride> getSpecifiedOverrides();
	JavaSpecifiedOverride getSpecifiedOverride(int index);
	JavaSpecifiedOverride getSpecifiedOverrideNamed(String name);
	ListIterable<? extends JavaVirtualOverride> getVirtualOverrides();
	JavaVirtualOverride convertOverrideToVirtual(SpecifiedOverride specifiedOverride);
	JavaSpecifiedOverride convertOverrideToSpecified(VirtualOverride virtualOverride);


	// ********** parent adapter **********

	interface ParentAdapter
		extends OverrideContainer.ParentAdapter
	{
		JavaResourceMember getResourceMember();
	}
}
