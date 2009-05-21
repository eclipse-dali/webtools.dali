/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.operations;

import org.eclipse.core.resources.IFile;
import org.eclipse.jpt.core.internal.operations.OrmFileCreationOperation;
import org.eclipse.jpt.core.resource.AbstractXmlResourceProvider;
import org.eclipse.jpt.core.resource.orm.XmlPersistenceUnitMetadata;
import org.eclipse.jpt.eclipselink.core.internal.resource.orm.EclipseLinkOrmXmlResourceProvider;
import org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmFactory;
import org.eclipse.wst.common.frameworks.datamodel.IDataModel;

public class EclipseLinkOrmFileCreationOperation extends OrmFileCreationOperation
{
	public EclipseLinkOrmFileCreationOperation(IDataModel dataModel) {
		super(dataModel);
	}
	
	@Override
	protected AbstractXmlResourceProvider getXmlResourceProvider(IFile file) {
		return EclipseLinkOrmXmlResourceProvider.getXmlResourceProvider(file);
	}
	
	@Override
	protected XmlPersistenceUnitMetadata createXmlPersistenceUnitMetadata() {
		return EclipseLinkOrmFactory.eINSTANCE.createXmlPersistenceUnitMetadata();
	}
}
