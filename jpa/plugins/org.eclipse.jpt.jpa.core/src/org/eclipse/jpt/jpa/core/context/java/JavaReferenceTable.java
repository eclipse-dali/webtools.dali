/*******************************************************************************
 * Copyright (c) 2006, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.context.java;

import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.jpa.core.context.ReferenceTable;
import org.eclipse.jpt.jpa.core.resource.java.ReferenceTableAnnotation;

/**
 * Java reference table
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 2.3
 * @since 2.3
 */
public interface JavaReferenceTable
	extends ReferenceTable, JavaTable
{
	ReferenceTableAnnotation getTableAnnotation();


	// ********** join columns **********

	ListIterable<JavaSpecifiedJoinColumn> getJoinColumns();

	ListIterable<JavaSpecifiedJoinColumn> getSpecifiedJoinColumns();
	JavaSpecifiedJoinColumn getSpecifiedJoinColumn(int index);
	JavaSpecifiedJoinColumn addSpecifiedJoinColumn();
	JavaSpecifiedJoinColumn addSpecifiedJoinColumn(int index);

	JavaSpecifiedJoinColumn getDefaultJoinColumn();
}
