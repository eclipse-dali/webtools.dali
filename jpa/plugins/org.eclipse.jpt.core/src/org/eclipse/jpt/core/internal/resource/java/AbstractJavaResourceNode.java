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

import org.eclipse.jpt.core.JpaAnnotationProvider;
import org.eclipse.jpt.core.resource.java.JavaResourceModel;
import org.eclipse.jpt.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.core.resource.java.JpaCompilationUnit;
import org.eclipse.jpt.core.utility.jdt.AnnotationEditFormatter;
import org.eclipse.jpt.utility.CommandExecutorProvider;
import org.eclipse.jpt.utility.internal.model.AbstractModel;
import org.eclipse.jpt.utility.internal.model.CallbackChangeSupport;
import org.eclipse.jpt.utility.internal.model.ChangeSupport;

public abstract class AbstractJavaResourceNode
	extends AbstractModel
	implements JavaResourceNode, CallbackChangeSupport.Source
{
	private final JavaResourceNode parent;

	protected AbstractJavaResourceNode(JavaResourceNode parent) {
		super();
		this.checkParent(parent);
		this.parent = parent;
	}

	protected void checkParent(JavaResourceNode p) {
		if (p == null) {
			if (this.requiresParent()) {
				throw new IllegalArgumentException("'parent' cannot be null");
			}
		} else {
			if (this.forbidsParent()) {
				throw new IllegalArgumentException("'parent' must be null");
			}
		}
	}

	protected boolean requiresParent() {
		return true;
	}

	protected boolean forbidsParent() {
		return ! this.requiresParent();  // assume 'parent' is not optional
	}

	@Override
	protected ChangeSupport buildChangeSupport() {
		return new CallbackChangeSupport(this);
	}

	public JavaResourceNode getParent() {
		return this.parent;
	}
	
	public JpaCompilationUnit getJpaCompilationUnit() {
		return this.parent.getJpaCompilationUnit();
	}
	
	
	// **************** JavaResource implementation ****************************
	
	public JpaAnnotationProvider getAnnotationProvider() {
		return this.getJpaCompilationUnit().getAnnotationProvider();
	}
	
	public CommandExecutorProvider getModifySharedDocumentCommandExecutorProvider() {
		return this.getJpaCompilationUnit().getModifySharedDocumentCommandExecutorProvider();
	}
	
	public AnnotationEditFormatter getAnnotationEditFormatter()  {
		return this.getJpaCompilationUnit().getAnnotationEditFormatter();
	}

	public JavaResourceModel getResourceModel() {
		return this.getJpaCompilationUnit().getResourceModel();
	}
	
	public String displayString() {
		return toString();
	}
	
	public void aspectChanged(String aspectName) {
		this.getJpaCompilationUnit().resourceChanged();
	}
}
