/*******************************************************************************
 * Copyright (c) 2008, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.db;

import org.eclipse.jpt.common.utility.internal.ObjectTools;

/**
 * An empty implementation of {@link ConnectionListener}.
 * <p>
 * Provisional API: This class is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public class ConnectionAdapter
	implements ConnectionListener
{
	public void aboutToClose(ConnectionProfile profile) {
		// do nothing
	}

	public void closed(ConnectionProfile profile) {
		// do nothing
	}

	public void databaseChanged(ConnectionProfile profile, Database database) {
		// do nothing
	}

	public void modified(ConnectionProfile profile) {
		// do nothing
	}

	public boolean okToClose(ConnectionProfile profile) {
		return true;
	}

	public void opened(ConnectionProfile profile) {
		// do nothing
	}

	public void catalogChanged(ConnectionProfile profile, Catalog catalog) {
		// do nothing
	}

	public void schemaChanged(ConnectionProfile profile, Schema schema) {
		// do nothing
	}

	public void sequenceChanged(ConnectionProfile profile, Sequence sequence) {
		// do nothing
	}

	public void tableChanged(ConnectionProfile profile, Table table) {
		// do nothing
	}

	public void columnChanged(ConnectionProfile profile, Column column) {
		// do nothing
	}

	public void foreignKeyChanged(ConnectionProfile profile, ForeignKey foreignKey) {
		// do nothing
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this);
	}
}
