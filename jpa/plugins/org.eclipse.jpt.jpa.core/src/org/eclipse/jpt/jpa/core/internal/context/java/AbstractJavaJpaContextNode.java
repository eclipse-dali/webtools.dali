/*******************************************************************************
 * Copyright (c) 2007, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.context.java;

import java.util.List;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.JptCommonCorePlugin;
import org.eclipse.jpt.common.core.JptResourceType;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.Filter;
import org.eclipse.jpt.jpa.core.context.JpaContextNode;
import org.eclipse.jpt.jpa.core.context.java.JavaJpaContextNode;
import org.eclipse.jpt.jpa.core.internal.context.AbstractJpaContextNode;
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

	/**
	 * Validate the specified node if it is not <code>null</code>.
	 */
	protected void validateNode(JavaJpaContextNode node, List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		if (node != null) {
			node.validate(messages, reporter, astRoot);
		}
	}

	/**
	 * Validate the specified nodes.
	 */
	protected void validateNodes(Iterable<? extends JavaJpaContextNode> nodes, List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		for (JavaJpaContextNode node : nodes) {
			node.validate(messages, reporter, astRoot);
		}
	}

	/**
	 * Return the specified text range if it is not <code>null</code>; if it is
	 * <code>null</code>, return the node's validation text range.
	 */
	protected TextRange getValidationTextRange(TextRange textRange, CompilationUnit astRoot) {
		return (textRange != null) ? textRange : this.getValidationTextRange(astRoot);
	}
}
