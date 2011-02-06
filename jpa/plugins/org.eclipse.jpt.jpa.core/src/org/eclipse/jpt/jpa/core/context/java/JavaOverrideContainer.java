/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.context.java;

import java.util.ListIterator;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jpa.core.context.OverrideContainer;
import org.eclipse.jpt.jpa.core.context.Override_;
import org.eclipse.jpt.jpa.core.context.VirtualOverride;
import org.eclipse.jpt.jpa.core.resource.java.JavaResourcePersistentMember;

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
	ListIterator<? extends JavaReadOnlyOverride> overrides();
	JavaReadOnlyOverride getOverrideNamed(String name);
	ListIterator<? extends JavaOverride> specifiedOverrides();
	JavaOverride getSpecifiedOverride(int index);
	JavaOverride getSpecifiedOverrideNamed(String name);
	ListIterator<? extends JavaVirtualOverride> virtualOverrides();
	JavaVirtualOverride convertOverrideToVirtual(Override_ specifiedOverride);
	JavaOverride convertOverrideToSpecified(VirtualOverride virtualOverride);

	/**
	 * JPA 2.0
	 * <p>
	 * Return a prefix (<em>without</em> the following <code>'.'</code>)
	 * that may be prepended to the override name.
	 * Return <code>null</code> if no prefix is supported.
	 */
	String getPossiblePrefix();
	String getWritePrefix();


	// ********** owner **********

	interface Owner
		extends OverrideContainer.Owner
	{
		JavaResourcePersistentMember getResourcePersistentMember();

		TextRange getValidationTextRange(CompilationUnit astRoot);

		/**
		 * JPA 2.0
		 * <p>
		 * Return the prefix (<em>without</em> the following <code>'.'</code>)
		 * to be prepended to the override name.
		 */
		String getWritePrefix();

		/**
		 * JPA 2.0
		 * <p>
		 * Return a prefix (<em>without</em> the following <code>'.'</code>)
		 * that may be prepended to the override name.
		 * Return <code>null</code> if no prefix is supported.
		 * <p>
		 * JPA 2.0 supports the prefixes <code>"map"</code> and <code>"key"</code>.
		 */
		String getPossiblePrefix();

		/**
		 * JPA 2.0
		 * <p>
		 * This is necessary for JPA 2.0 because an override annotation for an
		 * element collection can have a name with a prefix that indicates
		 * whether the override applies to element collection's embedded key or
		 * value. Return whether the specified override name, which may have a
		 * prefix, is relevant to the override container.
		 * <p>
		 * JPA 2.0 supports the prefixes <code>"map"</code> and <code>"key"</code>.
		 */
		boolean isRelevant(String overrideName);
	}
}
