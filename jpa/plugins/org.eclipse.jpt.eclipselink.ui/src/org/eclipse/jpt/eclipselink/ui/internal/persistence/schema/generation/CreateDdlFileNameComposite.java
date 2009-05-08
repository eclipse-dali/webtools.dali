/*******************************************************************************
* Copyright (c) 2008, 2009 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.persistence.schema.generation;

import org.eclipse.jpt.eclipselink.core.internal.context.persistence.schema.generation.SchemaGeneration;
import org.eclipse.jpt.eclipselink.ui.internal.EclipseLinkHelpContextIds;
import org.eclipse.jpt.eclipselink.ui.internal.EclipseLinkUiMessages;
import org.eclipse.jpt.ui.internal.util.SWTUtil;
import org.eclipse.jpt.ui.internal.widgets.Pane;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.PropertyListValueModelAdapter;
import org.eclipse.jpt.utility.model.value.ListValueModel;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.custom.CCombo;
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

	private WritablePropertyValueModel<String> buildCreateDdlFileNameHolder() {
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
				EclipseLinkUiMessages.PersistenceXmlSchemaGenerationTab_defaultWithOneParam,
				defaultValue
			);
		}
		else {
			return EclipseLinkUiMessages.PersistenceXmlSchemaGenerationTab_defaultEmpty;
		}
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void initializeLayout(Composite container) {

		CCombo combo = addLabeledEditableCCombo(
			container,
			EclipseLinkUiMessages.PersistenceXmlSchemaGenerationTab_createDdlFileNameLabel,
			this.buildDefaultCreateDdlFileNameListHolder(),
			this.buildCreateDdlFileNameHolder(),
			EclipseLinkHelpContextIds.PERSISTENCE_SCHEMA_GENERATION
		);
		SWTUtil.attachDefaultValueHandler(combo);
	}
}
