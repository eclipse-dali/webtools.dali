/*******************************************************************************
 *  Copyright (c) 2013  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License 2.0, which accompanies this distribution
 *  and is available at https://www.eclipse.org/legal/epl-2.0/
 *
 *  SPDX-License-Identifier: EPL-2.0
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.eclipselink.core.internal.operations;

import org.eclipse.core.resources.IFile;
import org.eclipse.jpt.common.core.internal.operations.AbstractJptFileCreationOperation;
import org.eclipse.jpt.common.core.resource.xml.JptXmlResourceProvider;
import org.eclipse.jpt.jaxb.eclipselink.core.internal.resource.oxm.OxmXmlResourceProvider;
import org.eclipse.wst.common.frameworks.datamodel.IDataModel;

public class OxmFileCreationOperation
		extends AbstractJptFileCreationOperation
		implements OxmFileCreationDataModelProperties {
	
	public OxmFileCreationOperation(IDataModel dataModel) {
		super(dataModel);
	}
	
	
	@Override
	protected JptXmlResourceProvider getXmlResourceProvider(IFile file) {
		return OxmXmlResourceProvider.getXmlResourceProvider(file);
	}
}
