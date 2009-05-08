/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
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
import org.eclipse.jpt.core.context.persistence.JarFileRef;
import org.eclipse.jpt.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.ui.JptUiPlugin;
import org.eclipse.jpt.ui.internal.JpaHelpContextIds;
import org.eclipse.jpt.ui.internal.JptUiIcons;
import org.eclipse.jpt.ui.internal.jface.JarFileViewerFilter;
import org.eclipse.jpt.ui.internal.persistence.JptUiPersistenceMessages;
import org.eclipse.jpt.ui.internal.util.SWTUtil;
import org.eclipse.jpt.ui.internal.widgets.AddRemoveListPane;
import org.eclipse.jpt.ui.internal.widgets.Pane;
import org.eclipse.jpt.ui.internal.widgets.PostExecution;
import org.eclipse.jpt.ui.internal.widgets.AddRemovePane.Adapter;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.model.value.ItemPropertyListValueModelAdapter;
import org.eclipse.jpt.utility.internal.model.value.ListAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.swing.ObjectListSelectionModel;
import org.eclipse.jpt.utility.model.value.ListValueModel;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.dialogs.ElementTreeSelectionDialog;
import org.eclipse.ui.dialogs.ISelectionStatusValidator;
import org.eclipse.ui.model.WorkbenchContentProvider;
import org.eclipse.ui.model.WorkbenchLabelProvider;
import org.eclipse.ui.views.navigator.ResourceComparator;

/**
 * Here the layout of this pane:
 * <pre>
 * -----------------------------------------------------------------------------
 * |                                                                           |
 * | Description                                                               |
 * |                                                                           |
 * | ------------------------------------------------------------------------- |
 * | |                                                                       | |
 * | | AddRemoveListPane                                                     | |
 * | |                                                                       | |
 * | ------------------------------------------------------------------------- |
 * -----------------------------------------------------------------------------</pre>
 *
 * @see PersistenceUnit
 * @see PersistenceUnitGeneralComposite - The parent container
 * @see AddRemoveListPane
 *
 * @version 2.0
 * @since 2.0
 */
public class PersistenceUnitJarFilesComposite extends Pane<PersistenceUnit>
{
	/**
	 * Creates a new <code>PersistenceUnitJPAMappingDescriptorsComposite</code>.
	 *
	 * @param parentPane The parent pane of this one
	 * @param parent The parent container
	 */
	public PersistenceUnitJarFilesComposite(Pane<? extends PersistenceUnit> parentPane,
	                                            Composite parent) {

		super(parentPane, parent, false);
	}

	
	
	@Override
	protected void initializeLayout(Composite container) {
		addJarFilesList(container);
	}
	
	protected void addJarFilesList(Composite container) {
		// List pane
		new AddRemoveListPane<PersistenceUnit>(
			this,
			container,
			this.buildAdapter(),
			this.buildItemListHolder(),
			this.buildSelectedItemHolder(),
			this.buildLabelProvider(),
			JpaHelpContextIds.PERSISTENCE_XML_GENERAL
		) {
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
	
	private void updateGridData(Composite container) {
		GridData gridData = new GridData();
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace   = true;
		gridData.horizontalAlignment       = SWT.FILL;
		gridData.verticalAlignment         = SWT.FILL;
		container.setLayoutData(gridData);
	}
	
	private Adapter buildAdapter() {
		return new AddRemoveListPane.AbstractAdapter() {
			public void addNewItem(ObjectListSelectionModel listSelectionModel) {
				addJarFileRef(listSelectionModel);
			}
			
			public void removeSelectedItems(ObjectListSelectionModel listSelectionModel) {
				for (Object item : listSelectionModel.selectedValues()) {
					getSubject().removeJarFileRef((JarFileRef) item);
				}
			}
		};
	}
	
	private ListValueModel<JarFileRef> buildItemListHolder() {
		return new ItemPropertyListValueModelAdapter<JarFileRef>(
			buildListHolder(),
			JarFileRef.FILE_NAME_PROPERTY
		);
	}
	
	private ListValueModel<JarFileRef> buildListHolder() {
		return new ListAspectAdapter<PersistenceUnit, JarFileRef>(getSubjectHolder(), PersistenceUnit.JAR_FILE_REFS_LIST) {
			@Override
			protected ListIterator<JarFileRef> listIterator_() {
				return this.subject.jarFileRefs();
			}
			
			@Override
			protected int size_() {
				return this.subject.jarFileRefsSize();
			}
		};
	}
	
	private WritablePropertyValueModel<JarFileRef> buildSelectedItemHolder() {
		return new SimplePropertyValueModel<JarFileRef>();
	}
	
	private ILabelProvider buildLabelProvider() {
		return new LabelProvider() {
			@Override
			public Image getImage(Object element) {
				return JptUiPlugin.getImage(JptUiIcons.JAR_FILE_REF);
			}
			
			@Override
			public String getText(Object element) {
				JarFileRef jarFileRef = (JarFileRef) element;
				String name = jarFileRef.getFileName();
				
				if (name == null) {
					name = JptUiPersistenceMessages.PersistenceUnitJarFilesComposite_noFileName;
				}
				
				return name;
			}
		};
	}
	
	private void addJarFileRef(ObjectListSelectionModel listSelectionModel) {
		IProject project = getSubject().getJpaProject().getProject();

		ElementTreeSelectionDialog dialog = new ElementTreeSelectionDialog(
			getShell(),
			new WorkbenchLabelProvider(),
			new WorkbenchContentProvider()
		);

		dialog.setHelpAvailable(false);
		dialog.setValidator(buildValidator());
		dialog.setTitle(JptUiPersistenceMessages.PersistenceUnitMappingFilesComposite_jarFileDialog_title);
		dialog.setMessage(JptUiPersistenceMessages.PersistenceUnitMappingFilesComposite_jarFileDialog_message);
		dialog.addFilter(new JarFileViewerFilter());
		dialog.setInput(project);
		dialog.setComparator(new ResourceComparator(ResourceComparator.NAME));

		SWTUtil.show(
			dialog,
			buildSelectionDialogPostExecution(listSelectionModel)
		);
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
	
	private PostExecution<ElementTreeSelectionDialog> buildSelectionDialogPostExecution(
			final ObjectListSelectionModel listSelectionModel) {
		return new PostExecution<ElementTreeSelectionDialog>() {
			public void execute(ElementTreeSelectionDialog dialog) {
				if (dialog.getReturnCode() == IDialogConstants.CANCEL_ID) {
					return;
				}
				
				for (Object result : dialog.getResult()) {
					IFile file = (IFile) result;
					// TODO - move to deploy path location
					IPath filePath = file.getProjectRelativePath();
					String fileName = filePath.toPortableString();
					if (jarFileRefExists(fileName)) {
						continue;
					}
					JarFileRef jarFileRef = getSubject().addJarFileRef();
					jarFileRef.setFileName(fileName);

					listSelectionModel.addSelectedValue(jarFileRef);
				}
			}
		};
	}
	
	private boolean jarFileRefExists(String fileName) {
		for (JarFileRef each : CollectionTools.iterable(getSubject().jarFileRefs())) {
			if (each.getFileName().equals(fileName)) {
				return true;
			}
		}
		return false;
	}
}
