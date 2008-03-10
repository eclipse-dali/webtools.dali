/*******************************************************************************
 *  Copyright (c) 2008 Oracle. All rights reserved. This
 *  program and the accompanying materials are made available under the terms of
 *  the Eclipse Public License v1.0 which accompanies this distribution, and is
 *  available at http://www.eclipse.org/legal/epl-v10.html
 *
 *  Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.ui.internal.editors;

import java.util.ListIterator;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jpt.core.JpaProject;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.core.context.JpaRootContextNode;
import org.eclipse.jpt.core.context.persistence.Persistence;
import org.eclipse.jpt.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.core.context.persistence.PersistenceXml;
import org.eclipse.jpt.ui.JpaPlatformUi;
import org.eclipse.jpt.ui.JpaUiFactory;
import org.eclipse.jpt.ui.details.JpaPageComposite;
import org.eclipse.jpt.ui.internal.persistence.JptUiPersistenceMessages;
import org.eclipse.jpt.ui.internal.platform.JpaPlatformUiRegistry;
import org.eclipse.jpt.ui.internal.util.SWTUtil;
import org.eclipse.jpt.ui.internal.widgets.FormWidgetFactory;
import org.eclipse.jpt.ui.internal.widgets.WidgetFactory;
import org.eclipse.jpt.utility.internal.model.value.ListAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.ListPropertyValueModelAdapter;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.utility.model.value.ListValueModel;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.editors.text.TextEditor;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;
import org.eclipse.wst.sse.ui.StructuredTextEditor;

/**
 * This is the editor for the JPA Persistence Configuration (persistence.xml).
 * The pages shown before the XML source editor are retrieved from the
 * <code>JpaUiFactory</code>.
 *
 * @see JpaUiFactory
 *
 * @version 2.0
 * @since 2.0
 */
@SuppressWarnings("nls")
public class PersistenceEditor extends FormEditor
{
	/**
	 * The XML text editor.
	 */
	private TextEditor editor;

	/**
	 * The root of the holders used to retrieve the persistence unit and be
	 * notified when it changes.
	 */
	private WritablePropertyValueModel<IFileEditorInput> editorInputHolder;

	/**
	 * The listener used for being notified when the project is being closed.
	 */
	private IResourceChangeListener resourceChangeListener;

	/**
	 * The factory used to create the various widgets.
	 */
	private WidgetFactory widgetFactory;

	/**
	 * Creates a new <code>PersistenceEditor</code>.
	 */
	public PersistenceEditor() {
		super();
		initialize();
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void addPages() {
		addPersistenceUnitPages();
		addXMLEditorPage();
	}

	/**
	 * Adds the pages that show the properties of the persistence configuration
	 * or its persistence units.
	 */
	private void addPersistenceUnitPages() {

		JpaProject jpaProject = jpaProject();
		String platformId = jpaProject.jpaPlatform().getId();
		JpaPlatformUi jpaPlatformUI = JpaPlatformUiRegistry.instance().jpaPlatform(platformId);
		JpaUiFactory uiFactory = jpaPlatformUI.getJpaUiFactory();

		ListIterator<JpaPageComposite<PersistenceUnit>> pages = uiFactory.createPersistenceUnitComposites(
			buildPersistenceUnitHolder(),
			getContainer(),
			widgetFactory
		);

		while (pages.hasNext()) {
			JpaPageComposite<PersistenceUnit> page = pages.next();

			try {
				FormPage formPage = new Page(page);
				addPage(formPage);
			}
			catch (PartInitException e) {
				// TODO
			}
		}
	}

	/**
	 * Adds the page containing the XML editor.
	 */
	private void addXMLEditorPage() {
		try {
			editor = new StructuredTextEditor();
			int index = addPage(editor, getEditorInput());
			setPageText(index, editor.getTitle());
		}
		catch (PartInitException e) {
			// TODO
		}
	}

	private WritablePropertyValueModel<IFileEditorInput> buildEditorInputHolder() {
		return new SimplePropertyValueModel<IFileEditorInput>();
	}

	private PropertyValueModel<JpaProject> buildJpaProjectHolder() {
		return new TransformationPropertyValueModel<IFileEditorInput, JpaProject>(editorInputHolder) {
			@Override
			protected JpaProject transform_(IFileEditorInput value) {
				return JptCorePlugin.jpaProject(value.getFile().getProject());
			}
		};
	}

	private PropertyValueModel<Persistence> buildPersistenceHolder() {
		return new PropertyAspectAdapter<PersistenceXml, Persistence>(buildPersistenceXmlHolder(), PersistenceXml.PERSISTENCE_PROPERTY) {
			@Override
			protected Persistence buildValue_() {
				return subject.getPersistence();
			}
		};
	}

	private PropertyValueModel<PersistenceUnit> buildPersistenceUnitHolder() {
		return new ListPropertyValueModelAdapter<PersistenceUnit>(buildPersistenceUnitListHolder()) {
			@Override
			protected PersistenceUnit buildValue() {
				return listHolder.size() > 0 ? (PersistenceUnit) listHolder.get(0) : null;
			}
		};
	}

	private ListValueModel<PersistenceUnit> buildPersistenceUnitListHolder() {
		return new ListAspectAdapter<Persistence, PersistenceUnit>(buildPersistenceHolder(), Persistence.PERSISTENCE_UNITS_LIST) {
			@Override
			protected ListIterator<PersistenceUnit> listIterator_() {
				return subject.persistenceUnits();
			}

			@Override
			protected int size_() {
				return subject.persistenceUnitsSize();
			}
		};
	}

	private PropertyValueModel<PersistenceXml> buildPersistenceXmlHolder() {
		return new PropertyAspectAdapter<JpaRootContextNode, PersistenceXml>(buildRootContextNodeHolder(), JpaRootContextNode.PERSISTENCE_XML_PROPERTY) {
			@Override
			protected PersistenceXml buildValue_() {
				return subject.persistenceXml();
			}
		};
	}

	private IResourceChangeListener buildResourceChangeListener() {
		return new IResourceChangeListener() {
			public void resourceChanged(final IResourceChangeEvent event) {

				if (event.getType() == IResourceChangeEvent.PRE_CLOSE) {

					SWTUtil.asyncExec(new Runnable() { public void run() {

						IWorkbenchPage[] pages = getSite().getWorkbenchWindow().getPages();

						for (int index = 0; index<pages.length; index++){

							FileEditorInput fileEditorInput = (FileEditorInput) editor.getEditorInput();

							if (fileEditorInput.getFile().getProject().equals(event.getResource())) {
								IEditorPart editorPart = pages[index].findEditor(editor.getEditorInput());
								pages[index].closeEditor(editorPart, true);
							}
						}
					}});
				}
			}
		};
	}

	private PropertyValueModel<JpaRootContextNode> buildRootContextNodeHolder() {
		return new TransformationPropertyValueModel<JpaProject, JpaRootContextNode>(buildJpaProjectHolder()) {
			@Override
			protected JpaRootContextNode transform_(JpaProject value) {
				return value.rootContext();
			}
		};
	}

	private WidgetFactory buildWidgetFactory() {
		return new FormWidgetFactory(
			new TabbedPropertySheetWidgetFactory()
		);
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	public void close(boolean save) {
		super.close(save);
		editorInputHolder.setValue(null);
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	public void dispose() {

		ResourcesPlugin.getWorkspace().removeResourceChangeListener(resourceChangeListener);
		resourceChangeListener = null;

		super.dispose();
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	public void doSave(IProgressMonitor monitor) {
		getEditor(getPageCount() - 1).doSave(monitor);
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	public void doSaveAs() {
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	public IFileEditorInput getEditorInput() {
		return (IFileEditorInput) super.getEditorInput();
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	public void init(IEditorSite site, IEditorInput editorInput) throws PartInitException {
		Assert.isLegal(editorInput instanceof IFileEditorInput, "Invalid Input: Must be IFileEditorInput");
		super.init(site, editorInput);

		editorInputHolder.setValue(getEditorInput());
	}

	/**
	 * Initializes this multi-page editor.
	 */
	private void initialize() {

		editorInputHolder      = buildEditorInputHolder();
		widgetFactory          = buildWidgetFactory();
		resourceChangeListener = buildResourceChangeListener();

		ResourcesPlugin.getWorkspace().addResourceChangeListener(resourceChangeListener);
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}

	/**
	 * Retrieves the JPA project associated with the project owning the editor
	 * intput file.
	 *
	 * @return The JPA project
	 */
	protected JpaProject jpaProject() {
		return JptCorePlugin.jpaProject(getEditorInput().getFile().getProject());
	}

	/**
	 * This extension over <code>FormPage</code> simply complete the layout by
	 * using the <code>JpaPageComposite</code>'s control as its form content.
	 */
	private class Page extends FormPage {

		private JpaPageComposite<PersistenceUnit> page;

		private Page(JpaPageComposite<PersistenceUnit> page) {

			super(PersistenceEditor.this,
			      page.getClass().getName(),
			      page.pageText());

			this.page = page;
		}

		@Override
		protected void createFormContent(IManagedForm managedForm) {

			ScrolledForm form = managedForm.getForm();
			managedForm.getToolkit().decorateFormHeading(form.getForm());

			// Update the text and image
			updateForm(form);

			// Add the page's control to this page
			Composite body = form.getBody();
			body.setLayout(new GridLayout(1, false));
			updateGridData(body);
			page.getControl().setParent(body);

			// This will finish the initialization of the buttons
			updateHelpButton();
			form.updateToolBar();

			// Populate the page
			page.populate();
		}

		/*
		 * (non-Javadoc)
		 */
		@Override
		public void dispose() {
			page.dispose();
			super.dispose();
		}

		private void updateForm(ScrolledForm form) {

			form.setText(page.pageText());

			Image image = page.pageImage();

			if (image != null) {
				form.setImage(image);
			}
		}

		private void updateGridData(Composite container) {

			GridData gridData = new GridData();
			gridData.grabExcessHorizontalSpace = true;
			gridData.grabExcessVerticalSpace   = true;
			gridData.horizontalAlignment       = SWT.FILL;
			gridData.verticalAlignment         = SWT.FILL;
			container.setLayoutData(gridData);
		}

		private void updateHelpButton() {

			String helpID = page.helpID();

			if (helpID != null) {
				Action helpAction = new HelpAction(helpID);

				ScrolledForm form = getManagedForm().getForm();
				IToolBarManager manager = form.getToolBarManager();
				manager.add(helpAction);
			}
		}

		private class HelpAction extends Action {

			private final String helpID;

			HelpAction(String helpID) {
				super(JptUiPersistenceMessages.PersistenceEditor_Page_help,
				      JFaceResources.getImageRegistry().getDescriptor(Dialog.DLG_IMG_HELP));

				this.helpID = helpID;
			}

			@Override
			public void run() {
				BusyIndicator.showWhile(getManagedForm().getForm().getDisplay(), new Runnable() {
					public void run() {
						PlatformUI.getWorkbench().getHelpSystem().displayHelpResource(helpID);
					}
				});
			}
		}
	}
}
