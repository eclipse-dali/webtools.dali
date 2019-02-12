/*******************************************************************************
 * Copyright (c) 2010, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.core.resource.java;

import org.eclipse.jdt.core.dom.EnumConstantDeclaration;

/**
 * Java source code or binary enum constant
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.0
 * @since 3.0
 */
public interface JavaResourceEnumConstant
	extends JavaResourceMember
{
	/**
	 * The Java resource enum constant's name does not change.
	 */
	String getName();

	/**
	 * Synchronize the [source] enum constant with the specified AST EnumConstantDeclaration.
	 */
	void synchronizeWith(EnumConstantDeclaration astEnumConstant);

	/**
	 * Resolve type information that could be dependent on changes elsewhere
	 * in the workspace.
	 */
	void resolveTypes(EnumConstantDeclaration astEnumConstant);
	
}
