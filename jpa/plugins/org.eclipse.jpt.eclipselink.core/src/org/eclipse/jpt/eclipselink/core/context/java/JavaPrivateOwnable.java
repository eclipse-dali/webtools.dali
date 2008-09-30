/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.context.java;

import org.eclipse.jpt.core.context.java.JavaJpaContextNode;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.eclipselink.core.context.PrivateOwnable;

/**
 * 
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface JavaPrivateOwnable extends JavaJpaContextNode, PrivateOwnable
{
	
	/**
	 * Initialize the JavaPrivateOwnable context model object to match the PrivateOwnedAnnotation 
	 * resource model object. This should be called immediately after object creation.
	 */
	void initialize(JavaResourcePersistentAttribute jrpa);
	
	/**
	 * Update the JavaPrivateOwnable context model object to match the PrivateOwnedAnnotation 
	 * resource model object. see {@link org.eclipse.jpt.core.JpaProject#update()}
	 */
	void update(JavaResourcePersistentAttribute jrpa);	

}
