/*******************************************************************************
* Copyright (c) 2008, 2012 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.persistence.schema.generation;

import org.eclipse.jpt.common.ui.internal.JptCommonUiMessages;
import org.eclipse.jpt.common.ui.internal.util.SWTUtil;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyListValueModelAdapter;
import org.eclipse.jpt.common.utility.model.value.ListValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.SchemaGeneration;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.EclipseLinkHelpContextIds;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.EclipseLinkUiMessages;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;

/**
 *  CreateDdlFileNameComposite
 */
public class CreateDdlFileNameComposite extends Pane<SchemaGeneration>
{
	/**
	 * Creates a new <code>CreateDdlFileNameComposite</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param parent The parent container
	 */
	public CreateDdlFileNameComposite(
								Pane<? extends SchemaGeneration> parentPane,
	                           Composite parent) {

		super(parentPane, parent);
	}

	private PropertyValueModel<String> buildDefaultCreateDdlFileNameHolder() {
		return new PropertyAspectAdapter<SchemaGeneration, String>(this.getSubjectHolder(), SchemaGeneration.DEFAULT_SCHEMA_GENERATION_CREATE_FILE_NAME) {
			@Override
			protected String buildValue_() {
				return CreateDdlFileNameComposite.this.getDefaultValue(subject);
			}
		};
	}

	private ListValueModel<String> buildDefaultCreateDdlFileNameListHolder() {
		return new PropertyListValueModelAdapter<String>(
			this.buildDefaultCreateDdlFileNameHolder()
		);
	}

	private ModifiablePropertyValueModel<String> buildCreateDdlFileNameHolder() {
		return new PropertyAspectAdapter<SchemaGeneration, String>(this.getSubjectHolder(), SchemaGeneration.CREATE_FILE_NAME_PROPERTY) {
			@Override
			protected String buildValue_() {

				String name = subject.getCreateFileName();
				if (name == null) {
					name = CreateDdlFileNameComposite.this.getDefaultValue(subject);
				}
				return name;
			}

			@Override
			protected void setValue_(String value) {

				if (getDefaultValue(subject).equals(value)) {
					value = null;
				}
				subject.setCreateFileName(value);
			}
		};
	}

	private String getDefaultValue(SchemaGeneration subject) {
		String defaultValue = subject.getDefaultCreateFileName();

		if (defaultValue != null) {
			return NLS.bind(
				JptCommonUiMessages.DefaultWithOneParam,
				defaultValue
			);
		}
		return JptCommonUiMessages.DefaultEmpty;
	}

	@Override
	protected void initializeLayout(Composite container) {
		Combo combo = addLabeledEditableCombo(
			container,
			EclipseLinkUiMessages.PersistenceXmlSchemaGenerationTab_createDdlFileNameLabel,
			this.buildDefaultCreateDdlFileNameListHolder(),
			this.buildCreateDdlFileNameHolder(),
			EclipseLinkHelpContextIds.PERSISTENCE_SCHEMA_GENERATION
		);
		SWTUtil.attachDefaultValueHandler(combo);
	}
}
