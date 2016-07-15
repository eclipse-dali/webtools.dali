/*******************************************************************************
 * Copyright (c) 2011, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.ui.internal.properties;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableContext;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jpt.common.ui.internal.plugin.JptCommonUiPlugin;
import org.eclipse.jpt.common.utility.internal.ArrayTools;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.model.value.BufferedPropertyValueModelAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyValueModelTools;
import org.eclipse.jpt.common.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.common.utility.model.Model;
import org.eclipse.jpt.common.utility.model.listener.AbstractChangeListener;
import org.eclipse.jpt.common.utility.model.listener.ChangeListener;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jst.common.project.facet.core.libprov.IPropertyChangeListener;
import org.eclipse.jst.common.project.facet.core.libprov.LibraryInstallDelegate;
import org.eclipse.jst.common.project.facet.ui.libprov.LibraryFacetPropertyPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.ui.preferences.IWorkbenchPreferenceContainer;
import org.eclipse.wst.common.project.facet.ui.internal.FacetsPropertyPage;


public abstract class JptProjectPropertiesPage
	extends LibraryFacetPropertyPage
{
	/**
	 * It's not clear what this page's lifecycle is (e.g. when {@link
	 * #createPageContents(Composite)} is called and how often it can be called);
	 * so we must maintain this flag.
	 */
	protected volatile boolean engaged = false;

	protected final ModifiablePropertyValueModel<IProject> projectModel;
	protected final BufferedPropertyValueModelAdapter.Trigger trigger;

	protected final ChangeListener validationListener;


	public JptProjectPropertiesPage() {
		super();

		this.projectModel = new SimplePropertyValueModel<>();
		this.trigger = PropertyValueModelTools.bufferedPropertyValueModelAdapterTrigger();

		this.buildModels();

		this.validationListener = this.buildValidationListener();
	}


	/**
	 * Build any additional models needed by this page.  The project model has been created at this
	 * point.
	 */
	protected abstract void buildModels();


	// ********** convenience methods **********

	public static boolean flagIsSet(PropertyValueModel<Boolean> flagModel) {
		Boolean flag = flagModel.getValue();
		return (flag != null) && flag.booleanValue();
	}


	// ********** LibraryFacetPropertyPage implementation **********

	protected IPropertyChangeListener buildLibraryProviderListener() {
		return new LibraryProviderListener();
	}

	protected class LibraryProviderListener
		implements IPropertyChangeListener
	{
		public void propertyChanged(String property, Object oldValue, Object newValue ) {
			if (LibraryInstallDelegate.PROP_AVAILABLE_PROVIDERS.equals(property)) {
				JptProjectPropertiesPage.this.adjustLibraryProviders();
			}
		}
		@Override
		public String toString() {
			return ObjectTools.toString(this);
		}
	}

	protected abstract void adjustLibraryProviders();


	// ********** page **********

	@Override
	protected Control createPageContents(Composite parent) {
		this.disengageListeners(); // not sure why we do this here...

		this.projectModel.setValue(this.getProject());

		Composite composite = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		composite.setLayout(layout);

		this.createWidgets(composite);

		Dialog.applyDialogFont(composite);

		this.adjustLibraryProviders();

		this.engageListeners();
		this.updateValidation();

		return composite;
	}

	/**
	 * Build specific widgets.  Layout and validation will be taken care of.
	 */
	protected abstract void createWidgets(Composite parent);

	protected final void engageListeners() {
		if ( ! this.engaged) {
			this.engageListeners_();
			this.engaged = true;
		}
	}

	protected void engageListeners_() {
		this.engageValidationListener();
	}

	protected final void disengageListeners() {
		if (this.engaged) {
			this.disengageListeners_();
			this.engaged = false;
		}
	}

	protected void disengageListeners_() {
		this.disengageValidationListener();
	}

	protected Link buildFacetsPageLink(Composite parent, String text) {
		Link facetsPageLink = this.buildLink(parent, text);
		facetsPageLink.addSelectionListener(new FacetsPageLinkListener());  // the link will be GCed
		return facetsPageLink;
	}

	/* CU private */ class FacetsPageLinkListener
		extends SelectionAdapter
	{
		@Override
		public void widgetSelected(SelectionEvent e) {
			JptProjectPropertiesPage.this.openProjectFacetsPage();
		}
		@Override
		public String toString() {
			return ObjectTools.toString(this);
		}
	}

	protected void openProjectFacetsPage() {
		((IWorkbenchPreferenceContainer)this.getContainer()).openPage(FacetsPropertyPage.ID, null);
	}

	/**
	 * Don't allow {@link org.eclipse.jface.preference.PreferencePage#computeSize()}
	 * to cache the page's size, since the size of the "Library" panel can
	 * change depending on the user's selection from the drop-down list.
	 */
	@Override
	public Point computeSize() {
		return this.doComputeSize();
	}


	// ********** widgets **********

	protected Button buildCheckBox(Composite parent, int horizontalSpan, String text) {
		return this.buildButton(parent, horizontalSpan, text, SWT.CHECK);
	}

	protected Button buildRadioButton(Composite parent, int horizontalSpan, String text) {
		return this.buildButton(parent, horizontalSpan, text, SWT.RADIO);
	}

	protected Button buildButton(Composite parent, int horizontalSpan, String text, int style) {
		Button button = new Button(parent, SWT.NONE | style);
		button.setText(text);
		GridData gd = new GridData();
		gd.horizontalSpan = horizontalSpan;
		button.setLayoutData(gd);
		return button;
	}

	protected Combo buildDropDown(Composite parent) {
		return this.buildDropDown(parent, 1);
	}

	protected Combo buildDropDown(Composite parent, int horizontalSpan) {
		Combo combo = new Combo(parent, SWT.READ_ONLY);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = horizontalSpan;
		combo.setLayoutData(gd);
		return combo;
	}

	protected Label buildLabel(Composite parent, String text) {
		Label label = new Label(parent, SWT.LEFT);
		label.setText(text);
		GridData gd = new GridData();
		gd.horizontalSpan = 1;
		label.setLayoutData(gd);
		return label;
	}

	protected Link buildLink(Composite parent, String text) {
		Link link = new Link(parent, SWT.NONE);
		GridData data = new GridData(GridData.END, GridData.CENTER, false, false);
		data.horizontalSpan = 2;
		link.setLayoutData(data);
		link.setText(text);
		return link;
	}


	// ********** OK/Revert/Apply behavior **********

	@Override
	public boolean performOk() {
		super.performOk();

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

	protected IRunnableContext buildOkProgressMonitorDialog() {
		return new ProgressMonitorDialog(this.getShell());
	}

	protected IRunnableWithProgress buildOkRunnableWithProgress() {
		return new OkRunnableWithProgress(this.getProject().getWorkspace());
	}

	protected class OkRunnableWithProgress
		implements IRunnableWithProgress
	{
		protected final IWorkspace workspace;
		protected OkRunnableWithProgress(IWorkspace workspace) {
			super();
			this.workspace = workspace;
		}
		public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
			try {
				// the build we execute in #performOk_() locks the workspace root,
				// so we need to use the workspace root as our scheduling rule here
				this.workspace.run(
						new OkWorkspaceRunnable(),
						this.workspace.getRoot(),
						IWorkspace.AVOID_UPDATE,
						monitor
					);
			} catch (CoreException ex) {
				throw new InvocationTargetException(ex);
			}
		}

		@Override
		public String toString() {
			return ObjectTools.toString(this);
		}

		/* class private */ class OkWorkspaceRunnable
			implements IWorkspaceRunnable
		{
			public void run(IProgressMonitor monitor) throws CoreException {
				JptProjectPropertiesPage.this.performOk_(monitor);
			}
			@Override
			public String toString() {
				return ObjectTools.toString(this);
			}
		}
	}


	// ********** OK/Revert/Apply behavior **********

	/* CU private */ void performOk_(IProgressMonitor monitor) throws CoreException {
		if (this.isBuffering()) {
			boolean rebuild = this.projectRebuildRequired();
			this.trigger.accept();
			if (rebuild) {
				this.rebuildProject();
			}
			this.getProject().build(IncrementalProjectBuilder.FULL_BUILD, monitor);
		}
	}

	protected abstract boolean projectRebuildRequired();

	protected abstract void rebuildProject() throws CoreException;

	/**
	 * Return whether any of the models are buffering a change.
	 */
	private boolean isBuffering() {
		for (PropertyValueModel<Boolean> flag : this.buildBufferingFlags()) {
			if (flag.getValue().booleanValue()) {
				return true;
			}
		}
		return false;
	}

	protected abstract PropertyValueModel<Boolean>[] buildBufferingFlags();

	@Override
	protected void performDefaults() {
		super.performDefaults();
		this.trigger.reset();
	}


	// ********** dispose **********

	@Override
	public void dispose() {
		this.disengageListeners();
		super.dispose();
	}


	// ********** validation **********

	private ChangeListener buildValidationListener() {
		return new ValidationListener();
	}

	/* CU private */ class ValidationListener
		extends AbstractChangeListener
	{
		@Override
		protected void modelChanged() {
			JptProjectPropertiesPage.this.validate();
		}
	}

	protected void validate() {
		if ( ! this.getControl().isDisposed()) {
			this.updateValidation();
		}
	}

	private void engageValidationListener() {
		for (Model model : this.buildValidationModels()) {
			model.addChangeListener(this.validationListener);
		}
	}

	protected abstract Model[] buildValidationModels();

	private void disengageValidationListener() {
		for (Model model : this.buildReverseValidationModels()) {
			model.removeChangeListener(this.validationListener);
		}
	}

	protected Model[] buildReverseValidationModels() {
		return ArrayTools.reverse(this.buildValidationModels());
	}

	protected static final Integer ERROR_STATUS = Integer.valueOf(IStatus.ERROR);
	protected static final Integer WARNING_STATUS = Integer.valueOf(IStatus.WARNING);
	protected static final Integer INFO_STATUS = Integer.valueOf(IStatus.INFO);
	protected static final Integer OK_STATUS = Integer.valueOf(IStatus.OK);

	protected IStatus buildInfoStatus(String message) {
		return this.buildStatus(IStatus.INFO, message);
	}

	protected IStatus buildWarningStatus(String message) {
		return this.buildStatus(IStatus.WARNING, message);
	}

	protected IStatus buildErrorStatus(String message) {
		return this.buildStatus(IStatus.ERROR, message);
	}

	protected IStatus buildStatus(int severity, String message) {
		return JptCommonUiPlugin.instance().buildStatus(severity, message);
	}

	@Override
	protected IStatus performValidation() {
		HashMap<Integer, ArrayList<IStatus>> statuses = new HashMap<>();
		statuses.put(ERROR_STATUS, new ArrayList<IStatus>());
		statuses.put(WARNING_STATUS, new ArrayList<IStatus>());
		statuses.put(INFO_STATUS, new ArrayList<IStatus>());
		statuses.put(OK_STATUS, new ArrayList<IStatus>());

		/* library provider */
		this.addStatus(super.performValidation(), statuses);
		this.performValidation(statuses);

		ArrayList<IStatus> list = statuses.get(ERROR_STATUS);
		if ( ! list.isEmpty()) {
			return list.get(0);
		}
		list = statuses.get(WARNING_STATUS);
		if ( ! list.isEmpty()) {
			return list.get(0);
		}
		list = statuses.get(INFO_STATUS);
		if ( ! list.isEmpty()) {
			return list.get(0);
		}
		return Status.OK_STATUS;
	}

	protected abstract void performValidation(Map<Integer, ArrayList<IStatus>> statuses);

	protected void addStatus(IStatus status, Map<Integer, ArrayList<IStatus>> statuses) {
		statuses.get(Integer.valueOf(status.getSeverity())).add(status);
	}
}
