/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.resource.java;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.JpaAnnotationProvider;
import org.eclipse.jpt.core.internal.jdtutility.AnnotationEditFormatter;
import org.eclipse.jpt.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.utility.CommandExecutorProvider;
import org.eclipse.jpt.utility.internal.node.AbstractNode;

public abstract class AbstractResource extends AbstractNode
	implements JavaResourceNode
{	
	protected AbstractResource(JavaResourceNode parent) {
		super(parent);
	}
	
	
	// **************** overrides **********************************************
	
	@Override
	public JavaResourceNode parent() {
		return (JavaResourceNode) super.parent();
	}
	
	@Override
	public JpaCompilationUnitResource root() {
		return (JpaCompilationUnitResource) super.root();
	}
	
	
	// **************** JavaResource implementation ****************************
	
	public JpaAnnotationProvider annotationProvider() {
		return root().annotationProvider();
	}
	
	public CommandExecutorProvider modifySharedDocumentCommandExecutorProvider() {
		return root().modifySharedDocumentCommandExecutorProvider();
	}
	
	public AnnotationEditFormatter annotationEditFormatter()  {
		return root().annotationEditFormatter();
	}

	public JavaResourceModel resourceModel() {
		return root().resourceModel();
	}
	
	public void resolveTypes(CompilationUnit astRoot) {	
	}
	
	public String displayString() {
		return toString();
	}
	
	@Override
	protected void aspectChanged(String aspectName) {
		super.aspectChanged(aspectName);
		root().resourceChanged();
	}
}
