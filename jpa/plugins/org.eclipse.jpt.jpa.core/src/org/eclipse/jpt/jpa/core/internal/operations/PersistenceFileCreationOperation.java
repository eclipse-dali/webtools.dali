/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.operations;

import org.eclipse.core.resources.IFile;
import org.eclipse.jpt.common.core.resource.xml.JptXmlResourceProvider;
import org.eclipse.jpt.jpa.core.internal.resource.persistence.PersistenceXmlResourceProvider;
import org.eclipse.wst.common.frameworks.datamodel.IDataModel;

public class PersistenceFileCreationOperation
	extends AbstractJpaFileCreationOperation
	implements PersistenceFileCreationDataModelProperties
{
	public PersistenceFileCreationOperation(IDataModel dataModel) {
		super(dataModel);
	}
	
	
	@Override
	protected JptXmlResourceProvider getXmlResourceProvider(IFile file) {
		return PersistenceXmlResourceProvider.getXmlResourceProvider(file);
	}
}
