/*******************************************************************************
* Copyright (c) 2008 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.schema.generation;

import org.eclipse.jpt.eclipselink.core.internal.context.schema.generation.SchemaGeneration;
import org.eclipse.jpt.eclipselink.ui.internal.EclipseLinkUiMessages;
import org.eclipse.jpt.ui.internal.util.SWTUtil;
import org.eclipse.jpt.ui.internal.widgets.AbstractPane;
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
public class CreateDdlFileNameComposite extends AbstractPane<SchemaGeneration>
{
	/**
	 * Creates a new <code>CreateDdlFileNameComposite</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param parent The parent container
	 */
	public CreateDdlFileNameComposite(
								AbstractPane<? extends SchemaGeneration> parentPane,
	                           Composite parent) {

		super(parentPane, parent);
	}

	private PropertyValueModel<String> buildDefaultCreateDdlFileNameHolder() {
		return new PropertyAspectAdapter<SchemaGeneration, String>(this.getSubjectHolder(), SchemaGeneration.DEFAULT_SCHEMA_GENERATION_CREATE_FILE_NAME) {
			@Override
			protected String buildValue_() {
				return CreateDdlFileNameComposite.this.defaultValue(subject);
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
					name = CreateDdlFileNameComposite.this.defaultValue(subject);
				}
				return name;
			}

			@Override
			protected void setValue_(String value) {

				if (defaultValue(subject).equals(value)) {
					value = null;
				}
				subject.setCreateFileName(value);
			}
		};
	}

	private String defaultValue(SchemaGeneration subject) {
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

		CCombo combo = buildLabeledEditableCCombo(
			container,
			EclipseLinkUiMessages.PersistenceXmlSchemaGenerationTab_createDdlFileName,
			this.buildDefaultCreateDdlFileNameListHolder(),
			this.buildCreateDdlFileNameHolder(),
			null		// EclipseLinkHelpContextIds.CREATE_DDL_FILE_NAME
		);
		SWTUtil.attachDefaultValueHandler(combo);
	}
}
