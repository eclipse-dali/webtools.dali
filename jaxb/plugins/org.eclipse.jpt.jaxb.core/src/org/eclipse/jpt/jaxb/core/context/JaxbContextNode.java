/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.context;

import org.eclipse.jpt.common.core.JptResourceType;
import org.eclipse.jpt.jaxb.core.JaxbNode;

/**
 * Common protocol for JAXB objects that have a context, as opposed to
 * resource objects.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.0
 * @since 3.0
 */
public interface JaxbContextNode
		extends JaxbNode {
	
	/**
	 * Return the root of the context model
	 */
	JaxbContextRoot getContextRoot();
	
	/**
	 * Return the resource type of the context node's resource.
	 */
	JptResourceType getResourceType();


	// ********** updating **********

	void synchronizeWithResourceModel();

	/**
	 * Update the context model with the content of the JAXB resource model.
	 */
	void update();

}
