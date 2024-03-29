/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.context.java;

import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.jpa.core.context.GeneratorContainer;
import org.eclipse.jpt.jpa.core.context.JpaContextModel;

/**
 * Java generator container
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.3
 * @since 2.3
 */
public interface JavaGeneratorContainer
	extends GeneratorContainer
{
	// ********** sequence generator **********

	JavaSequenceGenerator getSequenceGenerator();

	JavaSequenceGenerator addSequenceGenerator();


	// ********** table generator **********

	JavaTableGenerator getTableGenerator();

	JavaTableGenerator addTableGenerator();


	// ********** parent **********

	/**
	 * Parent adapter
	 */
	interface Parent
		extends JpaContextModel
	{
		/**
		 * Return the element that is annotated with generators.
		 */
		JavaResourceAnnotatedElement getResourceAnnotatedElement();

		/**
		 * Return whether the container's parent supports generators.
		 * (Virtual attributes do not support generators.)
		 */
		boolean supportsGenerators();
	}
}
