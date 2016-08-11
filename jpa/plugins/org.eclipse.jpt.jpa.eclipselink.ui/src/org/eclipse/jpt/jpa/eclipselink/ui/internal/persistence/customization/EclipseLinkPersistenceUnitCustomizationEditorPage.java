/*******************************************************************************
 * Copyright (c) 2009, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.persistence.customization;

import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.ui.internal.widgets.ClassChooserPane;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.ui.internal.widgets.TriStateCheckBox;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapterXXXX;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyValueModelTools;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.transformer.Transformer;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.EclipseLinkCustomization;
import org.eclipse.jpt.jpa.eclipselink.ui.JptJpaEclipseLinkUiMessages;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.EclipseLinkHelpContextIds;
import org.eclipse.jpt.jpa.ui.internal.TriStateCheckBoxLabelModelAdapter;
import org.eclipse.jpt.jpa.ui.internal.TriStateCheckBoxLabelModelStringTransformer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.Hyperlink;
import org.eclipse.ui.forms.widgets.Section;

public class EclipseLinkPersistenceUnitCustomizationEditorPage<T extends EclipseLinkCustomization>
	extends Pane<T>
{
	public EclipseLinkPersistenceUnitCustomizationEditorPage(
			PropertyValueModel<T> subjectModel,
			Composite parentComposite,
            WidgetFactory widgetFactory,
            ResourceManager resourceManager) {
		super(subjectModel, parentComposite, widgetFactory, resourceManager);
	}

	@Override
	protected Composite addComposite(Composite container) {
		GridLayout layout = new GridLayout(2, true); //2 columns equal width
		layout.marginHeight = 0;
		layout.marginWidth  = 0;
		layout.marginTop    = 0;
		layout.marginLeft   = 0;
		layout.marginBottom = 0;
		layout.marginRight  = 0;

		return this.addPane(container, layout);
	}

	@Override
	protected void initializeLayout(Composite parent) {
		Section weavingSection = this.getWidgetFactory().createSection(parent, ExpandableComposite.TITLE_BAR);
		weavingSection.setText(JptJpaEclipseLinkUiMessages.PERSISTENCE_XML_CUSTOMIZATION_TAB_WEAVING_PROPERTIES_GROUP_BOX);
		weavingSection.setClient(this.initializeWeavingSection(weavingSection));
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.verticalAlignment = SWT.TOP;
		weavingSection.setLayoutData(gridData);

		Section customizersSection = this.getWidgetFactory().createSection(parent, ExpandableComposite.TITLE_BAR);
		customizersSection.setText(JptJpaEclipseLinkUiMessages.PERSISTENCE_XML_CUSTOMIZATION_TAB_CUSTOMIZERS_SECTION);
		customizersSection.setClient(this.initializeCustomizersSection(customizersSection));
		gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.verticalAlignment = SWT.TOP;
		gridData.verticalSpan = 2;
		customizersSection.setLayoutData(gridData);

		Section otherSection = this.getWidgetFactory().createSection(parent, ExpandableComposite.TITLE_BAR);
		otherSection.setText(JptJpaEclipseLinkUiMessages.PERSISTENCE_XML_CUSTOMIZATION_TAB_OTHER_SECTION);
		otherSection.setClient(this.initializeOtherSection(otherSection));
		gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.verticalAlignment = SWT.TOP;
		otherSection.setLayoutData(gridData);
	}

	protected Control initializeWeavingSection(Section weavingSection) {
		return new EclipseLinkWeavingPropertiesComposite(this, weavingSection).getControl();
	}

	@SuppressWarnings("unused")
	protected Control initializeOtherSection(Section otherSection) {
		Composite container = this.addSubPane(otherSection, 2, 0, 0, 0, 0);

		// Validation Only
		TriStateCheckBox validationOnlyCheckBox = this.addTriStateCheckBoxWithDefault(
			container,
			JptJpaEclipseLinkUiMessages.PERSISTENCE_XML_CUSTOMIZATION_TAB_VALIDATION_ONLY_LABEL,
			this.buildValidationOnlyModel(),
			this.buildValidationOnlyStringModel(),
			EclipseLinkHelpContextIds.PERSISTENCE_CUSTOMIZATION
		);
		GridData gridData = new GridData();
		gridData.horizontalSpan = 2;
		validationOnlyCheckBox.getCheckBox().setLayoutData(gridData);

		// Mapping Files Validate Schema

		TriStateCheckBox validateSchemaCheckBox = this.addTriStateCheckBoxWithDefault(
			container,
			JptJpaEclipseLinkUiMessages.PERSISTENCE_XML_CUSTOMIZATION_TAB_VALIDATE_SCHEMA_LABEL,
			this.buildValidateSchemaModel(),
			this.buildValidateSchemaStringModel(),
			EclipseLinkHelpContextIds.PERSISTENCE_CUSTOMIZATION
		);
		gridData = new GridData();
		gridData.horizontalSpan = 2;
		validateSchemaCheckBox.getCheckBox().setLayoutData(gridData);

		// Throw Exceptions
		TriStateCheckBox throwExceptionsCheckBox = this.addTriStateCheckBoxWithDefault(
			container,
			JptJpaEclipseLinkUiMessages.PERSISTENCE_XML_CUSTOMIZATION_TAB_THROW_EXCEPTIONS_LABEL,
			this.buildThrowExceptionsModel(),
			this.buildThrowExceptionsStringModel(),
			EclipseLinkHelpContextIds.PERSISTENCE_CUSTOMIZATION
		);
		gridData = new GridData();
		gridData.horizontalSpan = 2;
		throwExceptionsCheckBox.getCheckBox().setLayoutData(gridData);

		// Exception Handler
		Hyperlink exceptionHandlerHyperlink = this.addHyperlink(container, JptJpaEclipseLinkUiMessages.PERSISTENCE_XML_CUSTOMIZATION_TAB_EXCEPTION_HANDLER_LABEL);
		this.initializeExceptionHandlerClassChooser(container, exceptionHandlerHyperlink);

		// Profiler:
		Hyperlink profilerHyperlink = this.addHyperlink(container, JptJpaEclipseLinkUiMessages.PERSISTENCE_XML_CUSTOMIZATION_TAB_PROFILER_LABEL);
		new EclipseLinkProfilerClassChooser(this, container, profilerHyperlink);
		
		return container;
	}

	@SuppressWarnings("unused")
	protected Control initializeCustomizersSection(Section customizersSection) {
		Composite container = this.addSubPane(customizersSection);

		// Session Customizer
		new EclipseLinkSessionCustomizersComposite(this, container);

		this.buildEntityListComposite(container);

		return container;
	}

	@SuppressWarnings("unused")
	protected void buildEntityListComposite(Composite parent) {
		new EclipseLinkEntityListComposite(this, parent); 
	}


	//********* validation only ***********

	private ModifiablePropertyValueModel<Boolean> buildValidationOnlyModel() {
		return new PropertyAspectAdapterXXXX<EclipseLinkCustomization, Boolean>(getSubjectHolder(), EclipseLinkCustomization.VALIDATION_ONLY_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				return this.subject.getValidationOnly();
			}

			@Override
			protected void setValue_(Boolean value) {
				this.subject.setValidationOnly(value);
			}
		};
	}

	private PropertyValueModel<String> buildValidationOnlyStringModel() {
		return PropertyValueModelTools.transform_(this.buildDefaultValidationOnlyModel(), VALIDATION_ONLY_TRANSFORMER);
	}

	private static final Transformer<Boolean, String> VALIDATION_ONLY_TRANSFORMER = new TriStateCheckBoxLabelModelStringTransformer(
			JptJpaEclipseLinkUiMessages.PERSISTENCE_XML_CUSTOMIZATION_TAB_VALIDATION_ONLY_LABEL_DEFAULT,
			JptJpaEclipseLinkUiMessages.PERSISTENCE_XML_CUSTOMIZATION_TAB_VALIDATION_ONLY_LABEL
		);

	private PropertyValueModel<Boolean> buildDefaultValidationOnlyModel() {
		return TriStateCheckBoxLabelModelAdapter.adaptSubjectModelAspects(
				this.getSubjectHolder(),
				EclipseLinkCustomization.VALIDATION_ONLY_PROPERTY,
				c -> c.getValidationOnly(),
				EclipseLinkCustomization.DEFAULT_VALIDATION_ONLY_PROPERTY,
				c -> c.getDefaultValidationOnly()
			);
	}


	//********* validate schema ***********

	private ModifiablePropertyValueModel<Boolean> buildValidateSchemaModel() {
		return new PropertyAspectAdapterXXXX<EclipseLinkCustomization, Boolean>(getSubjectHolder(), EclipseLinkCustomization.VALIDATE_SCHEMA_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				return this.subject.getValidateSchema();
			}

			@Override
			protected void setValue_(Boolean value) {
				this.subject.setValidateSchema(value);
			}
		};
	}

	private PropertyValueModel<String> buildValidateSchemaStringModel() {
		return PropertyValueModelTools.transform_(this.buildDefaultValidateSchemaModel(), VALIDATE_SCHEMA_TRANSFORMER);
	}

	private static final Transformer<Boolean, String> VALIDATE_SCHEMA_TRANSFORMER = new TriStateCheckBoxLabelModelStringTransformer(
			JptJpaEclipseLinkUiMessages.PERSISTENCE_XML_CUSTOMIZATION_TAB_VALIDATE_SCHEMA_LABEL_DEFAULT,
			JptJpaEclipseLinkUiMessages.PERSISTENCE_XML_CUSTOMIZATION_TAB_VALIDATE_SCHEMA_LABEL
		);

	private PropertyValueModel<Boolean> buildDefaultValidateSchemaModel() {
		return TriStateCheckBoxLabelModelAdapter.adaptSubjectModelAspects(
				this.getSubjectHolder(),
				EclipseLinkCustomization.VALIDATE_SCHEMA_PROPERTY,
				c -> c.getValidateSchema(),
				EclipseLinkCustomization.DEFAULT_VALIDATE_SCHEMA_PROPERTY,
				c -> c.getDefaultValidateSchema()
			);
	}


	//********* throw exceptions ***********

	private ModifiablePropertyValueModel<Boolean> buildThrowExceptionsModel() {
		return new PropertyAspectAdapterXXXX<EclipseLinkCustomization, Boolean>(getSubjectHolder(), EclipseLinkCustomization.THROW_EXCEPTIONS_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				return this.subject.getThrowExceptions();
			}
			@Override
			protected void setValue_(Boolean value) {
				this.subject.setThrowExceptions(value);
			}
		};
	}

	private PropertyValueModel<String> buildThrowExceptionsStringModel() {
		return PropertyValueModelTools.transform_(this.buildDefaultThrowExceptionsModel(), THROW_EXCEPTIONS_TRANSFORMER);
	}

	private static final Transformer<Boolean, String> THROW_EXCEPTIONS_TRANSFORMER = new TriStateCheckBoxLabelModelStringTransformer(
			JptJpaEclipseLinkUiMessages.PERSISTENCE_XML_CUSTOMIZATION_TAB_THROW_EXCEPTIONS_LABEL_DEFAULT,
			JptJpaEclipseLinkUiMessages.PERSISTENCE_XML_CUSTOMIZATION_TAB_THROW_EXCEPTIONS_LABEL
		);

	private PropertyValueModel<Boolean> buildDefaultThrowExceptionsModel() {
		return TriStateCheckBoxLabelModelAdapter.adaptSubjectModelAspects(
				this.getSubjectHolder(),
				EclipseLinkCustomization.THROW_EXCEPTIONS_PROPERTY,
				c -> c.getThrowExceptions(),
				EclipseLinkCustomization.DEFAULT_THROW_EXCEPTIONS_PROPERTY,
				c -> c.getDefaultThrowExceptions()
			);
	}


	//********* exception handler ***********

	private ClassChooserPane<EclipseLinkCustomization> initializeExceptionHandlerClassChooser(Composite container, Hyperlink hyperlink) {

		return new ClassChooserPane<EclipseLinkCustomization>(this, container, hyperlink) {

			@Override
			protected ModifiablePropertyValueModel<String> buildTextModel() {
				return new PropertyAspectAdapterXXXX<EclipseLinkCustomization, String>(
							this.getSubjectHolder(), EclipseLinkCustomization.EXCEPTION_HANDLER_PROPERTY) {
					@Override
					protected String buildValue_() {
						return this.subject.getExceptionHandler();
					}

					@Override
					protected void setValue_(String value) {

						if (value.length() == 0) {
							value = null;
						}
						this.subject.setExceptionHandler(value);
					}
				};
			}

			@Override
			protected String getClassName() {
				return this.getSubject().getExceptionHandler();
			}

			@Override
			protected IJavaProject getJavaProject() {
				return getSubject().getJpaProject().getJavaProject();
			}

			@Override
			protected void setClassName(String className) {
				this.getSubject().setExceptionHandler(className);
			}

			@Override
			protected String getSuperInterfaceName() {
				return EclipseLinkCustomization.ECLIPSELINK_EXCEPTION_HANDLER_CLASS_NAME;
			}
		};
	}
}
