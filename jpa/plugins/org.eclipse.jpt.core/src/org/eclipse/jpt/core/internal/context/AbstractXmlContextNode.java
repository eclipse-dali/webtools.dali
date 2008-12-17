/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context;

import java.util.List;

import org.eclipse.jpt.core.context.JpaContextNode;
import org.eclipse.jpt.core.context.XmlContextNode;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

public abstract class AbstractXmlContextNode
	extends AbstractJpaContextNode
	implements XmlContextNode
{

	// ********** constructor **********

	protected AbstractXmlContextNode(JpaContextNode parent) {
		super(parent);
	}


	// ********** validation **********

	/**
	 * All subclass implementations {@link #validate(List<IMessage>)} 
	 * should be preceded by a "super" call to this method
	 */
	public void validate(List<IMessage> messages) {
		// do nothing by default
	}

}
