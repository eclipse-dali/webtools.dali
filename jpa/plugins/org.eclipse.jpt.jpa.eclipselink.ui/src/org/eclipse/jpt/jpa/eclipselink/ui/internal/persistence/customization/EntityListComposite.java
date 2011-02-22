/*******************************************************************************
* Copyright (c) 2007, 2011 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
*
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.persistence.customization;

import java.util.ListIterator;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.search.IJavaSearchScope;
import org.eclipse.jdt.core.search.SearchEngine;
import org.eclipse.jdt.ui.IJavaElementSearchConstants;
import org.eclipse.jdt.ui.JavaUI;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.window.Window;
import org.eclipse.jpt.common.ui.internal.util.PaneEnabler;
import org.eclipse.jpt.common.ui.internal.widgets.AddRemoveListPane;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.common.utility.internal.model.value.ListAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.swing.ObjectListSelectionModel;
import org.eclipse.jpt.common.utility.model.value.ListValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.WritablePropertyValueModel;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.customization.Customization;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.customization.Entity;
import org.eclipse.jpt.jpa.eclipselink.ui.JptJpaEclipseLinkUiPlugin;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.EclipseLinkHelpContextIds;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.EclipseLinkUiMessages;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.SelectionDialog;
import org.eclipse.ui.progress.IProgressService;

/**
 *  EntityListComposite
 */
public class EntityListComposite extends Pane<Customization>
{
	private WritablePropertyValueModel<Entity> entityHolder;

	public EntityListComposite(Pane<? extends Customization> parentComposite, Composite parent) {

		super(parentComposite, parent);
	}

	@Override
	protected void initialize() {
		super.initialize();
		this.entityHolder = this.buildEntityHolder();
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
			this.buildEntitiesListHolder(),
			this.entityHolder,
			this.buildEntityLabelProvider(),
			EclipseLinkHelpContextIds.PERSISTENCE_CUSTOMIZATION
		);

		// Entity Customization property pane
		EntityCustomizationPropertyComposite pane = new EntityCustomizationPropertyComposite(
			this,
			this.entityHolder,
			container
		);
		this.installPaneEnabler(this.entityHolder, pane);
	}

	private AddRemoveListPane.Adapter buildEntitiesAdapter() {
		return new AddRemoveListPane.AbstractAdapter() {
			public void addNewItem(ObjectListSelectionModel listSelectionModel) {
				EntityListComposite.this.addEntities(listSelectionModel);
			}

			public void removeSelectedItems(ObjectListSelectionModel listSelectionModel) {
				Customization customization = getSubject();
				for (Object item : listSelectionModel.selectedValues()) {
					Entity entityCustomization = (Entity) item;
					customization.removeEntity(entityCustomization.getName());
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
				this.getSubject().addEntity(entityName);
				int index = CollectionTools.indexOf(this.getSubject().entityNames(), entityName);
				Entity entity = (Entity) listSelectionModel.getListModel().getElementAt(index);
				listSelectionModel.setSelectedValue(entity);
				this.entityHolder.setValue(entity);
			}
		}
	}
	
	private String getEntityName(String fullyQualifiedTypeName) {

		org.eclipse.jpt.jpa.core.context.Entity entity = getSubject().getPersistenceUnit().getEntity(fullyQualifiedTypeName);
		return entity != null ? entity.getName() : null;
	}
	
	private IType chooseEntity() {
		IJavaProject javaProject = getJavaProject();
		IJavaElement[] elements = new IJavaElement[] { javaProject };
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
			JptJpaEclipseLinkUiPlugin.log(e);
			return null;
		}
		typeSelectionDialog.setTitle(EclipseLinkUiMessages.CustomizationEntityListComposite_dialogTitle);
		typeSelectionDialog.setMessage(EclipseLinkUiMessages.CustomizationEntityListComposite_dialogMessage);

		if (typeSelectionDialog.open() == Window.OK) {
			return (IType) typeSelectionDialog.getResult()[0];
		}
		return null;
	}

	private IJavaProject getJavaProject() {
		return getSubject().getJpaProject().getJavaProject();
	}

	private ILabelProvider buildEntityLabelProvider() {
		return new LabelProvider() {
			@Override
			public String getText(Object element) {
				Entity entityCustomization = (Entity) element;
				return entityCustomization.getName();
			}
		};
	}

	private WritablePropertyValueModel<Entity> buildEntityHolder() {
		return new SimplePropertyValueModel<Entity>();
	}

	private ListValueModel<Entity> buildEntitiesListHolder() {
		return new ListAspectAdapter<Customization, Entity>(
				this.getSubjectHolder(), Customization.ENTITIES_LIST) {
			@Override
			protected ListIterator<Entity> listIterator_() {
				return this.subject.entities();
			}
			@Override
			protected int size_() {
				return this.subject.entitiesSize();
			}
		};
	}

	private void installPaneEnabler(WritablePropertyValueModel<Entity> entityHolder,
	                                EntityCustomizationPropertyComposite pane) {

		new PaneEnabler(
			this.buildPaneEnablerHolder(entityHolder),
			pane
		);
	}

	private PropertyValueModel<Boolean> buildPaneEnablerHolder(WritablePropertyValueModel<Entity> entityHolder) {
		return new TransformationPropertyValueModel<Entity, Boolean>(entityHolder) {
			@Override
			protected Boolean transform_(Entity value) {
				return value.entityNameIsValid();
			}
		};
	}
}
