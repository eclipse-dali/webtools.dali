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
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.Path;
import org.eclipse.jpt.core.internal.operations.OrmFileCreationOperation;
import org.eclipse.jpt.core.resource.orm.AccessType;
import org.eclipse.jpt.core.resource.orm.OrmFactory;
import org.eclipse.jpt.core.resource.orm.XmlPersistenceUnitDefaults;
import org.eclipse.jpt.core.resource.orm.XmlPersistenceUnitMetadata;
import org.eclipse.jpt.eclipselink.core.internal.resource.orm.EclipseLinkOrmXmlResourceProvider;
import org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmFactory;
import org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmXmlResource;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlEntityMappings;
import org.eclipse.wst.common.frameworks.datamodel.IDataModel;

public class EclipseLinkOrmFileCreationOperation extends OrmFileCreationOperation
{
	public EclipseLinkOrmFileCreationOperation(IDataModel dataModel) {
		super(dataModel);
	}
	
	
	@Override
	protected void createMappingFile(IFolder folder) {
		String filePath = getDataModel().getStringProperty(FILE_PATH);
		IFile file = folder.getFile(new Path(filePath));
		final EclipseLinkOrmXmlResourceProvider modelProvider =
			EclipseLinkOrmXmlResourceProvider.getXmlResourceProvider(file);
		
		modelProvider.modify(new Runnable() {
				public void run() {
					EclipseLinkOrmXmlResource ormResource = modelProvider.getXmlResource();
					
					XmlEntityMappings entityMappings = EclipseLinkOrmFactory.eINSTANCE.createXmlEntityMappings();
					entityMappings.setVersion("1.0"); //$NON-NLS-1$
					ormResource.getContents().add(entityMappings);
					
					AccessType defaultAccess = (AccessType) getDataModel().getProperty(DEFAULT_ACCESS); 
					if (defaultAccess != null) {
						XmlPersistenceUnitMetadata puMetadata = OrmFactory.eINSTANCE.createXmlPersistenceUnitMetadata();
						entityMappings.setPersistenceUnitMetadata(puMetadata);
						XmlPersistenceUnitDefaults puDefaults = OrmFactory.eINSTANCE.createXmlPersistenceUnitDefaults();
						puMetadata.setPersistenceUnitDefaults(puDefaults);
						puDefaults.setAccess(defaultAccess);
					}
				}
			});
	}
}
