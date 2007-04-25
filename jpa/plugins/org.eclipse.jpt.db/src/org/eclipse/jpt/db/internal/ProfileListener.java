/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.db.internal;

/**
 * ProfileListener integrate th DTP IProfileListener listener.
 * This class purpose is to decouple from the DTP listeners by accepting wrappers as parameter.
 * 
 * @see org.eclipse.datatools.connectivity.IProfileListener
 */
public interface ProfileListener {
	/**
	 * The specified profile has been added.
	 */
	public void profileAdded( ConnectionProfile profile);

	/**
	 * The specified profile has been deleted.
	 */
	public void profileDeleted( String profileName);

	/**
	 * The specified profile has been modified.  Modification includes
	 * changes to any properties, the name, auto-connect flag, etc.
	 */
	public void profileChanged( ConnectionProfile profile);

}
