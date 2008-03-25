/*******************************************************************************
* Copyright (c) 2007 Oracle. All rights reserved.
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
	public EntityListComposite(AbstractPane<Caching> parentComposite,
	                           Composite parent) {

		super(parentComposite, parent);
	}

	@Override
	protected void initializeLayout(Composite container) {

		container = this.buildTitledPane(
			container,
			"Entity Caching" // TODO
		);

		WritablePropertyValueModel<EntityCaching> entityHolder = this.buildEntityHolder();

		// Entities add/remove list pane
		AddRemoveListPane<Caching> listPane = new AddRemoveListPane<Caching>(
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

	private void installPaneEnabler(WritablePropertyValueModel<EntityCaching> entityHolder,
	                                EntityCachingPropertyComposite pane) {

		new PaneEnabler(
			this.buildPaneEnablerHolder(entityHolder),
			pane
		);
	}

	private PropertyValueModel<Boolean> buildPaneEnablerHolder(WritablePropertyValueModel<EntityCaching> entityHolder) {
		return new TransformationPropertyValueModel<EntityCaching, Boolean>(entityHolder) {
			@Override
			protected Boolean transform_(EntityCaching value) {
				return value.entityNameIsValid();
			}
		};
	}

	private AddRemoveListPane.Adapter buildEntitiesAdapter() {
		return new AddRemoveListPane.AbstractAdapter() {

			public void addNewItem(ObjectListSelectionModel listSelectionModel) {
				addEntityFromDialog(listSelectionModel);
			}

			@Override
			public boolean hasOptionalButton() {
				return true;
			}

			@Override
			public String optionalButtonText() {
				return "Edit..."; // TOOD
			}

			@Override
			public void optionOnSelection(ObjectListSelectionModel listSelectionModel) {
				editEntityFromDialog(listSelectionModel);
			}

			public void removeSelectedItems(ObjectListSelectionModel listSelectionModel) {
				Caching caching = subject();
				for (Object item : listSelectionModel.selectedValues()) {
					EntityCaching entityCaching = (EntityCaching) item;
					caching.removeEntity(entityCaching.getEntityName());
				}
			}
		};
	}

	private void addEntityFromDialog(ObjectListSelectionModel listSelectionModel) {

		EntityDialog dialog = new EntityDialog(getControl().getShell(), jpaProject());

		if (dialog.open() == Window.OK) {
			String name = dialog.getSelectedName();
			String entity = this.subject().addEntity(name);

			listSelectionModel.setSelectedValue(entity);
		}
	}

	private void editEntityFromDialog(ObjectListSelectionModel listSelectionModel) {

		EntityCaching entityCaching = (EntityCaching) listSelectionModel.selectedValue();

		EntityDialog dialog = new EntityDialog(shell(), jpaProject());
		dialog.setSelectedName(entityCaching.getEntityName());

		if (dialog.open() == Window.OK) {
			String name = dialog.getSelectedName();
			this.subject().removeEntity(entityCaching.getEntityName());
			String entity = this.subject().addEntity(name);

			listSelectionModel.setSelectedValue(entity);
		}
	}

	private ILabelProvider buildEntityLabelProvider() {
		return new LabelProvider() {
			@Override
			public String getText(Object element) {
				EntityCaching entityCaching = (EntityCaching) element;
				return entityCaching.getEntityName();
			}
		};
	}

	private WritablePropertyValueModel<EntityCaching> buildEntityHolder() {
		return new SimplePropertyValueModel<EntityCaching>();
	}

	private JpaProject jpaProject() {
		// TODO
		return null;
	}

	private ListValueModel<EntityCaching> buildEntityCachingListHolder() {
		return new TransformationListValueModelAdapter<String, EntityCaching>(buildEntitiesListHolder()) {
			@Override
			protected EntityCaching transformItem(String item) {
				return new EntityCaching(subject(), item);
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
