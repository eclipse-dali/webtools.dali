/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.operations;

import org.eclipse.core.resources.IFile;
import org.eclipse.jpt.common.core.resource.xml.JptXmlResourceProvider;
import org.eclipse.jpt.jpa.core.internal.operations.OrmFileCreationOperation;
import org.eclipse.jpt.jpa.eclipselink.core.internal.resource.orm.EclipseLinkOrmXmlResourceProvider;
import org.eclipse.wst.common.frameworks.datamodel.IDataModel;

public class EclipseLinkOrmFileCreationOperation extends OrmFileCreationOperation
{
	public EclipseLinkOrmFileCreationOperation(IDataModel dataModel) {
		super(dataModel);
	}
	
	
	@Override
	protected JptXmlResourceProvider getXmlResourceProvider(IFile file) {
		return EclipseLinkOrmXmlResourceProvider.getXmlResourceProvider(file);
	}
}
