/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.details.orm;

import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.orm.OrmEntity;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.jpa.ui.JptJpaUiMessages;
import org.eclipse.jpt.jpa.ui.details.JptJpaUiDetailsMessages;
import org.eclipse.jpt.jpa.ui.details.orm.JptJpaUiDetailsOrmMessages;
import org.eclipse.jpt.jpa.ui.internal.details.AbstractEntityComposite;
import org.eclipse.jpt.jpa.ui.internal.details.AccessTypeComboViewer;
import org.eclipse.jpt.jpa.ui.internal.details.EntityNameCombo;
import org.eclipse.jpt.jpa.ui.internal.details.IdClassChooser;
import org.eclipse.jpt.jpa.ui.internal.details.TableComposite;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.forms.widgets.Hyperlink;

public abstract class AbstractOrmEntityComposite<T extends OrmEntity>
	extends AbstractEntityComposite<T>
{
	protected AbstractOrmEntityComposite(
			PropertyValueModel<? extends T> entityModel,
			Composite parentComposite,
			WidgetFactory widgetFactory,
			ResourceManager resourceManager) {
		super(entityModel, parentComposite, widgetFactory, resourceManager);
	}
	
	
	@Override
	protected void initializeLayout(Composite container) {
		this.initializeEntityCollapsibleSection(container);
		this.initializeQueriesCollapsibleSection(container);
		this.initializeInheritanceCollapsibleSection(container);
		this.initializeAttributeOverridesCollapsibleSection(container);
		this.initializeGeneratorsCollapsibleSection(container);
		this.initializeSecondaryTablesCollapsibleSection(container);
	}

	@Override
	protected Control initializeEntitySection(Composite container) {
		container = this.addSubPane(container, 2, 0, 0, 0, 0);

		// Java class widgets
		Hyperlink javaClassHyperlink = this.addHyperlink(container, JptJpaUiDetailsOrmMessages.ORM_JAVA_CLASS_CHOOSER_JAVA_CLASS);
		new OrmJavaClassChooser(this, this.buildPersistentTypeReferenceModel(), container, javaClassHyperlink);

		// Table widgets
		TableComposite tableComposite = new TableComposite(this, container);
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = 2;
		tableComposite.getControl().setLayoutData(gridData);

		// Entity name widgets
		this.addLabel(container, JptJpaUiDetailsMessages.ENTITY_NAME_COMPOSITE_NAME);
		new EntityNameCombo(this, container);

		// Access type widgets
		this.addLabel(container, JptJpaUiMessages.ACCESS_TYPE_COMPOSITE_ACCESS);
		new AccessTypeComboViewer(this, buildAccessReferenceModel(), container);

		// Id class widgets
		Hyperlink hyperlink = this.addHyperlink(container,JptJpaUiDetailsMessages.ID_CLASS_COMPOSITE_LABEL);
		new IdClassChooser(this, this.buildIdClassReferenceModel(), container, hyperlink);

		// Metadata complete widgets
		MetadataCompleteTriStateCheckBox metadataCompleteCheckBox = new MetadataCompleteTriStateCheckBox(this, getSubjectHolder(), container);
		gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = 2;
		metadataCompleteCheckBox.getControl().setLayoutData(gridData);

		return container;
	}

	protected PropertyValueModel<OrmPersistentType> buildPersistentTypeReferenceModel() {
		return new PropertyAspectAdapter<T, OrmPersistentType>(getSubjectHolder()) {
			@Override
			protected OrmPersistentType buildValue_() {
				return this.subject.getPersistentType();
			}
		};
	}

	@Override
	protected Control initializeSecondaryTablesSection(Composite container) {
		return new OrmSecondaryTablesComposite(this, container).getControl();
	}

	@Override
	protected Control initializeInheritanceSection(Composite container) {
		return new OrmInheritanceComposite(this, container).getControl();
	}
}