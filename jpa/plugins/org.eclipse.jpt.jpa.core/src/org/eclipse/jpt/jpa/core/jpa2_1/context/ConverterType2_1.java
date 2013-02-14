/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.jpa2_1.context;

import org.eclipse.jpt.jpa.core.context.ManagedType;

/**
 * Context converter type.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.3
 * @since 3.3
 */
public interface ConverterType2_1
	extends ManagedType
{

	boolean isAutoApply();
	String AUTO_APPLY_PROPERTY = "autoApply"; //$NON-NLS-1$

	boolean isDefaultAutoApply();
		boolean DEFAULT_AUTO_APPLY = false;

	Boolean getSpecifiedAutoApply();
	void setSpecifiedAutoApply(Boolean autoApply);
		String SPECIFIED_AUTO_APPLY_PROPERTY = "specifiedAutoApply"; //$NON-NLS-1$
}
