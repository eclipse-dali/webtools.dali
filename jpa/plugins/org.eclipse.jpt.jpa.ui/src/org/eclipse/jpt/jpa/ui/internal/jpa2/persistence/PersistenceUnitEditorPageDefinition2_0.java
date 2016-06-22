/*******************************************************************************
 * Copyright (c) 2012, 2016 Oracle. All rights reserved.
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
import org.eclipse.jpt.common.utility.internal.model.value.ListValueModelTools;
import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.common.utility.model.value.ListValueModel;
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
		this.buildEditorPageContent(form.getForm().getBody(), widgetFactory, resourceManager, this.buildPersistenceUnitModel(jpaRootStructureNodeModel));
	}

	protected PropertyValueModel<PersistenceUnit> buildPersistenceUnitModel(PropertyValueModel<JpaStructureNode> jpaStructureNodeModel) {
		return ListValueModelTools.firstElementPropertyValueModel(this.buildPersistenceUnitListModel(jpaStructureNodeModel));
	}

	protected ListValueModel<PersistenceUnit> buildPersistenceUnitListModel(PropertyValueModel<JpaStructureNode> jpaStructureNodeModel) {
		return new PersistenceUnitListModel(jpaStructureNodeModel);
	}

	protected abstract void buildEditorPageContent(Composite parent, WidgetFactory widgetFactory, ResourceManager resourceManager, PropertyValueModel<PersistenceUnit> persistenceUnitModel);


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
