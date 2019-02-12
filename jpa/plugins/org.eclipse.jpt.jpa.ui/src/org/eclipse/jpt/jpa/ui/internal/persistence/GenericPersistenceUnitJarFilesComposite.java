/*******************************************************************************
 * Copyright (c) 2009, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.persistence;

import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.swt.widgets.Composite;

public class GenericPersistenceUnitJarFilesComposite
	extends PersistenceUnitJarFilesComposite
{
	public GenericPersistenceUnitJarFilesComposite(
			Pane<? extends PersistenceUnit> parent,
			Composite parentComposite) {
		super(parent, parentComposite);
	}
}
