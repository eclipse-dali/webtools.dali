/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.orm;

import java.util.List;
import org.eclipse.jpt.core.JpaNode;
import org.eclipse.jpt.core.context.orm.OrmJpaContextNode;
import org.eclipse.jpt.core.internal.context.AbstractJpaContextNode;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

public abstract class AbstractOrmJpaContextNode extends AbstractJpaContextNode implements OrmJpaContextNode
{
	// ********** constructor **********

	protected AbstractOrmJpaContextNode(JpaNode parent) {
		super(parent);
	}
	
	// ********** validation **********
	
	/**
	 * All subclass implementations {@link #addToMessages(List<IMessage>)} 
	 * should be preceded by a "super" call to this method
	 */
	public void addToMessages(List<IMessage> messages) {
		
	}

}
