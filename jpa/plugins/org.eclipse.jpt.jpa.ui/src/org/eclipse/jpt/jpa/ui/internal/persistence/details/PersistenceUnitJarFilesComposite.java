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

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.window.Window;
import org.eclipse.jpt.common.ui.internal.widgets.AddRemoveListPane;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.ui.internal.widgets.AddRemovePane.Adapter;
import org.eclipse.jpt.common.utility.internal.iterables.ListIterable;
import org.eclipse.jpt.common.utility.internal.model.value.ItemPropertyListValueModelAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.ListAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.swing.ObjectListSelectionModel;
import org.eclipse.jpt.common.utility.model.value.ListValueModel;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.jpa.core.context.persistence.JarFileRef;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpa.ui.JptJpaUiPlugin;
import org.eclipse.jpt.jpa.ui.internal.JpaHelpContextIds;
import org.eclipse.jpt.jpa.ui.internal.JptUiIcons;
import org.eclipse.jpt.jpa.ui.internal.persistence.JptUiPersistenceMessages;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.dialogs.ElementTreeSelectionDialog;

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
public abstract class PersistenceUnitJarFilesComposite 
	extends Pane<PersistenceUnit>
{
	private ModifiablePropertyValueModel<JarFileRef> selectedItemHolder;	

	/**
	 * Creates a new <code>PersistenceUnitJPAMappingDescriptorsComposite</code>.
	 *
	 * @param parentPane The parent pane of this one
	 * @param parent The parent container
	 */
	public PersistenceUnitJarFilesComposite(
			Pane<? extends PersistenceUnit> parentPane,
			Composite parent) {

		super(parentPane, parent, false);
	}

	@Override
	protected void initialize() {
		super.initialize();
		this.selectedItemHolder = buildSelectedItemHolder();
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
			this.selectedItemHolder,
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
				addJarFileRef();
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
			protected ListIterable<JarFileRef> getListIterable() {
				return this.subject.getJarFileRefs();
			}
			
			@Override
			protected int size_() {
				return this.subject.getJarFileRefsSize();
			}
		};
	}
	
	private ModifiablePropertyValueModel<JarFileRef> buildSelectedItemHolder() {
		return new SimplePropertyValueModel<JarFileRef>();
	}
	
	private ILabelProvider buildLabelProvider() {
		return new LabelProvider() {
			@Override
			public Image getImage(Object element) {
				return JptJpaUiPlugin.getImage(JptUiIcons.JAR_FILE_REF);
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
	
	private void addJarFileRef() {
		IProject project = getSubject().getJpaProject().getProject();

		ElementTreeSelectionDialog dialog = new ArchiveFileSelectionDialog(
			getShell(), buildJarFileDeploymentPathCalculator());
		
		dialog.setHelpAvailable(false);
		dialog.setTitle(JptUiPersistenceMessages.PersistenceUnitMappingFilesComposite_jarFileDialog_title);
		dialog.setMessage(JptUiPersistenceMessages.PersistenceUnitMappingFilesComposite_jarFileDialog_message);
		dialog.setInput(project);


		dialog.setBlockOnOpen(true);
		
		if (dialog.open() == Window.OK) {		
			for (Object result : dialog.getResult()) {
				String filePath = (String) result;
				if (jarFileRefExists(filePath)) {
					continue;
				}
				JarFileRef jarFileRef = getSubject().addJarFileRef(filePath);

				this.selectedItemHolder.setValue(jarFileRef);
			}
		}
	}
	
	protected ArchiveFileSelectionDialog.DeploymentPathCalculator buildJarFileDeploymentPathCalculator() {
		return new ArchiveFileSelectionDialog.ModuleDeploymentPathCalculator();
	}
	
	private boolean jarFileRefExists(String fileName) {
		for (JarFileRef each : getSubject().getJarFileRefs()) {
			if (each.getFileName().equals(fileName)) {
				return true;
			}
		}
		return false;
	}
}
