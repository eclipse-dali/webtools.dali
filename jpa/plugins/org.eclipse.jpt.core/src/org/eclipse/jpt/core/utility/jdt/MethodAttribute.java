/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.utility.jdt;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jpt.utility.MethodSignature;

/**
 * Property attribute: just some covariant overrides.
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * This interface is not intended to be implemented by clients.
 */
// TODO rename PropertyAttribute?
public interface MethodAttribute extends Attribute {

	/**
	 * Covariant override.
	 */
	IMethodBinding getBinding(CompilationUnit astRoot);

	/**
	 * Covariant override.
	 */
	MethodDeclaration getBodyDeclaration(CompilationUnit astRoot);

	/**
	 * This method must be used instead of Member#matches(String, int).
	 */
	boolean matches(MethodSignature signature, int occurrence);

}
