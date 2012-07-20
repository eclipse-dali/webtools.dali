/*******************************************************************************
  * Copyright (c) 2010, 2012 Red Hat, Inc.
  * Distributed under license by Red Hat, Inc. All rights reserved.
  * This program is made available under the terms of the
  * Eclipse Public License v1.0 which accompanies this distribution,
  * and is available at http://www.eclipse.org/legal/epl-v10.html
  *
  * Contributor:
  *     Red Hat, Inc. - initial API and implementation
  ******************************************************************************/
package org.eclipse.jpt.common.core.resource.java;

import org.eclipse.jdt.core.dom.PackageDeclaration;

/**
 * @author Dmitry Geraskov
 *
 * Java source code of package-info
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface JavaResourcePackage
		extends JavaResourceAnnotatedElement {

	/**
	 * Synchronize the [source] package with the specified AST PackageDeclaration.
	 */
	void synchronizeWith(PackageDeclaration pd);

	/**
	 * The Java resource persistent package's name.
	 */
	String getName();
	String NAME_PROPERTY = "name"; //$NON-NLS-1$
}
