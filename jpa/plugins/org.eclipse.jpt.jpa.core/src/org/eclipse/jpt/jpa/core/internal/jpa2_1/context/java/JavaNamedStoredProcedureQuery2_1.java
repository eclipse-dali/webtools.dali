/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa2_1.context.java;

import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.jpa.core.context.java.JavaQuery;
import org.eclipse.jpt.jpa.core.jpa2_1.context.NamedStoredProcedureQuery2_1;
import org.eclipse.jpt.jpa.core.jpa2_1.resource.java.NamedStoredProcedureQuery2_1Annotation;

/**
 * Java named stored procedure query
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
public interface JavaNamedStoredProcedureQuery2_1
	extends NamedStoredProcedureQuery2_1, JavaQuery
{
	NamedStoredProcedureQuery2_1Annotation getQueryAnnotation();

	// *********** parameters ************
	
	ListIterable<JavaStoredProcedureParameter2_1> getParameters();

	JavaStoredProcedureParameter2_1 addParameter();

	JavaStoredProcedureParameter2_1 addParameter(int index);
}
