/*******************************************************************************
 * Copyright (c) 2006, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.views;

import java.util.HashMap;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.resource.LocalResourceManager;
import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jpt.common.core.internal.utility.PlatformTools;
import org.eclipse.jpt.common.ui.internal.widgets.FormWidgetFactory;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.model.event.PropertyChangeEvent;
import org.eclipse.jpt.common.utility.model.listener.PropertyChangeAdapter;
import org.eclipse.jpt.common.utility.model.listener.PropertyChangeListener;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.JpaStructureNode;
import org.eclipse.jpt.jpa.ui.JpaWorkbench;
import org.eclipse.jpt.jpa.ui.details.JpaDetailsPageManager;
import org.eclipse.jpt.jpa.ui.internal.JptUiMessages;
import org.eclipse.jpt.jpa.ui.internal.plugin.JptJpaUiPlugin;
import org.eclipse.jpt.jpa.ui.selection.JpaViewManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.part.PageBook;
import org.eclipse.ui.part.ViewPart;

/**
 * The JPA details view has a page book that displays the JPA details page
 * corresponding to the current JPA selection. The JPA details only listens to
 * the JPA selection; it will never change the JPA selection.
 * <p>
 * See <code>org.eclipse.jpt.jpa.ui/plugin.xml</code>.
 *
 * @version 3.0
 * @since 1.0
 */
public class JpaDetailsView
	extends ViewPart
{
	/**
	 * The scrolled form that holds the {@link #pageBook page book}.
	 * We need to force it to reflow whenever we change the page book's
	 * current page.
	 */
	private volatile ScrolledForm scrolledForm;

	/**
	 * The container of all the details pages.
	 */
	private volatile PageBook pageBook;

	/**
	 * The page displayed by the {@link #pageBook page book}
	 * when nothing can be shown.
	 */
	private volatile Control defaultPage;

	/**
	 * The current JPA details page manager that was built based on the
	 * current JPA selection.
	 */
	private volatile JpaDetailsPageManager currentPageManager;

	/**
	 * The resource manager is created when the view's control is
	 * {@link #createPartControl(Composite) created}
	 * and disposed, if necessary, when the view is
	 * {@link #dispose() disposed}.
	 */
	private volatile ResourceManager resourceManager;

	/**
	 * The factory used by the details view and its page managers
	 * to create the their widgets.
	 * The widgetFactory is created when the view's control is
	 * {@link #createPartControl(Composite) created}
	 * and disposed, if necessary, when the view is
	 * {@link #dispose() disposed}.
	 */
	private volatile FormWidgetFactory widgetFactory;

	/**
	 * The manager is created when the view's control is
	 * {@link #createPartControl(Composite) created}
	 * and disposed, if necessary, when the view is
	 * {@link #dispose() disposed}.
	 */
	private volatile Manager manager;

	/**
	 * Listen for changes to the JPA selection.
	 */
	private final PropertyChangeListener jpaSelectionListener = new JpaSelectionListener();

	/**
	 * Cache of JPA details page managers
	 * keyed by JPA structure node context type.
	 */
	private final HashMap<JpaStructureNode.ContextType, JpaDetailsPageManager> pageManagers =
			new HashMap<JpaStructureNode.ContextType, JpaDetailsPageManager>();


	public JpaDetailsView() {
		super();
	}

	@Override
	public void createPartControl(Composite parent) {
		this.resourceManager = this.buildResourceManager();
		this.widgetFactory = new FormWidgetFactory(new FormToolkit(parent.getDisplay()));
		this.scrolledForm = this.widgetFactory.createScrolledForm(parent);
		JptJpaUiPlugin.instance().controlAffectsJavaSource(this.scrolledForm);
		this.scrolledForm.getBody().setLayout(new GridLayout());

		this.pageBook = new PageBook(this.scrolledForm.getBody(), SWT.NONE);
		GridData gridData = new GridData();
		gridData.grabExcessHorizontalSpace = true;
		gridData.horizontalAlignment = SWT.FILL;
		this.pageBook.setLayoutData(gridData);

		this.defaultPage = this.buildDefaultPage();
		this.pageBook.showPage(this.defaultPage);

		this.manager = this.buildManager();
		this.manager.getJpaSelectionModel().addPropertyChangeListener(PropertyValueModel.VALUE, this.jpaSelectionListener);
		this.setJpaSelection(this.manager.getJpaSelectionModel().getValue());
	}

	private ResourceManager buildResourceManager() {
		JpaWorkbench jpaWorkbench = this.getJpaWorkbench();
		return (jpaWorkbench != null) ? jpaWorkbench.buildLocalResourceManager() : new LocalResourceManager(JFaceResources.getResources(this.getWorkbench().getDisplay()));
	}

	private JpaWorkbench getJpaWorkbench() {
		return PlatformTools.getAdapter(this.getWorkbench(), JpaWorkbench.class);
	}

	private IWorkbench getWorkbench() {
		return this.getSite().getWorkbenchWindow().getWorkbench();
	}

	private Control buildDefaultPage() {
		return this.widgetFactory.createLabel(this.pageBook, JptUiMessages.JpaDetailsView_viewNotAvailable);
	}

	private Manager buildManager() {
		return new Manager(this.getPageManager());
	}

	/**
	 * Go to the singleton in the sky.
	 * <p>
	 * <strong>NB:</strong> This will trigger the creation of the appropriate
	 * page manager if it does not already exist.
	 */
	private JpaViewManager.PageManager getPageManager() {
		return (JpaViewManager.PageManager) this.getAdapter(JpaViewManager.PageManager.class);
	}


	// ********** JPA selection **********

	/* CU private */ class JpaSelectionListener
			extends PropertyChangeAdapter
	{
		@Override
		public void propertyChanged(PropertyChangeEvent event) {
			JpaDetailsView.this.setJpaSelection((JpaStructureNode) event.getNewValue());
		}
	}

	/* CU private */ void setJpaSelection(JpaStructureNode node) {
		boolean pageChange = false;
		if (node == null) {
			if (this.currentPageManager == null) {  // null => null
				// do nothing
			} else {  // node => null
				try {
					this.currentPageManager.setSubject(null);
				} catch (RuntimeException ex) {
					JptJpaUiPlugin.instance().logError(ex);
				}
				this.currentPageManager = null;
				pageChange = true;
			}
		} else {
			if (this.currentPageManager == null) {  // null => node
				this.currentPageManager = this.getPageManager(node);
				if (this.currentPageManager != null) {
					try {
						this.currentPageManager.setSubject(node);
						pageChange = true;
					} catch (RuntimeException ex) {
						JptJpaUiPlugin.instance().logError(ex);
						this.currentPageManager = null;  // leave default page
					}
				}
			} else {  // node => node
				JpaDetailsPageManager pageManager = this.getPageManager(node);
				if (pageManager != this.currentPageManager) {
					try {
						this.currentPageManager.setSubject(null);
					} catch (RuntimeException ex) {
						JptJpaUiPlugin.instance().logError(ex);
					}
					this.currentPageManager = pageManager;
					pageChange = true;
				}
				if (this.currentPageManager != null) {
					try {
						this.currentPageManager.setSubject(node);
					} catch (RuntimeException ex) {
						JptJpaUiPlugin.instance().logError(ex);
						this.currentPageManager = null;  // show default page
						pageChange = true;
					}
				}
			}
		}

		if (pageChange) {
			this.pageBook.showPage(this.getCurrentPage());
			this.scrolledForm.reflow(true);  // true => flush cache
		}
	}

	private Control getCurrentPage() {
		return (this.currentPageManager != null) ? this.currentPageManager.getPage() : this.defaultPage;
	}


	// ********** page managers **********

	/**
	 * @see org.eclipse.jpt.jpa.core.JpaStructureNode.ContextType
	 */
	private JpaDetailsPageManager getPageManager(JpaStructureNode node) {
		JpaStructureNode.ContextType nodeType = node.getContextType();
		JpaDetailsPageManager pageManager = this.pageManagers.get(nodeType);
		if (pageManager == null) {
			pageManager = this.buildPageManager(node);
			if (pageManager != null) {
				this.pageManagers.put(nodeType, pageManager);
			}
		}
		return pageManager;
	}

	private JpaDetailsPageManager buildPageManager(JpaStructureNode node) {
		return this.getJpaDetailsPageManagerFactory(node).buildPageManager(this.pageBook, this.widgetFactory, this.resourceManager);
	}

	private JpaDetailsPageManager.Factory getJpaDetailsPageManagerFactory(JpaStructureNode node) {
		return (JpaDetailsPageManager.Factory) node.getAdapter(JpaDetailsPageManager.Factory.class);
	}


	// ********** misc **********

	@Override
	public void setFocus() {
		this.pageBook.setFocus();
	}

	@Override
	public void dispose() {
		if (this.manager != null) {
			this.dispose_();
		}
		super.dispose();
	}

	private void dispose_() {
		if (this.currentPageManager != null) {
			this.currentPageManager.setSubject(null);
			this.currentPageManager = null;
		}

		this.pageManagers.clear();

		this.manager.getJpaSelectionModel().removePropertyChangeListener(PropertyValueModel.VALUE, this.jpaSelectionListener);
		this.manager.dispose();

		this.widgetFactory.dispose();

		this.resourceManager.dispose();
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this);
	}


	// ********** JPA view manager **********

	/**
	 * Adapter to the view's page manager.
	 */
	/* CU private */ class Manager
		implements JpaViewManager
	{
		/**
		 * The manager for the structure view's workbench page.
		 */
		private final JpaViewManager.PageManager pageManager;


		Manager(JpaViewManager.PageManager pageManager) {
			super();
			if (pageManager == null) {
				throw new NullPointerException();  // shouldn't happen...
			}
			this.pageManager = pageManager;
			this.pageManager.addViewManager(this);
		}

		public IViewPart getView() {
			return JpaDetailsView.this;
		}

		ModifiablePropertyValueModel<JpaStructureNode> getJpaSelectionModel() {
			return this.pageManager.getJpaSelectionModel();
		}

		void dispose() {
			this.pageManager.removeViewManager(this);
		}

		@Override
		public String toString() {
			return ObjectTools.toString(this);
		}
	}
}
