package org.eclipse.jpt.ui.internal.properties;

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
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableContext;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.utility.internal.ArrayTools;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.model.value.BufferedWritablePropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.utility.model.Model;
import org.eclipse.jpt.utility.model.listener.ChangeListener;
import org.eclipse.jpt.utility.model.listener.SimpleChangeListener;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;
import org.eclipse.jst.common.project.facet.core.libprov.IPropertyChangeListener;
import org.eclipse.jst.common.project.facet.core.libprov.LibraryInstallDelegate;
import org.eclipse.jst.common.project.facet.ui.libprov.LibraryFacetPropertyPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
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
import org.eclipse.wst.common.project.facet.core.IFacetedProject;
import org.eclipse.wst.common.project.facet.core.IProjectFacetVersion;
import org.eclipse.wst.common.project.facet.ui.internal.FacetsPropertyPage;


public abstract class JptProjectPropertiesPage
		extends LibraryFacetPropertyPage {
	
	protected final WritablePropertyValueModel<IProject> projectModel;
	protected final BufferedWritablePropertyValueModel.Trigger trigger;
	
	protected final ChangeListener validationListener;
	
	
	public JptProjectPropertiesPage() {
		super();

		this.projectModel = new SimplePropertyValueModel<IProject>();
		this.trigger = new BufferedWritablePropertyValueModel.Trigger();
		
		buildModels();
		
		this.validationListener = this.buildValidationListener();
	}
	
	
	/**
	 * Build any additional models needed by this page.  The project model has been created at this
	 * point.
	 */
	protected abstract void buildModels();
	
	
	// ********** convenience methods **********
	
	protected static boolean flagIsSet(PropertyValueModel<Boolean> flagModel) {
		Boolean flag = flagModel.getValue();
		return (flag != null) && flag.booleanValue();
	}
	
	
	// ********** LibraryFacetPropertyPage implementation **********
	
	@Override
	protected LibraryInstallDelegate createLibraryInstallDelegate(IFacetedProject project, IProjectFacetVersion fv) {
		LibraryInstallDelegate lid = new LibraryInstallDelegate(project, fv, null);
		lid.addListener(buildLibraryProviderListener());
		return lid;
	}
	
	protected IPropertyChangeListener buildLibraryProviderListener() {
		return new IPropertyChangeListener() {
				public void propertyChanged(String property, Object oldValue, Object newValue ) {
					if (LibraryInstallDelegate.PROP_AVAILABLE_PROVIDERS.equals(property)) {
						adjustLibraryProviders();
					}
				}
			};
	}
	
	protected abstract void adjustLibraryProviders();
	
	
	// ********** page **********

	@Override
	protected Control createPageContents(Composite parent) {
		if (this.projectModel.getValue() != null) {
			disengageListeners();
		}
		
		this.projectModel.setValue(getProject());
		
		Composite composite = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		composite.setLayout(layout);
		
		createWidgets(composite);
		
		Dialog.applyDialogFont(composite);
		
		adjustLibraryProviders();
		
		engageListeners();
		updateValidation();

		return composite;
	}
	
	/**
	 * Build specific widgets.  Layout and validation will be taken care of.
	 */
	protected abstract void createWidgets(Composite parent);
	
	protected void engageListeners() {
		engageValidationListener();
	}
	
	protected void disengageListeners() {
		disengageValidationListener();
	}
	
	protected Link buildFacetsPageLink(Composite parent, String text) {
		Link facetsPageLink = buildLink(parent, text);
		facetsPageLink.addSelectionListener(buildFacetsPageLinkListener());  // the link will be GCed
		return facetsPageLink;
	}
	
	private SelectionListener buildFacetsPageLinkListener() {
		return new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				openProjectFacetsPage();
			}
			@Override
			public String toString() {
				return "facets page link listener"; //$NON-NLS-1$
			}
		};
	}
	
	protected void openProjectFacetsPage() {
		((IWorkbenchPreferenceContainer)getContainer()).openPage(FacetsPropertyPage.ID, null);
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
		return buildButton(parent, horizontalSpan, text, SWT.CHECK);
	}
	
	protected Button buildRadioButton(Composite parent, int horizontalSpan, String text) {
		return buildButton(parent, horizontalSpan, text, SWT.RADIO);
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
		return buildDropDown(parent, 1);
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
			return false;
		} 
		catch (InvocationTargetException ex) {
			throw new RuntimeException(ex.getTargetException());
		}

		return true;
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
							buildOkWorkspaceRunnable(),
							ws.getRoot(),
							IWorkspace.AVOID_UPDATE,
							monitor);
				}
				catch (CoreException ex) {
					throw new InvocationTargetException(ex);
				}
			}
		};
	}
	
	/* private */ IWorkspaceRunnable buildOkWorkspaceRunnable() {
		return new IWorkspaceRunnable() {
			public void run(IProgressMonitor monitor) throws CoreException {
				performOk_(monitor);
			}
		};
	}
	
	// ********** OK/Revert/Apply behavior **********
	
	void performOk_(IProgressMonitor monitor) throws CoreException {
		if (isBuffering()) {
			boolean rebuild = projectRebuildRequired();
			this.trigger.accept();
			if (rebuild) {
				rebuildProject();
			}
			this.getProject().build(IncrementalProjectBuilder.FULL_BUILD, monitor);
		}
	}
	
	protected abstract boolean projectRebuildRequired();
	
	protected abstract void rebuildProject();
	
	/**
	 * Return whether any of the models are buffering a change.
	 */
	private boolean isBuffering() {
		for (BufferedWritablePropertyValueModel<?> model : buildBufferedModels()) {
			if (model.isBuffering()) {
				return true;
			}
		}
		return false;
	}
	
	protected abstract BufferedWritablePropertyValueModel<?>[] buildBufferedModels();
	
	@Override
	protected void performDefaults() {
		super.performDefaults();
		this.trigger.reset();
	}
	
	
	// ********** dispose **********
	
	@Override
	public void dispose() {
		disengageListeners();
		super.dispose();
	}
	
	
	// ********** validation **********
	
	private ChangeListener buildValidationListener() {
		return new SimpleChangeListener() {
			@Override
			protected void modelChanged() {
				validate();
			}
			@Override
			public String toString() {
				return "validation listener"; //$NON-NLS-1$
			}
		};
	}
	
	protected void validate() {
		if ( ! getControl().isDisposed()) {
			updateValidation();
		}
	}

	private void engageValidationListener() {
		for (Model model : buildValidationModels()) {
			model.addChangeListener(this.validationListener);
		}
	}
	
	protected abstract Model[] buildValidationModels();
	
	private void disengageValidationListener() {
		for (Model model : buildReverseValidationModels()) {
			model.removeChangeListener(this.validationListener);
		}
	}
	
	protected Model[] buildReverseValidationModels() {
		return ArrayTools.reverse(buildValidationModels());
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
		return new Status(severity, JptCorePlugin.PLUGIN_ID, message);
	}
	
	@Override
	protected IStatus performValidation() {
		HashMap<Integer, ArrayList<IStatus>> statuses = new HashMap<Integer, ArrayList<IStatus>>();
		statuses.put(ERROR_STATUS, new ArrayList<IStatus>());
		statuses.put(WARNING_STATUS, new ArrayList<IStatus>());
		statuses.put(INFO_STATUS, new ArrayList<IStatus>());
		statuses.put(OK_STATUS, CollectionTools.list(Status.OK_STATUS));
		
		performValidation(statuses);
		
		if ( ! statuses.get(ERROR_STATUS).isEmpty()) {
			return statuses.get(ERROR_STATUS).get(0);
		}
		else if ( ! statuses.get(WARNING_STATUS).isEmpty()) {
			return statuses.get(WARNING_STATUS).get(0);
		}
		else if ( ! statuses.get(INFO_STATUS).isEmpty()) {
			return statuses.get(INFO_STATUS).get(0);
		}
		else {
			return statuses.get(OK_STATUS).get(0);
		}
	}
	
	protected void performValidation(Map<Integer, ArrayList<IStatus>> statuses) {
		/* library provider */
		IStatus lpStatus = super.performValidation();
		statuses.get(Integer.valueOf(lpStatus.getSeverity())).add(lpStatus);
	}
}
