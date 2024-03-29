/*******************************************************************************
 * Copyright (c) 2010, 2012 Red Hat, Inc.
 * Distributed under license by Red Hat, Inc. All rights reserved.
 * This program is made available under the terms of the
 * Eclipse Public License 2.0 which accompanies this distribution,
 * and is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributor:
 *     Red Hat, Inc. - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.core.utility.jdt;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.PackageDeclaration;

/**
 * @author Dmitry Geraskov
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 *
 * This interface is not intended to be implemented by clients.
 */
public interface AnnotatedPackage extends AnnotatedElement {	

	/**
	 * Covariant override.
	 */
	PackageDeclaration getBodyDeclaration(CompilationUnit astRoot);

}
