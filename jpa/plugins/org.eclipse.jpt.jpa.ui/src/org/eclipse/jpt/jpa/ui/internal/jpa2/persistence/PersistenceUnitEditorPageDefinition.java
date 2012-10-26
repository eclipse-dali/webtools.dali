/*******************************************************************************
 * Copyright (c) 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.jpa2.persistence;

import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.utility.internal.model.value.ListAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.ListPropertyValueModelAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.common.utility.model.value.ListValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.JpaStructureNode;
import org.eclipse.jpt.jpa.core.context.persistence.Persistence;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpa.ui.editors.JpaEditorPageDefinition;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.IManagedForm;

public abstract class PersistenceUnitEditorPageDefinition
	implements JpaEditorPageDefinition
{

	protected PersistenceUnitEditorPageDefinition() {
		super();
	}

	public void buildEditorPageContent(IManagedForm form, WidgetFactory widgetFactory, PropertyValueModel<JpaStructureNode> jpaRootStructureNodeModel) {
		this.buildEditorPageContent(form.getForm().getBody(), widgetFactory, jpaRootStructureNodeModel);
	}

	protected abstract void buildEditorPageContent(Composite parent, WidgetFactory widgetFactory, PropertyValueModel<JpaStructureNode> jpaRootStructureNodeModel);

	protected PropertyValueModel<PersistenceUnit> buildPersistenceUnitModel(PropertyValueModel<JpaStructureNode> jpaStructureNodeModel) {
		return new ListPropertyValueModelAdapter<PersistenceUnit>(this.buildPersistenceUnitListModel(jpaStructureNodeModel)) {
			@Override
			protected PersistenceUnit buildValue() {
				return this.listModel.size() > 0 ? (PersistenceUnit) this.listModel.get(0) : null;
			}
		};
	}

	protected ListValueModel<PersistenceUnit> buildPersistenceUnitListModel(PropertyValueModel<JpaStructureNode> jpaStructureNodeModel) {
		return new ListAspectAdapter<Persistence, PersistenceUnit>(this.buildPersistenceModel(jpaStructureNodeModel), Persistence.PERSISTENCE_UNITS_LIST) {
			@Override
			protected ListIterable<PersistenceUnit> getListIterable() {
				return this.subject.getPersistenceUnits();
			}
	
			@Override
			protected int size_() {
				return this.subject.getPersistenceUnitsSize();
			}
		};
	}

	protected PropertyValueModel<Persistence> buildPersistenceModel(PropertyValueModel<JpaStructureNode> jpaStructureNodeModel) {
		return new TransformationPropertyValueModel<JpaStructureNode, Persistence>(jpaStructureNodeModel) {
			@Override
			protected Persistence transform(JpaStructureNode jpaStructureNode) {
				return (Persistence) jpaStructureNode;
			}
		};
	}

	@Override
	public String toString() {
		return this.getPageText();
	}
}