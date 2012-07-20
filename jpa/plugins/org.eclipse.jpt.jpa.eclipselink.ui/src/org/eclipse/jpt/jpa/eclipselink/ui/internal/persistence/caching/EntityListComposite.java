/*******************************************************************************
 * Copyright (c) 2007, 2012 Oracle. All rights reserved.
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
import org.eclipse.jdt.core.search.IJavaSearchScope;
import org.eclipse.jdt.core.search.SearchEngine;
import org.eclipse.jdt.ui.IJavaElementSearchConstants;
import org.eclipse.jdt.ui.JavaUI;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.window.Window;
import org.eclipse.jpt.common.ui.internal.widgets.AddRemoveListPane;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.internal.iterables.ListIterable;
import org.eclipse.jpt.common.utility.internal.model.value.CollectionPropertyValueModelAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.ListAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.SimpleCollectionValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.CollectionValueModel;
import org.eclipse.jpt.common.utility.model.value.ListValueModel;
import org.eclipse.jpt.common.utility.model.value.ModifiableCollectionValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.Caching;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.CachingEntity;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.EclipseLinkHelpContextIds;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.EclipseLinkUiMessages;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.plugin.JptJpaEclipseLinkUiPlugin;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.SelectionDialog;
import org.eclipse.ui.progress.IProgressService;

/**
 *  EntityListComposite
 */
public class EntityListComposite<T extends Caching> extends Pane<T>
{
	private ModifiableCollectionValueModel<CachingEntity> selectedEntitiesModel;
	private PropertyValueModel<CachingEntity> selectedEntityModel;
	
	public EntityListComposite(Pane<T> parentComposite, Composite parent) {

		super(parentComposite, parent);
	}

	@Override
	protected void initialize() {
		super.initialize();
		this.selectedEntitiesModel = this.buildSelectedEntitiesModel();
		this.selectedEntityModel = this.buildSelectedEntityModel(this.selectedEntitiesModel);
	}

	private ModifiableCollectionValueModel<CachingEntity> buildSelectedEntitiesModel() {
		return new SimpleCollectionValueModel<CachingEntity>();
	}

	private PropertyValueModel<CachingEntity> buildSelectedEntityModel(CollectionValueModel<CachingEntity> selectedEntitiesModel) {
		return new CollectionPropertyValueModelAdapter<CachingEntity, CachingEntity>(selectedEntitiesModel) {
			@Override
			protected CachingEntity buildValue() {
				if (this.collectionModel.size() == 1) {
					return this.collectionModel.iterator().next();
				}
				return null;
			}
		};
	}

	@Override
	protected Composite addComposite(Composite parent) {
		return this.addTitledGroup(
			parent,
			EclipseLinkUiMessages.CachingEntityListComposite_groupTitle
		);
	}

	@Override
	protected void initializeLayout(Composite container) {

		// Entities add/remove list pane
		new AddRemoveListPane<Caching, CachingEntity>(
			this,
			container,
			this.buildEntitiesAdapter(),
			this.buildEntitiesListHolder(),
			this.selectedEntitiesModel,
			this.buildEntityLabelProvider(),
			EclipseLinkHelpContextIds.PERSISTENCE_CACHING
		);

		// Entity Caching property pane
		new EntityCachingPropertyComposite(
			this,
			this.selectedEntityModel,
			buildPaneEnablerModel(this.selectedEntityModel),
			container
		);
	}
	
	private AddRemoveListPane.Adapter<CachingEntity> buildEntitiesAdapter() {
		return new AddRemoveListPane.AbstractAdapter<CachingEntity>() {

			public CachingEntity addNewItem() {
				return EntityListComposite.this.addEntity();
			}

			@Override
			public PropertyValueModel<Boolean> buildRemoveButtonEnabledModel(CollectionValueModel<CachingEntity> selectedItemsModel) {
				//enable the remove button only when 1 item is selected, same as the optional button
				return this.buildSingleSelectedItemEnabledModel(selectedItemsModel);
			}

			public void removeSelectedItems(CollectionValueModel<CachingEntity> selectedItemsModel) {
				//assume only 1 item since remove button is disabled otherwise
				CachingEntity cachingEntity = selectedItemsModel.iterator().next();
				getSubject().removeEntity(cachingEntity.getName());
			}
		};
	}
	
	private CachingEntity addEntity() {

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
			JptJpaEclipseLinkUiPlugin.instance().logError(e);
			return null;
		}
		typeSelectionDialog.setTitle(EclipseLinkUiMessages.CachingEntityListComposite_dialogTitle);
		typeSelectionDialog.setMessage(EclipseLinkUiMessages.CachingEntityListComposite_dialogMessage);

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
				CachingEntity entityCaching = (CachingEntity) element;
				return entityCaching.getName();
			}
		};
	}

	private ListValueModel<CachingEntity> buildEntitiesListHolder() {
		return new ListAspectAdapter<Caching, CachingEntity>(
					this.getSubjectHolder(), Caching.ENTITIES_LIST) {
			@Override
			protected ListIterable<CachingEntity> getListIterable() {
				return this.subject.getEntities();
			}
			@Override
			protected int size_() {
				return this.subject.getEntitiesSize();
			}
		};
	}

	private PropertyValueModel<Boolean> buildPaneEnablerModel(PropertyValueModel<CachingEntity> entityHolder) {
		return new TransformationPropertyValueModel<CachingEntity, Boolean>(entityHolder) {
			@Override
			protected Boolean transform_(CachingEntity value) {
				return Boolean.valueOf(value.entityNameIsValid());
			}
		};
	}
}
