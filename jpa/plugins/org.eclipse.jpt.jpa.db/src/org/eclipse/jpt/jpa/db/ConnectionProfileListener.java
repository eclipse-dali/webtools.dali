/*******************************************************************************
 * Copyright (c) 2006, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.db;

import java.util.EventListener;

/**
 * A <code>ProfileListener</code> is notified of any changes to the DTP connection profiles.
 * <p>
 * @see org.eclipse.datatools.connectivity.IProfileListener
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface ConnectionProfileListener
	extends EventListener
{
	/**
	 * The specified profile has been added.
	 */
	public void connectionProfileAdded(String name);

	/**
	 * The specified profile has been removed.
	 */
	public void connectionProfileRemoved(String name);

	/**
	 * The specified profile has been renamed.
	 */
	public void connectionProfileRenamed(String oldName, String newName);
}
