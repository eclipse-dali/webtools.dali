/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.jpa2_1.persistence;

import java.util.Collection;
import org.eclipse.jpt.common.ui.JptCommonUiMessages;
import org.eclipse.jpt.common.ui.internal.widgets.EnumFormComboViewer;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.ui.internal.widgets.TriStateCheckBox;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.jpa2_1.context.persistence.SchemaGenerationAction2_1;
import org.eclipse.jpt.jpa.core.jpa2_1.context.persistence.SchemaGenerationTarget2_1;
import org.eclipse.jpt.jpa.core.jpa2_1.context.persistence.schemagen.SchemaGeneration2_1;
import org.eclipse.jpt.jpa.ui.jpa2_1.persistence.JptJpaUiPersistenceMessages2_1;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;

/**
 *  SchemaGenerationComposite
 */
public class SchemaGenerationComposite extends Pane<SchemaGeneration2_1>
{
	public SchemaGenerationComposite(
			Pane<SchemaGeneration2_1> parent, 
			Composite container) {
		super(parent, container);
	}
	
	public SchemaGenerationComposite(
			Pane<?> parent, 
			PropertyValueModel<SchemaGeneration2_1> schemaGenModel, 
			Composite container) {
		super(parent, schemaGenModel, container);
	}
	
	@Override
	protected Composite addComposite(Composite parent) {
		return this.addTitledGroup(
			parent,
			JptJpaUiPersistenceMessages2_1.SchemaGenerationComposite_schemaGenerationGroupTitle,
			2,
			null
		);
	}

	@Override
	protected void initializeLayout(Composite parentComposite) {
		// DatabaseAction 
		this.addLabel(parentComposite, JptJpaUiPersistenceMessages2_1.SchemaGenerationComposite_databaseAction);
		this.buildDatabaseActionCombo(parentComposite);
		// ScriptsAction 
		this.addLabel(parentComposite, JptJpaUiPersistenceMessages2_1.SchemaGenerationComposite_scriptsGeneration); 
		this.buildScriptsActionCombo(parentComposite);
		// CreateSource 
		this.addLabel(parentComposite, JptJpaUiPersistenceMessages2_1.SchemaGenerationComposite_metadataAndScriptCreation); 
		this.buildCreateSourceCombo(parentComposite);
		// DropSource 
		this.addLabel(parentComposite, JptJpaUiPersistenceMessages2_1.SchemaGenerationComposite_metadataAndScriptDropping); 
		this.buildDropSourceCombo(parentComposite);

		// Create Database Schemas
		TriStateCheckBox createDatabaseSchemasCheckBox = this.buildCreateDatabaseSchemasCheckBox(parentComposite);
		GridData gridData = new GridData();
		gridData.horizontalSpan = 2;
		createDatabaseSchemasCheckBox.getCheckBox().setLayoutData(gridData);
		
		// ScriptsCreateTarget
		this.addLabel(parentComposite, JptJpaUiPersistenceMessages2_1.SchemaGenerationComposite_scriptsCreateTarget);
		this.addText(parentComposite, this.buildScriptsCreateTargetHolder());
		
		// ScriptsDropTarget
		this.addLabel(parentComposite, JptJpaUiPersistenceMessages2_1.SchemaGenerationComposite_scriptsDropTarget);
		this.addText(parentComposite, this.buildScriptsDropTargetHolder());
		
		// DatabaseProductName
		this.addLabel(parentComposite, JptJpaUiPersistenceMessages2_1.SchemaGenerationComposite_databaseProductName);
		this.addText(parentComposite, this.buildDatabaseProductNameHolder());
		
		// DatabaseMajorVersion
		this.addLabel(parentComposite, JptJpaUiPersistenceMessages2_1.SchemaGenerationComposite_databaseMajorVersion);
		this.addText(parentComposite, this.buildDatabaseMajorVersionHolder());
		
		// DatabaseMinorVersion
		this.addLabel(parentComposite, JptJpaUiPersistenceMessages2_1.SchemaGenerationComposite_databaseMinorVersion);
		this.addText(parentComposite, this.buildDatabaseMinorVersionHolder());

		// CreateScriptSource
		this.addLabel(parentComposite, JptJpaUiPersistenceMessages2_1.SchemaGenerationComposite_createScriptSource);
		this.addText(parentComposite, this.buildCreateScriptSourceHolder());

		// DropScriptSource
		this.addLabel(parentComposite, JptJpaUiPersistenceMessages2_1.SchemaGenerationComposite_dropScriptSource);
		this.addText(parentComposite, this.buildDropScriptSourceHolder());

		// Connection
		this.addLabel(parentComposite, JptJpaUiPersistenceMessages2_1.SchemaGenerationComposite_connection);
		this.addText(parentComposite, this.buildConnectionHolder());
	}

	// ********** ScriptsCreateTarget **********
	
	private ModifiablePropertyValueModel<String> buildScriptsCreateTargetHolder() {
		return new PropertyAspectAdapter<SchemaGeneration2_1, String>(
			this.getSubjectHolder(), 
			SchemaGeneration2_1.SCRIPTS_CREATE_TARGET_PROPERTY)
		{
			@Override
			protected String buildValue_() {
				return this.subject.getScriptsCreateTarget();
			}
	
			@Override
			protected void setValue_(String value) {
				if (value.length() == 0) {
					value = null;
				}
				this.subject.setScriptsCreateTarget(value);
			}
		};
	}

	// ********** ScriptsDropTarget **********
	
	private ModifiablePropertyValueModel<String> buildScriptsDropTargetHolder() {
		return new PropertyAspectAdapter<SchemaGeneration2_1, String>(
			this.getSubjectHolder(), 
			SchemaGeneration2_1.SCRIPTS_DROP_TARGET_PROPERTY)
		{
			@Override
			protected String buildValue_() {
				return this.subject.getScriptsDropTarget();
			}
	
			@Override
			protected void setValue_(String value) {
				if (value.length() == 0) {
					value = null;
				}
				this.subject.setScriptsDropTarget(value);
			}
		};
	}

	// ********** DatabaseProductName **********
	
	private ModifiablePropertyValueModel<String> buildDatabaseProductNameHolder() {
		return new PropertyAspectAdapter<SchemaGeneration2_1, String>(
			this.getSubjectHolder(), 
			SchemaGeneration2_1.DATABASE_PRODUCT_NAME_PROPERTY)
		{
			@Override
			protected String buildValue_() {
				return this.subject.getDatabaseProductName();
			}
	
			@Override
			protected void setValue_(String value) {
				if (value.length() == 0) {
					value = null;
				}
				this.subject.setDatabaseProductName(value);
			}
		};
	}

	// ********** DatabaseMajorVersion **********
	
	private ModifiablePropertyValueModel<String> buildDatabaseMajorVersionHolder() {
		return new PropertyAspectAdapter<SchemaGeneration2_1, String>(
			this.getSubjectHolder(), 
			SchemaGeneration2_1.DATABASE_MAJOR_VERSION_PROPERTY)
		{
			@Override
			protected String buildValue_() {
				return this.subject.getDatabaseMajorVersion();
			}
	
			@Override
			protected void setValue_(String value) {
				if (value.length() == 0) {
					value = null;
				}
				this.subject.setDatabaseMajorVersion(value);
			}
		};
	}

	// ********** DatabaseMinorVersion **********
	
	private ModifiablePropertyValueModel<String> buildDatabaseMinorVersionHolder() {
		return new PropertyAspectAdapter<SchemaGeneration2_1, String>(
			this.getSubjectHolder(), 
			SchemaGeneration2_1.DATABASE_MINOR_VERSION_PROPERTY)
		{
			@Override
			protected String buildValue_() {
				return this.subject.getDatabaseMinorVersion();
			}
	
			@Override
			protected void setValue_(String value) {
				if (value.length() == 0) {
					value = null;
				}
				this.subject.setDatabaseMinorVersion(value);
			}
		};
	}

	// ********** CreateScriptSource **********
	
	private ModifiablePropertyValueModel<String> buildCreateScriptSourceHolder() {
		return new PropertyAspectAdapter<SchemaGeneration2_1, String>(
			this.getSubjectHolder(), 
			SchemaGeneration2_1.CREATE_SCRIPT_SOURCE_PROPERTY)
		{
			@Override
			protected String buildValue_() {
				return this.subject.getCreateScriptSource();
			}
	
			@Override
			protected void setValue_(String value) {
				if (value.length() == 0) {
					value = null;
				}
				this.subject.setCreateScriptSource(value);
			}
		};
	}

	// ********** DropScriptSource **********
	
	private ModifiablePropertyValueModel<String> buildDropScriptSourceHolder() {
		return new PropertyAspectAdapter<SchemaGeneration2_1, String>(
			this.getSubjectHolder(), 
			SchemaGeneration2_1.DROP_SCRIPT_SOURCE_PROPERTY)
		{
			@Override
			protected String buildValue_() {
				return this.subject.getDropScriptSource();
			}
	
			@Override
			protected void setValue_(String value) {
				if (value.length() == 0) {
					value = null;
				}
				this.subject.setDropScriptSource(value);
			}
		};
	}

	// ********** Connection **********
	
	private ModifiablePropertyValueModel<String> buildConnectionHolder() {
		return new PropertyAspectAdapter<SchemaGeneration2_1, String>(
			this.getSubjectHolder(), 
			SchemaGeneration2_1.CONNECTION_PROPERTY)
		{
			@Override
			protected String buildValue_() {
				return this.subject.getConnection();
			}

			@Override
			protected void setValue_(String value) {
				if (value.length() == 0) {
					value = null;
				}
				this.subject.setConnection(value);
			}
		};
	}


	// ********* Create Database Schemas **********

	private TriStateCheckBox buildCreateDatabaseSchemasCheckBox(Composite container) {
		return this.addTriStateCheckBoxWithDefault(
			container,
			JptJpaUiPersistenceMessages2_1.SchemaGenerationComposite_createDatabaseSchemasLabel,
			this.buildCreateDatabaseSchemasHolder(),
			this.buildCreateDatabaseSchemasStringHolder(),
			null		// TODO HelpContextIds
		);
	}
	
	private ModifiablePropertyValueModel<Boolean> buildCreateDatabaseSchemasHolder() {
		return new PropertyAspectAdapter<SchemaGeneration2_1, Boolean>(
			this.getSubjectHolder(), 
			SchemaGeneration2_1.CREATE_DATABASE_SCHEMAS_PROPERTY)
		{
			@Override
			protected Boolean buildValue_() {
				return this.subject.getCreateDatabaseSchemas();
			}

			@Override
			protected void setValue_(Boolean value) {
				this.subject.setCreateDatabaseSchemas(value);
			}
		};
	}

	private PropertyValueModel<String> buildCreateDatabaseSchemasStringHolder() {
		return new TransformationPropertyValueModel<Boolean, String>(
			this.buildDefaultCreateDatabaseSchemasHolder()) 
		{
			@Override
			protected String transform(Boolean value) {
				if (value != null) {
					String defaultStringValue = value.booleanValue() ? JptCommonUiMessages.BOOLEAN_TRUE : JptCommonUiMessages.BOOLEAN_FALSE;
					return NLS.bind(JptJpaUiPersistenceMessages2_1.SchemaGenerationComposite_defaultCreateDatabaseSchemasLabel, defaultStringValue);
				}
				return JptJpaUiPersistenceMessages2_1.SchemaGenerationComposite_createDatabaseSchemasLabel;
			}
		};
	}
	
	private PropertyValueModel<Boolean> buildDefaultCreateDatabaseSchemasHolder() {
		return new PropertyAspectAdapter<SchemaGeneration2_1, Boolean>(
			this.getSubjectHolder(),
			SchemaGeneration2_1.CREATE_DATABASE_SCHEMAS_PROPERTY)
		{
			@Override
			protected Boolean buildValue_() {
				if (this.subject.getCreateDatabaseSchemas() != null) {
					return null;
				}
				return this.subject.getDefaultCreateDatabaseSchemas();
			}
		};
	}
	
	// ********** DatabaseAction **********
	private EnumFormComboViewer<SchemaGeneration2_1, SchemaGenerationAction2_1> buildDatabaseActionCombo(Composite container) {

		return new EnumFormComboViewer<SchemaGeneration2_1, SchemaGenerationAction2_1>(this, 
											this.getSubjectHolder(), 
											container) {
			@Override
			protected void addPropertyNames(Collection<String> propertyNames) {
				super.addPropertyNames(propertyNames);
				propertyNames.add(SchemaGeneration2_1.SCHEMAGEN_DATABASE_ACTION_PROPERTY);
			}

			@Override
			protected SchemaGenerationAction2_1[] getChoices() {
				return SchemaGenerationAction2_1.values();
			}

			@Override
			protected SchemaGenerationAction2_1 getDefaultValue() {
				return this.getSubject().getDefaultSchemaGenDatabaseAction();
			}

			@Override
			protected String displayString(SchemaGenerationAction2_1 value) {
				switch (value) {
					case none :
						return JptJpaUiPersistenceMessages2_1.SchemaGenerationAction_none;
					case create :
						return JptJpaUiPersistenceMessages2_1.SchemaGenerationAction_create;
					case drop_and_create :
						return JptJpaUiPersistenceMessages2_1.SchemaGenerationAction_drop_and_create;
					case drop :
						return JptJpaUiPersistenceMessages2_1.SchemaGenerationAction_drop;
					default :
						throw new IllegalStateException();
				}
			}

			@Override
			protected SchemaGenerationAction2_1 getValue() {
				return this.getSubject().getSchemaGenDatabaseAction();
			}

			@Override
			protected void setValue(SchemaGenerationAction2_1 value) {
				this.getSubject().setSchemaGenDatabaseAction(value);
			}
		};
	}
	
	// ********** ScriptsAction **********
	private EnumFormComboViewer<SchemaGeneration2_1, SchemaGenerationAction2_1> buildScriptsActionCombo(Composite container) {
	
		return new EnumFormComboViewer<SchemaGeneration2_1, SchemaGenerationAction2_1>(this, 
											this.getSubjectHolder(), 
											container) {
			@Override
			protected void addPropertyNames(Collection<String> propertyNames) {
				super.addPropertyNames(propertyNames);
				propertyNames.add(SchemaGeneration2_1.SCHEMAGEN_SCRIPTS_ACTION_PROPERTY);
			}
	
			@Override
			protected SchemaGenerationAction2_1[] getChoices() {
				return SchemaGenerationAction2_1.values();
			}
	
			@Override
			protected SchemaGenerationAction2_1 getDefaultValue() {
				return this.getSubject().getDefaultSchemaGenScriptsAction();
			}
	
			@Override
			protected String displayString(SchemaGenerationAction2_1 value) {
				switch (value) {
					case none :
						return JptJpaUiPersistenceMessages2_1.SchemaGenerationAction_none;
					case create :
						return JptJpaUiPersistenceMessages2_1.SchemaGenerationAction_create;
					case drop_and_create :
						return JptJpaUiPersistenceMessages2_1.SchemaGenerationAction_drop_and_create;
					case drop :
						return JptJpaUiPersistenceMessages2_1.SchemaGenerationAction_drop;
					default :
						throw new IllegalStateException();
				}
			}
	
			@Override
			protected SchemaGenerationAction2_1 getValue() {
				return this.getSubject().getSchemaGenScriptsAction();
			}
	
			@Override
			protected void setValue(SchemaGenerationAction2_1 value) {
				this.getSubject().setSchemaGenScriptsAction(value);
			}
	
	//		@Override
	//		protected String getHelpId() {
	//			return 	// TODO - 
	//		}
		};
	}		
	
	// ********** CreateSource **********
	private EnumFormComboViewer<SchemaGeneration2_1, SchemaGenerationTarget2_1> buildCreateSourceCombo(Composite container) {
	
		return new EnumFormComboViewer<SchemaGeneration2_1, SchemaGenerationTarget2_1>(this, 
											this.getSubjectHolder(), 
											container) {
			@Override
			protected void addPropertyNames(Collection<String> propertyNames) {
				super.addPropertyNames(propertyNames);
				propertyNames.add(SchemaGeneration2_1.SCHEMAGEN_CREATE_SOURCE_PROPERTY);
			}
	
			@Override
			protected SchemaGenerationTarget2_1[] getChoices() {
				return SchemaGenerationTarget2_1.values();
			}
	
			@Override
			protected SchemaGenerationTarget2_1 getDefaultValue() {
				return this.getSubject().getDefaultSchemaGenCreateSource();
			}
	
			@Override
			protected String displayString(SchemaGenerationTarget2_1 value) {
				switch (value) {
					case metadata :
						return JptJpaUiPersistenceMessages2_1.SchemaGenerationTarget_metadata;
					case script :
						return JptJpaUiPersistenceMessages2_1.SchemaGenerationTarget_script;
					case metadata_then_script :
						return JptJpaUiPersistenceMessages2_1.SchemaGenerationTarget_metadata_then_script;
					case script_then_metadata :
						return JptJpaUiPersistenceMessages2_1.SchemaGenerationTarget_script_then_metadata;
					default :
						throw new IllegalStateException();
				}
			}
	
			@Override
			protected SchemaGenerationTarget2_1 getValue() {
				return this.getSubject().getSchemaGenCreateSource();
			}
	
			@Override
			protected void setValue(SchemaGenerationTarget2_1 value) {
				this.getSubject().setSchemaGenCreateSource(value);
			}
	
	//		@Override
	//		protected String getHelpId() {
	//			return 	// TODO - 
	//		}
		};
	}
	
	// ********** DropSource **********
	private EnumFormComboViewer<SchemaGeneration2_1, SchemaGenerationTarget2_1> buildDropSourceCombo(Composite container) {

		return new EnumFormComboViewer<SchemaGeneration2_1, SchemaGenerationTarget2_1>(this, 
											this.getSubjectHolder(), 
											container) {
			@Override
			protected void addPropertyNames(Collection<String> propertyNames) {
				super.addPropertyNames(propertyNames);
				propertyNames.add(SchemaGeneration2_1.SCHEMAGEN_DROP_SOURCE_PROPERTY);
			}

			@Override
			protected SchemaGenerationTarget2_1[] getChoices() {
				return SchemaGenerationTarget2_1.values();
			}

			@Override
			protected SchemaGenerationTarget2_1 getDefaultValue() {
				return this.getSubject().getDefaultSchemaGenDropSource();
			}

			@Override
			protected String displayString(SchemaGenerationTarget2_1 value) {
				switch (value) {
					case metadata :
						return JptJpaUiPersistenceMessages2_1.SchemaGenerationTarget_metadata;
					case script :
						return JptJpaUiPersistenceMessages2_1.SchemaGenerationTarget_script;
					case metadata_then_script :
						return JptJpaUiPersistenceMessages2_1.SchemaGenerationTarget_metadata_then_script;
					case script_then_metadata :
						return JptJpaUiPersistenceMessages2_1.SchemaGenerationTarget_script_then_metadata;
					default :
						throw new IllegalStateException();
				}
			}

			@Override
			protected SchemaGenerationTarget2_1 getValue() {
				return this.getSubject().getSchemaGenDropSource();
			}

			@Override
			protected void setValue(SchemaGenerationTarget2_1 value) {
				this.getSubject().setSchemaGenDropSource(value);
			}

	//		@Override
	//		protected String getHelpId() {
	//			return 	// TODO - 
	//		}
		};
	}
	
}