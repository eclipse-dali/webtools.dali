/*******************************************************************************
 * Copyright (c) 2008, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.persistence.details;

import java.util.ListIterator;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.core.context.persistence.MappingFileRef;
import org.eclipse.jpt.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.ui.JptUiPlugin;
import org.eclipse.jpt.ui.internal.JpaHelpContextIds;
import org.eclipse.jpt.ui.internal.JptUiIcons;
import org.eclipse.jpt.ui.internal.jface.XmlMappingFileViewerFilter;
import org.eclipse.jpt.ui.internal.persistence.JptUiPersistenceMessages;
import org.eclipse.jpt.ui.internal.util.SWTUtil;
import org.eclipse.jpt.ui.internal.widgets.AddRemoveListPane;
import org.eclipse.jpt.ui.internal.widgets.AddRemovePane.Adapter;
import org.eclipse.jpt.ui.internal.widgets.Pane;
import org.eclipse.jpt.ui.internal.widgets.PostExecution;
import org.eclipse.jpt.utility.internal.model.value.ItemPropertyListValueModelAdapter;
import org.eclipse.jpt.utility.internal.model.value.ListAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.swing.ObjectListSelectionModel;
import org.eclipse.jpt.utility.model.value.ListValueModel;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;
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
	
	public PersistenceUnitMappingFilesComposite(
			Pane<? extends PersistenceUnit> parentPane, Composite parent) {
		
		super(parentPane, parent);
	}
	
	
	protected void addMappingFilesList(Composite container) {
		// List pane
		new AddRemoveListPane<PersistenceUnit>(
				this,
				container,
				buildAdapter(),
				buildItemListHolder(),
				buildSelectedItemHolder(),
				buildLabelProvider(),
				JpaHelpContextIds.PERSISTENCE_XML_GENERAL) {
			
			@Override
			protected Composite addContainer(Composite parent) {
				parent = super.addContainer(parent);
				updateGridData(parent);
				return parent;
			}
			
			@Override
			protected void initializeLayout(Composite container) {
				super.initializeLayout(container);
				updateGridData(getContainer());
			}
		};
	}
	
	/**
	 * Prompts a dialog showing a tree structure of the source paths where the
	 * only files shown are JPA mapping descriptors file. The XML file has to be
	 * an XML file with the root tag: {@code <entity-mappings>}.
	 *
	 * @param listSelectionModel The selection model used to select the new files
	 */
	private void addJPAMappingDescriptor(ObjectListSelectionModel listSelectionModel) {
		
		IProject project = getSubject().getJpaProject().getProject();
		
		ElementTreeSelectionDialog dialog = new ElementTreeSelectionDialog(
				getShell(),
				new WorkbenchLabelProvider(),
				new WorkbenchContentProvider());
		
		dialog.setHelpAvailable(false);
		dialog.setValidator(buildValidator());
		dialog.setTitle(JptUiPersistenceMessages.PersistenceUnitMappingFilesComposite_mappingFileDialog_title);
		dialog.setMessage(JptUiPersistenceMessages.PersistenceUnitMappingFilesComposite_mappingFileDialog_message);
		dialog.addFilter(new XmlMappingFileViewerFilter(getSubject().getJpaProject()));
		dialog.setInput(project);
		dialog.setComparator(new ResourceComparator(ResourceComparator.NAME));

		SWTUtil.show(
				dialog,
				buildSelectionDialogPostExecution(listSelectionModel));
	}
	
	private Adapter buildAdapter() {
		return new AddRemoveListPane.AbstractAdapter() {
			public void addNewItem(ObjectListSelectionModel listSelectionModel) {
				addJPAMappingDescriptor(listSelectionModel);
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
				return JptUiPlugin.getImage(JptUiIcons.MAPPING_FILE_REF);
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
			protected ListIterator<MappingFileRef> listIterator_() {
				return this.subject.specifiedMappingFileRefs();
			}
			
			@Override
			protected int size_() {
				return this.subject.specifiedMappingFileRefsSize();
			}
		};
	}
	
	private WritablePropertyValueModel<MappingFileRef> buildSelectedItemHolder() {
		return new SimplePropertyValueModel<MappingFileRef>();
	}
	
	private PostExecution<ElementTreeSelectionDialog> buildSelectionDialogPostExecution(
			final ObjectListSelectionModel listSelectionModel) {
		
		return new PostExecution<ElementTreeSelectionDialog>() {
			
			public void execute(ElementTreeSelectionDialog dialog) {
				
				if (dialog.getReturnCode() == IDialogConstants.CANCEL_ID) {
					return;
				}
				
				for (Object result : dialog.getResult()) {
					IFile file = (IFile) result;
					IProject project = file.getProject();
					IPath runtimePath = JptCorePlugin.getResourceLocator(project).getRuntimePath(project, file.getFullPath());
					String fileName = runtimePath.toPortableString();
					if (mappingFileRefExists(fileName)) {
						continue;
					}
					MappingFileRef mappingFileRef = getSubject().addSpecifiedMappingFileRef(fileName);
					
					listSelectionModel.addSelectedValue(mappingFileRef);
				}
			}
		};
	}
	
	private boolean mappingFileRefExists(String fileName) {
		for ( ListIterator<MappingFileRef> i = getSubject().specifiedMappingFileRefs(); i.hasNext(); ) {
			MappingFileRef mappingFileRef = i.next();
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
					return new Status(IStatus.ERROR, JptUiPlugin.PLUGIN_ID, "");
				}

				for (Object item : selection) {
					if (item instanceof IFolder) {
						return new Status(IStatus.ERROR, JptUiPlugin.PLUGIN_ID, "");
					}
				}

				return new Status(IStatus.OK, JptUiPlugin.PLUGIN_ID, "");
			}
		};
	}
	
	private void updateGridData(Composite container) {
		
		GridData gridData = new GridData();
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace   = true;
		gridData.horizontalAlignment       = SWT.FILL;
		gridData.verticalAlignment         = SWT.FILL;
		container.setLayoutData(gridData);
	}
}