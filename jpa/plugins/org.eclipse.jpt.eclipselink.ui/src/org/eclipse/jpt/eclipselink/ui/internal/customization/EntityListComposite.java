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

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.window.Window;
import org.eclipse.jpt.core.JpaProject;
import org.eclipse.jpt.eclipselink.core.internal.context.customization.Customization;
import org.eclipse.jpt.eclipselink.ui.internal.EclipseLinkUiMessages;
import org.eclipse.jpt.eclipselink.ui.internal.EntityDialog;
import org.eclipse.jpt.ui.internal.util.PaneEnabler;
import org.eclipse.jpt.ui.internal.widgets.AbstractPane;
import org.eclipse.jpt.ui.internal.widgets.AddRemoveListPane;
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

/**
 *  EntityListComposite
 */
public class EntityListComposite extends AbstractPane<Customization>
{
	private WritablePropertyValueModel<EntityCustomizerProperties> entityHolder;

	public EntityListComposite(AbstractPane<Customization> parentComposite, Composite parent) {

		super(parentComposite, parent);
	}

	@Override
	protected void initialize() {
		super.initialize();
		entityHolder = this.buildEntityHolder();
	}

	@Override
	protected void initializeLayout(Composite container) {

		container = this.buildTitledPane(
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

	private AddRemoveListPane.Adapter buildEntitiesAdapter() {
		return new AddRemoveListPane.AbstractAdapter() {

			public void addNewItem(ObjectListSelectionModel listSelectionModel) {
				addEntityFromDialog(listSelectionModel);
			}

			public void removeSelectedItems(ObjectListSelectionModel listSelectionModel) {
				Customization customization = subject();

				for (Object item : listSelectionModel.selectedValues()) {
					EntityCustomizerProperties entityCustomization = (EntityCustomizerProperties) item;
					customization.removeEntity(entityCustomization.getEntityName());
				}
			}
		};
	}

	private void addEntityFromDialog(ObjectListSelectionModel listSelectionModel) {

		EntityDialog dialog = new EntityDialog(getControl().getShell(), getJpaProject());

		if (dialog.open() == Window.OK) {
			String name = dialog.getSelectedName();
			this.subject().addEntity(name);

			int index = CollectionTools.indexOf(this.subject().entities(), name);
			EntityCustomizerProperties item = (EntityCustomizerProperties) listSelectionModel.getListModel().getElementAt(index);
			entityHolder.setValue(item);
		}
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

	private JpaProject getJpaProject() {
		return this.subject().getJpaProject();
	}

	private ListValueModel<EntityCustomizerProperties> buildEntityCustomizationListHolder() {
		return new TransformationListValueModelAdapter<String, EntityCustomizerProperties>(buildEntitiesListHolder()) {
			@Override
			protected EntityCustomizerProperties transformItem(String item) {
				return new EntityCustomizerProperties(subject(), item);
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
}
