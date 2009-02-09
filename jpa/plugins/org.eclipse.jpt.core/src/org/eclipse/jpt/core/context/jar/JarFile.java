/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.context.jar;

import java.util.List;

import org.eclipse.jpt.core.JpaStructureNode;
import org.eclipse.jpt.core.context.JpaContextNode;
import org.eclipse.jpt.core.context.PersistentType;
import org.eclipse.jpt.core.resource.jar.JarResourcePackageFragmentRoot;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

/**
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface JarFile
	extends JpaContextNode, JpaStructureNode
{

	PersistentType getPersistentType(String typeName);

	void update(JarResourcePackageFragmentRoot jrpfr);

	/**
	 * Add to the list of current validation messages
	 */
	void validate(List<IMessage> messages);

}
