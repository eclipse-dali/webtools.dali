/*******************************************************************************
 * Copyright (c) 2008, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.persistence;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.window.Window;
import org.eclipse.jpt.common.core.resource.ProjectResourceLocator;
import org.eclipse.jpt.common.ui.internal.widgets.AddRemoveListPane;
import org.eclipse.jpt.common.ui.internal.widgets.AddRemovePane.Adapter;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.internal.model.value.ItemPropertyListValueModelAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.ListAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.SimpleCollectionValueModel;
import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.common.utility.model.value.CollectionValueModel;
import org.eclipse.jpt.common.utility.model.value.ListValueModel;
import org.eclipse.jpt.common.utility.model.value.ModifiableCollectionValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.persistence.MappingFileRef;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpa.core.resource.ResourceMappingFile;
import org.eclipse.jpt.jpa.ui.internal.JpaHelpContextIds;
import org.eclipse.jpt.jpa.ui.internal.JptUiIcons;
import org.eclipse.jpt.jpa.ui.internal.jface.XmlMappingFileViewerFilter;
import org.eclipse.jpt.jpa.ui.internal.plugin.JptJpaUiPlugin;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.dialogs.ElementTreeSelectionDialog;
import org.eclipse.ui.dialogs.ISelectionStatusValidator;
import org.eclipse.ui.model.WorkbenchContentProvider;
import org.eclipse.ui.model.WorkbenchLabelProvider;
import org.eclipse.ui.views.navigator.ResourceComparator;

public abstract class PersistenceUnitMappingFilesComposite
		extends Pane<PersistenceUnit> {


	public PersistenceUnitMappingFilesComposite(
			Pane<? extends PersistenceUnit> parentPane, Composite parent) {

		super(parentPane, parent);
	}

	protected void addMappingFilesList(Composite container) {
		// List pane
		new AddRemoveListPane<PersistenceUnit, MappingFileRef>(
				this,
				container,
				buildAdapter(),
				buildItemListHolder(),
				buildSelectedMappingFileRefsModel(),
				buildLabelProvider(),
				JpaHelpContextIds.PERSISTENCE_XML_GENERAL);
	}
	
	/**
	 * Prompts a dialog showing a tree structure of the source paths where the
	 * only files shown are JPA mapping descriptors file. The XML file has to be
	 * an XML file with the root tag: {@code <entity-mappings>}.
	 *
	 * @param listSelectionModel The selection model used to select the new files
	 */
	private MappingFileRef addJPAMappingDescriptor() {
		
		ElementTreeSelectionDialog dialog = new ElementTreeSelectionDialog(
				getShell(),
				new WorkbenchLabelProvider(),
				new WorkbenchContentProvider());
		
		dialog.setHelpAvailable(false);
		dialog.setValidator(buildValidator());
		dialog.setTitle(JptUiPersistenceMessages.PersistenceUnitMappingFilesComposite_mappingFileDialog_title);
		dialog.setMessage(JptUiPersistenceMessages.PersistenceUnitMappingFilesComposite_mappingFileDialog_message);
		dialog.addFilter(new XmlMappingFileViewerFilter(getSubject().getJpaProject(), ResourceMappingFile.Root.CONTENT_TYPE));
		dialog.setInput(getSubject().getJpaProject().getProject());
		dialog.setComparator(new ResourceComparator(ResourceComparator.NAME));

		dialog.setBlockOnOpen(true);
		if (dialog.open() == Window.OK) {
			for (Object result : dialog.getResult()) {
				IFile file = (IFile) result;
				IProject project = file.getProject();
				ProjectResourceLocator locator = (ProjectResourceLocator) project.getAdapter(ProjectResourceLocator.class);
				IPath runtimePath = locator.getRuntimePath(file.getFullPath());
				String fileName = runtimePath.toPortableString();
				if (mappingFileRefExists(fileName)) {
					continue;
				}
				return getSubject().addSpecifiedMappingFileRef(fileName);
			}
		}
		return null;
	}
	
	private Adapter<MappingFileRef> buildAdapter() {
		return new AddRemoveListPane.AbstractAdapter<MappingFileRef>() {
			public MappingFileRef addNewItem() {
				return addJPAMappingDescriptor();
			}

			@Override
			public PropertyValueModel<Boolean> buildRemoveButtonEnabledModel(CollectionValueModel<MappingFileRef> selectedItemsModel) {
				return buildSingleSelectedItemEnabledModel(selectedItemsModel);
			}

			public void removeSelectedItems(CollectionValueModel<MappingFileRef> selectedItemsModel) {
				//assume only 1 item since remove button is disabled otherwise
				MappingFileRef mappingFileRef = selectedItemsModel.iterator().next();
				getSubject().removeSpecifiedMappingFileRef(mappingFileRef);
			}
		};
	}
	
	private ListValueModel<MappingFileRef> buildItemListHolder() {
		return new ItemPropertyListValueModelAdapter<MappingFileRef>(
				buildListHolder(),
				MappingFileRef.FILE_NAME_PROPERTY);
	}
	
	private ILabelProvider buildLabelProvider() {
		return new LabelProvider() {
			@Override
			public Image getImage(Object element) {
				return JptJpaUiPlugin.instance().getImage(JptUiIcons.MAPPING_FILE_REF);
			}
			
			@Override
			public String getText(Object element) {
				MappingFileRef mappingFileRef = (MappingFileRef) element;
				String name = mappingFileRef.getFileName();
				
				if (name == null) {
					name = JptUiPersistenceMessages.PersistenceUnitMappingFilesComposite_ormNoName;
				}
				
				return name;
			}
		};
	}
	
	private ListValueModel<MappingFileRef> buildListHolder() {
		return new ListAspectAdapter<PersistenceUnit, MappingFileRef>(
				getSubjectHolder(), PersistenceUnit.SPECIFIED_MAPPING_FILE_REFS_LIST) {
			
			@Override
			protected ListIterable<MappingFileRef> getListIterable() {
				return this.subject.getSpecifiedMappingFileRefs();
			}
			
			@Override
			protected int size_() {
				return this.subject.getSpecifiedMappingFileRefsSize();
			}
		};
	}

	protected ModifiableCollectionValueModel<MappingFileRef> buildSelectedMappingFileRefsModel() {
		return new SimpleCollectionValueModel<MappingFileRef>();
	}
	
	private boolean mappingFileRefExists(String fileName) {
		for (MappingFileRef mappingFileRef : getSubject().getSpecifiedMappingFileRefs()) {
			if( mappingFileRef.getFileName().equals(fileName)) {
				return true;
			}
		}
		return false;
	}
	
	private ISelectionStatusValidator buildValidator() {
		return new ISelectionStatusValidator() {
			public IStatus validate(Object[] selection) {

				if (selection.length == 0) {
					return JptJpaUiPlugin.instance().buildErrorStatus();
				}

				for (Object item : selection) {
					if (item instanceof IFolder) {
						return JptJpaUiPlugin.instance().buildErrorStatus();
					}
				}

				return JptJpaUiPlugin.instance().buildOKStatus();
			}
		};
	}
}
