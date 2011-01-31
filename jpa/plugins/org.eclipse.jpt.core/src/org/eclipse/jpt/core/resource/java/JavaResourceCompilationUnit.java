/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.resource.java;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.utility.jdt.AnnotationEditFormatter;
import org.eclipse.jpt.utility.CommandExecutor;

/**
 * Dali resource for JDT compilation unit.
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 2.3
 * @since 2.2
 */
public interface JavaResourceCompilationUnit
	extends JavaResourceNode.Root
{
	/**
	 * Return the corresponding JDT compilation unit.
	 */
	ICompilationUnit getCompilationUnit();

	/**
	 * Return the JPA project's annotation formatter. This is used to make any
	 * manipulated annotations reasonably readable after being written to the
	 * Java source file.
	 */
	AnnotationEditFormatter getAnnotationEditFormatter();

	/**
	 * This allows the resource model to modify the Java source code on the
	 * UI thread when it is executing on another thread.
	 */
	CommandExecutor getModifySharedDocumentCommandExecutor();

	/**
	 * Resolve type information that could be dependent on other files being
	 * added/removed.
	 */
	void resolveTypes();

	/**
	 * Something in Java has changed (typically either the compilation unit's
	 * source code or the Java classpath); synchronize the compilation unit's
	 * state with the Java source code etc.
	 */
	void synchronizeWithJavaSource();

	/**
	 * Build an AST for the compilation unit with its bindings resolved.
	 */
	CompilationUnit buildASTRoot();

}
