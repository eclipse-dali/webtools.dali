/*******************************************************************************
 * Copyright (c) 2024 Lakshminarayana Nekkanti. All rights reserved.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Lakshminarayana Nekkanti - initial API and implementation
 *
 *******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.jpa3_1.persistence;

import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.jpa2_1.context.persistence.schemagen.SchemaGeneration2_1;
import org.eclipse.jpt.jpa.core.jpa3_1.context.persistence.schemagen.SchemaGeneration3_1;
import org.eclipse.jpt.jpa.ui.jpa2_1.persistence.JptJpaUiPersistenceMessages2_1;
import org.eclipse.swt.widgets.Composite;

/**
 * DataLoadingComposite
 */
public class DataLoadingComposite3_1 extends Pane<SchemaGeneration3_1> {
	public DataLoadingComposite3_1(Pane<SchemaGeneration3_1> parent, Composite container) {
		super(parent, container);
	}

	public DataLoadingComposite3_1(Pane<?> parent, PropertyValueModel<SchemaGeneration3_1> schemaGenModel,
			Composite container) {
		super(parent, schemaGenModel, container);
	}

	@Override
	protected Composite addComposite(Composite parent) {
		return this.addTitledGroup(parent,
				JptJpaUiPersistenceMessages2_1.SCHEMA_GENERATION_COMPOSITE_DATA_LOADING_GROUP_TITLE, 2, null);
	}

	@Override
	protected void initializeLayout(Composite parentComposite) {

		// SqlLoadScriptSource
		this.addLabel(parentComposite,
				JptJpaUiPersistenceMessages2_1.SCHEMA_GENERATION_COMPOSITE_SQL_LOAD_SCRIPT_SOURCE_LABEL);
		this.addText(parentComposite, this.buildSqlLoadScriptSourceHolder());
	}

	// ********** SqlLoadScriptSource **********

	private ModifiablePropertyValueModel<String> buildSqlLoadScriptSourceHolder() {
		return new PropertyAspectAdapter<SchemaGeneration2_1, String>(this.getSubjectHolder(),
				SchemaGeneration3_1.SQL_LOAD_SCRIPT_SOURCE_PROPERTY) {
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
