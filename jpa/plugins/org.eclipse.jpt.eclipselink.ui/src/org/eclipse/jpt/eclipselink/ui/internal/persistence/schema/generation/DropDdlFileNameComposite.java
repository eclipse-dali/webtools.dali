/*******************************************************************************
* Copyright (c) 2008 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.persistence.schema.generation;

import org.eclipse.jpt.eclipselink.core.internal.context.persistence.schema.generation.SchemaGeneration;
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
 *  DropDdlFileNameComposite
 */
public class DropDdlFileNameComposite extends Pane<SchemaGeneration>
{
	/**
	 * Creates a new <code>DropDdlFileNameComposite</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param parent The parent container
	 */
	public DropDdlFileNameComposite(
								Pane<? extends SchemaGeneration> parentPane,
	                           Composite parent) {

		super(parentPane, parent);
	}

	private PropertyValueModel<String> buildDefaultDropDdlFileNameHolder() {
		return new PropertyAspectAdapter<SchemaGeneration, String>(this.getSubjectHolder(), SchemaGeneration.DEFAULT_SCHEMA_GENERATION_DROP_FILE_NAME) {
			@Override
			protected String buildValue_() {
				return DropDdlFileNameComposite.this.getDefaultValue(subject);
			}
		};
	}

	private ListValueModel<String> buildDefaultDropDdlFileNameListHolder() {
		return new PropertyListValueModelAdapter<String>(
			this.buildDefaultDropDdlFileNameHolder()
		);
	}

	private WritablePropertyValueModel<String> buildDropDdlFileNameHolder() {
		return new PropertyAspectAdapter<SchemaGeneration, String>(this.getSubjectHolder(), SchemaGeneration.DROP_FILE_NAME_PROPERTY) {
			@Override
			protected String buildValue_() {

				String name = subject.getDropFileName();
				if (name == null) {
					name = DropDdlFileNameComposite.this.getDefaultValue(subject);
				}
				return name;
			}

			@Override
			protected void setValue_(String value) {

				if (DropDdlFileNameComposite.this.getDefaultValue(subject).equals(value)) {
					value = null;
				}
				subject.setDropFileName(value);
			}
		};
	}

	private String getDefaultValue(SchemaGeneration subject) {
		String defaultValue = subject.getDefaultDropFileName();

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
			EclipseLinkUiMessages.PersistenceXmlSchemaGenerationTab_dropDdlFileNameLabel,
			this.buildDefaultDropDdlFileNameListHolder(),
			this.buildDropDdlFileNameHolder(),
			null		// EclipseLinkHelpContextIds.DROP_DDL_FILE_NAME
		);
		SWTUtil.attachDefaultValueHandler(combo);
	}
}
