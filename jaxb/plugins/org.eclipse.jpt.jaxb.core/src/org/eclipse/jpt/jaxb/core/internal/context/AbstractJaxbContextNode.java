/*******************************************************************************
 * Copyright (c) 2010, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.context;

import java.util.List;
import org.eclipse.jpt.common.core.JptResourceType;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.iterables.EmptyIterable;
import org.eclipse.jpt.jaxb.core.JaxbNode;
import org.eclipse.jpt.jaxb.core.context.JaxbContextNode;
import org.eclipse.jpt.jaxb.core.context.JaxbContextRoot;
import org.eclipse.jpt.jaxb.core.internal.AbstractJaxbNode;
import org.eclipse.jst.j2ee.model.internal.validation.ValidationCancelledException;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public abstract class AbstractJaxbContextNode
		extends AbstractJaxbNode
		implements JaxbContextNode {
	
	// ***** constructor *****
	
	protected AbstractJaxbContextNode(JaxbNode parent) {
		super(parent);
	}
	
	
	// ***** synchronize/update *****
	
	public void synchronizeWithResourceModel() {
		// NOP
	}
	
	/**
	 * convenience method
	 */
	protected void synchronizeNodesWithResourceModel(Iterable<? extends JaxbContextNode> nodes) {
		for (JaxbContextNode node : nodes) {
			node.synchronizeWithResourceModel();
		}
	}
	
	public void update() {
		// NOP
	}
	
	/**
	 * convenience method
	 */
	protected void updateNodes(Iterable<? extends JaxbContextNode> nodes) {
		for (JaxbContextNode node : nodes) {
			node.update();
		}
	}
	
	
	// ***** containment hierarchy *****
	
	/**
	 * covariant override
	 */
	@Override
	public JaxbContextNode getParent() {
		return (JaxbContextNode) super.getParent();	
	}
	
	/**
	 * Overridden in:<ul>
	 * <li>{@link org.eclipse.jpt.jaxb.core.internal.context.java.AbstractJavaContextNode#getResourceType() AbstractJavaContextNode}
	 * </ul>
	 */
	public JptResourceType getResourceType() {
		return getParent().getResourceType();
	}
	
	/**
	 * Overridden in:<ul>
	 * <li>{@link org.eclipse.jpt.jaxb.core.internal.context.GenericContextRoot#getContextRoot() GenericContextRoot}
	 * to return itself>
	 * </ul>
	 */
	public JaxbContextRoot getContextRoot() {
		return getParent().getContextRoot();
	}
	
	
	// ***** content assist *****
	
	/* default impl */
	public Iterable<String> getCompletionProposals(int pos) {
		return EmptyIterable.instance();
	}
	
	
	// ***** validation *****
	
	/* default impl */
	public TextRange getValidationTextRange() {
		return TextRange.Empty.instance(); //?
	}
	
	/**
	 * All subclass implementations {@link #validate(List, IReporter)} 
	 * should be preceded by a "super" call to this method
	 */
	public void validate(List<IMessage> messages, IReporter reporter) {
		if (reporter.isCancelled()) {
			throw new ValidationCancelledException();
		}
	}
}
