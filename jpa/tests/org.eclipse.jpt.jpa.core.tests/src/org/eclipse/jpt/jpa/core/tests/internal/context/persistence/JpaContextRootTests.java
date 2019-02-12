/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.tests.internal.context.persistence;

import org.eclipse.jpt.common.core.internal.operations.JptFileCreationDataModelProperties;
import org.eclipse.jpt.common.core.resource.xml.JptXmlResource;
import org.eclipse.jpt.jpa.core.context.JpaContextRoot;
import org.eclipse.jpt.jpa.core.internal.operations.PersistenceFileCreationDataModelProvider;
import org.eclipse.jpt.jpa.core.tests.internal.context.ContextModelTestCase;
import org.eclipse.wst.common.frameworks.datamodel.DataModelFactory;
import org.eclipse.wst.common.frameworks.datamodel.IDataModel;

@SuppressWarnings("nls")
public class JpaContextRootTests
	extends ContextModelTestCase
{
	public JpaContextRootTests(String name) {
		super(name);
	}
	
	public void testUpdateAddPersistenceXml() throws Exception {
		deleteResource(getPersistenceXmlResource());
		JpaContextRoot root = getJavaProjectTestHarness().getJpaProject().getContextRoot();
		
		assertFalse(getPersistenceXmlResource().fileExists());
		assertNull(root.getPersistenceXml());
		
		IDataModel config =
			DataModelFactory.createDataModel(new PersistenceFileCreationDataModelProvider());
		config.setProperty(JptFileCreationDataModelProperties.CONTAINER_PATH, 
				getJpaProject().getProject().getFolder("src/META-INF").getFullPath());
		config.getDefaultOperation().execute(null, null);
		
		assertNotNull(root.getPersistenceXml());
	}
	
	public void testUpdateRemovePersistenceXml() throws Exception {
		JptXmlResource pr = getPersistenceXmlResource();
		JpaContextRoot root = getJavaProjectTestHarness().getJpaProject().getContextRoot();
		
		assertNotNull(root.getPersistenceXml());
		
		deleteResource(pr);
		
		assertNull(root.getPersistenceXml());
	}
}
