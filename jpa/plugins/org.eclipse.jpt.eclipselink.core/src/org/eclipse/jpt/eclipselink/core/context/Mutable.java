/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.context;

import org.eclipse.jpt.core.context.JpaContextNode;

/**
 * 
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface Mutable extends JpaContextNode
{
	
	/**
	 * Return true if the mutable model object exists.  
	 * Have to have a separate flag for this since the default mutable type
	 * is different depending on whether hasMutable() returns true or false.
	 */
	boolean hasMutable();
	void setMutable(boolean mutable);
		String MUTABLE_PROPERTY = "mutableProperty"; //$NON-NLS-1$

	Boolean getMutable();

	Boolean getDefaultMutable();
		String DEFAULT_MUTABLE_PROPERTY = "defaultMutableProperty"; //$NON-NLS-1$
		
	Boolean getSpecifiedMutable();
	void setSpecifiedMutable(Boolean newSpecifiedMutable);
		String SPECIFIED_MUTABLE_PROPERTY = "specifiedMutableProperty"; //$NON-NLS-1$
	
}
