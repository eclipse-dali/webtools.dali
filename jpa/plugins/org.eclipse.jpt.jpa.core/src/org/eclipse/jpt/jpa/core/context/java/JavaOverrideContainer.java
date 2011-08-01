/*******************************************************************************
 * Copyright (c) 2010, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.context.java;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.resource.java.JavaResourceMember;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.iterables.ListIterable;
import org.eclipse.jpt.jpa.core.context.OverrideContainer;
import org.eclipse.jpt.jpa.core.context.Override_;
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
 * @version 2.3
 * @since 2.3
 */
public interface JavaOverrideContainer
	extends OverrideContainer, JavaJpaContextNode
{
	/**
	 * Return the names of all the container's overrides, specified and virtual.
	 * This is used by the <code>orm.xml</code> override container so any
	 * invalid Java overrides (i.e. overrides for a non-existent or
	 * non-overridable attributes) are included among the <code>orm.xml</code>
	 * virtual overrides.
	 * @see org.eclipse.jpt.jpa.core.internal.context.orm.AbstractOrmEntity.OverrideContainerOwner#getJavaOverrideNames()
	 */
	Iterable<String> getOverrideNames();

	// covariant overrides
	ListIterable<? extends JavaReadOnlyOverride> getOverrides();
	JavaReadOnlyOverride getOverrideNamed(String name);
	ListIterable<? extends JavaOverride> getSpecifiedOverrides();
	JavaOverride getSpecifiedOverride(int index);
	JavaOverride getSpecifiedOverrideNamed(String name);
	ListIterable<? extends JavaVirtualOverride> getVirtualOverrides();
	JavaVirtualOverride convertOverrideToVirtual(Override_ specifiedOverride);
	JavaOverride convertOverrideToSpecified(VirtualOverride virtualOverride);


	// ********** owner **********

	interface Owner
		extends OverrideContainer.Owner
	{
		JavaResourceMember getResourceMember();

		TextRange getValidationTextRange(CompilationUnit astRoot);
	}
}
