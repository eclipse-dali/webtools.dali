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
import org.eclipse.jpt.core.context.Entity;
import org.eclipse.jpt.eclipselink.core.internal.context.caching.Caching;
import org.eclipse.jpt.eclipselink.ui.internal.EntityDialog;
import org.eclipse.jpt.ui.internal.widgets.AbstractFormPane;
import org.eclipse.jpt.ui.internal.widgets.AddRemoveListPane;
import org.eclipse.jpt.utility.internal.model.value.ListAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.swing.ObjectListSelectionModel;
import org.eclipse.jpt.utility.model.value.ListValueModel;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;
import org.eclipse.swt.widgets.Composite;

/**
 *  EntityListComposite
 */
public class EntityListComposite extends AbstractFormPane<Caching>
{
	private AddRemoveListPane<Caching> listPane;

	public EntityListComposite(
								AbstractFormPane<Caching> parentComposite, 
						        PropertyValueModel<Caching> subjectHolder,
						        Composite parent) {

		super(parentComposite, subjectHolder, parent);
	}
	
	@Override
	protected void initializeLayout(Composite composite) {

		int groupBoxMargin = this.groupBoxMargin();

		WritablePropertyValueModel<Entity> entityHolder = this.buildEntityHolder();

		// Entities add/remove list pane
		this.listPane = new AddRemoveListPane<Caching>(
			this,
			this.buildSubPane(composite, 0, groupBoxMargin, 0, groupBoxMargin),
			this.buildEntitiesAdapter(),
			this.buildEntitiesListHolder(),
			entityHolder,
			this.buildEntityLabelProvider(),
			null			//		EclipseLinkHelpContextIds.CACHING_ENTITIES  
		);

	}

	private AddRemoveListPane.Adapter buildEntitiesAdapter() {
		return new AddRemoveListPane.AbstractAdapter() {

			public void addNewItem(ObjectListSelectionModel listSelectionModel) {
				EntityDialog dialog = new EntityDialog(getControl().getShell(), EntityListComposite.this.jpaProject());
				addEntityFromDialog(dialog, listSelectionModel);
			}

			public void removeSelectedItems(ObjectListSelectionModel listSelectionModel) {
				Caching caching = subject();
				String[] selections = (String[]) listSelectionModel.selectedValues();
				for(String name: selections) {
					caching.removeEntity(name);
				}
			}
		};
	}

	private void addEntityFromDialog(EntityDialog dialog,
	                                         ObjectListSelectionModel listSelectionModel) {
		if (dialog.open() == Window.OK) {
			String name = dialog.getSelectedName();
			String entity = this.subject().addEntity(name);

			listSelectionModel.setSelectedValue(entity);
		}
	}

	private ILabelProvider buildEntityLabelProvider() {
		return new LabelProvider() {
			@Override
			public String getText(Object element) {
				if (element != null) {
					return element.toString();
				}
				return "";
			}
		};
	}
	
	public AddRemoveListPane<Caching> listPane() {
		return this.listPane;
	}

	private WritablePropertyValueModel<Entity> buildEntityHolder() {
		return new SimplePropertyValueModel<Entity>();
	}
	
	private JpaProject jpaProject() {
		// TODO
		return null; 
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
