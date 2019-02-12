/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.context;

/**
 * Container for a table generator and/or sequence generator.
 * Used by entities and ID mappings.
 * <p>
 * <strong>NB:</strong> The <code>orm.xml</code> entity mappings element can
 * hold more than a single sequence or table generator, so it does not use this
 * container.
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
public interface GeneratorContainer
	extends JpaContextModel
{
	/**
	 * Return the container's sequence and table generators.
	 */
	Iterable<Generator> getGenerators();


	// ********** sequence generator **********

	String SEQUENCE_GENERATOR_PROPERTY = "sequenceGenerator"; //$NON-NLS-1$
	
	SequenceGenerator getSequenceGenerator();
	
	SequenceGenerator addSequenceGenerator();
	
	void removeSequenceGenerator();
	

	// ********** table generator **********
	
	String TABLE_GENERATOR_PROPERTY = "tableGenerator"; //$NON-NLS-1$
	
	TableGenerator getTableGenerator();
	
	TableGenerator addTableGenerator();
	
	void removeTableGenerator();
}
