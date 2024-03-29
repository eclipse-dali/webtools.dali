/*******************************************************************************
 * Copyright (c) 2010, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.context.orm;

import org.eclipse.emf.common.util.EList;
import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.jpa.core.context.OverrideContainer;
import org.eclipse.jpt.jpa.core.context.SpecifiedOverride;
import org.eclipse.jpt.jpa.core.context.VirtualOverride;
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
 * @version 3.3
 * @since 2.3
 */
public interface OrmOverrideContainer
	extends OverrideContainer
{
	// covariant overrides
	ListIterable<? extends OrmSpecifiedOverride> getSpecifiedOverrides();
	OrmSpecifiedOverride getSpecifiedOverride(int index);
	OrmSpecifiedOverride getSpecifiedOverrideNamed(String name);
	ListIterable<? extends OrmVirtualOverride> getVirtualOverrides();
	OrmVirtualOverride convertOverrideToVirtual(SpecifiedOverride specifiedOverride);
	OrmSpecifiedOverride convertOverrideToSpecified(VirtualOverride virtualOverride);


	// ********** parent adapter **********

	interface ParentAdapter
		extends OverrideContainer.ParentAdapter
	{		
		<T extends XmlOverride> EList<T> getXmlOverrides();
		
		/**
		 * Return the names of all the corresponding Java overrides, specified and
		 * virtual. Return <code>null</code> if the Java overrides are not relevant
		 * (i.e. the parent is not an entity, the parent entity has no 
		 * corresponding Java entity, or the parent entity is <em>metadata
		 * complete</em>). Return an empty list if the Java overrides are
		 * <em>possible</em> but there are simply none.
		 * <p>
		 * <strong>NB:</strong> Unlike overrides associated with attribute
		 * mappings, the overrides associated with an <code>orm.xml</code>
		 * entity are <em>additive</em> to any specified Java overrides. An
		 * <code>orm.xml</code> override only overrides the Java override with
		 * the same name. [JPA spec 10.1.3.13-14; JPA 2.0 spec 12.2.3.14-15]
		 * 
		 * @see org.eclipse.jpt.jpa.core.context.java.JavaOverrideContainer#getOverrideNames()
		 */
		Iterable<String> getJavaOverrideNames();

		OrmTypeMapping getTypeMapping();
	}
}
