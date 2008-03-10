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
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jpt.core.context.persistence.MappingFileRef;
import org.eclipse.jpt.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.ui.JptUiPlugin;
import org.eclipse.jpt.ui.internal.JptUiIcons;
import org.eclipse.jpt.ui.internal.persistence.JptUiPersistenceMessages;
import org.eclipse.jpt.ui.internal.widgets.AbstractPane;
import org.eclipse.jpt.ui.internal.widgets.AddRemoveListPane;
import org.eclipse.jpt.ui.internal.widgets.AddRemovePane.Adapter;
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
import org.eclipse.ui.dialogs.FilteredResourcesSelectionDialog;

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
public class PersistenceUnitMappingFilesComposite extends AbstractPane<PersistenceUnit>
{
	/**
	 * Creates a new <code>PersistenceUnitJPAMappingDescriptorsComposite</code>.
	 *
	 * @param parentPane The parent pane of this one
	 * @param parent The parent container
	 */
	public PersistenceUnitMappingFilesComposite(AbstractPane<? extends PersistenceUnit> parentPane,
	                                                     Composite parent) {

		super(parentPane, parent);
	}

	private void addJPAMappingDescriptor(ObjectListSelectionModel listSelectionModel) {

		IProject project = subject().jpaProject().project();
		// TODO: Retrieve the META-INF directory

		FilteredResourcesSelectionDialog dialog = new FilteredResourcesSelectionDialog(
			shell(),
			true,
			project,
			IResource.FILE
		);

		if (dialog.open() == IDialogConstants.OK_ID) {
			int index = subject().specifiedMappingFileRefsSize();

			for (Object result : dialog.getResult()) {
				IFile file = (IFile) result;

				MappingFileRef mappingFileRef = subject().addSpecifiedMappingFileRef(index++);
				mappingFileRef.setFileName(file.getProjectRelativePath().toPortableString());

				listSelectionModel.addSelectedValue(mappingFileRef);
			}
		}
	}

	private Adapter buildAdapter() {
		return new AddRemoveListPane.AbstractAdapter() {
			public void addNewItem(ObjectListSelectionModel listSelectionModel) {
				addJPAMappingDescriptor(listSelectionModel);
			}

			public void removeSelectedItems(ObjectListSelectionModel listSelectionModel) {
				for (Object item : listSelectionModel.selectedValues()) {
					subject().removeSpecifiedMappingFileRef((MappingFileRef) item);
				}
			}
		};
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected Composite buildContainer(Composite parent) {

		GridLayout layout = new GridLayout(1, true);
		layout.marginHeight = 0;
		layout.marginWidth  = 0;
		layout.marginTop    = 0;
		layout.marginLeft   = 0;
		layout.marginBottom = 0;
		layout.marginRight  = 0;

		Composite container = buildPane(parent, layout);
		updateGridData(container);

		return container;
	}

	private ListValueModel<MappingFileRef> buildItemListHolder() {
		return new ItemPropertyListValueModelAdapter<MappingFileRef>(
			buildListHolder(),
			MappingFileRef.FILE_NAME_PROPERTY
		);
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
					name = JptUiPersistenceMessages.PersistenceUnitJPAMappingDescriptorsComposite_ormNoName;
				}

				return name;
			}
		};
	}

	private ListValueModel<MappingFileRef> buildListHolder() {
		return new ListAspectAdapter<PersistenceUnit, MappingFileRef>(getSubjectHolder(), PersistenceUnit.SPECIFIED_MAPPING_FILE_REF_LIST) {
			@Override
			protected ListIterator<MappingFileRef> listIterator_() {
				return subject.specifiedMappingFileRefs();
			}

			@Override
			protected int size_() {
				return subject.specifiedMappingFileRefsSize();
			}
		};
	}

	private WritablePropertyValueModel<MappingFileRef> buildSelectedItemHolder() {
		return new SimplePropertyValueModel<MappingFileRef>();
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void initializeLayout(Composite container) {

		// Description
		buildMultiLineLabel(
			container,
			JptUiPersistenceMessages.PersistenceUnitJPAMappingDescriptorsComposite_description
		);

		// List pane
		new AddRemoveListPane<PersistenceUnit>(
			this,
			container,
			buildAdapter(),
			buildItemListHolder(),
			buildSelectedItemHolder(),
			buildLabelProvider()
		) {
			@Override
			protected Composite buildContainer(Composite parent) {
				parent = super.buildContainer(parent);
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
}
