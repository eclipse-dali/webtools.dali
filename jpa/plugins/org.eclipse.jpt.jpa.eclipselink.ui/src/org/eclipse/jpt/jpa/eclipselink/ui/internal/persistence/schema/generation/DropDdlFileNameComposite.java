/*******************************************************************************
* Copyright (c) 2008, 2010 Oracle. All rights reserved.
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
import org.eclipse.jpt.common.utility.model.value.WritablePropertyValueModel;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.schema.generation.SchemaGeneration;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.EclipseLinkHelpContextIds;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.EclipseLinkUiMessages;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.widgets.Combo;
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
			EclipseLinkUiMessages.PersistenceXmlSchemaGenerationTab_dropDdlFileNameLabel,
			this.buildDefaultDropDdlFileNameListHolder(),
			this.buildDropDdlFileNameHolder(),
			EclipseLinkHelpContextIds.PERSISTENCE_SCHEMA_GENERATION
		);
		SWTUtil.attachDefaultValueHandler(combo);
	}
}
