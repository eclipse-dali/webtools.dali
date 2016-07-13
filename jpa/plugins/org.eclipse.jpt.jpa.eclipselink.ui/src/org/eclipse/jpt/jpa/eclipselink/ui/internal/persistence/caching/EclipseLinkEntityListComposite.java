/*******************************************************************************
 * Copyright (c) 2007, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.persistence.caching;

import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.search.SearchEngine;
import org.eclipse.jdt.ui.IJavaElementSearchConstants;
import org.eclipse.jdt.ui.JavaUI;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.window.Window;
import org.eclipse.jpt.common.ui.internal.widgets.AddRemoveListPane;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.model.value.CollectionValueModelTools;
import org.eclipse.jpt.common.utility.internal.model.value.ListAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyValueModelTools;
import org.eclipse.jpt.common.utility.internal.model.value.SimpleCollectionValueModel;
import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.common.utility.model.value.CollectionValueModel;
import org.eclipse.jpt.common.utility.model.value.ListValueModel;
import org.eclipse.jpt.common.utility.model.value.ModifiableCollectionValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.EclipseLinkCaching;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.EclipseLinkCachingEntity;
import org.eclipse.jpt.jpa.eclipselink.ui.JptJpaEclipseLinkUiMessages;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.EclipseLinkHelpContextIds;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.plugin.JptJpaEclipseLinkUiPlugin;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.SelectionDialog;

/**
 *  EntityListComposite
 */
public class EclipseLinkEntityListComposite<T extends EclipseLinkCaching>
	extends Pane<T>
{
	private ModifiableCollectionValueModel<EclipseLinkCachingEntity> selectedEntitiesModel;
	private PropertyValueModel<EclipseLinkCachingEntity> selectedEntityModel;
	
	public EclipseLinkEntityListComposite(Pane<T> parentComposite, Composite parent) {

		super(parentComposite, parent);
	}

	@Override
	protected void initialize() {
		super.initialize();
		this.selectedEntitiesModel = this.buildSelectedEntitiesModel();
		this.selectedEntityModel = this.buildSelectedEntityModel();
	}

	private ModifiableCollectionValueModel<EclipseLinkCachingEntity> buildSelectedEntitiesModel() {
		return new SimpleCollectionValueModel<>();
	}

	private PropertyValueModel<EclipseLinkCachingEntity> buildSelectedEntityModel() {
		return CollectionValueModelTools.singleElementPropertyValueModel(this.selectedEntitiesModel);
	}

	@Override
	protected Composite addComposite(Composite parent) {
		return this.addTitledGroup(
			parent,
			JptJpaEclipseLinkUiMessages.CACHING_ENTITY_LIST_COMPOSITE_GROUP_TITLE
		);
	}

	@SuppressWarnings("unused")
	@Override
	protected void initializeLayout(Composite container) {

		// Entities add/remove list pane
		new AddRemoveListPane<EclipseLinkCaching, EclipseLinkCachingEntity>(
			this,
			container,
			this.buildEntitiesAdapter(),
			this.buildEntitiesListModel(),
			this.selectedEntitiesModel,
			this.buildEntityLabelProvider(),
			EclipseLinkHelpContextIds.PERSISTENCE_CACHING
		);

		// Entity Caching property pane
		new EclipseLinkEntityCachingPropertyComposite(
			this,
			this.selectedEntityModel,
			buildPaneEnablerModel(this.selectedEntityModel),
			container
		);
	}
	
	private AddRemoveListPane.Adapter<EclipseLinkCachingEntity> buildEntitiesAdapter() {
		return new AddRemoveListPane.AbstractAdapter<EclipseLinkCachingEntity>() {

			public EclipseLinkCachingEntity addNewItem() {
				return EclipseLinkEntityListComposite.this.addEntity();
			}

			@Override
			public PropertyValueModel<Boolean> buildRemoveButtonEnabledModel(CollectionValueModel<EclipseLinkCachingEntity> selectedItemsModel) {
				//enable the remove button only when 1 item is selected, same as the optional button
				return this.buildSingleSelectedItemEnabledModel(selectedItemsModel);
			}

			public void removeSelectedItems(CollectionValueModel<EclipseLinkCachingEntity> selectedItemsModel) {
				//assume only 1 item since remove button is disabled otherwise
				EclipseLinkCachingEntity cachingEntity = selectedItemsModel.iterator().next();
				getSubject().removeEntity(cachingEntity.getName());
			}
		};
	}
	
	EclipseLinkCachingEntity addEntity() {

		IType type = this.chooseEntity();

		if (type != null) {
			String entityName = this.getEntityName(type.getFullyQualifiedName());
			if (entityName == null) {
				entityName = type.getElementName();
			}
			
			if( ! this.getSubject().entityExists(entityName)) {
				return this.getSubject().addEntity(entityName);
			}
		}
		return null;
	}

	private String getEntityName(String fullyQualifiedTypeName) {

		org.eclipse.jpt.jpa.core.context.Entity entity = getSubject().getPersistenceUnit().getEntity(fullyQualifiedTypeName);
		return (entity != null) ? entity.getName() : null;
	}
	
	private IType chooseEntity() {
		SelectionDialog dialog;
		try {
			dialog = JavaUI.createTypeDialog(
					getShell(),
					PlatformUI.getWorkbench().getProgressService(),
					SearchEngine.createJavaSearchScope(new IJavaElement[] { getJavaProject() }),
					IJavaElementSearchConstants.CONSIDER_CLASSES,
					false,
					StringTools.EMPTY_STRING
				);
		} catch (JavaModelException ex) {
			JptJpaEclipseLinkUiPlugin.instance().logError(ex);
			return null;
		}

		dialog.setTitle(JptJpaEclipseLinkUiMessages.CACHING_ENTITY_LIST_COMPOSITE_DIALOG_TITLE);
		dialog.setMessage(JptJpaEclipseLinkUiMessages.CACHING_ENTITY_LIST_COMPOSITE_DIALOG_MESSAGE);

		return (dialog.open() == Window.OK) ? (IType) dialog.getResult()[0] : null;
	}

	private IJavaProject getJavaProject() {
		return getSubject().getJpaProject().getJavaProject();
	}

	private ILabelProvider buildEntityLabelProvider() {
		return new LabelProvider() {
			@Override
			public String getText(Object element) {
				EclipseLinkCachingEntity entityCaching = (EclipseLinkCachingEntity) element;
				return entityCaching.getName();
			}
		};
	}

	private ListValueModel<EclipseLinkCachingEntity> buildEntitiesListModel() {
		return new ListAspectAdapter<EclipseLinkCaching, EclipseLinkCachingEntity>(
					this.getSubjectHolder(), EclipseLinkCaching.ENTITIES_LIST) {
			@Override
			protected ListIterable<EclipseLinkCachingEntity> getListIterable() {
				return this.subject.getEntities();
			}
			@Override
			protected int size_() {
				return this.subject.getEntitiesSize();
			}
		};
	}

	private PropertyValueModel<Boolean> buildPaneEnablerModel(PropertyValueModel<EclipseLinkCachingEntity> entityModel) {
		return PropertyValueModelTools.valueAffirms(entityModel, EclipseLinkCachingEntity.NAME_IS_VALID);
	}
}
