/*******************************************************************************
 * Copyright (c) 2007, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.context;

import org.eclipse.jpt.jpa.core.context.JpaContextNode;
import org.eclipse.jpt.jpa.core.context.XmlContextNode;

public abstract class AbstractXmlContextNode
	extends AbstractJpaContextNode
	implements XmlContextNode
{
	protected AbstractXmlContextNode(JpaContextNode parent) {
		super(parent);
	}

}
