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

import org.eclipse.jpt.core.internal.context.persistence.AbstractImpliedMappingFileRef;
import org.eclipse.jpt.core.jpa2.context.persistence.MappingFileRef2_0;
import org.eclipse.jpt.core.jpa2.context.persistence.PersistenceUnit2_0;

public class ImpliedMappingFileRef2_0
	extends AbstractImpliedMappingFileRef
	implements MappingFileRef2_0
{
	public ImpliedMappingFileRef2_0(PersistenceUnit2_0 parent, String resourceFileName) {
		super(parent, resourceFileName);
	}

	public void synchronizeStaticMetaModel() {
		this.getMappingFile().synchronizeStaticMetaModel();
	}

}
