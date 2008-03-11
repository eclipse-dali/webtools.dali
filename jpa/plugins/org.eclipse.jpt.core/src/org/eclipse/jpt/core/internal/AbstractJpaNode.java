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
import org.eclipse.jpt.core.JpaFactory;
import org.eclipse.jpt.core.JpaNode;
import org.eclipse.jpt.core.JpaPlatform;
import org.eclipse.jpt.core.JpaProject;
import org.eclipse.jpt.db.ConnectionProfile;
import org.eclipse.jpt.db.Database;
import org.eclipse.jpt.utility.internal.iterators.TransformationIterator;
import org.eclipse.jpt.utility.internal.node.AbstractNode;
import org.eclipse.jpt.utility.internal.node.Node;

/**
 * 
 */
public abstract class AbstractJpaNode
	extends AbstractNode
	implements JpaNode
{


	// ********** constructor **********

	protected AbstractJpaNode(JpaNode parent) {
		super(parent);
	}
	
	
	// ********** IAdaptable implementation **********
	
	@SuppressWarnings("unchecked")
	public Object getAdapter(Class adapter) {
		return Platform.getAdapterManager().getAdapter(this, adapter);
	}


	// ********** IJpaNodeModel implementation **********

	public IResource resource() {
		return parent().resource();
	}

	public JpaProject jpaProject() {
		return parent().jpaProject();
	}

	public String displayString() {
		return this.toString();
	}


	// ********** overrides **********

	@Override
	public JpaNode parent() {
		return (JpaNode) super.parent();
	}
	

	// ********** convenience methods **********

	public Iterator<JpaNode> jpaChildren() {
		return new TransformationIterator<Node, JpaNode>(this.children()) {
			@Override
			protected JpaNode transform(Node next) {
				return (JpaNode) next;
			}
		};
	}

	protected JpaPlatform jpaPlatform() {
		return this.jpaProject().jpaPlatform();
	}

	protected JpaFactory jpaFactory() {
		return this.jpaPlatform().jpaFactory();
	}

	protected ConnectionProfile connectionProfile() {
		return this.jpaProject().connectionProfile();
	}

	protected Database database() {
		return this.connectionProfile().database();
	}

	public boolean connectionProfileIsActive() {
		return this.connectionProfile().isActive();
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
