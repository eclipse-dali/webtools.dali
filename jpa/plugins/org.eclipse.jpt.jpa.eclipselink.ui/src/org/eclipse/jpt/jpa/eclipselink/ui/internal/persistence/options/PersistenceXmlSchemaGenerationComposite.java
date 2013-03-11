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
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.EclipseLinkDdlGenerationType;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.EclipseLinkOutputMode;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.EclipseLinkSchemaGeneration;
import org.eclipse.jpt.jpa.eclipselink.ui.JptJpaEclipseLinkUiMessages;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.EclipseLinkHelpContextIds;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;

public class PersistenceXmlSchemaGenerationComposite
	extends Pane<EclipseLinkSchemaGeneration>
{
	public PersistenceXmlSchemaGenerationComposite(Pane<?> parent, PropertyValueModel<EclipseLinkSchemaGeneration> schemaGenModel, Composite parentComposite) {
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
		this.addLabel(container, JptJpaEclipseLinkUiMessages.PERSISTENCE_XML_SCHEMA_GENERATION_TAB_DDL_GENERATION_TYPE_LABEL);
		this.addDdlGenerationTypeCombo(container);

		// Output Mode:
		this.addLabel(container, JptJpaEclipseLinkUiMessages.PERSISTENCE_XML_SCHEMA_GENERATION_TAB_OUTPUT_MODE_LABEL);
		this.addBuildOutputModeCombo(container);

		// DDL Generation Location
		this.addLabel(container, JptJpaEclipseLinkUiMessages.PERSISTENCE_XML_SCHEMA_GENERATION_TAB_DDL_GENERATION_LOCATION_LABEL);
		this.buildDdlGenerationLocationComposite(container);

		// Create DDL File Name:
		this.addLabel(container, JptJpaEclipseLinkUiMessages.PERSISTENCE_XML_SCHEMA_GENERATION_TAB_CREATE_DDL_FILE_NAME_LABEL);
		Combo ddlFileNameCombo = addEditableCombo(
			container,
			this.buildDefaultCreateDdlFileNameListHolder(),
			this.buildCreateDdlFileNameHolder(),
			StringObjectTransformer.<String>instance(),
			getHelpID()
		);
		SWTUtil.attachDefaultValueHandler(ddlFileNameCombo);

		// Drop DDL File Name:
		this.addLabel(container, JptJpaEclipseLinkUiMessages.PERSISTENCE_XML_SCHEMA_GENERATION_TAB_DROP_DDL_FILE_NAME_LABEL);
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

	private EnumFormComboViewer<EclipseLinkSchemaGeneration, EclipseLinkDdlGenerationType> addDdlGenerationTypeCombo(Composite container) {
		return new EnumFormComboViewer<EclipseLinkSchemaGeneration, EclipseLinkDdlGenerationType>(this, container) {
			@Override
			protected void addPropertyNames(Collection<String> propertyNames) {
				super.addPropertyNames(propertyNames);
				propertyNames.add(EclipseLinkSchemaGeneration.DDL_GENERATION_TYPE_PROPERTY);
			}

			@Override
			protected EclipseLinkDdlGenerationType[] getChoices() {
				return EclipseLinkDdlGenerationType.values();
			}

			@Override
			protected EclipseLinkDdlGenerationType getDefaultValue() {
				return this.getSubject().getDefaultDdlGenerationType();
			}

			@Override
			protected String displayString(EclipseLinkDdlGenerationType value) {
				switch (value) {
					case create_tables :
						return JptJpaEclipseLinkUiMessages.DDL_GENERATION_TYPE_COMPOSITE_CREATE_TABLES;
					case drop_and_create_tables :
						return JptJpaEclipseLinkUiMessages.DDL_GENERATION_TYPE_COMPOSITE_DROP_AND_CREATE_TABLES;
					case none :
						return JptJpaEclipseLinkUiMessages.DDL_GENERATION_TYPE_COMPOSITE_NONE;
					default :
						throw new IllegalStateException();
				}
			}

			@Override
			protected EclipseLinkDdlGenerationType getValue() {
				return this.getSubject().getDdlGenerationType();
			}

			@Override
			protected void setValue(EclipseLinkDdlGenerationType value) {
				this.getSubject().setDdlGenerationType(value);
			}

			@Override
			protected String getHelpId() {
				return getHelpID();
			}
		};
	}


	//************ output mode ************

	private EnumFormComboViewer<EclipseLinkSchemaGeneration, EclipseLinkOutputMode> addBuildOutputModeCombo(Composite container) {
		return new EnumFormComboViewer<EclipseLinkSchemaGeneration, EclipseLinkOutputMode>(this, container) {
			@Override
			protected void addPropertyNames(Collection<String> propertyNames) {
				super.addPropertyNames(propertyNames);
				propertyNames.add(EclipseLinkSchemaGeneration.OUTPUT_MODE_PROPERTY);
			}

			@Override
			protected EclipseLinkOutputMode[] getChoices() {
				return EclipseLinkOutputMode.values();
			}

			@Override
			protected EclipseLinkOutputMode getDefaultValue() {
				return this.getSubject().getDefaultOutputMode();
			}

			@Override
			protected String displayString(EclipseLinkOutputMode value) {
				switch (value) {
					case both :
						return JptJpaEclipseLinkUiMessages.OUTPUT_MODE_COMPOSITE_BOTH;
					case database :
						return JptJpaEclipseLinkUiMessages.OUTPUT_MODE_COMPOSITE_DATABASE;
					case sql_script :
						return JptJpaEclipseLinkUiMessages.OUTPUT_MODE_COMPOSITE_SQL_SCRIPT;
					default :
						throw new IllegalStateException();
				}
			}

			@Override
			protected EclipseLinkOutputMode getValue() {
				return this.getSubject().getOutputMode();
			}

			@Override
			protected void setValue(EclipseLinkOutputMode value) {
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
		return new PropertyAspectAdapter<EclipseLinkSchemaGeneration, String>(this.getSubjectHolder(), EclipseLinkSchemaGeneration.DEFAULT_SCHEMA_GENERATION_CREATE_FILE_NAME) {
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
		return new PropertyAspectAdapter<EclipseLinkSchemaGeneration, String>(this.getSubjectHolder(), EclipseLinkSchemaGeneration.CREATE_FILE_NAME_PROPERTY) {
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

	private String getDefaultCreateFileNameValue(EclipseLinkSchemaGeneration subject) {
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
		return new PropertyAspectAdapter<EclipseLinkSchemaGeneration, String>(this.getSubjectHolder(), EclipseLinkSchemaGeneration.DEFAULT_SCHEMA_GENERATION_DROP_FILE_NAME) {
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
		return new PropertyAspectAdapter<EclipseLinkSchemaGeneration, String>(this.getSubjectHolder(), EclipseLinkSchemaGeneration.DROP_FILE_NAME_PROPERTY) {
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

	private String getDefaultDropDdlFileNameValue(EclipseLinkSchemaGeneration subject) {
		String defaultValue = subject.getDefaultDropFileName();

		if (defaultValue != null) {
			return NLS.bind(
				JptCommonUiMessages.DEFAULT_WITH_ONE_PARAM,
				defaultValue
			);
		}
		return JptCommonUiMessages.DEFAULT_EMPTY;
	}

	private Pane<EclipseLinkSchemaGeneration> buildDdlGenerationLocationComposite(Composite container) {
		return new FolderChooserComboPane<EclipseLinkSchemaGeneration>(this, container) {

			@Override
			protected ModifiablePropertyValueModel<String> buildTextHolder() {
				return new PropertyAspectAdapter<EclipseLinkSchemaGeneration, String>(
										getSubjectHolder(), EclipseLinkSchemaGeneration.APPLICATION_LOCATION_PROPERTY) {
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

			private String defaultValue(EclipseLinkSchemaGeneration subject) {
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
				return JptJpaEclipseLinkUiMessages.PERSISTENCE_XML_SCHEMA_GENERATION_TAB_DEFAULT_DOT;
			}

			@Override
			protected String getDialogMessage() {
				return JptJpaEclipseLinkUiMessages.DDL_GENERATION_LOCATION_COMPOSITE_DIALOG_MESSAGE;
			}

			@Override
			protected String getDialogTitle() {
				return JptJpaEclipseLinkUiMessages.DDL_GENERATION_LOCATION_COMPOSITE_DIALOG_TITLE;
			}
		};
	}
}
