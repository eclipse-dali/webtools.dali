/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.context.java;

import org.eclipse.jpt.core.context.JoinColumn;
import org.eclipse.jpt.core.resource.java.JoinColumnAnnotation;

/**
 * 
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface JavaJoinColumn extends JoinColumn, JavaAbstractJoinColumn, JavaAbstractColumn
{
	void initializeFromResource(JoinColumnAnnotation joinColumn);
	
	boolean connectionProfileIsActive();
	
	void update(JoinColumnAnnotation joinColumn);

	Owner owner();
	
	/**
	 * interface allowing join columns to be used in multiple places
	 * (e.g. 1:1 mappings and join tables)
	 */
	interface Owner extends JoinColumn.Owner, JavaAbstractJoinColumn.Owner, JavaAbstractColumn.Owner
	{
		// nothing yet
	}
}