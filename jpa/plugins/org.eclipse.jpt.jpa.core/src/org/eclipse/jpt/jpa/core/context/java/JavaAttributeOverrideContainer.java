/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.context.java;

import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.jpa.core.context.AttributeOverrideContainer;
import org.eclipse.jpt.jpa.core.context.SpecifiedOverride;
import org.eclipse.jpt.jpa.core.context.Column;
import org.eclipse.jpt.jpa.core.context.VirtualOverride;

/**
 * Java attribute override container
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
public interface JavaAttributeOverrideContainer
	extends AttributeOverrideContainer, JavaOverrideContainer
{
	/**
	 * Return the specified override's column.
	 * This is used by the <code>orm.xml</code> attribute override container
	 * when it is also using the Java container's override names.
	 * @see #getOverrideNames()
	 */
	Column getOverrideColumn(String overrideName);

	// covariant overrides
	ListIterable<JavaSpecifiedAttributeOverride> getSpecifiedOverrides();
	JavaSpecifiedAttributeOverride getSpecifiedOverride(int index);
	JavaSpecifiedAttributeOverride getSpecifiedOverrideNamed(String name);
	ListIterable<JavaVirtualAttributeOverride> getVirtualOverrides();
	JavaVirtualAttributeOverride convertOverrideToVirtual(SpecifiedOverride specifiedOverride);
	JavaSpecifiedAttributeOverride convertOverrideToSpecified(VirtualOverride virtualOverride);


	// ********** parent adapter **********

	interface ParentAdapter
		extends AttributeOverrideContainer.ParentAdapter, JavaOverrideContainer.ParentAdapter
	{		
		// combine two interfaces
	}
}
