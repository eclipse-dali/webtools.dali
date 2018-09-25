/*******************************************************************************
 * Copyright (c) 2018 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.jpa2_2.persistence;

import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.jpa2_1.context.persistence.schemagen.SchemaGeneration2_1;
import org.eclipse.jpt.jpa.core.jpa2_2.context.persistence.schemagen.SchemaGeneration2_2;
import org.eclipse.jpt.jpa.ui.jpa2_1.persistence.JptJpaUiPersistenceMessages2_1;
import org.eclipse.swt.widgets.Composite;

/**
 *  DataLoadingComposite
 */
public class DataLoadingComposite2_2 extends Pane<SchemaGeneration2_2>
{
	public DataLoadingComposite2_2(
			Pane<SchemaGeneration2_2> parent, 
			Composite container) {
		super(parent, container);
	}
	
	public DataLoadingComposite2_2(
			Pane<?> parent, 
			PropertyValueModel<SchemaGeneration2_2> schemaGenModel, 
			Composite container) {
		super(parent, schemaGenModel, container);
	}
	
	@Override
	protected Composite addComposite(Composite parent) {
		return this.addTitledGroup(
			parent,
			JptJpaUiPersistenceMessages2_1.SCHEMA_GENERATION_COMPOSITE_DATA_LOADING_GROUP_TITLE,
			2,
			null
		);
	}

	@Override
	protected void initializeLayout(Composite parentComposite) {

		// SqlLoadScriptSource
		this.addLabel(parentComposite, JptJpaUiPersistenceMessages2_1.SCHEMA_GENERATION_COMPOSITE_SQL_LOAD_SCRIPT_SOURCE_LABEL); 
		this.addText(parentComposite, this.buildSqlLoadScriptSourceHolder());
	}

	// ********** SqlLoadScriptSource **********
	
	private ModifiablePropertyValueModel<String> buildSqlLoadScriptSourceHolder() {
		return new PropertyAspectAdapter<SchemaGeneration2_1, String>(
				this.getSubjectHolder(), 
				SchemaGeneration2_2.SQL_LOAD_SCRIPT_SOURCE_PROPERTY) 
		{
			@Override
			protected String buildValue_() {
				return this.subject.getSqlLoadScriptSource();
			}

			@Override
			protected void setValue_(String value) {
				if (value.length() == 0) {
					value = null;
				}
				this.subject.setSqlLoadScriptSource(value);
			}
		};
	}
}
