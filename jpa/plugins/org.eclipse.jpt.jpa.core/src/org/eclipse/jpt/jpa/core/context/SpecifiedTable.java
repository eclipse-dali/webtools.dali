/*******************************************************************************
 * Copyright (c) 2006, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.context;

import org.eclipse.jpt.common.utility.iterable.ListIterable;

/**
 * <em>Specified</em> (as in not a <em>virtual</em> join or secondary table;
 * i.e. the table can still be a <em>default</em> table that is not "specified"
 * in the Java or XML source file - see {@link #isSpecifiedInResource()}):<ul>
 * <li>table
 * <li>secondary table
 * <li>join table
 * <li>collection table
 * </ul>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 2.2
 * @since 2.0
 */
public interface SpecifiedTable
	extends Table
{
	void setSpecifiedName(String value);

	void setSpecifiedSchema(String value);

	void setSpecifiedCatalog(String value);

	ListIterable<? extends SpecifiedUniqueConstraint> getUniqueConstraints();
	SpecifiedUniqueConstraint getUniqueConstraint(int index);
	SpecifiedUniqueConstraint addUniqueConstraint();
	SpecifiedUniqueConstraint addUniqueConstraint(int index);
	void removeUniqueConstraint(int index);
	void removeUniqueConstraint(SpecifiedUniqueConstraint uniqueConstraint);
	void moveUniqueConstraint(int targetIndex, int sourceIndex);

	/**
	 * Return whether the table is specified in the
	 * (Java or XML) resource.
	 */
	boolean isSpecifiedInResource();
}
