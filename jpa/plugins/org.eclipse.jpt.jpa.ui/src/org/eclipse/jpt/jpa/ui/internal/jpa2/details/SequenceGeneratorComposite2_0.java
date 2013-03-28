/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.jpa2.details;

import java.util.Arrays;
import java.util.Collection;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.internal.ArrayTools;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.core.context.SequenceGenerator;
import org.eclipse.jpt.jpa.core.jpa2.context.SequenceGenerator2_0;
import org.eclipse.jpt.jpa.db.SchemaContainer;
import org.eclipse.jpt.jpa.ui.details.JptJpaUiDetailsMessages;
import org.eclipse.jpt.jpa.ui.internal.JpaHelpContextIds;
import org.eclipse.jpt.jpa.ui.internal.details.SequenceGeneratorComposite;
import org.eclipse.jpt.jpa.ui.internal.details.db.CatalogCombo;
import org.eclipse.jpt.jpa.ui.internal.details.db.SchemaCombo;
import org.eclipse.jpt.jpa.ui.internal.details.db.SequenceCombo;
import org.eclipse.swt.widgets.Composite;

/**
 *  JavaSequenceGenerator2_0Composite
 */
public class SequenceGeneratorComposite2_0 extends SequenceGeneratorComposite
{

	public SequenceGeneratorComposite2_0(Pane<?> parentPane,
		PropertyValueModel<SequenceGenerator> subjectHolder,
		Composite parent,
		GeneratorBuilder<SequenceGenerator> builder) {

		super(parentPane, subjectHolder, parent, builder);
	}

	@Override
	protected void initializeLayout(Composite container) {
		// Name widgets
		this.addLabel(container, JptJpaUiDetailsMessages.SequenceGeneratorComposite_name);
		this.addText(container, this.buildGeneratorNameHolder(), JpaHelpContextIds.MAPPING_SEQUENCE_GENERATOR_NAME);

		// Sequence Generator widgets
		this.addLabel(container, JptJpaUiDetailsMessages.SequenceGeneratorComposite_sequence);
		this.buildSequenceNameCombo(container);

		// Schema widgets
		this.addLabel(container, JptJpaUiDetailsMessages.SequenceGeneratorComposite_schema);
		this.addSchemaCombo(container);

		// Catalog widgets
		this.addLabel(container, JptJpaUiDetailsMessages.SequenceGeneratorComposite_catalog);
		this.addCatalogCombo(container);

		// Allocation size widgets
		this.addLabel(container, JptJpaUiDetailsMessages.GeneratorComposite_allocationSize);
		this.addAllocationSizeCombo(container);

		// Initial value widgets
		this.addLabel(container, JptJpaUiDetailsMessages.GeneratorComposite_initialValue);
		this.addInitialValueCombo(container);
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
				return SequenceGeneratorComposite2_0.this.getJpaProject();
			}

			@Override
			protected void setValue(String value) {
				((SequenceGenerator2_0) SequenceGeneratorComposite2_0.this.retrieveGenerator()).setSpecifiedCatalog(value);
			}

			@Override
			protected String getValue() {
				return ((SequenceGenerator2_0) getSubject()).getSpecifiedCatalog();
			}
		};
	}
	
	private SchemaCombo<SequenceGenerator> addSchemaCombo(Composite container) {

		return new SchemaCombo<SequenceGenerator>(this, getSubjectHolder(), container) {

			@Override
			protected void addPropertyNames(Collection<String> propertyNames) {
				super.addPropertyNames(propertyNames);
				propertyNames.add(SequenceGenerator2_0.DEFAULT_SCHEMA_PROPERTY);
				propertyNames.add(SequenceGenerator2_0.SPECIFIED_SCHEMA_PROPERTY);
				propertyNames.addAll(SCHEMA_PICK_LIST_PROPERTIES);
			}

			@Override
			protected void propertyChanged(String propertyName) {
				if (SCHEMA_PICK_LIST_PROPERTIES.contains(propertyName)) {
					this.repopulateComboBox();
				} else {
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
				return SequenceGeneratorComposite2_0.this.getJpaProject();
			}

			@Override
			protected void setValue(String value) {
				((SequenceGenerator2_0) SequenceGeneratorComposite2_0.this.retrieveGenerator()).setSpecifiedSchema(value);
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
				return SequenceGeneratorComposite2_0.this.getSubject().getContextDefaultDbSchemaContainer();
			}
			
			@Override
			protected SchemaContainer getDbSchemaContainer_() {
				// we overrode #getDbSchemaContainer() instead
				throw new UnsupportedOperationException();
			}
		};
	}

	/* CU private */ static final Collection<String> SCHEMA_PICK_LIST_PROPERTIES = Arrays.asList(new String[] {
		SequenceGenerator2_0.DEFAULT_CATALOG_PROPERTY,
		SequenceGenerator2_0.SPECIFIED_CATALOG_PROPERTY
	});
	
	@Override
	protected SequenceCombo<SequenceGenerator> buildSequenceNameCombo(Composite parent) {
		return new LocalSequenceCombo2_0(this, getSubjectHolder(), parent);
	}

	protected class LocalSequenceCombo2_0
		extends LocalSequenceCombo
	{
		protected LocalSequenceCombo2_0(Pane<?> parentPane, PropertyValueModel<? extends SequenceGenerator> subjectHolder, Composite parent) {
			super(parentPane, subjectHolder, parent);
		}

		@Override
		protected void addPropertyNames(Collection<String> propertyNames) {
			super.addPropertyNames(propertyNames);
			propertyNames.addAll(SEQUENCE_PICK_LIST_PROPERTIES);
		}

		@Override
		protected void propertyChanged(String propertyName) {
			if (SEQUENCE_PICK_LIST_PROPERTIES.contains(propertyName)) {
				this.repopulateComboBox();
			} else {
				super.propertyChanged(propertyName);
			}
		}
	}

	/* CU private */ static final Collection<String> SEQUENCE_PICK_LIST_PROPERTIES = Arrays.asList(ArrayTools.addAll(SCHEMA_PICK_LIST_PROPERTIES.toArray(new String[0]),
		SequenceGenerator2_0.DEFAULT_SCHEMA_PROPERTY,
		SequenceGenerator2_0.SPECIFIED_SCHEMA_PROPERTY
	));
}
