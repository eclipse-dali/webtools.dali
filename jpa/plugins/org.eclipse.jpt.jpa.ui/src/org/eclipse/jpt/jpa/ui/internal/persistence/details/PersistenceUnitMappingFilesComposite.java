/*******************************************************************************
 * Copyright (c) 2008, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.persistence.details;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.window.Window;
import org.eclipse.jpt.common.core.resource.ProjectResourceLocator;
import org.eclipse.jpt.common.ui.internal.widgets.AddRemoveListPane;
import org.eclipse.jpt.common.ui.internal.widgets.AddRemovePane.Adapter;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.internal.iterables.ListIterable;
import org.eclipse.jpt.common.utility.internal.model.value.ItemPropertyListValueModelAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.ListAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.swing.ObjectListSelectionModel;
import org.eclipse.jpt.common.utility.model.value.ListValueModel;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.jpa.core.JptJpaCorePlugin;
import org.eclipse.jpt.jpa.core.context.persistence.MappingFileRef;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpa.ui.JptJpaUiPlugin;
import org.eclipse.jpt.jpa.ui.internal.JpaHelpContextIds;
import org.eclipse.jpt.jpa.ui.internal.JptUiIcons;
import org.eclipse.jpt.jpa.ui.internal.jface.XmlMappingFileViewerFilter;
import org.eclipse.jpt.jpa.ui.internal.persistence.JptUiPersistenceMessages;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.dialogs.ElementTreeSelectionDialog;
import org.eclipse.ui.dialogs.ISelectionStatusValidator;
import org.eclipse.ui.model.WorkbenchContentProvider;
import org.eclipse.ui.model.WorkbenchLabelProvider;
import org.eclipse.ui.views.navigator.ResourceComparator;

@SuppressWarnings("nls")
public abstract class PersistenceUnitMappingFilesComposite
		extends Pane<PersistenceUnit> {

	private ModifiablePropertyValueModel<MappingFileRef> selectedItemHolder;

	public PersistenceUnitMappingFilesComposite(
			Pane<? extends PersistenceUnit> parentPane, Composite parent) {

		super(parentPane, parent);
	}

	@Override
	protected void initialize() {
		super.initialize();
		this.selectedItemHolder = buildSelectedItemHolder();
	}


	protected void addMappingFilesList(Composite container) {
		// List pane
		new AddRemoveListPane<PersistenceUnit>(
				this,
				container,
				buildAdapter(),
				buildItemListHolder(),
				this.selectedItemHolder,
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
	private void addJPAMappingDescriptor() {

		ElementTreeSelectionDialog dialog = new ElementTreeSelectionDialog(
				getShell(),
				new WorkbenchLabelProvider(),
				new WorkbenchContentProvider());

		dialog.setHelpAvailable(false);
		dialog.setValidator(buildValidator());
		dialog.setTitle(JptUiPersistenceMessages.PersistenceUnitMappingFilesComposite_mappingFileDialog_title);
		dialog.setMessage(JptUiPersistenceMessages.PersistenceUnitMappingFilesComposite_mappingFileDialog_message);
		dialog.addFilter(new XmlMappingFileViewerFilter(getSubject().getJpaProject(), JptJpaCorePlugin.MAPPING_FILE_CONTENT_TYPE));
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
				MappingFileRef mappingFileRef = getSubject().addSpecifiedMappingFileRef(fileName);
				this.selectedItemHolder.setValue(mappingFileRef);
			}
		}
	}

	private Adapter buildAdapter() {
		return new AddRemoveListPane.AbstractAdapter() {
			public void addNewItem(ObjectListSelectionModel listSelectionModel) {
				addJPAMappingDescriptor();
			}

			public void removeSelectedItems(ObjectListSelectionModel listSelectionModel) {
				for (Object item : listSelectionModel.selectedValues()) {
					getSubject().removeSpecifiedMappingFileRef((MappingFileRef) item);
				}
			}
		};
	}

	@Override
	protected Composite addContainer(Composite parent) {

		GridLayout layout = new GridLayout(1, true);
		layout.marginHeight = 0;
		layout.marginWidth  = 0;
		layout.marginTop    = 0;
		layout.marginLeft   = 0;
		layout.marginBottom = 0;
		layout.marginRight  = 0;

		Composite container = addPane(parent, layout);
		updateGridData(container);

		return container;
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
				return JptJpaUiPlugin.getImage(JptUiIcons.MAPPING_FILE_REF);
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

	private ModifiablePropertyValueModel<MappingFileRef> buildSelectedItemHolder() {
		return new SimplePropertyValueModel<MappingFileRef>();
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
					return new Status(IStatus.ERROR, JptJpaUiPlugin.PLUGIN_ID, "");
				}

				for (Object item : selection) {
					if (item instanceof IFolder) {
						return new Status(IStatus.ERROR, JptJpaUiPlugin.PLUGIN_ID, "");
					}
				}

				return new Status(IStatus.OK, JptJpaUiPlugin.PLUGIN_ID, "");
			}
		};
	}

	private void updateGridData(Composite container) {

		GridData gridData = new GridData();
		gridData.minimumHeight             = 150;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace   = true;
		gridData.horizontalAlignment       = SWT.FILL;
		gridData.verticalAlignment         = SWT.FILL;
		container.setLayoutData(gridData);
	}
}