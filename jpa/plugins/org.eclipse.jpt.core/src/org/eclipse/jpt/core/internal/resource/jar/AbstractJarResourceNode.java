/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.resource.jar;

import org.eclipse.core.resources.IFile;
import org.eclipse.jpt.core.JpaAnnotationProvider;
import org.eclipse.jpt.core.resource.jar.JarResourceNode;
import org.eclipse.jpt.core.resource.jar.JarResourcePackageFragmentRoot;
import org.eclipse.jpt.utility.internal.model.AbstractModel;
import org.eclipse.jpt.utility.internal.model.CallbackChangeSupport;
import org.eclipse.jpt.utility.internal.model.ChangeSupport;

/**
 * resource containment hierarchy
 */
public abstract class AbstractJarResourceNode
	extends AbstractModel
	implements JarResourceNode, CallbackChangeSupport.Source
{
	private final JarResourceNode parent;


	// ********** construction **********
	
	protected AbstractJarResourceNode(JarResourceNode parent) {
		super();
		this.checkParent(parent);
		this.parent = parent;
	}

	protected void checkParent(JarResourceNode p) {
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
		return new CallbackChangeSupport(this);
	}


	// ********** JarResourceNode implementation **********
	
	public JarResourcePackageFragmentRoot getJarResourcePackageFragmentRoot() {
		return this.parent.getJarResourcePackageFragmentRoot();
	}

	public IFile getFile() {
		return this.getJarResourcePackageFragmentRoot().getFile();
	}
	

	// ********** CallbackChangeSupport.Source implementation **********
	
	public void aspectChanged(String aspectName) {
		this.getJarResourcePackageFragmentRoot().resourceModelChanged();
	}


	// ********** convenience methods **********
	
	protected JarResourceNode getParent() {
		return this.parent;
	}

	protected JpaAnnotationProvider getAnnotationProvider() {
		return this.getJarResourcePackageFragmentRoot().getAnnotationProvider();
	}
	
}
