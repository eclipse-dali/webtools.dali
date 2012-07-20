/*******************************************************************************
 * Copyright (c) 2009, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.operations;

import org.eclipse.core.resources.IFile;
import org.eclipse.jpt.jpa.core.internal.resource.persistence.PersistenceXmlResourceProvider;
import org.eclipse.jpt.jpa.core.resource.xml.JpaXmlResourceProvider;
import org.eclipse.wst.common.frameworks.datamodel.IDataModel;

public class PersistenceFileCreationOperation
	extends AbstractJpaFileCreationOperation
	implements PersistenceFileCreationDataModelProperties
{
	public PersistenceFileCreationOperation(IDataModel dataModel) {
		super(dataModel);
	}
	
	
	@Override
	protected JpaXmlResourceProvider getXmlResourceProvider(IFile file) {
		return PersistenceXmlResourceProvider.getXmlResourceProvider(file);
	}
}
