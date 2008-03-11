/*******************************************************************************
 * Copyright (c) 2006, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.db;

/**
 * A ProfileListener is notified of any changes to the connection profiles.
 * 
 * @see org.eclipse.datatools.connectivity.IProfileListener
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface ConnectionProfileListener {

	/**
	 * The specified old profile has been replaced with the specified
	 * new profile. The old profile is a "null" profile when a profile is
	 * added. The new profile is a "null" profile when a profile is
	 * removed.
	 */
	public void connectionProfileReplaced(ConnectionProfile oldProfile, ConnectionProfile newProfile);

	/**
	 * The specified profile has been modified. Modification includes
	 * changes to any properties, the name, auto-connect flag, etc.
	 */
	public void connectionProfileChanged(ConnectionProfile profile);

}
