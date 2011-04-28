/*******************************************************************************
 * Copyright (c) 2007, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.context;

import java.util.List;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jpa.core.context.JpaContextNode;
import org.eclipse.jpt.jpa.core.context.XmlContextNode;
import org.eclipse.jst.j2ee.model.internal.validation.ValidationCancelledException;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public abstract class AbstractXmlContextNode
	extends AbstractJpaContextNode
	implements XmlContextNode
{
	protected AbstractXmlContextNode(JpaContextNode parent) {
		super(parent);
	}


	// ********** validation **********

	/**
	 * All subclass implementations should be have a "super" call to this method.
	 */
	public void validate(List<IMessage> messages, IReporter reporter) {
		if (reporter.isCancelled()) {
			throw new ValidationCancelledException();
		}
	}

	/**
	 * Return the specified text range if it is not <code>null</code>; if it is
	 * <code>null</code>, return the node's validation text range.
	 */
	protected TextRange getValidationTextRange(TextRange textRange) {
		return (textRange != null) ? textRange : this.getValidationTextRange();
	}
}
