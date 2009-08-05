/*******************************************************************************
* Copyright (c) 2009 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.ui.internal.jpa2.java.details;

import java.util.Collection;

import org.eclipse.jpt.core.JpaProject;
import org.eclipse.jpt.core.context.GeneratorContainer;
import org.eclipse.jpt.core.jpa2.context.SequenceGenerator2_0;
import org.eclipse.jpt.db.SchemaContainer;
import org.eclipse.jpt.ui.internal.JpaHelpContextIds;
import org.eclipse.jpt.ui.internal.mappings.JptUiMappingsMessages;
import org.eclipse.jpt.ui.internal.mappings.db.CatalogCombo;
import org.eclipse.jpt.ui.internal.mappings.db.SchemaCombo;
import org.eclipse.jpt.ui.internal.mappings.details.SequenceGeneratorComposite;
import org.eclipse.jpt.ui.internal.widgets.Pane;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.swt.widgets.Composite;

/**
 *  JavaSequenceGenerator2_0Composite
 */
public class JavaSequenceGenerator2_0Composite extends SequenceGeneratorComposite
{

	public JavaSequenceGenerator2_0Composite(
			Pane<? extends GeneratorContainer> parentPane, 
			Composite parent) {
		super(parentPane, parent);
	}
	
	public JavaSequenceGenerator2_0Composite(
		Pane<?> parentPane, 
		PropertyValueModel<? extends GeneratorContainer> subjectHolder,
		Composite parent) {

		super(parentPane, subjectHolder, parent);
	}

	@Override
	protected void initializeLayout(Composite container) {

		// Name widgets
		this.addLabeledText(
			container,
			JptUiMappingsMessages.SequenceGeneratorComposite_name,
			this.buildGeneratorNameHolder(),
			JpaHelpContextIds.MAPPING_SEQUENCE_GENERATOR_NAME
		);

		// Sequence Generator widgets
		this.addLabeledComposite(
			container,
			JptUiMappingsMessages.SequenceGeneratorComposite_sequence,
			this.buildSequenceNameCombo(container),
			JpaHelpContextIds.MAPPING_SEQUENCE_GENERATOR_SEQUENCE
		);

		// Schema widgets
		this.addLabeledComposite(
			container,
			JptUiMappingsMessages.SequenceGeneratorComposite_schema,
			this.addSchemaCombo(container),
			null	// JpaHelpContextIds.MAPPING_SEQUENCE_GENERATOR_SCHEMA
		);

		// Catalog widgets
		this.addLabeledComposite(
			container,
			JptUiMappingsMessages.SequenceGeneratorComposite_catalog,
			this.addCatalogCombo(container),
			null	// JpaHelpContextIds.MAPPING_SEQUENCE_GENERATOR_CATALOG
		);

		this.addAllocationSizeCombo(container);
		this.addInitialValueCombo(container);
	}

	private PropertyValueModel<SequenceGenerator2_0> buildSequenceGeneratorHolder() {
		return new PropertyAspectAdapter<GeneratorContainer, SequenceGenerator2_0>(getSubjectHolder(), GeneratorContainer.SEQUENCE_GENERATOR_PROPERTY) {
			@Override
			protected SequenceGenerator2_0 buildValue_() {
				return (SequenceGenerator2_0) this.subject.getSequenceGenerator();
			}
		};
	}

	private SchemaCombo<SequenceGenerator2_0> addSchemaCombo(Composite container) {

		return new SchemaCombo<SequenceGenerator2_0>(this, buildSequenceGeneratorHolder(), container) {

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
				return getSubject().getDefaultSchema();
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
				return JavaSequenceGenerator2_0Composite.this.getJpaProject();
			}

			@Override
			protected void setValue(String value) {
				((SequenceGenerator2_0) JavaSequenceGenerator2_0Composite.this.retrieveGenerator(
					JavaSequenceGenerator2_0Composite.this.getSubject())).setSpecifiedSchema(value);
			}

			@Override
			protected String getValue() {
				return getSubject().getSpecifiedSchema();
			}

			@Override
			protected SchemaContainer getDbSchemaContainer() {
				SequenceGenerator2_0 tg = this.getSubject();
				if (tg != null) {
					return tg.getDbSchemaContainer();
				}
				return JavaSequenceGenerator2_0Composite.this.getSubject().getContextDefaultDbSchemaContainer();
			}
			
			@Override
			protected SchemaContainer getDbSchemaContainer_() {
				// we overrode #getDbSchemaContainer() instead
				throw new UnsupportedOperationException();
			}
		};
	}

	private CatalogCombo<SequenceGenerator2_0> addCatalogCombo(Composite container) {

		return new CatalogCombo<SequenceGenerator2_0>(this, buildSequenceGeneratorHolder(), container) {

			@Override
			protected void addPropertyNames(Collection<String> propertyNames) {
				super.addPropertyNames(propertyNames);
				propertyNames.add(SequenceGenerator2_0.DEFAULT_CATALOG_PROPERTY);
				propertyNames.add(SequenceGenerator2_0.SPECIFIED_CATALOG_PROPERTY);
			}

			@Override
			protected String getDefaultValue() {
				return getSubject().getDefaultCatalog();
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
				return JavaSequenceGenerator2_0Composite.this.getJpaProject();
			}

			@Override
			protected void setValue(String value) {
				((SequenceGenerator2_0) JavaSequenceGenerator2_0Composite.this.retrieveGenerator(
					JavaSequenceGenerator2_0Composite.this.getSubject())).setSpecifiedCatalog(value);
			}

			@Override
			protected String getValue() {
				return getSubject().getSpecifiedCatalog();
			}
		};
	}
	
}
