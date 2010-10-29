/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.context;

import org.eclipse.jpt.core.JpaResourceType;
import org.eclipse.jpt.jaxb.core.JaxbNode;
import org.eclipse.jpt.jaxb.core.context.JaxbContextNode;
import org.eclipse.jpt.jaxb.core.internal.AbstractJaxbNode;

public abstract class AbstractJaxbContextNode
	extends AbstractJaxbNode
	implements JaxbContextNode
{

	// ********** constructor **********

	protected AbstractJaxbContextNode(JaxbNode parent) {
		super(parent);
	}


	// ********** JpaNode implentation **********

	/**
	 * covariant override
	 */
	@Override
	public JaxbContextNode getParent() {
		return (JaxbContextNode) super.getParent();
	}


	// ********** JaxbContextNode implementation **********
	
	public JpaResourceType getResourceType() {
		return getParent().getResourceType();
	}
	

}
