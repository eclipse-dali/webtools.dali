/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.context;

/**
 * sequence generator
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 2.2
 * @since 2.0
 */
public interface SequenceGenerator
	extends DatabaseGenerator
{
	int DEFAULT_INITIAL_VALUE = 1;

	Class<SequenceGenerator> getGeneratorType();

	// ********** sequence name **********

	/**
	 * Return the specified sequence name if present, otherwise return the
	 * default sequence name.
	 */
	String getSequenceName();
	String getSpecifiedSequenceName();
	void setSpecifiedSequenceName(String value);
		String SPECIFIED_SEQUENCE_NAME_PROPERTY = "specifiedSequenceName"; //$NON-NLS-1$
	String getDefaultSequenceName();
		String DEFAULT_SEQUENCE_NAME_PROPERTY = "defaultSequenceName"; //$NON-NLS-1$
}
