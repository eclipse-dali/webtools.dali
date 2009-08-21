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
import org.eclipse.jpt.core.internal.context.persistence.AbstractGenericMappingFileRef;
import org.eclipse.jpt.core.resource.persistence.XmlMappingFileRef;

public class GenericMappingFileRef
	extends AbstractGenericMappingFileRef
{
	public GenericMappingFileRef(PersistenceUnit parent, XmlMappingFileRef xmlMappingFileRef) {
		super(parent, xmlMappingFileRef);
	}
}
