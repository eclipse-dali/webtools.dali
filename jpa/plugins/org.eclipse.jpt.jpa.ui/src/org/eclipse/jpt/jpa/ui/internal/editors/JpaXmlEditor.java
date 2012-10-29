/*******************************************************************************
 * Copyright (c) 2008, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.editors;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.resource.LocalResourceManager;
import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jpt.common.core.JptResourceType;
import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.ui.internal.util.SWTUtil;
import org.eclipse.jpt.common.ui.internal.widgets.FormWidgetFactory;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.StringBuilderTools;
import org.eclipse.jpt.common.utility.internal.model.value.CollectionAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.CollectionPropertyValueModelAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.DoublePropertyValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.common.utility.internal.transformer.AbstractTransformer;
import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.common.utility.model.event.PropertyChangeEvent;
import org.eclipse.jpt.common.utility.model.listener.PropertyChangeAdapter;
import org.eclipse.jpt.common.utility.model.listener.PropertyChangeListener;
import org.eclipse.jpt.common.utility.model.value.CollectionValueModel;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.transformer.Transformer;
import org.eclipse.jpt.jpa.core.JpaFile;
import org.eclipse.jpt.jpa.core.JpaPlatform;
import org.eclipse.jpt.jpa.core.JpaStructureNode;
import org.eclipse.jpt.jpa.ui.JpaFileModel;
import org.eclipse.jpt.jpa.ui.JpaPlatformUi;
import org.eclipse.jpt.jpa.ui.ResourceUiDefinition;
import org.eclipse.jpt.jpa.ui.editors.JpaEditorPageDefinition;
import org.eclipse.jpt.jpa.ui.internal.JptUiMessages;
import org.eclipse.jpt.jpa.ui.internal.plugin.JptJpaUiPlugin;
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.part.MultiPageEditorSite;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;
import org.eclipse.wst.sse.ui.StructuredTextEditor;

/**
 * This is an implementation of a multi-page editor that includes an
 * XML source editor source tab as the last tab. The other tabs
 * are defined by {@link ResourceUiDefinition#getEditorPageDefinitions()}
 * The ResourceUiDefinition is found from the <code>JptResourceType</code> of the
 * <code>JpaFile</code>
 *
 * @version 3.3
 * @since 3.3
 */
public class JpaXmlEditor
	extends FormEditor
{

	/**
	 * The IFileEditorInput model.
	 * <p>
	 * @see #setInput(IEditorInput)
	 */
	private final ModifiablePropertyValueModel<IFileEditorInput> editorInputModel = new SimplePropertyValueModel<IFileEditorInput>();

	/**
	 * The root structure node model is built from the editorInputModel. We assume
	 * there is only 1 root structure node in the JpaFile. This is true of the
	 * persistence.xml and orm.xml models.
	 * <p>
	 * We listen to changes to this model and swap out the editor pages.
	 * Do not use this model as the subjectModel for the pages, 
	 * we need to control that model ourselves.
	 * @see #pageRootStructureNodeModel
	 */
	private PropertyValueModel<JpaStructureNode> rootStructureNodeModel;

	/**
	 * Store the root structure node listener so we can remove it on {@link #dispose()}.
	 * Listens to {@link #rootStructureNodeModel} in order to swap out the editor pages.
	 */
	private final PropertyChangeListener rootStructureNodeListener = new RootStructureNodeListener();

	/**
	 * This root structure node model is passed to the pages. This gives us control
	 * over when the subject is changed for the pages.
	 */
	private ModifiablePropertyValueModel<JpaStructureNode> pageRootStructureNodeModel;


	/**
	 * The XML source text editor.
	 */
	private final StructuredTextEditor structuredTextEditor;

	/**
	 * The factory used to create the various widgets.
	 */
	private final WidgetFactory widgetFactory;

	/**
	 * The local resource manager, used to create/destroy Images
	 */
	final ResourceManager localResourceManager;


	public JpaXmlEditor() {
		super();
		this.localResourceManager = new LocalResourceManager(JFaceResources.getResources());
		this.widgetFactory = this.buildWidgetFactory();
		this.structuredTextEditor = new StructuredTextEditor();
		this.structuredTextEditor.setEditorPart(this);
	}

	private WidgetFactory buildWidgetFactory() {
		return new FormWidgetFactory(
			new TabbedPropertySheetWidgetFactory()
		);
	}

	@Override
	public void init(IEditorSite site, IEditorInput editorInput) throws PartInitException {
		Assert.isLegal(editorInput instanceof IFileEditorInput, "Invalid Input: Must be IFileEditorInput"); //$NON-NLS-1$
		super.init(site, editorInput);

		this.setPartName(editorInput.getName());
		this.rootStructureNodeModel = this.buildRootStructureNodeModel();
		this.rootStructureNodeModel.addPropertyChangeListener(PropertyValueModel.VALUE, this.rootStructureNodeListener);
	}

	@Override
	protected void setInput(IEditorInput editorInput) {
		super.setInput(editorInput);
		this.editorInputModel.setValue((IFileEditorInput) editorInput);
	}

	@Override
	protected void setInputWithNotify(IEditorInput editorInput) {
		super.setInputWithNotify(editorInput);
		this.editorInputModel.setValue((IFileEditorInput) editorInput);
	}

	@Override
	protected void addPages() {
		this.addXMLSourceEditorPage();
		if (this.rootStructureNodeModel.getValue() != null) {
			this.setPageRootStructureNode_(this.rootStructureNodeModel.getValue());
			this.setActivePage(0);
		}
	}

	/**
	 * Adds the page containing the XML source editor.
	 */
	protected void addXMLSourceEditorPage() {
		try {
			int index = this.addPage(this.structuredTextEditor, this.getEditorInput());
			this.setPageText(index, JptUiMessages.JpaXmlEditor_sourcePage);
		}
		catch (PartInitException e) {
			JptJpaUiPlugin.instance().logError(e);
		}
	}

	/**
	 * Add the pages for editing the selected JpaStructureNode. These
	 * will be the pages that come before the XML source editor.
	 * <p>
	 * @see #getRootStructureNode()
	 * @see Page
	 */
	protected void addSpecificPages(PropertyValueModel<JpaStructureNode> structureNodeModel) {
		JpaStructureNode rootStructureNode = structureNodeModel.getValue();
		JptResourceType resourceType = rootStructureNode.getResourceType();
		if (resourceType == null) {
			return;  // might not ever get here... (if we have a p.xml, it probably has a resource type...)
		}

		JpaPlatform jpaPlatform = rootStructureNode.getJpaPlatform();
		JpaPlatformUi jpaPlatformUI = (JpaPlatformUi) jpaPlatform.getAdapter(JpaPlatformUi.class);
		ResourceUiDefinition definition = jpaPlatformUI.getResourceUiDefinition(resourceType);

		ListIterable<JpaEditorPageDefinition> pageDefinitions = definition.getEditorPageDefinitions();

		for (JpaEditorPageDefinition editorPageDefinition : pageDefinitions) {
			FormPage formPage = new Page(editorPageDefinition, structureNodeModel);

			int index = this.getPageCount() == 0 ? 0 : this.getPageCount() - 1;//always keep the source tab as the last tab
			try {
				this.addPage(index, formPage);
			}
			catch (PartInitException e) {
				JptJpaUiPlugin.instance().logError(e);
			}
		}
	}

	@Override
	protected IEditorSite createSite(IEditorPart editor) {
		if (editor == this.structuredTextEditor) {
			return new MultiPageEditorSite(this, editor) {
				@Override
				public String getId() {
					// sets this id so nested editor is considered an xml source page
					// I know this makes the XML source toolbar buttons appear on all
					// the tabs instead of just the Source tab, not sure what else it does ~kfb
					return "org.eclipse.core.runtime.xml.source"; //$NON-NLS-1$;
				}
			};
		}
		return super.createSite(editor);
	}

	/**
	 * Delegate to the {@link #structuredTextEditor} if necessary.
	 */
	@Override
	public Object getAdapter(@SuppressWarnings("rawtypes") Class adapterClass) {
		Object adapter = super.getAdapter(adapterClass);
		return (adapter != null) ? adapter : this.structuredTextEditor.getAdapter(adapterClass);
	}

	@Override
	public void doSave(IProgressMonitor monitor) {
		this.structuredTextEditor.doSave(monitor);
	}

	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}

	@Override
	public void doSaveAs() {
		// do nothing
	}


	// ********** JPA file **********

	private PropertyValueModel<JpaFile> buildJpaFileModel() {
		return new DoublePropertyValueModel<JpaFile>(this.buildJpaFileModelModel());
	}

	private PropertyValueModel<PropertyValueModel<JpaFile>> buildJpaFileModelModel() {
		return new TransformationPropertyValueModel<IFileEditorInput, PropertyValueModel<JpaFile>>(this.editorInputModel, JPA_FILE_MODEL_TRANSFORMER);
	}

	private static final Transformer<IFileEditorInput, PropertyValueModel<JpaFile>> JPA_FILE_MODEL_TRANSFORMER = new JpaFileModelTransformer();

	/* CU private */ static class JpaFileModelTransformer
		extends AbstractTransformer<IFileEditorInput, PropertyValueModel<JpaFile>>
	{
		@Override
		protected PropertyValueModel<JpaFile> transform_(IFileEditorInput fileEditorInput) {
			return (JpaFileModel) fileEditorInput.getFile().getAdapter(JpaFileModel.class);
		}
	}

	/* CU private */ class RootStructureNodeListener
			extends PropertyChangeAdapter
	{
		@Override
		public void propertyChanged(PropertyChangeEvent event) {
			JpaXmlEditor.this.setPageRootStructureNode((JpaStructureNode) event.getNewValue());
		}
	}

	/* CU private */ void setPageRootStructureNode(JpaStructureNode jpaStructureNode) {
		this.execute(new SetPageRootStructureNodeRunnable(jpaStructureNode));
	}

	protected void setPageRootStructureNode_(JpaStructureNode rootStructureNode) {
		if (this.pageRootStructureNodeModel != null) {
			this.pageRootStructureNodeModel.setValue(null);
			this.pageRootStructureNodeModel = null;
		}
		if (this.getPageCount() > 1) {
			//set the XML source editor to be the active page before removing the other pages.
			//If I don't do this and the active page gets removed it will build the contents
			//of the next page. I don't want to do this since I'm trying to remove all the
			//pages except the XML source editor page.
			this.setActivePage(this.getPageCount() - 1);

			while (this.getPageCount() > 1) {//don't remove the XML Editor page
				this.removePage(0);
			}
		}
		if (rootStructureNode != null) {
			this.pageRootStructureNodeModel = new SimplePropertyValueModel<JpaStructureNode>(rootStructureNode);
			this.addSpecificPages(this.pageRootStructureNodeModel);
		}
	}

	//*should* be only 1 root structure node for the jpa file (this is true for persistence.xml and orm.xml files)
	protected PropertyValueModel<JpaStructureNode> buildRootStructureNodeModel() {
		return new CollectionPropertyValueModelAdapter<JpaStructureNode, JpaStructureNode>(this.buildRootStructureNodesCollectionModel()) {
			@Override
			protected JpaStructureNode buildValue() {
				return this.collectionModel.size() > 0 ? this.collectionModel.iterator().next() : null;
			}
		};
	}

	protected CollectionValueModel<JpaStructureNode> buildRootStructureNodesCollectionModel() {
		return new CollectionAspectAdapter<JpaFile, JpaStructureNode>(this.buildJpaFileModel(), JpaFile.ROOT_STRUCTURE_NODES_COLLECTION) {
			@Override
			protected Iterable<JpaStructureNode> getIterable() {
				return this.subject.getRootStructureNodes();
			}
	
			@Override
			protected int size_() {
				return this.subject.getRootStructureNodesSize();
			}
		};
	}

	/* CU private */ class SetPageRootStructureNodeRunnable
			implements Runnable
	{
		private final JpaStructureNode jpaStructureNode;

		SetPageRootStructureNodeRunnable(JpaStructureNode jpaStructureNode) {
			super();
			this.jpaStructureNode = jpaStructureNode;
		}

		public void run() {
			JpaXmlEditor.this.setPageRootStructureNode_(this.jpaStructureNode);
		}

		@Override
		public String toString() {
			return ObjectTools.toString(this, this.jpaStructureNode);
		}
	}


	
	@Override
	public void dispose() {
		this.editorInputModel.setValue(null);
		this.localResourceManager.dispose();
		this.rootStructureNodeModel.removePropertyChangeListener(PropertyValueModel.VALUE, this.rootStructureNodeListener);

		super.dispose();
	}


	// ********** misc **********

	@Override
	public IFileEditorInput getEditorInput() {
		return (IFileEditorInput) super.getEditorInput();
	}

	public WidgetFactory getWidgetFactory() {
		return this.widgetFactory;
	}

	private void execute(Runnable runnable) {
		SWTUtil.execute(this.getSite().getShell().getDisplay(), runnable);
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.editorInputModel.getValue().getFile());
	}


	// ********** form page **********

	/**
	 * This extension over <code>FormPage</code> simply completes the layout by
	 * using the <code>JpaEditorPageDefinition</code> 
	 * 
	 * @see JpaEditorPageDefinition#buildEditorPageContent(IManagedForm, WidgetFactory, PropertyValueModel)
	 */
	class Page
		extends FormPage
	{
		/**
		 * The editor page definition, find the page's text, image, help ID
		 * and build the content.
		 */
		private final JpaEditorPageDefinition editorPageDefinition;

		/**
		 * This root structure node model.
		 */
		private PropertyValueModel<JpaStructureNode> structureNodeModel;

		/**
		 * The FormPage's image descriptor, stored so that we can dispose of it.
		 */
		private ImageDescriptor imageDescriptor;

		Page(JpaEditorPageDefinition editorPageDefinition, PropertyValueModel<JpaStructureNode> structureNodeModel) {
			super(JpaXmlEditor.this,
					editorPageDefinition.getClass().getName(),
					editorPageDefinition.getPageText());

			this.editorPageDefinition = editorPageDefinition;
			this.structureNodeModel = structureNodeModel;
		}

		@Override
		protected void createFormContent(IManagedForm managedForm) {

			ScrolledForm form = managedForm.getForm();
			managedForm.getToolkit().decorateFormHeading(form.getForm());

			// Update the text and image
			updateForm(form);

			// Update the layout
			updateBody(managedForm);

			// This will finish the initialization of the buttons
			updateHelpButton();
			form.updateToolBar();
		}

		/**
		 * Updates the text and image of the form. The image can be null.
		 * 
		 */
		private void updateForm(ScrolledForm form) {
			form.setText(this.editorPageDefinition.getPageText());

			this.imageDescriptor = this.editorPageDefinition.getPageImageDescriptor();
			if (this.imageDescriptor != null) {
				form.setImage(JpaXmlEditor.this.localResourceManager.createImage(this.imageDescriptor));
			}
		}

		/**
		 * Adds the page's control to this page.
		 */
		private void updateBody(IManagedForm form) {
			Composite body = form.getForm().getBody();
			body.setLayout(new GridLayout(1, true));

			this.editorPageDefinition.buildEditorPageContent(form, getWidgetFactory(), this.structureNodeModel);
			//calling this because it makes the scroll bar appear on the editor tabs when the content
			//is larger than the editor tab area. Not sure how else to make this happen
			form.reflow(true);
		}

		/**
		 * Adds a help button to the page's toolbar if a help ID exists.
		 */
		private void updateHelpButton() {
			String helpID = this.editorPageDefinition.getHelpID();

			if (helpID != null) {
				Action helpAction = new HelpAction(helpID);

				ScrolledForm form = getManagedForm().getForm();
				IToolBarManager manager = form.getToolBarManager();
				manager.add(helpAction);
			}
		}

		@Override
		public void dispose() {
			JpaXmlEditor.this.localResourceManager.destroyImage(this.imageDescriptor);
			super.dispose();
		}

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			StringBuilderTools.appendHashCodeToString(sb, this);
			sb.append('(');
			sb.append(this.editorPageDefinition.getPageText());
			sb.append(')');
			return sb.toString();
		}

		private class HelpAction
			extends Action
		{
			final String helpID;

			HelpAction(String helpID) {
				super(JptUiMessages.JpaXmlEditor_page_help);
				this.helpID = helpID;
			}

			@Override
			public ImageDescriptor getImageDescriptor() {
				return PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_LCL_LINKTO_HELP);
			}

			@Override
			public void run() {
				BusyIndicator.showWhile(getManagedForm().getForm().getDisplay(), new Runnable() {
					public void run() {
						PlatformUI.getWorkbench().getHelpSystem().displayHelp(HelpAction.this.helpID);
					}
				});
			}
		}
	}
}