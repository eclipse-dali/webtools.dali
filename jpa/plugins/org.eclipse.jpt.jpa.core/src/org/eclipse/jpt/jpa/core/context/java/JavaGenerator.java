/*******************************************************************************
 * Copyright (c) 2006, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.context.java;

import org.eclipse.jpt.jpa.core.context.Generator;
import org.eclipse.jpt.jpa.core.context.orm.EntityMappings;
import org.eclipse.jpt.jpa.core.resource.java.GeneratorAnnotation;

/**
 * Java sequence and table generators
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.3
 * @since 2.0
 */
public interface JavaGenerator 
	extends Generator, JavaJpaContextNode
{
	GeneratorAnnotation getGeneratorAnnotation();

	//********* metadata conversion *********
	
	/**
	 * Add the appropriate mapping file generator to the specified entity
	 * mappings and convert it from this generator.
	 */
	void convertTo(EntityMappings entityMappings);

	/**
	 * Remove the generator from its parent.
	 */
	void delete();
}
