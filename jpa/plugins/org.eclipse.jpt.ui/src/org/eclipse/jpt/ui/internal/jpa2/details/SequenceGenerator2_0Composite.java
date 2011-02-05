/*******************************************************************************
* Copyright (c) 2009 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.ui.internal.jpa2.details;

import java.util.Collection;

import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.core.JpaProject;
import org.eclipse.jpt.core.context.SequenceGenerator;
import org.eclipse.jpt.core.jpa2.context.SequenceGenerator2_0;
import org.eclipse.jpt.jpa.db.SchemaContainer;
import org.eclipse.jpt.ui.internal.JpaHelpContextIds;
import org.eclipse.jpt.ui.internal.details.JptUiDetailsMessages;
import org.eclipse.jpt.ui.internal.details.SequenceGeneratorComposite;
import org.eclipse.jpt.ui.internal.details.db.CatalogCombo;
import org.eclipse.jpt.ui.internal.details.db.SchemaCombo;
import org.eclipse.swt.widgets.Composite;

/**
 *  JavaSequenceGenerator2_0Composite
 */
public class SequenceGenerator2_0Composite extends SequenceGeneratorComposite
{

	public SequenceGenerator2_0Composite(Pane<?> parentPane,
		PropertyValueModel<SequenceGenerator> subjectHolder,
		Composite parent,
		GeneratorBuilder<SequenceGenerator> builder) {

		super(parentPane, subjectHolder, parent, builder);
	}

	@Override
	protected void initializeLayout(Composite container) {

		// Name widgets
		this.addLabeledText(
			container,
			JptUiDetailsMessages.SequenceGeneratorComposite_name,
			this.buildGeneratorNameHolder(),
			JpaHelpContextIds.MAPPING_SEQUENCE_GENERATOR_NAME
		);

		// Sequence Generator widgets
		this.addLabeledComposite(
			container,
			JptUiDetailsMessages.SequenceGeneratorComposite_sequence,
			this.buildSequenceNameCombo(container),
			JpaHelpContextIds.MAPPING_SEQUENCE_GENERATOR_SEQUENCE
		);

		// Schema widgets
		this.addLabeledComposite(
			container,
			JptUiDetailsMessages.SequenceGeneratorComposite_schema,
			this.addSchemaCombo(container),
			null	// JpaHelpContextIds.MAPPING_SEQUENCE_GENERATOR_SCHEMA
		);

		// Catalog widgets
		this.addLabeledComposite(
			container,
			JptUiDetailsMessages.SequenceGeneratorComposite_catalog,
			this.addCatalogCombo(container),
			null	// JpaHelpContextIds.MAPPING_SEQUENCE_GENERATOR_CATALOG
		);

		this.addAllocationSizeCombo(container);
		this.addInitialValueCombo(container);
	}

	private SchemaCombo<SequenceGenerator> addSchemaCombo(Composite container) {

		return new SchemaCombo<SequenceGenerator>(this, getSubjectHolder(), container) {

			@Override
			protected void addPropertyNames(Collection<String> propertyNames) {
				super.addPropertyNames(propertyNames);
				propertyNames.add(SequenceGenerator2_0.DEFAULT_SCHEMA_PROPERTY);
				propertyNames.add(SequenceGenerator2_0.SPECIFIED_SCHEMA_PROPERTY);
				propertyNames.add(SequenceGenerator2_0.DEFAULT_CATALOG_PROPERTY);
				propertyNames.add(SequenceGenerator2_0.SPECIFIED_CATALOG_PROPERTY);
			}

			@Override
			protected void propertyChanged(String propertyName) {
				if (propertyName == SequenceGenerator2_0.DEFAULT_CATALOG_PROPERTY
					|| propertyName == SequenceGenerator2_0.SPECIFIED_CATALOG_PROPERTY ) {
					repopulateComboBox();
				}
				else {
					super.propertyChanged(propertyName);
				}
			}

			@Override
			protected String getDefaultValue() {
				return ((SequenceGenerator2_0) getSubject()).getDefaultSchema();
			}

			@Override
			protected boolean nullSubjectIsAllowed() {
				return true;
			}

			/**
			 * subject may be null, so delegate to the composite
			 */
			@Override
			protected JpaProject getJpaProject() {
				return SequenceGenerator2_0Composite.this.getJpaProject();
			}

			@Override
			protected void setValue(String value) {
				((SequenceGenerator2_0) SequenceGenerator2_0Composite.this.retrieveGenerator()).setSpecifiedSchema(value);
			}

			@Override
			protected String getValue() {
				return ((SequenceGenerator2_0) getSubject()).getSpecifiedSchema();
			}

			@Override
			protected SchemaContainer getDbSchemaContainer() {
				SequenceGenerator2_0 tg = (SequenceGenerator2_0) this.getSubject();
				if (tg != null) {
					return tg.getDbSchemaContainer();
				}
				return SequenceGenerator2_0Composite.this.getSubject().getContextDefaultDbSchemaContainer();
			}
			
			@Override
			protected SchemaContainer getDbSchemaContainer_() {
				// we overrode #getDbSchemaContainer() instead
				throw new UnsupportedOperationException();
			}
		};
	}

	private CatalogCombo<SequenceGenerator> addCatalogCombo(Composite container) {

		return new CatalogCombo<SequenceGenerator>(this, getSubjectHolder(), container) {

			@Override
			protected void addPropertyNames(Collection<String> propertyNames) {
				super.addPropertyNames(propertyNames);
				propertyNames.add(SequenceGenerator2_0.DEFAULT_CATALOG_PROPERTY);
				propertyNames.add(SequenceGenerator2_0.SPECIFIED_CATALOG_PROPERTY);
			}

			@Override
			protected String getDefaultValue() {
				return ((SequenceGenerator2_0) getSubject()).getDefaultCatalog();
			}

			@Override
			protected boolean nullSubjectIsAllowed() {
				return true;
			}

			/**
			 * subject may be null, so delegate to the composite
			 */
			@Override
			protected JpaProject getJpaProject() {
				return SequenceGenerator2_0Composite.this.getJpaProject();
			}

			@Override
			protected void setValue(String value) {
				((SequenceGenerator2_0) SequenceGenerator2_0Composite.this.retrieveGenerator()).setSpecifiedCatalog(value);
			}

			@Override
			protected String getValue() {
				return ((SequenceGenerator2_0) getSubject()).getSpecifiedCatalog();
			}
		};
	}
	
}
