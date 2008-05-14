/*******************************************************************************
* Copyright (c) 2007, 2008 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
*
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.caching;

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
import org.eclipse.jpt.eclipselink.core.internal.context.caching.Caching;
import org.eclipse.jpt.eclipselink.ui.internal.EclipseLinkUiMessages;
import org.eclipse.jpt.eclipselink.ui.internal.JptEclipseLinkUiPlugin;
import org.eclipse.jpt.ui.internal.util.PaneEnabler;
import org.eclipse.jpt.ui.internal.widgets.AbstractPane;
import org.eclipse.jpt.ui.internal.widgets.AddRemoveListPane;
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
public class EntityListComposite extends AbstractPane<Caching>
{
	public EntityListComposite(AbstractPane<Caching> parentComposite, Composite parent) {

		super(parentComposite, parent);
	}

	@Override
	protected void initializeLayout(Composite container) {

		container = this.buildTitledPane(
			container,
			EclipseLinkUiMessages.CachingEntityListComposite_groupTitle
		);

		WritablePropertyValueModel<EntityCacheProperties> entityHolder = this.buildEntityHolder();

		// Entities add/remove list pane
		new AddRemoveListPane<Caching>(
			this,
			container,
			this.buildEntitiesAdapter(),
			this.buildEntityCachingListHolder(),
			entityHolder,
			this.buildEntityLabelProvider(),
			null			//		EclipseLinkHelpContextIds.CACHING_ENTITIES
		);

		// Entity Caching property pane
		EntityCachingPropertyComposite pane = new EntityCachingPropertyComposite(
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
				Caching caching = subject();
				for (Object item : listSelectionModel.selectedValues()) {
					EntityCacheProperties entityCaching = (EntityCacheProperties) item;
					caching.removeEntity(entityCaching.getEntityName());
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
			
			if( ! this.subject().entityExists(entityName)) {
				String entity = this.subject().addEntity(entityName);
	
				listSelectionModel.setSelectedValue(entity);
			}
		}
	}
	
	private String getEntityName(String fullyQualifiedTypeName) {

		PersistentType persistentType = subject().persistenceUnit().getPersistentType(fullyQualifiedTypeName);

		if (persistentType != null && persistentType.getMappingKey() == MappingKeys.ENTITY_TYPE_MAPPING_KEY) {
			TypeMapping mapping = persistentType.getMapping();
			if (mapping instanceof Entity) {
				return ((Entity)mapping).getName();
			}
		}
		return null;
	}
	
	private IType chooseEntity() {

		IPackageFragmentRoot root = packageFragmentRoot();
		if (root == null) {
			return null;
		}
		IJavaElement[] elements = new IJavaElement[] { root.getJavaProject() };
		IJavaSearchScope scope = SearchEngine.createJavaSearchScope(elements);
		IProgressService service = PlatformUI.getWorkbench().getProgressService();
		SelectionDialog typeSelectionDialog;

		try {
			typeSelectionDialog = JavaUI.createTypeDialog(
				shell(),
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
		typeSelectionDialog.setTitle(EclipseLinkUiMessages.CachingEntityListComposite_dialogTitle);
		typeSelectionDialog.setMessage(EclipseLinkUiMessages.CachingEntityListComposite_dialogMessage);

		if (typeSelectionDialog.open() == Window.OK) {
			return (IType) typeSelectionDialog.getResult()[0];
		}
		return null;
	}

	private IPackageFragmentRoot packageFragmentRoot() {
		IProject project = subject().getJpaProject().getProject();
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
				EntityCacheProperties entityCaching = (EntityCacheProperties) element;
				return entityCaching.getEntityName();
			}
		};
	}

	private WritablePropertyValueModel<EntityCacheProperties> buildEntityHolder() {
		return new SimplePropertyValueModel<EntityCacheProperties>();
	}

	private ListValueModel<EntityCacheProperties> buildEntityCachingListHolder() {
		return new TransformationListValueModelAdapter<String, EntityCacheProperties>(buildEntitiesListHolder()) {
			@Override
			protected EntityCacheProperties transformItem(String item) {
				return new EntityCacheProperties(subject(), item);
			}
		};
	}

	private ListValueModel<String> buildEntitiesListHolder() {
		return new ListAspectAdapter<Caching, String>(
					this.getSubjectHolder(), Caching.ENTITIES_LIST_PROPERTY) {
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

	private void installPaneEnabler(WritablePropertyValueModel<EntityCacheProperties> entityHolder,
	                                EntityCachingPropertyComposite pane) {

		new PaneEnabler(
			this.buildPaneEnablerHolder(entityHolder),
			pane
		);
	}

	private PropertyValueModel<Boolean> buildPaneEnablerHolder(WritablePropertyValueModel<EntityCacheProperties> entityHolder) {
		return new TransformationPropertyValueModel<EntityCacheProperties, Boolean>(entityHolder) {
			@Override
			protected Boolean transform_(EntityCacheProperties value) {
				return value.entityNameIsValid();
			}
		};
	}
}
