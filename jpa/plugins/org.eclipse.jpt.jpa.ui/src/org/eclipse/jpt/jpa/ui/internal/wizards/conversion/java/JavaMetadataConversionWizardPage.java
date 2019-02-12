/*******************************************************************************
 * Copyright (c) 2011, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.wizards.conversion.java;

import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.jpt.common.core.internal.utility.WorkspaceRunnableAdapter;
import org.eclipse.jpt.common.core.resource.ProjectResourceLocator;
import org.eclipse.jpt.common.core.resource.xml.JptXmlResource;
import org.eclipse.jpt.common.ui.internal.WorkbenchTools;
import org.eclipse.jpt.common.ui.internal.swt.bindings.SWTBindingTools;
import org.eclipse.jpt.common.ui.internal.swt.widgets.DisplayTools;
import org.eclipse.jpt.common.ui.internal.utility.SynchronousUiCommandContext;
import org.eclipse.jpt.common.utility.command.Command;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.iterator.EmptyIterator;
import org.eclipse.jpt.common.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.common.utility.model.event.PropertyChangeEvent;
import org.eclipse.jpt.common.utility.model.listener.PropertyChangeAdapter;
import org.eclipse.jpt.common.utility.model.listener.PropertyChangeListener;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.core.JpaProjectManager;
import org.eclipse.jpt.jpa.core.JpaStructureNode;
import org.eclipse.jpt.jpa.core.context.MappingFile;
import org.eclipse.jpt.jpa.core.context.orm.EntityMappings;
import org.eclipse.jpt.jpa.core.context.persistence.MappingFileRef;
import org.eclipse.jpt.jpa.core.context.persistence.Persistence;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceXml;
import org.eclipse.jpt.jpa.core.resource.ResourceMappingFile;
import org.eclipse.jpt.jpa.ui.JptJpaUiMessages;
import org.eclipse.jpt.jpa.ui.internal.jface.XmlMappingFileViewerFilter;
import org.eclipse.jpt.jpa.ui.internal.plugin.JptJpaUiPlugin;
import org.eclipse.jpt.jpa.ui.internal.wizards.SelectMappingFileDialog;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.ide.IDE;

public abstract class JavaMetadataConversionWizardPage
	extends WizardPage
{
	/**
	 * The JPA project can<em>not</em> be <code>null</code>.
	 */
	protected final JpaProject jpaProject;

	/**
	 * The persistence unit can be <code>null</code>.
	 */
	protected final PersistenceUnit persistenceUnit;
	protected final boolean noConvertibleJavaMetadata;

	private final SimplePropertyValueModel<String> mappingFileNameModel = new SimplePropertyValueModel<String>();

	private static final String PAGE_NAME = "JavaMetadataConversion"; //$NON-NLS-1$
	public static final String HELP_CONTEXT_ID = JptJpaUiPlugin.instance().getPluginID() + '.' + PAGE_NAME;


	protected JavaMetadataConversionWizardPage(JpaProject jpaProject, String title, String description) {
		super(PAGE_NAME);
		if (jpaProject == null) {
			throw new NullPointerException();
		}
		this.jpaProject = jpaProject;
		this.persistenceUnit = this.buildPersistenceUnit();
		this.mappingFileNameModel.setValue(this.buildDefaultMappingFileName());
		this.setTitle(title);
		this.setDescription(description);
		this.noConvertibleJavaMetadata = this.hasNoConvertibleJavaMetadata();
	}

	/**
	 * Return the JPA project's <em>first</em> persistence unit.
	 */
	protected PersistenceUnit buildPersistenceUnit() {
		PersistenceXml persistenceXml = this.jpaProject.getContextRoot().getPersistenceXml();
		if (persistenceXml == null) {
			return null;
		}
		Persistence persistence = persistenceXml.getRoot();
		if (persistence == null) {
			return null;
		}
		Iterator<PersistenceUnit> stream = persistence.getPersistenceUnits().iterator();
		return stream.hasNext() ? stream.next() : null;
	}

	protected String buildDefaultMappingFileName() {
		IPath path = this.buildDefaultMappingFilePath();
		return (path == null) ? null : path.toString();
	}

	protected IPath buildDefaultMappingFilePath() {
		IPath path = this.getFirstMappingFilePath();
		return (path == null) ? this.getDefaultMappingFileRuntimPath() : this.convertToRuntimPath(path);
	}

	protected abstract IPath getDefaultMappingFileRuntimPath();
	
	/**
	 * Return the JPA project's <em>first</em> mapping file with the
	 * {@link #getMappingFileContentType() default content type}.
	 */
	protected IPath getFirstMappingFilePath() {
		if (this.persistenceUnit == null) {
			return null;
		}
		for (MappingFileRef ref : this.persistenceUnit.getMappingFileRefs()) {
			MappingFile mappingFile = ref.getMappingFile();
			if (mappingFile != null) {
				IResource file = mappingFile.getResource();
				if (file instanceof IFile) {
					if (this.jpaProject.getJpaFile((IFile) file).getContentType().isKindOf(this.getMappingFileContentType())) {
						return file.getFullPath();
					}
				}
			}
		}
		return null;
	}

	protected IContentType getMappingFileContentType() {
		return ResourceMappingFile.Root.CONTENT_TYPE;
	}

	protected IPath convertToRuntimPath(IPath path) {
		ProjectResourceLocator locator = (ProjectResourceLocator) this.jpaProject.getProject().getAdapter(ProjectResourceLocator.class);
		return locator.getRuntimePath(path);
	}


	// ********** missing metadata warning message **********

	protected final boolean hasNoConvertibleJavaMetadata() {
		return ! this.hasConvertibleJavaMetadata();
	}

	protected final boolean hasConvertibleJavaMetadata() {
		return (this.persistenceUnit != null) && this.hasConvertibleJavaMetadata_();
	}

	/**
	 * Pre-condition: the {@link #persistenceUnit persistence unit}
	 * is not <code>null</code>.
	 */
	protected abstract boolean hasConvertibleJavaMetadata_();

	protected abstract String getMissingJavaMetadataWarningMessage();


	// ********** UI control **********

	public void createControl(Composite parent) {
		Composite composite = new Composite(parent, SWT.NULL);
		composite.setLayout(new GridLayout());

		Label pageLabel = new Label(composite, SWT.NONE);
		pageLabel.setText(JptJpaUiMessages.JAVA_METADATA_CONVERSION_WIZARD_PAGE_LABEL);
		pageLabel.setBounds(3, 230, 150, 12);

		Control mappingFileControl = this.createMappingFileControl(composite);
		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		data.heightHint = 58;
		data.grabExcessHorizontalSpace = true;
		mappingFileControl.setLayoutData(data);

		Text noteTextField = new Text(composite, SWT.READ_ONLY | SWT.MULTI | SWT.WRAP);
		noteTextField.setText(JptJpaUiMessages.JAVA_METADATA_CONVERSION_WARNING);
		noteTextField.setFont(new Font(composite.getDisplay(), "Arial", 10, SWT.EMBEDDED)); //$NON-NLS-1$
		noteTextField.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true, 1, 1));

		this.setControl(composite);

		// no need to remove this listener, since we build the model ourselves
		this.mappingFileNameModel.addPropertyChangeListener(PropertyValueModel.VALUE, this.buildMappingFileNameListener());
		this.setPageComplete(this.getRootStructureNodes(this.getOrmXmlResource()).hasNext());
	}

	protected Control createMappingFileControl(Composite parent) {
		Composite composite = new Composite(parent, SWT.NULL);
		composite.setLayout(new GridLayout(3, false));

		Link link = new Link(composite, SWT.LEFT);
		link.setText(JptJpaUiMessages.JAVA_METADATA_CONVERSION_WIZARD_PAGE_NEW_MAPPING_FILE_LINK);
		link.setToolTipText(JptJpaUiMessages.JAVA_METADATA_CONVERSION_WIZARD_PAGE_NEW_MAPPING_FILE_LINK_TOOL_TIP);
		link.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING));
		link.addSelectionListener(this.buildMappingFileLinkListener());

		Text nameTextField = new Text(composite, SWT.SEARCH | SWT.SINGLE);
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.grabExcessVerticalSpace = true;
		gridData.widthHint = 486;
		gridData.heightHint = 14;
		gridData.horizontalSpan = 1;
		gridData.grabExcessHorizontalSpace = true;
		nameTextField.setLayoutData(gridData);
		SWTBindingTools.bind(this.mappingFileNameModel, nameTextField);

		Button browseButton = new Button(composite, SWT.CENTER);
		browseButton.setToolTipText(JptJpaUiMessages.JAVA_METADATA_CONVERSION_WIZARD_PAGE_MAPPING_FILE_BROWSE_BUTTON_TOOL_TIP);
		browseButton.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		browseButton.setText(JptJpaUiMessages.JAVA_METADATA_CONVERSION_WIZARD_PAGE_MAPPING_FILE_BROWSE_BUTTON);
		browseButton.addSelectionListener(this.buildMappingFileBrowseButtonListener());

		return composite;
	}


	// ********** mapping file name listener **********

	protected PropertyChangeListener buildMappingFileNameListener() {
		return new MappingFileNameListener();
	}

	protected class MappingFileNameListener
		extends PropertyChangeAdapter
	{
		@Override
		public void propertyChanged(PropertyChangeEvent event) {
			JavaMetadataConversionWizardPage.this.validate();
		}
	}

	protected void validate() {
		String errorMessage = this.buildErrorMessage();
		this.setErrorMessage(errorMessage);
		this.setPageComplete(errorMessage == null);
	}

	protected String buildErrorMessage() {
		JptXmlResource ormXmlResource = this.getOrmXmlResource();
		if (ormXmlResource == null) {
			return JptJpaUiMessages.JAVA_METADATA_CONVERSION_MAPPING_FILE_DOES_NOT_EXIST;
		}
		String mappingFileVersion = ormXmlResource.getVersion();
		String jpaProjectVersion = this.getJpaProjectVersion();
		if (ObjectTools.notEquals(mappingFileVersion, jpaProjectVersion)) {
			return NLS.bind(JptJpaUiMessages.JAVA_METADATA_CONVERSION_MAPPING_FILE_VERSION_IS_INVALID, mappingFileVersion, jpaProjectVersion);
		}
		if (this.getEntityMappings(ormXmlResource) == null) {
			return JptJpaUiMessages.JAVA_METADATA_CONVERSION_MAPPING_FILE_NOT_LISTED_IN_PERSISTENCE_XML;
		}
		if (this.noConvertibleJavaMetadata) {
			this.setMessage(this.getMissingJavaMetadataWarningMessage(), IMessageProvider.WARNING);
		}
		return null;
	}

	protected EntityMappings getEntityMappings() {
		return this.getEntityMappings(this.getOrmXmlResource());
	}

	protected EntityMappings getEntityMappings(JptXmlResource ormXmlResource) {
		return (ormXmlResource == null) ? null : this.getEntityMappings_(ormXmlResource);
	}

	/**
	 * Pre-condition: the specified <code>orm.xml</code> resource is
	 * not <code>null</code>.
	 */
	protected EntityMappings getEntityMappings_(JptXmlResource ormXmlResource) {
		Iterator<JpaStructureNode> nodes = this.getRootStructureNodes(ormXmlResource);
		return nodes.hasNext() ? (EntityMappings) nodes.next() : null;
	}

	protected JptXmlResource getOrmXmlResource() {
		IPath mappingFilePath = this.getMappingFilePath();
		return (mappingFilePath == null) ? null : this.jpaProject.getMappingFileXmlResource(mappingFilePath);
	}

	protected IPath getMappingFilePath() {
		String mappingFileName = this.getMappingFileName();
		return StringTools.isBlank(mappingFileName) ? null : new Path(mappingFileName);
	}

	protected String getMappingFileName() {
		String mappingFileName = this.mappingFileNameModel.getValue();
		return StringTools.isBlank(mappingFileName) ? null : mappingFileName;
	}

	protected String getJpaProjectVersion(){
		return this.jpaProject.getJpaPlatform().getJpaVersion().getVersion();
	}


	// ********** mapping file link listener **********

	protected SelectionListener buildMappingFileLinkListener() {
		return new MappingFileLinkListener();
	}

	protected class MappingFileLinkListener
		extends SelectionAdapter
	{
		@Override
		public void widgetSelected(SelectionEvent event) {
			JavaMetadataConversionWizardPage.this.mappingFileLinkPressed();
		}
	}

	protected void mappingFileLinkPressed() {
		IPath path = this.openNewMappingFileWizard();
		if (path != null) {
			this.mappingFileNameModel.setValue(path.toString());
			// revalidate because the mapping file may have been created
			this.validate();
		}
	}

	protected abstract IPath openNewMappingFileWizard();


	// ********** mapping file browse button listener **********

	protected SelectionListener buildMappingFileBrowseButtonListener() {
		return new MappingFileBrowseButtonListener();
	}

	protected class MappingFileBrowseButtonListener
		extends SelectionAdapter
	{
		@Override
		public void widgetSelected(SelectionEvent event) {
			JavaMetadataConversionWizardPage.this.mappingFileBrowseButtonPressed();
		}
	}

	protected void mappingFileBrowseButtonPressed() {
		IProject project = this.jpaProject.getProject();
		SelectMappingFileDialog dialog = this.buildSelectMappingFileDialog();
		dialog.setTitle(JptJpaUiMessages.SELECT_MAPPING_FILE_DIALOG_TITLE);
		dialog.setMessage(JptJpaUiMessages.SELECT_MAPPING_FILE_DIALOG_MESSAGE);
		dialog.addFilter(this.buildSelectMappingFileDialogViewerFilter());
		dialog.setInput(project);

		JptXmlResource resource = this.getOrmXmlResource();
		IFile file = (resource == null) ? null : resource.getFile();
		if (file != null) {
			dialog.setInitialSelection(file);
		}
		if (dialog.open() == Window.OK) {
			this.mappingFileNameModel.setValue(dialog.getChosenName());
			// revalidate because the mapping file may have been created
			this.validate();
		}
	}

	protected abstract SelectMappingFileDialog buildSelectMappingFileDialog();

	protected ViewerFilter buildSelectMappingFileDialogViewerFilter() {
		return new XmlMappingFileViewerFilter(this.jpaProject, this.getMappingFileContentType());
	}


	// ********** finish => conversion **********

	protected boolean performFinish() {
		return this.isOKToConvert() && this.performFinish_();
	}

	protected boolean isOKToConvert() {
		return this.hasConvertibleJavaMetadata();
	}

	protected boolean performFinish_() {
		// true=fork; true=cancellable
		try {
			this.buildProgressMonitorDialog().run(true, true, this.buildConversionRunnable());
		} catch (InvocationTargetException ex) {
			JptJpaUiPlugin.instance().logError(ex);
		} catch (InterruptedException ex) {
			Thread.currentThread().interrupt();  // runnable cancelled
		}
		return true;
	}

	protected ProgressMonitorDialog buildProgressMonitorDialog() {
		return new ProgressMonitorDialog(this.getShell());
	}

	protected IRunnableWithProgress buildConversionRunnable() {
		return new ConversionRunnable(this.getConversionCommandStrategy(), this.getEntityMappings());
	}

	protected abstract ConversionCommand.Strategy getConversionCommandStrategy();

	/**
	 * UI runnable
	 */
	protected static class ConversionRunnable
		implements IRunnableWithProgress
	{
		protected final ConversionCommand.Strategy conversionCommandStrategy;
		protected final EntityMappings entityMappings;

		protected ConversionRunnable(ConversionCommand.Strategy conversionCommandStrategy, EntityMappings entityMappings) {
			super();
			if ((conversionCommandStrategy == null) || (entityMappings == null)) {
				throw new NullPointerException();
			}
			this.conversionCommandStrategy = conversionCommandStrategy;
			this.entityMappings = entityMappings;
		}

		public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
			IWorkspace ws = ResourcesPlugin.getWorkspace();
			ISchedulingRule rule = ws.getRuleFactory().modifyRule(ws.getRoot());
			try {
				ws.run(this.buildWorkspaceRunnable(), rule, IWorkspace.AVOID_UPDATE, monitor);
			} catch (CoreException ex) {
				if (ex.getStatus().getSeverity() == IStatus.CANCEL) {
					throw new InterruptedException();  // ???
				}
				throw new InvocationTargetException(ex);
			}
		}

		protected void run_(IProgressMonitor monitor) {
			JpaProjectManager jpaProjectManager = this.getJpaProjectManager();
			if (jpaProjectManager == null) {
				throw new OperationCanceledException("Missing JPA project manager");
			}
			Command conversionCommand = new ConversionCommand(this.conversionCommandStrategy, this.entityMappings, monitor);
			try {
				jpaProjectManager.execute(conversionCommand, SynchronousUiCommandContext.instance());
			} catch (InterruptedException ex) {
				Thread.currentThread().interrupt();
				throw new OperationCanceledException();
			}
		}

		protected IWorkspaceRunnable buildWorkspaceRunnable() {
			return new WorkspaceRunnable();
		}

		protected JpaProjectManager getJpaProjectManager() {
			return (JpaProjectManager) this.getWorkspace().getAdapter(JpaProjectManager.class);
		}

		protected IWorkspace getWorkspace() {
			return this.entityMappings.getParent().getXmlResource().getFile().getWorkspace();
		}

		@Override
		public String toString() {
			return ObjectTools.toString(this);
		}

		/**
		 * Workspace runnable
		 */
		protected class WorkspaceRunnable
			extends WorkspaceRunnableAdapter
		{
			@Override
			public void run(IProgressMonitor monitor) throws CoreException {
				ConversionRunnable.this.run_(monitor);
			}
		}
	}


	/**
	 * Conversion command.
	 * This is dispatched to the JPA project manager.
	 */
	protected static class ConversionCommand
		implements Command
	{
		protected final Strategy strategy;
		protected final EntityMappings entityMappings;
		protected final IProgressMonitor monitor;

		protected ConversionCommand(Strategy strategy, EntityMappings entityMappings, IProgressMonitor monitor) {
			super();
			if ((strategy == null) || (entityMappings == null) || (monitor == null)) {
				throw new NullPointerException();
			}
			this.strategy = strategy;
			this.entityMappings = entityMappings;
			this.monitor = monitor;
		}

		public void execute() {
			this.strategy.execute(this.entityMappings, this.monitor);
			this.getOrmXmlResource().save();
			this.saveAllFiles();
			this.openEditorOnMappingFile();
		}

		protected JptXmlResource getOrmXmlResource() {
			return this.entityMappings.getParent().getXmlResource();
		}

		protected void saveAllFiles() {
			DisplayTools.asyncExec(this.buildSaveAllFilesRunnable());
		}

		protected Runnable buildSaveAllFilesRunnable() {
			return new SaveFilesRunnable(this.getProject());
		}

		protected IProject getProject() {
			return this.entityMappings.getJpaProject().getProject();
		}

		protected void openEditorOnMappingFile() {
			DisplayTools.asyncExec(this.buildOpenEditorOnMappingFileRunnable());
		}

		protected Runnable buildOpenEditorOnMappingFileRunnable() {
			return new OpenEditorRunnable(this.getOrmXmlResource().getFile());
		}

		@Override
		public String toString() {
			return ObjectTools.toString(this, this.entityMappings.getPersistenceUnit());
		}

		public interface Strategy {
			void execute(EntityMappings entityMappings, IProgressMonitor monitor);
		}
	}


	// ********** save files **********

	protected static class SaveFilesRunnable
		implements Runnable
	{
		protected final IResource[] resourceRoots;

		protected SaveFilesRunnable(IProject project) {
			super();
			if (project == null) {
				throw new NullPointerException();
			}
			this.resourceRoots = new IResource[] { project };
		}

		public void run() {
			IDE.saveAllEditors(this.resourceRoots, false);  // false=no confirm
		}

		@Override
		public String toString() {
			return ObjectTools.toString(this, this.resourceRoots[0]);
		}
	}


	// ********** open editor **********

	protected static class OpenEditorRunnable
		implements Runnable
	{
		protected final IFile file;

		protected OpenEditorRunnable(IFile file) {
			super();
			if (file == null) {
				throw new NullPointerException();
			}
			this.file = file;
		}

		public void run() {
			IWorkbenchPage page = WorkbenchTools.getActivePage();
			try {
				if (page != null) {
					IDE.openEditor(page, this.file);
				}
			} catch (PartInitException ex) {
				JptJpaUiPlugin.instance().logError(ex);
			}
		}

		@Override
		public String toString() {
			return ObjectTools.toString(this, this.file);
		}
	}


	// ********** misc **********

	/**
	 * Return the root structure nodes of the given XML resource
	 */
	protected Iterator<JpaStructureNode> getRootStructureNodes(JptXmlResource ormXmlResource) {
		if (ormXmlResource == null) {
			return EmptyIterator.instance();
		}
		return this.jpaProject.getJpaFile(ormXmlResource.getFile()).getRootStructureNodes().iterator();
	}

	@Override
	public final void performHelp() {
		WorkbenchTools.displayHelp(HELP_CONTEXT_ID);
	}
}
