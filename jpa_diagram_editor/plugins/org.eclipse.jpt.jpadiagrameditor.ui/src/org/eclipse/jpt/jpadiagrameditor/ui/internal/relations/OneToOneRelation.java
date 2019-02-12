/*******************************************************************************
 * <copyright>
 *
 * Copyright (c) 2005, 2013 SAP AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    Stefan Dimov - initial API, implementation and documentation
 *
 * </copyright>
 *
 *******************************************************************************/
package org.eclipse.jpt.jpadiagrameditor.ui.internal.relations;

import org.eclipse.jpt.jpa.core.context.PersistentType;

abstract public class OneToOneRelation extends AbstractRelation {
	
	public OneToOneRelation(PersistentType owner, 
							PersistentType inverse) {
		super(owner, inverse);
	}
	
	@Override
	public RelType getRelType() {
		return RelType.ONE_TO_ONE;
	}
		
}
