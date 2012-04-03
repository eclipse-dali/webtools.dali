/*******************************************************************************
 * Copyright (c) 2011, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.wizards.orm;

import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jpt.jpa.core.internal.operations.OrmFileCreationDataModelProvider;
import org.eclipse.wst.common.frameworks.datamodel.DataModelFactory;
import org.eclipse.wst.common.frameworks.datamodel.IDataModel;

/**
 * This wizard will create a new mapping file without opening it afterwards.
 * It can be used within other wizards where opening the mapping file is
 * unnecessary, making the process smoother.
 */
public class EmbeddedMappingFileWizard
	extends MappingFileWizard
{
	public static IPath createNewMappingFile(IStructuredSelection selection) {
		return createNewMappingFile(selection, null);
	}

	public static IPath createNewMappingFile(IStructuredSelection selection, String xmlFileName) {
		if (xmlFileName == null) {
			xmlFileName = DEFAULT_XML_FILE_NAME;
		} 
		EmbeddedMappingFileWizard wizard = new EmbeddedMappingFileWizard(DataModelFactory.createDataModel(new OrmFileCreationDataModelProvider()));
		return MappingFileWizard.createMappingFile(selection, xmlFileName, wizard);
	}

	private static final String DEFAULT_XML_FILE_NAME = "orm.xml"; //$NON-NLS-1$


	public EmbeddedMappingFileWizard() {
		super();
	}

	public EmbeddedMappingFileWizard(IDataModel dataModel) {
		super(dataModel);
	}

	@Override
	public final boolean performFinish() {
		return this.createMappingFile();
	}
}
