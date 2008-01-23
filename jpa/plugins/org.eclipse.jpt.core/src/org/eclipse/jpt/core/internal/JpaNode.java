/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jpt.db.internal.ConnectionProfile;
import org.eclipse.jpt.db.internal.Database;
import org.eclipse.jpt.utility.internal.iterators.TransformationIterator;
import org.eclipse.jpt.utility.internal.node.AbstractNode;
import org.eclipse.jpt.utility.internal.node.Node;

/**
 * 
 */
public abstract class JpaNode
	extends AbstractNode
	implements IJpaNode
{


	// ********** constructor **********

	protected JpaNode(IJpaNode parent) {
		super(parent);
	}
	
	
	// ********** IAdaptable implementation **********
	
	@SuppressWarnings("unchecked")
	public Object getAdapter(Class adapter) {
		return Platform.getAdapterManager().getAdapter(this, adapter);
	}


	// ********** IJpaNodeModel implementation **********

	public IResource resource() {
		return this.jpaProject().project();
	}

	public IJpaProject jpaProject() {
		return this.root();
	}

	public String displayString() {
		return this.toString();
	}


	// ********** overrides **********

	@Override
	public IJpaNode parent() {
		return (IJpaNode) super.parent();
	}

	@Override
	public IJpaProject root() {
		return (IJpaProject) super.root();
	}


	// ********** convenience methods **********

	public Iterator<IJpaNode> jpaChildren() {
		return new TransformationIterator<Node, IJpaNode>(this.children()) {
			@Override
			protected IJpaNode transform(Node next) {
				return (IJpaNode) next;
			}
		};
	}

	protected IJpaPlatform jpaPlatform() {
		return this.jpaProject().jpaPlatform();
	}

	protected IJpaFactory jpaFactory() {
		return this.jpaPlatform().jpaFactory();
	}

	protected ConnectionProfile connectionProfile() {
		return this.jpaProject().connectionProfile();
	}

	protected Database database() {
		return this.connectionProfile().getDatabase();
	}

	protected boolean isConnected() {
		return this.connectionProfile().isConnected();
	}


	// ********** update model **********

	private static final HashMap<Class<? extends AbstractNode>, HashSet<String>> nonUpdateAspectNameSets = new HashMap<Class<? extends AbstractNode>, HashSet<String>>();

	@Override
	protected void aspectChanged(String aspectName) {
		super.aspectChanged(aspectName);
		if (this.aspectTriggersUpdate(aspectName)) {
			// System.out.println(Thread.currentThread() + " \"update\" change: " + this + ": " + aspectName);
			this.jpaProject().update();
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

	protected void addNonUpdateAspectNamesTo(Set<String> nonUpdateAspectNames) {
		nonUpdateAspectNames.add(COMMENT_PROPERTY);
		nonUpdateAspectNames.add(DIRTY_BRANCH_PROPERTY);
		nonUpdateAspectNames.add(BRANCH_PROBLEMS_LIST);
		nonUpdateAspectNames.add(HAS_BRANCH_PROBLEMS_PROPERTY);
	// when you override this method, don't forget to include:
	//	super.addNonUpdateAspectNamesTo(nonUpdateAspectNames);
	}

}
