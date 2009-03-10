/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.resource.java;

import org.eclipse.core.resources.IFile;
import org.eclipse.jpt.core.JpaAnnotationProvider;
import org.eclipse.jpt.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.core.resource.java.JavaResourceCompilationUnit;
import org.eclipse.jpt.utility.internal.model.AbstractModel;
import org.eclipse.jpt.utility.internal.model.CallbackChangeSupport;
import org.eclipse.jpt.utility.internal.model.ChangeSupport;

/**
 * Java resource containment hierarchy
 */
public abstract class AbstractJavaResourceNode
	extends AbstractModel
	implements JavaResourceNode
{
	private final JavaResourceNode parent;


	// ********** construction **********
	
	protected AbstractJavaResourceNode(JavaResourceNode parent) {
		super();
		this.checkParent(parent);
		this.parent = parent;
	}

	protected void checkParent(JavaResourceNode p) {
		if (p == null) {
			if (this.requiresParent()) {
				throw new IllegalArgumentException("'parent' cannot be null"); //$NON-NLS-1$
			}
		} else {
			if (this.forbidsParent()) {
				throw new IllegalArgumentException("'parent' must be null"); //$NON-NLS-1$
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
		return new CallbackChangeSupport(this, this.buildChangeSupportListener());
	}

	protected CallbackChangeSupport.Listener buildChangeSupportListener() {
		return new CallbackChangeSupport.Listener() {
			public void aspectChanged(String aspectName) {
				AbstractJavaResourceNode.this.aspectChanged(aspectName);
			}
		};
	}


	// ********** JavaResourceNode implementation **********

	public Root getRoot() {
		return this.parent.getRoot();
	}

	public IFile getFile() {
		return this.getRoot().getFile();
	}
	

	// ********** change support callback **********

	/**
	 * ignore the aspect name, we notify listeners of *every* change
	 */
	protected void aspectChanged(@SuppressWarnings("unused") String aspectName) {
		this.getRoot().resourceModelChanged();
	}


	// ********** convenience methods **********
	
	protected JavaResourceNode getParent() {
		return this.parent;
	}

	protected JpaAnnotationProvider getAnnotationProvider() {
		return this.getRoot().getAnnotationProvider();
	}

	// TODO move... ========================
	public JavaResourceCompilationUnit getJavaResourceCompilationUnit() {
		return (JavaResourceCompilationUnit) this.getRoot();
	}

}
