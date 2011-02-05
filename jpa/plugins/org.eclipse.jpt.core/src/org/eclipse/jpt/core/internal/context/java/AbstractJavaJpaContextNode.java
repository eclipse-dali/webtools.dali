/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.java;

import java.util.Iterator;
import java.util.List;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.JptCommonCorePlugin;
import org.eclipse.jpt.common.core.JptResourceType;
import org.eclipse.jpt.common.utility.Filter;
import org.eclipse.jpt.core.context.JpaContextNode;
import org.eclipse.jpt.core.context.java.JavaJpaContextNode;
import org.eclipse.jpt.core.internal.context.AbstractJpaContextNode;
import org.eclipse.jst.j2ee.model.internal.validation.ValidationCancelledException;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public abstract class AbstractJavaJpaContextNode
	extends AbstractJpaContextNode
	implements JavaJpaContextNode
{
	protected AbstractJavaJpaContextNode(JpaContextNode parent) {
		super(parent);
	}
	
	@Override
	public JptResourceType getResourceType() {
		return JptCommonCorePlugin.JAVA_SOURCE_RESOURCE_TYPE;
	}
	
	public Iterator<String> javaCompletionProposals(int pos, Filter<String> filter, CompilationUnit astRoot) {
		if (this.connectionProfileIsActive()) {
			Iterator<String> result = this.connectedJavaCompletionProposals(pos, filter, astRoot);
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
	 * handled by #javaCompletionProposals(int, Filter, CompilationUnit).
	 */
	@SuppressWarnings("unused")
	public Iterator<String> connectedJavaCompletionProposals(int pos, Filter<String> filter, CompilationUnit astRoot) {
		return null;
	}
	
	// ********** validation **********
	
	/**
	 * All subclass implementations
	 * should be preceded by a "super" call to this method.
	 */
	public void validate(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		if (reporter.isCancelled()) {
			throw new ValidationCancelledException();
		}
	}

}
