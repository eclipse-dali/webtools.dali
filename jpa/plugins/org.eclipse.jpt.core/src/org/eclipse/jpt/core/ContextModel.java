/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core;

import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

public interface ContextModel
{
	/**
	 * Update the context model with the content of the JPA project
	 */
	void update(IProgressMonitor monitor);
	
	// ********** validation **********
	
	/**
	 * All subclass implementations {@link #addToMessages(List<IMessage>, CompilationUnit astRoot)} 
	 * should be preceded by a "super" call to this method
	 */
	public void addToMessages(List<IMessage> messages, CompilationUnit astRoot);
}