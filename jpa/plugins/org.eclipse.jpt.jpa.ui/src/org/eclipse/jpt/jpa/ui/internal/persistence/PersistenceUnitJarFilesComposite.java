/*******************************************************************************
 * Copyright (c) 2008, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.persistence;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.window.Window;
import org.eclipse.jpt.common.ui.internal.jface.ResourceManagerLabelProvider;
import org.eclipse.jpt.common.ui.internal.widgets.AddRemoveListPane;
import org.eclipse.jpt.common.ui.internal.widgets.AddRemovePane.Adapter;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.internal.model.value.ItemPropertyListValueModelAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.ListAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.SimpleCollectionValueModel;
import org.eclipse.jpt.common.utility.internal.transformer.TransformerAdapter;
import org.eclipse.jpt.common.utility.internal.transformer.TransformerTools;
import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.common.utility.model.value.CollectionValueModel;
import org.eclipse.jpt.common.utility.model.value.ListValueModel;
import org.eclipse.jpt.common.utility.model.value.ModifiableCollectionValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.transformer.Transformer;
import org.eclipse.jpt.jpa.core.context.persistence.JarFileRef;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpa.ui.JptJpaUiImages;
import org.eclipse.jpt.jpa.ui.internal.JpaHelpContextIds;
import org.eclipse.jpt.jpa.ui.persistence.JptJpaUiPersistenceMessages;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.dialogs.ElementTreeSelectionDialog;

public abstract class PersistenceUnitJarFilesComposite 
	extends Pane<PersistenceUnit>
{
	public PersistenceUnitJarFilesComposite(
			Pane<? extends PersistenceUnit> parent,
			Composite parentComposite) {
		super(parent, parentComposite);
	}

	@Override
	protected void initializeLayout(Composite container) {
		addJarFilesList(container);
	}
	
	protected void addJarFilesList(Composite container) {
		// List pane
		new AddRemoveListPane<PersistenceUnit, JarFileRef>(
			this,
			container,
			this.buildAdapter(),
			this.buildItemListHolder(),
			this.buildSelectedJarFileRefsModel(),
			this.buildJarFileRefLabelProvider(),
			JpaHelpContextIds.PERSISTENCE_XML_GENERAL);
	}

	private Adapter<JarFileRef> buildAdapter() {
		return new AddRemoveListPane.AbstractAdapter<JarFileRef>() {
			public JarFileRef addNewItem() {
				return addJarFileRef();
			}

			@Override
			public PropertyValueModel<Boolean> buildRemoveButtonEnabledModel(CollectionValueModel<JarFileRef> selectedItemsModel) {
				return buildSingleSelectedItemEnabledModel(selectedItemsModel);
			}

			public void removeSelectedItems(CollectionValueModel<JarFileRef> selectedItemsModel) {
				//assume only 1 item since remove button is disabled otherwise
				JarFileRef jarFileRef = selectedItemsModel.iterator().next();
				getSubject().removeJarFileRef(jarFileRef);
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

	protected ModifiableCollectionValueModel<JarFileRef> buildSelectedJarFileRefsModel() {
		return new SimpleCollectionValueModel<JarFileRef>();
	}
	
	private ILabelProvider buildJarFileRefLabelProvider() {
		return new ResourceManagerLabelProvider<JarFileRef>(
				JAR_FILE_REF_LABEL_IMAGE_DESCRIPTOR_TRANSFORMER,
				JAR_FILE_REF_LABEL_TEXT_TRANSFORMER,
				this.getResourceManager()
			);
	}

	private static final Transformer<JarFileRef, ImageDescriptor> JAR_FILE_REF_LABEL_IMAGE_DESCRIPTOR_TRANSFORMER =
			TransformerTools.staticOutputTransformer(JptJpaUiImages.JAR_FILE_REF);

	private static final Transformer<JarFileRef, String> JAR_FILE_REF_LABEL_TEXT_TRANSFORMER = new JarFileRefLabelTextTransformer();
	/* CU private */ static class JarFileRefLabelTextTransformer
		extends TransformerAdapter<JarFileRef, String>
	{
		@Override
		public String transform(JarFileRef jarFileRef) {
			if (jarFileRef == null) {
				return null;
			}
			String name = jarFileRef.getFileName();
			return (name != null) ? name : JptJpaUiPersistenceMessages.PERSISTENCE_UNIT_JAR_FILES_COMPOSITE_NO_FILE_NAME;
		}
	}
	
	JarFileRef addJarFileRef() {
		IProject project = getSubject().getJpaProject().getProject();

		ElementTreeSelectionDialog dialog = new ArchiveFileSelectionDialog(
			getShell(), buildJarFileDeploymentPathCalculator());
		
		dialog.setHelpAvailable(false);
		dialog.setTitle(JptJpaUiPersistenceMessages.PERSISTENCE_UNIT_MAPPING_FILES_COMPOSITE_JAR_FILE_DIALOG_TITLE);
		dialog.setMessage(JptJpaUiPersistenceMessages.PERSISTENCE_UNIT_MAPPING_FILES_COMPOSITE_JAR_FILE_DIALOG_MESSAGE);
		dialog.setInput(project);


		dialog.setBlockOnOpen(true);
		
		if (dialog.open() == Window.OK) {		
			for (Object result : dialog.getResult()) {
				String filePath = (String) result;
				if (jarFileRefExists(filePath)) {
					continue;
				}
				return getSubject().addJarFileRef(filePath);
			}
		}

		return null;
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
