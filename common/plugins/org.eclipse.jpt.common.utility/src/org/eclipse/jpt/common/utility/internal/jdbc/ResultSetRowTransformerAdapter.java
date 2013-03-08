/*******************************************************************************
 * Copyright (c) 2005, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
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
public class ResultSetRowTransformerAdapter<E>
	implements ResultSetRowTransformer<E>
{
	public E transform(ResultSet resultSet) throws SQLException {
		return null;
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this);
	}
}
