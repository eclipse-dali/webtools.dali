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
import org.eclipse.jpt.jpa.core.context.AssociationOverrideContainer;
import org.eclipse.jpt.jpa.core.context.SpecifiedOverride;
import org.eclipse.jpt.jpa.core.context.OverrideRelationship;
import org.eclipse.jpt.jpa.core.context.VirtualOverride;

/**
 * Java association override container
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
public interface JavaAssociationOverrideContainer
	extends AssociationOverrideContainer, JavaOverrideContainer
{
	/**
	 * Return the specified override's relationship.
	 * This is used by the <code>orm.xml</code> association override container
	 * when it is also using the Java container's override names.
	 * @see #getOverrideNames()
	 */
	OverrideRelationship getOverrideRelationship(String overrideName);

	// covariant overrides
	ListIterable<JavaSpecifiedAssociationOverride> getSpecifiedOverrides();
	JavaSpecifiedAssociationOverride getSpecifiedOverride(int index);
	JavaSpecifiedAssociationOverride getSpecifiedOverrideNamed(String name);
	ListIterable<JavaVirtualAssociationOverride> getVirtualOverrides();
	JavaVirtualAssociationOverride convertOverrideToVirtual(SpecifiedOverride specifiedOverride);
	JavaSpecifiedAssociationOverride convertOverrideToSpecified(VirtualOverride virtualOverride);


	// ********** parent adapter **********

	interface ParentAdapter
		extends AssociationOverrideContainer.ParentAdapter, JavaOverrideContainer.ParentAdapter
	{		
		// combine interfaces
	}
}
