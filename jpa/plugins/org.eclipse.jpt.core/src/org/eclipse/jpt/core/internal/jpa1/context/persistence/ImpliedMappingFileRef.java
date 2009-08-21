/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.jpa1.context.persistence;

import org.eclipse.jpt.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.core.internal.context.persistence.AbstractImpliedMappingFileRef;

public class ImpliedMappingFileRef
	extends AbstractImpliedMappingFileRef
{
	public ImpliedMappingFileRef(PersistenceUnit parent, String resourceFileName) {
		super(parent, resourceFileName);
	}
}
