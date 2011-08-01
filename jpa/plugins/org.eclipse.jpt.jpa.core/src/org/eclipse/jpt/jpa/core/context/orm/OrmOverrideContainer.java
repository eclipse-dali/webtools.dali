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

import org.eclipse.emf.common.util.EList;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.iterables.ListIterable;
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
	// covariant overrides
	/**
	 * We need this covariant override because there is no override
	 * <em>container</em> element in the <code>orm.xml</code> file;
	 * there is simply a list of overrides.
	 */
	XmlContextNode getParent();
	ListIterable<? extends OrmReadOnlyOverride> getOverrides();
	OrmReadOnlyOverride getOverrideNamed(String name);
	ListIterable<? extends OrmOverride> getSpecifiedOverrides();
	OrmOverride getSpecifiedOverride(int index);
	OrmOverride getSpecifiedOverrideNamed(String name);
	ListIterable<? extends OrmVirtualOverride> getVirtualOverrides();
	OrmVirtualOverride convertOverrideToVirtual(Override_ specifiedOverride);
	OrmOverride convertOverrideToSpecified(VirtualOverride virtualOverride);


	interface Owner
		extends OverrideContainer.Owner
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

		TextRange getValidationTextRange();

		OrmTypeMapping getTypeMapping();
	}
}
