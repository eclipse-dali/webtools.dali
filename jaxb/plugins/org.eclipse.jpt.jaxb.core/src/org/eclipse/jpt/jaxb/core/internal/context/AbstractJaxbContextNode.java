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

import org.eclipse.jpt.common.core.JptResourceType;
import org.eclipse.jpt.jaxb.core.JaxbNode;
import org.eclipse.jpt.jaxb.core.context.JaxbContextNode;
import org.eclipse.jpt.jaxb.core.context.JaxbContextRoot;
import org.eclipse.jpt.jaxb.core.internal.AbstractJaxbNode;

public abstract class AbstractJaxbContextNode
	extends AbstractJaxbNode
	implements JaxbContextNode
{
	
	// ********** constructor **********
	
	protected AbstractJaxbContextNode(JaxbNode parent) {
		super(parent);
	}
	

	// ********** synchronize/update **********

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
	
	// ********** containment hierarchy **********
	
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

}
