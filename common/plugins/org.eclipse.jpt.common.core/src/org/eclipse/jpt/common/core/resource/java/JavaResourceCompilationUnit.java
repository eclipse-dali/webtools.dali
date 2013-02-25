/*******************************************************************************
 * Copyright (c) 2010, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.core.resource.java;

import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.internal.plugin.JptCommonCorePlugin;
import org.eclipse.jpt.common.core.utility.jdt.AnnotationEditFormatter;
import org.eclipse.jpt.common.utility.command.CommandExecutor;

/**
 * Dali resource for JDT compilation unit (i.e. a Java source code file).
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
public interface JavaResourceCompilationUnit
	extends JavaResourceModel.Root
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
	 * The primary type of the AST compilation unit, can be null.
	 * This is named the same as the compilation unit.
	 */
	JavaResourceAbstractType getPrimaryType();

	/**
	 * Something in Java has changed (typically either the compilation unit's
	 * source code or the Java classpath); synchronize the compilation unit's
	 * state with the Java source code etc.
	 */
	void synchronizeWithJavaSource();

	/**
	 * Call this method over {@link #synchronizeWithJavaSource()} if possible
	 * It is more performant to pass an already build astRoot than to build
	 * a new one. The passed in CompilationUnit must have resolved bindings.
	 */
	void synchronizeWithJavaSource(CompilationUnit astRoot);

	/**
	 * Checks {@link ICompilationUnit#isConsistent()} and if
	 * true, does nothing. If false, calls synchronizeWithJavaSource()
	 * which calls buildASTRoot(). Trying to avoid building astRoots
	 * since that is expensive. 
	 */
	void synchronizeWithJavaSourceIfNecessary();

	/**
	 * Build an AST for the compilation unit with its bindings resolved.
	 */
	CompilationUnit buildASTRoot();


	// ********** content types **********

	/**
	 * The content type for Java source code files.
	 */
	IContentType CONTENT_TYPE = Platform.getContentTypeManager().getContentType(JavaCore.JAVA_SOURCE_CONTENT_TYPE);

	/**
	 * The content type for <code>package-info</code> Java source code files.
	 */
	IContentType PACKAGE_INFO_CONTENT_TYPE = JptCommonCorePlugin.instance().getContentType("javaPackageInfo"); //$NON-NLS-1$
}
