/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.context;

/**
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface GeneratorHolder
	extends JpaContextNode
{
	//******************** table generator *****************
	
	String TABLE_GENERATOR_PROPERTY = "tableGenerator"; //$NON-NLS-1$
	
	TableGenerator getTableGenerator();
	
	TableGenerator addTableGenerator();
	
	void removeTableGenerator();
	
	
	//******************** sequence generator *****************
	
	String SEQUENCE_GENERATOR_PROPERTY = "sequenceGenerator"; //$NON-NLS-1$
	
	SequenceGenerator getSequenceGenerator();
	
	SequenceGenerator addSequenceGenerator();
	
	void removeSequenceGenerator();
	
}
