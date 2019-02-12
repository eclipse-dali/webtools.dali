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

import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;

/**
 * Named generators, typically sequence and table generators
 * <p>
 * Generators can be defined on<ul>
 * <li>Java and <code>orm.xml</code> entities
 * <li>Java and <code>orm.xml</code> ID mappings
 * <li><code>orm.xml</code> entity mappings elements
 * </ul>
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @see PersistenceUnit#getGenerators()
 * @version 3.3
 * @since 2.0
 */
public interface Generator
	extends JpaNamedContextModel
{
	/**
	 * Return the generator's type.
	 */
	Class<? extends Generator> getGeneratorType();

	/**
	 * Return whether the specified generator has the same state as this
	 * generator but is <em>not</em> this generator.
	 * @see #getGeneratorType()
	 */
	boolean isEquivalentTo(Generator generator);


	// ********** validation **********

	/**
	 * Return the (best guess) text location of the name.
	 */
	TextRange getNameTextRange();

	/**
	 * Return whether this generator should be validated and have validation messages displayed
	 */
	boolean supportsValidationMessages();
}
