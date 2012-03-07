/*******************************************************************************
 * Copyright (c) 2011, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.wizards.conversion.java;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jpt.common.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.jpa.core.JptJpaCorePlugin;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpa.ui.internal.jface.XmlMappingFileViewerFilter;
import org.eclipse.jpt.jpa.ui.internal.wizards.SelectJpaOrmMappingFileDialog;
import org.eclipse.jpt.jpa.ui.internal.wizards.orm.EmbeddedMappingFileWizard;
import org.eclipse.swt.widgets.Shell;

public abstract class GenericJpaJavaGlobalMetadataConversionWizardPage extends
	JpaJavaGlobalMetadataConversionWizardPage<PersistenceUnit> {

	public GenericJpaJavaGlobalMetadataConversionWizardPage(
			PersistenceUnit persistenceUnit, SimplePropertyValueModel<String> model, String helpContextId) {
		super(persistenceUnit, model, helpContextId);
	}

	@Override
	protected ViewerFilter buildSelectMappingFileDialogViewerFilter() {
		return new XmlMappingFileViewerFilter(this.getJpaProject(), JptJpaCorePlugin.MAPPING_FILE_CONTENT_TYPE);
	}

	@Override
	protected SelectJpaOrmMappingFileDialog buildSelectMappingFileDialog 
	(Shell shell, IProject project, ILabelProvider lp, ITreeContentProvider cp){
		return new SelectJpaOrmMappingFileDialog(shell, project, lp, cp);
	}

	@Override
	protected IPath getMappingFilePath(){
		return EmbeddedMappingFileWizard.createNewMappingFile(
				new StructuredSelection(this.getJpaProject().getProject()), 
				getOrmXmlResourceName());
	}

	@Override
	protected String getDefaultMappingFileRuntimPath() {
		return JptJpaCorePlugin.DEFAULT_ORM_XML_RUNTIME_PATH.toString();
	}
}
