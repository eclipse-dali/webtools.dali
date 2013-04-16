/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.ui.internal.prefs;

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
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jpt.common.core.internal.utility.WorkspaceRunnableAdapter;
import org.eclipse.jpt.common.core.utility.ValidationMessage;
import org.eclipse.jpt.common.ui.JptCommonUiMessages;
import org.eclipse.jpt.common.ui.internal.JptUIPlugin;
import org.eclipse.jpt.common.ui.internal.jface.RunnableWithProgressAdapter;
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
import org.eclipse.ui.forms.events.ExpansionAdapter;
import org.eclipse.ui.forms.events.ExpansionEvent;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

/**
 * Abstract problem severities page that supports
 * workspace- and project-level severities.
 */
@SuppressWarnings("restriction")
public abstract class JptProblemSeveritiesPage
	extends PropertyAndPreferencePage
{
	/**
	 * Changed severities are stored in this map and either committed
	 * (e.g. when the user presses the 'OK' button) or discarded
	 * (e.g. when the user presses the 'Cancel' button).<ul>
	 * <li> key = preference key (which is the
	 * {@link ValidationMessage#getID() validation message ID})
	 * <li> value = preference severity level (which is the
	 *   {@link IMessage#getSeverity() validation message severity}):<ul>
	 *   <li>{@link IMessage#HIGH_SEVERITY}
	 *   <li>{@link IMessage#NORMAL_SEVERITY}
	 *   <li>{@link IMessage#LOW_SEVERITY}
	 *   <li>{@link ValidationMessage#IGNORE_SEVERITY}
	 *   </ul>
	 * </ul>
	 */
	private final HashMap<String, Integer> changedSeverities = new HashMap<String, Integer>();

	/**
	 * Cache the {@link Combo}s so we can revert the settings.
	 */
	private final ArrayList<Combo> combos = new ArrayList<Combo>();

	/**
	 * Cache the {@link ExpandableComposite}s so we can save
	 * and restore the page's expansion state.
	 */
	private final ArrayList<ExpandableComposite> expandablePanes = new ArrayList<ExpandableComposite>();

	/**
	 * The key used to store and retrieve a combo's validation message.
	 * @see org.eclipse.swt.widgets.Widget#getData(String)
	 */
	/* CU private */ static final String VALIDATION_MESSAGE = ValidationMessage.class.getName();

	/**
	 * The scrollable pane used to show the content of this page.
	 */
	private ScrolledPageContent mainComposite;

	/**
	 * The possible choices which describes the severity of a single problem.
	 */
	private final PreferenceSeverity[] preferenceSeverities;
	private final String[] severityDisplayStrings;

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


	public JptProblemSeveritiesPage() {
		super();
		this.preferenceSeverities = this.buildPreferenceSeverities();
		this.severityDisplayStrings = this.buildSeverityDisplayStrings();
	}

	@Override
	protected IPreferenceStore doGetPreferenceStore() {
		return this.getUIPlugin().getPreferenceStore();
	}

	protected abstract JptUIPlugin getUIPlugin();

	protected PreferenceSeverity[] buildPreferenceSeverities() {
		return DEFAULT_PREFERENCE_SEVERITIES;
	}

	protected static final PreferenceSeverity[] DEFAULT_PREFERENCE_SEVERITIES = buildDefaultPreferenceSeverities();
	protected static PreferenceSeverity[] buildDefaultPreferenceSeverities() {
		ArrayList<PreferenceSeverity> severities = new ArrayList<PreferenceSeverity>();
		severities.add(new PreferenceSeverity(IMessage.HIGH_SEVERITY, JptCommonUiMessages.PROBLEM_SEVERITIES_PAGE__ERROR));
		severities.add(new PreferenceSeverity(IMessage.NORMAL_SEVERITY, JptCommonUiMessages.PROBLEM_SEVERITIES_PAGE__WARNING));
		severities.add(new PreferenceSeverity(IMessage.LOW_SEVERITY, JptCommonUiMessages.PROBLEM_SEVERITIES_PAGE__INFO));
		severities.add(new PreferenceSeverity(ValidationMessage.IGNORE_SEVERITY, JptCommonUiMessages.PROBLEM_SEVERITIES_PAGE__IGNORE));
		return severities.toArray(new PreferenceSeverity[severities.size()]);
	}

	/**
	 * Pair a preference value with its localized display string
	 * (e.g. <code>"error"</code> and <code>"Error"</code>).
	 */
	public static class PreferenceSeverity {
		public final int preferenceValue;
		public final String displayString;
		public PreferenceSeverity(int preferenceValue, String displayString) {
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
		// create a container because the caller will set the GridData and we need
		// to change the heightHint of the first child and we also need to set the
		// font otherwise the layout won't be calculated correctly
		Composite container = new Composite(parent, SWT.NONE);
		container.setFont(parent.getFont());
		GridLayout layout = new GridLayout(1, false);
		layout.marginHeight = 0;
		layout.marginWidth  = 0;
		container.setLayout(layout);

		// the page's main composite
		this.mainComposite = new ScrolledPageContent(container);

		GridData gridData = new GridData(GridData.FILL, GridData.FILL, true, true);
		gridData.heightHint = pixelConverter.convertHeightInCharsToPixels(20);
		this.mainComposite.setLayoutData(gridData);

		parent = this.mainComposite.getBody();
		parent.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, true));
		layout = new GridLayout(1, false);
		layout.marginHeight = 0;
		layout.marginWidth  = 0;
		parent.setLayout(layout);

		this.addCombos(parent);

		this.restoreSectionExpansionStates();

		return container;
	}

	protected abstract void addCombos(Composite parent);

	protected void restoreSectionExpansionStates() {
		IDialogSettings settings = this.getDialogPreferences();
		for (int index = this.expandablePanes.size(); index-- > 0; ) {
			ExpandableComposite expandablePane = this.expandablePanes.get(index);
			if (settings == null) {
				expandablePane.setExpanded(index == 0);  // only expand the first node by default
			} else {
				expandablePane.setExpanded(settings.getBoolean(SETTINGS_EXPANDED + index));
			}
		}
	}

	@Override
	public Point computeSize() {
		return this.doComputeSize();
	}

	protected Composite addExpandableSection(Composite parent, String text) {
		return this.addExpandableSection(parent, text, new GridData(GridData.FILL, GridData.FILL, true, false));
	}

	protected Composite addSubExpandableSection(Composite parent, String text) {
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
		int expansionStype = ExpandableComposite.TWISTIE | ExpandableComposite.CLIENT_INDENT;
		ExpandableComposite expandablePane = new ExpandableComposite(parent, SWT.NONE, expansionStype);

		expandablePane.setText(text);
		expandablePane.setFont(JFaceResources.getFontRegistry().getBold(JFaceResources.DIALOG_FONT));
		expandablePane.setLayoutData(gridData);
		expandablePane.addExpansionListener(this.buildExpansionListener());

		this.mainComposite.adaptChild(expandablePane);
		this.expandablePanes.add(expandablePane);

		parent = new Composite(expandablePane, SWT.NONE);
		parent.setLayout(new GridLayout(2, false));
		expandablePane.setClient(parent);

		return parent;
	}

	private ExpansionAdapter buildExpansionListener() {
		return new ExpansionListener();
	}

	/* CU private */ class ExpansionListener
		extends ExpansionAdapter
	{
		@Override
		public void expansionStateChanged(ExpansionEvent e) {
			JptProblemSeveritiesPage.this.expansionStateChanged();
		}
	}

	/**
	 * Revalidates the layout in order to show or hide the vertical scroll bar
	 * after a section was either expanded or collapsed. This unfortunately does
	 * not happen automatically.
	 */
	protected void expansionStateChanged() {
		this.mainComposite.reflow(true);
	}

	/**
	 * Creates and adds to the given parent a labeled combo where the possible
	 * choices are "Ignore", "Error" and "Warning".
	 *
	 * @param parent The parent to which the widgets are added
	 * @param validationMessage The corresponding validation message
	 */
	protected void addLabeledCombo(Composite parent, ValidationMessage validationMessage) {
		Label label = new Label(parent, SWT.NONE);
		label.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		label.setText(validationMessage.getDescription() + ':');

		Combo combo = new Combo(parent, SWT.READ_ONLY);
		combo.setItems(this.severityDisplayStrings);
		combo.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL));
		combo.setData(VALIDATION_MESSAGE, validationMessage);
		combo.select(this.getInitialComboIndex(validationMessage));
		combo.addSelectionListener(this.buildComboSelectionListener());

		this.mainComposite.adaptChild(combo);
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
	protected int getPreferenceValue(ValidationMessage validationMessage) {
		int prefValue = this.getPreferenceValue_(validationMessage);
		return (prefValue != ValidationMessage.UNSET_SEVERITY_PREFERENCE) ? prefValue : validationMessage.getDefaultSeverity();
	}

	protected int getPreferenceValue_(ValidationMessage validationMessage) {
		String prefKey = validationMessage.getID();
		// useProjectSettings() won't work here since the page is still being initialized
		return (this.isProjectPreferencePage() && this.hasProjectSpecificOptions(this.getProject())) ?
				this.getValidationMessageSeverityPreference(this.getProject(), prefKey) :
				this.getValidationMessageSeverityPreference(prefKey);
	}

	protected int convertPreferenceValueToComboIndex(int prefValue) {
		for (int i = 0; i < this.preferenceSeverities.length; i++) {
			if (prefValue == this.preferenceSeverities[i].preferenceValue) {
				return i;
			}
		}
		throw new IllegalArgumentException("unknown preference value: " + prefValue); //$NON-NLS-1$
	}

	private SelectionListener buildComboSelectionListener() {
		return new ComboSelectionListener();
	}

	/* CU private */ class ComboSelectionListener
		extends SelectionAdapter
	{
		@Override
		public void widgetSelected(SelectionEvent e) {
			JptProblemSeveritiesPage.this.comboSelected((Combo) e.widget);
		}
	}

	protected void comboSelected(Combo combo) {
		ValidationMessage msg = (ValidationMessage) combo.getData(VALIDATION_MESSAGE);
		String prefKey = msg.getID();
		int prefValue = this.preferenceSeverities[combo.getSelectionIndex()].preferenceValue;
		this.changedSeverities.put(prefKey, Integer.valueOf(prefValue));
	}

	@Override
	protected boolean hasProjectSpecificOptions(IProject project) {
		return this.getWorkspaceValidationPreferencesOverridden(project);
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
		int prefValue = validationMessage.getDefaultSeverity();
		combo.select(this.convertPreferenceValueToComboIndex(prefValue));
		// force the workspace-level preference to be removed
		this.changedSeverities.put(prefKey, Integer.valueOf(ValidationMessage.UNSET_SEVERITY_PREFERENCE));
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
		int prefValue = this.getValidationMessageSeverityPreference(prefKey);
		if (prefValue == ValidationMessage.UNSET_SEVERITY_PREFERENCE) {
			prefValue = validationMessage.getDefaultSeverity();
		}
		combo.select(this.convertPreferenceValueToComboIndex(prefValue));
		// combo does not fire a selection event when set programmatically...
		this.changedSeverities.put(prefKey, Integer.valueOf(prefValue));
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
		int prefValue = this.getValidationMessageSeverityPreference(prefKey);
		if (prefValue == ValidationMessage.UNSET_SEVERITY_PREFERENCE) {
			prefValue = validationMessage.getDefaultSeverity();
		}
		combo.select(this.convertPreferenceValueToComboIndex(prefValue));
		// force the project-level preference to be removed
		this.changedSeverities.put(prefKey, Integer.valueOf(ValidationMessage.UNSET_SEVERITY_PREFERENCE));
	}

	@Override
	protected void noDefaultAndApplyButton() {
		throw new IllegalStateException("Don't call this, see enableProjectSpecificSettings for the hack that looks for the defaultsButton being null"); //$NON-NLS-1$
	}


	// ********** plug-in preferences **********

	protected abstract int getValidationMessageSeverityPreference(IProject project, String prefKey);

	protected abstract int getValidationMessageSeverityPreference(String prefKey);

	protected abstract boolean getWorkspaceValidationPreferencesOverridden(IProject project);

	protected abstract void setWorkspaceValidationPreferencesOverridden(IProject project, boolean value);

	protected abstract void setValidationMessageSeverityPreference(String prefKey, int value);

	protected abstract void setValidationMessageSeverityPreference(IProject project, String prefKey, int value);


	// ********** OK/Revert/Apply behavior **********

	@Override
	public boolean performOk() {
		super.performOk();
		if (this.hasProjectSpecificPreferences != null) {
			this.setWorkspaceValidationPreferencesOverridden(this.getProject(), this.hasProjectSpecificPreferences.booleanValue());
		}
		for (Map.Entry<String, Integer> entry : this.changedSeverities.entrySet()) {
			this.setProblemSeverityPreference(entry.getKey(), entry.getValue().intValue());
		}
		try {
			// true=fork; false=uncancellable
			this.buildOKDialog().run(true, false, this.buildOKRunnable());
		} catch (InterruptedException ex) {
			// should *not* happen...
			Thread.currentThread().interrupt();
			return false;
		} catch (InvocationTargetException ex) {
			throw new RuntimeException(ex.getTargetException());
		}

		return true;
	}

	protected void setProblemSeverityPreference(String prefKey, int value) {
		if (this.isProjectPreferencePage()) {
			this.setValidationMessageSeverityPreference(this.getProject(), prefKey, value);
		} else {
			this.setValidationMessageSeverityPreference(prefKey, value);
		}
	}

	private IRunnableContext buildOKDialog() {
		return new ProgressMonitorDialog(this.getShell());
	}

	private IRunnableWithProgress buildOKRunnable() {
		return new OKRunnable();
	}

	/* CU private */ class OKRunnable
		extends RunnableWithProgressAdapter
	{
		@Override
		public void run(IProgressMonitor monitor) throws InvocationTargetException {
			try {
				this.run_(monitor);
			} catch (CoreException ex) {
				throw new InvocationTargetException(ex);
			}
		}
		/**
		 * {@link #performOk_(IProgressMonitor)} triggers a build that locks the
		 * workspace root, so we need to use the workspace root as our
		 * scheduling rule here
		 */
		private void run_(IProgressMonitor monitor) throws CoreException {
			IWorkspace ws = ResourcesPlugin.getWorkspace();
			ws.run(
					JptProblemSeveritiesPage.this.buildOkWorkspaceRunnable(),
					ws.getRoot(),
					IWorkspace.AVOID_UPDATE,
					monitor
				);
		}
	}

	IWorkspaceRunnable buildOkWorkspaceRunnable() {
		return new OKWorkspaceRunnable();
	}

	/* CU private */ class OKWorkspaceRunnable
		extends WorkspaceRunnableAdapter
	{
		@Override
		public void run(IProgressMonitor monitor) throws CoreException {
			JptProblemSeveritiesPage.this.performOk_(monitor);
		}
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
		return this.getUIPlugin().getDialogSettings(SETTINGS_SECTION_NAME);
	}

	protected void storeSectionExpansionStates(IDialogSettings settings) {
		for (int index = this.expandablePanes.size(); index-- > 0; ) {
			ExpandableComposite expandablePane = this.expandablePanes.get(index);
			settings.put(SETTINGS_EXPANDED + index, expandablePane.isExpanded());
		}
	}

}
