/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility;

import java.io.PrintWriter;
import java.lang.reflect.Method;

/**
 * This interface describes a Java method signature; i.e. its "name"
 * and its "parameter types". The parameter types are referenced by name,
 * allowing us to reference classes that are not (or cannot be) loaded.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * <p>
 * This interface is not intended to be implemented by clients.
 */
public interface MethodSignature {

	/**
	 * Return the method's name.
	 */
	String getName();

	/**
	 * Return the method's parameter types.
	 */
	JavaType[] getParameterTypes();

	/**
	 * Return whether the method signature describes the specified method.
	 */
	boolean describes(Method method);

	/**
	 * Return whether the method signature equals the specified signature.
	 */
	boolean equals(String otherName, JavaType[] otherParameterTypes);

	/**
	 * Return whether the method signature equals the specified signature.
	 */
	boolean equals(MethodSignature other);

	/**
	 * Return a string representation of the method's signature:<p>
	 * <code>"foo(int, java.lang.String)"</code>
	 */
	String getSignature();

	/**
	 * Append a string representation of the method's signature:<p>
	 * <code>"foo(int, java.lang.String)"</code>
	 */
	void appendSignatureTo(StringBuilder sb);

	/**
	 * Print a string representation of the method's signature:<p>
	 * <code>"foo(int, java.lang.String)"</code>
	 */
	void printSignatureOn(PrintWriter pw);

}
