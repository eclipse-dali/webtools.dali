/*******************************************************************************
 * Copyright (c) 2010, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.context;

import java.util.List;

import org.eclipse.jpt.common.core.JptResourceTypeReference;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jaxb.core.JaxbNode;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

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
 * @version 3.3
 * @since 3.0
 */
public interface JaxbContextNode
		extends JaxbNode, JptResourceTypeReference {
	
	/**
	 * Return the root of the context model
	 */
	JaxbContextRoot getContextRoot();
	

	// ********** updating **********

	void synchronizeWithResourceModel();

	/**
	 * Update the context model with the content of the JAXB resource model.
	 */
	void update();

	
	
	// ******************** validation ****************************************
	
	/**
	 * Adds to the list of current validation messages
	 */
	void validate(List<IMessage> messages, IReporter reporter);
	
	/**
	 * Return the text range for highlighting errors for this object
	 */
	TextRange getValidationTextRange();

}
