/*******************************************************************************
 * Copyright (c) 2010, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.core.resource.java;

import org.eclipse.jdt.core.dom.EnumDeclaration;



/**
 * Java source code or binary enum.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.0
 * @since 3.0
 */
public interface JavaResourceEnum
	extends JavaResourceAbstractType
{

	/**
	 * Synchronize the [source] enum with the specified AST EnumDeclaration.
	 */
	void synchronizeWith(EnumDeclaration enumDeclaration);
	
	/**
	 * Resolve type information that could be dependent on changes elsewhere
	 * in the workspace.
	 */
	void resolveTypes(EnumDeclaration enumDeclaration);


	// ********** enum constants **********

	/**
	 * Return the enum's enum constants.
	 */
	Iterable<JavaResourceEnumConstant> getEnumConstants();
		String ENUM_CONSTANTS_COLLECTION = "enumConstants"; //$NON-NLS-1$
}
