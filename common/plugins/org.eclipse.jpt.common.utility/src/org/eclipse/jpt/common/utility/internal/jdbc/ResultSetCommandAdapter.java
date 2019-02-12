/*******************************************************************************
 * Copyright (c) 2005, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.eclipse.jpt.common.utility.internal.ObjectTools;

/**
 * Convenience implementation.
 */
public class ResultSetCommandAdapter
	implements ResultSetCommand
{
	public void execute(ResultSet resultSet) throws SQLException {
		// NOP
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this);
	}
}
