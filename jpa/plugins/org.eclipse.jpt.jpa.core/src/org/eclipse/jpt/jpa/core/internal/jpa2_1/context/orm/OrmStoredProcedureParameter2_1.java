/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa2_1.context.orm;

import org.eclipse.jpt.jpa.core.internal.jpa2_1.context.StoredProcedureParameter2_1;
import org.eclipse.jpt.jpa.core.internal.jpa2_1.context.java.JavaStoredProcedureParameter2_1;
import org.eclipse.jpt.jpa.core.resource.orm.XmlStoredProcedureParameter;

public interface OrmStoredProcedureParameter2_1
	extends StoredProcedureParameter2_1
{
	XmlStoredProcedureParameter getXmlStoredProcedureParameter();

	// ****** metadata conversion ****
	/**
	 * Build up a mapping file stored procedure parameter
	 * from the given Java stored procedure parameter
	 */
	void convertFrom(JavaStoredProcedureParameter2_1 javaStoredProcedureParameter);

}
