/*******************************************************************************
 * Copyright (c) 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.db.internal.driver;

import org.eclipse.jpt.jpa.db.Database;

/**
 * Factory interface.
 */
interface DTPDriverAdapterFactory {
	/**
	 * Return the vendors supported by the factory's adapter.
	 */
	String[] getSupportedVendors();

	/**
	 * Build a new DTP driver adapter for the specified Dali database.
	 */
	DTPDriverAdapter buildAdapter(Database database);
}