/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
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
		return this.jpaPlatform().getJpaFactory();
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


	// TODO this stuff should go away when we rework "defaults"
	// ********** recalculate defaults **********

	private static final HashMap<Class<? extends AbstractNode>, HashSet<String>> nonDefaultAspectNameSets = new HashMap<Class<? extends AbstractNode>, HashSet<String>>();

	@Override
	protected void aspectChanged(String aspectName) {
		super.aspectChanged(aspectName);
		if (this.aspectAffectsDefaults(aspectName)) {
			// System.out.println(Thread.currentThread() + " defaults change: " + this + ": " + aspectName);
			this.jpaProject().update();
		}
	}

	private boolean aspectAffectsDefaults(String aspectName) {
		return ! this.aspectDoesNotAffectDefaults(aspectName);
	}

	private boolean aspectDoesNotAffectDefaults(String aspectName) {
		return this.nonDefaultAspectNames().contains(aspectName);
	}

	protected final Set<String> nonDefaultAspectNames() {
		synchronized (nonDefaultAspectNameSets) {
			HashSet<String> nonDefaultAspectNames = nonDefaultAspectNameSets.get(this.getClass());
			if (nonDefaultAspectNames == null) {
				nonDefaultAspectNames = new HashSet<String>();
				this.addNonDefaultAspectNamesTo(nonDefaultAspectNames);
				nonDefaultAspectNameSets.put(this.getClass(), nonDefaultAspectNames);
			}
			return nonDefaultAspectNames;
		}
	}

	protected void addNonDefaultAspectNamesTo(Set<String> nonDefaultAspectNames) {
		nonDefaultAspectNames.add(COMMENT_PROPERTY);
		nonDefaultAspectNames.add(DIRTY_BRANCH_PROPERTY);
		nonDefaultAspectNames.add(BRANCH_PROBLEMS_LIST);
		nonDefaultAspectNames.add(HAS_BRANCH_PROBLEMS_PROPERTY);
	// when you override this method, don't forget to include:
	//	super.addNonDefaultAspectNamesTo(nonDefaultAspectNames);
	}

}
