/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.context.persistence;

import org.eclipse.jpt.core.context.persistence.Persistence;
import org.eclipse.jpt.core.resource.persistence.XmlPersistenceUnit;

public class GenericPersistenceUnit extends AbstractPersistenceUnit
{
	
	public GenericPersistenceUnit(Persistence parent, XmlPersistenceUnit persistenceUnit) {
		super(parent);
		this.initialize(persistenceUnit);
	}
}
