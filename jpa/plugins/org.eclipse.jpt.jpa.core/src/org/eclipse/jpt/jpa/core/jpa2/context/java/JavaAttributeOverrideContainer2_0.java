/*******************************************************************************
 * Copyright (c) 2011, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.jpa2.context.java;

import org.eclipse.jpt.jpa.core.context.java.JavaAttributeOverrideContainer;

/**
 * JPA 2.0
 * Java attribute override container
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface JavaAttributeOverrideContainer2_0
	extends JavaAttributeOverrideContainer, JavaOverrideContainer2_0
{
	// combine interfaces


	// ********** parent adapter **********

	interface ParentAdapter
		extends JavaAttributeOverrideContainer.ParentAdapter, JavaOverrideContainer2_0.ParentAdapter
	{		
		// combine interfaces
	}
}
