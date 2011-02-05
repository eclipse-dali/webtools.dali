/*******************************************************************************
* Copyright (c) 2007, 2010 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
*
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.persistence.caching;

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
import org.eclipse.jpt.eclipselink.core.context.persistence.caching.Caching;
import org.eclipse.jpt.eclipselink.core.internal.context.persistence.caching.Entity;
import org.eclipse.jpt.eclipselink.ui.JptEclipseLinkUiPlugin;
import org.eclipse.jpt.eclipselink.ui.internal.EclipseLinkHelpContextIds;
import org.eclipse.jpt.eclipselink.ui.internal.EclipseLinkUiMessages;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.SelectionDialog;
import org.eclipse.ui.progress.IProgressService;

/**
 *  EntityListComposite
 */
public class EntityListComposite<T extends Caching> extends Pane<T>
{
	WritablePropertyValueModel<Entity> entityHolder;
	
	public EntityListComposite(Pane<T> parentComposite, Composite parent) {

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
			EclipseLinkUiMessages.CachingEntityListComposite_groupTitle
		);

		// Entities add/remove list pane
		new AddRemoveListPane<Caching>(
			this,
			container,
			this.buildEntitiesAdapter(),
			this.buildEntitiesListHolder(),
			this.entityHolder,
			this.buildEntityLabelProvider(),
			EclipseLinkHelpContextIds.PERSISTENCE_CACHING
		);

		// Entity Caching property pane
		EntityCachingPropertyComposite pane = new EntityCachingPropertyComposite(
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
				Caching caching = getSubject();
				for (Object item : listSelectionModel.selectedValues()) {
					Entity entityCaching = (Entity) item;
					caching.removeEntity(entityCaching.getName());
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

		org.eclipse.jpt.core.context.Entity entity = getSubject().getPersistenceUnit().getEntity(fullyQualifiedTypeName);
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

	private IJavaProject getJavaProject() {
		return getSubject().getJpaProject().getJavaProject();
	}

	private ILabelProvider buildEntityLabelProvider() {
		return new LabelProvider() {
			@Override
			public String getText(Object element) {
				Entity entityCaching = (Entity) element;
				return entityCaching.getName();
			}
		};
	}

	private WritablePropertyValueModel<Entity> buildEntityHolder() {
		return new SimplePropertyValueModel<Entity>();
	}

	private ListValueModel<Entity> buildEntitiesListHolder() {
		return new ListAspectAdapter<Caching, Entity>(
					this.getSubjectHolder(), Caching.ENTITIES_LIST) {
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
	                                EntityCachingPropertyComposite pane) {

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
