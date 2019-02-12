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

import org.eclipse.jpt.jpa.core.context.SpecifiedAssociationOverride;
import org.eclipse.jpt.jpa.core.resource.java.AssociationOverrideAnnotation;

/**
 * <em>Specified</em> Java association override
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
public interface JavaSpecifiedAssociationOverride
	extends SpecifiedAssociationOverride, JavaSpecifiedOverride
{
	/**
	 * Called when a default override is converted into a specified override.
	 * @see org.eclipse.jpt.jpa.core.context.AssociationOverrideContainer
	 * AssociationOverrideContainer for a list of clients
	 */
	void initializeFrom(JavaVirtualAssociationOverride virtualOverride);

	JavaVirtualAssociationOverride convertToVirtual();

	AssociationOverrideAnnotation getOverrideAnnotation();

	JavaSpecifiedOverrideRelationship getRelationship();
}
