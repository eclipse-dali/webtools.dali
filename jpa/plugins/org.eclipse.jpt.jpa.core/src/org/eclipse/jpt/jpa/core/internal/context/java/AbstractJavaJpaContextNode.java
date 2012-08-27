/*******************************************************************************
 * Copyright (c) 2007, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.context.java;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.JptResourceType;
import org.eclipse.jpt.common.utility.Filter;
import org.eclipse.jpt.jpa.core.context.JpaContextNode;
import org.eclipse.jpt.jpa.core.context.java.JavaJpaContextNode;
import org.eclipse.jpt.jpa.core.internal.context.AbstractJpaContextNode;

public abstract class AbstractJavaJpaContextNode
	extends AbstractJpaContextNode
	implements JavaJpaContextNode
{
	protected AbstractJavaJpaContextNode(JpaContextNode parent) {
		super(parent);
	}
	
	@Override
	public JptResourceType getResourceType() {
		return JavaSourceFileDefinition.instance().getResourceType();
	}
	
	public Iterable<String> getJavaCompletionProposals(int pos, Filter<String> filter, CompilationUnit astRoot) {
		if (this.connectionProfileIsActive()) {
			Iterable<String> result = this.getConnectedJavaCompletionProposals(pos, filter, astRoot);
			if (result != null) {
				return result;
			}
		}
		return null;
	}
	
	/**
	 * This method is called if the database is connected, allowing us to
	 * get candidates from the various database tables etc.
	 * This method should <em>not</em> be cascaded to "child" objects; it should
	 * only return candidates for the current object. The cascading is
	 * handled by {@link #getJavaCompletionProposals(int, Filter, CompilationUnit)}.
	 */
	@SuppressWarnings("unused")
	protected Iterable<String> getConnectedJavaCompletionProposals(int pos, Filter<String> filter, CompilationUnit astRoot) {
		return null;
	}

}
