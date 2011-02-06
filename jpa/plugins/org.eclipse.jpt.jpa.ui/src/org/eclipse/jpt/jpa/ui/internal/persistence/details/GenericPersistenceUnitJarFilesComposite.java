/*******************************************************************************
 *  Copyright (c) 2009  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.persistence.details;

import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.swt.widgets.Composite;

public class GenericPersistenceUnitJarFilesComposite
	extends PersistenceUnitJarFilesComposite
{
	public GenericPersistenceUnitJarFilesComposite(
			Pane<? extends PersistenceUnit> parentPane,
			Composite parent) {

		super(parentPane, parent);
	}
}
