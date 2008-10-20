/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.resource.java;

import java.util.Iterator;

import org.eclipse.jdt.core.ElementChangedEvent;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.core.JpaAnnotationProvider;
import org.eclipse.jpt.core.utility.jdt.AnnotationEditFormatter;
import org.eclipse.jpt.utility.CommandExecutorProvider;

/**
 * 
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface JpaCompilationUnit extends JavaResourceNode {

	ICompilationUnit getCompilationUnit();

	Iterator<JavaResourcePersistentType> persistableTypes();

	JpaAnnotationProvider getAnnotationProvider();

	CommandExecutorProvider getModifySharedDocumentCommandExecutorProvider();
	
	AnnotationEditFormatter getAnnotationEditFormatter();

	/**
	 * Called (via a hook in change notification) whenever anything in the JPA
	 * compilation unit changes. Forwarded to the JPA file and on to
	 * various listeners (namely the JPA project).
	 */
	void resourceModelChanged();
	
	/**
	 * Resolve type information that could be dependent on other files being
	 * added/removed.
	 */
	void resolveTypes();

	/**
	 * JDT has fired an event indicating a Java element has changed.
	 * If it is related to the compilation unit, update it with the changed
	 * Java source code.
	 */
	void javaElementChanged(ElementChangedEvent event);

}
