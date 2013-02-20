/*******************************************************************************
 * Copyright (c) 2010, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.context;

import java.util.List;
import org.eclipse.core.resources.IResource;
import org.eclipse.jpt.common.core.JptResourceType;
import org.eclipse.jpt.common.core.internal.utility.ValidationMessageTools;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.core.utility.ValidationMessage;
import org.eclipse.jpt.common.utility.internal.iterable.EmptyIterable;
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

	/**
	 * @see #buildErrorValidationMessage(ValidationMessage, JaxbNode)
	 * @see IMessage#HIGH_SEVERITY
	 */
	protected IMessage buildErrorValidationMessage(ValidationMessage message) {
		return this.buildErrorValidationMessage(message, this);
	}

	/**
	 * @see ValidationMessageTools#buildErrorValidationMessage(ValidationMessage, IResource)
	 * @see IMessage#HIGH_SEVERITY
	 */
	protected IMessage buildErrorValidationMessage(ValidationMessage message, JaxbNode target) {
		return ValidationMessageTools.buildErrorValidationMessage(message, target.getResource());
	}

	/**
	 * @see #buildValidationMessage(ValidationMessage, int, JaxbNode)
	 */
	protected IMessage buildValidationMessage(ValidationMessage message, int defaultSeverity) {
		return this.buildValidationMessage(message, defaultSeverity, this);
	}

	/**
	 * @see ValidationMessageTools#buildValidationMessage(ValidationMessage, int, IResource)
	 */
	protected IMessage buildValidationMessage(ValidationMessage message, int defaultSeverity, JaxbNode target) {
		return ValidationMessageTools.buildValidationMessage(message, defaultSeverity, target.getResource());
	}

	/**
	 * @see #buildErrorValidationMessage(ValidationMessage, JaxbNode, Object[])
	 * @see IMessage#HIGH_SEVERITY
	 */
	protected IMessage buildErrorValidationMessage(ValidationMessage message, Object... args) {
		return this.buildErrorValidationMessage(message, this, args);
	}

	/**
	 * @see ValidationMessageTools#buildErrorValidationMessage(ValidationMessage, IResource, Object[])
	 * @see IMessage#HIGH_SEVERITY
	 */
	protected IMessage buildErrorValidationMessage(ValidationMessage message, JaxbNode target, Object... args) {
		return ValidationMessageTools.buildErrorValidationMessage(message, target.getResource(), args);
	}

	/**
	 * @see #buildValidationMessage(ValidationMessage, int, JaxbNode, Object[])
	 */
	protected IMessage buildValidationMessage(ValidationMessage message, int defaultSeverity, Object... args) {
		return this.buildValidationMessage(message, defaultSeverity, this, args);
	}

	/**
	 * @see ValidationMessageTools#buildValidationMessage(ValidationMessage, int, IResource, Object[])
	 */
	protected IMessage buildValidationMessage(ValidationMessage message, int defaultSeverity, JaxbNode target, Object... args) {
		return ValidationMessageTools.buildValidationMessage(message, defaultSeverity, target.getResource(), args);
	}

	/**
	 * @see #buildErrorValidationMessage(ValidationMessage, JaxbNode, TextRange)
	 * @see IMessage#HIGH_SEVERITY
	 */
	protected IMessage buildErrorValidationMessage(ValidationMessage message, TextRange textRange) {
		return this.buildErrorValidationMessage(message, this, textRange);
	}

	/**
	 * @see ValidationMessageTools#buildErrorValidationMessage(ValidationMessage, IResource, TextRange)
	 * @see IMessage#HIGH_SEVERITY
	 */
	protected IMessage buildErrorValidationMessage(ValidationMessage message, JaxbNode target, TextRange textRange) {
		return ValidationMessageTools.buildErrorValidationMessage(message, target.getResource(), textRange);
	}

	/**
	 * @see #buildValidationMessage(ValidationMessage, int, JaxbNode, TextRange)
	 */
	protected IMessage buildValidationMessage(ValidationMessage message, int defaultSeverity, TextRange textRange) {
		return this.buildValidationMessage(message, defaultSeverity, this, textRange);
	}

	/**
	 * @see ValidationMessageTools#buildValidationMessage(ValidationMessage, int, IResource, TextRange)
	 */
	protected IMessage buildValidationMessage(ValidationMessage message, int defaultSeverity, JaxbNode target, TextRange textRange) {
		return ValidationMessageTools.buildValidationMessage(message, defaultSeverity, target.getResource(), textRange);
	}

	/**
	 * @see #buildErrorValidationMessage(ValidationMessage, JaxbNode, TextRange, Object[])
	 * @see IMessage#HIGH_SEVERITY
	 */
	protected IMessage buildErrorValidationMessage(ValidationMessage message, TextRange textRange, Object... args) {
		return this.buildErrorValidationMessage(message, this, textRange, args);
	}

	/**
	 * @see ValidationMessageTools#buildErrorValidationMessage(ValidationMessage, IResource, TextRange, Object[])
	 * @see IMessage#HIGH_SEVERITY
	 */
	protected IMessage buildErrorValidationMessage(ValidationMessage message, JaxbNode target, TextRange textRange, Object... args) {
		return ValidationMessageTools.buildErrorValidationMessage(message, target.getResource(), textRange, args);
	}

	/**
	 * @see #buildValidationMessage(ValidationMessage, int, JaxbNode, TextRange, Object[])
	 */
	protected IMessage buildValidationMessage(ValidationMessage message, int defaultSeverity, TextRange textRange, Object... args) {
		return this.buildValidationMessage(message, defaultSeverity, this, textRange, args);
	}

	/**
	 * @see ValidationMessageTools#buildValidationMessage(ValidationMessage, int, IResource, TextRange, Object[])
	 */
	protected IMessage buildValidationMessage(ValidationMessage message, int defaultSeverity, JaxbNode target, TextRange textRange, Object... args) {
		return ValidationMessageTools.buildValidationMessage(message, defaultSeverity, target.getResource(), textRange, args);
	}
}
