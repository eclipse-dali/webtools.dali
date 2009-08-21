/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.jpa2.context.persistence;

import org.eclipse.jpt.core.internal.context.persistence.AbstractGenericMappingFileRef;
import org.eclipse.jpt.core.jpa2.context.persistence.MappingFileRef2_0;
import org.eclipse.jpt.core.jpa2.context.persistence.PersistenceUnit2_0;
import org.eclipse.jpt.core.resource.persistence.XmlMappingFileRef;

public class GenericMappingFileRef2_0
	extends AbstractGenericMappingFileRef
	implements MappingFileRef2_0
{
	public GenericMappingFileRef2_0(PersistenceUnit2_0 parent, XmlMappingFileRef xmlMappingFileRef) {
		super(parent, xmlMappingFileRef);
	}

	public void synchronizeStaticMetaModel() {
		this.getMappingFile().synchronizeStaticMetaModel();
	}

}
