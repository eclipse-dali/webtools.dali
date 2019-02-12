/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.context.java;

import org.eclipse.jpt.jpa.core.context.SpecifiedAttributeOverride;
import org.eclipse.jpt.jpa.core.resource.java.AttributeOverrideAnnotation;

/**
 * <em>Specified</em> Java attribute override
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.3
 * @since 2.0
 */
public interface JavaSpecifiedAttributeOverride
	extends SpecifiedAttributeOverride, JavaSpecifiedOverride
{
	/**
	 * Called when a default override is converted into a specified override.
	 * @see org.eclipse.jpt.jpa.core.context.AttributeOverrideContainer
	 * AttributeOverrideContainer for a list of clients
	 */
	void initializeFrom(JavaVirtualAttributeOverride oldOverride);

	JavaVirtualAttributeOverride convertToVirtual();

	AttributeOverrideAnnotation getOverrideAnnotation();

	JavaSpecifiedColumn getColumn();
}
