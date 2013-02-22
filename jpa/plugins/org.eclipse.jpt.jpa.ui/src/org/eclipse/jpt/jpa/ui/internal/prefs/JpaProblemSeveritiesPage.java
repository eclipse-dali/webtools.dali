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
import org.eclipse.jpt.common.core.utility.ValidationMessage;
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
 * This page shows the JPA validation severity settings. It supports
 * workspace- and project-level severities.
 */
@SuppressWarnings("restriction")
public class JpaProblemSeveritiesPage
	extends PropertyAndPreferencePage
{
	/**
	 * Changed severities are stored in this map and either committed
	 * (e.g. when the user presses the OK button) or discarded
	 * (e.g. when the user presses the Cancel button).<ul>
	 * <li> key = preference key (which is the
	 * {@link ValidationMessage#getID() validation message ID})
	 * <li> value = preference severity level:<ul>
	 *   <li>{@link JpaPreferences#PROBLEM_ERROR}
	 *   <li>{@link JpaPreferences#PROBLEM_WARNING}
	 *   <li>{@link JpaPreferences#PROBLEM_INFO}
	 *   <li>{@link JpaPreferences#PROBLEM_IGNORE}
	 *   </ul>
	 * </ul>
	 */
	/* CU private */ HashMap<String, String> changedSeverities = new HashMap<String, String>();

	/**
	 * Cache the {@link Combo}s so we can revert the settings.
	 */
	private ArrayList<Combo> combos = new ArrayList<Combo>();

	/**
	 * Cache the {@link ExpandableComposite}s so we can save
	 * and restore the page's expansion state.
	 */
	private ArrayList<ExpandableComposite> expandablePanes = new ArrayList<ExpandableComposite>();

	/**
	 * The unique identifier for this page when it is shown in the IDE
	 * preferences dialog.
	 */
	private static final String PREFERENCE_PAGE_ID = "org.eclipse.jpt.jpa.ui.jpaProblemSeveritiesPreferences"; //$NON-NLS-1$

	/**
	 * The unique identifier for this page when it is shown in the project
	 * properties dialog.
	 */
	private static final String PROPERTY_PAGE_ID = "org.eclipse.jpt.jpa.ui.jpaProblemSeveritiesProperties"; //$NON-NLS-1$

	/**
	 * The key used to store and retrieve a combo's validation message.
	 * @see org.eclipse.swt.widgets.Widget#getData(String)
	 */
	/* CU private */ static final String VALIDATION_MESSAGE = ValidationMessage.class.getName();

	/**
	 * The scrollable pane used to show the content of this page.
	 */
	private ScrolledPageContent scrollable;

	/**
	 * The possible choices which describes the severity of a single problem.
	 */
	private PreferenceSeverity[] preferenceSeverities;
	private String[] severityDisplayStrings;

	private Boolean hasProjectSpecificPreferences = null;

	/**
	 * Constant used to store the expansion state of each expandable pane.
	 */
	public static final String SETTINGS_EXPANDED = "expanded"; //$NON-NLS-1$

	/**
	 * The preference key used to retrieve the dialog settings where the expansion
	 * states have been stored.
	 */
	public static final String SETTINGS_SECTION_NAME = "JpaProblemSeveritiesPage"; //$NON-NLS-1$


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
		this.preferenceSeverities = this.buildPreferenceSeverities();
		this.severityDisplayStrings = this.buildSeverityDisplayStrings();
	}

	protected PreferenceSeverity[] buildPreferenceSeverities() {
		return DEFAULT_PREFERENCE_SEVERITIES;
	}

	protected static final PreferenceSeverity[] DEFAULT_PREFERENCE_SEVERITIES = buildDefaultPreferenceSeverities();
	protected static PreferenceSeverity[] buildDefaultPreferenceSeverities() {
		ArrayList<PreferenceSeverity> severities = new ArrayList<PreferenceSeverity>();
		severities.add(new PreferenceSeverity(JpaPreferences.PROBLEM_ERROR, JptJpaUiMessages.JpaProblemSeveritiesPage_Error));
		severities.add(new PreferenceSeverity(JpaPreferences.PROBLEM_WARNING, JptJpaUiMessages.JpaProblemSeveritiesPage_Warning));
		severities.add(new PreferenceSeverity(JpaPreferences.PROBLEM_INFO, JptJpaUiMessages.JpaProblemSeveritiesPage_Info));
		severities.add(new PreferenceSeverity(JpaPreferences.PROBLEM_IGNORE, JptJpaUiMessages.JpaProblemSeveritiesPage_Ignore));
		return severities.toArray(new PreferenceSeverity[severities.size()]);
	}

	/**
	 * Pair a preference value with its localized display string
	 * (e.g. <code>"error"</code> and <code>"Error"</code>).
	 */
	public static class PreferenceSeverity {
		public final String preferenceValue;
		public final String displayString;
		public PreferenceSeverity(String preferenceValue, String displayString) {
			super();
			this.preferenceValue = preferenceValue;
			this.displayString = displayString;
		}
	}

	/**
	 * Pre-condition: {@link #preferenceSeverities} is already built.
	 */
	protected String[] buildSeverityDisplayStrings() {
		int len = this.preferenceSeverities.length;
		String[] displayStrings = new String[len];
		for (int i = 0; i < len; i++) {
			displayStrings[i] = this.preferenceSeverities[i].displayString;
		}
		return displayStrings;
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
		this.addProjectLevelCategory(parent);
		this.addPersistenceUnitLevelCategory(parent);
		this.addTypeLevelCategory(parent);
		this.addAttributeLevelCategory(parent);
		this.addDatabaseCategory(parent);
		this.addInheritanceStrategyCategory(parent);
		this.addQueriesGeneratorsCategory(parent);

		// Restore the expansion states
		this.restoreSectionExpansionStates(this.getDialogPreferences());

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

		parent = this.addExpandableSection(parent, JptJpaUiPreferencesValidationMessages.PROJECT_LEVEL_CATEGORY);

		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.NO_JPA_PROJECT);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.PERSISTENCE_MULTIPLE_PERSISTENCE_UNITS);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.PERSISTENCE_NO_PERSISTENCE_UNIT);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.PERSISTENCE_XML_INVALID_CONTENT);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.PERSISTENCE_XML_UNSUPPORTED_CONTENT); //3.0 M7
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.PROJECT_MULTIPLE_PERSISTENCE_XML);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.PROJECT_INACTIVE_CONNECTION);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.PROJECT_INVALID_CONNECTION);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.PROJECT_INVALID_LIBRARY_PROVIDER); //3.0 M7
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.PROJECT_NO_CONNECTION);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.PROJECT_NO_PERSISTENCE_XML);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.XML_VERSION_NOT_LATEST);
	}

	private void addPersistenceUnitLevelCategory(Composite parent) {

		parent = this.addExpandableSection(parent, JptJpaUiPreferencesValidationMessages.PERSISTENCE_UNIT_LEVEL_CATEGORY);

		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.MAPPING_FILE_EXTRANEOUS_PERSISTENCE_UNIT_METADATA);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.PERSISTENT_TYPE_DUPLICATE_CLASS);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.PERSISTENCE_UNIT_DUPLICATE_CLASS);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.PERSISTENCE_UNIT_DUPLICATE_JAR_FILE);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.PERSISTENCE_UNIT_DUPLICATE_MAPPING_FILE);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.PERSISTENCE_UNIT_INVALID_CLASS);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.PERSISTENCE_UNIT_INVALID_MAPPING_FILE);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.PERSISTENCE_UNIT_JAR_FILE_DEPLOYMENT_PATH_WARNING);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.PERSISTENCE_UNIT_NONEXISTENT_CLASS);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.PERSISTENCE_UNIT_LISTED_CLASS_IS_AN_INTERFACE);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.PERSISTENCE_UNIT_LISTED_CLASS_IS_AN_ENUM);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.PERSISTENCE_UNIT_NONEXISTENT_JAR_FILE);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.PERSISTENCE_UNIT_NONEXISTENT_MAPPING_FILE);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.PERSISTENCE_UNIT_REDUNDANT_CLASS);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.PERSISTENCE_UNIT_UNSPECIFIED_CLASS);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.PERSISTENCE_UNIT_UNSPECIFIED_JAR_FILE);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.PERSISTENCE_UNIT_UNSPECIFIED_MAPPING_FILE);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.PERSISTENCE_UNIT_UNSUPPORTED_MAPPING_FILE_CONTENT);
	}

	private void addTypeLevelCategory(Composite parent) {

		parent = this.addExpandableSection(parent, JptJpaUiPreferencesValidationMessages.TYPE_LEVEL_CATEGORY);

		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.ENTITY_NAME_DUPLICATED); //3.0 M7
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.ENTITY_NAME_MISSING); //3.0 M7
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.ENTITY_NO_PK);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.ENTITY_NON_ROOT_ID_ATTRIBUTE_SPECIFIED); //3.0 M7
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.ENTITY_NON_ROOT_ID_CLASS_SPECIFIED); //3.0 M7
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.TYPE_ANNOTATED_BUT_NOT_LISTED_IN_PERSISTENCE_XML);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.TYPE_MANAGED_BUT_NOT_LISTED_IN_PERSISTENCE_XML);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.MANAGED_TYPE_UNRESOLVED_CLASS);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.MANAGED_TYPE_UNSPECIFIED_CLASS);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.TYPE_MAPPING_CLASS_MISSING_NO_ARG_CONSTRUCTOR);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.TYPE_MAPPING_CLASS_PRIVATE_NO_ARG_CONSTRUCTOR);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.TYPE_MAPPING_FINAL_CLASS);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.TYPE_MAPPING_ID_AND_EMBEDDED_ID_BOTH_USED);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.TYPE_MAPPING_ID_CLASS_AND_EMBEDDED_ID_BOTH_USED);	//3.0 M7
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.TYPE_MAPPING_ID_CLASS_ATTRIBUTE_DOES_NOT_EXIST);	//3.2
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.TYPE_MAPPING_ID_CLASS_ATTRIBUTE_MAPPING_DUPLICATE_MATCH);	//3.0 M7
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.TYPE_MAPPING_ID_CLASS_ATTRIBUTE_MAPPING_NO_MATCH);	//3.0 M7
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.TYPE_MAPPING_ID_CLASS_ATTRIBUTE_NO_MATCH);	//3.0 M7
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.TYPE_MAPPING_ID_CLASS_ATTRIBUTE_NOT_PRIMARY_KEY);	//3.0 M7
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.TYPE_MAPPING_ID_CLASS_ATTRIBUTE_TYPE_DOES_NOT_AGREE);	//3.0 M7
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.TYPE_MAPPING_ID_CLASS_MISSING_EQUALS_METHOD);	//3.2
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.TYPE_MAPPING_ID_CLASS_MISSING_HASHCODE_METHOD); //3.2
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.TYPE_MAPPING_ID_CLASS_MISSING_NO_ARG_CONSTRUCTOR); //3.2
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.TYPE_MAPPING_ID_CLASS_MISSING_PUBLIC_NO_ARG_CONSTRUCTOR); //3.2
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.TYPE_MAPPING_ID_CLASS_NAME_EMPTY);	//3.0 M7
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.TYPE_MAPPING_ID_CLASS_NOT_EXIST);	//3.0 M7
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.TYPE_MAPPING_ID_CLASS_NOT_IMPLEMENT_SERIALIZABLE);	//3.2
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.TYPE_MAPPING_ID_CLASS_NOT_PUBLIC);	//3.2
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.TYPE_MAPPING_ID_CLASS_PROPERTY_METHOD_NOT_PUBLIC);	//3.2
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.TYPE_MAPPING_ID_CLASS_REDEFINED);	//3.0 M7
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.TYPE_MAPPING_ID_CLASS_REQUIRED);	//3.0 M7
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.TYPE_MAPPING_ID_CLASS_WITH_MAPS_ID);	//3.0 M7
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.TYPE_MAPPING_MAPS_ID_ATTRIBUTE_TYPE_DOES_NOT_AGREE);	//3.0 M7
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.TYPE_MAPPING_MEMBER_CLASS);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.TYPE_MAPPING_MULTIPLE_EMBEDDED_ID);	//3.0 M7
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.TYPE_MAPPING_PK_REDEFINED_ID_ATTRIBUTE);	//3.0 M7
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.TYPE_MAPPING_PK_REDEFINED_ID_CLASS);	//3.0 M7
	}

	private void addAttributeLevelCategory(Composite parent) {

		parent = this.addExpandableSection(parent, JptJpaUiPreferencesValidationMessages.ATTRIBUTE_LEVEL_CATEGORY);

		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.ELEMENT_COLLECTION_TARGET_CLASS_MUST_BE_EMBEDDABLE_OR_BASIC_TYPE);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.ELEMENT_COLLECTION_TARGET_CLASS_DOES_NOT_EXIST); //3.0 M7
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.ELEMENT_COLLECTION_TARGET_CLASS_NOT_DEFINED);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.ELEMENT_COLLECTION_CONTAINS_EMBEDDABLE_WITH_ELEMENT_COLLECTION_MAPPING);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.ELEMENT_COLLECTION_CONTAINS_EMBEDDABLE_WITH_PROHIBITED_RELATIONSHIP_MAPPING);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.EMBEDDED_ID_CLASS_SHOULD_BE_PUBLIC);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.EMBEDDED_ID_CLASS_SHOULD_IMPLEMENT_EQUALS_HASHCODE);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.EMBEDDED_ID_CLASS_SHOULD_IMPLEMENT_NO_ARG_CONSTRUCTOR);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.EMBEDDED_ID_CLASS_SHOULD_IMPLEMENT_SERIALIZABLE);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.EMBEDDED_ID_CLASS_SHOULD_NOT_CONTAIN_RELATIONSHIP_MAPPINGS);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.EMBEDDED_ID_MAPPING_MAPPED_BY_RELATIONSHIP_AND_ATTRIBUTE_OVERRIDES_SPECIFIED); //3.0 M7
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.ID_MAPPING_MAPPED_BY_RELATIONSHIP_AND_COLUMN_SPECIFIED); 	//3.0 M7
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.MAPS_ID_VALUE_INVALID);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.MAPS_ID_VALUE_NOT_RESOLVED);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.MAPS_ID_VALUE_NOT_SPECIFIED);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.MAPPING_INVALID_MAPPED_BY);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.MAPPING_MAPPED_BY_ON_BOTH_SIDES);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.MAPPING_UNRESOLVED_MAPPED_BY);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.ORDER_COLUMN_AND_ORDER_BY_BOTH_SPECIFIED);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.PERSISTENT_ATTRIBUTE_ELEMENT_COLLECTION_INVALID_VALUE_TYPE); //3.0 M7
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.PERSISTENT_ATTRIBUTE_FINAL_FIELD);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.PERSISTENT_ATTRIBUTE_FINAL_GETTER);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.PERSISTENT_ATTRIBUTE_INHERITED_ATTRIBUTES_NOT_SUPPORTED);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.PERSISTENT_ATTRIBUTE_INVALID_MAPPING);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.PERSISTENT_ATTRIBUTE_INVALID_TEMPORAL_MAPPING_TYPE); //3.0 M7
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.PERSISTENT_ATTRIBUTE_INVALID_TEMPORAL_MAP_KEY_TYPE);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.PERSISTENT_ATTRIBUTE_INVALID_VERSION_MAPPING_TYPE); //3.0 M7
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.PERSISTENT_ATTRIBUTE_PUBLIC_FIELD);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.PERSISTENT_ATTRIBUTE_UNRESOLVED_NAME);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.PERSISTENT_ATTRIBUTE_UNSPECIFIED_NAME);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.TARGET_ENTITY_IS_NOT_AN_ENTITY);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.TARGET_ENTITY_NOT_DEFINED);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.TARGET_ENTITY_NOT_EXIST);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.TARGET_NOT_AN_EMBEDDABLE); //3.0 M7
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.MAP_KEY_CLASS_NOT_DEFINED);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.MAP_KEY_CLASS_NOT_EXIST);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.MAP_KEY_CLASS_MUST_BE_ENTITY_EMBEDDABLE_OR_BASIC_TYPE);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.ATTRIBUTE_TYPE_IS_NOT_SUPPORTED_COLLECTION_TYPE);

		parent = this.addSubExpandableSection(parent, JptJpaUiPreferencesValidationMessages.IMPLIED_ATTRIBUTE_LEVEL_CATEGORY);

		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_ELEMENT_COLLECTION_TARGET_CLASS_DOES_NOT_EXIST);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_ELEMENT_COLLECTION_TARGET_CLASS_MUST_BE_EMBEDDABLE_OR_BASIC_TYPE);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_ELEMENT_COLLECTION_TARGET_CLASS_NOT_DEFINED);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_ELEMENT_COLLECTION_CONTAINS_EMBEDDABLE_WITH_ELEMENT_COLLECTION_MAPPING);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_ELEMENT_COLLECTION_CONTAINS_EMBEDDABLE_WITH_PROHIBITED_RELATIONSHIP_MAPPING);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_EMBEDDED_ID_CLASS_SHOULD_BE_PUBLIC);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_EMBEDDED_ID_CLASS_SHOULD_IMPLEMENT_EQUALS_HASHCODE);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_EMBEDDED_ID_CLASS_SHOULD_IMPLEMENT_NO_ARG_CONSTRUCTOR);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_EMBEDDED_ID_CLASS_SHOULD_IMPLEMENT_SERIALIZABLE);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_EMBEDDED_ID_CLASS_SHOULD_NOT_CONTAIN_RELATIONSHIP_MAPPINGS);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_EMBEDDED_ID_MAPPING_MAPPED_BY_RELATIONSHIP_AND_ATTRIBUTE_OVERRIDES_SPECIFIED);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_TARGET_ENTITY_IS_NOT_AN_ENTITY);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_TARGET_ENTITY_NOT_DEFINED);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_TARGET_NOT_AN_EMBEDDABLE);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_MAP_KEY_CLASS_NOT_DEFINED);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_MAP_KEY_CLASS_MUST_BE_ENTITY_EMBEDDABLE_OR_BASIC_TYPE);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_INVALID_TEMPORAL_MAP_KEY_TYPE);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_ELEMENT_COLLECTION_INVALID_VALUE_TYPE);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_INVALID_TEMPORAL_MAPPING_TYPE);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_ATTRIBUTE_TYPE_IS_NOT_SUPPORTED_COLLECTION_TYPE);
	}

	private void addDatabaseCategory(Composite parent) {
		parent = this.addExpandableSection(parent, JptJpaUiPreferencesValidationMessages.DATABASE_CATEGORY);
		parent.setLayout(new GridLayout(1, false));

		this.addTableCategory(parent);
		this.addColumnCategory(parent);
		this.addOverridesCategory(parent);
	}

	private void addTableCategory(Composite parent) {

		parent = this.addExpandableSection(parent, JptJpaUiPreferencesValidationMessages.TABLE_CATEGORY);

		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.COLLECTION_TABLE_UNRESOLVED_CATALOG);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.COLLECTION_TABLE_UNRESOLVED_NAME);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.COLLECTION_TABLE_UNRESOLVED_SCHEMA);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.JOIN_TABLE_UNRESOLVED_CATALOG);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.JOIN_TABLE_UNRESOLVED_NAME);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.JOIN_TABLE_UNRESOLVED_SCHEMA);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.SECONDARY_TABLE_UNRESOLVED_CATALOG);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.SECONDARY_TABLE_UNRESOLVED_NAME);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.SECONDARY_TABLE_UNRESOLVED_SCHEMA);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.TABLE_UNRESOLVED_CATALOG);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.TABLE_UNRESOLVED_NAME);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.TABLE_UNRESOLVED_SCHEMA);

		parent = this.addSubExpandableSection(parent, JptJpaUiPreferencesValidationMessages.IMPLIED_ATTRIBUTE_LEVEL_CATEGORY);

		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_COLLECTION_TABLE_UNRESOLVED_CATALOG);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_COLLECTION_TABLE_UNRESOLVED_NAME);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_COLLECTION_TABLE_UNRESOLVED_SCHEMA);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_JOIN_TABLE_UNRESOLVED_CATALOG);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_JOIN_TABLE_UNRESOLVED_NAME);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_JOIN_TABLE_UNRESOLVED_SCHEMA);
	}

	private void addColumnCategory(Composite parent) {
		parent = this.addExpandableSection(parent, JptJpaUiPreferencesValidationMessages.COLUMN_CATEGORY);

		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.COLUMN_TABLE_NOT_VALID);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.COLUMN_UNRESOLVED_NAME);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.COLUMN_UNRESOLVED_TABLE);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.INVERSE_JOIN_COLUMN_NAME_MUST_BE_SPECIFIED_MULTIPLE_JOIN_COLUMNS);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.INVERSE_JOIN_COLUMN_REFERENCED_COLUMN_NAME_MUST_BE_SPECIFIED_MULTIPLE_JOIN_COLUMNS);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.INVERSE_JOIN_COLUMN_TABLE_NOT_VALID);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.INVERSE_JOIN_COLUMN_UNRESOLVED_NAME);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.INVERSE_JOIN_COLUMN_UNRESOLVED_REFERENCED_COLUMN_NAME);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.JOIN_COLUMN_NAME_MUST_BE_SPECIFIED_MULTIPLE_JOIN_COLUMNS);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.JOIN_COLUMN_REFERENCED_COLUMN_NAME_MUST_BE_SPECIFIED_MULTIPLE_JOIN_COLUMNS);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.JOIN_COLUMN_TABLE_NOT_VALID);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.JOIN_COLUMN_UNRESOLVED_NAME);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.JOIN_COLUMN_UNRESOLVED_REFERENCED_COLUMN_NAME);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.MAP_KEY_COLUMN_TABLE_NOT_VALID);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.MAP_KEY_COLUMN_UNRESOLVED_NAME); // 3.0 M7
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.ORDER_COLUMN_UNRESOLVED_NAME);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.PRIMARY_KEY_JOIN_COLUMN_NAME_MUST_BE_SPECIFIED_MULTIPLE_JOIN_COLUMNS);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.PRIMARY_KEY_JOIN_COLUMN_REFERENCED_COLUMN_NAME_MUST_BE_SPECIFIED_MULTIPLE_JOIN_COLUMNS);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.PRIMARY_KEY_JOIN_COLUMN_UNRESOLVED_NAME);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.PRIMARY_KEY_JOIN_COLUMN_UNRESOLVED_REFERENCED_COLUMN_NAME);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.MAP_KEY_JOIN_COLUMN_NAME_MUST_BE_SPECIFIED_MULTIPLE_JOIN_COLUMNS);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.MAP_KEY_JOIN_COLUMN_TABLE_NOT_VALID);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.MAP_KEY_JOIN_COLUMN_REFERENCED_COLUMN_NAME_MUST_BE_SPECIFIED_MULTIPLE_JOIN_COLUMNS);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.MAP_KEY_JOIN_COLUMN_UNRESOLVED_NAME);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.MAP_KEY_JOIN_COLUMN_UNRESOLVED_REFERENCED_COLUMN_NAME);

		parent = this.addSubExpandableSection(parent, JptJpaUiPreferencesValidationMessages.IMPLIED_ATTRIBUTE_LEVEL_CATEGORY);

		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_COLUMN_TABLE_NOT_VALID);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_COLUMN_UNRESOLVED_NAME);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_INVERSE_JOIN_COLUMN_NAME_MUST_BE_SPECIFIED_MULTIPLE_JOIN_COLUMNS);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_INVERSE_JOIN_COLUMN_REFERENCED_COLUMN_NAME_MUST_BE_SPECIFIED_MULTIPLE_JOIN_COLUMNS);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_INVERSE_JOIN_COLUMN_TABLE_NOT_VALID);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_INVERSE_JOIN_COLUMN_UNRESOLVED_NAME);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_INVERSE_JOIN_COLUMN_UNRESOLVED_REFERENCED_COLUMN_NAME);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_JOIN_COLUMN_NAME_MUST_BE_SPECIFIED_MULTIPLE_JOIN_COLUMNS);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_JOIN_COLUMN_REFERENCED_COLUMN_NAME_MUST_BE_SPECIFIED_MULTIPLE_JOIN_COLUMNS);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_JOIN_COLUMN_UNRESOLVED_NAME);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_JOIN_COLUMN_UNRESOLVED_REFERENCED_COLUMN_NAME);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_JOIN_COLUMN_TABLE_NOT_VALID);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_MAP_KEY_COLUMN_TABLE_NOT_VALID);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_MAP_KEY_COLUMN_UNRESOLVED_NAME); // 3.0 M7
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_ORDER_COLUMN_UNRESOLVED_NAME);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.VIRTUAL_SECONDARY_TABLE_PRIMARY_KEY_JOIN_COLUMN_NAME_MUST_BE_SPECIFIED_MULTIPLE_JOIN_COLUMNS);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.VIRTUAL_SECONDARY_TABLE_PRIMARY_KEY_JOIN_COLUMN_REFERENCED_COLUMN_NAME_MUST_BE_SPECIFIED_MULTIPLE_JOIN_COLUMNS);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.VIRTUAL_SECONDARY_TABLE_PRIMARY_KEY_JOIN_COLUMN_UNRESOLVED_NAME);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.VIRTUAL_SECONDARY_TABLE_PRIMARY_KEY_JOIN_COLUMN_UNRESOLVED_REFERENCED_COLUMN_NAME);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.VIRTUAL_PRIMARY_KEY_JOIN_COLUMN_NAME_MUST_BE_SPECIFIED_MULTIPLE_JOIN_COLUMNS);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.VIRTUAL_PRIMARY_KEY_JOIN_COLUMN_REFERENCED_COLUMN_NAME_MUST_BE_SPECIFIED_MULTIPLE_JOIN_COLUMNS);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.VIRTUAL_PRIMARY_KEY_JOIN_COLUMN_UNRESOLVED_NAME);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.VIRTUAL_PRIMARY_KEY_JOIN_COLUMN_UNRESOLVED_REFERENCED_COLUMN_NAME);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_MAP_KEY_JOIN_COLUMN_NAME_MUST_BE_SPECIFIED_MULTIPLE_MAP_KEY_JOIN_COLUMNS);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_MAP_KEY_JOIN_COLUMN_REFERENCED_COLUMN_NAME_MUST_BE_SPECIFIED_MULTIPLE_MAP_KEY_JOIN_COLUMNS);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_MAP_KEY_JOIN_COLUMN_TABLE_NOT_VALID);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_MAP_KEY_JOIN_COLUMN_UNRESOLVED_NAME);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_MAP_KEY_JOIN_COLUMN_UNRESOLVED_REFERENCED_COLUMN_NAME);
	}

	private void addOverridesCategory(Composite parent) {

		parent = this.addExpandableSection(parent, JptJpaUiPreferencesValidationMessages.OVERRIDES_CATEGORY);

		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.ASSOCIATION_OVERRIDE_INVALID_NAME);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.ATTRIBUTE_OVERRIDE_INVALID_NAME);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.ATTRIBUTE_OVERRIDE_INVALID_TYPE);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.ATTRIBUTE_OVERRIDE_DERIVED_AND_SPECIFIED); //3.0 M7
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.VIRTUAL_ASSOCIATION_OVERRIDE_INVALID_NAME);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.VIRTUAL_ASSOCIATION_OVERRIDE_INVERSE_JOIN_COLUMN_NAME_MUST_BE_SPECIFIED_MULTIPLE_INVERSE_JOIN_COLUMNS);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.VIRTUAL_ASSOCIATION_OVERRIDE_INVERSE_JOIN_COLUMN_REFERENCED_COLUMN_NAME_MUST_BE_SPECIFIED_MULTIPLE_INVERSE_JOIN_COLUMNS);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.VIRTUAL_ASSOCIATION_OVERRIDE_INVERSE_JOIN_COLUMN_TABLE_NOT_VALID);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.VIRTUAL_ASSOCIATION_OVERRIDE_INVERSE_JOIN_COLUMN_UNRESOLVED_NAME);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.VIRTUAL_ASSOCIATION_OVERRIDE_INVERSE_JOIN_COLUMN_UNRESOLVED_REFERENCED_COLUMN_NAME);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.VIRTUAL_ASSOCIATION_OVERRIDE_JOIN_COLUMN_NAME_MUST_BE_SPECIFIED_MULTIPLE_JOIN_COLUMNS);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.VIRTUAL_ASSOCIATION_OVERRIDE_JOIN_COLUMN_REFERENCED_COLUMN_NAME_MUST_BE_SPECIFIED_MULTIPLE_JOIN_COLUMNS);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.VIRTUAL_ASSOCIATION_OVERRIDE_JOIN_COLUMN_TABLE_NOT_VALID);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.VIRTUAL_ASSOCIATION_OVERRIDE_JOIN_COLUMN_UNRESOLVED_NAME);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.VIRTUAL_ASSOCIATION_OVERRIDE_JOIN_COLUMN_UNRESOLVED_REFERENCED_COLUMN_NAME);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.VIRTUAL_ASSOCIATION_OVERRIDE_JOIN_TABLE_UNRESOLVED_CATALOG);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.VIRTUAL_ASSOCIATION_OVERRIDE_JOIN_TABLE_UNRESOLVED_NAME);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.VIRTUAL_ASSOCIATION_OVERRIDE_JOIN_TABLE_UNRESOLVED_SCHEMA);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_OVERRIDE_COLUMN_TABLE_NOT_VALID);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_OVERRIDE_COLUMN_UNRESOLVED_NAME);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_OVERRIDE_INVALID_NAME);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.VIRTUAL_MAP_KEY_ATTRIBUTE_OVERRIDE_COLUMN_TABLE_NOT_VALID);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.VIRTUAL_MAP_KEY_ATTRIBUTE_OVERRIDE_INVALID_NAME);


		parent = this.addSubExpandableSection(parent, JptJpaUiPreferencesValidationMessages.IMPLIED_ATTRIBUTE_LEVEL_CATEGORY);

		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_ASSOCIATION_OVERRIDE_INVALID_NAME);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_ASSOCIATION_OVERRIDE_INVERSE_JOIN_COLUMN_NAME_MUST_BE_SPECIFIED_MULTIPLE_INVERSE_JOIN_COLUMNS);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_ASSOCIATION_OVERRIDE_INVERSE_JOIN_COLUMN_REFERENCED_COLUMN_NAME_MUST_BE_SPECIFIED_MULTIPLE_INVERSE_JOIN_COLUMNS);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_ASSOCIATION_OVERRIDE_INVERSE_JOIN_COLUMN_REFERENCED_COLUMN_UNRESOLVED_NAME);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_ASSOCIATION_OVERRIDE_INVERSE_JOIN_COLUMN_TABLE_NOT_VALID);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_ASSOCIATION_OVERRIDE_INVERSE_JOIN_COLUMN_UNRESOLVED_NAME);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_ASSOCIATION_OVERRIDE_JOIN_COLUMN_NAME_MUST_BE_SPECIFIED_MULTIPLE_JOIN_COLUMNS);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_ASSOCIATION_OVERRIDE_JOIN_COLUMN_REFERENCED_COLUMN_NAME_MUST_BE_SPECIFIED_MULTIPLE_JOIN_COLUMNS);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_ASSOCIATION_OVERRIDE_JOIN_COLUMN_REFERENCED_COLUMN_UNRESOLVED_NAME);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_ASSOCIATION_OVERRIDE_JOIN_COLUMN_TABLE_NOT_VALID);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_ASSOCIATION_OVERRIDE_JOIN_COLUMN_UNRESOLVED_NAME);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_ATTRIBUTE_OVERRIDE_COLUMN_TABLE_NOT_VALID);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_ATTRIBUTE_OVERRIDE_COLUMN_UNRESOLVED_NAME);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_ATTRIBUTE_OVERRIDE_INVALID_NAME);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_MAP_KEY_ATTRIBUTE_OVERRIDE_COLUMN_TABLE_NOT_VALID);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_MAP_KEY_ATTRIBUTE_OVERRIDE_INVALID_NAME);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_MAP_KEY_ATTRIBUTE_OVERRIDE_COLUMN_UNRESOLVED_NAME); //3.0 M7
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.VIRTUAL_MAP_KEY_ATTRIBUTE_OVERRIDE_COLUMN_UNRESOLVED_NAME); //3.0 M7
	}

	private void addInheritanceStrategyCategory(Composite parent) {

		parent = this.addExpandableSection(parent, JptJpaUiPreferencesValidationMessages.INHERITANCE_CATEGORY);

		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.DISCRIMINATOR_COLUMN_UNRESOLVED_NAME);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.ENTITY_ABSTRACT_DISCRIMINATOR_VALUE_DEFINED);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.ENTITY_ABSTRACT_TABLE_PER_CLASS_DEFINES_TABLE);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.ENTITY_NON_ROOT_DISCRIMINATOR_COLUMN_DEFINED);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.ENTITY_SINGLE_TABLE_DESCENDANT_DEFINES_TABLE);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.ENTITY_TABLE_PER_CLASS_DISCRIMINATOR_COLUMN_DEFINED);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.ENTITY_TABLE_PER_CLASS_DISCRIMINATOR_VALUE_DEFINED);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.ENTITY_TABLE_PER_CLASS_NOT_PORTABLE_ON_PLATFORM);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.ENTITY_TABLE_PER_CLASS_NOT_SUPPORTED_ON_PLATFORM);
	}

	private void addQueriesGeneratorsCategory(Composite parent) {

		parent = this.addExpandableSection(parent, JptJpaUiPreferencesValidationMessages.QUERIES_GENERATORS_CATEGORY);

		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.GENERATOR_DUPLICATE_NAME);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.GENERATOR_NAME_UNDEFINED); //3.0 M7
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.UNRESOLVED_GENERATOR_NAME);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.JPQL_QUERY_VALIDATION);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.QUERY_DUPLICATE_NAME);
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.QUERY_NAME_UNDEFINED); //3.0 M7
		this.addLabeledCombo(parent, JptJpaCoreValidationMessages.QUERY_STATEMENT_UNDEFINED); //3.0 M7
	}

	private Composite addExpandableSection(Composite parent, String text) {
		return this.addExpandableSection(parent, text, new GridData(GridData.FILL, GridData.FILL, true, false));
	}

	private Composite addSubExpandableSection(Composite parent, String text) {
		return this.addExpandableSection(parent, text, new GridData(GridData.FILL, GridData.FILL, true, false, 2, 1));
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
		expandablePane.addExpansionListener(this.buildExpansionListener());

		this.scrollable.adaptChild(expandablePane);
		this.expandablePanes.add(expandablePane);

		parent = new Composite(expandablePane, SWT.NONE);
		parent.setLayout(new GridLayout(2, false));
		expandablePane.setClient(parent);

		return parent;
	}

	private ExpansionAdapter buildExpansionListener() {
		return new ExpansionAdapter() {
			@Override
			public void expansionStateChanged(ExpansionEvent e) {
				JpaProblemSeveritiesPage.this.expansionStateChanged();
			}
		};
	}

	/**
	 * Revalidates the layout in order to show or hide the vertical scroll bar
	 * after a section was either expanded or collapsed. This unfortunately does
	 * not happen automatically.
	 */
	protected void expansionStateChanged() {
		this.scrollable.reflow(true);
	}

	/**
	 * Creates and adds to the given parent a labeled combo where the possible
	 * choices are "Ignore", "Error" and "Warning".
	 *
	 * @param parent The parent to which the widgets are added
	 * @param validationMessage The corresponding validation message
	 */
	private void addLabeledCombo(Composite parent, ValidationMessage validationMessage) {
		Label label = new Label(parent, SWT.NONE);
		label.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		label.setText(validationMessage.getDescription() + ':');

		Combo combo = new Combo(parent, SWT.READ_ONLY);
		combo.setItems(this.severityDisplayStrings);
		combo.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL));
		combo.setData(VALIDATION_MESSAGE, validationMessage);
		combo.select(this.getInitialComboIndex(validationMessage));
		combo.addSelectionListener(this.buildComboSelectionListener());

		this.scrollable.adaptChild(combo);
		this.combos.add(combo);
	}

	/**
	 * Return the combo index corresponding to the specified validation message's
	 * current preference value.
	 * <p>
	 * Only called during initialization.
	 */
	protected int getInitialComboIndex(ValidationMessage validationMessage) {
		return this.convertPreferenceValueToComboIndex(this.getPreferenceValue(validationMessage));
	}

	/**
	 * Return the current preference value for the specified validation message.
	 * <p>
	 * Only called during initialization.
	 */
	protected String getPreferenceValue(ValidationMessage validationMessage) {
		String prefKey = validationMessage.getID();
		String prefValue = null;
		// useProjectSettings() won't work since the page is being under construction
		if (this.isProjectPreferencePage() && this.hasProjectSpecificOptions(this.getProject())) {
			prefValue = JpaPreferences.getProblemSeverity(this.getProject(), prefKey);
		} else {
			prefValue = JpaPreferences.getProblemSeverity(prefKey);
		}
		if (prefValue == null) {
			prefValue = JpaPreferences.convertMessageSeverityToPreferenceValue(validationMessage.getDefaultSeverity());
		}
		return prefValue;
	}

	protected int convertPreferenceValueToComboIndex(String prefValue) {
		for (int i = 0; i < this.preferenceSeverities.length; i++) {
			if (prefValue.equals(this.preferenceSeverities[i].preferenceValue)) {
				return i;
			}
		}
		throw new IllegalArgumentException("unknown preference value: " + prefValue); //$NON-NLS-1$
	}

	private SelectionListener buildComboSelectionListener() {
		return new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				JpaProblemSeveritiesPage.this.comboSelected((Combo) e.widget);
			}
		};
	}

	protected void comboSelected(Combo combo) {
		ValidationMessage vm = (ValidationMessage) combo.getData(VALIDATION_MESSAGE);
		String prefKey = vm.getID();
		String prefValue = this.preferenceSeverities[combo.getSelectionIndex()].preferenceValue;
		this.changedSeverities.put(prefKey, prefValue);
	}

	@Override
	protected String getPreferencePageID() {
		return PREFERENCE_PAGE_ID;
	}

	@Override
	protected String getPropertyPageID() {
		return PROPERTY_PAGE_ID;
	}

	@Override
	protected boolean hasProjectSpecificOptions(IProject project) {
		return JpaPreferences.getWorkspaceValidationPreferencesOverridden(project);
	}

	/**
	 * For a project properties page, the superclass implementation calls
	 * {@link #enableProjectSpecificSettings(boolean)}, with an argument of
	 * <code>false</code>; so we need handle only the workspace preferences
	 * page case here.
	 */
	@Override
	protected void performDefaults() {
		if ( ! this.isProjectPreferencePage()) {
			this.revertWorkspaceToDefaultPreferences();
		}
		super.performDefaults();
	}

	/**
	 * This is called only when the page is a workspace preferences page.
	 */
	protected void revertWorkspaceToDefaultPreferences() {
		for (Combo combo : this.combos) {
			this.revertWorkspaceToDefaultPreference(combo);
		}
	}

	/**
	 * This is called only when the page is a workspace preferences page.
	 */
	protected void revertWorkspaceToDefaultPreference(Combo combo) {
		ValidationMessage validationMessage = (ValidationMessage) combo.getData(VALIDATION_MESSAGE);
		String prefKey = validationMessage.getID();
		String prefValue = JpaPreferences.convertMessageSeverityToPreferenceValue(validationMessage.getDefaultSeverity());
		combo.select(this.convertPreferenceValueToComboIndex(prefValue));
		// force the workspace-level preference to be removed
		this.changedSeverities.put(prefKey, null);
	}

	/**
	 * This is called only when the page is a project properties page.
	 */
	@Override
	protected void enableProjectSpecificSettings(boolean useProjectSpecificSettings) {
		super.enableProjectSpecificSettings(useProjectSpecificSettings);
		if (this.getDefaultsButton() == null) {
			//@Hack("If the defaults button is null the control is currently being built," +
			//      "otherwise the 'enable project specific settings' checkbox is being pressed")
			return;
		}

		this.hasProjectSpecificPreferences = Boolean.valueOf(useProjectSpecificSettings);

		if (useProjectSpecificSettings){
			this.copyCurrentPreferencesToProject();
		} else {
			this.revertProjectPreferences();
		}
	}

	/**
	 * Copy <em>all</em> the current settings to the project-level settings.
	 * This locks down all the settings; otherwise future changes to the
	 * workspace-level settings would affect any project-level settings
	 * that were not copied over.
	 * <p>
	 * This is called only when the page is a project properties page.
	 */
	protected void copyCurrentPreferencesToProject() {
		for (Combo combo : this.combos) {
			this.copyCurrentPreferenceToProject(combo);
		}
	}

	/**
	 * This is called only when the page is a project properties page.
	 */
	protected void copyCurrentPreferenceToProject(Combo combo) {
		ValidationMessage validationMessage = (ValidationMessage) combo.getData(VALIDATION_MESSAGE);
		String prefKey = validationMessage.getID();
		String prefValue = JpaPreferences.getProblemSeverity(prefKey);
		if (prefValue == null) {
			prefValue = JpaPreferences.convertMessageSeverityToPreferenceValue(validationMessage.getDefaultSeverity());
		}
		combo.select(this.convertPreferenceValueToComboIndex(prefValue));
		// combo does not fire a selection event when set programmatically...
		this.changedSeverities.put(prefKey, prefValue);
	}

	/**
	 * This is called only when the page is a project properties page.
	 */
	protected void revertProjectPreferences() {
		for (Combo combo : this.combos) {
			this.revertProjectPreference(combo);
		}
	}

	/**
	 * This is called only when the page is a project properties page.
	 */
	protected void revertProjectPreference(Combo combo) {
		ValidationMessage validationMessage = (ValidationMessage) combo.getData(VALIDATION_MESSAGE);
		String prefKey = validationMessage.getID();
		String prefValue = JpaPreferences.getProblemSeverity(prefKey);
		if (prefValue == null) {
			prefValue = JpaPreferences.convertMessageSeverityToPreferenceValue(validationMessage.getDefaultSeverity());
		}
		combo.select(this.convertPreferenceValueToComboIndex(prefValue));
		// force the project-level preference to be removed
		this.changedSeverities.put(prefKey, null);
	}

	@Override
	protected void noDefaultAndApplyButton() {
		throw new IllegalStateException("Don't call this, see enableProjectSpecificSettings for the hack that looks for the defaultsButton being null"); //$NON-NLS-1$
	}


	// ********** OK/Revert/Apply behavior **********

	@Override
	public boolean performOk() {
		super.performOk();
		if (this.hasProjectSpecificPreferences != null) {
			JpaPreferences.setWorkspaceValidationPreferencesOverridden(this.getProject(), this.hasProjectSpecificPreferences.booleanValue());
		}
		for (Map.Entry<String, String> entry : this.changedSeverities.entrySet()) {
			this.updatePreference(entry.getKey(), entry.getValue());
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

	protected void updatePreference(String prefKey, String value) {
		if (this.isProjectPreferencePage()) {
			JpaPreferences.setProblemSeverity(this.getProject(), prefKey, value);
		} else {
			JpaPreferences.setProblemSeverity(prefKey, value);
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
		this.storeSectionExpansionStates(this.getDialogPreferences());
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
