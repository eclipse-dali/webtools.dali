/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jpt.jaxb.core.JaxbFactory;
import org.eclipse.jpt.jaxb.core.JaxbFile;
import org.eclipse.jpt.jaxb.core.JaxbNode;
import org.eclipse.jpt.jaxb.core.JaxbProject;
import org.eclipse.jpt.jaxb.core.platform.JaxbPlatform;
import org.eclipse.jpt.utility.internal.model.AbstractModel;
import org.eclipse.jpt.utility.internal.model.CallbackChangeSupport;
import org.eclipse.jpt.utility.internal.model.ChangeSupport;

/**
 * Some common Dali behavior:<ul>
 * <li>containment hierarchy
 * <li>Eclipse adaptable
 * <li>update triggers
 * </ul>
 */
public abstract class AbstractJaxbNode
	extends AbstractModel
	implements JaxbNode
{
	private final JaxbNode parent;


	// ********** constructor/initialization **********

	protected AbstractJaxbNode(JaxbNode parent) {
		super();
		this.checkParent(parent);
		this.parent = parent;
	}

	protected void checkParent(JaxbNode p) {
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
				AbstractJaxbNode.this.aspectChanged(aspectName);
			}
		};
	}


	// ********** IAdaptable implementation **********

	@SuppressWarnings("rawtypes")
	public Object getAdapter(Class adapter) {
		return Platform.getAdapterManager().getAdapter(this, adapter);
	}


	// ********** JaxbNode implementation **********

	public JaxbNode getParent() {
		return this.parent;
	}

	public IResource getResource() {
		return this.parent.getResource();
	}

	public JaxbProject getJaxbProject() {
		return this.parent.getJaxbProject();
	}


	// ********** convenience methods **********

	protected JaxbPlatform getJaxbPlatform() {
		return this.getJaxbProject().getJaxbPlatform();
	}
	
//	protected JaxbPlatform.Version getJaxbPlatformVersion() {
//		return this.getJaxbPlatform().getJaxbPlatformVersion();
//	}

	protected JaxbFactory getFactory() {
		return this.getJaxbPlatform().getFactory();
	}

	protected JaxbFile getJaxbFile(IFile file) {
		return this.getJaxbProject().getJaxbFile(file);
	}


	// ********** CallbackChangeSupport.Listener support **********

	protected void aspectChanged(String aspectName) {
		if (this.aspectTriggersUpdate(aspectName)) {
//			String msg = Thread.currentThread() + " aspect change: " + this + ": " + aspectName;
//			System.out.println(msg);
//			new Exception(msg).printStackTrace(System.out);
			this.getJaxbProject().update();
		}
	}

	private boolean aspectTriggersUpdate(String aspectName) {
		return ! this.aspectDoesNotTriggerUpdate(aspectName);
	}

	private boolean aspectDoesNotTriggerUpdate(String aspectName) {
		return this.nonUpdateAspectNames().contains(aspectName);
	}

	protected final Set<String> nonUpdateAspectNames() {
		synchronized (nonUpdateAspectNameSets) {
			HashSet<String> nonUpdateAspectNames = nonUpdateAspectNameSets.get(this.getClass());
			if (nonUpdateAspectNames == null) {
				nonUpdateAspectNames = new HashSet<String>();
				this.addNonUpdateAspectNamesTo(nonUpdateAspectNames);
				nonUpdateAspectNameSets.put(this.getClass(), nonUpdateAspectNames);
			}
			return nonUpdateAspectNames;
		}
	}

	private static final HashMap<Class<? extends AbstractJaxbNode>, HashSet<String>> nonUpdateAspectNameSets = new HashMap<Class<? extends AbstractJaxbNode>, HashSet<String>>();

	protected void addNonUpdateAspectNamesTo(@SuppressWarnings("unused") Set<String> nonUpdateAspectNames) {
	// when you override this method, don't forget to include:
	//	super.addNonUpdateAspectNamesTo(nonUpdateAspectNames);
	}

}
