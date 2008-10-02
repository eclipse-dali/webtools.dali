/*******************************************************************************
* Copyright (c) 2007, 2008 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
*
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.customization;

import java.util.ListIterator;
import org.eclipse.core.resources.IProject;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.search.IJavaSearchScope;
import org.eclipse.jdt.core.search.SearchEngine;
import org.eclipse.jdt.ui.IJavaElementSearchConstants;
import org.eclipse.jdt.ui.JavaUI;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.window.Window;
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.Entity;
import org.eclipse.jpt.core.context.PersistentType;
import org.eclipse.jpt.core.context.TypeMapping;
import org.eclipse.jpt.eclipselink.core.internal.context.customization.Customization;
import org.eclipse.jpt.eclipselink.ui.JptEclipseLinkUiPlugin;
import org.eclipse.jpt.eclipselink.ui.internal.EclipseLinkUiMessages;
import org.eclipse.jpt.ui.internal.util.PaneEnabler;
import org.eclipse.jpt.ui.internal.widgets.AddRemoveListPane;
import org.eclipse.jpt.ui.internal.widgets.Pane;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.model.value.ListAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.TransformationListValueModelAdapter;
import org.eclipse.jpt.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.swing.ObjectListSelectionModel;
import org.eclipse.jpt.utility.model.value.ListValueModel;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.SelectionDialog;
import org.eclipse.ui.progress.IProgressService;

/**
 *  EntityListComposite
 */
public class EntityListComposite extends Pane<Customization>
{
	private WritablePropertyValueModel<EntityCustomizerProperties> entityHolder;

	public EntityListComposite(Pane<Customization> parentComposite, Composite parent) {

		super(parentComposite, parent);
	}

	@Override
	protected void initialize() {
		super.initialize();
		entityHolder = this.buildEntityHolder();
	}

	@Override
	protected void initializeLayout(Composite container) {

		container = this.addTitledGroup(
			container,
			EclipseLinkUiMessages.CustomizationEntityListComposite_groupTitle
		);

		// Entities add/remove list pane
		new AddRemoveListPane<Customization>(
			this,
			container,
			this.buildEntitiesAdapter(),
			this.buildEntityCustomizationListHolder(),
			entityHolder,
			this.buildEntityLabelProvider(),
			null			//		EclipseLinkHelpContextIds.CUSTOMIZATION_ENTITIES
		);

		// Entity Customization property pane
		EntityCustomizationPropertyComposite pane = new EntityCustomizationPropertyComposite(
			this,
			entityHolder,
			container
		);
		this.installPaneEnabler(entityHolder, pane);
	}

	private AddRemoveListPane.Adapter buildEntitiesAdapter() {
		return new AddRemoveListPane.AbstractAdapter() {
			public void addNewItem(ObjectListSelectionModel listSelectionModel) {
				addEntities(listSelectionModel);
			}

			public void removeSelectedItems(ObjectListSelectionModel listSelectionModel) {
				Customization customization = getSubject();
				for (Object item : listSelectionModel.selectedValues()) {
					EntityCustomizerProperties entityCustomization = (EntityCustomizerProperties) item;
					customization.removeEntity(entityCustomization.getEntityName());
				}
			}
		};
	}
	
	private void addEntities(ObjectListSelectionModel listSelectionModel) {

		IType type = this.chooseEntity();

		if (type != null) {
			String entityName = this.getEntityName(type.getFullyQualifiedName());
			if (entityName == null) {
				entityName = type.getElementName();
			}
			
			if( ! this.getSubject().entityExists(entityName)) {
				String entity = this.getSubject().addEntity(entityName);

				int index = CollectionTools.indexOf(this.getSubject().entities(), entityName);
				EntityCustomizerProperties item = (EntityCustomizerProperties) listSelectionModel.getListModel().getElementAt(index);
				entityHolder.setValue(item);
			}
		}
	}
	
	private String getEntityName(String fullyQualifiedTypeName) {

		PersistentType persistentType = getSubject().persistenceUnit().getPersistentType(fullyQualifiedTypeName);

		if (persistentType != null && persistentType.getMappingKey() == MappingKeys.ENTITY_TYPE_MAPPING_KEY) {
			TypeMapping mapping = persistentType.getMapping();
			if (mapping instanceof Entity) {
				return ((Entity)mapping).getName();
			}
		}
		return null;
	}
	
	private IType chooseEntity() {

		IPackageFragmentRoot root = getPackageFragmentRoot();
		if (root == null) {
			return null;
		}
		IJavaElement[] elements = new IJavaElement[] { root.getJavaProject() };
		IJavaSearchScope scope = SearchEngine.createJavaSearchScope(elements);
		IProgressService service = PlatformUI.getWorkbench().getProgressService();
		SelectionDialog typeSelectionDialog;

		try {
			typeSelectionDialog = JavaUI.createTypeDialog(
				getShell(),
				service,
				scope,
				IJavaElementSearchConstants.CONSIDER_CLASSES,
				false,
				""
			);
		}
		catch (JavaModelException e) {
			JptEclipseLinkUiPlugin.log(e);
			return null;
		}
		typeSelectionDialog.setTitle(EclipseLinkUiMessages.CustomizationEntityListComposite_dialogTitle);
		typeSelectionDialog.setMessage(EclipseLinkUiMessages.CustomizationEntityListComposite_dialogMessage);

		if (typeSelectionDialog.open() == Window.OK) {
			return (IType) typeSelectionDialog.getResult()[0];
		}
		return null;
	}

	private IPackageFragmentRoot getPackageFragmentRoot() {
		IProject project = getSubject().getJpaProject().getProject();
		IJavaProject root = JavaCore.create(project);

		try {
			return root.getAllPackageFragmentRoots()[0];
		}
		catch (JavaModelException e) {
			JptEclipseLinkUiPlugin.log(e);
		}
		return null;
	}

	private ILabelProvider buildEntityLabelProvider() {
		return new LabelProvider() {
			@Override
			public String getText(Object element) {
				EntityCustomizerProperties entityCustomization = (EntityCustomizerProperties) element;
				return entityCustomization.getEntityName();
			}
		};
	}

	private WritablePropertyValueModel<EntityCustomizerProperties> buildEntityHolder() {
		return new SimplePropertyValueModel<EntityCustomizerProperties>();
	}

	private ListValueModel<EntityCustomizerProperties> buildEntityCustomizationListHolder() {
		return new TransformationListValueModelAdapter<String, EntityCustomizerProperties>(buildEntitiesListHolder()) {
			@Override
			protected EntityCustomizerProperties transformItem(String item) {
				return new EntityCustomizerProperties(getSubject(), item);
			}
		};
	}

	private ListValueModel<String> buildEntitiesListHolder() {
		return new ListAspectAdapter<Customization, String>(
				this.getSubjectHolder(), Customization.ENTITIES_LIST_PROPERTY) {
			@Override
			protected ListIterator<String> listIterator_() {
				return this.subject.entities();
			}
			@Override
			protected int size_() {
				return this.subject.entitiesSize();
			}
		};
	}

	private void installPaneEnabler(WritablePropertyValueModel<EntityCustomizerProperties> entityHolder,
	                                EntityCustomizationPropertyComposite pane) {

		new PaneEnabler(
			this.buildPaneEnablerHolder(entityHolder),
			pane
		);
	}

	private PropertyValueModel<Boolean> buildPaneEnablerHolder(WritablePropertyValueModel<EntityCustomizerProperties> entityHolder) {
		return new TransformationPropertyValueModel<EntityCustomizerProperties, Boolean>(entityHolder) {
			@Override
			protected Boolean transform_(EntityCustomizerProperties value) {
				return value.entityNameIsValid();
			}
		};
	}
}
