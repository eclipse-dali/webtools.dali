/*******************************************************************************
 * Copyright (c) 2008, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.db;

/**
 * An empty implementation of {@link ConnectionProfileListener}.
 * <p>
 * Provisional API: This class is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public class ConnectionProfileAdapter
	implements ConnectionProfileListener
{
	public void connectionProfileAdded(String name) {
		// do nothing
	}

	public void connectionProfileRemoved(String name) {
		// do nothing
	}

	public void connectionProfileRenamed(String oldName, String newName) {
		// do nothing
	}
}
