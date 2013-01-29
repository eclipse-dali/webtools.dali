/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.persistence.options;

import java.util.Collection;
import org.eclipse.jpt.common.ui.JptCommonUiMessages;
import org.eclipse.jpt.common.ui.internal.util.SWTUtil;
import org.eclipse.jpt.common.ui.internal.widgets.EnumFormComboViewer;
import org.eclipse.jpt.common.ui.internal.widgets.FolderChooserComboPane;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyListValueModelAdapter;
import org.eclipse.jpt.common.utility.internal.transformer.StringObjectTransformer;
import org.eclipse.jpt.common.utility.model.value.ListValueModel;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.DdlGenerationType;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.OutputMode;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.SchemaGeneration;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.EclipseLinkHelpContextIds;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.EclipseLinkUiMessages;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;

public class PersistenceXmlSchemaGenerationComposite
	extends Pane<SchemaGeneration>
{
	public PersistenceXmlSchemaGenerationComposite(Pane<?> parent, PropertyValueModel<SchemaGeneration> schemaGenModel, Composite parentComposite) {
		super(parent, schemaGenModel, parentComposite);
	}

	protected String getHelpID() {
		return EclipseLinkHelpContextIds.PERSISTENCE_SCHEMA_GENERATION;
	}

	@Override
	protected Composite addComposite(Composite container) {
		return addSubPane(container, 2, 0, 0, 0, 0);
	}

	@Override
	protected void initializeLayout(Composite container) {
		// DDL Generation Type:
		this.addLabel(container, EclipseLinkUiMessages.PersistenceXmlSchemaGenerationTab_ddlGenerationTypeLabel);
		this.addDdlGenerationTypeCombo(container);

		// Output Mode:
		this.addLabel(container, EclipseLinkUiMessages.PersistenceXmlSchemaGenerationTab_outputModeLabel);
		this.addBuildOutputModeCombo(container);

		// DDL Generation Location
		this.addLabel(container, EclipseLinkUiMessages.PersistenceXmlSchemaGenerationTab_ddlGenerationLocationLabel);
		this.buildDdlGenerationLocationComposite(container);

		// Create DDL File Name:
		this.addLabel(container, EclipseLinkUiMessages.PersistenceXmlSchemaGenerationTab_createDdlFileNameLabel);
		Combo ddlFileNameCombo = addEditableCombo(
			container,
			this.buildDefaultCreateDdlFileNameListHolder(),
			this.buildCreateDdlFileNameHolder(),
			StringObjectTransformer.<String>instance(),
			getHelpID()
		);
		SWTUtil.attachDefaultValueHandler(ddlFileNameCombo);

		// Drop DDL File Name:
		this.addLabel(container, EclipseLinkUiMessages.PersistenceXmlSchemaGenerationTab_dropDdlFileNameLabel);
		Combo dropDDLCombo = addEditableCombo(
			container,
			this.buildDefaultDropDdlFileNameListHolder(),
			this.buildDropDdlFileNameHolder(),
			StringObjectTransformer.<String>instance(),
			getHelpID()
		);
		SWTUtil.attachDefaultValueHandler(dropDDLCombo);
	}

	//************ DDL generation type ************

	private EnumFormComboViewer<SchemaGeneration, DdlGenerationType> addDdlGenerationTypeCombo(Composite container) {
		return new EnumFormComboViewer<SchemaGeneration, DdlGenerationType>(this, container) {
			@Override
			protected void addPropertyNames(Collection<String> propertyNames) {
				super.addPropertyNames(propertyNames);
				propertyNames.add(SchemaGeneration.DDL_GENERATION_TYPE_PROPERTY);
			}

			@Override
			protected DdlGenerationType[] getChoices() {
				return DdlGenerationType.values();
			}

			@Override
			protected DdlGenerationType getDefaultValue() {
				return this.getSubject().getDefaultDdlGenerationType();
			}

			@Override
			protected String displayString(DdlGenerationType value) {
				switch (value) {
					case create_tables :
						return EclipseLinkUiMessages.DdlGenerationTypeComposite_create_tables;
					case drop_and_create_tables :
						return EclipseLinkUiMessages.DdlGenerationTypeComposite_drop_and_create_tables;
					case none :
						return EclipseLinkUiMessages.DdlGenerationTypeComposite_none;
					default :
						throw new IllegalStateException();
				}
			}

			@Override
			protected DdlGenerationType getValue() {
				return this.getSubject().getDdlGenerationType();
			}

			@Override
			protected void setValue(DdlGenerationType value) {
				this.getSubject().setDdlGenerationType(value);
			}

			@Override
			protected String getHelpId() {
				return getHelpID();
			}
		};
	}


	//************ output mode ************

	private EnumFormComboViewer<SchemaGeneration, OutputMode> addBuildOutputModeCombo(Composite container) {
		return new EnumFormComboViewer<SchemaGeneration, OutputMode>(this, container) {
			@Override
			protected void addPropertyNames(Collection<String> propertyNames) {
				super.addPropertyNames(propertyNames);
				propertyNames.add(SchemaGeneration.OUTPUT_MODE_PROPERTY);
			}

			@Override
			protected OutputMode[] getChoices() {
				return OutputMode.values();
			}

			@Override
			protected OutputMode getDefaultValue() {
				return this.getSubject().getDefaultOutputMode();
			}

			@Override
			protected String displayString(OutputMode value) {
				switch (value) {
					case both :
						return EclipseLinkUiMessages.OutputModeComposite_both;
					case database :
						return EclipseLinkUiMessages.OutputModeComposite_database;
					case sql_script :
						return EclipseLinkUiMessages.OutputModeComposite_sql_script;
					default :
						throw new IllegalStateException();
				}
			}

			@Override
			protected OutputMode getValue() {
				return this.getSubject().getOutputMode();
			}

			@Override
			protected void setValue(OutputMode value) {
				this.getSubject().setOutputMode(value);
			}
			@Override
			protected String getHelpId() {
				return getHelpID();
			}
		};
	}


	//************ Create DDL file name ************

	private PropertyValueModel<String> buildDefaultCreateDdlFileNameHolder() {
		return new PropertyAspectAdapter<SchemaGeneration, String>(this.getSubjectHolder(), SchemaGeneration.DEFAULT_SCHEMA_GENERATION_CREATE_FILE_NAME) {
			@Override
			protected String buildValue_() {
				return PersistenceXmlSchemaGenerationComposite.this.getDefaultCreateFileNameValue(subject);
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
					name = PersistenceXmlSchemaGenerationComposite.this.getDefaultCreateFileNameValue(subject);
				}
				return name;
			}

			@Override
			protected void setValue_(String value) {

				if (getDefaultCreateFileNameValue(subject).equals(value)) {
					value = null;
				}
				subject.setCreateFileName(value);
			}
		};
	}

	private String getDefaultCreateFileNameValue(SchemaGeneration subject) {
		String defaultValue = subject.getDefaultCreateFileName();

		if (defaultValue != null) {
			return NLS.bind(
				JptCommonUiMessages.DEFAULT_WITH_ONE_PARAM,
				defaultValue
			);
		}
		return JptCommonUiMessages.DEFAULT_EMPTY;
	}


	//************ Drop DDL file name ************


	private PropertyValueModel<String> buildDefaultDropDdlFileNameHolder() {
		return new PropertyAspectAdapter<SchemaGeneration, String>(this.getSubjectHolder(), SchemaGeneration.DEFAULT_SCHEMA_GENERATION_DROP_FILE_NAME) {
			@Override
			protected String buildValue_() {
				return PersistenceXmlSchemaGenerationComposite.this.getDefaultDropDdlFileNameValue(subject);
			}
		};
	}

	private ListValueModel<String> buildDefaultDropDdlFileNameListHolder() {
		return new PropertyListValueModelAdapter<String>(
			this.buildDefaultDropDdlFileNameHolder()
		);
	}

	private ModifiablePropertyValueModel<String> buildDropDdlFileNameHolder() {
		return new PropertyAspectAdapter<SchemaGeneration, String>(this.getSubjectHolder(), SchemaGeneration.DROP_FILE_NAME_PROPERTY) {
			@Override
			protected String buildValue_() {

				String name = subject.getDropFileName();
				if (name == null) {
					name = PersistenceXmlSchemaGenerationComposite.this.getDefaultDropDdlFileNameValue(subject);
				}
				return name;
			}

			@Override
			protected void setValue_(String value) {

				if (PersistenceXmlSchemaGenerationComposite.this.getDefaultDropDdlFileNameValue(subject).equals(value)) {
					value = null;
				}
				subject.setDropFileName(value);
			}
		};
	}

	private String getDefaultDropDdlFileNameValue(SchemaGeneration subject) {
		String defaultValue = subject.getDefaultDropFileName();

		if (defaultValue != null) {
			return NLS.bind(
				JptCommonUiMessages.DEFAULT_WITH_ONE_PARAM,
				defaultValue
			);
		}
		return JptCommonUiMessages.DEFAULT_EMPTY;
	}

	private Pane<SchemaGeneration> buildDdlGenerationLocationComposite(Composite container) {
		return new FolderChooserComboPane<SchemaGeneration>(this, container) {

			@Override
			protected ModifiablePropertyValueModel<String> buildTextHolder() {
				return new PropertyAspectAdapter<SchemaGeneration, String>(
										getSubjectHolder(), SchemaGeneration.APPLICATION_LOCATION_PROPERTY) {
					@Override
					protected String buildValue_() {

						String name = subject.getApplicationLocation();
						if (name == null) {
							name = defaultValue(subject);
						}
						return name;
					}

					@Override
					protected void setValue_(String value) {

						if (defaultValue(subject).equals(value)) {
							value = null;
						}
						subject.setApplicationLocation(value);
					}
				};
			}

			private String defaultValue(SchemaGeneration subject) {
				String defaultValue = subject.getDefaultApplicationLocation();

				if (defaultValue != null) {
					return NLS.bind(
						JptCommonUiMessages.DEFAULT_WITH_ONE_PARAM,
						defaultValue
					);
				}
				return this.getDefaultString();
			}

			@Override
			protected String getDefaultString() {
				return EclipseLinkUiMessages.PersistenceXmlSchemaGenerationTab_defaultDot;
			}

			@Override
			protected String getDialogMessage() {
				return EclipseLinkUiMessages.DdlGenerationLocationComposite_dialogMessage;
			}

			@Override
			protected String getDialogTitle() {
				return EclipseLinkUiMessages.DdlGenerationLocationComposite_dialogTitle;
			}
		};
	}
}
