/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.resource.java;

import java.util.Iterator;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.JpaAnnotationProvider;
import org.eclipse.jpt.core.JpaResourceModel;
import org.eclipse.jpt.core.utility.jdt.AnnotationEditFormatter;
import org.eclipse.jpt.utility.CommandExecutor;

/**
 * 
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface JavaResourceCompilationUnit
	extends JavaResourceNode, JpaResourceModel
{
	/**
	 * Return the corresponding JDT compilation unit.
	 */
	ICompilationUnit getCompilationUnit();

	/**
	 * Return all the Java resource types that are "persistable", as defined
	 * by the JPA spec.
	 */
	Iterator<JavaResourcePersistentType> persistableTypes();

	/**
	 * Return the annotation provider that supplies the annotations the resource
	 * model uses to modify the Java source.
	 */
	JpaAnnotationProvider getAnnotationProvider();

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
	 * Called (via a hook in change notification) whenever anything in the
	 * Java resource compilation unit changes. Forwarded to resource model
	 * listeners (namely the JPA project).
	 */
	void resourceModelChanged();

	/**
	 * Resolve type information that could be dependent on other files being
	 * added/removed.
	 */
	void resolveTypes();

	/**
	 * Something in Java has changed (typically either the compilation unit's
	 * source code or the Java classpath); update the JPA compilation unit's
	 * state to be in synch with the source code etc.
	 */
	void update();

	/**
	 * Build an AST for the compilation unit with its bindings resolved.
	 */
	CompilationUnit buildASTRoot();

}
