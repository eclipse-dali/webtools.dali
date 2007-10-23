/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.resource.java;

import org.eclipse.jpt.core.internal.IJpaAnnotationProvider;
import org.eclipse.jpt.utility.internal.CommandExecutorProvider;
import org.eclipse.jpt.utility.internal.node.AbstractNodeModel;
import org.eclipse.jpt.utility.internal.node.Node;

public abstract class AbstractResource extends AbstractNodeModel 
	implements JavaResource
{	
	protected AbstractResource(JavaResource parent) {
		super(parent);
	}
	
	
	// **************** overrides **********************************************
	
	@Override
	public JavaResource parent() {
		return (JavaResource) super.parent();
	}
	
	@Override
	public JpaCompilationUnitResource root() {
		return (JpaCompilationUnitResource) super.root();
	}
	
	
	// **************** JavaResource implementation ****************************
	
	public IJpaAnnotationProvider annotationProvider() {
		return root().annotationProvider();
	}
	
	public CommandExecutorProvider modifySharedDocumentCommandExecutorProvider() {
		return root().modifySharedDocumentCommandExecutorProvider();
	}
	
	/**
	 * @see Node#displayString()
	 * 
	 * Return simple toString.  Override if this class is to be displayed.
	 */
	public String displayString() {
		return toString();
	}
}
