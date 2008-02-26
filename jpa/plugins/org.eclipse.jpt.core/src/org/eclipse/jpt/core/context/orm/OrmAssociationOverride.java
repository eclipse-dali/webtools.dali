/*******************************************************************************
 *  Copyright (c) 2008 Oracle. All rights reserved. This
 *  program and the accompanying materials are made available under the terms of
 *  the Eclipse Public License v1.0 which accompanies this distribution, and is
 *  available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.context.orm;

import java.util.ListIterator;
import org.eclipse.jpt.core.context.AssociationOverride;
import org.eclipse.jpt.core.resource.orm.XmlAssociationOverride;

public interface OrmAssociationOverride extends AssociationOverride
{
	@SuppressWarnings("unchecked")
	ListIterator<OrmJoinColumn> joinColumns();

	@SuppressWarnings("unchecked")
	ListIterator<OrmJoinColumn> defaultJoinColumns();

	@SuppressWarnings("unchecked")
	ListIterator<OrmJoinColumn> specifiedJoinColumns();

	OrmJoinColumn addSpecifiedJoinColumn(int index);

	void initialize(XmlAssociationOverride associationOverride);

	void update(XmlAssociationOverride associationOverride);
}