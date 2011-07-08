/*******************************************************************************
 * Copyright (c) 2009, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.context.java;

import java.util.ListIterator;
import org.eclipse.jpt.jpa.core.context.AttributeOverrideContainer;
import org.eclipse.jpt.jpa.core.context.Override_;
import org.eclipse.jpt.jpa.core.context.ReadOnlyColumn;
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
	ReadOnlyColumn getOverrideColumn(String overrideName);

	// covariant overrides
	ListIterator<JavaReadOnlyAttributeOverride> overrides();
	JavaReadOnlyAttributeOverride getOverrideNamed(String name);
	ListIterator<JavaAttributeOverride> specifiedOverrides();
	JavaAttributeOverride getSpecifiedOverride(int index);
	JavaAttributeOverride getSpecifiedOverrideNamed(String name);
	ListIterator<JavaVirtualAttributeOverride> virtualOverrides();
	JavaVirtualAttributeOverride convertOverrideToVirtual(Override_ specifiedOverride);
	JavaAttributeOverride convertOverrideToSpecified(VirtualOverride virtualOverride);


	// ********** Java owner **********

	interface Owner
		extends AttributeOverrideContainer.Owner, JavaOverrideContainer.Owner
	{		
		// combine two interfaces
	}
}
