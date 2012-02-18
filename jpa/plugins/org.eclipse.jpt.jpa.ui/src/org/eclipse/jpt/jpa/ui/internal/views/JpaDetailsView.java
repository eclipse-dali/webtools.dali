/*******************************************************************************
 * Copyright (c) 2006, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.views;

import java.util.HashMap;
import org.eclipse.jpt.common.ui.internal.widgets.PropertySheetWidgetFactory;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.model.event.PropertyChangeEvent;
import org.eclipse.jpt.common.utility.model.listener.PropertyChangeAdapter;
import org.eclipse.jpt.common.utility.model.listener.PropertyChangeListener;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.jpa.core.JpaStructureNode;
import org.eclipse.jpt.jpa.ui.JptJpaUiPlugin;
import org.eclipse.jpt.jpa.ui.details.JpaDetailsPageManager;
import org.eclipse.jpt.jpa.ui.internal.JptUiMessages;
import org.eclipse.jpt.jpa.ui.selection.JpaViewManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IViewPart;
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
	 * The factory used by the details view and its page managers
	 * to create the their widgets.
	 */
	private final PropertySheetWidgetFactory widgetFactory = new PropertySheetWidgetFactory();

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
	private volatile JpaDetailsPageManager<? extends JpaStructureNode> currentPageManager;

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
	private final HashMap<JpaStructureNode.ContextType, JpaDetailsPageManager<? extends JpaStructureNode>> pageManagers =
			new HashMap<JpaStructureNode.ContextType, JpaDetailsPageManager<? extends JpaStructureNode>>();


	public JpaDetailsView() {
		super();
	}

	@Override
	public void createPartControl(Composite parent) {
		this.scrolledForm = this.widgetFactory.getWidgetFactory().createScrolledForm(parent);
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

	private Control buildDefaultPage() {
		Composite composite = this.widgetFactory.createComposite(this.pageBook);
		composite.setLayout(new FillLayout(SWT.VERTICAL));
		this.widgetFactory.createLabel(composite, JptUiMessages.JpaDetailsView_viewNotAvailable);
		return composite;
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
					JptJpaUiPlugin.log(ex);
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
						JptJpaUiPlugin.log(ex);
						this.currentPageManager = null;  // leave default page
					}
				}
			} else {  // node => node
				JpaDetailsPageManager<? extends JpaStructureNode> pageManager = this.getPageManager(node);
				if (pageManager != this.currentPageManager) {
					try {
						this.currentPageManager.setSubject(null);
					} catch (RuntimeException ex) {
						JptJpaUiPlugin.log(ex);
					}
					this.currentPageManager = pageManager;
					pageChange = true;
				}
				if (this.currentPageManager != null) {
					try {
						this.currentPageManager.setSubject(node);
					} catch (RuntimeException ex) {
						JptJpaUiPlugin.log(ex);
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
	private JpaDetailsPageManager<? extends JpaStructureNode> getPageManager(JpaStructureNode node) {
		JpaStructureNode.ContextType nodeType = node.getContextType();
		JpaDetailsPageManager<? extends JpaStructureNode> pageManager = this.pageManagers.get(nodeType);
		if (pageManager == null) {
			pageManager = this.buildPageManager(node);
			if (pageManager != null) {
				this.pageManagers.put(nodeType, pageManager);
			}
		}
		return pageManager;
	}

	private JpaDetailsPageManager<? extends JpaStructureNode> buildPageManager(JpaStructureNode node) {
		return this.getJpaDetailsPageManagerFactory(node).buildPageManager(this.pageBook, this.widgetFactory);
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
		this.manager.getJpaSelectionModel().removePropertyChangeListener(PropertyValueModel.VALUE, this.jpaSelectionListener);
		this.manager.dispose();

		if (this.currentPageManager != null) {
			this.currentPageManager.setSubject(null);
			this.currentPageManager = null;
		}
		for (JpaDetailsPageManager<? extends JpaStructureNode> detailsPage : this.pageManagers.values()) {
			detailsPage.dispose();
		}
		this.pageManagers.clear();
	}

	@Override
	public String toString() {
		return StringTools.buildToStringFor(this);
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
			return StringTools.buildToStringFor(this);
		}
	}
}
