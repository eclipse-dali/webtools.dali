/*******************************************************************************
 * Copyright (c) 2008, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.editors;

import java.util.ListIterator;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.resource.LocalResourceManager;
import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jpt.common.core.JptResourceType;
import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.ui.internal.widgets.FormWidgetFactory;
import org.eclipse.jpt.common.utility.internal.iterables.ListIterable;
import org.eclipse.jpt.common.utility.internal.model.value.CachingTransformationPropertyValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.ListAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.ListPropertyValueModelAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.ListValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.WritablePropertyValueModel;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.core.JptJpaCorePlugin;
import org.eclipse.jpt.jpa.core.context.JpaRootContextNode;
import org.eclipse.jpt.jpa.core.context.persistence.Persistence;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceXml;
import org.eclipse.jpt.jpa.ui.JpaPlatformUi;
import org.eclipse.jpt.jpa.ui.PersistenceXmlResourceUiDefinition;
import org.eclipse.jpt.jpa.ui.details.JpaPageComposite;
import org.eclipse.jpt.jpa.ui.internal.persistence.JptUiPersistenceMessages;
import org.eclipse.jpt.jpa.ui.internal.platform.JpaPlatformUiRegistry;
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.TableWrapData;
import org.eclipse.ui.forms.widgets.TableWrapLayout;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;
import org.eclipse.wst.sse.ui.StructuredTextEditor;

/**
 * This is the editor for the JPA Persistence Configuration (persistence.xml).
 * The pages shown before the XML source editor are retrieved from the
 * <code>JpaUiFactory</code>.
 *
 * @see JpaUiFactory
 *
 * @version 2.3
 * @since 2.0
 */
@SuppressWarnings("nls")
public class PersistenceEditor extends FormEditor
{
	/**
	 * The XML text editor.
	 */
	private StructuredTextEditor editor;

	/**
	 * The root of the holders used to retrieve the persistence unit and be
	 * notified when it changes.
	 */
	private WritablePropertyValueModel<IFileEditorInput> editorInputHolder;

	/**
	 * The factory used to create the various widgets.
	 */
	private WidgetFactory widgetFactory;

	private final ResourceManager resourceManager;

	/**
	 * Creates a new <code>PersistenceEditor</code>.
	 */
	public PersistenceEditor() {
		super();
		this.resourceManager = new LocalResourceManager(JFaceResources.getResources());
		initialize();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public Object getAdapter(Class adapterClass) {
		Object adapter = super.getAdapter(adapterClass);
		if (adapter == null) {
			adapter = editor.getAdapter(adapterClass);
		}
		return adapter;
	}

	/**
	 * {@inheritDoc}
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

		JpaProject jpaProject = getJpaProject();

		// The project doesn't have JPA
		if (jpaProject == null) {
			return;
		}

		PersistenceXml persistenceXml = jpaProject.getRootContextNode().getPersistenceXml();
		if (persistenceXml == null) {
			return;
		}
		JptResourceType resourceType = persistenceXml.getResourceType();
		if (resourceType == null) {
			return;  // might not ever get here... (if we have a p.xml, it probably has a resource type...)
		}
		String platformId = jpaProject.getJpaPlatform().getId();
		JpaPlatformUi jpaPlatformUI = JpaPlatformUiRegistry.instance().getJpaPlatformUi(platformId);
		PersistenceXmlResourceUiDefinition definition = 
			(PersistenceXmlResourceUiDefinition) jpaPlatformUI.getResourceUiDefinition(resourceType);

		ListIterator<JpaPageComposite> pages = definition.buildPersistenceUnitComposites(
			buildPersistenceUnitHolder(),
			getContainer(),
			this.widgetFactory
		);

		while (pages.hasNext()) {
			JpaPageComposite page = pages.next();

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
			editor.setEditorPart(this);
			int index = addPage(editor, getEditorInput());
			setPageText(index, JptUiPersistenceMessages.PersistenceEditor_sourceTab);
		}
		catch (PartInitException e) {
			// TODO
		}
	}

	private WritablePropertyValueModel<IFileEditorInput> buildEditorInputHolder() {
		return new SimplePropertyValueModel<IFileEditorInput>();
	}

	private PropertyValueModel<JpaProject> buildJpaProjectHolder() {
		return new CachingTransformationPropertyValueModel<IFileEditorInput, JpaProject>(this.editorInputHolder) {
			@Override
			protected JpaProject transform_(IFileEditorInput fileEditorInput) {
				return JptJpaCorePlugin.getJpaProject(fileEditorInput.getFile().getProject());
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
			protected ListIterable<PersistenceUnit> getListIterable() {
				return subject.getPersistenceUnits();
			}

			@Override
			protected int size_() {
				return subject.getPersistenceUnitsSize();
			}
		};
	}

	private PropertyValueModel<PersistenceXml> buildPersistenceXmlHolder() {
		return new PropertyAspectAdapter<JpaRootContextNode, PersistenceXml>(buildRootContextNodeHolder(), JpaRootContextNode.PERSISTENCE_XML_PROPERTY) {
			@Override
			protected PersistenceXml buildValue_() {
				return subject.getPersistenceXml();
			}
		};
	}

	private PropertyValueModel<JpaRootContextNode> buildRootContextNodeHolder() {
		return new TransformationPropertyValueModel<JpaProject, JpaRootContextNode>(buildJpaProjectHolder()) {
			@Override
			protected JpaRootContextNode transform_(JpaProject value) {
				return value.getRootContextNode();
			}
		};
	}

	private WidgetFactory buildWidgetFactory() {
		return new FormWidgetFactory(
			new TabbedPropertySheetWidgetFactory()
		);
	}

	@Override
	public void dispose() {
		this.editorInputHolder.setValue(null);
		this.resourceManager.dispose();
		super.dispose();
	}

	@Override
	public void doSave(IProgressMonitor monitor) {
		getEditor(getPageCount() - 1).doSave(monitor);
	}

	@Override
	public void doSaveAs() {
	}

	@Override
	public IFileEditorInput getEditorInput() {
		return (IFileEditorInput) super.getEditorInput();
	}

	@Override
	public void init(IEditorSite site, IEditorInput editorInput) throws PartInitException {
		Assert.isLegal(editorInput instanceof IFileEditorInput, "Invalid Input: Must be IFileEditorInput");
		super.init(site, editorInput);

		setPartName(editorInput.getName());
		editorInputHolder.setValue(getEditorInput());
	}

	/**
	 * Initializes this multi-page editor.
	 */
	private void initialize() {

		widgetFactory      = buildWidgetFactory();
		editorInputHolder  = buildEditorInputHolder();
	}

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
	protected JpaProject getJpaProject() {
		return JptJpaCorePlugin.getJpaProject(getEditorInput().getFile().getProject());
	}

	/**
	 * This extension over <code>FormPage</code> simply complete the layout by
	 * using the <code>JpaPageComposite</code>'s control as its form content.
	 */
	private class Page extends FormPage {

		/**
		 * The wrapped page that actually contains the widgets to show with this
		 * form page.
		 */
		private final JpaPageComposite page;

		private ImageDescriptor imageDescriptor;

		/**
		 * Creates a new <code>Page</code>.
		 *
		 * @param page The wrapped <code>JpaPageComposite</code>
		 */
		private Page(JpaPageComposite page) {

			super(PersistenceEditor.this,
			      page.getClass().getName(),
			      page.getPageText());

			this.page = page;
		}

		@Override
		protected void createFormContent(IManagedForm managedForm) {

			ScrolledForm form = managedForm.getForm();
			managedForm.getToolkit().decorateFormHeading(form.getForm());

			// Update the text and image
			updateForm(form);

			// Update the layout
			updateBody(form);

			// This will finish the initialization of the buttons
			updateHelpButton();
			form.updateToolBar();
		}

		@Override
		public void dispose() {
			this.page.dispose();
			if (this.imageDescriptor != null) {
				PersistenceEditor.this.resourceManager.destroyImage(this.imageDescriptor);
			}
			super.dispose();
		}

		@Override
		public void setFocus() {
			this.page.getControl().setFocus();
		}

		/**
		 * Adds the page's control to this page.
		 *
		 * @param form The form containing the composite with which the page's
		 * control is parented
		 */
		private void updateBody(ScrolledForm form) {

			Composite body = form.getBody();

			body.setLayout(new TableWrapLayout());

			TableWrapData wrapData = new TableWrapData(
				TableWrapData.FILL_GRAB,
				TableWrapData.FILL_GRAB
			);

			this.page.getControl().setLayoutData(wrapData);
			this.page.getControl().setParent(body);
		}

		/**
		 * Updates the text and image of the form.
		 *
		 * @param form The form to have its title bar updated by setting the text
		 * and image, the image can be <code>null</code>
		 */
		private void updateForm(ScrolledForm form) {
			form.setText(this.page.getPageText());

			this.imageDescriptor = this.page.getPageImageDescriptor();
			if (this.imageDescriptor != null) {
				form.setImage(PersistenceEditor.this.resourceManager.createImage(this.imageDescriptor));
			}
		}

		/**
		 * Adds a help button to the page's toolbar if a help ID exists.
		 */
		private void updateHelpButton() {

			String helpID = this.page.getHelpID();

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
				super(JptUiPersistenceMessages.PersistenceEditor_page_help,
				      JFaceResources.getImageRegistry().getDescriptor(Dialog.DLG_IMG_HELP));

				this.helpID = helpID;
			}

			@Override
			public void run() {
				BusyIndicator.showWhile(getManagedForm().getForm().getDisplay(), new Runnable() {
					public void run() {
						PlatformUI.getWorkbench().getHelpSystem().displayHelp(helpID);
					}
				});
			}
		}
	}
}