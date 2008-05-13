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

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.window.Window;
import org.eclipse.jpt.core.JpaProject;
import org.eclipse.jpt.eclipselink.core.internal.context.caching.Caching;
import org.eclipse.jpt.eclipselink.ui.internal.EclipseLinkUiMessages;
import org.eclipse.jpt.eclipselink.ui.internal.EntityDialog;
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

	private AddRemoveListPane.Adapter buildEntitiesAdapter() {
		return new AddRemoveListPane.AbstractAdapter() {

			public void addNewItem(ObjectListSelectionModel listSelectionModel) {
				addEntityFromDialog(listSelectionModel);
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

	private void addEntityFromDialog(ObjectListSelectionModel listSelectionModel) {

		EntityDialog dialog = new EntityDialog(getControl().getShell(), jpaProject());

		if (dialog.open() == Window.OK) {
			String name = dialog.getSelectedName();
			if( ! this.subject().entityExists(name)) {
				String entity = this.subject().addEntity(name);
	
				listSelectionModel.setSelectedValue(entity);
			}
		}
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

	private JpaProject jpaProject() {
		return subject().getJpaProject();
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
}
