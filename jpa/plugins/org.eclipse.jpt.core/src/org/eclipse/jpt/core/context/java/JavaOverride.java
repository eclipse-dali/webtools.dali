/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.context.java;

import org.eclipse.jpt.core.context.BaseOverride;
import org.eclipse.jpt.core.resource.java.OverrideAnnotation;

/**
 * 
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 2.3
 * @since 2.3
 */
public interface JavaOverride
	extends BaseOverride, JavaJpaContextNode
{

	OverrideAnnotation getOverrideAnnotation();
	
	interface Owner extends BaseOverride.Owner
	{

		/**
		 * Return a prefix (ending in '.') that is allowed to be appended to the override name.
		 * Return null if no prefix is supported.
		 */
		String getPossiblePrefix();
	}
}