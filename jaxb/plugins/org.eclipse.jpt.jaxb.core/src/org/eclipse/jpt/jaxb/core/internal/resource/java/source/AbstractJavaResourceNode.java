/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.resource.java.source;

import org.eclipse.core.resources.IFile;
import org.eclipse.jpt.common.utility.internal.model.AbstractModel;
import org.eclipse.jpt.common.utility.internal.model.AspectChangeSupport;
import org.eclipse.jpt.common.utility.internal.model.ChangeSupport;
import org.eclipse.jpt.jaxb.core.AnnotationProvider;
import org.eclipse.jpt.jaxb.core.resource.java.JavaResourceNode;

/**
 * Java resource containment hierarchy
 */
public abstract class AbstractJavaResourceNode
	extends AbstractModel
	implements JavaResourceNode
{
	protected final JavaResourceNode parent;


	// ********** constructor **********
	
	protected AbstractJavaResourceNode(JavaResourceNode parent) {
		super();
		this.checkParent(parent);
		this.parent = parent;
	}

	protected JavaResourceNode getParent() {
		return this.parent;
	}

	// ********** parent **********

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


	// ********** change support callback hook **********

	@Override
	protected final ChangeSupport buildChangeSupport() {
		return new AspectChangeSupport(this, this.buildChangeSupportListener());
	}

	private AspectChangeSupport.Listener buildChangeSupportListener() {
		return new AspectChangeSupport.Listener() {
			public void aspectChanged(String aspectName) {
				AbstractJavaResourceNode.this.aspectChanged(aspectName);
			}
		};
	}

	/**
	 * ignore the aspect name, we notify listeners of *every* change
	 */
	protected void aspectChanged(@SuppressWarnings("unused") String aspectName) {
		this.getRoot().resourceModelChanged();
	}


	// ********** JavaResourceNode implementation **********

	/**
	 * @see org.eclipse.jpt.core.internal.resource.java.source.SourceCompilationUnit#getRoot()
	 * @see org.eclipse.jpt.core.internal.resource.java.binary.BinaryPackageFragmentRoot#getRoot()
	 * @see org.eclipse.jpt.core.internal.resource.java.binary.BinaryPersistentTypeCache#getRoot()
	 */
	public Root getRoot() {
		return this.parent.getRoot();
	}

	public IFile getFile() {
		return this.getRoot().getFile();
	}


	// ********** convenience methods **********

	protected AnnotationProvider getAnnotationProvider() {
		return this.getRoot().getAnnotationProvider();
	}

}
