/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core;

import java.util.List;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

/**
 * 
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface ContextModel
{
	/**
	 * Update the context model with the content of the JPA project
	 */
	void update(IProgressMonitor monitor);
	
	// ********** validation **********
	
	/**
	 * All subclass implementations {@link #addToMessages(List<IMessage>)} 
	 * should be preceded by a "super" call to this method
	 */
	public void addToMessages(List<IMessage> messages);
}