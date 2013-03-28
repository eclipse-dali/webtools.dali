/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.jpa2.persistence;

import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.utility.internal.model.value.ListAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.ListPropertyValueModelAdapter;
import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.JpaStructureNode;
import org.eclipse.jpt.jpa.core.context.persistence.Persistence;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpa.ui.editors.JpaEditorPageDefinition;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.IManagedForm;

public abstract class PersistenceUnitEditorPageDefinition2_0
	implements JpaEditorPageDefinition
{
	protected PersistenceUnitEditorPageDefinition2_0() {
		super();
	}

	public void buildContent(IManagedForm form, WidgetFactory widgetFactory, ResourceManager resourceManager, PropertyValueModel<JpaStructureNode> jpaRootStructureNodeModel) {
		this.buildEditorPageContent(form.getForm().getBody(), widgetFactory, resourceManager, new PersistenceUnitModel(jpaRootStructureNodeModel));
	}

	protected abstract void buildEditorPageContent(Composite parent, WidgetFactory widgetFactory, ResourceManager resourceManager, PropertyValueModel<PersistenceUnit> persistenceUnitModel);

	protected static class PersistenceUnitModel
		extends ListPropertyValueModelAdapter<PersistenceUnit>
	{
		protected PersistenceUnitModel(PropertyValueModel<JpaStructureNode> jpaStructureNodeModel) {
			super(new PersistenceUnitListModel(jpaStructureNodeModel));
		}

		@Override
		protected PersistenceUnit buildValue() {
			return (this.listModel.size() > 0) ? (PersistenceUnit) this.listModel.get(0) : null;
		}
	}

	/**
	 * Assume the JPA structure node is a persistence.
	 */
	protected static class PersistenceUnitListModel
		extends ListAspectAdapter<JpaStructureNode, PersistenceUnit>
	{
		protected PersistenceUnitListModel(PropertyValueModel<JpaStructureNode> jpaStructureNodeModel) {
			super(jpaStructureNodeModel, Persistence.PERSISTENCE_UNITS_LIST);
		}

		@Override
		protected ListIterable<PersistenceUnit> getListIterable() {
			return ((Persistence) this.subject).getPersistenceUnits();
		}

		@Override
		protected int size_() {
			return ((Persistence) this.subject).getPersistenceUnitsSize();
		}
	}

	@Override
	public String toString() {
		return this.getTitleText();
	}
}
