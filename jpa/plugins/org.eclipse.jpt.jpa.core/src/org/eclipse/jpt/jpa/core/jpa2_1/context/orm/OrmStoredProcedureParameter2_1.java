/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.jpa2_1.context.orm;

import org.eclipse.jpt.jpa.core.jpa2_1.context.StoredProcedureParameter2_1;
import org.eclipse.jpt.jpa.core.jpa2_1.context.java.JavaStoredProcedureParameter2_1;
import org.eclipse.jpt.jpa.core.resource.orm.XmlStoredProcedureParameter;

/**
 * JPA 2.1
 * <code>orm.xml</code> stored procedure parameter
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.3
 * @since 3.3
 */
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
