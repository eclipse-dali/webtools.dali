/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.prefs;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.internal.ui.preferences.PropertyAndPreferencePage;
import org.eclipse.jdt.internal.ui.preferences.ScrolledPageContent;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.layout.PixelConverter;
import org.eclipse.jface.operation.IRunnableContext;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jpt.jpa.core.JpaPreferences;
import org.eclipse.jpt.jpa.core.validation.JptJpaCoreValidationMessages;
import org.eclipse.jpt.jpa.ui.JptJpaUiMessages;
import org.eclipse.jpt.jpa.ui.internal.plugin.JptJpaUiPlugin;
import org.eclipse.jpt.jpa.ui.prefs.validation.JptJpaUiPreferencesValidationMessages;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.forms.events.ExpansionAdapter;
import org.eclipse.ui.forms.events.ExpansionEvent;
import org.eclipse.ui.forms.widgets.ExpandableComposite;

/**
 * This page shows the Java Persistence validation options. It supports
 * workspace and project levels severities.
 *
 * @version 2.3
 * @since 2.2
 */
@SuppressWarnings("restriction")
public class JpaProblemSeveritiesPage extends PropertyAndPreferencePage {

	/**
	 * Severity level state is stored in this Map and is either committed or discarded
	 * based on user action.
	 * <br> key is the preferenceKey which is also the validation message key @see JpaValidationMessages.
	 * <br> value is a severity level - @see JpaValidationPreferences#ERROR WARNING INFO IGNORE
	 */
	Map<String, String> severityLevels;

	/**
	 * Default severity levels are stored here,  ERROR is the default default so only need
	 * to include WARNING, INFO, IGNORE in this Map. These will be displayed if neither the project
	 * or workspace preference applies.
	 * <br> key is the preferenceKey which is also the validation message key @see JpaValidationMessages.
	 * <br> value is a severity level - @see JpaValidationPreferences#ERROR WARNING INFO IGNORE
	 */
	private Map<String, String> defaultSeverities;

	/**
	 * The list of <code>Combo</code>s is cached in order to perform a revert of
	 * the properties.
	 */
	private List<Combo> combos;

	/**
	 * The list of <code>ExpandableComposite</code> is cached in order to save
	 * and restore the expansion state.
	 */
	private List<ExpandableComposite> expandablePanes;

	/**
	 * The position of the "Error" choice in the combo's model.
	 */
	private static final int ERROR_INDEX = 0;

	/**
	 * The position of the "Warning" choice in the combo's model.
	 */
	private static final int WARNING_INDEX = 1;

	/**
	 * The position of the "Info" choice in the combo's model.
	 */
	private static final int INFO_INDEX = 2;

	/**
	 * The position of the "Ignore" choice in the combo's model.
	 */
	private static final int IGNORE_INDEX = 3;

	/**
	 * The unique identifier for this page when it is shown in the IDE
	 * preferences dialog.
	 */
	private static final String JPT_PREFERENCES_PROBLEM_SEVERITIES_ID = "org.eclipse.jpt.jpa.ui.jpaProblemSeveritiesPreferences"; //$NON-NLS-1$

	/**
	 * The unique identifier for this page when it is shown in the project
	 * preferences dialog.
	 */
	private static final String JPT_PROPERTY_PAGES_PROBLEM_SEVERITIES_ID = "org.eclipse.jpt.jpa.ui.jpaProblemSeveritiesProperties"; //$NON-NLS-1$

	/**
	 * A constant used to store and retrieve the preference key (message ID) from
	 * the combo.
	 */
	private static final String PREFERENCE_KEY = "preferenceKey"; //$NON-NLS-1$

	/**
	 * The scrollable pane used to show the content of this page.
	 */
	private ScrolledPageContent scrollable;

	/**
	 * The possible choices which describes the severity of a single problem.
	 */
	private String[] severityDisplayStrings;

	/**
	 * Constant used to store the expansion state of each expandable pane.
	 */
	public static final String SETTINGS_EXPANDED = "expanded"; //$NON-NLS-1$

	/**
	 * The preference key used to retrieve the dialog settings where the expansion
	 * states have been stored.
	 */
	public static final String SETTINGS_SECTION_NAME = "JpaProblemSeveritiesPage"; //$NON-NLS-1$


	private Boolean hasProjectSpecificPreferences = null;

	/**
	 * Creates a new <code>JpaProblemSeveritiesPage</code>.
	 */
	public JpaProblemSeveritiesPage() {
		super();
		this.initialize();
	}

	@Override
	public void init(IWorkbench workbench) {
		this.setPreferenceStore(JptJpaUiPlugin.instance().getPreferenceStore());
		this.setDescription(JptJpaUiMessages.JpaProblemSeveritiesPage_Description);
	}

	protected void initialize() {
		this.combos = new ArrayList<Combo>();
		this.expandablePanes = new ArrayList<ExpandableComposite>();
		this.severityDisplayStrings = this.buildSeverityDisplayStrings();
		this.severityLevels = new HashMap<String, String>();
		this.defaultSeverities = this.buildDefaultSeverities();
	}

	//since most of our problems have a default severity of ERROR, we are just defining the WARNING, INFO, IGNORE cases
	protected Map<String, String> buildDefaultSeverities() {
		 Map<String, String> result = new HashMap<String, String>();

		 result.put(JptJpaCoreValidationMessages.PERSISTENCE_MULTIPLE_PERSISTENCE_UNITS, JpaPreferences.PROBLEM_WARNING);
		 result.put(JptJpaCoreValidationMessages.PROJECT_NO_CONNECTION, JpaPreferences.PROBLEM_WARNING);
		 result.put(JptJpaCoreValidationMessages.PROJECT_INVALID_CONNECTION, JpaPreferences.PROBLEM_WARNING);
		 result.put(JptJpaCoreValidationMessages.PROJECT_INACTIVE_CONNECTION, JpaPreferences.PROBLEM_WARNING);
		 result.put(JptJpaCoreValidationMessages.MAPPING_FILE_EXTRANEOUS_PERSISTENCE_UNIT_METADATA, JpaPreferences.PROBLEM_WARNING);
		 result.put(JptJpaCoreValidationMessages.PERSISTENT_TYPE_DUPLICATE_CLASS, JpaPreferences.PROBLEM_WARNING); //3.0 M7
		 result.put(JptJpaCoreValidationMessages.PERSISTENCE_UNIT_JAR_FILE_DEPLOYMENT_PATH_WARNING, JpaPreferences.PROBLEM_WARNING);
		 result.put(JptJpaCoreValidationMessages.PERSISTENT_ATTRIBUTE_INHERITED_ATTRIBUTES_NOT_SUPPORTED, JpaPreferences.PROBLEM_WARNING);
		 result.put(JptJpaCoreValidationMessages.PERSISTENT_TYPE_ANNOTATED_BUT_NOT_INCLUDED_IN_PERSISTENCE_UNIT, JpaPreferences.PROBLEM_WARNING);
		 result.put(JptJpaCoreValidationMessages.ENTITY_ABSTRACT_DISCRIMINATOR_VALUE_DEFINED, JpaPreferences.PROBLEM_WARNING);
		 result.put(JptJpaCoreValidationMessages.PERSISTENT_ATTRIBUTE_INVALID_VERSION_MAPPING_TYPE, JpaPreferences.PROBLEM_WARNING); //3.0 M7
		 result.put(JptJpaCoreValidationMessages.ENTITY_TABLE_PER_CLASS_DISCRIMINATOR_VALUE_DEFINED, JpaPreferences.PROBLEM_WARNING);
		 result.put(JptJpaCoreValidationMessages.ENTITY_TABLE_PER_CLASS_NOT_PORTABLE_ON_PLATFORM, JpaPreferences.PROBLEM_WARNING);

		 result.put(JptJpaCoreValidationMessages.XML_VERSION_NOT_LATEST, JpaPreferences.PROBLEM_INFO);
		 result.put(JptJpaCoreValidationMessages.PERSISTENCE_UNIT_REDUNDANT_CLASS, JpaPreferences.PROBLEM_INFO);
		 return result;
	}

	@Override
	protected Control createPreferenceContent(Composite parent) {

		PixelConverter pixelConverter = new PixelConverter(parent);

		// Create a container because the caller will set the GridData and we need
		// to change the heightHint of the first child and we also need to set the
		// font otherwise the layout won't be calculated correctly
		Composite container = new Composite(parent, SWT.NONE);
		container.setFont(parent.getFont());
		GridLayout layout = new GridLayout(1, false);
		layout.marginHeight = 0;
		layout.marginWidth  = 0;
		container.setLayout(layout);

		// Create the main composite of this page
		this.scrollable = new ScrolledPageContent(container);

		GridData gridData = new GridData(GridData.FILL, GridData.FILL, true, true);
		gridData.heightHint = pixelConverter.convertHeightInCharsToPixels(20);
		this.scrollable.setLayoutData(gridData);

		// Update the layout of the ScrolledPageContent's body
		layout = new GridLayout(1, false);
		layout.marginHeight = 0;
		layout.marginWidth  = 0;

		parent = this.scrollable.getBody();
		parent.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, true));
		parent.setLayout(layout);

		// Add each expandable category
		addProjectLevelCategory(parent);
		addPersistenceUnitLevelCategory(parent);
		addTypeLevelCategory(parent);
		addAttributeLevelCategory(parent);
		addDatabaseCategory(parent);
		addInheritanceStrategyCategory(parent);
		addQueriesGeneratorsCategory(parent);

		// Restore the expansion states
		restoreSectionExpansionStates(getDialogPreferences());

		return container;
	}

	protected void restoreSectionExpansionStates(IDialogSettings settings) {
		for (int index = this.expandablePanes.size(); --index >= 0; ) {
			ExpandableComposite expandablePane = this.expandablePanes.get(index);

			if (settings == null) {
				// Only expand the first node by default
				expandablePane.setExpanded(index == 0);
			}
			else {
				expandablePane.setExpanded(settings.getBoolean(SETTINGS_EXPANDED + index));
			}
		}
	}

	@Override
	public Point computeSize() {
		return this.doComputeSize();
	}

	//In each category below, entries are listed alphabetically.
	//If adding a new entry, please add it to the corresponding category at the right place.
	
	private void addProjectLevelCategory(Composite parent) {

		parent = addExpandableSection(parent, JptJpaUiPreferencesValidationMessages.PROJECT_LEVEL_CATEGORY);

		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.NO_JPA_PROJECT,                         					JptJpaCoreValidationMessages.NO_JPA_PROJECT);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.PERSISTENCE_MULTIPLE_PERSISTENCE_UNITS, 	JptJpaCoreValidationMessages.PERSISTENCE_MULTIPLE_PERSISTENCE_UNITS);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.PERSISTENCE_NO_PERSISTENCE_UNIT,        		JptJpaCoreValidationMessages.PERSISTENCE_NO_PERSISTENCE_UNIT);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.PERSISTENCE_XML_INVALID_CONTENT,        		JptJpaCoreValidationMessages.PERSISTENCE_XML_INVALID_CONTENT);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.PERSISTENCE_XML_UNSUPPORTED_CONTENT, 	JptJpaCoreValidationMessages.PERSISTENCE_XML_UNSUPPORTED_CONTENT); //3.0 M7
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.PROJECT_MULTIPLE_PERSISTENCE_XML,       		JptJpaCoreValidationMessages.PROJECT_MULTIPLE_PERSISTENCE_XML);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.PROJECT_INACTIVE_CONNECTION,            			JptJpaCoreValidationMessages.PROJECT_INACTIVE_CONNECTION);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.PROJECT_INVALID_CONNECTION,             			JptJpaCoreValidationMessages.PROJECT_INVALID_CONNECTION);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.PROJECT_INVALID_LIBRARY_PROVIDER,       		JptJpaCoreValidationMessages.PROJECT_INVALID_LIBRARY_PROVIDER); //3.0 M7
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.PROJECT_NO_CONNECTION,                  			JptJpaCoreValidationMessages.PROJECT_NO_CONNECTION);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.PROJECT_NO_PERSISTENCE_XML,             			JptJpaCoreValidationMessages.PROJECT_NO_PERSISTENCE_XML);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.XML_VERSION_NOT_LATEST,                 			JptJpaCoreValidationMessages.XML_VERSION_NOT_LATEST);
	}

	private void addPersistenceUnitLevelCategory(Composite parent) {

		parent = addExpandableSection(parent, JptJpaUiPreferencesValidationMessages.PERSISTENCE_UNIT_LEVEL_CATEGORY);

		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.MAPPING_FILE_EXTRANEOUS_PERSISTENCE_UNIT_METADATA,    	JptJpaCoreValidationMessages.MAPPING_FILE_EXTRANEOUS_PERSISTENCE_UNIT_METADATA);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.PERSISTENT_TYPE_DUPLICATE_CLASS,                               			JptJpaCoreValidationMessages.PERSISTENT_TYPE_DUPLICATE_CLASS);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.PERSISTENCE_UNIT_DUPLICATE_CLASS,                               			JptJpaCoreValidationMessages.PERSISTENCE_UNIT_DUPLICATE_CLASS);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.PERSISTENCE_UNIT_DUPLICATE_JAR_FILE,                            			JptJpaCoreValidationMessages.PERSISTENCE_UNIT_DUPLICATE_JAR_FILE);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.PERSISTENCE_UNIT_DUPLICATE_MAPPING_FILE,                        		JptJpaCoreValidationMessages.PERSISTENCE_UNIT_DUPLICATE_MAPPING_FILE);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.PERSISTENCE_UNIT_INVALID_CLASS,                                 				JptJpaCoreValidationMessages.PERSISTENCE_UNIT_INVALID_CLASS);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.PERSISTENCE_UNIT_INVALID_MAPPING_FILE,                          		JptJpaCoreValidationMessages.PERSISTENCE_UNIT_INVALID_MAPPING_FILE);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.PERSISTENCE_UNIT_JAR_FILE_DEPLOYMENT_PATH_WARNING,    	JptJpaCoreValidationMessages.PERSISTENCE_UNIT_JAR_FILE_DEPLOYMENT_PATH_WARNING);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.PERSISTENCE_UNIT_NONEXISTENT_CLASS,                             		JptJpaCoreValidationMessages.PERSISTENCE_UNIT_NONEXISTENT_CLASS);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.PERSISTENCE_UNIT_LISTED_CLASS_IS_AN_INTERFACE,                             		JptJpaCoreValidationMessages.PERSISTENCE_UNIT_LISTED_CLASS_IS_AN_INTERFACE);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.PERSISTENCE_UNIT_LISTED_CLASS_IS_AN_ENUM,                             		JptJpaCoreValidationMessages.PERSISTENCE_UNIT_LISTED_CLASS_IS_AN_ENUM);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.PERSISTENCE_UNIT_NONEXISTENT_JAR_FILE,                          		JptJpaCoreValidationMessages.PERSISTENCE_UNIT_NONEXISTENT_JAR_FILE);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.PERSISTENCE_UNIT_NONEXISTENT_MAPPING_FILE,                      	JptJpaCoreValidationMessages.PERSISTENCE_UNIT_NONEXISTENT_MAPPING_FILE);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.PERSISTENCE_UNIT_REDUNDANT_CLASS,                               			JptJpaCoreValidationMessages.PERSISTENCE_UNIT_REDUNDANT_CLASS);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.PERSISTENCE_UNIT_UNSPECIFIED_CLASS,                             			JptJpaCoreValidationMessages.PERSISTENCE_UNIT_UNSPECIFIED_CLASS);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.PERSISTENCE_UNIT_UNSPECIFIED_JAR_FILE,                          			JptJpaCoreValidationMessages.PERSISTENCE_UNIT_UNSPECIFIED_JAR_FILE);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.PERSISTENCE_UNIT_UNSPECIFIED_MAPPING_FILE,                      		JptJpaCoreValidationMessages.PERSISTENCE_UNIT_UNSPECIFIED_MAPPING_FILE);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.PERSISTENCE_UNIT_UNSUPPORTED_MAPPING_FILE_CONTENT,       JptJpaCoreValidationMessages.PERSISTENCE_UNIT_UNSUPPORTED_MAPPING_FILE_CONTENT);
	}

	private void addTypeLevelCategory(Composite parent) {

		parent = addExpandableSection(parent, JptJpaUiPreferencesValidationMessages.TYPE_LEVEL_CATEGORY);

		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.ENTITY_NAME_DUPLICATED,																			JptJpaCoreValidationMessages.ENTITY_NAME_DUPLICATED); //3.0 M7
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.ENTITY_NAME_MISSING,																				JptJpaCoreValidationMessages.ENTITY_NAME_MISSING); //3.0 M7
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.ENTITY_NO_PK,																								JptJpaCoreValidationMessages.ENTITY_NO_PK);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.ENTITY_NON_ROOT_ID_ATTRIBUTE_SPECIFIED,												JptJpaCoreValidationMessages.ENTITY_NON_ROOT_ID_ATTRIBUTE_SPECIFIED); //3.0 M7
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.ENTITY_NON_ROOT_ID_CLASS_SPECIFIED,														JptJpaCoreValidationMessages.ENTITY_NON_ROOT_ID_CLASS_SPECIFIED); //3.0 M7
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.PERSISTENT_TYPE_ANNOTATED_BUT_NOT_INCLUDED_IN_PERSISTENCE_UNIT,	JptJpaCoreValidationMessages.PERSISTENT_TYPE_ANNOTATED_BUT_NOT_INCLUDED_IN_PERSISTENCE_UNIT);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.PERSISTENT_TYPE_MAPPED_BUT_NOT_INCLUDED_IN_PERSISTENCE_UNIT,		JptJpaCoreValidationMessages.PERSISTENT_TYPE_MAPPED_BUT_NOT_INCLUDED_IN_PERSISTENCE_UNIT);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.PERSISTENT_TYPE_UNRESOLVED_CLASS,														JptJpaCoreValidationMessages.PERSISTENT_TYPE_UNRESOLVED_CLASS);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.PERSISTENT_TYPE_UNSPECIFIED_CLASS,														JptJpaCoreValidationMessages.PERSISTENT_TYPE_UNSPECIFIED_CLASS);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.TYPE_MAPPING_CLASS_MISSING_NO_ARG_CONSTRUCTOR,								JptJpaCoreValidationMessages.TYPE_MAPPING_CLASS_MISSING_NO_ARG_CONSTRUCTOR);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.TYPE_MAPPING_CLASS_PRIVATE_NO_ARG_CONSTRUCTOR,								JptJpaCoreValidationMessages.TYPE_MAPPING_CLASS_PRIVATE_NO_ARG_CONSTRUCTOR);		
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.TYPE_MAPPING_FINAL_CLASS,																		JptJpaCoreValidationMessages.TYPE_MAPPING_FINAL_CLASS);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.TYPE_MAPPING_ID_AND_EMBEDDED_ID_BOTH_USED,												JptJpaCoreValidationMessages.TYPE_MAPPING_ID_AND_EMBEDDED_ID_BOTH_USED);	
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.TYPE_MAPPING_ID_CLASS_AND_EMBEDDED_ID_BOTH_USED,							JptJpaCoreValidationMessages.TYPE_MAPPING_ID_CLASS_AND_EMBEDDED_ID_BOTH_USED);	//3.0 M7
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.TYPE_MAPPING_ID_CLASS_ATTRIBUTE_DOES_NOT_EXIST,							JptJpaCoreValidationMessages.TYPE_MAPPING_ID_CLASS_ATTRIBUTE_DOES_NOT_EXIST);	//3.2
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.TYPE_MAPPING_ID_CLASS_ATTRIBUTE_MAPPING_DUPLICATE_MATCH,				JptJpaCoreValidationMessages.TYPE_MAPPING_ID_CLASS_ATTRIBUTE_MAPPING_DUPLICATE_MATCH);	//3.0 M7
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.TYPE_MAPPING_ID_CLASS_ATTRIBUTE_MAPPING_NO_MATCH,							JptJpaCoreValidationMessages.TYPE_MAPPING_ID_CLASS_ATTRIBUTE_MAPPING_NO_MATCH);	//3.0 M7
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.TYPE_MAPPING_ID_CLASS_ATTRIBUTE_NO_MATCH,										JptJpaCoreValidationMessages.TYPE_MAPPING_ID_CLASS_ATTRIBUTE_NO_MATCH);	//3.0 M7
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.TYPE_MAPPING_ID_CLASS_ATTRIBUTE_NOT_PRIMARY_KEY,								JptJpaCoreValidationMessages.TYPE_MAPPING_ID_CLASS_ATTRIBUTE_NOT_PRIMARY_KEY);	//3.0 M7
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.TYPE_MAPPING_ID_CLASS_ATTRIBUTE_TYPE_DOES_NOT_AGREE,					JptJpaCoreValidationMessages.TYPE_MAPPING_ID_CLASS_ATTRIBUTE_TYPE_DOES_NOT_AGREE);	//3.0 M7
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.TYPE_MAPPING_ID_CLASS_MISSING_EQUALS_METHOD,											JptJpaCoreValidationMessages.TYPE_MAPPING_ID_CLASS_MISSING_EQUALS_METHOD);	//3.2
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.TYPE_MAPPING_ID_CLASS_MISSING_HASHCODE_METHOD,										JptJpaCoreValidationMessages.TYPE_MAPPING_ID_CLASS_MISSING_HASHCODE_METHOD); //3.2
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.TYPE_MAPPING_ID_CLASS_MISSING_NO_ARG_CONSTRUCTOR,										JptJpaCoreValidationMessages.TYPE_MAPPING_ID_CLASS_MISSING_NO_ARG_CONSTRUCTOR); //3.2
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.TYPE_MAPPING_ID_CLASS_MISSING_PUBLIC_NO_ARG_CONSTRUCTOR,								JptJpaCoreValidationMessages.TYPE_MAPPING_ID_CLASS_MISSING_PUBLIC_NO_ARG_CONSTRUCTOR); //3.2
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.TYPE_MAPPING_ID_CLASS_NAME_EMPTY,														JptJpaCoreValidationMessages.TYPE_MAPPING_ID_CLASS_NAME_EMPTY);	//3.0 M7
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.TYPE_MAPPING_ID_CLASS_NOT_EXIST,														JptJpaCoreValidationMessages.TYPE_MAPPING_ID_CLASS_NOT_EXIST);	//3.0 M7
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.TYPE_MAPPING_ID_CLASS_NOT_IMPLEMENT_SERIALIZABLE,										JptJpaCoreValidationMessages.TYPE_MAPPING_ID_CLASS_NOT_IMPLEMENT_SERIALIZABLE);	//3.2
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.TYPE_MAPPING_ID_CLASS_NOT_PUBLIC,														JptJpaCoreValidationMessages.TYPE_MAPPING_ID_CLASS_NOT_PUBLIC);	//3.2
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.TYPE_MAPPING_ID_CLASS_PROPERTY_METHOD_NOT_PUBLIC,										JptJpaCoreValidationMessages.TYPE_MAPPING_ID_CLASS_PROPERTY_METHOD_NOT_PUBLIC);	//3.2
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.TYPE_MAPPING_ID_CLASS_REDEFINED,														JptJpaCoreValidationMessages.TYPE_MAPPING_ID_CLASS_REDEFINED);	//3.0 M7
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.TYPE_MAPPING_ID_CLASS_REQUIRED,														JptJpaCoreValidationMessages.TYPE_MAPPING_ID_CLASS_REQUIRED);	//3.0 M7
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.TYPE_MAPPING_ID_CLASS_WITH_MAPS_ID,													JptJpaCoreValidationMessages.TYPE_MAPPING_ID_CLASS_WITH_MAPS_ID);	//3.0 M7
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.TYPE_MAPPING_MAPS_ID_ATTRIBUTE_TYPE_DOES_NOT_AGREE,									JptJpaCoreValidationMessages.TYPE_MAPPING_MAPS_ID_ATTRIBUTE_TYPE_DOES_NOT_AGREE);	//3.0 M7
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.TYPE_MAPPING_MEMBER_CLASS,															JptJpaCoreValidationMessages.TYPE_MAPPING_MEMBER_CLASS);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.TYPE_MAPPING_MULTIPLE_EMBEDDED_ID,													JptJpaCoreValidationMessages.TYPE_MAPPING_MULTIPLE_EMBEDDED_ID);	//3.0 M7
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.TYPE_MAPPING_PK_REDEFINED_ID_ATTRIBUTE,												JptJpaCoreValidationMessages.TYPE_MAPPING_PK_REDEFINED_ID_ATTRIBUTE);	//3.0 M7
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.TYPE_MAPPING_PK_REDEFINED_ID_CLASS,													JptJpaCoreValidationMessages.TYPE_MAPPING_PK_REDEFINED_ID_CLASS);	//3.0 M7
	}

	private void addAttributeLevelCategory(Composite parent) {

		parent = addExpandableSection(parent, JptJpaUiPreferencesValidationMessages.ATTRIBUTE_LEVEL_CATEGORY);
		
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.ELEMENT_COLLECTION_TARGET_CLASS_MUST_BE_EMBEDDABLE_OR_BASIC_TYPE, 					JptJpaCoreValidationMessages.ELEMENT_COLLECTION_TARGET_CLASS_MUST_BE_EMBEDDABLE_OR_BASIC_TYPE);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.ELEMENT_COLLECTION_TARGET_CLASS_DOES_NOT_EXIST,													JptJpaCoreValidationMessages.ELEMENT_COLLECTION_TARGET_CLASS_DOES_NOT_EXIST); //3.0 M7
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.ELEMENT_COLLECTION_TARGET_CLASS_NOT_DEFINED,															JptJpaCoreValidationMessages.ELEMENT_COLLECTION_TARGET_CLASS_NOT_DEFINED);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.ELEMENT_COLLECTION_CONTAINS_EMBEDDABLE_WITH_ELEMENT_COLLECTION_MAPPING, 				JptJpaCoreValidationMessages.ELEMENT_COLLECTION_CONTAINS_EMBEDDABLE_WITH_ELEMENT_COLLECTION_MAPPING);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.ELEMENT_COLLECTION_CONTAINS_EMBEDDABLE_WITH_PROHIBITED_RELATIONSHIP_MAPPING, 			JptJpaCoreValidationMessages.ELEMENT_COLLECTION_CONTAINS_EMBEDDABLE_WITH_PROHIBITED_RELATIONSHIP_MAPPING);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.EMBEDDED_ID_CLASS_SHOULD_BE_PUBLIC,																JptJpaCoreValidationMessages.EMBEDDED_ID_CLASS_SHOULD_BE_PUBLIC);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.EMBEDDED_ID_CLASS_SHOULD_IMPLEMENT_EQUALS_HASHCODE,																JptJpaCoreValidationMessages.EMBEDDED_ID_CLASS_SHOULD_IMPLEMENT_EQUALS_HASHCODE);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.EMBEDDED_ID_CLASS_SHOULD_IMPLEMENT_NO_ARG_CONSTRUCTOR, 								JptJpaCoreValidationMessages.EMBEDDED_ID_CLASS_SHOULD_IMPLEMENT_NO_ARG_CONSTRUCTOR);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.EMBEDDED_ID_CLASS_SHOULD_IMPLEMENT_SERIALIZABLE,																JptJpaCoreValidationMessages.EMBEDDED_ID_CLASS_SHOULD_IMPLEMENT_SERIALIZABLE);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.EMBEDDED_ID_CLASS_SHOULD_NOT_CONTAIN_RELATIONSHIP_MAPPINGS,																JptJpaCoreValidationMessages.EMBEDDED_ID_CLASS_SHOULD_NOT_CONTAIN_RELATIONSHIP_MAPPINGS);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.EMBEDDED_ID_MAPPING_MAPPED_BY_RELATIONSHIP_AND_ATTRIBUTE_OVERRIDES_SPECIFIED,	JptJpaCoreValidationMessages.EMBEDDED_ID_MAPPING_MAPPED_BY_RELATIONSHIP_AND_ATTRIBUTE_OVERRIDES_SPECIFIED); //3.0 M7
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.ID_MAPPING_MAPPED_BY_RELATIONSHIP_AND_COLUMN_SPECIFIED,										JptJpaCoreValidationMessages.ID_MAPPING_MAPPED_BY_RELATIONSHIP_AND_COLUMN_SPECIFIED); 	//3.0 M7
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.MAPS_ID_VALUE_INVALID,																										JptJpaCoreValidationMessages.MAPS_ID_VALUE_INVALID);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.MAPS_ID_VALUE_NOT_RESOLVED,																							JptJpaCoreValidationMessages.MAPS_ID_VALUE_NOT_RESOLVED);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.MAPS_ID_VALUE_NOT_SPECIFIED,																							JptJpaCoreValidationMessages.MAPS_ID_VALUE_NOT_SPECIFIED);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.MAPPING_INVALID_MAPPED_BY,																								JptJpaCoreValidationMessages.MAPPING_INVALID_MAPPED_BY);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.MAPPING_MAPPED_BY_ON_BOTH_SIDES,																					JptJpaCoreValidationMessages.MAPPING_MAPPED_BY_ON_BOTH_SIDES);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.MAPPING_UNRESOLVED_MAPPED_BY,																						JptJpaCoreValidationMessages.MAPPING_UNRESOLVED_MAPPED_BY);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.ORDER_COLUMN_AND_ORDER_BY_BOTH_SPECIFIED,																JptJpaCoreValidationMessages.ORDER_COLUMN_AND_ORDER_BY_BOTH_SPECIFIED);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.PERSISTENT_ATTRIBUTE_ELEMENT_COLLECTION_INVALID_VALUE_TYPE,									JptJpaCoreValidationMessages.PERSISTENT_ATTRIBUTE_ELEMENT_COLLECTION_INVALID_VALUE_TYPE); //3.0 M7
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.PERSISTENT_ATTRIBUTE_FINAL_FIELD,																					JptJpaCoreValidationMessages.PERSISTENT_ATTRIBUTE_FINAL_FIELD);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.PERSISTENT_ATTRIBUTE_FINAL_GETTER,																				JptJpaCoreValidationMessages.PERSISTENT_ATTRIBUTE_FINAL_GETTER);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.PERSISTENT_ATTRIBUTE_INHERITED_ATTRIBUTES_NOT_SUPPORTED, 										JptJpaCoreValidationMessages.PERSISTENT_ATTRIBUTE_INHERITED_ATTRIBUTES_NOT_SUPPORTED);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.PERSISTENT_ATTRIBUTE_INVALID_MAPPING,																			JptJpaCoreValidationMessages.PERSISTENT_ATTRIBUTE_INVALID_MAPPING);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.PERSISTENT_ATTRIBUTE_INVALID_TEMPORAL_MAPPING_TYPE,												JptJpaCoreValidationMessages.PERSISTENT_ATTRIBUTE_INVALID_TEMPORAL_MAPPING_TYPE); //3.0 M7
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.PERSISTENT_ATTRIBUTE_INVALID_TEMPORAL_MAP_KEY_TYPE,											JptJpaCoreValidationMessages.PERSISTENT_ATTRIBUTE_INVALID_TEMPORAL_MAP_KEY_TYPE);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.PERSISTENT_ATTRIBUTE_INVALID_VERSION_MAPPING_TYPE,													JptJpaCoreValidationMessages.PERSISTENT_ATTRIBUTE_INVALID_VERSION_MAPPING_TYPE); //3.0 M7
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.PERSISTENT_ATTRIBUTE_PUBLIC_FIELD,																					JptJpaCoreValidationMessages.PERSISTENT_ATTRIBUTE_PUBLIC_FIELD);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.PERSISTENT_ATTRIBUTE_UNRESOLVED_NAME,																		JptJpaCoreValidationMessages.PERSISTENT_ATTRIBUTE_UNRESOLVED_NAME);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.PERSISTENT_ATTRIBUTE_UNSPECIFIED_NAME,																		JptJpaCoreValidationMessages.PERSISTENT_ATTRIBUTE_UNSPECIFIED_NAME);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.TARGET_ENTITY_IS_NOT_AN_ENTITY,																					JptJpaCoreValidationMessages.TARGET_ENTITY_IS_NOT_AN_ENTITY);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.TARGET_ENTITY_NOT_DEFINED,																							JptJpaCoreValidationMessages.TARGET_ENTITY_NOT_DEFINED);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.TARGET_ENTITY_NOT_EXIST,																							JptJpaCoreValidationMessages.TARGET_ENTITY_NOT_EXIST);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.TARGET_NOT_AN_EMBEDDABLE,																	JptJpaCoreValidationMessages.TARGET_NOT_AN_EMBEDDABLE); //3.0 M7
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.MAP_KEY_CLASS_NOT_DEFINED,															JptJpaCoreValidationMessages.MAP_KEY_CLASS_NOT_DEFINED);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.MAP_KEY_CLASS_NOT_EXIST,															JptJpaCoreValidationMessages.MAP_KEY_CLASS_NOT_EXIST);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.MAP_KEY_CLASS_MUST_BE_ENTITY_EMBEDDABLE_OR_BASIC_TYPE,							JptJpaCoreValidationMessages.MAP_KEY_CLASS_MUST_BE_ENTITY_EMBEDDABLE_OR_BASIC_TYPE);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.ATTRIBUTE_TYPE_IS_NOT_SUPPORTED_COLLECTION_TYPE,									JptJpaCoreValidationMessages.ATTRIBUTE_TYPE_IS_NOT_SUPPORTED_COLLECTION_TYPE);

		parent = addSubExpandableSection(parent, JptJpaUiPreferencesValidationMessages.IMPLIED_ATTRIBUTE_LEVEL_CATEGORY);
		
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.VIRTUAL_ATTRIBUTE_ELEMENT_COLLECTION_TARGET_CLASS_DOES_NOT_EXIST,									JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_ELEMENT_COLLECTION_TARGET_CLASS_DOES_NOT_EXIST);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.VIRTUAL_ATTRIBUTE_ELEMENT_COLLECTION_TARGET_CLASS_MUST_BE_EMBEDDABLE_OR_BASIC_TYPE,	JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_ELEMENT_COLLECTION_TARGET_CLASS_MUST_BE_EMBEDDABLE_OR_BASIC_TYPE);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.VIRTUAL_ATTRIBUTE_ELEMENT_COLLECTION_TARGET_CLASS_NOT_DEFINED,											JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_ELEMENT_COLLECTION_TARGET_CLASS_NOT_DEFINED);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.VIRTUAL_ATTRIBUTE_ELEMENT_COLLECTION_CONTAINS_EMBEDDABLE_WITH_ELEMENT_COLLECTION_MAPPING, JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_ELEMENT_COLLECTION_CONTAINS_EMBEDDABLE_WITH_ELEMENT_COLLECTION_MAPPING);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.VIRTUAL_ATTRIBUTE_ELEMENT_COLLECTION_CONTAINS_EMBEDDABLE_WITH_PROHIBITED_RELATIONSHIP_MAPPING, JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_ELEMENT_COLLECTION_CONTAINS_EMBEDDABLE_WITH_PROHIBITED_RELATIONSHIP_MAPPING);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.VIRTUAL_ATTRIBUTE_EMBEDDED_ID_CLASS_SHOULD_BE_PUBLIC, 											JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_EMBEDDED_ID_CLASS_SHOULD_BE_PUBLIC);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.VIRTUAL_ATTRIBUTE_EMBEDDED_ID_CLASS_SHOULD_IMPLEMENT_EQUALS_HASHCODE, 							JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_EMBEDDED_ID_CLASS_SHOULD_IMPLEMENT_EQUALS_HASHCODE);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.VIRTUAL_ATTRIBUTE_EMBEDDED_ID_CLASS_SHOULD_IMPLEMENT_NO_ARG_CONSTRUCTOR, 							JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_EMBEDDED_ID_CLASS_SHOULD_IMPLEMENT_NO_ARG_CONSTRUCTOR);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.VIRTUAL_ATTRIBUTE_EMBEDDED_ID_CLASS_SHOULD_IMPLEMENT_SERIALIZABLE, 								JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_EMBEDDED_ID_CLASS_SHOULD_IMPLEMENT_SERIALIZABLE);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.VIRTUAL_ATTRIBUTE_EMBEDDED_ID_CLASS_SHOULD_NOT_CONTAIN_RELATIONSHIP_MAPPINGS, 					JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_EMBEDDED_ID_CLASS_SHOULD_NOT_CONTAIN_RELATIONSHIP_MAPPINGS);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.VIRTUAL_ATTRIBUTE_EMBEDDED_ID_MAPPING_MAPPED_BY_RELATIONSHIP_AND_ATTRIBUTE_OVERRIDES_SPECIFIED, 	JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_EMBEDDED_ID_MAPPING_MAPPED_BY_RELATIONSHIP_AND_ATTRIBUTE_OVERRIDES_SPECIFIED);		
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.VIRTUAL_ATTRIBUTE_TARGET_ENTITY_IS_NOT_AN_ENTITY,																	JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_TARGET_ENTITY_IS_NOT_AN_ENTITY);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.VIRTUAL_ATTRIBUTE_TARGET_ENTITY_NOT_DEFINED,																			JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_TARGET_ENTITY_NOT_DEFINED);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.VIRTUAL_ATTRIBUTE_TARGET_NOT_AN_EMBEDDABLE,																			JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_TARGET_NOT_AN_EMBEDDABLE);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.VIRTUAL_ATTRIBUTE_MAP_KEY_CLASS_NOT_DEFINED,										JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_MAP_KEY_CLASS_NOT_DEFINED);	
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.VIRTUAL_ATTRIBUTE_MAP_KEY_CLASS_MUST_BE_ENTITY_EMBEDDABLE_OR_BASIC_TYPE,							JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_MAP_KEY_CLASS_MUST_BE_ENTITY_EMBEDDABLE_OR_BASIC_TYPE);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.VIRTUAL_ATTRIBUTE_INVALID_TEMPORAL_MAP_KEY_TYPE,																			JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_INVALID_TEMPORAL_MAP_KEY_TYPE);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.VIRTUAL_ATTRIBUTE_ELEMENT_COLLECTION_INVALID_VALUE_TYPE,																	JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_ELEMENT_COLLECTION_INVALID_VALUE_TYPE);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.VIRTUAL_ATTRIBUTE_INVALID_TEMPORAL_MAPPING_TYPE,																			JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_INVALID_TEMPORAL_MAPPING_TYPE);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.VIRTUAL_ATTRIBUTE_ATTRIBUTE_TYPE_IS_NOT_SUPPORTED_COLLECTION_TYPE,														JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_ATTRIBUTE_TYPE_IS_NOT_SUPPORTED_COLLECTION_TYPE);
	}

	private void addDatabaseCategory(Composite parent) {
		parent = addExpandableSection(parent, JptJpaUiPreferencesValidationMessages.DATABASE_CATEGORY);
		parent.setLayout(new GridLayout(1, false));

		addTableCategory(parent);
		addColumnCategory(parent);
		addOverridesCategory(parent);
	}

	private void addTableCategory(Composite parent) {
		
		parent = addExpandableSection(parent, JptJpaUiPreferencesValidationMessages.TABLE_CATEGORY);
		
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.COLLECTION_TABLE_UNRESOLVED_CATALOG,                                		JptJpaCoreValidationMessages.COLLECTION_TABLE_UNRESOLVED_CATALOG);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.COLLECTION_TABLE_UNRESOLVED_NAME,                                    			JptJpaCoreValidationMessages.COLLECTION_TABLE_UNRESOLVED_NAME);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.COLLECTION_TABLE_UNRESOLVED_SCHEMA,                                 		 	JptJpaCoreValidationMessages.COLLECTION_TABLE_UNRESOLVED_SCHEMA);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.JOIN_TABLE_UNRESOLVED_CATALOG,                                       				JptJpaCoreValidationMessages.JOIN_TABLE_UNRESOLVED_CATALOG);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.JOIN_TABLE_UNRESOLVED_NAME,                                          				JptJpaCoreValidationMessages.JOIN_TABLE_UNRESOLVED_NAME);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.JOIN_TABLE_UNRESOLVED_SCHEMA,                                        				JptJpaCoreValidationMessages.JOIN_TABLE_UNRESOLVED_SCHEMA);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.SECONDARY_TABLE_UNRESOLVED_CATALOG,                                  		JptJpaCoreValidationMessages.SECONDARY_TABLE_UNRESOLVED_CATALOG);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.SECONDARY_TABLE_UNRESOLVED_NAME,                                     			JptJpaCoreValidationMessages.SECONDARY_TABLE_UNRESOLVED_NAME);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.SECONDARY_TABLE_UNRESOLVED_SCHEMA,                                   		JptJpaCoreValidationMessages.SECONDARY_TABLE_UNRESOLVED_SCHEMA);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.TABLE_UNRESOLVED_CATALOG,                                            					JptJpaCoreValidationMessages.TABLE_UNRESOLVED_CATALOG);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.TABLE_UNRESOLVED_NAME,                                               					JptJpaCoreValidationMessages.TABLE_UNRESOLVED_NAME);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.TABLE_UNRESOLVED_SCHEMA,                                             					JptJpaCoreValidationMessages.TABLE_UNRESOLVED_SCHEMA);

		parent = addSubExpandableSection(parent, JptJpaUiPreferencesValidationMessages.IMPLIED_ATTRIBUTE_LEVEL_CATEGORY);

		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.VIRTUAL_ATTRIBUTE_COLLECTION_TABLE_UNRESOLVED_CATALOG,           JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_COLLECTION_TABLE_UNRESOLVED_CATALOG);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.VIRTUAL_ATTRIBUTE_COLLECTION_TABLE_UNRESOLVED_NAME,                 JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_COLLECTION_TABLE_UNRESOLVED_NAME);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.VIRTUAL_ATTRIBUTE_COLLECTION_TABLE_UNRESOLVED_SCHEMA,             JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_COLLECTION_TABLE_UNRESOLVED_SCHEMA);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.VIRTUAL_ATTRIBUTE_JOIN_TABLE_UNRESOLVED_CATALOG,                       JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_JOIN_TABLE_UNRESOLVED_CATALOG);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.VIRTUAL_ATTRIBUTE_JOIN_TABLE_UNRESOLVED_NAME,                             JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_JOIN_TABLE_UNRESOLVED_NAME);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.VIRTUAL_ATTRIBUTE_JOIN_TABLE_UNRESOLVED_SCHEMA,                         JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_JOIN_TABLE_UNRESOLVED_SCHEMA);
	}
	
	private void addColumnCategory(Composite parent) {	
		parent = addExpandableSection(parent, JptJpaUiPreferencesValidationMessages.COLUMN_CATEGORY);
		
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.COLUMN_TABLE_NOT_VALID,                                      								JptJpaCoreValidationMessages.COLUMN_TABLE_NOT_VALID);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.COLUMN_UNRESOLVED_NAME,                                              						JptJpaCoreValidationMessages.COLUMN_UNRESOLVED_NAME);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.COLUMN_UNRESOLVED_TABLE,                                             						JptJpaCoreValidationMessages.COLUMN_UNRESOLVED_TABLE);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.INVERSE_JOIN_COLUMN_NAME_MUST_BE_SPECIFIED_MULTIPLE_JOIN_COLUMNS,                   		JptJpaCoreValidationMessages.INVERSE_JOIN_COLUMN_NAME_MUST_BE_SPECIFIED_MULTIPLE_JOIN_COLUMNS);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.INVERSE_JOIN_COLUMN_REFERENCED_COLUMN_NAME_MUST_BE_SPECIFIED_MULTIPLE_JOIN_COLUMNS,		JptJpaCoreValidationMessages.INVERSE_JOIN_COLUMN_REFERENCED_COLUMN_NAME_MUST_BE_SPECIFIED_MULTIPLE_JOIN_COLUMNS);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.INVERSE_JOIN_COLUMN_TABLE_NOT_VALID,                                  					JptJpaCoreValidationMessages.INVERSE_JOIN_COLUMN_TABLE_NOT_VALID);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.INVERSE_JOIN_COLUMN_UNRESOLVED_NAME,                                         				JptJpaCoreValidationMessages.INVERSE_JOIN_COLUMN_UNRESOLVED_NAME);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.INVERSE_JOIN_COLUMN_UNRESOLVED_REFERENCED_COLUMN_NAME,                       				JptJpaCoreValidationMessages.INVERSE_JOIN_COLUMN_UNRESOLVED_REFERENCED_COLUMN_NAME);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.JOIN_COLUMN_NAME_MUST_BE_SPECIFIED_MULTIPLE_JOIN_COLUMNS,                   				JptJpaCoreValidationMessages.JOIN_COLUMN_NAME_MUST_BE_SPECIFIED_MULTIPLE_JOIN_COLUMNS);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.JOIN_COLUMN_REFERENCED_COLUMN_NAME_MUST_BE_SPECIFIED_MULTIPLE_JOIN_COLUMNS, 				JptJpaCoreValidationMessages.JOIN_COLUMN_REFERENCED_COLUMN_NAME_MUST_BE_SPECIFIED_MULTIPLE_JOIN_COLUMNS);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.JOIN_COLUMN_TABLE_NOT_VALID,                                  							JptJpaCoreValidationMessages.JOIN_COLUMN_TABLE_NOT_VALID);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.JOIN_COLUMN_UNRESOLVED_NAME,                                         						JptJpaCoreValidationMessages.JOIN_COLUMN_UNRESOLVED_NAME);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.JOIN_COLUMN_UNRESOLVED_REFERENCED_COLUMN_NAME,                       						JptJpaCoreValidationMessages.JOIN_COLUMN_UNRESOLVED_REFERENCED_COLUMN_NAME);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.MAP_KEY_COLUMN_TABLE_NOT_VALID, 															JptJpaCoreValidationMessages.MAP_KEY_COLUMN_TABLE_NOT_VALID);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.MAP_KEY_COLUMN_UNRESOLVED_NAME, 															JptJpaCoreValidationMessages.MAP_KEY_COLUMN_UNRESOLVED_NAME); // 3.0 M7
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.ORDER_COLUMN_UNRESOLVED_NAME,                                        						JptJpaCoreValidationMessages.ORDER_COLUMN_UNRESOLVED_NAME);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.PRIMARY_KEY_JOIN_COLUMN_NAME_MUST_BE_SPECIFIED_MULTIPLE_JOIN_COLUMNS,                 	JptJpaCoreValidationMessages.PRIMARY_KEY_JOIN_COLUMN_NAME_MUST_BE_SPECIFIED_MULTIPLE_JOIN_COLUMNS);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.PRIMARY_KEY_JOIN_COLUMN_REFERENCED_COLUMN_NAME_MUST_BE_SPECIFIED_MULTIPLE_JOIN_COLUMNS,	JptJpaCoreValidationMessages.PRIMARY_KEY_JOIN_COLUMN_REFERENCED_COLUMN_NAME_MUST_BE_SPECIFIED_MULTIPLE_JOIN_COLUMNS);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.PRIMARY_KEY_JOIN_COLUMN_UNRESOLVED_NAME,                            	 					JptJpaCoreValidationMessages.PRIMARY_KEY_JOIN_COLUMN_UNRESOLVED_NAME);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.PRIMARY_KEY_JOIN_COLUMN_UNRESOLVED_REFERENCED_COLUMN_NAME,           						JptJpaCoreValidationMessages.PRIMARY_KEY_JOIN_COLUMN_UNRESOLVED_REFERENCED_COLUMN_NAME);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.MAP_KEY_JOIN_COLUMN_NAME_MUST_BE_SPECIFIED_MULTIPLE_JOIN_COLUMNS,           				JptJpaCoreValidationMessages.MAP_KEY_JOIN_COLUMN_NAME_MUST_BE_SPECIFIED_MULTIPLE_JOIN_COLUMNS);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.MAP_KEY_JOIN_COLUMN_TABLE_NOT_VALID,           											JptJpaCoreValidationMessages.MAP_KEY_JOIN_COLUMN_TABLE_NOT_VALID);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.MAP_KEY_JOIN_COLUMN_REFERENCED_COLUMN_NAME_MUST_BE_SPECIFIED_MULTIPLE_JOIN_COLUMNS,       JptJpaCoreValidationMessages.MAP_KEY_JOIN_COLUMN_REFERENCED_COLUMN_NAME_MUST_BE_SPECIFIED_MULTIPLE_JOIN_COLUMNS);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.MAP_KEY_JOIN_COLUMN_UNRESOLVED_NAME,           											JptJpaCoreValidationMessages.MAP_KEY_JOIN_COLUMN_UNRESOLVED_NAME);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.MAP_KEY_JOIN_COLUMN_UNRESOLVED_REFERENCED_COLUMN_NAME,           							JptJpaCoreValidationMessages.MAP_KEY_JOIN_COLUMN_UNRESOLVED_REFERENCED_COLUMN_NAME);

		parent = addSubExpandableSection(parent, JptJpaUiPreferencesValidationMessages.IMPLIED_ATTRIBUTE_LEVEL_CATEGORY);

		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.VIRTUAL_ATTRIBUTE_COLUMN_TABLE_NOT_VALID,                  				                   	 																				JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_COLUMN_TABLE_NOT_VALID);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.VIRTUAL_ATTRIBUTE_COLUMN_UNRESOLVED_NAME,                                              																						JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_COLUMN_UNRESOLVED_NAME);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.VIRTUAL_ATTRIBUTE_INVERSE_JOIN_COLUMN_NAME_MUST_BE_SPECIFIED_MULTIPLE_JOIN_COLUMNS,      													JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_INVERSE_JOIN_COLUMN_NAME_MUST_BE_SPECIFIED_MULTIPLE_JOIN_COLUMNS);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.VIRTUAL_ATTRIBUTE_INVERSE_JOIN_COLUMN_REFERENCED_COLUMN_NAME_MUST_BE_SPECIFIED_MULTIPLE_JOIN_COLUMNS, 						JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_INVERSE_JOIN_COLUMN_REFERENCED_COLUMN_NAME_MUST_BE_SPECIFIED_MULTIPLE_JOIN_COLUMNS);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.VIRTUAL_ATTRIBUTE_INVERSE_JOIN_COLUMN_TABLE_NOT_VALID,      																										JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_INVERSE_JOIN_COLUMN_TABLE_NOT_VALID);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.VIRTUAL_ATTRIBUTE_INVERSE_JOIN_COLUMN_UNRESOLVED_NAME,      																										JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_INVERSE_JOIN_COLUMN_UNRESOLVED_NAME);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.VIRTUAL_ATTRIBUTE_INVERSE_JOIN_COLUMN_UNRESOLVED_REFERENCED_COLUMN_NAME,      																	JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_INVERSE_JOIN_COLUMN_UNRESOLVED_REFERENCED_COLUMN_NAME);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.VIRTUAL_ATTRIBUTE_JOIN_COLUMN_NAME_MUST_BE_SPECIFIED_MULTIPLE_JOIN_COLUMNS,                   													JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_JOIN_COLUMN_NAME_MUST_BE_SPECIFIED_MULTIPLE_JOIN_COLUMNS);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.VIRTUAL_ATTRIBUTE_JOIN_COLUMN_REFERENCED_COLUMN_NAME_MUST_BE_SPECIFIED_MULTIPLE_JOIN_COLUMNS,		 							JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_JOIN_COLUMN_REFERENCED_COLUMN_NAME_MUST_BE_SPECIFIED_MULTIPLE_JOIN_COLUMNS);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.VIRTUAL_ATTRIBUTE_JOIN_COLUMN_UNRESOLVED_NAME,                                         																					JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_JOIN_COLUMN_UNRESOLVED_NAME);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.VIRTUAL_ATTRIBUTE_JOIN_COLUMN_UNRESOLVED_REFERENCED_COLUMN_NAME,                       																	JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_JOIN_COLUMN_UNRESOLVED_REFERENCED_COLUMN_NAME);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.VIRTUAL_ATTRIBUTE_JOIN_COLUMN_TABLE_NOT_VALID,      																														JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_JOIN_COLUMN_TABLE_NOT_VALID);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.VIRTUAL_ATTRIBUTE_MAP_KEY_COLUMN_TABLE_NOT_VALID, 																													JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_MAP_KEY_COLUMN_TABLE_NOT_VALID);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.VIRTUAL_ATTRIBUTE_MAP_KEY_COLUMN_UNRESOLVED_NAME, 																													JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_MAP_KEY_COLUMN_UNRESOLVED_NAME); // 3.0 M7
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.VIRTUAL_ATTRIBUTE_ORDER_COLUMN_UNRESOLVED_NAME,                                        																				JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_ORDER_COLUMN_UNRESOLVED_NAME);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.VIRTUAL_SECONDARY_TABLE_PRIMARY_KEY_JOIN_COLUMN_NAME_MUST_BE_SPECIFIED_MULTIPLE_JOIN_COLUMNS, 									JptJpaCoreValidationMessages.VIRTUAL_SECONDARY_TABLE_PRIMARY_KEY_JOIN_COLUMN_NAME_MUST_BE_SPECIFIED_MULTIPLE_JOIN_COLUMNS);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.VIRTUAL_SECONDARY_TABLE_PRIMARY_KEY_JOIN_COLUMN_REFERENCED_COLUMN_NAME_MUST_BE_SPECIFIED_MULTIPLE_JOIN_COLUMNS, JptJpaCoreValidationMessages.VIRTUAL_SECONDARY_TABLE_PRIMARY_KEY_JOIN_COLUMN_REFERENCED_COLUMN_NAME_MUST_BE_SPECIFIED_MULTIPLE_JOIN_COLUMNS);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.VIRTUAL_SECONDARY_TABLE_PRIMARY_KEY_JOIN_COLUMN_UNRESOLVED_NAME, 																						JptJpaCoreValidationMessages.VIRTUAL_SECONDARY_TABLE_PRIMARY_KEY_JOIN_COLUMN_UNRESOLVED_NAME);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.VIRTUAL_SECONDARY_TABLE_PRIMARY_KEY_JOIN_COLUMN_UNRESOLVED_REFERENCED_COLUMN_NAME, 														JptJpaCoreValidationMessages.VIRTUAL_SECONDARY_TABLE_PRIMARY_KEY_JOIN_COLUMN_UNRESOLVED_REFERENCED_COLUMN_NAME);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.VIRTUAL_PRIMARY_KEY_JOIN_COLUMN_NAME_MUST_BE_SPECIFIED_MULTIPLE_JOIN_COLUMNS, 																	JptJpaCoreValidationMessages.VIRTUAL_PRIMARY_KEY_JOIN_COLUMN_NAME_MUST_BE_SPECIFIED_MULTIPLE_JOIN_COLUMNS);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.VIRTUAL_PRIMARY_KEY_JOIN_COLUMN_REFERENCED_COLUMN_NAME_MUST_BE_SPECIFIED_MULTIPLE_JOIN_COLUMNS, 								JptJpaCoreValidationMessages.VIRTUAL_PRIMARY_KEY_JOIN_COLUMN_REFERENCED_COLUMN_NAME_MUST_BE_SPECIFIED_MULTIPLE_JOIN_COLUMNS);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.VIRTUAL_PRIMARY_KEY_JOIN_COLUMN_UNRESOLVED_NAME, 																														JptJpaCoreValidationMessages.VIRTUAL_PRIMARY_KEY_JOIN_COLUMN_UNRESOLVED_NAME);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.VIRTUAL_PRIMARY_KEY_JOIN_COLUMN_UNRESOLVED_REFERENCED_COLUMN_NAME, 																					JptJpaCoreValidationMessages.VIRTUAL_PRIMARY_KEY_JOIN_COLUMN_UNRESOLVED_REFERENCED_COLUMN_NAME);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.VIRTUAL_ATTRIBUTE_MAP_KEY_JOIN_COLUMN_NAME_MUST_BE_SPECIFIED_MULTIPLE_MAP_KEY_JOIN_COLUMNS, 															JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_MAP_KEY_JOIN_COLUMN_NAME_MUST_BE_SPECIFIED_MULTIPLE_MAP_KEY_JOIN_COLUMNS);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.VIRTUAL_ATTRIBUTE_MAP_KEY_JOIN_COLUMN_REFERENCED_COLUMN_NAME_MUST_BE_SPECIFIED_MULTIPLE_MAP_KEY_JOIN_COLUMNS, 										JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_MAP_KEY_JOIN_COLUMN_REFERENCED_COLUMN_NAME_MUST_BE_SPECIFIED_MULTIPLE_MAP_KEY_JOIN_COLUMNS);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.VIRTUAL_ATTRIBUTE_MAP_KEY_JOIN_COLUMN_TABLE_NOT_VALID, 																								JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_MAP_KEY_JOIN_COLUMN_TABLE_NOT_VALID);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.VIRTUAL_ATTRIBUTE_MAP_KEY_JOIN_COLUMN_UNRESOLVED_NAME, 																								JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_MAP_KEY_JOIN_COLUMN_UNRESOLVED_NAME);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.VIRTUAL_ATTRIBUTE_MAP_KEY_JOIN_COLUMN_UNRESOLVED_REFERENCED_COLUMN_NAME, 																				JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_MAP_KEY_JOIN_COLUMN_UNRESOLVED_REFERENCED_COLUMN_NAME);
	}

	private void addOverridesCategory(Composite parent) {	

		parent = addExpandableSection(parent, JptJpaUiPreferencesValidationMessages.OVERRIDES_CATEGORY);

		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.ASSOCIATION_OVERRIDE_INVALID_NAME,                    																																			JptJpaCoreValidationMessages.ASSOCIATION_OVERRIDE_INVALID_NAME);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.ATTRIBUTE_OVERRIDE_INVALID_NAME,                    																																				JptJpaCoreValidationMessages.ATTRIBUTE_OVERRIDE_INVALID_NAME);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.ATTRIBUTE_OVERRIDE_INVALID_TYPE,                    																																				JptJpaCoreValidationMessages.ATTRIBUTE_OVERRIDE_INVALID_TYPE);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.ATTRIBUTE_OVERRIDE_MAPPED_BY_RELATIONSHIP_AND_SPECIFIED,                    																									JptJpaCoreValidationMessages.ATTRIBUTE_OVERRIDE_DERIVED_AND_SPECIFIED); //3.0 M7
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.VIRTUAL_ASSOCIATION_OVERRIDE_INVALID_NAME,                    																																JptJpaCoreValidationMessages.VIRTUAL_ASSOCIATION_OVERRIDE_INVALID_NAME);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.VIRTUAL_ASSOCIATION_OVERRIDE_INVERSE_JOIN_COLUMN_NAME_MUST_BE_SPECIFIED_MULTIPLE_INVERSE_JOIN_COLUMNS, 									JptJpaCoreValidationMessages.VIRTUAL_ASSOCIATION_OVERRIDE_INVERSE_JOIN_COLUMN_NAME_MUST_BE_SPECIFIED_MULTIPLE_INVERSE_JOIN_COLUMNS);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.VIRTUAL_ASSOCIATION_OVERRIDE_INVERSE_JOIN_COLUMN_REFERENCED_COLUMN_NAME_MUST_BE_SPECIFIED_MULTIPLE_INVERSE_JOIN_COLUMNS,JptJpaCoreValidationMessages.VIRTUAL_ASSOCIATION_OVERRIDE_INVERSE_JOIN_COLUMN_REFERENCED_COLUMN_NAME_MUST_BE_SPECIFIED_MULTIPLE_INVERSE_JOIN_COLUMNS);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.VIRTUAL_ASSOCIATION_OVERRIDE_INVERSE_JOIN_COLUMN_TABLE_NOT_VALID, 																										JptJpaCoreValidationMessages.VIRTUAL_ASSOCIATION_OVERRIDE_INVERSE_JOIN_COLUMN_TABLE_NOT_VALID);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.VIRTUAL_ASSOCIATION_OVERRIDE_INVERSE_JOIN_COLUMN_UNRESOLVED_NAME,                   																					JptJpaCoreValidationMessages.VIRTUAL_ASSOCIATION_OVERRIDE_INVERSE_JOIN_COLUMN_UNRESOLVED_NAME);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.VIRTUAL_ASSOCIATION_OVERRIDE_INVERSE_JOIN_COLUMN_UNRESOLVED_REFERENCED_COLUMN_NAME, 																	JptJpaCoreValidationMessages.VIRTUAL_ASSOCIATION_OVERRIDE_INVERSE_JOIN_COLUMN_UNRESOLVED_REFERENCED_COLUMN_NAME);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.VIRTUAL_ASSOCIATION_OVERRIDE_JOIN_COLUMN_NAME_MUST_BE_SPECIFIED_MULTIPLE_JOIN_COLUMNS, 																JptJpaCoreValidationMessages.VIRTUAL_ASSOCIATION_OVERRIDE_JOIN_COLUMN_NAME_MUST_BE_SPECIFIED_MULTIPLE_JOIN_COLUMNS);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.VIRTUAL_ASSOCIATION_OVERRIDE_JOIN_COLUMN_REFERENCED_COLUMN_NAME_MUST_BE_SPECIFIED_MULTIPLE_JOIN_COLUMNS, 							JptJpaCoreValidationMessages.VIRTUAL_ASSOCIATION_OVERRIDE_JOIN_COLUMN_REFERENCED_COLUMN_NAME_MUST_BE_SPECIFIED_MULTIPLE_JOIN_COLUMNS);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.VIRTUAL_ASSOCIATION_OVERRIDE_JOIN_COLUMN_TABLE_NOT_VALID, 																													JptJpaCoreValidationMessages.VIRTUAL_ASSOCIATION_OVERRIDE_JOIN_COLUMN_TABLE_NOT_VALID);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.VIRTUAL_ASSOCIATION_OVERRIDE_JOIN_COLUMN_UNRESOLVED_NAME,                   																								JptJpaCoreValidationMessages.VIRTUAL_ASSOCIATION_OVERRIDE_JOIN_COLUMN_UNRESOLVED_NAME);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.VIRTUAL_ASSOCIATION_OVERRIDE_JOIN_COLUMN_UNRESOLVED_REFERENCED_COLUMN_NAME, 																				JptJpaCoreValidationMessages.VIRTUAL_ASSOCIATION_OVERRIDE_JOIN_COLUMN_UNRESOLVED_REFERENCED_COLUMN_NAME);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.VIRTUAL_ASSOCIATION_OVERRIDE_JOIN_TABLE_UNRESOLVED_CATALOG, 																												JptJpaCoreValidationMessages.VIRTUAL_ASSOCIATION_OVERRIDE_JOIN_TABLE_UNRESOLVED_CATALOG);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.VIRTUAL_ASSOCIATION_OVERRIDE_JOIN_TABLE_UNRESOLVED_NAME, 																														JptJpaCoreValidationMessages.VIRTUAL_ASSOCIATION_OVERRIDE_JOIN_TABLE_UNRESOLVED_NAME);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.VIRTUAL_ASSOCIATION_OVERRIDE_JOIN_TABLE_UNRESOLVED_SCHEMA, 																													JptJpaCoreValidationMessages.VIRTUAL_ASSOCIATION_OVERRIDE_JOIN_TABLE_UNRESOLVED_SCHEMA);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.VIRTUAL_ATTRIBUTE_OVERRIDE_COLUMN_TABLE_NOT_VALID,                    																											JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_OVERRIDE_COLUMN_TABLE_NOT_VALID);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.VIRTUAL_ATTRIBUTE_OVERRIDE_COLUMN_UNRESOLVED_NAME,                                    																							JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_OVERRIDE_COLUMN_UNRESOLVED_NAME);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.VIRTUAL_ATTRIBUTE_OVERRIDE_INVALID_NAME,                    																																JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_OVERRIDE_INVALID_NAME);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.VIRTUAL_MAP_KEY_ATTRIBUTE_OVERRIDE_COLUMN_TABLE_NOT_VALID, 																												JptJpaCoreValidationMessages.VIRTUAL_MAP_KEY_ATTRIBUTE_OVERRIDE_COLUMN_TABLE_NOT_VALID);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.VIRTUAL_MAP_KEY_ATTRIBUTE_OVERRIDE_INVALID_NAME,                    																													JptJpaCoreValidationMessages.VIRTUAL_MAP_KEY_ATTRIBUTE_OVERRIDE_INVALID_NAME);


		parent = addSubExpandableSection(parent, JptJpaUiPreferencesValidationMessages.IMPLIED_ATTRIBUTE_LEVEL_CATEGORY);

		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.VIRTUAL_ATTRIBUTE_ASSOCIATION_OVERRIDE_INVALID_NAME,                  																																	JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_ASSOCIATION_OVERRIDE_INVALID_NAME);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.VIRTUAL_ATTRIBUTE_ASSOCIATION_OVERRIDE_INVERSE_JOIN_COLUMN_NAME_MUST_BE_SPECIFIED_MULTIPLE_INVERSE_JOIN_COLUMNS, 									JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_ASSOCIATION_OVERRIDE_INVERSE_JOIN_COLUMN_NAME_MUST_BE_SPECIFIED_MULTIPLE_INVERSE_JOIN_COLUMNS);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.VIRTUAL_ATTRIBUTE_ASSOCIATION_OVERRIDE_INVERSE_JOIN_COLUMN_REFERENCED_COLUMN_NAME_MUST_BE_SPECIFIED_MULTIPLE_INVERSE_JOIN_COLUMNS, 	JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_ASSOCIATION_OVERRIDE_INVERSE_JOIN_COLUMN_REFERENCED_COLUMN_NAME_MUST_BE_SPECIFIED_MULTIPLE_INVERSE_JOIN_COLUMNS);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.VIRTUAL_ATTRIBUTE_ASSOCIATION_OVERRIDE_INVERSE_JOIN_COLUMN_REFERENCED_COLUMN_UNRESOLVED_NAME, 																	JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_ASSOCIATION_OVERRIDE_INVERSE_JOIN_COLUMN_REFERENCED_COLUMN_UNRESOLVED_NAME);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.VIRTUAL_ATTRIBUTE_ASSOCIATION_OVERRIDE_INVERSE_JOIN_COLUMN_TABLE_NOT_VALID,             																JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_ASSOCIATION_OVERRIDE_INVERSE_JOIN_COLUMN_TABLE_NOT_VALID);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.VIRTUAL_ATTRIBUTE_ASSOCIATION_OVERRIDE_INVERSE_JOIN_COLUMN_UNRESOLVED_NAME, 																			JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_ASSOCIATION_OVERRIDE_INVERSE_JOIN_COLUMN_UNRESOLVED_NAME);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.VIRTUAL_ATTRIBUTE_ASSOCIATION_OVERRIDE_JOIN_COLUMN_NAME_MUST_BE_SPECIFIED_MULTIPLE_JOIN_COLUMNS, 									JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_ASSOCIATION_OVERRIDE_JOIN_COLUMN_NAME_MUST_BE_SPECIFIED_MULTIPLE_JOIN_COLUMNS);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.VIRTUAL_ATTRIBUTE_ASSOCIATION_OVERRIDE_JOIN_COLUMN_REFERENCED_COLUMN_NAME_MUST_BE_SPECIFIED_MULTIPLE_JOIN_COLUMNS, JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_ASSOCIATION_OVERRIDE_JOIN_COLUMN_REFERENCED_COLUMN_NAME_MUST_BE_SPECIFIED_MULTIPLE_JOIN_COLUMNS);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.VIRTUAL_ATTRIBUTE_ASSOCIATION_OVERRIDE_JOIN_COLUMN_REFERENCED_COLUMN_UNRESOLVED_NAME, 														JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_ASSOCIATION_OVERRIDE_JOIN_COLUMN_REFERENCED_COLUMN_UNRESOLVED_NAME);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.VIRTUAL_ATTRIBUTE_ASSOCIATION_OVERRIDE_JOIN_COLUMN_TABLE_NOT_VALID,                   		JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_ASSOCIATION_OVERRIDE_JOIN_COLUMN_TABLE_NOT_VALID);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.VIRTUAL_ATTRIBUTE_ASSOCIATION_OVERRIDE_JOIN_COLUMN_UNRESOLVED_NAME, 						JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_ASSOCIATION_OVERRIDE_JOIN_COLUMN_UNRESOLVED_NAME);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.VIRTUAL_ATTRIBUTE_ATTRIBUTE_OVERRIDE_COLUMN_TABLE_NOT_VALID,                  					JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_ATTRIBUTE_OVERRIDE_COLUMN_TABLE_NOT_VALID);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.VIRTUAL_ATTRIBUTE_ATTRIBUTE_OVERRIDE_COLUMN_UNRESOLVED_NAME,                                  	JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_ATTRIBUTE_OVERRIDE_COLUMN_UNRESOLVED_NAME);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.VIRTUAL_ATTRIBUTE_ATTRIBUTE_OVERRIDE_INVALID_NAME,                  										JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_ATTRIBUTE_OVERRIDE_INVALID_NAME);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.VIRTUAL_ATTRIBUTE_MAP_KEY_ATTRIBUTE_OVERRIDE_COLUMN_TABLE_NOT_VALID, 						JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_MAP_KEY_ATTRIBUTE_OVERRIDE_COLUMN_TABLE_NOT_VALID);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.VIRTUAL_ATTRIBUTE_MAP_KEY_ATTRIBUTE_OVERRIDE_INVALID_NAME,                  							JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_MAP_KEY_ATTRIBUTE_OVERRIDE_INVALID_NAME);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.VIRTUAL_ATTRIBUTE_MAP_KEY_ATTRIBUTE_OVERRIDE_COLUMN_UNRESOLVED_NAME,                  	JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_MAP_KEY_ATTRIBUTE_OVERRIDE_COLUMN_UNRESOLVED_NAME); //3.0 M7
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.VIRTUAL_MAP_KEY_ATTRIBUTE_OVERRIDE_COLUMN_UNRESOLVED_NAME,                  						JptJpaCoreValidationMessages.VIRTUAL_MAP_KEY_ATTRIBUTE_OVERRIDE_COLUMN_UNRESOLVED_NAME); //3.0 M7
	}

	private void addInheritanceStrategyCategory(Composite parent) {

		parent = addExpandableSection(parent, JptJpaUiPreferencesValidationMessages.INHERITANCE_CATEGORY);

		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.DISCRIMINATOR_COLUMN_UNRESOLVED_NAME,							JptJpaCoreValidationMessages.DISCRIMINATOR_COLUMN_UNRESOLVED_NAME);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.ENTITY_ABSTRACT_DISCRIMINATOR_VALUE_DEFINED,					JptJpaCoreValidationMessages.ENTITY_ABSTRACT_DISCRIMINATOR_VALUE_DEFINED);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.ENTITY_ABSTRACT_TABLE_PER_CLASS_DEFINES_TABLE,				JptJpaCoreValidationMessages.ENTITY_ABSTRACT_TABLE_PER_CLASS_DEFINES_TABLE);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.ENTITY_NON_ROOT_DISCRIMINATOR_COLUMN_DEFINED,			JptJpaCoreValidationMessages.ENTITY_NON_ROOT_DISCRIMINATOR_COLUMN_DEFINED);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.ENTITY_SINGLE_TABLE_DESCENDANT_DEFINES_TABLE,				JptJpaCoreValidationMessages.ENTITY_SINGLE_TABLE_DESCENDANT_DEFINES_TABLE);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.ENTITY_TABLE_PER_CLASS_DISCRIMINATOR_COLUMN_DEFINED,	JptJpaCoreValidationMessages.ENTITY_TABLE_PER_CLASS_DISCRIMINATOR_COLUMN_DEFINED);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.ENTITY_TABLE_PER_CLASS_DISCRIMINATOR_VALUE_DEFINED,		JptJpaCoreValidationMessages.ENTITY_TABLE_PER_CLASS_DISCRIMINATOR_VALUE_DEFINED);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.ENTITY_TABLE_PER_CLASS_NOT_PORTABLE_ON_PLATFORM,		JptJpaCoreValidationMessages.ENTITY_TABLE_PER_CLASS_NOT_PORTABLE_ON_PLATFORM);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.ENTITY_TABLE_PER_CLASS_NOT_SUPPORTED_ON_PLATFORM,	JptJpaCoreValidationMessages.ENTITY_TABLE_PER_CLASS_NOT_SUPPORTED_ON_PLATFORM);
	}

	private void addQueriesGeneratorsCategory(Composite parent) {

		parent = addExpandableSection(parent, JptJpaUiPreferencesValidationMessages.QUERIES_GENERATORS_CATEGORY);

		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.GENERATOR_DUPLICATE_NAME,		JptJpaCoreValidationMessages.GENERATOR_DUPLICATE_NAME);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.GENERATOR_NAME_UNDEFINED,		JptJpaCoreValidationMessages.GENERATOR_NAME_UNDEFINED); //3.0 M7
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.UNRESOLVED_GENERATOR_NAME,	JptJpaCoreValidationMessages.UNRESOLVED_GENERATOR_NAME);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.JPQL_QUERY_VALIDATION,		JptJpaCoreValidationMessages.JPQL_QUERY_VALIDATION);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.QUERY_DUPLICATE_NAME,			JptJpaCoreValidationMessages.QUERY_DUPLICATE_NAME);
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.QUERY_NAME_UNDEFINED,			JptJpaCoreValidationMessages.QUERY_NAME_UNDEFINED); //3.0 M7
		addLabeledCombo(parent, JptJpaUiPreferencesValidationMessages.QUERY_STATEMENT_UNDEFINED,	JptJpaCoreValidationMessages.QUERY_STATEMENT_UNDEFINED); //3.0 M7
	}

	private Composite addExpandableSection(Composite parent, String text) {
		return addExpandableSection(parent, text, new GridData(GridData.FILL, GridData.FILL, true, false));
	}

	private Composite addSubExpandableSection(Composite parent, String text) {
		return addExpandableSection(parent, text, new GridData(GridData.FILL, GridData.FILL, true, false, 2, 1));
	}

	/**
	 * Creates and adds to the given <code>Composite</code> an expandable pane
	 * where its content can be shown or hidden depending on the expansion state.
	 *
	 * @param parent The parent container
	 * @param text The title of the expandable section
	 * @return The container to which widgets can be added, which is a child of
	 * the expandable pane
	 */
	private Composite addExpandableSection(Composite parent, String text, GridData gridData) {
		ExpandableComposite expandablePane = new ExpandableComposite(
			parent,
			SWT.NONE,
			ExpandableComposite.TWISTIE | ExpandableComposite.CLIENT_INDENT
		);

		expandablePane.setText(text);
		expandablePane.setFont(JFaceResources.getFontRegistry().getBold(JFaceResources.DIALOG_FONT));
		expandablePane.setLayoutData(gridData);
		expandablePane.addExpansionListener(buildExpansionListener());

		this.scrollable.adaptChild(expandablePane);
		this.expandablePanes.add(expandablePane);

		parent = new Composite(expandablePane, SWT.NONE);
		parent.setLayout(new GridLayout(2, false));
		expandablePane.setClient(parent);

		return parent;
	}

	/**
	 * Creates and adds to the given parent a labeled combo where the possible
	 * choices are "Ignore", "Error" and "Warning".
	 *
	 * @param parent The parent to which the widgets are added
	 * @param labelText The text labelling the combo
	 * @param preferenceKey The key used to retrieve and to store the value
	 * associated with the validation problem
	 */
	private void addLabeledCombo(Composite parent, String labelText, String preferenceKey) {
		Label label = new Label(parent, SWT.NONE);
		label.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		label.setText(labelText);

		Combo combo = new Combo(parent, SWT.READ_ONLY);
		combo.setItems(this.severityDisplayStrings);
		combo.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL));
		combo.setData(PREFERENCE_KEY, preferenceKey);
		combo.select(convertPreferenceKeyToComboIndex(preferenceKey));
		combo.addSelectionListener(buildComboSelectionListener());

		this.scrollable.adaptChild(combo);
		this.combos.add(combo);
	}

	private SelectionListener buildComboSelectionListener() {
		return new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Combo combo = (Combo) e.widget;
				String preferenceKey = (String) combo.getData(PREFERENCE_KEY);
				String value = convertToPreferenceValue(combo.getSelectionIndex());
				JpaProblemSeveritiesPage.this.severityLevels.put(preferenceKey, value);
			}
		};
	}

	private ExpansionAdapter buildExpansionListener() {
		return new ExpansionAdapter() {
			@Override
			public void expansionStateChanged(ExpansionEvent e) {
				JpaProblemSeveritiesPage.this.expansionStateChanged();
			}
		};
	}

	protected String[] buildSeverityDisplayStrings() {
		String[] severities = new String[4];
		severities[ERROR_INDEX]   = JptJpaUiMessages.JpaProblemSeveritiesPage_Error;
		severities[WARNING_INDEX] = JptJpaUiMessages.JpaProblemSeveritiesPage_Warning;
		severities[INFO_INDEX]    = JptJpaUiMessages.JpaProblemSeveritiesPage_Info;
		severities[IGNORE_INDEX]  = JptJpaUiMessages.JpaProblemSeveritiesPage_Ignore;
		return severities;
	}

	protected int convertPreferenceKeyToComboIndex(String preferenceKey) {
		return convertPreferenceValueToComboIndex(getPreferenceValue(preferenceKey));
	}

	protected String getPreferenceValue(String preferenceKey) {
		String preference = null;
		if (this.isProjectPreferencePage() && this.hasProjectSpecificOptions(this.getProject())) { //useProjectSettings() won't work since the page is being built
			preference = JpaPreferences.getProblemSeverity(this.getProject(), preferenceKey);
		} else {
			//don't get the workspace preference when the project has overridden workspace preferences
			preference = JpaPreferences.getProblemSeverity(preferenceKey);
		}
		if (preference == null) {
			preference = getDefaultPreferenceValue(preferenceKey);
		}
		return preference;
	}

	/**
	 * Return the default severity or ERROR if no default exists.
	 */
	protected String getDefaultPreferenceValue(String preferenceKey) {
		String preference = this.defaultSeverities.get(preferenceKey);
		return preference == null ? JpaPreferences.PROBLEM_ERROR : preference;
	}

	protected int convertPreferenceValueToComboIndex(String preferenceValue) {
		if (JpaPreferences.PROBLEM_ERROR.equals(preferenceValue)) {
			return ERROR_INDEX;
		}
		if (JpaPreferences.PROBLEM_WARNING.equals(preferenceValue)) {
			return WARNING_INDEX;
		}
		if (JpaPreferences.PROBLEM_INFO.equals(preferenceValue)) {
			return INFO_INDEX;
		}
		if (JpaPreferences.PROBLEM_IGNORE.equals(preferenceValue)) {
			return IGNORE_INDEX;
		}
		throw new IllegalStateException();
	}

	protected String convertToPreferenceValue(int selectionIndex) {
		switch (selectionIndex) {
			case ERROR_INDEX:   return JpaPreferences.PROBLEM_ERROR;
			case WARNING_INDEX: return JpaPreferences.PROBLEM_WARNING;
			case INFO_INDEX:    return JpaPreferences.PROBLEM_INFO;
			case IGNORE_INDEX:  return JpaPreferences.PROBLEM_IGNORE;
			default:            return null;
		}
	}

	/**
	 * Revalidates the layout in order to show or hide the vertical scroll bar
	 * after a section was either expanded or collapsed. This unfortunately does
	 * not happen automatically.
	 */
	protected void expansionStateChanged() {
		this.scrollable.reflow(true);
	}

	@Override
	protected String getPreferencePageID() {
		return JPT_PREFERENCES_PROBLEM_SEVERITIES_ID;
	}

	@Override
	protected String getPropertyPageID() {
		return JPT_PROPERTY_PAGES_PROBLEM_SEVERITIES_ID;
	}

	@Override
	protected boolean hasProjectSpecificOptions(IProject project) {
		return JpaPreferences.getWorkspaceValidationPreferencesOverridden(project);
	}

	@Override
	protected void performDefaults() {
		super.performDefaults();
		//this call would be redundant on project preference page - bug in the JDT superclass
		if (!isProjectPreferencePage()) {
			revertToDefault();
		}
	}

	@Override
	protected void enableProjectSpecificSettings(boolean useProjectSpecificSettings) {
		super.enableProjectSpecificSettings(useProjectSpecificSettings);
		if (getDefaultsButton() == null) {
			//@Hack("If the defaults button is null the control is currently being built," +
			//      "otherwise the 'enable project specific settings' checkbox is being pressed")
			return;
		}

		this.hasProjectSpecificPreferences = Boolean.valueOf(useProjectSpecificSettings);

		//set all specified workspace preferences in the project preferences
		if (useProjectSpecificSettings){
			this.overrideWorkspacePreferences();
		}
		else {//remove any project specific settings if set to false
			this.revertToDefault();
		}
	}

	@Override
	protected void noDefaultAndApplyButton() {
		throw new IllegalStateException("Don't call this, see enableProjectSpecificSettings for the hack that looks for the defaultsButton being null"); //$NON-NLS-1$
	}

	protected void revertToDefault() {
		for (Combo combo : this.combos) {
			String preferenceKey = (String) combo.getData(PREFERENCE_KEY);
			combo.select(convertPreferenceValueToComboIndex(getDefaultPreferenceValue(preferenceKey)));
			//UI will show the defaults from the workspace, but set all preferences
			//to null so they will be deleted from project preferences
			this.severityLevels.put(preferenceKey, null);
		}
	}

	protected void overrideWorkspacePreferences() {
		for (Combo combo : this.combos) {
			String preferenceKey = (String) combo.getData(PREFERENCE_KEY);
			String workspacePreference = JpaPreferences.getProblemSeverity(preferenceKey);
			String defaultPreference = getDefaultPreferenceValue(preferenceKey);
			if (workspacePreference != null && !workspacePreference.equals(defaultPreference)) {
				combo.select(convertPreferenceValueToComboIndex(workspacePreference));
				//silly combo doesn't fire a selection event, so we can't expect our listener to set this
				this.severityLevels.put(preferenceKey, workspacePreference);
			}
			else {
				combo.select(convertPreferenceValueToComboIndex(defaultPreference));
			}
		}
	}

	// ********** OK/Revert/Apply behavior **********

	@Override
	public boolean performOk() {
		super.performOk();
		if (this.hasProjectSpecificPreferences != null) {
			JpaPreferences.setWorkspaceValidationPreferencesOverridden(this.getProject(), this.hasProjectSpecificPreferences.booleanValue());
		}
		for (String validationPreferenceKey : this.severityLevels.keySet()) {
			this.updatePreference(validationPreferenceKey, this.severityLevels.get(validationPreferenceKey));
		}
		try {
			// true=fork; false=uncancellable
			this.buildOkProgressMonitorDialog().run(true, false, this.buildOkRunnableWithProgress());
		}
		catch (InterruptedException ex) {
			// should *not* happen...
			Thread.currentThread().interrupt();
			return false;
		}
		catch (InvocationTargetException ex) {
			throw new RuntimeException(ex.getTargetException());
		}

		return true;
	}

	protected void updatePreference(String preferenceKey, String value) {
		if (this.isProjectPreferencePage()) {
			JpaPreferences.setProblemSeverity(this.getProject(), preferenceKey, value);
		} else {
			JpaPreferences.setProblemSeverity(preferenceKey, value);
		}
	}

	private IRunnableContext buildOkProgressMonitorDialog() {
		return new ProgressMonitorDialog(this.getShell());
	}

	private IRunnableWithProgress buildOkRunnableWithProgress() {
		return new IRunnableWithProgress() {
			public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
				IWorkspace ws = ResourcesPlugin.getWorkspace();
				try {
					// the build we execute in #performOk_() locks the workspace root,
					// so we need to use the workspace root as our scheduling rule here
					ws.run(
							JpaProblemSeveritiesPage.this.buildOkWorkspaceRunnable(),
							ws.getRoot(),
							IWorkspace.AVOID_UPDATE,
							monitor
					);
				}
				catch (CoreException ex) {
					throw new InvocationTargetException(ex);
				}
			}
		};
	}

	IWorkspaceRunnable buildOkWorkspaceRunnable() {
		return new IWorkspaceRunnable() {
			public void run(IProgressMonitor monitor) throws CoreException {
				JpaProblemSeveritiesPage.this.performOk_(monitor);
			}
		};
	}

	void performOk_(IProgressMonitor monitor) throws CoreException {
		int buildKind = IncrementalProjectBuilder.FULL_BUILD;
		IProject project = this.getProject();
		if (project != null) {
			// project preference page
			project.build(buildKind, monitor);
		} else {
			// workspace preference page
			ResourcesPlugin.getWorkspace().build(buildKind, monitor);
		}
	}

	@Override
	public void dispose() {
		storeSectionExpansionStates(getDialogPreferences());
		super.dispose();
	}

	protected IDialogSettings getDialogPreferences() {
		return JptJpaUiPlugin.instance().getDialogSettings(SETTINGS_SECTION_NAME);
	}

	protected void storeSectionExpansionStates(IDialogSettings settings) {
		for (int index = this.expandablePanes.size(); --index >= 0; ) {
			ExpandableComposite expandablePane = this.expandablePanes.get(index);
			settings.put(SETTINGS_EXPANDED + index, expandablePane.isExpanded());
		}
	}

}
