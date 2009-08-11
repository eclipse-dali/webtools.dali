/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.context.orm;

import java.util.ListIterator;
import org.eclipse.jpt.core.context.XmlContextNode;
import org.eclipse.jpt.core.resource.orm.XmlAssociationOverride;

public interface OrmJoinColumnInAssociationOverrideJoiningStrategy 
	extends 
		XmlContextNode,
		OrmJoinColumnJoiningStrategy
{
	void update(XmlAssociationOverride xao);

	ListIterator<OrmJoinColumn> joinColumns();
}
