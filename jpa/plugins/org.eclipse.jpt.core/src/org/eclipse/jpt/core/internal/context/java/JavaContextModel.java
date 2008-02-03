/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.java;

import java.util.Iterator;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.IJpaNode;
import org.eclipse.jpt.core.internal.context.base.JpaContextNode;
import org.eclipse.jpt.utility.internal.Filter;

public abstract class JavaContextModel extends JpaContextNode implements IJavaJpaContextNode
{
	// ********** constructor **********

	protected JavaContextModel(IJpaNode parent) {
		super(parent);
	}

	public Iterator<String> candidateValuesFor(int pos, Filter<String> filter, CompilationUnit astRoot) {
		if (this.isConnected()) {
			Iterator<String> result = this.connectedCandidateValuesFor(pos, filter, astRoot);
			if (result != null) {
				return result;
			}
		}
		return null;
	}
	
	/**
	 * This method is called if the database is connected, allowing us to
	 * get candidates from the various database tables etc.
	 * This method should NOT be cascaded to "child" objects; it should
	 * only return candidates for the current object. The cascading is
	 * handled by #candidateValuesFor(int, Filter, CompilationUnit).
	 */
	public Iterator<String> connectedCandidateValuesFor(int pos, Filter<String> filter, CompilationUnit astRoot) {
		return null;
	}

}
